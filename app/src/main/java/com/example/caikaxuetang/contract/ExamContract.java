package com.example.caikaxuetang.contract;

import android.annotation.SuppressLint;

import com.example.caikaxuetang.responses.CommitAnswerResponse;
import com.example.caikaxuetang.responses.CommitExamResponse;
import com.example.caikaxuetang.responses.CommitSuccessResponse;
import com.example.caikaxuetang.responses.ExamInfoResponse;
import com.example.caikaxuetang.responses.ExamTaskInfoResponse;
import com.example.caikaxuetang.responses.GroupInfo;
import com.example.caikaxuetang.responses.GroupNoticeBean;
import com.example.caikaxuetang.responses.ResetExamResponse;
import com.example.caikaxuetang.responses.WallPaperResponse;
import com.google.gson.Gson;
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
public class ExamContract {

    public static class ExamPresenter extends BasePresenter<ExamView> {

        @SuppressLint("CheckResult")
        public void getWallPaper(String chapterId) {
            ApiService service = NetworkApi.createService(ApiService.class);
            JSONObject json = new JSONObject();
            try {
                json.put("chapterId", chapterId);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody body = FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());

            service.getExamInfo(body).compose(NetworkApi.applySchedulers(new BaseObserver<ExamInfoResponse>() {
                @Override
                public void onSuccess(ExamInfoResponse wallPaperResponse) {
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
        public void getResetExam(String chapterId) {
            ApiService service = NetworkApi.createService(ApiService.class);
            JSONObject json = new JSONObject();
            try {
                json.put("chapterId", chapterId);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody body = FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());

            service.getResetExam(body).compose(NetworkApi.applySchedulers(new BaseObserver<ResetExamResponse>() {
                @Override
                public void onSuccess(ResetExamResponse wallPaperResponse) {
                    if (getView() != null) {
                        getView().getResetExam(wallPaperResponse);
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
        public void getExamTaskInfo(String taskId) {
            ApiService service = NetworkApi.createService(ApiService.class);
            JSONObject json = new JSONObject();
            try {
                json.put("taskId", taskId);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody body = FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());

            service.getExamTaskInfo(body).compose(NetworkApi.applySchedulers(new BaseObserver<ExamTaskInfoResponse>() {
                @Override
                public void onSuccess(ExamTaskInfoResponse wallPaperResponse) {
                    if (getView() != null) {
                        getView().getExamTaskInfo(wallPaperResponse);
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
        public void getCommitExam(CommitExamResponse commitAnswerResponse) {
            ApiService service = NetworkApi.createService(ApiService.class);

            String s = new Gson().toJson(commitAnswerResponse);
            RequestBody body = FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), s);

            service.getSubmitExam(body).compose(NetworkApi.applySchedulers(new BaseObserver<CommitSuccessResponse>() {
                @Override
                public void onSuccess(CommitSuccessResponse wallPaperResponse) {
                    if (getView() != null) {
                        getView().getCommitExam(wallPaperResponse);
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

    public interface ExamView extends BaseView {
        void getWallPaper(ExamInfoResponse wallPaperResponse);

        void getExamTaskInfo(ExamTaskInfoResponse wallPaperResponse);

        void getCommitExam(CommitSuccessResponse wallPaperResponse);
        void getResetExam(ResetExamResponse wallPaperResponse);
    }
}
