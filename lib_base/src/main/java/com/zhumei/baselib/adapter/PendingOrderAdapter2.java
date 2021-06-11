//package com.zhumei.baselib.adapter;
//
//import android.content.Context;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.constraintlayout.widget.Guideline;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.zhumei.baselib.R;
//import com.zhumei.baselib.bean.ble_setting.BleCmd2Bean;
//import com.zhumei.baselib.helper.UiHelper;
//
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class PendingOrderAdapter2 extends RecyclerView.Adapter<PendingOrderAdapter2.ViewHolder> {
//    private Context mContext;
//    private List<BleCmd2Bean> mData = new ArrayList<>();
//
//
//    public PendingOrderAdapter2(Context mContext, List<BleCmd2Bean> data) {
//        this.mContext = mContext;
//        mData.clear();
//        mData.addAll(data);
//
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.pending_order_item, parent, false));
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
//        BleCmd2Bean item = mData.get(position);
//        int index = position + 1;
//        String orderPrice = item.getUnitPrice() + "/" + item.getUnit();
//        String weight = item.getWeightPcs() + item.getUnit();
//
//        viewHolder.mTvOrderName.setText(TextUtils.isEmpty(item.getName()) ? index + "" : index + "." + item.getName());
//        viewHolder.mTvOrderPrice.setText(orderPrice);
//        viewHolder.mTvOrderWeight.setText("x" + weight);
//        viewHolder.mTvMoney.setText(item.getTotPrice() + "元");
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return super.getItemId(position);
//    }
//
//
//    @Override
//    public int getItemCount() {
//        return mData == null ? 0 : mData.size();
//    }
//
//    public void setData(List<BleCmd2Bean> mData, boolean flag) {
//        if (flag) {
//            this.mData = new ArrayList<>();
//        }
//        this.mData.addAll(mData);
//        notifyDataSetChanged();
//    }
//
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        private UiHelper uiHelper;
//        private TextView mTvOrderName;
//        private Guideline mLine1;
//        private Guideline mLine2;
//        private Guideline mLine3;
//        private TextView mTvOrderPrice;
//        private TextView mTvOrderWeight;
//        private TextView mTvMoney;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            mTvOrderName = (TextView) itemView.findViewById(R.id.tv_order_name);
//            mLine1 = (Guideline) itemView.findViewById(R.id.line1);
//            mLine2 = (Guideline) itemView.findViewById(R.id.line2);
//            mLine3 = (Guideline) itemView.findViewById(R.id.line3);
//            mTvOrderPrice = (TextView) itemView.findViewById(R.id.tv_order_price);
//            mTvOrderWeight = (TextView) itemView.findViewById(R.id.tv_order_weight);
//            mTvMoney = (TextView) itemView.findViewById(R.id.tv_money);
//
//        }
//    }
//}
