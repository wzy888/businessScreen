package com.zhumei.baselib.widget.viewpager;

import android.content.Context;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Scroller;

import androidx.viewpager.widget.ViewPager;

import java.lang.reflect.Field;

/**
 * ViewPager的滑动速度设置
 */
public class ViewPagerScroller extends Scroller {
    /**
     * 滑动速度
     */
    private int mScrollDuration;

    /**
     * 设置速度速度
     * @param duration
     */
    public void setDuration(int duration){
        this.mScrollDuration = duration;
    }

    public ViewPagerScroller(Context context) {
        super(context);
    }

    public ViewPagerScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public ViewPagerScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mScrollDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mScrollDuration);
    }

    public void setViewPagerScrollSpeed(ViewPager viewPager, int speed) {
        try{
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            ViewPagerScroller viewPagerScroller = new ViewPagerScroller(viewPager.getContext(), new OvershootInterpolator(0.6F));
            field.set(viewPager, viewPagerScroller);
            viewPagerScroller.setDuration(speed);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
