package com.zhumei.baselib.module.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class GoodsPriceRes implements Parcelable {


    /**
     * title : 今日菜价 Today's  Dish  Price
     * content : [{"image":"http://192.168.1.178:8010/uploads/20170705/595cb8b7d3884.png","unit_name":"元/kg","unit_id":1053,"goods_name":"酸菜鱼调料","price":"0.00","weight":6372,"goods_id":28015,"id":6942},{"image":"http://192.168.1.178:8010/uploads/20181031/s_5bd90ab8eb9c8.png","unit_name":"元/kg","unit_id":1053,"goods_name":"猪肉","price":"0.00","weight":6371,"goods_id":28011,"id":6941}]
     */

    private String title;
    private List<ContentRes> content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ContentRes> getContent() {
        return content;
    }

    public void setContent(List<ContentRes> content) {
        this.content = content;
    }

    public static class ContentRes implements Parcelable {
        /**
         * image : http://192.168.1.178:8010/uploads/20170705/595cb8b7d3884.png
         * unit_name : 元/kg
         * unit_id : 1053
         * goods_name : 酸菜鱼调料
         * price : 0.00
         * weight : 6372
         * goods_id : 28015
         * id : 6942
         */

        private String image;
        private String unit_name;
        private Integer unit_id;
        private String goods_name;
        private String price;
        private BigDecimal weight;
        private long goods_id;
        private long id;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getUnit_name() {
            return unit_name;
        }

        public void setUnit_name(String unit_name) {
            this.unit_name = unit_name;
        }

        public Integer getUnit_id() {
            return unit_id;
        }

        public void setUnit_id(Integer unit_id) {
            this.unit_id = unit_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public BigDecimal getWeight() {
            return weight;
        }

        public void setWeight(BigDecimal weight) {
            this.weight = weight;
        }

        public long getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(long goods_id) {
            this.goods_id = goods_id;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "ContentRes{" +
                    "image='" + image + '\'' +
                    ", unit_name='" + unit_name + '\'' +
                    ", unit_id=" + unit_id +
                    ", goods_name='" + goods_name + '\'' +
                    ", price='" + price + '\'' +
                    ", weight=" + weight +
                    ", goods_id=" + goods_id +
                    ", id=" + id +
                    '}';
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.image);
            dest.writeString(this.unit_name);
            dest.writeValue(this.unit_id);
            dest.writeString(this.goods_name);
            dest.writeString(this.price);
            dest.writeSerializable(this.weight);
            dest.writeLong(this.goods_id);
            dest.writeLong(this.id);
        }

        public ContentRes() {
        }

        protected ContentRes(Parcel in) {
            this.image = in.readString();
            this.unit_name = in.readString();
            this.unit_id = (Integer) in.readValue(Integer.class.getClassLoader());
            this.goods_name = in.readString();
            this.price = in.readString();
            this.weight = (BigDecimal) in.readSerializable();
            this.goods_id = in.readLong();
            this.id = in.readLong();
        }

        public static final Creator<ContentRes> CREATOR = new Creator<ContentRes>() {
            @Override
            public ContentRes createFromParcel(Parcel source) {
                return new ContentRes(source);
            }

            @Override
            public ContentRes[] newArray(int size) {
                return new ContentRes[size];
            }
        };
    }

    @Override
    public String toString() {
        return "GoodsPriceRes{" +
                "title='" + title + '\'' +
                ", content=" + content +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeList(this.content);
    }

    public GoodsPriceRes() {
    }

    protected GoodsPriceRes(Parcel in) {
        this.title = in.readString();
        this.content = new ArrayList<ContentRes>();
        in.readList(this.content, ContentRes.class.getClassLoader());
    }

    public static final Parcelable.Creator<GoodsPriceRes> CREATOR = new Parcelable.Creator<GoodsPriceRes>() {
        @Override
        public GoodsPriceRes createFromParcel(Parcel source) {
            return new GoodsPriceRes(source);
        }

        @Override
        public GoodsPriceRes[] newArray(int size) {
            return new GoodsPriceRes[size];
        }
    };
}
