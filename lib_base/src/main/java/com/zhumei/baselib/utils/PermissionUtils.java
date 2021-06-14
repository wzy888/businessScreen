package com.zhumei.baselib.utils;

import android.Manifest;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.option.RuntimeOption;
import com.zhumei.baselib.BaseHelper;
import com.zhumei.baselib.RuntimeRationale;

import java.util.List;

/**
 * 第三方 权限工具类
 */
public class PermissionUtils {

    private Context mContext;


   private    String[] requestPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            Manifest.permission.READ_CALL_LOG,
//            Manifest.permission.READ_PHONE_STATE,
//           Manifest.permission.CALL_PHONE,
           Manifest.permission.CAMERA,

    };

   private PermissionCallBack callBack;

    public PermissionUtils() {
        mContext = BaseHelper.getInstance().getContext();
    }

    public void requestPermission(final PermissionCallBack callBack) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                AndPermission.with(mContext)
//                        .runtime()
//                        .permission(requestPermissions)
//                        .onGranted(permissions1 -> {
//
//                            if (callBack!=null){
//                                callBack.onSuccess();
//                            }
//                        })
//                        .onDenied(new Action<List<String>>() {
//                            @Override
//                            public void onAction(@NonNull List<String> permissions) {
//                                if (callBack!=null){
//                                    callBack.onFail(permissions);
//                                }
//                            }
//                        })
//                        .start();
//                AndPermission.with(mContext)
//                        .runtime()
//                        .permission(requestPermissions)
//                        .rationale(new RuntimeRationale())
//                        .onGranted(new Action<List<String>>() {
//                            @Override
//                            public void onAction(List<String> permissions) {
////                                toast(R.string.successfully);
//                            }
//                        })
//                        .onDenied(new Action<List<String>>() {
//                            @Override
//                            public void onAction(@NonNull List<String> permissions) {
//                                toast(R.string.failure);
//                                if (AndPermission.hasAlwaysDeniedPermission(MainActivity.this, permissions)) {
//                                    showSettingDialog(MainActivity.this, permissions);
//                                }
//                            }
//                        })
//                        .start();

                AndPermission.with(mContext)
                        .runtime()
                        .permission(requestPermissions)
                        .rationale(new RuntimeRationale())
                        .onGranted(new Action<List<String>>() {
                            @Override
                            public void onAction(List<String> data) {
                                if (callBack!=null){
                                    callBack.onSuccess();
                                }
                            }
                        }).onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        if (callBack!=null){
                            callBack.onFail(permissions);
                        }
                        if (AndPermission.hasAlwaysDeniedPermission(mContext, permissions)) {
                            /***
                             *  回调方法 写到Activity 显示UI
                             * */
//                                    showSettingDialog(MainActivity.this, permissions);
                                }
                    }
                }).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public interface PermissionCallBack{
        void onSuccess();
        void onFail(List<String> permissions);

    }


}
