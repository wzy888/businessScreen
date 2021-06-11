package com.zhumei.baselib.dao;

import android.content.Context;

import com.zhumei.baselib.app.AppConstants;
//import com.zhumei.baselib.bean.scale.DaoMaster;
//import com.zhumei.baselib.bean.scale.DaoSession;

import org.greenrobot.greendao.query.QueryBuilder;

public class ScaleDaoManager {

    /**
     * 多线程访问
     */
    private volatile  ScaleDaoManager mScaleDaoManager;
    private DaoMaster mDaoMaster;
    private  DaoSession mDaoSession;
    private Context mContext;
    private MyGreenDaoOpenHelper mHelper;

    /**
     * 使用单例模式创建DaoManager
     *
     * @return
     */
    public  ScaleDaoManager getInstance() {
        ScaleDaoManager instance = null;
        if (mScaleDaoManager == null) {
            synchronized (ScaleDaoManager.class) {
                if (instance == null) {
                    instance = new ScaleDaoManager();
                    mScaleDaoManager = instance;
                }
            }
        }
        return mScaleDaoManager;
    }

    /**
     * 初始化Context
     *
     * @param context
     */
    public void initContext(Context context) {
        this.mContext = context;
    }

    /**
     * 判断DaoMaster是否存在 不存在则创建
     *
     * @return
     */
    public DaoMaster getDaoMaster() {
        if (mDaoMaster == null) {
            // 创建数据库帮助类 给数据库命名 一个数据库DaoManager只有一个 里面可能会多多个数据库表
            mHelper = new MyGreenDaoOpenHelper(mContext, AppConstants.DefaultSetting.SCALE_DB_NAME, null);
            mDaoMaster = new DaoMaster(mHelper.getWritableDatabase());
        }
        return mDaoMaster;
    }

    /**
     * 判断DaoSession是否存在 不存在再判断DaoMaster是否存在 不存在则创建 由DaoMaster获取DaoSession
     *
     * @return
     */
    public DaoSession getDaoSession() {
        if (mDaoSession == null) {
            if (mDaoMaster == null) {
                mDaoMaster = getDaoMaster();
            }
            mDaoSession = mDaoMaster.newSession();
        }
        return mDaoSession;
    }

    /**
     * 设置数据库的调试模式是否打开
     *
     * @param debugFlag
     */
    public void setDebug(boolean debugFlag) {
        QueryBuilder.LOG_SQL = debugFlag;
        QueryBuilder.LOG_VALUES = debugFlag;
    }

    public void closeDataBase() {
        closeHelper();
        closeDaoSession();
    }

    /**
     * helper关掉之后 DaoMaster就关掉了
     */
    public void closeHelper() {
        if (mHelper != null) {
            mHelper.close();
            mHelper = null;
        }
    }

    public void closeDaoSession() {
        if (mDaoSession != null) {
            mDaoSession.clear();
            mDaoSession = null;
        }
    }
}
