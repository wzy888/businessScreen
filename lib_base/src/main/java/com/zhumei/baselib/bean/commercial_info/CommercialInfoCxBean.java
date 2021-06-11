package com.zhumei.baselib.bean.commercial_info;

import java.util.List;

public class CommercialInfoCxBean {


    /**
     * merchant_data : [{"merchant_name":"魏淑芬1","stall_number":"TW01","star_num":3,"business_img":"http://cdn.zhumei.net/data/public/default/img/photo.png"},{"merchant_name":"魏淑芬2","stall_number":"TW02","star_num":3,"business_img":"http://cdn.zhumei.net/data/public/default/img/photo.png"},{"merchant_name":"魏淑芬3","stall_number":"TW03","star_num":3,"business_img":"http://cdn.zhumei.net/data/public/default/img/photo.png"}]
     * market_title_data : [{"title_en":"market_name","title":"晨曦园农贸市场"},{"title_en":"sczdj","title":"市场指导价"},{"title_en":"sczdj_en","title":"Market guide price"},{"title_en":"tanwei","title":"摊位号"},{"title_en":"merchant_name","title":"经营者"},{"title_en":"star","title":"星级"}]
     * market_price_one_data : [{"goods_name":"金针菇","goods_price":"4.5","unit_name":"元/500"},{"goods_name":"金针菇","goods_price":"4.5","unit_name":"元/500"},{"goods_name":"金针菇","goods_price":"4.5","unit_name":"元/500"},{"goods_name":"金针菇","goods_price":"4.5","unit_name":"元/500"},{"goods_name":"金针菇","goods_price":"4.5","unit_name":"元/500"},{"goods_name":"金针菇","goods_price":"4.5","unit_name":"元/500"},{"goods_name":"金针菇","goods_price":"4.5","unit_name":"元/500"}]
     * market_price_two_data : [{"goods_name":"金针菇","goods_price":"4.5","unit_name":"元/500"},{"goods_name":"金针菇","goods_price":"4.5","unit_name":"元/500"},{"goods_name":"金针菇","goods_price":"4.5","unit_name":"元/500"},{"goods_name":"金针菇","goods_price":"4.5","unit_name":"元/500"},{"goods_name":"金针菇","goods_price":"4.5","unit_name":"元/500"},{"goods_name":"金针菇","goods_price":"4.5","unit_name":"元/500"},{"goods_name":"金针菇","goods_price":"4.5","unit_name":"元/500"}]
     * market_notice_data : {"title":"市场公告","list":[{"title":"有黑扫黑、无黑除恶、无恶治乱、无乱安居。"},{"title":"有黑扫黑、无黑除恶、无恶治乱、无乱安居。"}]}
     * get_weather_data : {"date":"周一 07月20日 (实时：25℃)","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/xiaoyu.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/xiaoyu.png","weather":"小雨","wind":"无持续风向微风","temperature":"28 ~ 25℃"}
     */

    private MarketNoticeDataBean market_notice_data;
    private GetWeatherDataBean get_weather_data;
    private List<MerchantDataBean> merchant_data;
    private List<MarketTitleDataBean> market_title_data;
    private List<MarketPriceOneDataBean> market_price_one_data;
    private List<MarketPriceTwoDataBean> market_price_two_data;

    public MarketNoticeDataBean getMarket_notice_data() {
        return market_notice_data;
    }

    public void setMarket_notice_data(MarketNoticeDataBean market_notice_data) {
        this.market_notice_data = market_notice_data;
    }

    public GetWeatherDataBean getGet_weather_data() {
        return get_weather_data;
    }

    public void setGet_weather_data(GetWeatherDataBean get_weather_data) {
        this.get_weather_data = get_weather_data;
    }

    public List<MerchantDataBean> getMerchant_data() {
        return merchant_data;
    }

    public void setMerchant_data(List<MerchantDataBean> merchant_data) {
        this.merchant_data = merchant_data;
    }

    public List<MarketTitleDataBean> getMarket_title_data() {
        return market_title_data;
    }

    public void setMarket_title_data(List<MarketTitleDataBean> market_title_data) {
        this.market_title_data = market_title_data;
    }

