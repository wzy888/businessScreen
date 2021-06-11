package com.zhumei.baselib.module.response;

import java.util.List;

public class BannerRes {


    private List<String> full;
    private List<String> right;
    private List<String> half;

    public List<String> getFull() {
        return full;
    }

    public void setFull(List<String> full) {
        this.full = full;
    }

    public List<String> getRight() {
        return right;
    }

    public void setRight(List<String> right) {
        this.right = right;
    }

    public List<String> getHalf() {
        return half;
    }

    public void setHalf(List<String> half) {
        this.half = half;
    }

    @Override
    public String toString() {
        return "BannerRes{" +
                "full=" + full +
                ", right=" + right +
                ", half=" + half +
                '}';
    }
}
