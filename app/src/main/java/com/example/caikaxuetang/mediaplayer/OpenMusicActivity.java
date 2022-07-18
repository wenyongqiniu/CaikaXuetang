package com.example.caikaxuetang.mediaplayer;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;


import com.example.caikaxuetang.R;
import com.example.caikaxuetang.contract.SectionDetailContract;
import com.example.caikaxuetang.learningActivitys.XbSectionDetailActivity;
import com.example.caikaxuetang.responses.SectionDetailResponse;
import com.example.caikaxuetang.utils.MusicListPopup;
import com.example.caikaxuetang.utils.ToastUtils;
import com.llw.mvplibrary.mvp.MvpActivity;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.necer.ndialog.ChoiceDialog;

import java.util.ArrayList;

import io.rong.imkit.picture.tools.ScreenUtils;

import static com.example.caikaxuetang.MyApplication.loadingPopupView;
import static com.example.caikaxuetang.MyApplication.mediaPlayerIngHolder;


public class OpenMusicActivity extends MvpActivity<SectionDetailContract.SectionDetailPresenter> implements SectionDetailContract.SectionDetailView, PlaybackInfoListener, View.OnClickListener, ChoiceDialog.OnItemClickListener, OnSelectListener {

    private int currentProgress;
    private SeekBarAndText bar_music;
    private ImageView iv_pause;
    private ChoiceDialog choiceDialog;
    private TextView tv_double_speed;
    private TextView iv_music_list;
    private ArrayList<SectionDetailResponse.AudioList> audioList;
    public static TextView tv_musci_title;

    @Override
    protected SectionDetailContract.SectionDetailPresenter createPresenter() {
        return new SectionDetailContract.SectionDetailPresenter();
    }


    @Override
    public void getFailed(Throwable e) {
        loadingPopupView.dismiss();

    }

