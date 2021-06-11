package com.zhumei.baselib.widget.percent_support_extends;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.zhumei.baselib.app.AppConstants;


/**
 * Created by zhy on 15/6/30.
 */
public class PercentLinearLayout extends LinearLayout
{

    private static final String TAG = "PercentLinearLayout";
    private PercentLayoutHelper mPercentLayoutHelper;

    public PercentLinearLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        mPercentLayoutHelper = new PercentLayoutHelper(this);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int tmpHeightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, heightMode);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int tmpWidthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, widthMode);

        //fixed scrollview height problems
        if (heightMode == MeasureSpec.UNSPECIFIED && getParent() != null && (getParent() instanceof ScrollView))
        {
            int baseHeight = 0;
            Context context = getContext();
            if (context instanceof Activity)
            {
                Activity act = (Activity) context;
                int measuredHeight = act.findViewById(android.R.id.content).getMeasuredHeight();
                baseHeight = measuredHeight;
            } else
            {
                baseHeight = getScreenHeight();
            }
            tmpHeightMeasureSpec = MeasureSpec.makeMeasureSpec(baseHeight, heightMode);
        }


        mPercentLayoutHelper.adjustChildren(tmpWidthMeasureSpec, tmpHeightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mPercentLayoutHelper.handleMeasuredStateTooSmall())
        {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    private int getScreenHeight()
    {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        if (wm != null) {
            // 底边栏空白的问题 通过修改这里解决了
            if(AppConstants.BoradType.BORAD_TYPE.contains(AppConstants.BoradType.TECLAST_PAD)){
                // 获取的是不含虚拟键盘的分辨率 虚拟键盘也占了有分辨率
                wm.getDefaultDisplay().getMetrics(outMetrics);
            }else {
                // 获取的是屏幕的实际分辨率
                wm.getDefaultDisplay().getRealMetrics(outMetrics);
            }
        }
        return outMetrics.heightPixels;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        super.onLayout(changed, l, t, r, b);
        mPercentLayoutHelper.restoreOriginalParams();
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs)
    {
        return new LayoutParams(getContext(), attrs);
    }


    public static class LayoutParams extends LinearLayout.LayoutParams
            implements PercentLayoutHelper.PercentLayoutParams
    {
        private PercentLayoutHelper.PercentLayoutInfo mPercentLayoutInfo;

        public LayoutParams(Context c, AttributeSet attrs)
        {
            super(c, attrs);
            mPercentLayoutInfo = PercentLayoutHelper.getPercentLayoutInfo(c, attrs);
        }

        @Override
        public PercentLayoutHelper.PercentLayoutInfo getPercentLayoutInfo()
        {
            return mPercentLayoutInfo;
        }

        @Override
        protected void setBaseAttributes(TypedArray a, int widthAttr, int heightAttr)
        {
            PercentLayoutHelper.fetchWidthAndHeight(this, a, widthAttr, heightAttr);
        }

        public LayoutParams(int width, int height)
        {
            super(width, height);
        }


        public LayoutParams(ViewGroup.LayoutParams source)
        {
            super(source);
        }

        public LayoutParams(MarginLayoutParams source)
        {
            super(source);
        }

    }

}
