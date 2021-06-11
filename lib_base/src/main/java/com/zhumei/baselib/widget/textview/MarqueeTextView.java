package com.zhumei.baselib.widget.textview;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.AttributeSet;

/**
 * 具有跑马灯效果的TextView
 * 实际上就是将使用这个MarqueeTextView控件的全部设置为强制被选中
 */
public class MarqueeTextView extends androidx.appcompat.widget.AppCompatTextView {

    public MarqueeTextView(Context context) {
        super(context);
    }

    public MarqueeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 强制被选中
     */
    @Override
    public boolean isFocused() {
        return true;
    }

    /**
     * 排除每行文字间的padding
     *
     * @param text
     */
    public void setCustomText(CharSequence text) {
        if (text == null) {
            return;
        }
        // 获得视觉定义的每行文字的行高
        int lineHeight = (int) getTextSize();
        SpannableStringBuilder ssb;
        if (text instanceof SpannableStringBuilder) {
            ssb = (SpannableStringBuilder) text;
            // 设置LineHeightSpan
        } else {
            ssb = new SpannableStringBuilder(text);
            // 设置LineHeightSpan
        }
        ssb.setSpan(new ExcludeInnerLineSpaceSpan(lineHeight),
                0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //调用系统自带方法
        setText(ssb);
    }
}