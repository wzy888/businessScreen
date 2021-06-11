package com.zhumei.baselib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class AutoScrollRecyclerView extends RecyclerView {

    private Disposable mAutoTask;

    public AutoScrollRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void start() {

        try {


            if (mAutoTask != null && !mAutoTask.isDisposed()) {
                mAutoTask.dispose();
            }

            mAutoTask = Observable.interval(5 * 1000, 100, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            smoothScrollBy(0, 4);
                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void stop() {
        if (mAutoTask != null && !mAutoTask.isDisposed()) {
            mAutoTask.dispose();
            mAutoTask = null;
        }
    }


    //禁止手动滑动
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return true;
    }
//    作者：kermitye
//    链接：https://juejin.cn/post/6844903689472344071
//    来源：掘金
//    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
}
