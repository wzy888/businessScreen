package com.zhumei.baselib.utils.useing.software;


import com.zhumei.baselib.app.AppConstants;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class MathUtils {

    /**
     * 保留三位小数
     *
     * @param data
     * @return
     */
    public static String keepThreeDecimalDigits(double data) {
        String format = "0.000";
        try {

            format = new DecimalFormat("#,##0.000").format(data);
        } catch (Exception e) {
            e.printStackTrace();
            format = "0.000";

        }
        return format;
    }

    /**
     * 保留两位小数
     *
     * @param data
     * @return
     */
    public static String keepTwoDecimalDigits(double data) {
        String format = "0.00";
        try {
            format = new DecimalFormat("#,##0.00").format(data);
            if (format.contains(AppConstants.ScalePay.NEGATIVE_SIGN)) {
                format = AppConstants.ScalePay.KEEP_TWO_DECIMAL_DIGITS_ZERO;
            }
        } catch (Exception e) {
            e.printStackTrace();
            format = "0.00";
        }

        return format;
    }

    /**
     * 保留两位小数并四舍五入
     *
     * @param data
     * @return
     */
    public static String keepTwoDecimalDigitsAndFourToFive(double data) {
        // 之前使用BigDecimal.ROUND_HALF_UP 有点问题 0.825无法四舍五入为0.83 目前可以
        // RoundingMode.UP
        String format="0.00";
        try {
        format = new DecimalFormat("0.00").format(new BigDecimal(data).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            if (format.contains(AppConstants.ScalePay.NEGATIVE_SIGN)) {
                format = AppConstants.ScalePay.KEEP_TWO_DECIMAL_DIGITS_ZERO;
            }
        }catch (Exception e){
            e.printStackTrace();
            format="0.00";
        }
        return format;
    }

    /**
     * BigDecimal类型相乘
     *
     * @param a
     * @param b
     * @return
     */
    public static double bigDecimalMultiply(double a, double b) {
        BigDecimal d1 = new BigDecimal(Double.toString(a));
        BigDecimal d2 = new BigDecimal(Double.toString(b));
        return d1.multiply(d2).doubleValue();
    }
}
