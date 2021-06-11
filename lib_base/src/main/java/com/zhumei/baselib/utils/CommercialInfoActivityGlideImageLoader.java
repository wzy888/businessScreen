package com.zhumei.baselib.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.youth.banner.loader.ImageLoader;
import com.zhumei.baselib.R;

/**
 * 证照图片轮播
 */
public class CommercialInfoActivityGlideImageLoader extends ImageLoader {

    private static final String TAG = "CommercialInfoActivityGlideImageLoader";

    @Override
    public void displayImage(Context context, Object path, final ImageView imageView) {
        Glide.with(context)
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
                        if (width > height) {
                            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

                        } else if (width <= height) {
                            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

                        }
                        //图片 铺满
//                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

                        return false;
                    }
                })
                .into(imageView);




    }
}