package com.zhumei.baselib.widget.recyclreview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;

import androidx.recyclerview.widget.RecyclerView;


import com.zhumei.baselib.app.AppConstants;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 可以不断竖向轮播的RecyclerView
 */
public class MarqueeVerRecyclerView extends RecyclerView {

    private Handler mHandler;
    private Thread thread = null;
    AtomicBoolean shouldContinue = new AtomicBoolean(false);

    public MarqueeVerRecyclerView(Context context) {
        super(context);
    }

    public MarqueeVerRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MarqueeVerRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 初始化RecyclerView的滚动
     */
    @SuppressLint("HandlerLeak")
    private void initScroll() {
        // 主线程的handler，用于执行Marquee的滚动消息
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case AppConstants.EventCode.RECYCLERVIEW_START_SCROLL:
                        MarqueeVerRecyclerView.this.scrollBy(0, 1);
                        break;

                    default:
                        break;
                }
            }
        };

        if (thread == null) {
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (shouldContinue.get()) {
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Message msg = mHandler.obtainMessage();
                        msg.what = AppConstants.EventCode.RECYCLERVIEW_START_SCROLL;
                        msg.sendToTarget();
                    }
                    //退出循环时清理handler
                    mHandler = null;
                }
            });
        }
    }


    /**
     * 在附到窗口的时候开始滚动
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        shouldContinue.set(true);
        // 初始化RecyclerView的滚动
        initScroll();
        thread.start();
    }


    /**
     * 在脱离窗口时处理相关内容
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopMarquee();
    }

    /**
     * 停止滚动
     */
    public void stopMarquee() {
        shouldContinue.set(false);
        thread = null;
    }
}
