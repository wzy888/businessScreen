package com.zhumei.baselib.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.zhumei.baselib.BuildConfig;
import com.zhumei.baselib.MyBaseApplication;
import com.zhumei.baselib.app.AppConstants;
import com.zhumei.baselib.base.Event;
import com.zhumei.baselib.base.MmkvUtils;
import com.zhumei.baselib.bean.scale.ScaleTrade;
import com.zhumei.baselib.config.Constant;
import com.zhumei.baselib.dao.ScaleDaoUtil;
import com.zhumei.baselib.dao.ScaleTradeDao;
import com.zhumei.baselib.module.response.MerchantInfo;
import com.zhumei.baselib.module.response.TradeDataRes;
import com.zhumei.baselib.receiver.CommitTradeReceiver;
import com.zhumei.baselib.utils.HttpUtils;
import com.zhumei.baselib.utils.useing.event.EventBusUtils;
import com.zhumei.baselib.utils.useing.event.ThreadManager;
import com.zhumei.baselib.utils.useing.software.LogUtils;
import com.zhumei.baselib.utils.useing.software.StringUtils;
import com.zhumei.baselib.utils.useing.software.TimeUtils;


import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import org.greenrobot.greendao.annotation.NotNull;
import org.json.JSONObject;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 后台 提交订单交易Service ...
 */
public class CommitTradeService extends Service {


    private  final String TAG = CommitTradeService.class.getSimpleName();
    private ScaleDaoUtil mScaleDaoUtil;

    private int pageSize = 0;


    private int totalPage = 1;

