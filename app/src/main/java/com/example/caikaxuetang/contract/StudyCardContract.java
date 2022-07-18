package com.example.caikaxuetang.contract;

import android.annotation.SuppressLint;

import com.example.caikaxuetang.responses.CommitExamResponse;
import com.example.caikaxuetang.responses.CommitSuccessResponse;
import com.example.caikaxuetang.responses.ExamInfoResponse;
import com.example.caikaxuetang.responses.ExamTaskInfoResponse;
import com.example.caikaxuetang.responses.ResetExamResponse;
import com.example.caikaxuetang.responses.StudyCardResponse;
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
public class StudyCardContract {

    public static class StudyCardPresenter extends BasePresenter<ExamView> {

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

            service.getStudyCard(body).compose(NetworkApi.applySchedulers(new BaseObserver<StudyCardResponse>() {
                @Override
                public void onSuccess(StudyCardResponse wallPaperResponse) {
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

    }

    public interface ExamView extends BaseView {
        void getWallPaper(StudyCardResponse wallPaperResponse);

    }
}
