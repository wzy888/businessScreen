package com.zhumei.baselib.bean;

import java.math.BigDecimal;

public class AriUtil {

    // 除法运算默认精度
    private static final int DEF_DIV_SCALE = 10;

    private AriUtil() {

    }

    /**
     * 精确加法
     */
    public static double add(double value1, double value2) {
        BigDecimal b1 = BigDecimal.valueOf(value1);
        BigDecimal b2 = BigDecimal.valueOf(value2);
        return b1.add(b2).doubleValue();
    }

    /**
     * 精确减法
     */
    public static double sub(double value1, double value2) {
        BigDecimal b1 = BigDecimal.valueOf(value1);
        BigDecimal b2 = BigDecimal.valueOf(value2);
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 精确乘法
     */
    public static double mul(double value1, double value2) {
        BigDecimal b1 = BigDecimal.valueOf(value1);
        BigDecimal b2 = BigDecimal.valueOf(value2);
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 精确除法 使用默认精度
     */
    public static double div(double value1, double value2) throws IllegalAccessException {
        return div(value1, value2, DEF_DIV_SCALE);
    }

    /**
     * 精确除法
     * @param scale 精度
     */
    public static double div(double value1, double value2, int scale) throws IllegalAccessException {
        if(scale < 0) {
            throw new IllegalAccessException("精确度不能小于0");
        }
        BigDecimal b1 = BigDecimal.valueOf(value1);
        BigDecimal b2 = BigDecimal.valueOf(value2);
        // return b1.divide(b2, scale).doubleValue();
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 四舍五入
     * @param scale 小数点后保留几位
     */
    public static double round(double v, int scale) throws IllegalAccessException {
        return div(v, 1, scale);
    }


    public static String round2String(double v) {
        return  String.valueOf(v);
    }



    /**
     * 提供精确的小数位四舍五入处理
     *
     * @param v
     * 需要四舍五入的数字
     * @param scale
     * 小数点后保留几位
     * @param round_mode
     * 指定的舍入模式
     * @return 四舍五入后的结果，以字符串格式返回
     */
    public static String round(String v, int scale, int round_mode) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(v);
        return b.setScale(scale, round_mode).toString();
    }



    public static String round(String v, int scale) {
        return round(v, scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 比较大小
     */
    public static boolean equalTo(BigDecimal b1, BigDecimal b2) {
        if(b1 == null || b2 == null) {
            return false;
        }
        return 0 == b1.compareTo(b2);
    }

    public static String sub (String totalHours, String usedHours) {


        // 总共课时
        BigDecimal stringFir = new BigDecimal(String.valueOf (totalHours));
        // 可用课时
        BigDecimal stringSec = new BigDecimal(String.valueOf (usedHours));

        BigDecimal subtractVal = stringFir.subtract (stringSec);
        // 剩余课时
//		String sub = String.format ("%.2f", subtractVal);
        String sub = String.format ("%s", subtractVal);
        return sub;
    }
}
