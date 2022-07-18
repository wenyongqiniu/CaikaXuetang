package com.example.caikaxuetang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caikaxuetang.contract.MainContract;
import com.example.caikaxuetang.fragments.LearningCenterFragment;
import com.example.caikaxuetang.fragments.MessageFragment;
import com.example.caikaxuetang.fragments.MineFragment;
import com.example.caikaxuetang.fragments.ShowFragment;
import com.example.caikaxuetang.responses.GroupInfo;
import com.example.caikaxuetang.responses.GroupNoticeBean;
import com.example.caikaxuetang.responses.VersionEntity;
import com.example.caikaxuetang.responses.WallPaperResponse;
import com.example.caikaxuetang.utils.BottomBar;
import com.example.caikaxuetang.utils.MPermissionHelper;
import com.example.caikaxuetang.utils.UiUtils;
import com.example.caikaxuetang.utils.UpDateAppPopup;
import com.example.caikaxuetang.utils.VersionControlUtils;
import com.llw.mvplibrary.BaseApplication;
import com.llw.mvplibrary.mvp.MvpActivity;
import com.llw.mvplibrary.network.utils.SpUtils;
import com.llw.mvplibrary.network.utils.StatusBarUtils;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.IMCenter;
import io.rong.imkit.userinfo.RongUserInfoManager;
import io.rong.imkit.userinfo.model.GroupUserInfo;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.UserInfo;

import static com.llw.mvplibrary.BaseApplication.getActivityManager;

public class MainActivity extends MvpActivity<MainContract.MainPresenter> implements MainContract.IMainView, MPermissionHelper.PermissionCallBack {
    String[] permissions = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_NOTIFICATION_POLICY};
    private String TAG = "666";
    List<String> permissionsList = new ArrayList<>();
    private final int MY_REQUEST_CODE = 1000;
    public static TextView tv_revice_message_count;
    public static int countMessage = 0;
    private BasePopupView show;

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

    @Override
    public void getCheckUpdate(VersionEntity groupInfo) {
        if (groupInfo.getCode() == 0) {
            VersionEntity.DataBean data = groupInfo.getData();
            if (data.getHaveLatestVersion() == 0) {//没有更新
            } else {//当前有更新
                update(data);
            }
        }
    }

    private void update(VersionEntity.DataBean data) {
        show = new XPopup.Builder(this)
                .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                .enableDrag(false)
                .dismissOnBackPressed(false)
                .asCustom(new UpDateAppPopup(context, data))
                .show();

    }

    @Override
    public void getFailed(Throwable e) {

    }


    /**
     * 请求权限
     */
    private void initPermissions() {
        permissionsList.clear();
        //判断哪些权限未授予
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(permission);
            }
        }
        //请求权限
        if (!permissionsList.isEmpty()) {
            String[] permissions = permissionsList.toArray(new String[permissionsList.size()]);//将List转为数组
            ActivityCompat.requestPermissions(MainActivity.this, permissions, MY_REQUEST_CODE);
        }
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        String versionCode = VersionControlUtils.getVersionName(this);
        mPresenter.getCkeckUpdate(2 + "", 0 + "", versionCode);
        initPermissions();
        StatusBarUtils.setColor(this, Color.parseColor("#F8F8F8"));
        StatusBarUtils.setTextDark(this, true);
        BottomBar bottomBar = findViewById(R.id.bottom_navigation);
        tv_revice_message_count = findViewById(R.id.tv_revice_message_count);
        String ry = SpUtils.getSpString(context, "ry", "");
        if (ry.equals("")) {
            bottomBar.setContainer(R.id.fl_container)
                    .setTitleSize(10)
                    .setTitleIconMargin(8)
                    .setIconWidth(25)
                    .setIconHeight(25)
                    .setTitleBeforeAndAfterColor("#8E8F90", "#FE8000")
                    .addItem(ShowFragment.class,
                            "学习中心", R.drawable.no_learning_center, R.drawable.learning_center)
                    .addItem(MineFragment.class,
                            "我的", R.drawable.no_mine, R.drawable.mine)
                    .build();
            tv_revice_message_count.setVisibility(View.GONE);
        } else {
            bottomBar.setContainer(R.id.fl_container)
                    .setTitleSize(10)
                    .setTitleIconMargin(8)
                    .setIconWidth(25)
                    .setIconHeight(25)
                    .setTitleBeforeAndAfterColor("#8E8F90", "#FE8000")
                    .addItem(ShowFragment.class,
                            "学习中心", R.drawable.no_learning_center, R.drawable.learning_center)
                    .addItem(MessageFragment.class,
                            "消息", R.drawable.no_question_bank, R.drawable.question_bank)
                    .addItem(MineFragment.class,
                            "我的", R.drawable.no_mine, R.drawable.mine)
                    .build();
        }


        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) tv_revice_message_count.getLayoutParams();

        layoutParams.setMarginStart(UiUtils.getMaxWidth(context) / 2 + 20);

        tv_revice_message_count.setLayoutParams(layoutParams);


        IMCenter.getInstance().addOnReceiveMessageListener(new RongIMClient.OnReceiveMessageWrapperListener() {
            @Override
            public boolean onReceived(io.rong.imlib.model.Message message, int i, boolean b, boolean b1) {

                RongIMClient.getInstance().getTotalUnreadCount(new RongIMClient.ResultCallback<Integer>() {

                    @Override
                    public void onSuccess(Integer integer) {
                        countMessage = integer;
                        if (countMessage > 0) {
                            tv_revice_message_count.setVisibility(View.VISIBLE);
                        } else {
                            tv_revice_message_count.setVisibility(View.GONE);
                        }
                        if (countMessage >= 99) {
                            tv_revice_message_count.setText(99 + "+");
                        } else {
                            tv_revice_message_count.setText(countMessage + "");
                        }
                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {
                        Log.e("error", "onError: " + errorCode);
                    }
                });
                return false;
            }
        });


    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void permissionRegisterSuccess(String... permissions) {

    }

    @Override
    public void permissionRegisterError(String... permissions) {

    }
}