package com.zhumei.baselib.base;

/**
 * Created by PC on 2019/9/16.
 */

public interface BaseView {
    /**
     * 显示dialog
     */
    void showLoading();

    /**
     * 隐藏 dialog
     */

    void hideLoading();

    /**
     * 显示错误信息
     *
     * @param msg
     */
    void showError(String msg);

    /**
     * 错误码
     */
    void onErrorCode(BaseResponse model);
}
