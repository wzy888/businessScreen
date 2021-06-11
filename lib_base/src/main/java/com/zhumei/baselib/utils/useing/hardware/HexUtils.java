package com.zhumei.baselib.utils.useing.hardware;

public class HexUtils {

    /**
     * byte[] to hex string
     * @param bytes
     * @return
     */
    public static String bytesToHex(byte[] bytes) {
        if(bytes != null){
            StringBuilder buf = new StringBuilder(bytes.length * 2);
            // 使用String的format方法进行转换
            for(byte b : bytes) {
                buf.append(String.format("%02x", b & 0xff));
            }
            return buf.toString();
        }else{
            return "";
        }
    }

    /**
     * 将字符串转化为16进制的字节
     * @param message 需要被转换的字符
     * @return
     */
    public static byte[] getHexBytes(String message) {
        int len = message.length() / 2;
        char[] chars = message.toCharArray();
        String[] hexStr = new String[len];
        byte[] bytes = new byte[len];
        for (int i = 0, j = 0; j < len; i += 2, j++) {
            hexStr[j] = "" + chars[i] + chars[i + 1];
            bytes[j] = (byte) Integer.parseInt(hexStr[j], 16);
        }
        return bytes;
    }
}
