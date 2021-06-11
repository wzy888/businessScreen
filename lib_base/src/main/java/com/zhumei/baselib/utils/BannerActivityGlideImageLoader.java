package com.zhumei.baselib.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.youth.banner.loader.ImageLoader;
import com.zhumei.baselib.R;
import com.zhumei.baselib.glide.GlideApp;


/**
 * Banner页面图片轮播
 */
public class BannerActivityGlideImageLoader extends ImageLoader {

    private static final String TAG = "BannerActivityGlideImageLoader";

    @Override
    public void displayImage(Context context, Object path, final ImageView imageView) {
        GlideApp.with(context)
            .load(path)
            .error(R.drawable.default_hbanner1)
            .skipMemoryCache(true)
            .listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }
                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    // 根据图片的实际长宽判断，如果宽大于长则拉伸，如果宽小于等于长则居中显示
                    int width = resource.getIntrinsicWidth();
                    int height = resource.getIntrinsicHeight();
                    if(width > height){
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    }else if(width <= height){
                        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    }
                    return false;
                }
            })
            .into(imageView);
    }
}