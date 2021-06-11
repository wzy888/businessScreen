package com.zhumei.baselib.utils.useing.software;


import android.annotation.SuppressLint;


import com.zhumei.baselib.app.AppConstants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符相关的工具类
 */
public class StringUtils {

    /**
     * 提取字符串中的 数字 带小数点 ，没有就返回""
     *
     * @param money
     * @return
     */
    public static String getMoney(String money) {
        Pattern pattern = Pattern.compile("(\\d+\\.\\d+)");
        Matcher m = pattern.matcher(money);
        if (m.find()) {
            money = m.group(1) == null ? "" : m.group(1);
        } else {
            pattern = Pattern.compile("(\\d+)");
            m = pattern.matcher(money);
            if (m.find()) {
                money = m.group(1) == null ? "" : m.group(1);
            } else {
                money = "";
            }
        }

        return money;
    }

    /**
     * 在字符串中判断某个字符出现的次数
     *
     * @param all
     * @param regex
     * @return
     */
    public static int appearCount(String all, String regex) {
        int allLen = all.length();
        if (all.contains(regex)) {
            return allLen - all.replaceAll(regex, "").length();
        } else {
            return 0;
        }
    }

    /**
     * 判断字符串中的中文标点格式和汉字的个数
     */
    @SuppressLint("NewApi")
    public static int countChinese(String a) {
        char[] c = a.toCharArray();
        int count = 0;
        for (char d : c) {
//            Character.UnicodeBlock ub = Character.UnicodeBlock.of(d);
//            if (ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
//                    || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
//                    || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
//                    || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_FORMS
//                    || ub == Character.UnicodeBlock.VERTICAL_FORMS
//                    || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
//                    || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
//                    || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
//                    || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_C
//                    || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_D
//                    || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
//                    || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT) {
//                count++;
//            }
            if ((d >= 0x4e00) && (d <= 0x9fbb)) {
                count++;
            }
        }
        return count;
    }

    public final static boolean isNumeric(String s) {

        if (s != null && !"".equals(s.trim())) {
            return s.matches("^[0-9]*$");
        }

        return false;
    }

    /**
     * 判断字符串是否为有效字符串
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return null != str && !str.isEmpty() && !AppConstants.CommonStr.NULL_STR.equals(str)
                && !AppConstants.CommonStr.MIDDLE_BRACKETS_QUOTATION_MARKS.equals(str) && !AppConstants.CommonStr.MIDDLE_BRACKETS.equals(str);
    }

    public static boolean isNotEmpty2(String str) {
        return null != str && !str.isEmpty() && !AppConstants.CommonStr.NULL_STR.equals(str);
    }

    public static boolean isEmpty(String str) {
        return null == str || str.isEmpty() || AppConstants.CommonStr.NULL_STR.equals(str);
    }


    //截取数字
    public static String getNumbers(String content) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }

    // 截取非数字
    public static String splitNotNumber(String content) {
        Pattern pattern = Pattern.compile("\\D+");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }

    // 判断一个字符串是否含有数字
    public static boolean HasDigit(String content) {
        boolean flag = false;
        Pattern p = Pattern.compile(".*\\d+.*");
        Matcher m = p.matcher(content);
        if (m.matches()) {
            flag = true;
        }
        return flag;
    }

    public static boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     *
     * 符串是否为数字或小数*/
    public static boolean isDecimal(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        if(str.indexOf(".")>0){//判断是否有小数点
            if(str.indexOf(".")==str.lastIndexOf(".") && str.split("\\.").length==2){ //判断是否只有一个小数点
                return pattern.matcher(str.replace(".","")).matches();
            }else {
                return false;
            }
        }else {
            return pattern.matcher(str).matches();
        }
    }
}