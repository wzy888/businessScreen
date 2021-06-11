package com.zhumei.baselib.utils;

import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.zhumei.baselib.BaseHelper;
import com.zhumei.baselib.MyBaseApplication;


public class ResourceManager {


    public static int getColResource(int col) {
        return ContextCompat.getColor(MyBaseApplication.getMyApplication(), col);
    }

    public static Drawable getDrawableResource(int drawable) {

        return ContextCompat.getDrawable( MyBaseApplication.getMyApplication(), drawable);
    }

    public static String getStringResource(int strResource) {
        return MyBaseApplication.getMyApplication().getResources().getString(strResource);
    }
}
