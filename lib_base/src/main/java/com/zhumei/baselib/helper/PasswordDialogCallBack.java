package com.zhumei.baselib.helper;

public interface PasswordDialogCallBack {

    /**
     * PasswordDialog密码正确
     */
    void onPasswordCorrect();

    /**
     * PasswordDialog密码错误
     */
    void onPasswordIncorrect();
}
