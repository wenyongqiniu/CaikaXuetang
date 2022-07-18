package com.example.caikaxuetang.learningActivitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.caikaxuetang.R;
import com.example.caikaxuetang.adapters.AnswerAdapter;
import com.example.caikaxuetang.adapters.HomeWorkAdapter;
import com.example.caikaxuetang.adapters.HomeWorkHeaderAdapter;
import com.example.caikaxuetang.contract.HomeWorkContract;
import com.example.caikaxuetang.responses.CommitAnswerResponse;
import com.example.caikaxuetang.responses.CommitSuccessResponse;
import com.example.caikaxuetang.responses.HomeWorkResponse;
import com.ljb.page.PageState;
import com.ljb.page.PageStateLayout;
import com.llw.mvplibrary.mvp.MvpActivity;

import java.util.ArrayList;
import java.util.List;

public class AnswerActivity extends MvpActivity<HomeWorkContract.HomeWorkPresenter> implements HomeWorkContract.HomeWorkView {

    private RecyclerView rv_homework_head;
    private RecyclerView rv_homework;
    private List<HomeWorkResponse.DataBean.TaskListBean> taskList;

    private String sectionId;
    private String chapterId;
    private PageStateLayout page_layout;
    private TextView tv_question_num;
    private RelativeLayout rl_exam_info;

    @Override
    protected HomeWorkContract.HomeWorkPresenter createPresenter() {
        return new HomeWorkContract.HomeWorkPresenter();
    }

    @Override
    public void getHomeWork(HomeWorkResponse wallPaperResponse) {
        if (wallPaperResponse.getCode() == 0) {
            tv_question_num.setText("总题数" + wallPaperResponse.getData().getTotalNum() + "答对" + wallPaperResponse.getData().getRightNum() + "题");
            List<HomeWorkResponse.DataBean.TaskRecordListBean> taskRecordList = wallPaperResponse.getData().getTaskRecordList();
            HomeWorkHeaderAdapter homeWorkHeaderAdapter = new HomeWorkHeaderAdapter(R.layout.item_homework_header, taskRecordList);
            rv_homework_head.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
            rv_homework_head.setAdapter(homeWorkHeaderAdapter);
            homeWorkHeaderAdapter.notifyDataSetChanged();

            taskList = wallPaperResponse.getData().getTaskList();
            AnswerAdapter homeWorkAdapter = new AnswerAdapter(R.layout.item_homework_answer, taskList);
            rv_homework.setLayoutManager(new LinearLayoutManager(context));
            rv_homework.setAdapter(homeWorkAdapter);
            homeWorkAdapter.notifyDataSetChanged();
            page_layout.setPage(PageState.STATE_SUCCESS);
        } else {
            page_layout.setPage(PageState.STATE_EMPTY);
        }
    }

    @Override
    public void getSubmitAnswer(CommitSuccessResponse wallPaperResponse) {

    }

    @Override
    public void getFailed(Throwable e) {
        page_layout.setPage(PageState.STATE_ERROR);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        sectionId = intent.getStringExtra("sectionId");
        chapterId = intent.getStringExtra("chapterId");
        String type = intent.getStringExtra("type");
        page_layout = findViewById(R.id.page_layout);
        page_layout.setContentView(R.layout.activity_answer);
        rl_exam_info = findViewById(R.id.rl_exam_info);
        tv_question_num = findViewById(R.id.tv_question_num);

        if (type.equals("sectionType")) {
            rl_exam_info.setVisibility(View.GONE);
            mPresenter.getHomeWork(sectionId);
        } else {
            rl_exam_info.setVisibility(View.VISIBLE);
            mPresenter.getExamAnswer(chapterId);
        }


        page_layout.setPage(PageState.STATE_LOADING);


        TextView retry = page_layout.findViewById(R.id.retry);
        rv_homework_head = findViewById(R.id.rv_homework_head);
        rv_homework = findViewById(R.id.rv_homework);
        RelativeLayout rl_left = findViewById(R.id.rl_left);
        TextView tv_title_top = findViewById(R.id.tv_title_top);
        tv_title_top.setText("答案解析");

        rl_left.setOnClickListener(v -> finish());
        //点击重试
        retry.setOnClickListener(view -> {
            mPresenter.getHomeWork(sectionId);
            page_layout.setPage(PageState.STATE_LOADING);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.base_layout;
    }
}