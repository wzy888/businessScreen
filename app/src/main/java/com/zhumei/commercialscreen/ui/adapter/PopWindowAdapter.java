package com.zhumei.commercialscreen.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhumei.baselib.module.response.MarketCodeRes;
import com.zhumei.baselib.utils.useing.hardware.HardwareUtils;
import com.zhumei.commercialscreen.R;

import java.util.List;

public class PopWindowAdapter extends BaseAdapter {

    private Context mContext;

    private List<MarketCodeRes> codeRes;

    public PopWindowAdapter(Context context, List<MarketCodeRes> codeRes) {
        this.mContext = context;
        this.codeRes = codeRes;
    }

    @Override
    public int getCount() {
        return codeRes == null ? 0 : codeRes.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return hashCode();
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            View view;
            ViewHolder mHolder;
            // 缓存子布局文件中的控件对象
            if (convertView == null) {
                if (HardwareUtils.getScreenResolution(mContext).x > HardwareUtils.getScreenResolution(mContext).y) {
                    view = LayoutInflater.from(mContext).inflate(R.layout.custome_droped_listview_item_horizontal, null, false);
                } else {
                    view = LayoutInflater.from(mContext).inflate(R.layout.custome_droped_listview_item_vertical, null, false);
                }
                mHolder = new ViewHolder();
                mHolder.tvItem = view.findViewById(R.id.tv_login_bottom_listview_item);
                view.setTag(mHolder);
            } else {
                //缓存已滑入ListView中的item view
                view = convertView;
                mHolder = (ViewHolder) view.getTag();
            }
//            mHolder.tvItem.setText(mTestStr[position]);
            mHolder.tvItem.setText(TextUtils.isEmpty(codeRes.get(position).getCode()) ? "" : codeRes.get(position).getCode());
            return view;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    static class ViewHolder {
        TextView tvItem;
    }
}
