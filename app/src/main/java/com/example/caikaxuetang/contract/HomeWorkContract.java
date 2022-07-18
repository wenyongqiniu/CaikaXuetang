package com.example.caikaxuetang.contract;

import android.annotation.SuppressLint;

import com.example.caikaxuetang.responses.CommitAnswerResponse;
import com.example.caikaxuetang.responses.CommitSuccessResponse;
import com.example.caikaxuetang.responses.HomeWorkResponse;
import com.example.caikaxuetang.responses.SectionDetailResponse;
import com.google.gson.Gson;
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
 *
 * @author llw
 */
public class HomeWorkContract {

    public static class HomeWorkPresenter extends BasePresenter<HomeWorkView> {

        @SuppressLint("CheckResult")
        public void getHomeWork(String sectionId) {
            ApiService service = NetworkApi.createService(ApiService.class);
            JSONObject json = new JSONObject();
            try {
                json.put("sectionId", sectionId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody body = FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());
            service.getHomeWork(body).compose(NetworkApi.applySchedulers(new BaseObserver<HomeWorkResponse>() {
                @Override
                public void onSuccess(HomeWorkResponse wallPaperResponse) {
                    if (getView() != null) {
                        getView().getHomeWork(wallPaperResponse);
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
        public void getExamAnswer(String chapterId) {
            ApiService service = NetworkApi.createService(ApiService.class);
            JSONObject json = new JSONObject();
            try {
                json.put("chapterId", chapterId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody body = FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());
            service.getExamAnswer(body).compose(NetworkApi.applySchedulers(new BaseObserver<HomeWorkResponse>() {
                @Override
                public void onSuccess(HomeWorkResponse wallPaperResponse) {
                    if (getView() != null) {
                        getView().getHomeWork(wallPaperResponse);
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
        public void commitAnswer(CommitAnswerResponse commitAnswerResponse) {
            ApiService service = NetworkApi.createService(ApiService.class);
            String json = new Gson().toJson(commitAnswerResponse);
            RequestBody body = FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
            service.getSubmitAnswer(body).compose(NetworkApi.applySchedulers(new BaseObserver<CommitSuccessResponse>() {
                @Override
                public void onSuccess(CommitSuccessResponse wallPaperResponse) {
                    if (getView() != null) {
                        getView().getSubmitAnswer(wallPaperResponse);
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

    public interface HomeWorkView extends BaseView {
        void getHomeWork(HomeWorkResponse wallPaperResponse);

        void getSubmitAnswer(CommitSuccessResponse wallPaperResponse);

    }
}
