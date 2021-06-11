package com.zhumei.baselib.widget.toast;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationSet;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhumei.baselib.R;


public class TtPromptError extends Toast {
    private Context mContext;
    private AnimationSet mModalInAnim;
    private AnimationSet mModalOutAnim;
    private TextView chapterNameTV;
    private RelativeLayout rlRoot;

    public TtPromptError(Context context) {
        super(context);
        this.mContext = context;
        mModalInAnim = (AnimationSet) OptAnimationLoader.loadAnimation(mContext, R.anim.alert_dialog_modal_in);
        mModalOutAnim = (AnimationSet) OptAnimationLoader.loadAnimation(mContext, R.anim.alert_dialog_modal_out);
        initView();
    }

    protected void initView() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.tt_prompt_error_item, null);
        rlRoot = (RelativeLayout) view.findViewById(R.id.rl_root);
        chapterNameTV = (TextView) view.findViewById(R.id.chapterName);
        setGravity(Gravity.CENTER, 0, 0);
        setDuration(Toast.LENGTH_LONG);
        setView(view);

    }


    public void showToast(String msg) {
        if (chapterNameTV != null) {
            chapterNameTV.setText(msg);
            mModalInAnim.setDuration(250);
            rlRoot.setVisibility(View.VISIBLE);
            rlRoot.startAnimation(mModalInAnim);
            show();

            rlRoot.postDelayed(new Runnable() {
                @Override
                public void run() {
                    rlRoot.setVisibility(View.INVISIBLE);
                    rlRoot.startAnimation(mModalOutAnim);
                }
            },1000);
        }
    }

}
