package com.zhumei.baselib.module.response;

import android.os.Parcel;
import android.os.Parcelable;


import com.zhumei.baselib.bean.commercial_info.ScaleGoods;
import com.zhumei.baselib.bean.commercial_info.ScaleHotkeyCommInfo;
import com.zhumei.baselib.bean.commercial_info.ScaleTicket;


import java.util.ArrayList;
import java.util.List;

public class MerchantInfo implements Parcelable {


    /**
     * merchant_name : 阳仔
     * merchant_id : 878
     * stall : YZ-01
     * stall_id : 2436
     * avator :
     * star : 1
     * wxpay_img :
     * alipay_img :
     * thumb : 30
     * marquee_title : 今日菜价 Today's  Dish  Price
     * merchant_ad_data : []
     * health_img : http://192.168.1.178:8010/upload/excel_tpl/921b98e8b1c769730d1898f31199abd3.jpg
     * temperature : http://192.168.1.178:8010/upload/excel_tpl/b15835d53c7198bd464f0ad6a69b63a5.png
     * discount_title : 今日特价
     * red_black : http://192.168.1.178:8010/upload/excel_tpl/46ed7fe6e76435acb3024385f557e33d.png
     * qr_image :
     * password : ###1b3dd4b73d651f18803e7817fe6569c9
     * mobile : 13222217912
     * is_pay : 0
     */

    private String merchant_name;
    private int merchant_id;
    private String stall;
    private int stall_id;
    private String avator;
    private int star;
    private String wxpay_img;
    private String alipay_img;
    private int thumb;
    private String marquee_title;
    private String health_img;
    private String temperature;
    private String discount_title;
    private String red_black;
    private String qr_image;
    private String password;
    private String mobile;
    private int is_pay;
    private List<String> merchant_ad_data;

    private String quit;
    private String bluetooth;
    private String logo;
    private String logo_title;

    private String evaluation_qr;
    private String template_id;

    private List<String> discount_content;

    private List<String> news;

    public List<String> getNews() {
        return news;
    }

    public void setNews(List<String> news) {
        this.news = news;
    }

    public List<String> getDiscount_content() {
        return discount_content;
    }

    public void setDiscount_content(List<String> discount_content) {
        this.discount_content = discount_content;
    }

    public String getBackground_image() {
        return background_image;
    }

    public void setBackground_image(String background_image) {
        this.background_image = background_image;
    }

    private String background_image;

    private List<ScaleHotkeyCommInfo> hot_key;
    private List<ScaleTicket> ticket_info;

    private List<ScaleGoods> goods;

    public List<ScaleGoods> getGoods() {
        return goods;
    }

    public void setGoods(List<ScaleGoods> goods) {
        this.goods = goods;
    }

    public List<ScaleHotkeyCommInfo> getHot_key() {
        return hot_key;
    }

    public void setHot_key(List<ScaleHotkeyCommInfo> hot_key) {
        this.hot_key = hot_key;
    }

    public List<ScaleTicket> getTicket_info() {
        return ticket_info;
    }

    public void setTicket_info(List<ScaleTicket> ticket_info) {
        this.ticket_info = ticket_info;
    }





    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getEvaluation_qr() {
        return evaluation_qr;
    }

    public void setEvaluation_qr(String evaluation_qr) {
        this.evaluation_qr = evaluation_qr;
    }

    public String getQuit() {
        return quit;
    }

    public void setQuit(String quit) {
        this.quit = quit;
    }

    public String getBluetooth() {
        return bluetooth;
    }