    public List<MarketPriceOneDataBean> getMarket_price_one_data() {
        return market_price_one_data;
    }

    public void setMarket_price_one_data(List<MarketPriceOneDataBean> market_price_one_data) {
        this.market_price_one_data = market_price_one_data;
    }

    public List<MarketPriceTwoDataBean> getMarket_price_two_data() {
        return market_price_two_data;
    }

    public void setMarket_price_two_data(List<MarketPriceTwoDataBean> market_price_two_data) {
        this.market_price_two_data = market_price_two_data;
    }

    public static class MarketNoticeDataBean {
        /**
         * title : 市场公告
         * list : [{"title":"有黑扫黑、无黑除恶、无恶治乱、无乱安居。"},{"title":"有黑扫黑、无黑除恶、无恶治乱、无乱安居。"}]
         */

        private String title;
        private List<ListBean> list;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * title : 有黑扫黑、无黑除恶、无恶治乱、无乱安居。
             */

            private String title;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }

    public static class GetWeatherDataBean {
        /**
         * date : 周一 07月20日 (实时：25℃)
         * dayPictureUrl : http://api.map.baidu.com/images/weather/day/xiaoyu.png
         * nightPictureUrl : http://api.map.baidu.com/images/weather/night/xiaoyu.png
         * weather : 小雨
         * wind : 无持续风向微风
         * temperature : 28 ~ 25℃
         */

        private String date;
        private String dayPictureUrl;
        private String nightPictureUrl;
        private String weather;
        private String wind;
        private String temperature;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDayPictureUrl() {
            return dayPictureUrl;
        }

        public void setDayPictureUrl(String dayPictureUrl) {
            this.dayPictureUrl = dayPictureUrl;
        }

        public String getNightPictureUrl() {
            return nightPictureUrl;
        }

        public void setNightPictureUrl(String nightPictureUrl) {
            this.nightPictureUrl = nightPictureUrl;
        }

        public String getWeather() {
            return weather;
        }

        public void setWeather(String weather) {
            this.weather = weather;
        }

        public String getWind() {
            return wind;
        }

        public void setWind(String wind) {
            this.wind = wind;
        }

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }
    }

    public static class MerchantDataBean {
        /**
         * merchant_name : 魏淑芬1
         * stall_number : TW01
         * star_num : 3
         * business_img : http://cdn.zhumei.net/data/public/default/img/photo.png
         */

        private String merchant_name;
        private String stall_number;
        private String star_num;
        private String business_img;

        public String getMerchant_name() {
            return merchant_name;
        }

        public void setMerchant_name(String merchant_name) {
            this.merchant_name = merchant_name;
        }

        public String getStall_number() {
            return stall_number;
        }

        public void setStall_number(String stall_number) {
            this.stall_number = stall_number;
        }

        public String getStar_num() {
            return star_num;
        }

        public void setStar_num(String star_num) {
            this.star_num = star_num;
        }

        public String getBusiness_img() {
            return business_img;
        }

        public void setBusiness_img(String business_img) {
            this.business_img = business_img;
        }
    }

    public static class MarketTitleDataBean {
        /**
         * title_en : market_name
         * title : 晨曦园农贸市场
         */

        private String title_en;
        private String title;

        public String getTitle_en() {
            return title_en;
        }

        public void setTitle_en(String title_en) {
            this.title_en = title_en;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class MarketPriceOneDataBean {
        /**
         * goods_name : 金针菇
         * goods_price : 4.5
         * unit_name : 元/500
         */

        private String goods_name;
        private String goods_price;
        private String unit_name;

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }

        public String getUnit_name() {
            return unit_name;
        }

        public void setUnit_name(String unit_name) {
            this.unit_name = unit_name;
        }
    }

    public static class MarketPriceTwoDataBean {
        /**
         * goods_name : 金针菇
         * goods_price : 4.5
         * unit_name : 元/500
         */

        private String goods_name;
        private String goods_price;
        private String unit_name;

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }

        public String getUnit_name() {
            return unit_name;
        }

        public void setUnit_name(String unit_name) {
            this.unit_name = unit_name;
        }
    }
}
