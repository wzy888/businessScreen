package com.zhumei.update;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.blankj.utilcode.util.LogUtils;
import com.hikvision.dmb.util.InfoUtilApi;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class UpdateService extends Service {
    private static final String TAG = "UpdateService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                final String url = extras.getString("url");
                String versionName = extras.getString("versionName");
                LogUtils.e(TAG, "update url:" + url + "----update versionName:" + versionName);
                File zhumeiShpFile = new File(AppConstants.DefaultSetting.SET_ZHUMEI_COMMERCIAL_SCREEN_PATH);
                if (!zhumeiShpFile.exists()) {
                    zhumeiShpFile.mkdir();
                } else {
                    LogUtils.e(TAG, "the zhumei folder already exists!");
                }
                final String downloadDir = AppConstants.DefaultSetting.SET_ZHUMEI_COMMERCIAL_SCREEN_PATH + File.separator +
                        AppConstants.SHP.PRODUCT_NAME.toLowerCase() + "_" + versionName + "_" +
                        new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(System.currentTimeMillis())) + ".apk";
                LogUtils.e(TAG, "update downloadDir:" + downloadDir);
                // ???????????????????????????
                int randomUpdate = random(AppConstants.DefaultSetting.SET_RANDOM_UPDATE_CYCLE);
                LogUtils.e(TAG, (randomUpdate / 60 / 1000) + " minutes left for update!");

                Timer timer = new Timer();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        LogUtils.e(TAG, "It is time to upgrade!");
                        softwareUpdate(url, downloadDir);
                    }
                };
                timer.schedule(task, randomUpdate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * ??????0???10??????????????????
     *
     * @param endMs
     * @return
     */
    private int random(int endMs) {
        if (endMs > 0) {
            return new Random().nextInt(endMs + 1);
        } else {
            return 0;
        }
    }

    /**
     * ????????????
     *
     * @param url
     * @param downloadDir
     */
    private void softwareUpdate(String url, final String downloadDir) {
        FileDownloader.getImpl().create(url).setPath(downloadDir)
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, final int soFarBytes, final int totalBytes) {
                        LogUtils.e(TAG, "soFarBytes:" + soFarBytes + ",totalBytes:" + totalBytes);
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        // ??????
                        normalInstall(new File(downloadDir), downloadDir);
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                    }
                }).start();
    }

    /**
     * ????????????
     */
    public void normalInstall(File downloadFile, String downloadDir) {
        if (AppConstants.BoradType.BORAD_TYPE.equals(AppConstants.BoradType.YANCHENG_PROJECT)) {
            // ???????????????????????????????????????????????????
            Intent cmdIntent = new Intent("com.zhsd.setting.syscmd");
            cmdIntent.putExtra("cmd", "appinstall");
            cmdIntent.putExtra("parm", downloadDir);
            sendBroadcast(cmdIntent);
        } else if (AppConstants.BoradType.BORAD_TYPE.equals(AppConstants.BoradType.MStar_TV)) {
            // ???????????????????????????????????????????????????
            Intent cmdIntent = new Intent();
            cmdIntent.setAction("com.android.lango.installapp");
            cmdIntent.putExtra("apppath", downloadDir);
            sendBroadcast(cmdIntent);
        } else {
            // ???????????????jar????????????
            if (InfoUtilApi.isAvailable()) {
                int i = InfoUtilApi.silentInstallation(downloadDir);
                // 0????????????  -1????????????
                LogUtils.e("hkws normalInstall:" + i);
            } else {
                Intent intent = new Intent();

                // android 7.0??????
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    Uri contentUri = FileProvider.getUriForFile(this, "com.zhumei.commercialscreen.fileProvider", downloadFile);
                    intent.setDataAndType(contentUri, "application/vnd.android.package-archive");

                    Context context = UpdateHelper.getInstance().getContext();
                    // ?????????????????? intent ?????????????????????????????????????????????????????????????????? setDataAndType ????????????
                    List<ResolveInfo> resolveLists = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                    // ??????????????????
                    for (ResolveInfo resolveInfo : resolveLists) {
                        String packageName = resolveInfo.activityInfo.packageName;
                        context.grantUriPermission(packageName, contentUri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    }
                } else {
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setDataAndType(Uri.fromFile(downloadFile), "application/vnd.android.package-archive");
                }
                intent.setAction(Intent.ACTION_VIEW);
                startActivity(intent);
            }
        }
    }

    public static void installSlient(File apk) {

        String cmd = "pm install -r " + apk.getPath();
        Process process = null;
        DataOutputStream os = null;
        BufferedReader successResult = null;
        BufferedReader errorResult = null;
        StringBuilder successMsg = null;
        StringBuilder errorMsg = null;
        try {
            //??????????????????root??????
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.write(cmd.getBytes());
            os.writeBytes("\n");
            os.writeBytes("exit\n");
            os.flush();

            //????????????
            process.waitFor();

            //??????????????????
            successMsg = new StringBuilder();
            errorMsg = new StringBuilder();

            successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
            errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            String s;
            while ((s = successResult.readLine()) != null) {
                successMsg.append(s);
            }

            while ((s = errorResult.readLine()) != null) {
                errorMsg.append(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (process != null) {
                    process.destroy();
                }
                if (successResult != null) {
                    successResult.close();
                }
                if (errorResult != null) {
                    errorResult.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Log.e(TAG, "=================== successMsg: " + successMsg.toString() + ", errorMsg: " + errorMsg.toString());

        //????????????
        if ("Success".equals(successMsg.toString())) {

            Log.e(TAG, "======= apk install success");

        }

    }

}
