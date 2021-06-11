package com.zhumei.baselib.retrofit;



import com.zhumei.baselib.base.BaseResponse;
import com.zhumei.baselib.config.ConstantApi;
import com.zhumei.baselib.module.response.AutoUpdateRes;
import com.zhumei.baselib.module.response.BannerRes;
import com.zhumei.baselib.module.response.GoodsInfoRes;
import com.zhumei.baselib.module.response.GoodsPriceRes;
import com.zhumei.baselib.module.response.GuideHotKeyRes;
import com.zhumei.baselib.module.response.LoginRes;
import com.zhumei.baselib.module.response.MarketCodeRes;
import com.zhumei.baselib.module.response.MerchantInfo;


import java.util.List;
import java.util.Map;


import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiServerNew {

    //    //登录
    @FormUrlEncoded
    @POST(ConstantApi.LOGIN)
    Observable<BaseResponse<LoginRes>> login(@FieldMap Map<String, Object> paras);

    @FormUrlEncoded
    @POST(ConstantApi.MARKET_CODE)
    Observable<BaseResponse<List<MarketCodeRes>>> getMarketCode(@FieldMap Map<String, Object> paras);

    @FormUrlEncoded
    @POST(ConstantApi.COMMERCIAL_INFO)
    Observable<BaseResponse<MerchantInfo>> getCommercialInfo(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST(ConstantApi.GET_GOODS)
    Observable<BaseResponse<GoodsPriceRes>> getGoods(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST(ConstantApi.GET_BANNER)
    Observable<BaseResponse<BannerRes>> getBanner(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST(ConstantApi.AUTO_UPDATE)
    Observable<BaseResponse<AutoUpdateRes>> autoUpdate(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST(ConstantApi.HOT)
    Observable<BaseResponse<GuideHotKeyRes>> getHot(@FieldMap Map<String,Object>  params);


    @FormUrlEncoded
    @POST(ConstantApi.MERCHANTGOODSINFO)
    Observable<BaseResponse<List<GoodsInfoRes>>> getMerchantGoodsInfo(@FieldMap Map<String,Object>  params);

}
