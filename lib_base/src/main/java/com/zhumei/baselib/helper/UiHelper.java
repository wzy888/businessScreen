package com.zhumei.baselib.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.bumptech.glide.Glide;
import com.zhumei.baselib.MyBaseApplication;
import com.zhumei.baselib.R;
import com.zhumei.baselib.adapter.PopWindowAdapter;
import com.zhumei.baselib.app.AppConstants;
import com.zhumei.baselib.module.response.MarketCodeRes;
import com.zhumei.baselib.utils.ActivityUtil;
import com.zhumei.baselib.utils.TextUtil;
import com.zhumei.baselib.utils.useing.event.ThreadManager;
import com.zhumei.baselib.widget.toast.AlertDialog;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class UiHelper {

    private static final String TAG = "BaseViewImpl";
    //    private Context mContext;
//    private Toast mToast;
    private AlertDialog mDialog;
    private long mPeriod = 10 * 1000;
    private Timer mBannerTimer;
    private TimerTask mBannerTimerTask;
    private int mMixBannerCount;


    public interface ProgressBarCallBack {
        void onServletStarted();
    }

    public void showLoadingProgressBar(final ProgressBar progressBar, final ProgressBarCallBack callBack) {
        try {
            if (progressBar != null && callBack != null) {
                ThreadManager.executeSingleTask(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 100; i++) {
                            int progress = 0;
                            SystemClock.sleep(AppConstants.DefaultSetting.SET_PROGRESS_BAR_SLEEP_CYCLE);
                            progressBar.setProgress(++progress);
                            progressBar.incrementProgressBy(i);
                            if (i == 95) {
                                callBack.onServletStarted();
                            }
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void showImageView(ImageView imageView, int resId) {
        try {
            if (imageView != null) {
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageResource(resId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadImageView(ImageView imageView, String logoUrl, int defaultResId) {
        try {
            Context myApplication = MyBaseApplication.getMyApplication();
            if (imageView != null) {
                if (!TextUtils.isEmpty(logoUrl) && !AppConstants.CommonStr.NULL_STR.equals(logoUrl)) {
                    Glide.with(myApplication)
                            .load(logoUrl)
                            .error(defaultResId)
                            .skipMemoryCache(true)
                            .into(imageView);
                } else {
                    Glide.with(myApplication)
                            .load(defaultResId)
                            .error(defaultResId)
                            .skipMemoryCache(true)
                            .into(imageView);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void showTextView(TextView textView, String data, String defaultData) {
        try {
            if (textView != null) {
                textView.invalidate();
                if (!TextUtils.isEmpty(data) && !AppConstants.CommonStr.NULL_STR.equals(data)) {
                    textView.setText(data);
                } else {
                    textView.setText(defaultData);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showEditText(EditText editText, String data, int defaultResId) {
        try {
            if (editText != null) {
                if (!TextUtils.isEmpty(data) && !AppConstants.CommonStr.NULL_STR.equals(data)) {
                    editText.setText(data);
                } else {
                    editText.setText(defaultResId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * EditText内容移动至最后
     *
     * @param editText
     */
    public void editTextMoveLast(final EditText editText) {
        try {
            if (editText != null) {
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String editable = editText.getText().toString();
                        if (!TextUtils.isEmpty(editable)) {
//设置新的光标所在位置
                            editText.setSelection(editable.length());
                        }

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void showPsdDialog(Activity activity, String title, final String psd, String positiveButtonText,
                              final PasswordDialogCallBack callBack, String negativeButtonText) {
        try {

            ActivityUtil activityUtil = new ActivityUtil();
            boolean activityEnable = activityUtil.isActivityEnable(activity);
            if (!activityEnable) {
                return;
            }
            mDialog = new AlertDialog(activity);
            // AutoSize 适配对话框显示异常问题
            WindowManager windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
            Display defaultDisplay;
            Point size = new Point();
            if (null != windowManager) {
                defaultDisplay = windowManager.getDefaultDisplay();
                defaultDisplay.getSize(size);
            }
//            AutoSize.autoConvertDensity(activity, size.y, false);
//            TextUtil.hideKeyboard2(mDialog.getmTextPsd(),activity);
            TextUtil.hideInputManager(activity, mDialog.getmTextPsd());

            mDialog
                    .builder()
                    .setTitle(title)
                    .setPsd(AppConstants.CommonStr.EMPTY_STR)
                    .setCancelable(false)
                    .setCanceledOnTouchOutside(false)
                    .setPositiveButton(positiveButtonText, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (ObjectUtils.isEmpty(psd)){
                                LogUtils.d("psd is null...");
                                return;
                            }
                            if (psd.equals(mDialog.getPsd().toString().trim())) {
                                callBack.onPasswordCorrect();
                            } else {
                                callBack.onPasswordIncorrect();
                            }
                        }
                    })
                    .setNegativeButton(negativeButtonText, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mDialog.dismiss();
                        }
                    })
                    .show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void initRecyclerView(RecyclerView rv, RecyclerView.LayoutManager layout) {
        rv.setLayoutManager(layout);
    }


    public void setBackgroundColor(View view, String colorRes, int defaultRes) {

        if (view == null) {
            return;
        }

        if (TextUtils.isEmpty(colorRes)) {
            view.setBackgroundColor(defaultRes);
            return;
        }

        view.setBackgroundColor(Color.parseColor(colorRes));


    }

    /**
     * 展示5星 评价/评分
     */


    public void showStarNew(int defaultStar, int ratingCount, ImageView ivStar1, ImageView ivStar2, ImageView ivStar3,
                            ImageView ivStar4, ImageView ivStar5) {

        int count = ratingCount <= 0 ? defaultStar : ratingCount;
        switch (count) {
            case 1:
                ivStar1.setImageResource(R.drawable.icon_star_yellow);
                ivStar2.setImageResource(R.drawable.icon_star_ash);
                ivStar3.setImageResource(R.drawable.icon_star_ash);
                ivStar4.setImageResource(R.drawable.icon_star_ash);
                ivStar5.setImageResource(R.drawable.icon_star_ash);
                break;
            case 2:
                ivStar1.setImageResource(R.drawable.icon_star_yellow);
                ivStar2.setImageResource(R.drawable.icon_star_yellow);
                ivStar3.setImageResource(R.drawable.icon_star_ash);
                ivStar4.setImageResource(R.drawable.icon_star_ash);
                ivStar5.setImageResource(R.drawable.icon_star_ash);
                break;
            case 3:
                ivStar1.setImageResource(R.drawable.icon_star_yellow);
                ivStar2.setImageResource(R.drawable.icon_star_yellow);
                ivStar3.setImageResource(R.drawable.icon_star_yellow);
                ivStar4.setImageResource(R.drawable.icon_star_ash);
                ivStar5.setImageResource(R.drawable.icon_star_ash);
                break;
            case 4:
                ivStar1.setImageResource(R.drawable.icon_star_yellow);
                ivStar2.setImageResource(R.drawable.icon_star_yellow);
                ivStar3.setImageResource(R.drawable.icon_star_yellow);
                ivStar4.setImageResource(R.drawable.icon_star_yellow);
                ivStar5.setImageResource(R.drawable.icon_star_ash);
                break;
            case 5:
                ivStar1.setImageResource(R.drawable.icon_star_yellow);
                ivStar2.setImageResource(R.drawable.icon_star_yellow);
                ivStar3.setImageResource(R.drawable.icon_star_yellow);
                ivStar4.setImageResource(R.drawable.icon_star_yellow);
                ivStar5.setImageResource(R.drawable.icon_star_yellow);
                break;
            default:
                ivStar1.setImageResource(R.drawable.icon_star_ash);
                ivStar2.setImageResource(R.drawable.icon_star_ash);
                ivStar3.setImageResource(R.drawable.icon_star_ash);
                ivStar4.setImageResource(R.drawable.icon_star_ash);
                ivStar5.setImageResource(R.drawable.icon_star_ash);
                break;
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    public void initMarqueeTextView(final TextView tv, final HorizontalScrollView sc, final String speed, final String defaultSpeed) {
        try {
            if (sc != null) {


                if (tv != null) {
                    tv.clearAnimation();
                    tv.post(new Runnable() {
                        @Override
                        public void run() {
                            try {


                                int width1 = sc.getWidth();
                                int width2 = tv.getWidth();
                                Animation translateAnimation = new TranslateAnimation(width1, -width2, 0, 0);
                                translateAnimation.setRepeatCount(Animation.INFINITE);
                                // 设置匀速插值器
                                translateAnimation.setInterpolator(new LinearInterpolator());
                                translateAnimation.setFillBefore(false);
                                translateAnimation.setFillAfter(false);
                                translateAnimation.setFillEnabled(false);
                                int i = width1 + width2;
                                if (speed != null) {
                                    double v = Double.parseDouble(speed);
                                    if ((!TextUtils.isEmpty(speed) && !AppConstants.CommonStr.NULL_STR.equals(speed)) && v > 0) {
                                        if (v < 0.1) {
                                            v = 0.1;
                                        }
                                        if (v > 0.3) {
                                            v = 0.3;
                                        }
                                        translateAnimation.setDuration((long) (i / v));
                                    } else {
                                        translateAnimation.setDuration((long) (i / Double.parseDouble(defaultSpeed)));
                                    }
                                } else {
                                    translateAnimation.setDuration((long) (i / Double.parseDouble(defaultSpeed)));
                                }
                                translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {
                                        tv.clearAnimation();
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {

                                    }
                                });
                                tv.startAnimation(translateAnimation);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void showDialog(Activity activity, String title, String msg, String positiveButtonText,
                           View.OnClickListener positiveButtonListener
            , String negativeButtonText, View.OnClickListener negativeButtonListener) {
        try {
            ActivityUtil activityUtil = new ActivityUtil();
            boolean activityEnable = activityUtil.isActivityEnable(activity);
            if (!activityEnable) {
                return;
            }
            mDialog = new AlertDialog(activity);
            // AutoSize 适配对话框显示异常问题
            WindowManager windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
            Display defaultDisplay;
            Point size = new Point();
            if (null != windowManager) {
                defaultDisplay = windowManager.getDefaultDisplay();
                defaultDisplay.getSize(size);
            }
//            AutoSize.autoConvertDensity(activity, size.y, true);
            mDialog
                    .builder()
                    .setTitle(title)
                    .setMsg(msg)
                    .setCancelable(false)
                    .setCanceledOnTouchOutside(false)
                    .setPositiveButton(positiveButtonText, positiveButtonListener)
                    .setNegativeButton(negativeButtonText, negativeButtonListener)
                    .show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    public void listViewSetAdapter(Activity mContext, ListView listView, List<MarketCodeRes> codeRes) {
        try {
            if (listView != null) {
                listView.setAdapter(new PopWindowAdapter(mContext, codeRes));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
