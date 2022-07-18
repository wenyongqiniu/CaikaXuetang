package com.example.caikaxuetang;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.caikaxuetang.contract.MainContract;
import com.example.caikaxuetang.contract.PhoneLoginContract;
import com.example.caikaxuetang.responses.GroupInfo;
import com.example.caikaxuetang.responses.GroupNoticeBean;
import com.example.caikaxuetang.responses.PhoneLoginResponse;
import com.example.caikaxuetang.responses.WallPaperResponse;
import com.example.caikaxuetang.utils.CustomerToastUtils;
import com.example.caikaxuetang.wxapi.WXEntryActivity;
import com.llw.mvplibrary.BaseApplication;
import com.llw.mvplibrary.mvp.MvpActivity;
import com.llw.mvplibrary.network.utils.SpUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class PhoneLoginActivity extends MvpActivity<PhoneLoginContract.MainPresenter> implements PhoneLoginContract.IMainView {

    private TextView mTvBack;
    private TextView mTvPhoneNum;
    private EditText mEdPhoneNum;
    private EditText mEdPassword;
    private TextView mTvSureLogin;
    private String phone = "";
    private String pass = "";


    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
        // ^ 匹配输入字符串开始的位置
        // \d 匹配一个或多个数字，其中 \ 要转义，所以是 \\d
        // $ 匹配输入字符串结尾的位置
        String regExp = "^((13[0-9])|(14[5,7,9])|(15[0-3,5-9])|(166)|(17[3,5,6,7,8])" +
                "|(18[0-9])|(19[8,9]))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    @Override
    protected PhoneLoginContract.MainPresenter createPresenter() {
        return new PhoneLoginContract.MainPresenter();
    }


    @Override
    public void getPhoneLogin(PhoneLoginResponse groupInfo) {
        if (groupInfo.getCode() == 0) {
            SpUtils.putSpString(getApplication(), "token", groupInfo.getData().getToken());
            SpUtils.putSpString(getApplication(), "headImg", groupInfo.getData().getHeadImage());
            SpUtils.putSpString(getApplication(), "nickName", groupInfo.getData().getNickname());
            SpUtils.putSpString(getApplication(), "studyNo", groupInfo.getData().getStudyNo());
            SpUtils.remove(getApplication(),"rytoken");
            BaseApplication.token = groupInfo.getData().getToken();
            Intent intent = new Intent(PhoneLoginActivity.this, MainActivity.class);
            intent.putExtra("ry","");
            SpUtils.putSpString(context,"ry","");
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void getFailed(Throwable e) {
        CustomerToastUtils.toastShow(context).show();
        CustomerToastUtils.tv_toast.setText(e.getMessage() + "");
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        mTvPhoneNum = findViewById(R.id.tv_phone_num);
        mEdPhoneNum = findViewById(R.id.ed_phone_num);
        mEdPassword = findViewById(R.id.ed_password);
        mTvSureLogin = findViewById(R.id.tv_sure_login);

        RelativeLayout rl_left = findViewById(R.id.rl_left);
        TextView tv_title_top = findViewById(R.id.tv_title_top);
        tv_title_top.setText("手机号登录");
        rl_left.setOnClickListener(v -> finish());
        mEdPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                pass = s.toString().trim();

            }
        });

        mEdPhoneNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                phone = s.toString().trim();
            }
        });


        mTvSureLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getPhoneLogin(phone, pass, 1);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_phone_login;
    }
}
