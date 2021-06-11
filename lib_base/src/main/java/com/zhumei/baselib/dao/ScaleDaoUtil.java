package com.zhumei.baselib.dao;

import android.content.Context;
import android.os.SystemClock;

import com.zhumei.baselib.BuildConfig;
import com.zhumei.baselib.app.AppConstants;
import com.zhumei.baselib.bean.commercial_info.ScaleHotkeyCommInfo;
import com.zhumei.baselib.bean.scale.ScaleGoods;
import com.zhumei.baselib.bean.scale.ScaleHotkey;
import com.zhumei.baselib.bean.scale.ScaleTicket;
import com.zhumei.baselib.bean.scale.ScaleTrade;
import com.zhumei.baselib.utils.useing.cache.CacheUtils;
import com.zhumei.baselib.utils.useing.software.LogUtils;

import org.greenrobot.greendao.async.AsyncSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

public class ScaleDaoUtil {

    private DaoSession mDaoSession;

    private CacheUtils cacheUtils;
    private String TAG = "ScaleDaoUtil";

    public ScaleDaoUtil(Context context) {
        ScaleDaoManager scaleDaoManager = new ScaleDaoManager().getInstance();
        scaleDaoManager.initContext(context);
        scaleDaoManager.setDebug(AppConstants.UsefulSetting.DEBUG_FLAG);
        mDaoSession = scaleDaoManager.getDaoSession();
        cacheUtils = new CacheUtils();


    }

    public AsyncSession getAsyncSession() {
        if (mDaoSession == null) {
            mDaoSession = new ScaleDaoManager().getInstance().getDaoSession();
        }
        return mDaoSession.startAsyncSession();
    }

    /**
     * 获取ScaleTradeDao对象
     *
     * @return
     */
    public ScaleTradeDao getScaleTradeDao() {
        return mDaoSession.getScaleTradeDao();
    }

    /**
     * 获取ScaleHotKeyDao对象
     *
     * @return
     */
    public ScaleHotkeyDao getScaleHotKeyDao() {
        return mDaoSession.getScaleHotkeyDao();
    }

    /**
     * 获取ScaleGoodsDao对象
     *
     * @return
     */
    public ScaleGoodsDao getScaleGoodsDao() {
        return mDaoSession.getScaleGoodsDao();
    }

    /**
     * 获取ScaleGoodsDao对象
     *
     * @return
     */
    public ScaleTicketDao getScaleTicketDao() {
        return mDaoSession.getScaleTicketDao();
    }

    /**
     * 增加一条数据
     *
     * @param scaleTrade
     */
    public void insertData(ScaleTrade scaleTrade) {

        mDaoSession.insertOrReplace(scaleTrade);
    }

    /**
     * 增加一条数据
     *
     * @param scaleHotke
     */
    public void insertData(ScaleHotkey scaleHotke) {
        mDaoSession.insertOrReplace(scaleHotke);

    }


    /**
     * 增加 插入多条数据.
     */
    public void insertDatas(List<ScaleHotkey> scaleHotke) {
        mDaoSession.getScaleHotkeyDao().insertInTx(scaleHotke);

    }


    public void insertGoods(List<ScaleGoods> scaleGoods) {
        mDaoSession.getScaleGoodsDao().insertInTx(scaleGoods);

    }

    /**
     * 增加一条数据
     *
     * @param scaleGoods
     */
    public void insertData(ScaleGoods scaleGoods) {
        mDaoSession.insertOrReplace(scaleGoods);
    }

    /**
     * 增加一条数据
     *
     * @param scaleTicket
     */
    public void insertData(ScaleTicket scaleTicket) {
        mDaoSession.insertOrReplace(scaleTicket);
    }