    public void setBluetooth(String bluetooth) {
        this.bluetooth = bluetooth;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLogo_title() {
        return logo_title;
    }

    public void setLogo_title(String logo_title) {
        this.logo_title = logo_title;
    }

    public String getMerchant_name() {
        return merchant_name;
    }

    public void setMerchant_name(String merchant_name) {
        this.merchant_name = merchant_name;
    }

    public int getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(int merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getStall() {
        return stall;
    }

    public void setStall(String stall) {
        this.stall = stall;
    }

    public int getStall_id() {
        return stall_id;
    }

    public void setStall_id(int stall_id) {
        this.stall_id = stall_id;
    }

    public String getAvator() {
        return avator;
    }

    public void setAvator(String avator) {
        this.avator = avator;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getWxpay_img() {
        return wxpay_img;
    }

    public void setWxpay_img(String wxpay_img) {
        this.wxpay_img = wxpay_img;
    }

    public String getAlipay_img() {
        return alipay_img;
    }

    public void setAlipay_img(String alipay_img) {
        this.alipay_img = alipay_img;
    }

    public int getThumb() {
        return thumb;
    }

    public void setThumb(int thumb) {
        this.thumb = thumb;
    }

    public String getMarquee_title() {
        return marquee_title;
    }

    public void setMarquee_title(String marquee_title) {
        this.marquee_title = marquee_title;
    }

    public String getHealth_img() {
        return health_img;
    }

    public void setHealth_img(String health_img) {
        this.health_img = health_img;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getDiscount_title() {
        return discount_title;
    }

    public void setDiscount_title(String discount_title) {
        this.discount_title = discount_title;
    }

    public String getRed_black() {
        return red_black;
    }

    public void setRed_black(String red_black) {
        this.red_black = red_black;
    }

    public String getQr_image() {
        return qr_image;
    }

    public void setQr_image(String qr_image) {
        this.qr_image = qr_image;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getIs_pay() {
        return is_pay;
    }

    public void setIs_pay(int is_pay) {
        this.is_pay = is_pay;
    }

    public List<String> getMerchant_ad_data() {
        return merchant_ad_data;
    }

    public void setMerchant_ad_data(List<String> merchant_ad_data) {
        this.merchant_ad_data = merchant_ad_data;
    }

    @Override
    public String toString() {
        return "MerchantInfo{" +
                "merchant_name='" + merchant_name + '\'' +
                ", merchant_id=" + merchant_id +
                ", stall='" + stall + '\'' +
                ", stall_id=" + stall_id +
                ", avator='" + avator + '\'' +
                ", star=" + star +
                ", wxpay_img='" + wxpay_img + '\'' +
                ", alipay_img='" + alipay_img + '\'' +
                ", thumb=" + thumb +
                ", marquee_title='" + marquee_title + '\'' +
                ", health_img='" + health_img + '\'' +
                ", temperature='" + temperature + '\'' +
                ", discount_title='" + discount_title + '\'' +
                ", red_black='" + red_black + '\'' +
                ", qr_image='" + qr_image + '\'' +
                ", password='" + password + '\'' +
                ", mobile='" + mobile + '\'' +
                ", is_pay=" + is_pay +
                ", merchant_ad_data=" + merchant_ad_data +
                '}';
    }

    public MerchantInfo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.merchant_name);
        dest.writeInt(this.merchant_id);
        dest.writeString(this.stall);
        dest.writeInt(this.stall_id);
        dest.writeString(this.avator);
        dest.writeInt(this.star);
        dest.writeString(this.wxpay_img);
        dest.writeString(this.alipay_img);
        dest.writeInt(this.thumb);
        dest.writeString(this.marquee_title);
        dest.writeString(this.health_img);
        dest.writeString(this.temperature);
        dest.writeString(this.discount_title);
        dest.writeString(this.red_black);
        dest.writeString(this.qr_image);
        dest.writeString(this.password);
        dest.writeString(this.mobile);
        dest.writeInt(this.is_pay);
        dest.writeStringList(this.merchant_ad_data);
        dest.writeString(this.quit);
        dest.writeString(this.bluetooth);
        dest.writeString(this.logo);
        dest.writeString(this.logo_title);
        dest.writeString(this.evaluation_qr);
        dest.writeString(this.template_id);
        dest.writeStringList(this.discount_content);
        dest.writeStringList(this.news);
        dest.writeString(this.background_image);
        dest.writeTypedList(this.hot_key);
        dest.writeTypedList(this.ticket_info);
        dest.writeTypedList(this.goods);
    }

    protected MerchantInfo(Parcel in) {
        this.merchant_name = in.readString();
        this.merchant_id = in.readInt();
        this.stall = in.readString();
        this.stall_id = in.readInt();
        this.avator = in.readString();
        this.star = in.readInt();
        this.wxpay_img = in.readString();
        this.alipay_img = in.readString();
        this.thumb = in.readInt();
        this.marquee_title = in.readString();
        this.health_img = in.readString();
        this.temperature = in.readString();
        this.discount_title = in.readString();
        this.red_black = in.readString();
        this.qr_image = in.readString();
        this.password = in.readString();
        this.mobile = in.readString();
        this.is_pay = in.readInt();
        this.merchant_ad_data = in.createStringArrayList();
        this.quit = in.readString();
        this.bluetooth = in.readString();
        this.logo = in.readString();
        this.logo_title = in.readString();
        this.evaluation_qr = in.readString();
        this.template_id = in.readString();
        this.discount_content = in.createStringArrayList();
        this.news = in.createStringArrayList();
        this.background_image = in.readString();
        this.hot_key = in.createTypedArrayList(ScaleHotkeyCommInfo.CREATOR);
        this.ticket_info = in.createTypedArrayList(ScaleTicket.CREATOR);
        this.goods = in.createTypedArrayList(ScaleGoods.CREATOR);
    }

    public static final Creator<MerchantInfo> CREATOR = new Creator<MerchantInfo>() {
        @Override
        public MerchantInfo createFromParcel(Parcel source) {
            return new MerchantInfo(source);
        }

        @Override
        public MerchantInfo[] newArray(int size) {
            return new MerchantInfo[size];
        }
    };
}
