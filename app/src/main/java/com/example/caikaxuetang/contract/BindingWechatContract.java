package com.example.caikaxuetang.contract;

import android.annotation.SuppressLint;


import com.example.caikaxuetang.responses.BindingWechatResponse;
import com.example.caikaxuetang.responses.LoginWxResponse;
import com.llw.mvplibrary.base.BasePresenter;
import com.llw.mvplibrary.base.BaseView;
import com.llw.mvplibrary.network.NetworkApi;
import com.llw.mvplibrary.network.observer.BaseObserver;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.RequestBody;


/**
 * 将V与M订阅起来
 * @author llw
 */
public class BindingWechatContract {

    public static class BindingWechatPresenter extends BasePresenter<BindingWechatView> {

        @SuppressLint("CheckResult")
        public void getWecahtBinding(String appId,String code){
            ApiService service  = NetworkApi.createService(ApiService.class);
            JSONObject json = new JSONObject();
            try {
                json.put("appId", appId);
                json.put("code", code);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody body = FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());
            service.getBindindWecahtResponse(body).compose(NetworkApi.applySchedulers(new BaseObserver<BindingWechatResponse>() {
                @Override
                public void onSuccess(BindingWechatResponse wallPaperResponse) {
                    if (getView() != null) {
                        getView().getWallPaper(wallPaperResponse);
                    }
                }

                @Override
                public void onFailure(Throwable e) {
                    if (getView() != null) {
                        getView().getFailed(e);
                    }
                }
            }));
        }

        @SuppressLint("CheckResult")
        public void getLoginWecahtResponse(String appId, String code) {
            ApiService service  = NetworkApi.createService(ApiService.class);
            JSONObject json = new JSONObject();
            try {
                json.put("appId", appId);
                json.put("code", code);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody body = FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());
            service.getLoginWecahtResponse(body).compose(NetworkApi.applySchedulers(new BaseObserver<LoginWxResponse>() {
                @Override
                public void onSuccess(LoginWxResponse wallPaperResponse) {
                    if (getView() != null) {
                        getView().getWxLogin(wallPaperResponse);
                    }
                }

                @Override
                public void onFailure(Throwable e) {
                    if (getView() != null) {
                        getView().getFailed(e);
                    }
                }
            }));
        }
    }

    public interface BindingWechatView extends BaseView {
        void getWallPaper(BindingWechatResponse wallPaperResponse);
        void getWxLogin(LoginWxResponse loginWxResponse);
    }
}
