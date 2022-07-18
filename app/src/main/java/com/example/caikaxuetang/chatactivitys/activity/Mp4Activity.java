package com.example.caikaxuetang.chatactivitys.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.caikaxuetang.R;
import com.example.caikaxuetang.contract.MainContract;
import com.example.caikaxuetang.responses.GroupInfo;
import com.example.caikaxuetang.responses.GroupNoticeBean;
import com.example.caikaxuetang.responses.VersionEntity;
import com.example.caikaxuetang.responses.WallPaperResponse;
import com.example.caikaxuetang.utils.MyJieCaoVideoView;
import com.llw.mvplibrary.mvp.MvpActivity;

import java.io.File;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

public class Mp4Activity extends MvpActivity<MainContract.MainPresenter> implements MainContract.IMainView {



    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

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

    }

    @Override
    public void getFailed(Throwable e) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String mName = intent.getStringExtra("mName");


        TextView tv_title_top = findViewById(R.id.tv_title_top);
        RelativeLayout rl_left = findViewById(R.id.rl_left);
        tv_title_top.setText("财咖学堂");

        rl_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        MyJieCaoVideoView playerStandard = findViewById(R.id.mp4);
        Glide.with(this)
                .load(mName)
                .into(playerStandard.thumbImageView);
        playerStandard.setUp(mName, MyJieCaoVideoView.SCREEN_LAYOUT_NORMAL, "");
        JCVideoPlayer.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;  //横向
        JCVideoPlayer.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;  //纵向

        playerStandard.startVideo();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_mp4;
    }
}