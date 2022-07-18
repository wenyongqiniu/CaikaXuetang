package com.example.caikaxuetang.contract;

import android.annotation.SuppressLint;

import com.example.caikaxuetang.responses.GroupInfo;
import com.example.caikaxuetang.responses.GroupNoticeBean;
import com.example.caikaxuetang.responses.GroupNumberResponse;
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
public class GroupInfoContract {

    public static class MainPresenter extends BasePresenter<IMainView> {

        @SuppressLint("CheckResult")
        public void getWallPaper(String groupId,int flag) {
            ApiService service = NetworkApi.createService(ApiService.class);
            JSONObject json = new JSONObject();

            try {
                json.put("groupId",groupId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody body = FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());
           /* HashMap<String, Object> stringStringHashMap = new HashMap<>();
            stringStringHashMap.put("groupId",groupId);*/


            service.groupList(body).compose(NetworkApi.applySchedulers(new BaseObserver<GroupNumberResponse>() {
                @Override
                public void onSuccess(GroupNumberResponse wallPaperResponse) {
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
        public void getGroupInfo(String groupId) {
            ApiService service = NetworkApi.createService(ApiService.class);
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("groupId",groupId);
            service.groupInfomation(hashMap).compose(NetworkApi.applySchedulers(new BaseObserver<GroupInfo>() {
                @Override
                public void onSuccess(GroupInfo wallPaperResponse) {
                    if (getView() != null) {
                        getView().getGroupInfo(wallPaperResponse);
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
        public void getNotice(String groupId) {
            ApiService service = NetworkApi.createService(ApiService.class);
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("groupId",groupId);
            service.groupNotice(hashMap).compose(NetworkApi.applySchedulers(new BaseObserver<GroupNoticeBean>() {
                @Override
                public void onSuccess(GroupNoticeBean wallPaperResponse) {
                    if (getView() != null) {
                        getView().getGroupNotice(wallPaperResponse);
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
        void getWallPaper(GroupNumberResponse wallPaperResponse);
        void getGroupInfo(GroupInfo groupInfo);
        void getGroupNotice(GroupNoticeBean groupInfo);

    }
}
