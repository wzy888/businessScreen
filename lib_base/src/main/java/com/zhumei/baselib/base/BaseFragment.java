package com.zhumei.baselib.base;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


import com.zhumei.baselib.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

//import com.squareup.leakcanary.RefWatcher;


/**
 * Created by PC on 2019/9/16.
 */

public abstract class BaseFragment<P extends BasePresenterNew> extends Fragment implements BaseView{

    protected abstract int getLayoutId();
    protected abstract P createPresenter();

    protected P presenter;
    Unbinder unbinder;

    /**
     * 是否第一次加载数据
     */
    private boolean isFirstLoad = true;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(getLayoutId(), container, false);

        presenter = createPresenter();

        unbinder = ButterKnife.bind(this, view);


        initView(view);
        return view;
    }

    public void initView(View view) {
    }

    @Override
    public void showLoading() {

    }
    @Override
    public void hideLoading() {

    }
    @Override
    public void showError(String msg) {
        setToast(msg);
    }

    @Override
    public void onErrorCode(BaseResponse model) {
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
//        if (presenter != null) {
//            presenter.detachView();
//        }

//        RefWatcher refWatcher = MyApp.getRefWatcher(getActivity());
//        refWatcher.watch(this);
    }


    @Override
    public void onDestroyView() {
        if (presenter != null) {
            presenter.detachView();
        }
        if (unbinder!=null){
            unbinder.unbind();
        }
        isViewInitiated = false;
        isVisibleToUser = false;
        isDataInitiated = false;
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (presenter != null) {
            presenter = null;
        }
    }


    public void setToast(String msg){
        Toast toast = Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT);
        LinearLayout linearLayout = (LinearLayout) toast.getView();
        TextView messageTextView = (TextView) linearLayout.getChildAt(0);
        messageTextView.setTextSize(14);
        toast.show();
    }


    public void ToastMessage(int imgId, int duration,String messages) {
        //LayoutInflater的作用：对于一个没有被载入或者想要动态载入的界面，都需要LayoutInflater.inflate()来载入，LayoutInflater是用来找res/layout/下的xml布局文件，并且实例化
        LayoutInflater inflater = getLayoutInflater();//调用Activity的getLayoutInflater()
        View view = inflater.inflate(R.layout.toast_style, null); //加載layout下的布局
        ImageView iv = view.findViewById(R.id.toast_img);
        iv.setImageResource(imgId);//显示的图片
        TextView text = view.findViewById(R.id.toast_name);
        text.setText(messages); //toast内容
        Toast toast = new Toast(getActivity());
        toast.setGravity(Gravity.CENTER, 12, 20);//setGravity用来设置Toast显示的位置，相当于xml中的android:gravity或android:layout_gravity
        toast.setDuration(duration);//setDuration方法：设置持续时间，以毫秒为单位。该方法是设置补间动画时间长度的主要方法
        toast.setView(view); //添加视图文件
        toast.show();
    }




    protected boolean isViewInitiated; //控件是否初始化完成
    protected boolean isVisibleToUser; //页面是否可见
    protected boolean isDataInitiated; //数据是否加载

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
        prepareFetchData();
    }

    public abstract void loadData();


    protected void prepareFetchData() {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated)) {
            loadData();
            isDataInitiated = true;
        }
    }







}
