package com.zhumei.baselib.widget.textview;

import android.graphics.Paint;
import android.text.style.LineHeightSpan;

public class ExcludeInnerLineSpaceSpan implements LineHeightSpan {
    // TextView行高
    private final int mHeight;


    public ExcludeInnerLineSpaceSpan(int mHeight) {
        this.mHeight = mHeight;
    }

    @Override
    public void chooseHeight(CharSequence text, int start, int end,
                             int spanstartv, int lineHeight,
                             Paint.FontMetricsInt fm) {
        // 原始行高
        final int originHeight = fm.descent - fm.ascent;
        if (originHeight <= 0) {
            return;
        }
        // 计算比例值
        final float ratio = mHeight * 1.0f / originHeight;
        // 根据最新行高，修改descent
        fm.descent = Math.round(fm.descent * ratio);
        // 根据最新行高，修改ascent
        fm.ascent = fm.descent - mHeight;
    }
}
