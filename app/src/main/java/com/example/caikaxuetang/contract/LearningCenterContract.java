package com.example.caikaxuetang.contract;

import android.annotation.SuppressLint;

import com.example.caikaxuetang.responses.LearningCenterResponse;
import com.llw.mvplibrary.base.BasePresenter;
import com.llw.mvplibrary.base.BaseView;
import com.llw.mvplibrary.network.NetworkApi;
import com.llw.mvplibrary.network.observer.BaseObserver;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.RequestBody;


/**
 * 将V与M订阅起来
 * @author llw
 */
public class LearningCenterContract {

    public static class LearningCenterPresenter extends BasePresenter<LearningCenterView> {

        @SuppressLint("CheckResult")
        public void getLearningCenterPaper(){
            ApiService service  = NetworkApi.createService(ApiService.class);
            JSONObject json = new JSONObject();

            RequestBody body = FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());

            service.getLearningCenter(body).compose(NetworkApi.applySchedulers(new BaseObserver<LearningCenterResponse>() {
                @Override
                public void onSuccess(LearningCenterResponse wallPaperResponse) {
                    if (getView() != null) {
                        getView().getLearningCenter(wallPaperResponse);
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

    public interface LearningCenterView extends BaseView {
        void getLearningCenter(LearningCenterResponse wallPaperResponse);
    }
}
