package com.zhumei.baselib.helper;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhumei.baselib.MyBaseApplication;
import com.zhumei.baselib.app.AppConstants;
import com.zhumei.baselib.module.localdata.LoginLocalData;
import com.zhumei.baselib.module.response.AutoUpdateRes;
import com.zhumei.baselib.module.response.LoginRes;
import com.zhumei.baselib.utils.useing.cache.CacheUtils;
import com.zhumei.baselib.utils.useing.software.LogUtils;
import com.zhumei.baselib.utils.useing.software.SoftwareUtils;

import com.zhumei.baselib.config.Constant;
import com.zhumei.update.UpdateService;


import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;

/**
 * Splash 页面一些逻辑处理类.
 */
public class SplashHelper {

    private static final String TAG = "SplashHelper";


    public SplashHelper() {

    }

    /**
     * 创建筑美商户屏文件夹
     */

    public void createZhumeiShpDir() {
        try {
            File zhumeiShpFile = new File(AppConstants.DefaultSetting.SET_ZHUMEI_COMMERCIAL_SCREEN_PATH);
            if (!zhumeiShpFile.exists()) {
                boolean mkdir = zhumeiShpFile.mkdir();
                if (mkdir) {
                    LogUtils.e(TAG, "the success of the zhumei folder creation!");
                } else {
                    LogUtils.e(TAG, "zhumei folder creation failure!");
                }
            } else {
                LogUtils.e(TAG, "the zhumei folder already exists!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 静默安装逻辑
     *
     * @param srcFile
     * @param desFile
     */
    private void slientInstall(String srcFile, String desFile) {
        try {
            LogUtils.e(TAG, "start to transfer HDP.apk");
            // 将assets下的文件转移到可读写文件目录下
            createFile(srcFile, desFile);
            File file = new File(AppConstants.DefaultSetting.SET_ZHUMEI_COMMERCIAL_SCREEN_PATH + File.separator + desFile);
            boolean result = false;
            Process process;
            OutputStream out;
            if (file.exists()) {
                System.out.println(file.getPath());
                try {
                    process = Runtime.getRuntime().exec("su");
                    out = process.getOutputStream();
                    DataOutputStream dataOutputStream = new DataOutputStream(out);
                    // 获取文件所有权限
                    dataOutputStream.writeBytes("chmod 777 " + file.getPath() + "\n");
                    // 进行静默安装命令
                    dataOutputStream.writeBytes("LD_LIBRARY_PATH=/vendor/lib:/system/lib pm install -r " + file.getPath());
                    // 提交命令
                    dataOutputStream.flush();
                    // 关闭流操作
                    dataOutputStream.close();
                    out.close();
                    int value = process.waitFor();
                    // 代表成功
                    if (value == 0) {
                        LogUtils.e(TAG, "HDP install success!");
                        result = true;
                        // 失败
                    } else if (value == 1) {
                        LogUtils.e(TAG, "HDP install fail!");
                        result = false;
                    } else {
                        // 未知情况
                        LogUtils.e(TAG, "HDP unknown situation！");
                        result = false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (!result) {
                    LogUtils.e(TAG, "root permissions fail, and HDP will be installed in general!");
                    // 不能静默安装就不安装了
//                    Intent intent = new Intent();
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.setAction(Intent.ACTION_VIEW);
//                    intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
//                    mContext.startActivity(intent);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 进行资源的转移 将assets下的某文件转移到可读写文件目录下并重命名
     *
     * @param srcFile
     * @param desFile
     */
    private void createFile(String srcFile, String desFile) {
        try {
            InputStream is = null;
            FileOutputStream fos = null;
            try {
                is = MyBaseApplication.getMyApplication().getAssets().open(srcFile);
                File zhumeiShpFile = new File(AppConstants.DefaultSetting.SET_ZHUMEI_COMMERCIAL_SCREEN_PATH);
                if (!zhumeiShpFile.exists()) {
                    zhumeiShpFile.mkdir();
                } else {
                    LogUtils.e(TAG, "the zhumei folder already exists!");
                }
                File file = new File(AppConstants.DefaultSetting.SET_ZHUMEI_COMMERCIAL_SCREEN_PATH + File.separator + desFile);
                file.createNewFile();
                fos = new FileOutputStream(file);
                byte[] temp = new byte[1024 * 8];
                int i = 0;
                while ((i = is.read(temp)) > 0) {
                    fos.write(temp, 0, i);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            LogUtils.e(TAG, "resource transfer is completed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    public void dealAutoUpdate(Activity mContext, AutoUpdateRes autoUpdateRes, String marketId) {
        try {

            String url = autoUpdateRes.getAddress();
            String versionCode = autoUpdateRes.getVersion_code();
            String packageName = autoUpdateRes.getPackage_name();
//            String marketId = autoUpdateRes.getMarketId();
            LogUtils.e(TAG, "versionCode:" + versionCode);
            if (TextUtils.isEmpty(packageName) || AppConstants.CommonStr.NULL_STR.equals(packageName)) {
                LogUtils.e(TAG, "暂未获取到packAgeName ！");
                return;
            }

            //如果包名不相同，不安装
            if (!packageName.equals(mContext.getPackageName())) {
                LogUtils.e(TAG, "the name of the package is wrong! Don't update it!");
                return;
            }
            BigDecimal code = new BigDecimal(versionCode);
            // 如果包名相同 市场ID的不同

            //是否 能升级更新.
            boolean isUpdate = SoftwareUtils.getAppVersionCode(MyBaseApplication.getMyApplication()) < code.intValue();

            LoginLocalData entity = CacheUtils.getEntity(Constant.LOGIN_LOCAL, LoginLocalData.class);
            if (entity == null) {
                LogUtils.e(TAG, "LoginLocalData is Null !");
                return;
            }
            LoginRes loginRes = entity.getLoginRes();
            String market_id = loginRes.getMarket_id();
            // 如果市场id相同
            if (marketId.equals(market_id) && isUpdate) {

                LogUtils.e(TAG, "the server side package name is the same as the currently installed software package name!");
                // 如果当前版本小于服务器版本 静默升级

                LogUtils.e(TAG, "the version code of the software placed on the server is larger than that of the " +
                        "currently installed software version code. It needs updating!");
                Intent intent = new Intent(mContext, UpdateService.class);
                Bundle bundle = new Bundle();
                bundle.putString("url", url);
                bundle.putString("versionName", autoUpdateRes.getPackage_name());
                intent.putExtras(bundle);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    mContext.startForegroundService(intent);
                } else {
                    mContext.startService(intent);

                }
            } else {
                LogUtils.e(TAG, "the version code of the software placed on the server is smaller than that of the " +
                        "currently installed software version code. Don't update it!");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    public void installApk(String packageName, final String srcFile, final String desFile) {
//        try {
//            PackageInfo mPackageInfo;
//            try {
//                mPackageInfo = MyApplication.getMyApplication().getPackageManager().getPackageInfo(packageName, 0);
//            } catch (Exception e) {
//                mPackageInfo = null;
//                e.printStackTrace();
//            }
//            if (mPackageInfo == null) {
//                // 启用安装新线程
//                ThreadManager.executeSingleTask(new Runnable() {
//                    @Override
//                    public void run() {
//                        LogUtils.e(TAG, "not installed, needs to be installed!");
//                        // 静默安装逻辑
//                        slientInstall(srcFile, desFile);
//                    }
//                });
//
//
//            } else {
//                LogUtils.e(TAG, "already installed!");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


}
