package com.zhumei.baselib.bean;


import com.zhumei.baselib.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataBean {
    public Integer imageRes;
    public String imageUrl;
    public String title;
    public int viewType;

    public Integer getImageRes() {
        return imageRes;
    }

    public void setImageRes(Integer imageRes) {
        this.imageRes = imageRes;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public DataBean(Integer imageRes, String title, int viewType) {
        this.imageRes = imageRes;
        this.title = title;
        this.viewType = viewType;
    }
    public DataBean(Integer imageRes, String title, int viewType,String imageUrl) {
        this.imageRes = imageRes;
        this.title = title;
        this.viewType = viewType;
        this.imageUrl = imageUrl;
    }

    public DataBean(String imageUrl, String title, int viewType) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.viewType = viewType;
    }

    public static List<DataBean> getTestData() {
        List<DataBean> list = new ArrayList<>();
        list.add(new DataBean(R.drawable.default_hbanner1, "相信自己,你努力的样子真的很美", 1));
        list.add(new DataBean(R.drawable.default_hbanner1, "极致简约,梦幻小屋", 3));
        list.add(new DataBean(R.drawable.default_hbanner1, "超级卖梦人", 3));
        list.add(new DataBean(R.drawable.default_hbanner1, "夏季新搭配", 1));
        list.add(new DataBean(R.drawable.default_hbanner1, "绝美风格搭配", 1));
        list.add(new DataBean(R.drawable.default_hbanner1, "微微一笑 很倾城", 3));
        return list;
    }

    public static List<DataBean> getDefaultData() {
        List<DataBean> list = new ArrayList<>();
        list.add(new DataBean(R.drawable.default_hbanner1, "", 1,""));
        list.add(new DataBean(R.drawable.default_hbanner2, "", 1,""));
        list.add(new DataBean(R.drawable.default_hbanner3, "", 1,""));
        list.add(new DataBean(R.drawable.default_hbanner4, "", 1,""));
        return list;
    }

    public static List<DataBean> getDefaultDataV() {
        List<DataBean> list = new ArrayList<>();
        list.add(new DataBean(R.drawable.default_vbanner1, "", 1,""));
        list.add(new DataBean(R.drawable.default_vbanner2, "", 1,""));
        list.add(new DataBean(R.drawable.default_vbanner3, "", 1,""));
        list.add(new DataBean(R.drawable.default_vbanner4, "", 1,""));
        return list;
    }

    /**
     * 仿淘宝商品详情第一个是视频
     * @return
     */
    public static List<DataBean> getTestDataVideo() {
        List<DataBean> list = new ArrayList<>();
        list.add(new DataBean("http://vfx.mtime.cn/Video/2019/03/09/mp4/190309153658147087.mp4", "第一个放视频", 2));
        list.add(new DataBean(R.drawable.default_hbanner4, "听风.赏雨", 1));
        list.add(new DataBean(R.drawable.default_hbanner3, "迪丽热巴.迪力木拉提", 1));
        list.add(new DataBean(R.drawable.default_hbanner2, "爱美.人间有之", 1));
        list.add(new DataBean(R.drawable.default_hbanner1, "洋洋洋.气质篇", 1));
        list.add(new DataBean(R.drawable.default_hbanner3, "生活的态度", 1));
        return list;
    }

    public static List<DataBean> getTestData3() {
        List<DataBean> list = new ArrayList<>();
        list.add(new DataBean("https://img.zcool.cn/community/013de756fb63036ac7257948747896.jpg", null, 1));
        list.add(new DataBean("https://img.zcool.cn/community/01639a56fb62ff6ac725794891960d.jpg", null, 1));
        list.add(new DataBean("https://img.zcool.cn/community/01270156fb62fd6ac72579485aa893.jpg", null, 1));
        list.add(new DataBean("https://img.zcool.cn/community/01233056fb62fe32f875a9447400e1.jpg", null, 1));
        list.add(new DataBean("https://img.zcool.cn/community/016a2256fb63006ac7257948f83349.jpg", null, 1));
        return list;
    }

    public static List<String> getColors(int size) {
        List<String> list = new ArrayList<>();
        for(int i = 0; i < size; i++) {
            list.add(getRandColor());
        }
        return list;
    }

    /**
     * 获取十六进制的颜色代码.例如  "#5A6677"
     * 分别取R、G、B的随机值，然后加起来即可
     *
     * @return String
     */
    public static String getRandColor() {
        String R, G, B;
        Random random = new Random();
        R = Integer.toHexString(random.nextInt(256)).toUpperCase();
        G = Integer.toHexString(random.nextInt(256)).toUpperCase();
        B = Integer.toHexString(random.nextInt(256)).toUpperCase();

        R = R.length() == 1 ? "0" + R : R;
        G = G.length() == 1 ? "0" + G : G;
        B = B.length() == 1 ? "0" + B : B;

        return "#" + R + G + B;
    }
}
