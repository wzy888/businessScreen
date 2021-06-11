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
//import androidx.constraintlayout.widget.ConstraintLayout;
//import androidx.constraintlayout.widget.Guideline;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.zhumei.baselib.R;
//import com.zhumei.baselib.helper.UiHelper;
//import com.zhumei.baselib.module.response.GoodsPriceRes;
//import com.zhumei.baselib.utils.useing.software.StringUtils;
//import com.zhumei.baselib.widget.imageview.CircleImageView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class VegetablePricesAdapter2 extends RecyclerView.Adapter<VegetablePricesAdapter2.ViewHolder> {
//    private Context mContext;
//    private List<GoodsPriceRes.ContentRes> mData = new ArrayList<>();
//    private UiHelper uiHelper;
//
//
//    public VegetablePricesAdapter2(Context mContext, List<GoodsPriceRes.ContentRes> data) {
//        this.mContext = mContext;
//         mData.clear();
//         mData.addAll(data);
//        uiHelper = new UiHelper();
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.vegetable_prices_item, parent, false));
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//
//        GoodsPriceRes.ContentRes item = mData.get(position);
//        String price = item.getPrice() + item.getUnit_name();
////        helper.setText(R.id.tv_name, item.getGoods_name())
////                .setText(R.id.tv_price, StringUtils.isNotEmpty2(price) ? price : "0.00元/斤");
////        ImageView iv_goods = helper.getView(R.id.iv_goods);
//        holder.mTvName.setText(TextUtils.isEmpty(item.getGoods_name())?"暂无商品":item.getGoods_name());
//        holder.mTvPrice.setText(StringUtils.isNotEmpty2(price) ? price : "0.00元/斤");
//        uiHelper.loadImageView(holder.mIvGoods, item.getImage(), R.drawable.shucai);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return super.getItemId(position);
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        //刚开始进入包含该类的activity时,count为0。就会出现0%0的情况，这会抛出异常，所以我们要在下面做一下判断
//        int count = mData.size();
//        if (count <= 0) {
//            count = 1;
//        }
//        int newPosition = position % count;
//        return super.getItemViewType(newPosition);
//    }
//
//    @Override
//    public int getItemCount() {
//        return Integer.MAX_VALUE;
//    }
//
//    public void setData(List<GoodsPriceRes.ContentRes> list, boolean flag){
//        if (flag){
//            this.mData = new ArrayList<>();
//        }
//        this.mData.addAll(list);
//        notifyDataSetChanged();
//    }
//
//
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        private ConstraintLayout mClItem;
//        private CircleImageView mIvGoods;
//        private TextView mTvName;
//        private Guideline mGuideLine;
//        private TextView mTvPrice;
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            mClItem = (ConstraintLayout) itemView.findViewById(R.id.cl_item);
//            mIvGoods = (CircleImageView) itemView.findViewById(R.id.iv_goods);
//            mTvName = (TextView) itemView.findViewById(R.id.tv_name);
//            mGuideLine = (Guideline) itemView.findViewById(R.id.guide_line);
//            mTvPrice = (TextView) itemView.findViewById(R.id.tv_price);
//        }
//    }
//}