    /**
     * 增加数据集
     *
     * @param scaleTrades
     */
    public void insertScaleTradeDataList(final List<ScaleTrade> scaleTrades) {

        if (scaleTrades == null || scaleTrades.size() <= 0) {
            LogUtils.e("insert Trade is NULL!");
            return;
        }
        try {

            mDaoSession.getScaleTradeDao().insertInTx(scaleTrades);
            if (BuildConfig.DEBUG) {
                LogUtils.e("插入数据 ", scaleTrades.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 增加数据集
     *
     * @param scaleHotkeys
     */
    public void insertScaleHotkeyDataList(final List<ScaleHotkey> scaleHotkeys) {
        mDaoSession.runInTx(new Runnable() {
            @Override
            public void run() {
                for (ScaleHotkey scaleHotkey : scaleHotkeys) {
                    mDaoSession.insertOrReplace(scaleHotkey);
                }
            }
        });
    }

    /**
     * 增加数据集
     *
     * @param scaleGoods
     */
    public void insertScaleGoodsDataList(final List<ScaleGoods> scaleGoods) {
        mDaoSession.runInTx(new Runnable() {
            @Override
            public void run() {
                for (ScaleGoods goods : scaleGoods) {
                    mDaoSession.insertOrReplace(goods);
                }
            }
        });
    }

    /**
     * 增加数据集
     *
     * @param scaleTicket
     */
    public void insertScaleTicketDataList(final List<ScaleTicket> scaleTicket) {
        mDaoSession.runInTx(new Runnable() {
            @Override
            public void run() {
                for (ScaleTicket scaleTickets : scaleTicket) {
                    mDaoSession.insertOrReplace(scaleTickets);
                }
            }
        });
    }

    /**
     * 删除一条数据
     *
     * @param scaleTrade
     */
    public void deleteData(ScaleTrade scaleTrade) {
        mDaoSession.delete(scaleTrade);
    }

    /**
     * 删除一条数据
     *
     * @param scaleHotkey
     */
    public void deleteData(ScaleHotkey scaleHotkey) {
        mDaoSession.delete(scaleHotkey);
    }

    /**
     * 删除一条数据
     *
     * @param scaleGoods
     */
    public void deleteData(ScaleGoods scaleGoods) {
        mDaoSession.delete(scaleGoods);
    }

    /**
     * 删除一条数据
     *
     * @param scaleTicket
     */
    public void deleteData(ScaleTicket scaleTicket) {
        mDaoSession.delete(scaleTicket);
    }

    /**
     * 删除全部数据 scale.class
     */
    public void deleteAllScaleTradeData(Class<ScaleTrade> cls) {
        try {

//            String sql = "update sqlite_sequence SET id = 0 where name ='SCALE_TRADE'";//自增长ID为0
//            mDaoSession.getDatabase().rawQuery(sql,null);
            mDaoSession.deleteAll(cls);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 删除全部数据 scale.class
     */
    public void deleteScaleTradeDataByTime(String time) {
        Database database = mDaoSession.getDatabase();
        //开启事物
        database.beginTransaction();
        try {

//            String sql = "update sqlite_sequence SET id = 0 where name ='SCALE_TRADE'";//自增长ID为0
//            mDaoSession.getDatabase().rawQuery(sql,null);
//            删除数据表中某个时间前的所有数据：
//            delete from 表名 where cast(字段名  as datetime)<'2009-07-21' 2021-04-28  11:00:00
            long count1 = queryScaleTradeDataList(ScaleTrade.class).count();

            String mSql = "delete from SCALE_TRADE where TRADE_TIME  <" + time + "";
            LogUtils.E("sql-->", mSql);
            mDaoSession.getDatabase().execSQL(mSql);
            database.setTransactionSuccessful();

            long count2 = queryScaleTradeDataList(ScaleTrade.class).count();
            LogUtils.E("删除前==>", count1 + " -- " + count2);
            LogUtils.E(TAG, "delete success!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();

        }

    }


    /**
     * 删除全部数据 scale.class
     */
    public void deleteAllScaleHotkeyData(Class<ScaleHotkey> cls) {
        mDaoSession.deleteAll(cls);
    }

    /**
     * 删除全部数据 scale.class
     */
    public void deleteAllScaleGoodsData(Class<ScaleGoods> cls) {
        mDaoSession.deleteAll(cls);
    }

    /**
     * 删除全部数据 scale.class
     */
    public void deleteAllScaleTicketData(Class<ScaleTicket> cls) {
        mDaoSession.deleteAll(cls);
    }

    /**
     * 修改一条数据
     */
    public void updateData(ScaleTrade scaleTrade) {
        if (scaleTrade != null) {
            mDaoSession.update(scaleTrade);
            LogUtils.e("update==>", scaleTrade.toString());
        }
    }


    /**
     * 开启数据库 事物  更新
     */
    public void upDateScales(List<ScaleTrade> trades) {
        if (trades != null && trades.size() > 0) {
            mDaoSession.getScaleTradeDao().updateInTx(trades);

        }

    }

    /**
     * 修改一条数据
     */
    public void updateData(ScaleHotkey scaleHotkey) {
        mDaoSession.update(scaleHotkey);
    }

    /**
     * 修改一条数据
     */
    public void updateData(ScaleGoods scaleGoods) {
        mDaoSession.update(scaleGoods);
    }

    /**
     * 修改一条数据
     */
    public void updateData(ScaleTicket scaleTicket) {
        mDaoSession.update(scaleTicket);
    }

    /**
     * 根据主键查询一条数据
     *
     * @param key
     */
    public ScaleTrade queryScaleTradeData(long key, Class<ScaleTrade> cls) {
        return mDaoSession.load(cls, key);
    }

    /**
     * 根据主键查询一条数据
     *
     * @param key
     */
    public ScaleHotkey queryScaleHotkeyData(long key, Class<ScaleHotkey> cls) {
        return mDaoSession.load(cls, key);
    }

    /**
     * 根据主键查询一条数据
     *
     * @param key
     */
    public ScaleGoods queryScaleGoodsData(long key, Class<ScaleGoods> cls) {
        return mDaoSession.load(cls, key);
    }

    /**
     * 根据主键查询一条数据
     *
     * @param key
     */
    public ScaleTicket queryScaleTicketData(long key, Class<ScaleTicket> cls) {
        return mDaoSession.load(cls, key);
    }

    /**
     * 查询数据集
     *
     * @return
     */
    public QueryBuilder<ScaleTrade> queryScaleTradeDataList(Class<ScaleTrade> cls) {
        return mDaoSession.queryBuilder(cls);
    }

    /**
     * 查询数据集
     *
     * @return
     */
    public QueryBuilder<ScaleHotkey> queryScaleHotkeyDataList(Class<ScaleHotkey> cls) {
        return mDaoSession.queryBuilder(cls);
    }

    /**
     * 查询数据集
     *
     * @return
     */
    public QueryBuilder<ScaleGoods> queryScaleGoodsDataList(Class<ScaleGoods> cls) {
        return mDaoSession.queryBuilder(cls);
    }

    /**
     * 查询数据集
     *
     * @return
     */
    public QueryBuilder<ScaleTicket> queryScaleTicketDataList(Class<ScaleTicket> cls) {
        return mDaoSession.queryBuilder(cls);
    }

    /**
     * 查询所有数据
     *
     * @param cls
     * @return
     */
    public List<ScaleTrade> queryAllScaleTradeData(Class<ScaleTrade> cls) {
        return mDaoSession.loadAll(cls);
    }

    /**
     * 查询所有数据
     *
     * @param cls
     * @return
     */
    public List<ScaleHotkey> queryAllScaleHotkeyData(Class<ScaleHotkey> cls) {
        return mDaoSession.loadAll(cls);
    }

    /**
     * 查询所有数据deleteAllScaleGoodsData
     *
     * @param cls
     * @return
     */
    public List<ScaleGoods> queryAllScaleGoodsData(Class<ScaleGoods> cls) {
        return mDaoSession.loadAll(cls);
    }

    /**
     * 查询所有数据deleteAllScaleTicketData
     *
     * @param cls
     * @return
     */
    public List<ScaleTicket> queryAllScaleTicketData(Class<ScaleTicket> cls) {
        return mDaoSession.loadAll(cls);
    }


    /**
     * 清空电子秤热键本地数据库数据
     */
    public void clearScaleKeyDbData() {
        try {
            cacheUtils.putBoolean(AppConstants.Cache.SCALE_KEY_INFAC_MODIFIED, true);
            deleteAllScaleHotkeyData(ScaleHotkey.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 清空电子秤商品本地数据库数据
     */
    public void clearScaleGoodsDbData() {
        try {
            cacheUtils.putBoolean(AppConstants.Cache.SCALE_GOODS_INFAC_MODIFIED, true);
            deleteAllScaleGoodsData(ScaleGoods.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清空电子秤小票本地数据库数据
     */
    public void clearScaleTicketDbData() {
        try {
            cacheUtils.putBoolean(AppConstants.Cache.SCALE_TICKET_INFAC_MODIFIED, true);
            deleteAllScaleTicketData(ScaleTicket.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改电子秤热键本地数据库数据
     *
     * @param commercialInfoScaleHotkeys
     */
    public void changeScaleHotkeyDbData(List<ScaleHotkeyCommInfo> commercialInfoScaleHotkeys) {
        try {
            cacheUtils.putBoolean(AppConstants.Cache.SCALE_KEY_INFAC_MODIFIED, true);
            deleteAllScaleHotkeyData(ScaleHotkey.class);
            SystemClock.sleep(200);
            List<ScaleHotkey> scaleHotkeys = new ArrayList<>();
            scaleHotkeys.clear();
            for (ScaleHotkeyCommInfo commercialInfoScaleHotkey : commercialInfoScaleHotkeys) {
                ScaleHotkey scaleHotkey = new ScaleHotkey();
                scaleHotkey.setIndex(commercialInfoScaleHotkey.getIndex());
                scaleHotkey.setPLU1(commercialInfoScaleHotkey.getPLU1());
                scaleHotkey.setPLU2(commercialInfoScaleHotkey.getPLU2());
                scaleHotkeys.add(scaleHotkey);
            }
            insertDatas(scaleHotkeys);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改电子秤商品本地数据库数据
     *
     * @param commercialInfoScaleGoods
     */
    public void changeScaleGoodsDbData(List<com.zhumei.baselib.bean.commercial_info.ScaleGoods> commercialInfoScaleGoods) {
        try {
            cacheUtils.putBoolean(AppConstants.Cache.SCALE_GOODS_INFAC_MODIFIED, true);
            deleteAllScaleGoodsData(ScaleGoods.class);
            SystemClock.sleep(200);
            List<ScaleGoods> scaleGoods = new ArrayList<>();
            scaleGoods.clear();
            for (com.zhumei.baselib.bean.commercial_info.ScaleGoods commercialInfoScaleGood : commercialInfoScaleGoods) {
                ScaleGoods scaleGood = new ScaleGoods();
                scaleGood.setName(commercialInfoScaleGood.getName());
                scaleGood.setUnit(commercialInfoScaleGood.getUnit());
                scaleGood.setPrice(commercialInfoScaleGood.getPrice());
                scaleGood.setMemPrice(commercialInfoScaleGood.getMemPrice());
                scaleGood.setPluType(commercialInfoScaleGood.getPluType());
                scaleGood.setMinPrice(commercialInfoScaleGood.getMinPrice());
                scaleGood.setDiscount(commercialInfoScaleGood.getDiscount());
                scaleGood.setChangePrice(commercialInfoScaleGood.getChangePrice());
                scaleGood.setPLU(commercialInfoScaleGood.getPLU());
                scaleGood.setSelfCode(commercialInfoScaleGood.getSelfCode());
//                insertData(scaleGood);
                scaleGoods.add(scaleGood);
            }
            SystemClock.sleep(300);
            insertGoods(scaleGoods);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改电子秤小票本地数据库数据
     *
     * @param commercialInfoScaleTickets
     */
    public void changeScaleTicketDbData(List<com.zhumei.baselib.bean.commercial_info.ScaleTicket> commercialInfoScaleTickets) {
        try {
            cacheUtils.putBoolean(AppConstants.Cache.SCALE_TICKET_INFAC_MODIFIED, true);
            deleteAllScaleTicketData(ScaleTicket.class);
            for (com.zhumei.baselib.bean.commercial_info.ScaleTicket commercialInfoScaleTicket : commercialInfoScaleTickets) {
                ScaleTicket scaleTicket = new ScaleTicket();
                scaleTicket.setTicFlg(commercialInfoScaleTicket.getTicFlg());
                scaleTicket.setAliagnFlg(commercialInfoScaleTicket.getAliagnFlg());
                scaleTicket.setPrtFlg(commercialInfoScaleTicket.getPrtFlg());
                scaleTicket.setPrtData(commercialInfoScaleTicket.getPrtData());
                insertData(scaleTicket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
