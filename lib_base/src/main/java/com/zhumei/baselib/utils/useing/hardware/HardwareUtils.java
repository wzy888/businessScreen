package com.zhumei.baselib.utils.useing.hardware;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.WindowManager;


import com.zhumei.baselib.app.AppConstants;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;

public class HardwareUtils {

    /**
     * 获取网口的Mac地址
     */
    public static String getEthernetMacAddr(){
        String procVersionStr;
        try {


           /**
            *  该方法在Android 7.0开始也行不通了，
            *  执行上面的代码会抛出java.io.FileNotFoundException:
            *  /sys/class/net/wlan0/address (Permission denied)异常
            作者：快乐丸
            链接：https://www.jianshu.com/p/e8b6cafa91d5
            */

            BufferedReader reader = new BufferedReader(new FileReader("/sys/class/net/eth0/address"), 256);
            try {
                procVersionStr = reader.readLine();
            } finally {
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return AppConstants.DefaultSetting.SET_MAC;
        }
        return procVersionStr;
    }

    /**
     * 获取WIFI的Mac地址
     */
    public static String getWIFIMacAddr() throws SocketException {
        String address = null;
        // 把当前机器上的访问网络接口的存入 Enumeration集合中
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface netWork = interfaces.nextElement();
            // 如果存在硬件地址并可以使用给定的当前权限访问，则返回该硬件地址（通常是 MAC）。
            byte[] by = netWork.getHardwareAddress();
            if (by == null || by.length == 0) {
                continue;
            }
            StringBuilder builder = new StringBuilder();
            for (byte b : by) {
                builder.append(String.format("%02X:", b));
            }
            if (builder.length() > 0) {
                builder.deleteCharAt(builder.length() - 1);
            }
            String mac = builder.toString();
            // 从路由器上在线设备的MAC地址列表，可以印证设备Wifi的 name 是 wlan0
            if ("wlan0".equals(netWork.getName())) {
                address = mac;
            }
        }
        if(address == null){
            address = AppConstants.DefaultSetting.SET_MAC;
        }
        return address;
    }

    /**
     * 获取硬件id: androidID + Serial Number 然后进行MD5
     * @param context
     * @return
     */
    public static String getHardwareId(Context context){
        @SuppressLint("HardwareIds")
        // 恢复出厂设置后此id会重置
                String androidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        @SuppressLint("HardwareIds")
        String id = androidID + Build.SERIAL;
        try {
            return toMD5(id);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return id;
        }
    }

    private static String toMD5(String text) throws NoSuchAlgorithmException {
        // 获取摘要器 MessageDigest
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        //通过摘要器对字符串的二进制字节数组进行hash计算
        byte[] digest = messageDigest.digest(text.getBytes());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < digest.length; i++) {
            //循环每个字符 将计算结果转化为正整数;
            int digestInt = digest[i] & 0xff;
            //将10进制转化为较短的16进制
            String hexString = Integer.toHexString(digestInt);
            //转化结果如果是个位数会省略0,因此判断并补0
            if (hexString.length() < 2) {
                sb.append(0);
            }
            //将循环结果添加到缓冲区
            sb.append(hexString);
        }
        //返回整个结果
        return sb.toString();
    }

    /**
     * 获取系统参数
     * @param key
     * @return
     */
    @SuppressLint("PrivateApi")
    public static String getPropStr(String key) {
        Class<?> mClassType = null;
        Method mGetMethod = null;
        String value = "";
        try {
            if (mClassType == null) {
                mClassType = Class.forName("android.os.SystemProperties");
                mGetMethod = mClassType.getDeclaredMethod("get", String.class);
                value = (String) mGetMethod.invoke(mClassType, key);
            }
        } catch (Exception e) {
            e.printStackTrace();
            value = "";
        }
        return value;
    }

    /**
     * 获取屏幕分辨率
     * @param context
     * @return
     */
    public static Point getScreenResolution(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        Point size = new Point();
        if (wm != null) {
            // 获取的是不含虚拟键盘的分辨率 虚拟键盘也占了有分辨率
            wm.getDefaultDisplay().getMetrics(dm);
            size.x = dm.widthPixels;
            size.y = dm.heightPixels;
        }
        return size;
    }
}
