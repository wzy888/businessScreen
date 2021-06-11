package com.zhumei.baselib.utils.useing.hardware;


import com.zhumei.baselib.app.AppConstants;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {


    public static String md5Str(String str) {
        if (str == null) {
            return "";
        }
        return md5Str(str, 0);
    }

    /**
     * 计算消息摘要
     *
     * @param offset 数据偏移地址
     * @return 摘要结果(16字节)
     */
    public static String md5Str(String str, int offset) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] b = str.getBytes("UTF8");
            md5.update(b, offset, b.length);
            return byteArrayToHexString(md5.digest());
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * @param b byte[]
     * @return String
     */
    public static String byteArrayToHexString(byte[] b) {
        String result = AppConstants.CommonStr.EMPTY_STR;
        for (byte c : b) {
            result += byteToHexString(c);
        }
        return result;
    }

    private static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * 将字节转换为对应的16进制明文
     *
     * @param b byte
     * @return String
     */
    public static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }


}

