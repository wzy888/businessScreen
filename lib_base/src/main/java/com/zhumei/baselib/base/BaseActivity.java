package com.zhumei.baselib.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zhumei.baselib.MyBaseApplication;
import com.zhumei.baselib.R;
import com.zhumei.baselib.app.AppConstants;
import com.zhumei.baselib.receiver.NetBroadcastReceiver;
import com.zhumei.baselib.utils.TextUtil;
import com.zhumei.baselib.utils.useing.event.EventBusUtils;
import com.zhumei.baselib.utils.useing.software.LogUtils;


import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.util.Iterator;


/**
 * Created by PC on 2019/9/16.
 */

public abstract class BaseActivity<P extends BasePresenterNew> extends AppCompatActivity implements BaseView {
    private String TAG = "MyBase";
    private int mIsGeTuiMsgInUse = 88;

    protected abstract int getLayoutId();

    //    protected abstract int getRootId();
    protected abstract Activity getActivity();

    protected abstract P createPresenter();
//    protected abstract boolean isBlack();

    protected P presenter;

    /**
     * 网络类型
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // 设置全屏
        setFullScreen();
        // 取消锁屏
        removeLock();
//         取消虚拟键盘
        removeVirtualKeyboard();

        super.onCreate(savedInstanceState);

        if (getLayoutId() != -1) {
            try {


                setContentView(getLayoutId());
                ARouter.getInstance().inject(this);
                MyBaseApplication.addActivity(getActivity());
                presenter = createPresenter();

                initView();
                initData();
                EventBusUtils.register(this);
                registerNetBoard();

            } catch (Exception e) {
                e.printStackTrace();
            }


        }



    }




    public  boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    private void getDp() {

        //获取屏幕分辨率
        WindowManager windowManager = getWindow().getWindowManager();
        Point point = new Point();
        windowManager.getDefaultDisplay().getRealSize(point);
//屏幕实际宽度（像素个数）
        int width = point.x;
//屏幕实际高度（像素个数）
        int height = point.y;

        LogUtils.i("像素 = ", width + " - " + height);
    }




    /**
     * 设置 app 不随着系统字体的调整而变化
     */

    public Resources getResources() {
        //禁止app字体大小跟随系统字体大小调节
        Resources resources = super.getResources();
        if (resources != null && resources.getConfiguration().fontScale != 1.0f) {
            Configuration configuration = resources.getConfiguration();
            configuration.fontScale = 1.0f;
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        }
        return resources;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    public void initData() {
    }

    public void initView() {
    }

    public void setHideVirtualKey(Window window) {
        //保持布局状态
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                //布局位于状态栏下方
                // View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION|
                //全屏
                //View.SYSTEM_UI_FLAG_FULLSCREEN|
                //隐藏导航栏
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        //View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        if (Build.VERSION.SDK_INT >= 19) {
            uiOptions |= 0x00001000;
        } else {
            uiOptions |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
        }
        window.getDecorView().setSystemUiVisibility(uiOptions);
    }

