package com.llw.mvplibrary.network.interceptor;


import android.content.Intent;
import android.widget.Toast;

import com.llw.mvplibrary.BaseApplication;
import com.llw.mvplibrary.network.utils.KLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.llw.mvplibrary.BaseApplication.getActivityManager;
import static com.llw.mvplibrary.BaseApplication.getContext;

/**
 * 返回拦截器(响应拦截器)
 *
 * @author llw
 */
public class ResponseInterceptor implements Interceptor {

    private static final String TAG = "ResponseInterceptor";

    /**
     * 拦截
     */
    @Override
    public Response intercept(Chain chain) throws IOException {
        long requestTime = System.currentTimeMillis();
        Response response = chain.proceed(chain.request());

        ResponseBody responseBody = response.peekBody(1024 * 1024);
        String string = responseBody.string();

        try {
            JSONObject jsonObject = new JSONObject(string);
            int code = (int) jsonObject.get("code");
            BaseApplication.code = code;
            return response;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        KLog.i(TAG, "requestSpendTime=" + (System.currentTimeMillis() - requestTime) + "ms");
        return null;
    }
}