    private Handler mHandler;
    private int count = 40;
    private int singleCount = 10;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        EventBusUtils.register(this);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        LogUtils.E(TAG, "3min 秒后run()方法执行了！");
//                 初始化 查询页码数
        pageSize = 0;
        if (mHandler == null) {
            mHandler = new Handler();
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                LogUtils.E(TAG, "commitData ，，，开始上传");
                commitData();
            }
        }, 3 * 60 * 1000);


        // 两分钟通过广播执行一次前台后台判断
        long triggerAtTime = SystemClock.elapsedRealtime() + AppConstants.DefaultSetting.SET_UPLOAD_TIME;
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent i = new Intent(this, CommitTradeReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, i, 0);
        // 设置定时
        if (am != null) {
            am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pendingIntent);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void commitData() {

        ThreadManager.executeSingleTask(new Runnable() {
            @Override
            public void run() {
                uploadTradeData();
            }
        });


    }


    /***
     * 分页取订单数据
     * */
    @Subscribe(threadMode = ThreadMode.ASYNC, sticky = true)
    public void onMainThreadEventPost(Event event) {

        if (event == null) {
            LogUtils.e("event is Null!");
            return;
        }

        long totalCount = getTotalCount();
        getTotalPage(totalCount);
        LogUtils.E("总条数==>", String.valueOf(totalCount));
        if (event.getCode() == Constant.OK) {
            Integer page = (Integer) event.getData();
            pageSize = page.intValue();
            if (pageSize <= totalPage) {

                pageSize++;
                LogUtils.E("page Size = ", String.valueOf(pageSize));
                commitData();
            } else {
                pageSize--;
                LogUtils.e("NO MORE DATA ! pages = ", String.valueOf(pageSize));
            }
        } else {
            //最后一页 几条未上传
            if (totalCount > 0) {
                String merchantId = getMerchantId();
                List<ScaleTrade> scaleTrades = mScaleDaoUtil.queryScaleTradeDataList(ScaleTrade.class)
                        .where(ScaleTradeDao.Properties.IsUpdate.eq(AppConstants.CommonInt.NO_INT))
                        .where(ScaleTradeDao.Properties.MerchantId.eq(merchantId))
                        // 一个月内未上传成功的进行查询
                        .where(ScaleTradeDao.Properties.TradeTime.between(TimeUtils.getMonthAgoTime(), TimeUtils.getCurrentTime()))
                        .list();

                String s = new Gson().toJson(scaleTrades);
                LogUtils.E("查询data 222==>", s);
                submitTradeData(scaleTrades, false);
            }
        }

    }


    @Subscribe(threadMode = ThreadMode.ASYNC, sticky = true)
    public void onEventPost(Event event) {

        if (event == null) {
            LogUtils.e("event is Null!");
            return;
        }

        long totalCount = getTotalCount();
        getTotalPage(totalCount);
        LogUtils.e("总条数==>", String.valueOf(totalCount));
        if (event.getCode() == Constant.OK2) {
            uploadTradeDataSingle();

        }

    }


    private void getTotalPage(long total) {
        double f1 = new BigDecimal(total / count)
                .setScale(2, BigDecimal.ROUND_HALF_UP).intValue();
        totalPage = (int) Math.ceil(f1) + 1;
    }

    private void uploadTradeData() {
        try {
            if (mScaleDaoUtil == null) {
                mScaleDaoUtil = new ScaleDaoUtil(MyBaseApplication.getMyApplication());
            }


            // 这里对时间的过滤 保证一下提交的数据不会太多 也保证 submit trade data return:{"status":"false"} 这样的废数据不会太多
            String merchantId = getMerchantId();

//            WhereCondition.StringCondition stringCondition = new WhereCondition.StringCondition("_ID IN (SELECT * FROM SCALE_TRADE WHERE isUpdate = 0)");
            List<ScaleTrade> tradeDataList = mScaleDaoUtil.queryScaleTradeDataList(ScaleTrade.class)
                    .where(ScaleTradeDao.Properties.IsUpdate.eq(AppConstants.CommonInt.NO_INT))
                    .where(ScaleTradeDao.Properties.MerchantId.eq(merchantId))
                    // 一个月内未上传成功的进行查询
                    .where(ScaleTradeDao.Properties.TradeTime.between(TimeUtils.getMonthAgoTime(), TimeUtils.getCurrentTime()))
                    // 10天内未上传成功的进行查询
//                    .where(ScaleTradeDao.Properties.TradeTime.between(TimeUtils.getDayAgoTime(10), TimeUtils.getCurrentTime()))
                    .offset(count * (pageSize - 1))
                    .limit(count)
                    .list();


//            LogUtils.e(TAG, "ago:" + TimeUtils.getDayAgoTime(10) + ",now:" + TimeUtils.getCurrentTime());
            if (BuildConfig.DEBUG) {
                LogUtils.E(TAG, "merchantId :" + merchantId);

                LogUtils.E(TAG, "page :" + (pageSize - 1));
                LogUtils.E("offset =>", String.valueOf(count * (pageSize - 1)));
                LogUtils.E(TAG, "need to submit trade data count:" + tradeDataList.size());
            }

//            String s = new Gson().toJson(tradeDataList);
//
//            LogUtils.E("查询data==>", s);

            submitTradeData(tradeDataList, true);
            SystemClock.sleep(AppConstants.DefaultSetting.UPLOAD_TRADE2);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void delDbCache() {
        try {
            /**
             *  执行插入前 查询数据库缓存大小 >50M 就删除，重新存表上传.
             * */
            //获取app 内部db 路径...  "scale.db"
            String internalAppDbPath = PathUtils.getInternalAppDbPath(Constant.SCALE_DB_NAME);
            if (BuildConfig.DEBUG) {
                LogUtils.e("内部数据库路径-->", internalAppDbPath);
            }
            File file = new File(internalAppDbPath);
            if (file.exists()) {
                String fileSize = FileUtils.getFileSize(file);
                if (BuildConfig.DEBUG) {
                    LogUtils.e("数据库Cache ->", fileSize);
                }


                if (fileSize.contains("MB")) {
                    LogUtils.e("MB ->", fileSize);

                    String numbers = StringUtils.getNumbers(fileSize);
                    BigDecimal bigDecimal = new BigDecimal(numbers);
                    double cacheSize = bigDecimal.doubleValue();

                    if (cacheSize >= 50) {
                        //清除数据库
                        mScaleDaoUtil.deleteAllScaleTradeData(ScaleTrade.class);
                        LogUtils.E(TAG, "清除ScaleTrade Dao !!!");

                    }

                } else if (fileSize.contains("GB")) {
                    LogUtils.E("GB ->", fileSize);
                    //清除数据库
                    mScaleDaoUtil.deleteAllScaleTradeData(ScaleTrade.class);

                }


            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 交易 一单就上传
     */
    private void uploadTradeDataSingle() {
        try {
            if (mScaleDaoUtil == null) {
                mScaleDaoUtil = new ScaleDaoUtil(MyBaseApplication.getMyApplication());
            }
            String merchantId = getMerchantId();

//            mScaleDaoUtil.getScaleTradeDao().detachAll();
            // 这里对时间的过滤 保证一下提交的数据不会太多 也保证 submit trade data return:{"status":"false"} 这样的废数据不会太多
            List<ScaleTrade> tradeDataList = mScaleDaoUtil.queryScaleTradeDataList(ScaleTrade.class)
                    .where(ScaleTradeDao.Properties.IsUpdate.eq(AppConstants.CommonInt.NO_INT))
                    .where(ScaleTradeDao.Properties.MerchantId.eq(merchantId))
                    // 一个月内未上传成功的进行查询
                    .where(ScaleTradeDao.Properties.TradeTime.between(TimeUtils.getMonthAgoTime(), TimeUtils.getCurrentTime()))
                    .offset(0)
                    .limit(singleCount)
                    .list();

            if (BuildConfig.DEBUG) {
                LogUtils.E(TAG, "merchantId: " + merchantId);
                LogUtils.E(TAG, "page :" + (pageSize - 1));
                LogUtils.E("offset =>", String.valueOf(count * (pageSize - 1)));
                LogUtils.E(TAG, "need to submit trade data count:" + tradeDataList.size());
            }


//            String s = new Gson().toJson(tradeDataList);
//            LogUtils.E("查询data==>", s);

            submitTradeData(tradeDataList, false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private long getTotalCount() {
        long count = 0;
        try {
            if (mScaleDaoUtil == null) {
                mScaleDaoUtil = new ScaleDaoUtil(MyBaseApplication.getMyApplication());
            }

            String merchantId = getMerchantId();
            count = mScaleDaoUtil.queryScaleTradeDataList(ScaleTrade.class)
                    .where(ScaleTradeDao.Properties.IsUpdate.eq(AppConstants.CommonInt.NO_INT))
                    .where(ScaleTradeDao.Properties.MerchantId.eq(merchantId))
                    // 一个月内未上传成功的进行查询
                    .where(ScaleTradeDao.Properties.TradeTime.between(TimeUtils.getMonthAgoTime(), TimeUtils.getCurrentTime()))
                    .count();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }

    @NotNull
    private String getMerchantId() {
        String merchnatId = "0";
        try {
            MerchantInfo merchantInfo = MmkvUtils.getInstance().getObject(Constant.MERCHANT_INFO, MerchantInfo.class);
            if (ObjectUtils.isNotEmpty(merchantInfo)) {
                merchnatId = String.valueOf(merchantInfo.getMerchant_id());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return merchnatId;
    }


    private void submitTradeData(final List<ScaleTrade> tradeDataList, final boolean isLimit) {

        try {


            if (tradeDataList != null && tradeDataList.size() > 0) {
                //跟据某个属性分组  高版本 属性API调用
                // 分组 1. Java 8 API
//            Map<String, List<ScaleTrade>> collect = tradeDataList.stream().collect(Collectors.groupingBy(ScaleTrade::getTradeNo));
                /*2、分组算法**/
                List<String> tradeNos = new ArrayList<>();
                tradeNos.clear();


                List<List<ScaleTrade>> scaleTrades = new ArrayList<>();
                scaleTrades.clear();

                Map<String, List<ScaleTrade>> tradeMap = new HashMap<>();
                tradeMap.clear();
                for (ScaleTrade trade : tradeDataList) {
                    List<ScaleTrade> tempList = tradeMap.get(trade.getTradeNo());
                    /*如果取不到数据,那么直接new一个空的ArrayList**/
                    if (tempList == null) {
                        tempList = new ArrayList<>();
                        tempList.add(trade);
                        tradeMap.put(trade.getTradeNo(), tempList);
                    } else {
                        /*某个trade 之前已经存放过了,则直接追加数据到原来的List里**/
                        tempList.add(trade);
                    }
                }


                for (Map.Entry<String, List<ScaleTrade>> entry : tradeMap.entrySet()) {
                    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                    // 根据多个订单号 分组多个订单
                    List<ScaleTrade> value = entry.getValue();
                    String key = entry.getKey();
                    tradeNos.add(key);
                    scaleTrades.add(value);
                }


                HttpUtils.getInstance().checkOrder(tradeNos, scaleTrades, new HttpUtils.HttpCallBack() {
                    @Override
                    public void onSuccess(String jsonData) {
                        if (TextUtils.isEmpty(jsonData)) {
                            LogUtils.e("checkOrder Response is NULL!");
                            return;
                        }

                        LogUtils.E("jsonData==>", jsonData);
                        try {

                            JSONObject jsonObject = new JSONObject(jsonData);
                            String code = jsonObject.getString("code");
                            if (code.equals("0")) {
                                String msg = jsonObject.getString("msg");
                                ToastUtils.showShort(msg);
                                return;
                            }

                            TradeDataRes tradeDataRes = new Gson().fromJson(jsonData, TradeDataRes.class);
                            if (tradeDataRes == null) {
                                LogUtils.e("checkOrder tradeDataRes is NULL!");
                                return;
                            }
                            int threadNum = Runtime.getRuntime().availableProcessors();
                            int corePoolSize = Math.max(2, Math.min(threadNum - 1, 4));
                            if (BuildConfig.DEBUG) {
                                LogUtils.e("checkOrder==>", jsonData);
                                com.blankj.utilcode.util.LogUtils.d("lock 可用线程数： ", corePoolSize);
                            }


                            if (mScaleDaoUtil == null) {
                                mScaleDaoUtil = new ScaleDaoUtil(MyBaseApplication.getMyApplication());
                            }
                            if (tradeDataRes.getCode() == 1 && tradeDataRes.getData() != null) {
                                TradeDataRes.DataBean data = tradeDataRes.getData();
                                if (data.getSearch() != null && data.getSearch().size() > 0) {
                                    List<String> search = data.getSearch();
//                            ["1","2","3","4","5","6","7","8","9"] 比对是否上传的ID

                                    List<ScaleTrade> trades = new ArrayList<>();
                                    trades.clear();
                                    for (int i = 0; i < tradeDataList.size(); i++) {

                                        ScaleTrade scaleTrade = tradeDataList.get(i);
                                        for (int j = 0; j < search.size(); j++) {
                                            String successId = search.get(j);
                                            if (successId.equals(String.valueOf(scaleTrade.getId()))) {
                                                scaleTrade.setIsUpdate(AppConstants.CommonInt.YES_INT);
                                                //单条执行效率低 易卡顿 开启事物
                                                trades.add(scaleTrade);
                                                LogUtils.e(TAG, "Insert successful insertion of ID was !" + successId);

                                            }
                                        }


                                    }
                                    // 更新库
                                    mScaleDaoUtil.upDateScales(trades);

                                }
                            }


                            ThreadManager.executeSingleTask(new Runnable() {
                                @Override
                                public void run() {

                                    // 是否删除库
                                    delDbCache();
                                    SystemClock.sleep(1000);
                                    mScaleDaoUtil.getScaleTradeDao().detachAll();
                                    SystemClock.sleep(AppConstants.DefaultSetting.UPLOAD_TRADE);


                                    if (isLimit) {
                                        //分页
                                        EventBusUtils.postSticky(new Event(Constant.OK, pageSize));

                                    }
                                }


                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onFail(String errorMsg) {
                        LogUtils.e("checkOrder==>", errorMsg);
                        ThreadManager.executeSingleTask(new Runnable() {
                            @Override
                            public void run() {
                                if (isLimit) {
                                    //分页
                                    SystemClock.sleep(AppConstants.DefaultSetting.UPLOAD_TRADE);
                                    EventBusUtils.postSticky(new Event(Constant.OK, pageSize));

                                }
                            }
                        });


                    }
                });

            } else {
                LogUtils.e("submit data is NULL !");
                ThreadManager.executeSingleTask(new Runnable() {
                    @Override
                    public void run() {
                        if (isLimit) {
                            SystemClock.sleep(AppConstants.DefaultSetting.UPLOAD_TRADE);
                            EventBusUtils.postSticky(new Event(Constant.OK, pageSize));

                        }
                    }
                });

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtils.unRegister(this);


        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }
}