    public void setHideVirtualKeyByBlack(Window window) {
        //保持布局状态
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                //布局位于状态栏下方
                // View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION|
                //全屏
                //View.SYSTEM_UI_FLAG_FULLSCREEN|
                //状态栏字体黑色
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR |
                //隐藏导航栏
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        //View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        if (Build.VERSION.SDK_INT >= 19) {
            uiOptions |= 0x00001000;
        } else {
            uiOptions |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
        }
        window.getDecorView().setSystemUiVisibility(uiOptions);
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String msg) {
        ToastMessage(R.drawable.toast_x, Toast.LENGTH_SHORT, msg);
    }

    @Override
    public void onErrorCode(BaseResponse model) {
    }


    @Override
    protected void onPause() {
        // 清除一些数据
        clearData();

        super.onPause();
    }

    @Override
    public void onBackPressed() {
        // 点击返回键做的操作
        backPress();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 取消EventBus注册
        EventBusUtils.unRegister(this);


        if (presenter != null) {
            presenter.detachView();
        }


    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    /**
     * 检查弱引用是否释放，若释放，则从栈中清理掉该元素
     */
    public void checkWeakReference() {
        if (MyBaseApplication.mActivityStack != null) {
            // 使用迭代器进行安全删除
            for (Iterator<WeakReference<Activity>> it = MyBaseApplication.mActivityStack.iterator(); it.hasNext(); ) {
                WeakReference<Activity> activityReference = it.next();
                Activity temp = activityReference.get();
                if (temp == null) {
                    it.remove();
                }
            }
        }
    }

    /**
     * 获取当前Activity（栈中最后一个压入的）
     *
     * @return
     */
    public Activity currentActivity() {
        checkWeakReference();
        if (MyBaseApplication.mActivityStack != null && !MyBaseApplication.mActivityStack.isEmpty()) {
            return MyBaseApplication.mActivityStack.lastElement().get();
        }
        return null;
    }

    /**
     * 关闭当前Activity（栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = currentActivity();
        if (activity != null) {
            finishActivity(activity);
        }
    }

    /**
     * 关闭指定的Activity
     *
     * @param activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null && MyBaseApplication.mActivityStack != null) {
            // 使用迭代器进行安全删除
            for (Iterator<WeakReference<Activity>> it = MyBaseApplication.mActivityStack.iterator(); it.hasNext(); ) {
                WeakReference<Activity> activityReference = it.next();
                Activity temp = activityReference.get();
                // 清理掉已经释放的activity
                if (temp == null) {
                    it.remove();
                    continue;
                }
                if (temp == activity) {
                    it.remove();
                }
            }
            activity.finish();
        }
    }

    /**
     * 关闭指定类名的所有Activity
     *
     * @param cls
     */
    public void finishActivity(Class<?> cls) {
        if (MyBaseApplication.mActivityStack != null) {
            // 使用迭代器进行安全删除
            for (Iterator<WeakReference<Activity>> it = MyBaseApplication.mActivityStack.iterator(); it.hasNext(); ) {
                WeakReference<Activity> activityReference = it.next();
                Activity activity = activityReference.get();
                // 清理掉已经释放的activity
                if (activity == null) {
                    it.remove();
                    continue;
                }
                if (activity.getClass().equals(cls)) {
                    it.remove();
                    activity.finish();
                }
            }
        }
    }

    public void finishisJustHave(Class<?> cls) {
        if (MyBaseApplication.mActivityStack != null) {
            // 使用迭代器进行安全删除
            for (Iterator<WeakReference<Activity>> it = MyBaseApplication.mActivityStack.iterator(); it.hasNext(); ) {
                WeakReference<Activity> activityReference = it.next();
                Activity activity = activityReference.get();
                // 清理掉已经释放的activity
                if (activity == null) {
                    it.remove();
                    continue;
                }
                if (!activity.getClass().equals(cls)) {
                    it.remove();
                    activity.finish();
                }
            }
        }
    }

    public boolean isHaveSortActivity(Class<?> cls) {
        boolean ishave = false;
        if (MyBaseApplication.mActivityStack != null) {
            // 使用迭代器进行安全删除
            for (Iterator<WeakReference<Activity>> it = MyBaseApplication.mActivityStack.iterator(); it.hasNext(); ) {
                WeakReference<Activity> activityReference = it.next();
                Activity activity = activityReference.get();
                // 清理掉已经释放的activity
                if (activity == null) {
                    it.remove();
                    continue;
                }
                if (activity.getClass().equals(cls)) {
                    ishave = true;
                }
            }
        }
        return ishave;
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        if (MyBaseApplication.mActivityStack != null) {
            for (WeakReference<Activity> activityReference : MyBaseApplication.mActivityStack) {
                Activity activity = activityReference.get();
                if (activity != null) {
                    activity.finish();
                }
            }
            MyBaseApplication.mActivityStack.clear();
        }
    }

    public int haveNumberActivity() {
        int number = 0;
        if (MyBaseApplication.mActivityStack != null) {
            for (WeakReference<Activity> activityReference : MyBaseApplication.mActivityStack) {
                number = number + 1;
            }
            return number;
        }
        return number;
    }

    /**
     * 退出应用程序
     */
    public void exitApp() {
        try {
            finishAllActivity();
            // 退出JVM,释放所占内存资源,0表示正常退出
            System.exit(0);
            // 从系统中kill掉应用程序
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                View view = getCurrentFocus();
                TextUtil.hideKeyboard(ev, view, BaseActivity.this);//调用方法判断是否需要隐藏键盘
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    public void ToastMessage(int imgId, int duration, String messages) {
        //LayoutInflater的作用：对于一个没有被载入或者想要动态载入的界面，都需要LayoutInflater.inflate()来载入，LayoutInflater是用来找res/layout/下的xml布局文件，并且实例化
        LayoutInflater inflater = getLayoutInflater();//调用Activity的getLayoutInflater()
        View view = inflater.inflate(R.layout.toast_style, null); //加載layout下的布局
        ImageView iv = view.findViewById(R.id.toast_img);
        iv.setImageResource(imgId);//显示的图片
        TextView text = view.findViewById(R.id.toast_name);
        text.setText(messages); //toast内容
        Toast toast = new Toast(MyBaseApplication.getMyApplication());
        toast.setGravity(Gravity.CENTER, 12, 20);//setGravity用来设置Toast显示的位置，相当于xml中的android:gravity或android:layout_gravity
        toast.setDuration(duration);//setDuration方法：设置持续时间，以毫秒为单位。该方法是设置补间动画时间长度的主要方法
        toast.setView(view); //添加视图文件
        toast.show();
    }


    /**
     * 判断Activity是否Destroy
     * mActivity
     *
     * @return
     */
    public static boolean isDestroy(Activity mActivity) {

        if (mActivity == null || mActivity.isFinishing() || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && mActivity.isDestroyed())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 点击返回键做的操作
     */
    protected abstract void backPress();

    /**
     * 准备退出此activity
     */
    protected abstract void clearData();

    /**
     * 设置全屏
     */
    protected void setFullScreen() {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //如果上面的不起作用，可以换成下面的。
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        if (getActionBar() != null) getActionBar().hide();
        //no status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    /**
     * 取消锁屏
     */
    @SuppressLint("MissingPermission")
    private void removeLock() {
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock lock = keyguardManager.newKeyguardLock(Context.KEYGUARD_SERVICE);
        lock.disableKeyguard();
    }

    /**
     * 取消虚拟键盘
     */
    private void removeVirtualKeyboard() {
        // 解决一进入Activity就自动弹出虚拟键盘问题
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    /**
     * 隐藏ActionBar和StatusBar
     */
    private void hideActionStatusBar() {
        //set no title bar 需要在setContentView之前调用
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //如果上面的不起作用，可以换成下面的。
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        if (getActionBar() != null) getActionBar().hide();
        //no status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 隐藏 NavigationBar和StatusBar
     */
    protected void hideBottomStatusBar() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    /**
     * 点击遥控器上不同的按键实现不同功能
     * 静音键在V3.2.6版本取消了Home键功能，改为了蓝牙设置功能 在commercialinfo页面使用
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 数字1键
        if (keyCode == KeyEvent.KEYCODE_1) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri contentUrl = Uri.parse(AppConstants.DefaultSetting.SET_BAIDU_URL);
            intent.setData(contentUrl);
            startActivity(intent);
            overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
            // 数字2键
        } else if (keyCode == KeyEvent.KEYCODE_2) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri contentUrl = Uri.parse(AppConstants.DefaultSetting.SET_ZHUMEI_URL);
            intent.setData(contentUrl);
            startActivity(intent);
            overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        }
//        // 静音键就是home键(写到单独每个Activity里)
//        else if (keyCode == KeyEvent.KEYCODE_MUTE || keyCode == KeyEvent.KEYCODE_VOLUME_MUTE) {
//            Intent intent = new Intent(Intent.ACTION_MAIN);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.addCategory(Intent.CATEGORY_HOME);
//            startActivity(intent);
//            return true;
//        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 判断外网网络
     */

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainThreadEventPost(Event event) {
        if (event != null) {
            receiveMainThreadEvent(event);
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onBackgroundEventPost(Event event) {
        if (event != null) {
            receiveBackgroundEvent(event);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMainThreadStickyEventPost(Event event) {
        if (event != null) {
            receiveMainThreadStickyEvent(event);
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND, sticky = true)
    public void onBackgroundStickyEventPost(Event event) {
        if (event != null) {
            receiveBackgroundStickyEvent(event);
        }
    }

    protected void receiveMainThreadEvent(Event event) {
    }


    protected void receiveMainThreadStickyEvent(Event event) {
    }

    protected void receiveBackgroundEvent(Event event) {
    }

    protected void receiveBackgroundStickyEvent(Event event) {
    }


    /**
     *  android 版本 > 7 静态注册广播无效 ; 需要动态注册监听网络变化.
     * */
    public void registerNetBoard(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        NetBroadcastReceiver networkChangeReceiver = new NetBroadcastReceiver();
        registerReceiver(networkChangeReceiver,intentFilter);//注册广播接收器，接收CONNECTIVITY_CHANGE这个广播
    }

}
