package com.example.caikaxuetang.contract;

import android.annotation.SuppressLint;

import com.example.caikaxuetang.responses.GroupInfo;
import com.example.caikaxuetang.responses.GroupNoticeBean;
import com.example.caikaxuetang.responses.HetongResponse;
import com.example.caikaxuetang.responses.MyClassResponse;
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
public class HetongContract {

    public static class MainPresenter extends BasePresenter<IMainView> {

        @SuppressLint("CheckResult")
        public void getHetong() {
            ApiService service = NetworkApi.createService(ApiService.class);
            JSONObject json = new JSONObject();
            RequestBody body = FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());

            service.getHetong(body).compose(NetworkApi.applySchedulers(new BaseObserver<HetongResponse>() {
                @Override
                public void onSuccess(HetongResponse wallPaperResponse) {
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
        public void getMyClass() {
            ApiService service = NetworkApi.createService(ApiService.class);
            JSONObject json = new JSONObject();
            RequestBody body = FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());

            service.getMyclass(body).compose(NetworkApi.applySchedulers(new BaseObserver<MyClassResponse>() {
                @Override
                public void onSuccess(MyClassResponse wallPaperResponse) {
                    if (getView() != null) {
                        getView().getMyClass(wallPaperResponse);
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
        void getWallPaper(HetongResponse wallPaperResponse);
        void getMyClass(MyClassResponse wallPaperResponse);
    }
}
