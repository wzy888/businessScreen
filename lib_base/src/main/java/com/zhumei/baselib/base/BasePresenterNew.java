package com.zhumei.baselib.base;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhumei.baselib.BaseHelper;
import com.zhumei.baselib.retrofit.ApiRetrofitNew;
import com.zhumei.baselib.retrofit.ApiServerNew;


import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by PC on 2019/9/16.
 */

public class BasePresenterNew<V extends BaseView> {
    private CompositeDisposable compositeDisposable;


    public V baseView;

    protected ApiServerNew apiServer = ApiRetrofitNew.getInstance().getApiService();

    public BasePresenterNew(V baseView) {

        this.baseView = baseView;
    }

    /**
     * 解除绑定
     */
    public void detachView() {
        baseView = null;
        removeDisposable();
    }

    /**
     * 返回 view
     *
     * @return
     */
    public V getBaseView() {
        return baseView;
    }


    public void addDisposable(Observable<?> observable, BaseObserverNew observer) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer));


    }

    public void removeDisposable() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    public void setToast(String msg){
        try {
            Toast toast = Toast.makeText(BaseHelper.getInstance().getContext(), msg, Toast.LENGTH_SHORT);
            if (toast.getView()!=null){
                LinearLayout linearLayout = (LinearLayout) toast.getView();
                TextView messageTextView = (TextView) linearLayout.getChildAt(0);
                messageTextView.setTextSize(14);
                toast.show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

}
