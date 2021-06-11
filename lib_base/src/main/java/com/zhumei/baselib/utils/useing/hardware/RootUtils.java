package com.zhumei.baselib.utils.useing.hardware;


import com.zhumei.baselib.utils.useing.software.LogUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class RootUtils {

    public static final String TAG = "RootUtils";

    // 此方法工作有误
    @Deprecated
    public static boolean isRooted() {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("su");
            OutputStream outputStream = process.getOutputStream();
            InputStream inputStream = process.getInputStream();
            outputStream.write("id\n".getBytes("utf-8"));
//            outputStream.flush();
            outputStream.write("exit\n".getBytes());
//            outputStream.flush();
            process.waitFor();
            InputStreamReader is = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(is);
            String s = bufferedReader.readLine();
            if(s !=null ){
                if (s.contains("uid=0")) {
                    return true;
                }
            }
            is.close();
            outputStream.close();
            bufferedReader.close();
        } catch (IOException e) {
            LogUtils.E(TAG, "没有root权限");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
        return false;
    }

    public static boolean checkRooted() {
        boolean result = false;
        try {
            result = new File("/system/bin/su").exists() || new File("/system/xbin/su").exists();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
