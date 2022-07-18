package com.example.caikaxuetang.contract;

import android.annotation.SuppressLint;


import com.example.caikaxuetang.responses.AddressResponse;
import com.example.caikaxuetang.responses.ConfirmCourseTineResponse;
import com.example.caikaxuetang.responses.ContractResponse;
import com.example.caikaxuetang.responses.CourseTimeResponse;
import com.example.caikaxuetang.responses.DakeStepResponse;
import com.example.caikaxuetang.responses.SealContractResponse;
import com.example.caikaxuetang.responses.SubmitAddressResponse;
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
public class DakeStepContract {

    public static class DakeStepPresenter extends BasePresenter<DakeStepView> {

        @SuppressLint("CheckResult")
        public void getDakeStep(String packageId) {
            ApiService service = NetworkApi.createService(ApiService.class);
            JSONObject json = new JSONObject();
            try {
                json.put("packageId", packageId);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody body = FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());
            service.getDakeStep(body).compose(NetworkApi.applySchedulers(new BaseObserver<DakeStepResponse>() {
                @Override
                public void onSuccess(DakeStepResponse wallPaperResponse) {
                    if (getView() != null) {
                        getView().getDakeStep(wallPaperResponse);
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
        public void submitAddress(String addressId,String packageId) {
            ApiService service = NetworkApi.createService(ApiService.class);
            JSONObject json = new JSONObject();
            try {
                json.put("addressId", addressId);
                json.put("packageId", packageId);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody body = FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());
            service.getSubmitAddress(body).compose(NetworkApi.applySchedulers(new BaseObserver<SubmitAddressResponse>() {
                @Override
                public void onSuccess(SubmitAddressResponse wallPaperResponse) {
                    if (getView() != null) {
                        getView().getSubmitAddress(wallPaperResponse);
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
        public void getContract(String packageId) {
            ApiService service = NetworkApi.createService(ApiService.class);
            JSONObject json = new JSONObject();
            try {
                json.put("packageId", packageId);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody body = FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());
            service.getContract(body).compose(NetworkApi.applySchedulers(new BaseObserver<ContractResponse>() {
                @Override
                public void onSuccess(ContractResponse wallPaperResponse) {
                    if (getView() != null) {
                        getView().getContract(wallPaperResponse);
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
        public void getCourseTime(String packageId) {
            ApiService service = NetworkApi.createService(ApiService.class);
            JSONObject json = new JSONObject();
            try {
                json.put("packageId", packageId);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody body = FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());
            service.getCourseTime(body).compose(NetworkApi.applySchedulers(new BaseObserver<CourseTimeResponse>() {
                @Override
                public void onSuccess(CourseTimeResponse wallPaperResponse) {
                    if (getView() != null) {
                        getView().getCourseTime(wallPaperResponse);
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
        public void getAddress() {
            ApiService service = NetworkApi.createService(ApiService.class);
            JSONObject json = new JSONObject();

            RequestBody body = FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());
            service.getAddress(body).compose(NetworkApi.applySchedulers(new BaseObserver<AddressResponse>() {
                @Override
                public void onSuccess(AddressResponse wallPaperResponse) {
                    if (getView() != null) {
                        getView().getAddress(wallPaperResponse);
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
        public void getSelectTime(String classId) {
            ApiService service = NetworkApi.createService(ApiService.class);
            JSONObject json = new JSONObject();
            try {
                json.put("classId",classId);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            RequestBody body = FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());
            service.getSelecttime(body).compose(NetworkApi.applySchedulers(new BaseObserver<ConfirmCourseTineResponse>() {
                @Override
                public void onSuccess(ConfirmCourseTineResponse wallPaperResponse) {
                    if (getView() != null) {
                        getView().getConfirmTime(wallPaperResponse);
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
        public void getSealContract(String contractId, String realName, String certNo, String mobile, String address) {
            ApiService service = NetworkApi.createService(ApiService.class);
            JSONObject json = new JSONObject();
            try {
                json.put("contractId", contractId);
                json.put("realName", realName);
                json.put("certNo", certNo);
                json.put("mobile", mobile);
                json.put("address", address);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody body = FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());
            service.getSealContract(body).compose(NetworkApi.applySchedulers(new BaseObserver<SealContractResponse>() {
                @Override
                public void onSuccess(SealContractResponse wallPaperResponse) {
                    if (getView() != null) {
                        getView().getSealContract(wallPaperResponse);
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

    public interface DakeStepView extends BaseView {
        void getDakeStep(DakeStepResponse wallPaperResponse);

        void getContract(ContractResponse wallPaperResponse);

        void getSealContract(SealContractResponse wallPaperResponse);

        void getAddress(AddressResponse wallPaperResponse);
        void getCourseTime(CourseTimeResponse wallPaperResponse);
        void getConfirmTime(ConfirmCourseTineResponse wallPaperResponse);
        void getSubmitAddress(SubmitAddressResponse wallPaperResponse);
    }
}
