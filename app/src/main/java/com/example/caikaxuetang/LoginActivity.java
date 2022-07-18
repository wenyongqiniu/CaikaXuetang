package com.example.caikaxuetang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caikaxuetang.MyApplication;
import com.example.caikaxuetang.R;
import com.example.caikaxuetang.contract.MainContract;
import com.example.caikaxuetang.responses.GroupInfo;
import com.example.caikaxuetang.responses.GroupNoticeBean;
import com.example.caikaxuetang.responses.VersionEntity;
import com.example.caikaxuetang.responses.WallPaperResponse;
import com.example.caikaxuetang.utils.CustomUpdatePrompter;
import com.example.caikaxuetang.utils.CustomerToastUtils;
import com.example.caikaxuetang.utils.MusicListPopup;
import com.example.caikaxuetang.utils.UpDateAppPopup;
import com.example.caikaxuetang.utils.VersionControlUtils;
import com.llw.mvplibrary.BaseApplication;
import com.llw.mvplibrary.mvp.MvpActivity;
import com.llw.mvplibrary.network.utils.SpUtils;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;

import io.rong.imkit.RongIM;

import io.rong.imlib.RongIMClient;

import static com.example.caikaxuetang.MyApplication.getActivityManager;

public class LoginActivity extends MvpActivity<MainContract.MainPresenter> implements MainContract.IMainView {

    private int checked = 0;
    private RadioButton mRbCheck;
    private RelativeLayout rb_check_rl;
    private BasePopupView show;
    public static String token;
    private RelativeLayout rl_phone_login;

    @Override
    protected MainContract.MainPresenter createPresenter() {
        return new MainContract.MainPresenter();
    }

    @Override
    public void getWallPaper(WallPaperResponse wallPaperResponse) {

    }

    @Override
    public void getGroupInfo(GroupInfo groupInfo) {

    }

    @Override
    public void getGroupNotice(GroupNoticeBean groupInfo) {

    }

    public static void startMain() {
        Intent intent = new Intent(MyApplication.getContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("ry", "");
        MyApplication.getContext().startActivity(intent);
        RongIM.getInstance().disconnect();
        String rytoken = SpUtils.getSpString(MyApplication.getContext(), "rytoken", "");
        RongIM.connect(rytoken, new RongIMClient.ConnectCallback() {
            @Override
            public void onSuccess(String s) {
                SpUtils.putSpString(MyApplication.getContext(), "ryId", s);

                Log.e("erroi", "onError: " + s);
            }

            @Override
            public void onError(RongIMClient.ConnectionErrorCode connectionErrorCode) {
                Toast.makeText(MyApplication.getContext(), connectionErrorCode + "", Toast.LENGTH_SHORT).show();
                Log.e("erroi", "onError: " + connectionErrorCode);

            }

            @Override
            public void onDatabaseOpened(RongIMClient.DatabaseOpenStatus databaseOpenStatus) {

            }
        });
    }

    @Override
    public void getCheckUpdate(VersionEntity groupInfo) {
        if (groupInfo.getCode() == 0) {
            VersionEntity.DataBean data = groupInfo.getData();
            if (data.getShowPhoneLogin() == 0) {
                rl_phone_login.setVisibility(View.INVISIBLE);
            } else {
                rl_phone_login.setVisibility(View.VISIBLE);
            }
        }

    }

    private void update(VersionEntity.DataBean data) {
        show = new XPopup.Builder(this)
                .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                .enableDrag(false)
                .asCustom(new UpDateAppPopup(context, data))
                .show();

    }

    @Override
    public void getFailed(Throwable e) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        String versionCode = VersionControlUtils.getVersionName(this);

        token = SpUtils.getSpString(this, "token", "");

        mPresenter.getCkeckUpdate(2 + "", 0 + "", versionCode);
        if (!token.equals("")) {
            //mPresenter.getCkeckUpdate(2 + "", 0 + "", versionCode);
            getActivityManager().finishActivity(LoginActivity.class);
            BaseApplication.token = token;
            startMain();
            finish();
        } else {
            // mPresenter.getCkeckUpdate(2 + "", 0 + "", versionCode);
            //finish();
        }

        getActivityManager().addActivity(this);
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
        RelativeLayout rl_login = findViewById(R.id.rl_login);
        TextView tv_service_txt = findViewById(R.id.tv_service_txt);
        TextView tv_service_perssion = findViewById(R.id.tv_service_perssion);

        //服物条款
        tv_service_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, YsActivity.class);
                intent.putExtra("ystype", "service");
                startActivity(intent);
            }
        });

        //隐私协议
        tv_service_perssion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, YsActivity.class);
                intent.putExtra("ystype", "ys");
                startActivity(intent);
            }
        });

        rl_phone_login = findViewById(R.id.rl_phone_login);
        mRbCheck = findViewById(R.id.rb_check);

        rb_check_rl = findViewById(R.id.rb_check_rl);
        rl_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checked == 1) {
                    SpUtils.putSpString(getApplication(), "token", "");
                    MyApplication.wxLogin();
                } else {

                    CustomerToastUtils.toastShow(context).show();
                    CustomerToastUtils.tv_toast.setText("请勾选相关协议");
                }
            }
        });

        //手机号登录
        rl_phone_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checked == 1) {
                    SpUtils.putSpString(getApplication(), "token", "");
                    startActivity(new Intent(context, PhoneLoginActivity.class));
                } else {

                }
            }
        });


        rb_check_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checked == 0) {
                    mRbCheck.setChecked(true);
                    checked = 1;
                } else {
                    mRbCheck.setChecked(false);
                    checked = 0;
                }
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }
}