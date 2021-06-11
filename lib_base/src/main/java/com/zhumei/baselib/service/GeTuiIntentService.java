package com.zhumei.baselib.service;

import android.annotation.SuppressLint;
import android.content.Context;

import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import com.zhumei.baselib.app.AppConstants;
import com.zhumei.baselib.base.Event;
import com.zhumei.baselib.utils.useing.cache.CacheUtils;
import com.zhumei.baselib.utils.useing.event.EventBusUtils;
import com.zhumei.baselib.utils.useing.hardware.TimeSwitchUtils;
import com.zhumei.baselib.utils.useing.software.LogUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * 继承 GTIntentService 接收来自个推的消息, 所有消息在线程中回调, 如果注册了该服务, 则务必要在 AndroidManifest中声明, 否则无法接受消息
 * onReceiveMessageData 处理透传消息
 * onReceiveClientId 接收 cid
 * onReceiveOnlineState cid 离线上线通知
 * onReceiveCommandResult 各种事件处理回执
 */
public class GeTuiIntentService extends GTIntentService {

    private static final String TAG = "GeTuiIntentService";
    private String mDevicePath;
    private int mBoardFlag;


    @Override
    public void onReceiveServicePid(Context context, int pid) {
    }

    @Override
    public void onReceiveClientId(Context context, String clientid) {
        LogUtils.e("GeTuiIntentService==>", clientid);
        CacheUtils.putString(AppConstants.Cache.CLIENT_ID, clientid);
    }

