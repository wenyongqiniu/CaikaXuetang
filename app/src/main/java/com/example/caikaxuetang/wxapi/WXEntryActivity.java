package com.example.caikaxuetang.wxapi;


import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


import com.example.caikaxuetang.LoginActivity;
import com.example.caikaxuetang.MainActivity;
import com.example.caikaxuetang.MyApplication;
import com.example.caikaxuetang.PhoneLoginActivity;
import com.example.caikaxuetang.R;
import com.example.caikaxuetang.contract.BindingWechatContract;
import com.example.caikaxuetang.responses.BindingWechatResponse;
import com.example.caikaxuetang.responses.LoginWxResponse;
import com.example.caikaxuetang.utils.CustomerToastUtils;
import com.example.caikaxuetang.utils.ToastUtils;
import com.llw.mvplibrary.BaseApplication;
import com.llw.mvplibrary.mvp.MvpActivity;
import com.llw.mvplibrary.network.utils.SpUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

import static com.example.caikaxuetang.MyApplication.getActivityManager;


public class WXEntryActivity extends MvpActivity<BindingWechatContract.BindingWechatPresenter> implements BindingWechatContract.BindingWechatView, IWXAPIEventHandler {


    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        //登录回调
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                String code = ((SendAuth.Resp) baseResp).code;
                if (((SendAuth.Resp) baseResp).state.equals("wechat_binding")) {
                    bindingWx(code);
                } else {
                    loginWx(code);
                }
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED://用户拒绝授权
                CustomerToastUtils.toastShow(this).show();
                CustomerToastUtils.tv_toast.setText("用户拒绝授权");
                finish();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL://用户取消
                CustomerToastUtils.toastShow(this).show();
                CustomerToastUtils.tv_toast.setText("用户取消授权");
                finish();
                break;
            default:
                finish();
                break;
        }
    }

    private void bindingWx(String code) {
        mPresenter.getWecahtBinding(MyApplication.APP_ID, code);
    }

    private void loginWx(String code) {
        mPresenter.getLoginWecahtResponse(MyApplication.APP_ID, code);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected BindingWechatContract.BindingWechatPresenter createPresenter() {
        return new BindingWechatContract.BindingWechatPresenter();
    }

    @Override
    public void getWallPaper(BindingWechatResponse wallPaperResponse) {
        if (wallPaperResponse.getCode() == 0) {//绑定成功
            startActivity(new Intent(WXEntryActivity.this, MainActivity.class));
            finish();
        } else {
            ToastUtils.showShort(WXEntryActivity.this, wallPaperResponse.getMessage() + "");
        }
    }

    @Override
    public void getWxLogin(LoginWxResponse loginWxResponse) {
        if (loginWxResponse.getCode() == 0) {//登录成功
            BaseApplication.token = loginWxResponse.getData().getToken();
            RongIM.getInstance().disconnect();
            RongIM.connect(loginWxResponse.getData().getRyToken(), new RongIMClient.ConnectCallback() {
                @Override
                public void onSuccess(String s) {
                    SpUtils.putSpString(getApplication(), "ryId", s);

                    Log.e("erroi", "onError: " + s);
                }

                @Override
                public void onError(RongIMClient.ConnectionErrorCode connectionErrorCode) {
                    Toast.makeText(context, connectionErrorCode + "", Toast.LENGTH_SHORT).show();
                    Log.e("erroi", "onError: " + connectionErrorCode);

                }

                @Override
                public void onDatabaseOpened(RongIMClient.DatabaseOpenStatus databaseOpenStatus) {

                }
            });
            SpUtils.putSpString(getApplication(), "rytoken", loginWxResponse.getData().getRyToken());
            SpUtils.putSpString(getApplication(), "token", loginWxResponse.getData().getToken());
            SpUtils.putSpString(getApplication(), "headImg", loginWxResponse.getData().getHeadImage());
            SpUtils.putSpString(getApplication(), "nickName", loginWxResponse.getData().getNickname());
            SpUtils.putSpString(getApplication(), "studyNo", loginWxResponse.getData().getStudyNo());

            Intent intent = new Intent(WXEntryActivity.this, MainActivity.class);
            intent.putExtra("ry","ry");
            SpUtils.putSpString(context,"ry","ry");
            startActivity(intent);
            getActivityManager().finishActivity(LoginActivity.class);
        } else {
            ToastUtils.showShort(WXEntryActivity.this, loginWxResponse.getMessage() + "");
        }
        finish();
    }

    @Override
    public void getFailed(Throwable e) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(this.getResources().getColor(R.color.white));
        View decorView = getWindow().getDecorView();

        //保持View属性改变View不会重新绘制
        int option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE

                //隐藏状态栏
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;


        decorView.setSystemUiVisibility(option);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        MyApplication.api.handleIntent(getIntent(), this);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }
}
