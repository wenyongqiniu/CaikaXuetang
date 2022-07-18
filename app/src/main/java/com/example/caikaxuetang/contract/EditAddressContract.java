package com.example.caikaxuetang.contract;

import android.annotation.SuppressLint;


import com.example.caikaxuetang.responses.AddAddressResponse;
import com.example.caikaxuetang.responses.AddressListResponse;
import com.example.caikaxuetang.responses.CommitSuccessResponse;
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
public class EditAddressContract {

    public static class EditAddressPresenter extends BasePresenter<EditAddressView> {

        @SuppressLint("CheckResult")
        public void getEditAddress(String realName,String prove,String city,String area,String addressDetail,String mobile,int isDefault){
            ApiService service  = NetworkApi.createService(ApiService.class);
            JSONObject json = new JSONObject();
            try {
                json.put("realName",realName);
                json.put("prove",prove);
                json.put("city",city);
                json.put("area",area);
                json.put("addressDetail",addressDetail);
                json.put("mobile",mobile);
                json.put("isDefault",isDefault);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            RequestBody body = FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());

            service.getEditAddress(body).compose(NetworkApi.applySchedulers(new BaseObserver<AddAddressResponse>() {
                @Override
                public void onSuccess(AddAddressResponse wallPaperResponse) {
                    if (getView() != null) {
                        getView().getEditAddress(wallPaperResponse);
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
        public void getChangeAddress(String id,String realName,String prove,String city,String area,String addressDetail,String mobile,int isDefault,int directUse,String packageId){
            ApiService service  = NetworkApi.createService(ApiService.class);
            JSONObject json = new JSONObject();
            try {
                json.put("id",id);
                json.put("realName",realName);
                json.put("prove",prove);
                json.put("city",city);
                json.put("area",area);
                json.put("addressDetail",addressDetail);
                json.put("mobile",mobile);
                json.put("isDefault",isDefault);
                json.put("directUse",directUse);
                json.put("packageId",packageId);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            RequestBody body = FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());

            service.getChangeAddress(body).compose(NetworkApi.applySchedulers(new BaseObserver<AddAddressResponse>() {
                @Override
                public void onSuccess(AddAddressResponse wallPaperResponse) {
                    if (getView() != null) {
                        getView().getEditAddress(wallPaperResponse);
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
        public void getAddressList(){
            ApiService service  = NetworkApi.createService(ApiService.class);
            JSONObject json = new JSONObject();

            RequestBody body = FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());

            service.getAddressList(body).compose(NetworkApi.applySchedulers(new BaseObserver<AddressListResponse>() {
                @Override
                public void onSuccess(AddressListResponse wallPaperResponse) {
                    if (getView() != null) {
                        getView().getAddressList(wallPaperResponse);
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
        public void getDeleteAddress(String id){
            ApiService service  = NetworkApi.createService(ApiService.class);
            JSONObject json = new JSONObject();
            try {
                json.put("id",id);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            RequestBody body = FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());
            service.getDeleteAddress(body).compose(NetworkApi.applySchedulers(new BaseObserver<CommitSuccessResponse>() {
                @Override
                public void onSuccess(CommitSuccessResponse wallPaperResponse) {
                    if (getView() != null) {
                        getView().getDeleteAddress(wallPaperResponse);
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

    public interface EditAddressView extends BaseView {
        void getEditAddress(AddAddressResponse wallPaperResponse);
        void getAddressList(AddressListResponse wallPaperResponse);
        void getDeleteAddress(CommitSuccessResponse wallPaperResponse);
    }
}