    @Override
    public void initData(Bundle savedInstanceState) {

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        audioList = (ArrayList<SectionDetailResponse.AudioList>) intent.getSerializableExtra("audioList");


        bar_music = findViewById(R.id.app_bar_music);
        iv_pause = findViewById(R.id.iv_pause);
        tv_musci_title = findViewById(R.id.tv_musci_title);
        TextView tv_time_j = findViewById(R.id.tv_time_j);
        TextView tv_time_z = findViewById(R.id.tv_time_z);
        tv_double_speed = findViewById(R.id.tv_double_speed);
        iv_music_list = findViewById(R.id.iv_music_list);
        ImageView iv_last_music = findViewById(R.id.iv_last_music);
        ImageView iv_next_music = findViewById(R.id.iv_next_music);
        ImageView iv_back_music = findViewById(R.id.iv_back_music);

        tv_time_j.setOnClickListener(this);
        tv_time_z.setOnClickListener(this);
        iv_last_music.setOnClickListener(this);
        iv_next_music.setOnClickListener(this);
        iv_pause.setOnClickListener(this);
        iv_back_music.setOnClickListener(this);
        tv_double_speed.setOnClickListener(this);
        iv_music_list.setOnClickListener(this);

        if (mediaPlayerIngHolder.isPlaying()) {
            iv_pause.setBackgroundResource(R.mipmap.contimiu_music);
        } else {
            iv_pause.setBackgroundResource(R.mipmap.pause_music);
        }

        mediaPlayerIngHolder.setmPlaybackInfoListener(this);
        tv_musci_title.setText(title);
        bar_music.setMax(MediaPlayerHolder.maxProgress);
        bar_music.setSongTimeCallBack(new SeekBarAndText.SongTimeCallBack() {
            @Override
            public String getSongTime(int progress) {
                return currentProgress + "";
            }

            @Override
            public String getDrawText() {
                return mediaPlayerIngHolder.toMinutes(currentProgress) + "/" + mediaPlayerIngHolder.totalTime;
            }
        });

        //seekbar监听，手动拖动到最后自动播放下一节
        bar_music.setOnSeekBarChangeListener(new SeekBarAndText.OnSeekBarAndtextChangeListener() {
            @Override
            public void onProgress(SeekBar seekBar, int progress, float indicatorOffset) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromuser) {
                currentProgress = seekBar.getProgress();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                currentProgress = seekBar.getProgress();
                mediaPlayerIngHolder.seekTo(currentProgress);//设置进度条同时间跟进
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    bar_music.setProgress(currentProgress, false);
                }
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_open_music;
    }

    @Override
    public void onTotalDuration(int duration) {

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onPositionChanged(int position) {
        currentProgress = position;
        bar_music.setProgress(currentProgress);
    }

    @Override
    public void onStateChanged(int state) {
    }

    @Override
    public void onPlaybackCompleted() {
        for (int i = 0; i < audioList.size(); i++) {
            if (audioList.get(i).getPlayStatus() == 1 && i < audioList.size() - 1) {
                mediaPlayerIngHolder.loadMedia(audioList.get(i + 1).getAudioUrl());
                loadingPopupView.show();
                mPresenter.geSectionDetail(audioList.get(i + 1).getSectionId());
                audioList.get(i).setPlayStatus(0);
                audioList.get(i + 1).setPlayStatus(1);
                MusicListPopup.musicListAdapter.notifyDataSetChanged();
                break;
            }
        }
        ToastUtils.showShort(this, "onPlaybackCompleted: " + "播放完毕");
    }

    @Override
    public void onLoadPrepared() {
        bar_music.setMax(MediaPlayerHolder.maxProgress);
        mediaPlayerIngHolder.play();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_pause:
                //rotateAnimation(iv_pause);
                if (mediaPlayerIngHolder.isPlaying()) {
                    mediaPlayerIngHolder.pause();
                    iv_pause.setBackgroundResource(R.mipmap.pause_music);
                } else {
                    mediaPlayerIngHolder.play();
                    iv_pause.setBackgroundResource(R.drawable.ic_continu_music);
                }
                break;
            case R.id.iv_last_music:
                for (int i = 0; i < audioList.size(); i++) {
                    if (audioList.get(i).getPlayStatus() == 1 && i > 0) {
                        mediaPlayerIngHolder.loadMedia(audioList.get(i - 1).getAudioUrl());
                        loadingPopupView.show();
                        mPresenter.geSectionDetail(audioList.get(i - 1).getSectionId());
                        audioList.get(i).setPlayStatus(0);
                        audioList.get(i - 1).setPlayStatus(1);
                        break;
                    }
                }
                ToastUtils.showShort(this, "上一首");
                break;
            case R.id.iv_next_music:
                for (int i = 0; i < audioList.size(); i++) {
                    if (audioList.get(i).getPlayStatus() == 1 && i < audioList.size() - 1) {
                        mediaPlayerIngHolder.loadMedia(audioList.get(i + 1).getAudioUrl());
                        loadingPopupView.show();
                        mPresenter.geSectionDetail(audioList.get(i + 1).getSectionId());
                        audioList.get(i).setPlayStatus(0);
                        audioList.get(i + 1).setPlayStatus(1);
                        break;
                    }
                }

                ToastUtils.showShort(this, "下一首");
                break;
            case R.id.tv_time_j:
                mediaPlayerIngHolder.seekTo(currentProgress - 15000);
                break;
            case R.id.tv_time_z:
                mediaPlayerIngHolder.seekTo(currentProgress + 15000);
                break;
            case R.id.tv_double_speed:
                showSpeedDialog();
                break;
            case R.id.iv_back_music:
                finish();
                break;
            case R.id.iv_music_list:
                showMusicList();
                break;

        }
    }


    //播放列表
    private void showMusicList() {

        new XPopup.Builder(this)
                .maxHeight(ScreenUtils.getScreenHeight(this) *2/ 3)
                .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                .enableDrag(true)
                .asCustom(new MusicListPopup(this, audioList))
                .show();

    }


    //倍速选择弹框
    private void showSpeedDialog() {

        String[] objects = new String[]{"0.5X", "1.0X", "1.5X", "2.0X"};

        choiceDialog = new ChoiceDialog(this, true);

        choiceDialog
                .setTitleSize(28)
                .setTitleSize(14)

                .setTitleColor(Color.parseColor("#000000"))

                .setTitleGravity(Gravity.CENTER)

                .setTtitlePadding(12, 12, 12, 12)

                .setTitleMaxLines(1)

                .setItems(objects)

                .setItemTextSize(13)

                .setItemTextColor(Color.parseColor("#9B9B9B"))

                .setItemTextPadding(12, 12)

                .setItemDividerPadding(8)

                .setCancleButtonText("关闭")

                .setItemTextGravity(Gravity.CENTER)

                .setOnItemClickListener(this)

                .setItemHeight(50)


                .setDividerColor(Color.parseColor("#F3F3F3"))

                .hasCancleButton(true)
                .setTtitle("倍速选择")
                .setDialogHeight(ScreenUtils.getScreenHeight(this) * 5 / 12)

                .setDialogCornersRadius(8)


                .setIsFromBottom(true)

                .setCancelable(true)

                .setCanceledOnTouchOutside(true)
                .create().show();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.anim_bottom_out);
        for (int i = 0; i < audioList.size(); i++) {
            if (audioList.get(i).getPlayStatus()==1){
                XbSectionDetailActivity.finishSectionId=audioList.get(i).getSectionId();
            }
        }
    }

    @Override
    public void onItemClick(TextView onClickView, int position) {
        if (position == 0) {
            mediaPlayerIngHolder.setPlayerSpeed(0.5f);
            tv_double_speed.setText(R.string.half_speed);
            iv_pause.setBackgroundResource(R.mipmap.contimiu_music);
        } else if (position == 1) {
            mediaPlayerIngHolder.setPlayerSpeed(1.0f);
            tv_double_speed.setText(R.string.one_speed);
            iv_pause.setBackgroundResource(R.mipmap.contimiu_music);
        } else if (position == 2) {
            mediaPlayerIngHolder.setPlayerSpeed(1.5f);
            tv_double_speed.setText(R.string.one_half_speed);
            iv_pause.setBackgroundResource(R.mipmap.contimiu_music);
        } else if (position == 3) {
            mediaPlayerIngHolder.setPlayerSpeed(2.0f);
            tv_double_speed.setText(R.string.double_speed);
            iv_pause.setBackgroundResource(R.mipmap.contimiu_music);
        }
    }

    //旋转动画
    public void rotateAnimation(View view) {
        RotateAnimation rotate = new RotateAnimation(0, 90, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0);
        LinearInterpolator lin = new LinearInterpolator();
        rotate.setInterpolator(lin);
        rotate.setDuration(1000);//设置动画持续周期
        rotate.setRepeatCount(1);//设置重复次数
        rotate.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        view.setAnimation(rotate);
    }

    @Override
    public void onSelect(int position, String text) {

    }

    @Override
    public void getSectionDetail(SectionDetailResponse wallPaperResponse) {
        loadingPopupView.dismiss();

        if (wallPaperResponse.getCode() == 0) {
            tv_musci_title.setText(wallPaperResponse.getData().getSectionName());
        }

    }
}