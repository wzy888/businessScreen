package com.zhumei.commercialscreen.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhumei.baselib.app.AppConstants;
import com.zhumei.baselib.utils.ResourceManager;
import com.zhumei.baselib.utils.useing.software.StringUtils;
import com.zhumei.baselib.widget.anim.FlipCardAnimation;
import com.zhumei.baselib.widget.imageview.CircleImageView;
import com.zhumei.commercialscreen.R;
import com.zhumei.baselib.module.response.GoodsPriceRes;
import com.zhumei.commercialscreen.help.UiHelper;


import java.util.List;

public class GoodsPriceAdapter extends BaseQuickAdapter<GoodsPriceRes.ContentRes, BaseViewHolder> {


    private int mItemIndex = 0;
    //    private FlipCardAnimation cardAnimation = new FlipCardAnimation();
    private UiHelper uiHelper;

    public GoodsPriceAdapter(@Nullable List<GoodsPriceRes.ContentRes> data) {
        super(R.layout.price_item, data);
        uiHelper = new UiHelper();
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsPriceRes.ContentRes item) {

        int position = helper.getAdapterPosition();
//        int size = getData().size();
        ConstraintLayout view = helper.getView(R.id.cl_item);
        int i = (position + 1) % 4;

        if (i == 1 || i == 2) {
            view.setBackgroundColor(ResourceManager.getColResource(R.color.col_1baff7));
        } else {
            view.setBackgroundColor(ResourceManager.getColResource(R.color.col_2f92));
        }
        String price = item.getPrice() + item.getUnit_name();
        helper.setText(R.id.tv_name, item.getGoods_name())
                .setText(R.id.tv_price, StringUtils.isNotEmpty2(price) ? price : "0.00元/斤");
        ImageView iv_goods = helper.getView(R.id.iv_goods);
        uiHelper.loadImageView(iv_goods, item.getImage(), R.drawable.shucai);

        startItemAnim(view, position, helper);

    }

    /**
     * 菜价翻转动画
     *
     * @param
     * @param rl
     * @param position
     * @param helper
     */
    private void startItemAnim(final ConstraintLayout rl, int position, BaseViewHolder helper) {
        try {
//            if (animation != null) {
//                animation.setCanContentChange();
//                rl.startAnimation(animation);
//            } else {
            FlipCardAnimation animation;
            // 测量获取控件宽高
            int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            rl.measure(w, h);
            int width = rl.getMeasuredWidth() / 2;
            int height = rl.getMeasuredHeight() / 2;
            animation = new FlipCardAnimation(0, -180, width, height);
            animation.setInterpolator(new DecelerateInterpolator(10f));
            animation.setDuration(AppConstants.DefaultSetting.SET_PRICE_ROLL_OVER_CYCLE);
            animation.setFillAfter(false);
            animation.setRepeatCount(Animation.INFINITE);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    ((FlipCardAnimation) animation).setCanContentChange();
                }
            });
            animation.setOnContentChangeListener(new FlipCardAnimation.OnContentChangeListener() {
                @Override
                public void contentChange() {

                    if (rl.getId() == R.id.cl_item) {
                        if (mItemIndex >= getData().size()) {
                            mItemIndex = 0;
                        }

                        //显示变化的菜品信息， 价格 .
                        GoodsPriceRes.ContentRes contentRes = getData().get(mItemIndex);
                        String price = contentRes.getPrice() + contentRes.getUnit_name();


                        helper.setText(R.id.tv_price, StringUtils.isNotEmpty2(price) ? price : "0.00元/斤")
                                .setText(R.id.tv_name, TextUtils.isEmpty(contentRes.getGoods_name()) ? "" : contentRes.getGoods_name());

                        CircleImageView ivGoods = helper.getView(R.id.iv_goods);
                        uiHelper.loadImageView(ivGoods, contentRes.getImage(), R.drawable.shucai);
                        mItemIndex++;
                    }
                }
            });
            rl.startAnimation(animation);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