    /**
     * 处理透传消息
     *
     * @param context
     * @param gtTransmitMessage
     */
    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage gtTransmitMessage) {
        try {
            String geTuiMsg = new String(gtTransmitMessage.getPayload());
            // 需要全局使用的推送
            // 定时开关设置 {"event_type":"12" , "shut_down":"1" , switcher_clear":"1" , "open_time":"20190103070000" , "off_time":"20190103210000"}
//            if (geTuiMsg.contains(AppConstants.PushEventType.PUSH_TIME_SWITCH)) {
////                timeSwitch(context, geTuiMsg);
//                // 远程截屏 {"event_type":"13" , "screen_shot":"1"}
//            } else if (geTuiMsg.contains(AppConstants.PushEventType.PUSH_SCREEN_SHOT)) {
////                screenShot(context, geTuiMsg);
//                // 个推的全局推送，传递到每个activity中
//            } else {
//                EventBusUtils.post(new Event<>(AppConstants.EventCode.GETUI_MSG, geTuiMsg));
//            }

            EventBusUtils.post(new Event<>(AppConstants.EventCode.GETUI_MSG, geTuiMsg));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onReceiveOnlineState(Context context, boolean online) {
    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage gtCmdMessage) {
    }

    @Override
    public void onNotificationMessageArrived(Context context, GTNotificationMessage gtNotificationMessage) {
    }

    @Override
    public void onNotificationMessageClicked(Context context, GTNotificationMessage gtNotificationMessage) {
    }

    /**
     * 设置定时开关
     *
     * @param context
     * @param geTuiMsg
     */
//    private void timeSwitch(Context context, String geTuiMsg) {
//        try {
//            LogUtils.e(TAG, "PUSH_TIME_SWITCH:" + geTuiMsg);
//            MmkvUtils.getInstance().putString(AppConstants.Cache.TIME_SWITCH, geTuiMsg);
//            if (!TextUtils.isEmpty(geTuiMsg) && !AppConstants.CommonStr.NULL_STR.equals(geTuiMsg) && !AppConstants.CommonStr.MIDDLE_BRACKETS.equals(geTuiMsg)) {
//                TimeSwitchPushBean timeSwitchPushBean = JSON.parseObject(geTuiMsg, TimeSwitchPushBean.class);
//                // 定时开关设置
//                String openTime = timeSwitchPushBean.getOpenTime();
//                String offTime = timeSwitchPushBean.getOffTime();
//                // 真南的软件推送定时这里有点问题 从接口获取是没问题的 如果解决要安装更新
//                if (!TextUtils.isEmpty(openTime) && !AppConstants.CommonStr.NULL_STR.equals(openTime) && (!TextUtils.isEmpty(offTime) && !AppConstants.CommonStr.NULL_STR.equals(offTime))
//                        && openTime.length() == 14 && offTime.length() == 14) {
//                    // 定时开关设置
//                    @SuppressLint("SimpleDateFormat")
//                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
//                    openTime = openTime.substring(0, 11) + new Random().nextInt(6) + new Random().nextInt(7) + new Random().nextInt(6);
//                    Date openDate = sdf1.parse(openTime);
//                    Date offDate = sdf1.parse(offTime);
//                    // 格式化设置的时分秒
//                    Calendar openCalendar = Calendar.getInstance();
//                    openCalendar.setTime(openDate);
//                    Calendar offCalendar = Calendar.getInstance();
//                    offCalendar.setTime(offDate);
//                    // 格式化本地的年月日
//                    @SuppressLint("SimpleDateFormat")
//                    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
//                    Date date = sdf2.parse(sdf2.format(new Date(System.currentTimeMillis())));
//                    Calendar calendar = Calendar.getInstance();
//                    calendar.setTime(date);
//                    TimeSwitchUtils.setTimeSwitch(context, openCalendar, offCalendar, calendar, mBoardFlag);
//                } else {
//                    setDefaultTimeSwitch(context);
//                }
//
//            } else {
//                setDefaultTimeSwitch(context);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            setDefaultTimeSwitch(context);
//        }
//    }

    /**
     * 远程截屏
     *
     * @param context
     * @param geTuiMsg
     */
//    private void screenShot(Context context, String geTuiMsg) {
//        try {
//            LogUtils.e(TAG, "PUSH_SCREEN_SHOT:" + geTuiMsg);
//            ScreenShotPushBean timeSwitchPushBean = JSON.parseObject(geTuiMsg, ScreenShotPushBean.class);
//            if (AppConstants.CommonStr.YES_STR.equals(timeSwitchPushBean.getScreenShot())) {
//                @SuppressLint("SimpleDateFormat")
//                String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(System.currentTimeMillis())) + ".png";
//                File zhumeiShpFile = new File(AppConstants.DefaultSetting.SET_ZHUMEI_COMMERCIAL_SCREEN_PATH);
//                if (!zhumeiShpFile.exists()) {
//                    zhumeiShpFile.mkdir();
//                } else {
//                    LogUtils.e(TAG, "the zhumei folder already exists!");
//                }
//                String fileFullPath = AppConstants.DefaultSetting.SET_ZHUMEI_COMMERCIAL_SCREEN_PATH + File.separator + fileName;
//                // 只针对Root设备以及android 5.0 以下的未Root设备
//                if (AppConstants.BoradType.BORAD_TYPE.equals(AppConstants.BoradType.YANCHENG_PROJECT)) {
//                    Intent cmdIntent = new Intent("com.zhsd.setting.syscmd");
//                    cmdIntent.putExtra("cmd", "screencap");
//                    cmdIntent.putExtra("fullscreen", true);
//                    // scale<=1.0,不缩放;  scale>1.0,图片长宽按scale缩放.缩放的越大,截屏之后，保存图片的速度也更快.
//                    cmdIntent.putExtra("scale", 0.5f);
//                    // 截屏存储位置,请用sdcard（内置internal_sd或者外置U盘）这些公用空间.
//                    cmdIntent.putExtra("filepath", fileFullPath);
//                    sendBroadcast(cmdIntent);
//                } else {
//                    ScreentShotUtils.getInstance().takeScreenshot(context, fileFullPath);
//                }
//
//
//                HashMap<String, String> paramsMap = new HashMap<>();
//                paramsMap.put("device_id",CacheUtils.getString(AppConstants.Cache.DEVICE_ID));
//                paramsMap.put("market_id", CacheUtils.getString(AppConstants.Cache.MARKET_ID));
//                paramsMap.put("screen_shot", Base64Utils.convertBitmapToString(BitmapFactory.decodeFile(fileFullPath)));
//                OkhttpUtil.okHttpPost(ApiConstants.UPLOAD_SCREEN_SHOT_POST, paramsMap, new CallBackUtil.CallBackString() {
//                    @Override
//                    public void onFailure(Call call, Exception e) {
//                        LogUtils.e(TAG, "screenshot upload fail !");
//                    }
//
//                    @Override
//                    public void onResponse(String response) {
//                        LogUtils.e(TAG, "screenshot upload success :" + response);
//
//                    }
//                });
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 设置默认的定时开关
     *
     * @param context
     */
    private void setDefaultTimeSwitch(Context context) {
        try {
            // 格式化本地的年月日
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
            String yearTime = sdf1.format(new Date(System.currentTimeMillis())).substring(0, 8);
            Calendar yearCalendar = Calendar.getInstance();
            yearCalendar.setTime(sdf1.parse(yearTime));
            // 格式化设置的时分秒
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
            // 当前时间的七点 包左不包右
            Date openDate = sdf2.parse(yearTime + AppConstants.DefaultSetting.SET_OPEN_DATE + new Random().nextInt(6) + new Random().nextInt(7) + new Random().nextInt(6));
            // 当前时间的二十点
            Date offDate = sdf2.parse(yearTime + AppConstants.DefaultSetting.SET_OFF_DATE);
            // 格式化设置的时分秒
            Calendar openCalendar = Calendar.getInstance();
            openCalendar.setTime(openDate);
            Calendar offCalendar = Calendar.getInstance();
            offCalendar.setTime(offDate);
            TimeSwitchUtils.setTimeSwitch(context, openCalendar, offCalendar, yearCalendar, mBoardFlag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
