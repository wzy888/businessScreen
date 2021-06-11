package com.zhumei.baselib.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;


import com.zhumei.baselib.BaseHelper;
import com.zhumei.baselib.BuildConfig;
import com.zhumei.baselib.MyBaseApplication;
import com.zhumei.baselib.config.Constant;
import com.zhumei.baselib.config.ConstantApi;
import com.zhumei.baselib.utils.useing.event.ThreadManager;
import com.zhumei.baselib.utils.useing.software.LogUtils;
import com.zhumei.lib_network.okhttp.CallBackUtil;
import com.zhumei.lib_network.okhttp.OkhttpUtil;

import java.net.Socket;

import okhttp3.Call;

public class NetUtil {

    /**
     * 没有连接网络
     */
    public static final int NETWORK_NONE = -1;
    /**
     * 移动网络
     */
    public static final int NETWORK_MOBILE = 0;
    /**
     * 无线网络
     */
    public static final int NETWORK_WIFI = 1;
    private static int NETWORK_CONNECTED = 0;

    public static int getNetWorkState() {

        // 得到连接管理器对象
        ConnectivityManager connectivityManager = (ConnectivityManager) MyBaseApplication.getMyApplication()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_WIFI)) {
                return NETWORK_WIFI;
            } else if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_MOBILE)) {
                return NETWORK_MOBILE;
            }
        } else {
            return NETWORK_NONE;
        }
        return NETWORK_NONE;
    }


    /**
     * 判断当前网络是否可用(通用方法)
     * 耗时12秒
     *
     * @return boolean
     * 链接：https://juejin.im/post/6844903965964894216
     */
    static    boolean isEnable = false;


    /**
     * 耗时操作.
     */
    public static void isNetPingBd(final NetCallBack callBack, final String realIp) {

        ThreadManager.executeSingleTask(new Runnable() {
            @Override
            public void run() {
                boolean isEnable;
                Runtime runtime = Runtime.getRuntime();
                try {
//                    180.101.49.12
//                    Process process = runtime.exec("ping -c 3 www.baidu.com");
                    String ping = String.format("ping -c 1 %s", realIp);
                    LogUtils.E("isNetPingBd == ",ping);
                    Process process = runtime.exec(ping);

                    int ret = process.waitFor();
                    isEnable = ret == NETWORK_CONNECTED;

                } catch (Exception e) {
                    e.printStackTrace();
                    isEnable = false;
                }

                if (callBack != null) {
                    callBack.isConnect(isEnable);
                }

            }
        });

    }


    public interface NetCallBack {
        void isConnect(boolean connect);
    }

    /**
     * 网络 是否连接 wifi 移动
     */
    public static boolean isNetEnabled() {
//        return    getNetWorkState() != NETWORK_NONE ;


        ThreadManager.executeSingleTask(new Runnable() {
            @Override
            public void run() {
                Runtime runtime = Runtime.getRuntime();
                try {
//                    180.101.49.12
//                    Process process = runtime.exec("ping -c 3 www.baidu.com");
                    String ping = String.format("ping -c 1 %s", Constant.PING_BAIDU_IP);
                    LogUtils.E("isNetPingBd == ",ping);
                    Process process = runtime.exec(ping);

                    int ret = process.waitFor();
                    isEnable = ret == NETWORK_CONNECTED;

                } catch (Exception e) {
                    e.printStackTrace();
                    isEnable = false;
                }


            }
        });
        return isEnable;

    }








    public interface  InternetStatus{

        void getStatus(boolean flag);
    }



}
