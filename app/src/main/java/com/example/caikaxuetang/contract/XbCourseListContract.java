package com.example.caikaxuetang.contract;

import android.annotation.SuppressLint;

import com.example.caikaxuetang.responses.CommitSuccessResponse;
import com.example.caikaxuetang.responses.DkCourseListResponse;
import com.example.caikaxuetang.responses.NoteDetailResponse;
import com.example.caikaxuetang.responses.XbCourseListResponse;
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
public class XbCourseListContract {

    public static class XbCourseListPresenter extends BasePresenter<XbCourseListView> {

        @SuppressLint("CheckResult")
        public void getXbCourseList(String courseId) {
            ApiService service = NetworkApi.createService(ApiService.class);
            JSONObject json = new JSONObject();
            try {
                json.put("courseId", courseId);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody body = FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());
            service.getXbCourseList(body).compose(NetworkApi.applySchedulers(new BaseObserver<XbCourseListResponse>() {
                @Override
                public void onSuccess(XbCourseListResponse wallPaperResponse) {
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
        public void keepNote(String sectionId, String edContent,String noteId) {
            ApiService service = NetworkApi.createService(ApiService.class);
            JSONObject json = new JSONObject();
            try {
                json.put("sectionId", sectionId);
                json.put("noteContent", edContent);
                json.put("noteId", noteId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody body = FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());
            service.getKeepNote(body).compose(NetworkApi.applySchedulers(new BaseObserver<CommitSuccessResponse>() {
                @Override
                public void onSuccess(CommitSuccessResponse wallPaperResponse) {
                    if (getView() != null) {
                        getView().getKeepNote(wallPaperResponse);
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
        public void noteDetail(String sectionId) {
            ApiService service = NetworkApi.createService(ApiService.class);
            JSONObject json = new JSONObject();
            try {
                json.put("sectionId", sectionId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody body = FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());
            service.getNoteDetail(body).compose(NetworkApi.applySchedulers(new BaseObserver<NoteDetailResponse>() {
                @Override
                public void onSuccess(NoteDetailResponse wallPaperResponse) {
                    if (getView() != null) {
                        getView().getNoteDetail(wallPaperResponse);
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
        public void getDkCourseList(String courseId) {
            ApiService service = NetworkApi.createService(ApiService.class);
            JSONObject json = new JSONObject();
            try {
                json.put("courseId", courseId);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody body = FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());
            service.getDkCourseList(body).compose(NetworkApi.applySchedulers(new BaseObserver<DkCourseListResponse>() {
                @Override
                public void onSuccess(DkCourseListResponse wallPaperResponse) {
                    if (getView() != null) {
                        getView().getDkCourseList(wallPaperResponse);
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

    public interface XbCourseListView extends BaseView {
        void getWallPaper(XbCourseListResponse wallPaperResponse);

        void getDkCourseList(DkCourseListResponse wallPaperResponse);

        void getKeepNote(CommitSuccessResponse wallPaperResponse);
        void getNoteDetail(NoteDetailResponse wallPaperResponse);
    }
}
