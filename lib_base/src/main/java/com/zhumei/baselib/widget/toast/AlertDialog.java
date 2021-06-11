package com.zhumei.baselib.widget.toast;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.text.Editable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhumei.baselib.R;
import com.zhumei.baselib.widget.dialog.PasswordInputView;


/**
 * 中间弹窗 仿ios的提示框
 */
public class AlertDialog {

    private TextView mTextTitle;
    private TextView mTextMsg;
    private PasswordInputView mTextPsd;
    private Button mBtnNeg;
    private ImageView mImgLine;
    private Button mBtnPos;
    private Context mContext;
    private Display mDisplay;
    private Dialog mDialog;

    /**
     * 是否显示AlertDialog的标题(默认不显示)
     */
    private boolean showTitle = false;
    /**
     * 是否显示AlertDialog的内容
     */
    private boolean showMsg = false;
    /**
     * 是否显示AlertDialog的确定按钮
     */
    private boolean showPosBtn = false;
    /**
     * 是否显示AlertDialog的取消按钮
     */
    private boolean showNegBtn = false;
    /**
     * 是否显示AlertDialog的密码输入框
     */
    private boolean showPsd = false;

    public AlertDialog(Context context) {
        this.mContext = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            mDisplay = windowManager.getDefaultDisplay();
        }
    }

    /**
     * 采用建造者模式,给AlertDialog创建一个建造器
     */
    public AlertDialog builder() {
        // 使用打气筒打出一个布局
        View view = LayoutInflater.from(mContext).inflate(R.layout.alert_dialog_item, null);

        // 找到AlertDialog中的控件
        mTextTitle = (TextView) view.findViewById(R.id.text_title);
        mTextMsg = (TextView) view.findViewById(R.id.text_msg);
        mTextPsd = (PasswordInputView) view.findViewById(R.id.text_psd);
        mBtnNeg = (Button) view.findViewById(R.id.btn_neg);
        mImgLine = (ImageView) view.findViewById(R.id.img_line);
        mBtnPos = (Button) view.findViewById(R.id.btn_pos);
        LinearLayout mLinearLayoutBg = (LinearLayout) view.findViewById(R.id.linearLayout_bg);

        // 创建Dialog的布局和参数
        mDialog = new Dialog(mContext, R.style.AlertDialogStyle);

        // 把View作为布局添加到Dialog中
        mDialog.setContentView(view);

        Point size = new Point();
        mDisplay.getSize(size);

        // 调整Dialog的宽度大小为85%的屏幕宽度 (mDisplay.getWidth() 过期)
//        mLinearLayoutBg.setLayoutParams(new FrameLayout.LayoutParams((int)(mDisplay.getWidth() * 0.85) , LayoutParams.WRAP_CONTENT));
//        mLinearLayoutBg.setLayoutParams(new FrameLayout.LayoutParams((int)(size.x * 0.85), FrameLayout.LayoutParams.WRAP_CONTENT));
        mLinearLayoutBg.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));

        return this;
    }

    /**
     * 给AlertDialog设置标题
     */
    public AlertDialog setTitle(String title){
        showTitle = true;
        if("".equals(title)){
            mTextTitle.setText("标题");
        }else{
            mTextTitle.setText(title);
        }
        return this;
    }

    /**
     * 给AlertDialog设置内容
     */
    public AlertDialog setMsg(String msg){
        showMsg = true;
        if("".equals(msg)){
            mTextMsg.setText("内容");
        }else{
            mTextMsg.setText(msg);
        }
        return this;
    }

    /**
     * 给AlertDialog设置密码数据
     */
    public AlertDialog setPsd(String msg){
        showPsd = true;
        if("".equals(msg)){
            mTextMsg.setText("");
        }else{
            mTextMsg.setText(msg);
        }
        return this;
    }

    /**
     * 让AlertDialog获取最终密码数据
     */
    public Editable getPsd(){
        showPsd = true;
        return mTextPsd.getText();
    }

    public PasswordInputView getmTextPsd() {
        return mTextPsd;
    }

    /**
     * 设置AlertDialog是否可取消
     */
    public AlertDialog setCancelable(boolean cancel){
        mDialog.setCancelable(cancel);
        return this;
    }

    /**
     * 设置AlertDialog dismiss
     */
    public void dismiss(){
        mDialog.dismiss();
    }

    /**
     * 设置AlertDialog点击外部是否可取消
     */
    public AlertDialog setCanceledOnTouchOutside(boolean cancel){
        mDialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    /**
     * 设置AlertDialog确定按钮
     */
    public AlertDialog setPositiveButton(String text, final OnClickListener listener){
        showPosBtn = true;
        if("".equals(text)){
            mBtnPos.setText("确定");
        }else{
            mBtnPos.setText(text);
        }
        mBtnPos.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                mDialog.dismiss();
            }
        });
        return this;
    }

    /**
     * 设置AlertDialog取消按钮
     */
    public AlertDialog setNegativeButton(String text, final OnClickListener listener){
        showNegBtn = true;
        if("".equals(text)){
            mBtnNeg.setText("取消");
        }else{
            mBtnNeg.setText(text);
        }
        mBtnNeg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                mDialog.dismiss();
            }
        });
        return this;
    }

    /**
     * 设置AlertDialog的布局
     */
    private void setLayout(){
        // 如果标题、内容和密码输入框都不设置显示
        if(!showTitle && !showMsg && !showPsd){
            // 显示标题,名字为提示
            mTextTitle.setText("提示");
            mTextTitle.setVisibility(View.VISIBLE);
        }

        // 如果标题、内容设置不显示 密码输入框设置显示
        if(!showTitle && !showMsg && showPsd){
            // 显示标题,名字为提示
            mTextTitle.setText("提示");
            mTextTitle.setVisibility(View.VISIBLE);
            // 显示密码输入框
            mTextPsd.setVisibility(View.VISIBLE);
        }

        // 如果标题设置不显示  内容、密码输入框设置显示
        if(!showTitle && showMsg && showPsd){
            // 显示标题,名字为提示
            mTextTitle.setText("提示");
            mTextTitle.setVisibility(View.VISIBLE);
            // 显示内容
            mTextMsg.setVisibility(View.VISIBLE);
        }

        // 如果标题、密码输入框不显示 内容设置显示
        if(!showTitle && showMsg && !showPsd){
            // 显示标题,名字为提示
            mTextTitle.setText("提示");
            mTextTitle.setVisibility(View.VISIBLE);
            // 显示内容
            mTextMsg.setVisibility(View.VISIBLE);
        }

        // 如果标题设置显示 内容、密码输入框设置不显示
        if(showTitle && !showMsg && !showPsd){
            // 显示标题,名字为提示
            mTextTitle.setVisibility(View.VISIBLE);
        }

        // 如果标题、密码输入框设置显示 内容设置不显示
        if(showTitle && !showMsg && showPsd){
            // 显示标题,名字为提示
            mTextTitle.setVisibility(View.VISIBLE);
            // 显示密码输入框
            mTextPsd.setVisibility(View.VISIBLE);
        }

        // 如果标题、内容设置显示  密码输入框设置不显示
        if(showTitle && showMsg && !showPsd){
            // 显示标题,名字为提示
            mTextTitle.setVisibility(View.VISIBLE);
            // 显示密码输入框
            mTextMsg.setVisibility(View.VISIBLE);
        }

        // 如果标题、内容、密码输入框都设置显示
        if(showTitle && showMsg && showPsd){
            // 显示标题,名字为提示
            mTextTitle.setVisibility(View.VISIBLE);
            // 显示内容
            mTextMsg.setVisibility(View.VISIBLE);
        }

        // 如果确认和取消按钮都不设置显示
        if(!showPosBtn && !showNegBtn){
            // 显示确定按钮,名字为确定
            mBtnPos.setText("确定");
            mBtnPos.setVisibility(View.VISIBLE);
            mBtnPos.setBackgroundResource(R.drawable.alert_dialog_single_selector);
            mBtnPos.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                }
            });
        }

        // 如果确认和取消按钮都设置显示
        if(showPosBtn && showNegBtn){
            mBtnPos.setVisibility(View.VISIBLE);
            mBtnNeg.setVisibility(View.VISIBLE);
            mImgLine.setVisibility(View.VISIBLE);
            mBtnPos.setBackgroundResource(R.drawable.alert_dialog_right_selector);
            mBtnNeg.setBackgroundResource(R.drawable.alert_dialog_left_selector);
        }

        // 如果显示确认按钮,并且不显示取消按钮
        if(showPosBtn && !showNegBtn){
            mBtnPos.setVisibility(View.VISIBLE);
            mBtnPos.setBackgroundResource(R.drawable.alert_dialog_single_selector);
        }

        // 如果显示取消按钮,并且不显示确认按钮
        if(!showPosBtn && showNegBtn){
            mBtnNeg.setVisibility(View.VISIBLE);
            mBtnNeg.setBackgroundResource(R.drawable.alert_dialog_single_selector);
        }
    }

    /**
     * 展示AlertDialog
     */
    public void show() {
        setLayout();
        mDialog.show();
    }
}