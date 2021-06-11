package com.zhumei.baselib.utils;

import android.text.TextUtils;


import com.zhumei.baselib.app.AppConstants;

import java.util.List;

public class ListUtils {

    public ListUtils() {
    }

    public String listToString(List<String> list) {
        try {
            StringBuilder sbNewsTitle = new StringBuilder();
//            String s = AppConstants.CommonStr.EMPTY_STR;

            if (list == null) {
                return "";
            }
            if (list.size() == 0) {
                return "";
            }


            for (int i = 0; i < list.size(); i++) {
                String news = list.get(i);
//                T t = list.get(i);
//                if (t instanceof CommercialInfoRealTimeDiscountNewsBean) {
//                    s = ((CommercialInfoRealTimeDiscountNewsBean) t).getPostTitle() + "      ";
//                }
//                if (t instanceof CommercialInfoRealTimeWelcomeNewsBean) {
//                    s = ((CommercialInfoRealTimeWelcomeNewsBean) t).getPostTitle() + "      ";
//                }
//                if (t instanceof CommercialInfoCxBean.MarketNoticeDataBean.ListBean) {
//                    s = ((CommercialInfoCxBean.MarketNoticeDataBean.ListBean) t).getTitle() + "      ";
//                }

                sbNewsTitle.append(news).append("      ");
            }
            String newsTitle = sbNewsTitle.toString();
            newsTitle = fillEntireLine(newsTitle);
            return newsTitle;


        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public String fillEntireLine(String content) {
        try {

            if (!TextUtils.isEmpty(content) && !AppConstants.CommonStr.NULL_STR.equals(content)) {
                // 强行填满一行
                return content + "                                                                                                      ";
            } else {
                return AppConstants.CommonStr.EMPTY_STR;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return AppConstants.CommonStr.EMPTY_STR;
        }
    }

}
