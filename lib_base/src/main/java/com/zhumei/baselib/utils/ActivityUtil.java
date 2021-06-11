package com.zhumei.baselib.utils;

import android.app.Activity;
import android.content.Intent;

import com.zhumei.baselib.R;


public class ActivityUtil {

    public ActivityUtil() {

    }

    public void toOtherActivity(Activity currentActivity, Class target) {
        try {
            if (currentActivity != null && target != null) {
                currentActivity.startActivity(new Intent(currentActivity, target));
                currentActivity.overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
                currentActivity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Activity是否已被销毁
     * @return
     */
    public boolean isActivityEnable(Activity activity){
        if(activity == null || activity.isDestroyed() || activity.isFinishing()){
            return false;
        }
        return true;
    }


}
