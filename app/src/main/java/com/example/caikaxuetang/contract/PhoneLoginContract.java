package com.example.caikaxuetang.contract;

import android.annotation.SuppressLint;

import com.example.caikaxuetang.responses.GroupInfo;
import com.example.caikaxuetang.responses.GroupNoticeBean;
import com.example.caikaxuetang.responses.PhoneLoginResponse;
import com.example.caikaxuetang.responses.WallPaperResponse;
import com.llw.mvplibrary.base.BasePresenter;
import com.llw.mvplibrary.base.BaseView;
import com.llw.mvplibrary.network.NetworkApi;
import com.llw.mvplibrary.network.observer.BaseObserver;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.RequestBody;


/**
 * 将V与M订阅起来
 *
 * @author llw
 */
public class PhoneLoginContract {

    public static class MainPresenter extends BasePresenter<IMainView> {



        @SuppressLint("CheckResult")
        public void getPhoneLogin(String mobile, String password, Integer loginType) {
            ApiService service = NetworkApi.createService(ApiService.class);
            JSONObject json = new JSONObject();
            try {
                json.put("mobile", mobile);
                json.put("password", password);
                json.put("loginType", loginType);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody body = FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());

            service.getPhoneLogin(body).compose(NetworkApi.applySchedulers(new BaseObserver<PhoneLoginResponse>() {
                @Override
                public void onSuccess(PhoneLoginResponse wallPaperResponse) {
                    if (getView() != null) {
                        getView().getPhoneLogin(wallPaperResponse);
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

    public interface IMainView extends BaseView {
        void getPhoneLogin(PhoneLoginResponse groupInfo);
    }
}
