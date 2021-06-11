package com.zhumei.commercialscreen.map;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.zhumei.baselib.app.AppConstants;
import com.zhumei.baselib.utils.useing.cache.CacheUtils;

/**
 * 百度地图
 */
public class MyLocationListener implements BDLocationListener {
    @Override
    public void onReceiveLocation(BDLocation bdLocation) {


        String longitude = "120.19";
        String latitude = "30.26";
//      杭州市经纬度  经度：120.19 ， 纬度：30.26
        if (bdLocation != null) {
            longitude = String.valueOf(bdLocation.getLongitude());
            latitude = String.valueOf(bdLocation.getLatitude());
        }
        CacheUtils.putString(AppConstants.Cache.LONGITUDE, longitude);
        CacheUtils.putString(AppConstants.Cache.LATITUDE, latitude);
    }
}
