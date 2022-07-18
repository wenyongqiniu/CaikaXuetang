package com.example.caikaxuetang.learningActivitys;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.caikaxuetang.R;
import com.example.caikaxuetang.contract.StudyCardContract;
import com.example.caikaxuetang.responses.ExamInfoResponse;
import com.example.caikaxuetang.responses.StudyCardResponse;
import com.example.caikaxuetang.utils.DonwloadSaveImg;
import com.ljb.page.PageState;
import com.ljb.page.PageStateLayout;
import com.llw.mvplibrary.mvp.MvpActivity;

public class StudyCardActivity extends MvpActivity<StudyCardContract.StudyCardPresenter> implements StudyCardContract.ExamView {


    private ImageView iv_study_card;
    private PageStateLayout page_layout;
    private String data;

    @Override
    protected StudyCardContract.StudyCardPresenter createPresenter() {
        return new StudyCardContract.StudyCardPresenter();
    }

    @Override
    public void getWallPaper(StudyCardResponse wallPaperResponse) {
        if (wallPaperResponse.getCode() == 0) {
            data = wallPaperResponse.getData();
            Glide.with(context).load(data).into(iv_study_card);
            page_layout.setPage(PageState.STATE_SUCCESS);
        } else {
            page_layout.setPage(PageState.STATE_EMPTY);
        }
    }

    @Override
    public void getFailed(Throwable e) {
        page_layout.setPage(PageState.STATE_ERROR);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        page_layout = findViewById(R.id.page_layout);
        Intent intent = getIntent();
        String chapterId = intent.getStringExtra("chapterId");

        page_layout.setContentView(R.layout.activity_study_card);
        mPresenter.getWallPaper(chapterId);

        page_layout.setPage(PageState.STATE_LOADING);
        RelativeLayout rl_left = findViewById(R.id.rl_left);
        TextView tv_title_top = findViewById(R.id.tv_title_top);
        iv_study_card = findViewById(R.id.iv_study_card);
        tv_title_top.setText("毕业证");
        rl_left.setOnClickListener(v -> finish());
        TextView retry = findViewById(R.id.retry);

        iv_study_card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
                vib.vibrate(200);//只震动一秒，一次
                DonwloadSaveImg.donwloadImg(context, data);
                return false;
            }
        });
        //点击重试
        retry.setOnClickListener(v -> mPresenter.getWallPaper(chapterId));

    }

    @Override
    public int getLayoutId() {
        return R.layout.base_layout;
    }
}