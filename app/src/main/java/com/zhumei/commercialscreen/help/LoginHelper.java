package com.zhumei.commercialscreen.help;

import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupWindow;

public class LoginHelper {

    private static final String TAG = "LoginHelper";

    //    private Context mContext;

    public LoginHelper() {
//        this.mContext = context;
        //构造方法 注入
    }


    public void imageButtonSetOnFocusChangeListener(ImageButton imageButton, final int focusedId, final int normalId) {
        if (imageButton == null) {
            return;
        }
        imageButton.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    v.setBackgroundResource(focusedId);
                } else {
                    v.setBackgroundResource(normalId);
                }
            }
        });


    }


    public void imageButtonSetOnClickListener(ImageButton imageButton, final PopupWindow popupWindow) {

        if (imageButton == null) {
            return;
        }
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // popupwindow相对view位置x轴偏移量
                if (popupWindow != null) {
                    View viewTemp = popupWindow.getContentView();
                    viewTemp.measure(0, 0);
                    int width = viewTemp.getMeasuredWidth();
                    int xOffset = view.getWidth() - width;
                    popupWindow.showAsDropDown(view, xOffset, 0);
                }
            }
        });

    }

}
