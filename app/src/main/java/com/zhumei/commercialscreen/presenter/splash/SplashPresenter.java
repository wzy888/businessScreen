//package com.zhumei.commercialscreen.presenter.splash;
//
//import android.util.Log;
//
//import com.zhumei.commercialscreen.app.AppConstants;
//import com.zhumei.commercialscreen.base.BaseObserver;
//import com.zhumei.commercialscreen.base.BasePresenter;
//
//import java.util.HashMap;
//
//import okhttp3.ResponseBody;
//
//public class SplashPresenter extends BasePresenter<SplashView> {
//    public SplashPresenter(SplashView baseView) {
//        super(baseView);
//    }
//
////    public void getInterfaceStatus(String mSavedDeviceId) {
////        HashMap params = new HashMap();
////        try {
////            params.put("device_id", mSavedDeviceId);
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//////        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), params.toString());
////        addDisposable(apiServer.interfaceStatus(params), new BaseObserver<ResponseBody>(baseView) {
////            @Override
////            public void onSuccess(String o) {
////                baseView.getInterfaceStatus(o);
////            }
////
////            @Override
////            public void onError(String msg) {
////                Log.i("getInterfaceStatus", msg);
////                setToast(msg);
////                baseView.showInterfaceError(msg);
////            }
////        });
////    }
//
//
//    /**
//     * 预请求 Banner
//     */
//    public void prepareForBanner(String mSavedDeviceId) {
//        HashMap params = new HashMap();
//        try {
//            params.put("device_id", mSavedDeviceId);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
////        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), params.toString());
//        addDisposable(apiServer.prepareForBanner(params), new BaseObserver<ResponseBody>(baseView) {
//            @Override
//            public void onSuccess(String o) {
//                baseView.prepareForBanner(o);
//            }
//
//            @Override
//            public void onError(String msg) {
//                Log.i("prepareForBanner", msg);
////                setToast(msg);
//                baseView.showBannerError(msg);
//            }
//        });
//    }
//
//    public void getCommercialInfo(String mSavedMarketNum, String mSavedStallNum,
//                                  int mTemplateCode, String mSavedDeviceId, int templateType) {
//        HashMap params = new HashMap();
//        try {
////             .addParams("farm", marketNum)
////                .addParams("id", stallNum)
////                .addParams("config", String.valueOf(templateCode))
////                .addParams("device_id", deviceId);
//            params.put("device_id", mSavedDeviceId);
//            params.put("farm", mSavedMarketNum);
//            params.put("config", String.valueOf(mTemplateCode));
//            params.put("id", mSavedStallNum);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
////        MediaType.parse("application/json; charset=utf-8")
////        RequestBody requestBody = RequestBody.create( MediaType.parse("application/x-www-form-urlencoded"), params.toString());
//        addDisposable(apiServer.getCommercialInfo(params), new BaseObserver<ResponseBody>(baseView) {
//            @Override
//            public void onSuccess(String o) {
//                //模板类型
//                baseView.getCommercialInfo(o, templateType);
//            }
//
//            @Override
//            public void onError(String msg) {
//                Log.i("getCommercialInfo", msg);
//                setToast(msg);
//                baseView.showCommInfoError(msg, templateType);
//            }
//        });
//    }
//
//    /**
//     * 获取密码
//     */
//    public void getPassword(String deviceId) {
//        String TAG = "getPassword";
//        try {
//
//
//            HashMap params = new HashMap();
//            try {
//                params.put("device_id", deviceId);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
////            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), params.toString());
//            addDisposable(apiServer.getPassword(params), new BaseObserver<ResponseBody>(baseView) {
//                @Override
//                public void onSuccess(String response) {
//                    baseView.getPassword(response);
//                }
//
//                @Override
//                public void onError(String msg) {
//                    Log.i("pwd", msg);
//                    setToast(msg);
//                    baseView.showPwdError(msg);
//                }
//            });
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 获取模板
//     */
//    public void getTemplateCode(String deviceId) {
//
//        HashMap params = new HashMap();
//        try {
//            params.put("device_id", deviceId);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
////        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), params.toString());
//        addDisposable(apiServer.getTemplateCode(params), new BaseObserver<ResponseBody>(baseView) {
//            @Override
//            public void onSuccess(String response) {
//                baseView.getTemplateCode(response);
//            }
//
//            @Override
//            public void onError(String msg) {
//                Log.i("getCommercialInfoElec", msg);
//                setToast(msg);
//                baseView.showTemplateError(msg);
//            }
//        });
//
//
//    }
//
//    public void getLogoTitleData(String deviceId) {
//        String TAG = "logoTitle";
////        try {
////            if (!CacheUtils.getBoolean(AppConstants.Cache.ACCESS_LOGO_TITLE)) {
////                LogUtils.e(TAG, "GET_LOGO_TITLE_CONTENT--->device_id=" + deviceId);
////                getStringCallback(ApiConstants.GET_LOGO_TITLE_CONTENT_POST, OkHttpUtils.post().addParams("device_id", deviceId)
////                        , AppConstants.DefaultSetting.SET_CONNECT_TIMEOUT_CYCLE_SHORT, AppConstants.EventCode.GET_LOGO_TITLE_FAIL, AppConstants.EventCode.GET_LOGO_TITLE_SUCCESS, true);
////            }
//
//        HashMap params = new HashMap();
//        try {
//            params.put("device_id", deviceId);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
////        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), params.toString());
//        addDisposable(apiServer.getLogoTitleData(params), new BaseObserver<ResponseBody>(baseView) {
//            @Override
//            public void onSuccess(String response) {
//                baseView.getLogoTitleData(response);
//            }
//
//            @Override
//            public void onError(String msg) {
//                Log.i("getLogoTitleData", msg);
//                setToast(msg);
//                baseView.showLogoTitleError(msg);
//            }
//        });
//
//
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//    }
//
//
////    public void setTimeSwitch(String deviceId, String marketId) {
////        String TAG = "setTimeSwitch";
////
////
////        HashMap params = new HashMap();
////        try {
////            params.put("device_id", deviceId);
////            params.put("market_id", marketId);
////
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//////        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), params.toString());
////        addDisposable(apiServer.setTimeSwitch(params), new BaseObserver<ResponseBody>(baseView) {
////            @Override
////            public void onSuccess(String response) {
////                baseView.setTimeSwitch(response);
////            }
////
////            @Override
////            public void onError(String msg) {
////                Log.i("getLogoTitleData", msg);
////                setToast(msg);
////                baseView.showTimeSwitchError(msg);
////            }
////        });
////
////
////    }
//
//
//    public void update(String deviceId, String marketId) {
//        String TAG = "UPDATE";
////            if (!CacheUtils.getBoolean(AppConstants.Cache.ACCESS_UPDATE) || accessIsEffect) {
////                LogUtils.e(TAG, "SOFTWARE_UPDATE--->device_id=" + deviceId + "&market_id=" + marketId + "&type=" + AppConstants.DeviceType.COMMERCIAL_SCREEN);
////                // 软件升级功能
////                getStringCallback(ApiConstants.SOFTWARE_UPDATE_POST, OkHttpUtils.post()
////                                .addParams("device_id", deviceId)
////                                .addParams("market_id", marketId)
////                                .addParams("type", AppConstants.DeviceType.COMMERCIAL_SCREEN)
////                        , AppConstants.DefaultSetting.SET_CONNECT_TIMEOUT_CYCLE_SHORT, AppConstants.EventCode.UPDATE_FAIL, AppConstants.EventCode.UPDATE_SUCCESS, true);
////            }
//
//
//        HashMap params = new HashMap();
//        try {
//            params.put("device_id", deviceId);
//            params.put("market_id", marketId);
//            params.put("type", AppConstants.DeviceType.COMMERCIAL_SCREEN);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
////        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), params.toString());
//        addDisposable(apiServer.update(params), new BaseObserver<ResponseBody>(baseView) {
//            @Override
//            public void onSuccess(String response) {
//                baseView.update(response);
//            }
//
//            @Override
//            public void onError(String msg) {
//                Log.i("getLogoTitleData", msg);
//                setToast(msg);
//                baseView.showUpdateError(msg);
//            }
//        });
//
//
//    }
//
////    public void getMarketInfo(String marketNum, String stallNum, int templateCode, String deviceId) {
////
////        HashMap params = new HashMap();
////        try {
////            params.put("device_id", deviceId);
////            params.put("farm", marketNum);
////            params.put("config", String.valueOf(templateCode));
////            params.put("id", stallNum);
////
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//////        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), params.toString());
////        addDisposable(apiServer.getMarketInfo(params), new BaseObserver<ResponseBody>(baseView) {
////            @Override
////            public void onSuccess(String response) {
////                baseView.getMarketInfo(response);
////            }
////
////            @Override
////            public void onError(String msg) {
////                Log.i("getMarketInfo", msg);
////                setToast(msg);
////                baseView.showMarketError(msg);
////            }
////        });
////
////    }
//
//
//    /**
//     * 晨曦 模板
//     */
////    public void getCommercialInfoCx(String code, String deviceId) {
////        HashMap params = new HashMap();
////        try {
////            params.put("market_id", code);
////            params.put("device_id", deviceId);
////
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//////        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), params.toString());
////        addDisposable(apiServer.CommercialInfoCx(params), new BaseObserver<ResponseBody>(baseView) {
////            @Override
////            public void onSuccess(String response) {
////                baseView.getCommercialInfoCx(response);
////            }
////
////            @Override
////            public void onError(String msg) {
////                Log.i("getCommercialInfoCx", msg);
////                setToast(msg);
////                baseView.showCxError(msg);
////            }
////        });
////    }
//
//    public void getRealTimeVolume(String marketNum, String stallNum, int templateCode, String deviceId) {
//
//        HashMap params = new HashMap();
//
//        try {
//            params.put("device_id", deviceId);
//            params.put("farm", marketNum);
//            params.put("config", String.valueOf(templateCode));
//            params.put("id", stallNum);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
////        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), params.toString());
//        addDisposable(apiServer.getRealTimeVolume(params), new BaseObserver<ResponseBody>(baseView) {
//            @Override
//            public void onSuccess(String response) {
//                baseView.getRealTimeVolume(response);
//            }
//
//            @Override
//            public void onError(String msg) {
//                Log.i("getRealTimeVolume", msg);
//                setToast(msg);
//                baseView.showRealTimeError(msg);
//            }
//        });
//    }
//
//    public void getVolumeData(String marketNum, String stallNum, int templateCode, String deviceId, String merchantId) {
//
//        HashMap params = new HashMap();
//        try {
//            params.put("device_id", deviceId);
//            params.put("farm", marketNum);
//            params.put("config", String.valueOf(templateCode));
//            params.put("id", stallNum);
//            params.put("merchant_id", merchantId);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
////        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), params.toString());
//        addDisposable(apiServer.getVolumeData(params), new BaseObserver<ResponseBody>(baseView) {
//            @Override
//            public void onSuccess(String response) {
//                baseView.getVolumeData(response);
//            }
//
//            @Override
//            public void onError(String msg) {
//                Log.i("getVolumeData", msg);
//                setToast(msg);
//                baseView.showVolumeDataError(msg);
//            }
//        });
//    }
//
//    public void getMerchantPayConfig(String marketId, int templateCode, String deviceId, String merchantId) {
//        HashMap params = new HashMap();
//        try {
//            params.put("market_id", marketId);
//            params.put("device_id", deviceId);
//            params.put("config", String.valueOf(templateCode));
//            params.put("merchant_id", merchantId);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
////        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), params.toString());
//        addDisposable(apiServer.getMerchantPayConfig(params), new BaseObserver<ResponseBody>(baseView) {
//            @Override
//            public void onSuccess(String response) {
//                baseView.getMerchantPayConfig(response);
//            }
//
//            @Override
//            public void onError(String msg) {
//                Log.i("getVolumeData", msg);
//                setToast(msg);
//                baseView.showMerchantPayError(msg);
//            }
//        });
//
//    }
//}
