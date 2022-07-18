package com.llw.mvplibrary.network.interceptor;


import android.os.Build;

import androidx.annotation.RequiresApi;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.llw.mvplibrary.BaseApplication;
import com.llw.mvplibrary.network.INetworkRequiredInfo;
import com.llw.mvplibrary.network.utils.DateUtil;
import com.llw.mvplibrary.network.utils.SpUtils;

import java.io.IOException;
import java.util.Base64;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.http.HTTP;

/**
 * 请求拦截器
 *
 * @author llw
 */
public class RequestInterceptor implements Interceptor {
    /**
     * 网络请求信息
     */
    private INetworkRequiredInfo iNetworkRequiredInfo;

    public RequestInterceptor(INetworkRequiredInfo iNetworkRequiredInfo) {
        this.iNetworkRequiredInfo = iNetworkRequiredInfo;
    }

    /**
     * 拦截
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public Response intercept(Chain chain) throws IOException {

        String nowDateTime = DateUtil.getNowDateTime();
        //构建器
        Request.Builder builder = chain.request().newBuilder();
        //添加token
        builder.addHeader("token", BaseApplication.token);
        //添加使用环境
        builder.addHeader("os", "android");
        //添加版本号
        builder.addHeader("appVersionCode", this.iNetworkRequiredInfo.getAppVersionCode());
        //添加版本名
        builder.addHeader("appVersionName", this.iNetworkRequiredInfo.getAppVersionName());
        //添加日期时间
        builder.addHeader("datetime", nowDateTime);
        builder.addHeader("Content-Type", "application/json");
        //返回
        return chain.proceed(builder.build());

    }
}
