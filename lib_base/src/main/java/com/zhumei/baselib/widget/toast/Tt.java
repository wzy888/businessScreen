package com.zhumei.baselib.widget.toast;

import android.content.Context;
import android.widget.Toast;

public class Tt {
    public static TtPrompt ttPrompt;
    public static TtPromptSuccess tPromptSuccess;
    public static TtPromptError tPromptError;

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showAnimToast(Context mContext, String msg) {
        if (!isFastDoubleClick()) {
            if (ttPrompt == null) {
                ttPrompt = new TtPrompt(mContext);
            }
            ttPrompt.showToast(msg);
        }
    }

    public static void showAnimSuccessToast(Context mContext, String msg) {
        if (!isFastDoubleClick()) {
            if (tPromptSuccess == null) {
                tPromptSuccess = new TtPromptSuccess(mContext);
            }
            tPromptSuccess.showToast(msg);
        }
    }

    public static void showAnimErrorToast(Context mContext, String msg) {
        if (!isFastDoubleClick()) {
            if (tPromptError == null) {
                tPromptError = new TtPromptError(mContext);
            }
            tPromptError.showToast(msg);
        }
    }

    protected static long lastClickTime;

    protected static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
