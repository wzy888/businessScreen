package com.zhumei.baselib.widget.recyclreview;

import android.content.Context;
import android.os.Message;
import android.util.AttributeSet;

import androidx.recyclerview.widget.RecyclerView;


import com.zhumei.baselib.app.AppConstants;
import com.zhumei.baselib.utils.HandlerUtils;
import com.zhumei.baselib.utils.useing.event.ThreadManager;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 可以不断轮播的RecyclerView
 */
public class MarqueeRecyclerView extends RecyclerView {

    private Thread thread = null;
    AtomicBoolean shouldContinue = new AtomicBoolean(false);


    private HandlerUtils.HandlerHolder mHandler = new HandlerUtils.HandlerHolder(new HandlerUtils.OnReceiveMessageListener() {
        @Override
        public void handlerMessage(Message msg) {
            if (msg.what == AppConstants.EventCode.RECYCLERVIEW_START_SCROLL) {
//                    // 横纵坐标
                MarqueeRecyclerView.this.scrollBy(1, 0);
            }
        }
    });

    public MarqueeRecyclerView(Context context) {
        super(context);
    }

    public MarqueeRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MarqueeRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 初始化RecyclerView的滚动
     */
    private void initScroll() {
//        AppApplication.setOnHandlerListener(new AppApplication.HandlerListener() {
//            @Override
//            public void heandleMessage(Message msg) {
//                if (msg.what == AppConstants.EventCode.RECYCLERVIEW_START_SCROLL) {
//                    // 横纵坐标
//                    MarqueeRecyclerView.this.scrollBy(1, 0);
//                }
//            }
//        });

//        if (thread == null) {
//            thread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    while (shouldContinue.get()) {
//                        try {
//                            Thread.sleep(20);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        Message msg = AppApplication.mThreadHandler.obtainMessage();
//                        msg.what = AppConstants.EventCode.RECYCLERVIEW_START_SCROLL;
//                        msg.sendToTarget();
//                    }
//                }
//            });
//        }

        ThreadManager.executeSingleTask(new Runnable() {
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
            }
        });

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
