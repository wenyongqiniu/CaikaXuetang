package com.example.caikaxuetang.learningActivitys;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.caikaxuetang.R;
import com.example.caikaxuetang.adapters.XbCourseListAdapter;
import com.example.caikaxuetang.contract.XbCourseListContract;
import com.example.caikaxuetang.responses.CommitSuccessResponse;
import com.example.caikaxuetang.responses.DkCourseListResponse;
import com.example.caikaxuetang.responses.NoteDetailResponse;
import com.example.caikaxuetang.responses.XbCourseListResponse;
import com.example.caikaxuetang.utils.DonwloadSaveImg;
import com.ljb.page.PageState;
import com.ljb.page.PageStateLayout;
import com.llw.mvplibrary.mvp.MvpActivity;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.interfaces.OnConfirmListener;

public class XbCourseListActivity extends MvpActivity<XbCourseListContract.XbCourseListPresenter> implements XbCourseListContract.XbCourseListView {


    private RelativeLayout rl_left;
    private RelativeLayout rl_detail__message;
    private RelativeLayout rl_add_wx;
    private TextView tv_title_xb;
    private TextView retry;
    private TextView tv_title_top;
    private TextView tv_date_xb;
    private RecyclerView rv_xb_list;
    private PageStateLayout page_layout;
    private XbCourseListResponse.DataBean data;
    private String courseId;

    @Override
    protected XbCourseListContract.XbCourseListPresenter createPresenter() {
        return new XbCourseListContract.XbCourseListPresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        courseId = intent.getStringExtra("courseId");
        mPresenter.getXbCourseList(courseId);
    }

    @Override
    public void getFailed(Throwable e) {
        page_layout.setPage(PageState.STATE_ERROR);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        page_layout = findViewById(R.id.page_layout);
        page_layout.setPage(PageState.STATE_LOADING);
        page_layout.setContentView(R.layout.activity_xb_course_list);


        rl_left = findViewById(R.id.rl_left);
        tv_title_top = findViewById(R.id.tv_title_top);
        retry = findViewById(R.id.retry);
        rl_detail__message = findViewById(R.id.rl_detail__message);
        rl_add_wx = findViewById(R.id.rl_add_wx);
        tv_title_xb = findViewById(R.id.tv_title_xb);
        tv_date_xb = findViewById(R.id.tv_date_xb);
        rv_xb_list = findViewById(R.id.rv_xb_list);
        rl_left.setOnClickListener(v -> finish());

        tv_title_top.setText("课程目录");

        //加载出错点击重试
        retry.setOnClickListener(v -> {
            page_layout.setPage(PageState.STATE_LOADING);
            mPresenter.getXbCourseList(courseId);
        });
        //添加老师微信
        rl_add_wx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTeacherPop();
            }
        });
    }

    //弹出老师二维码
    private void showTeacherPop() {
        new XPopup.Builder(context)
                .enableDrag(true)
                .asCustom(new addWxPop(context, data))
                .show();
    }

    public static class addWxPop extends BasePopupView {
        private Context context;
        private XbCourseListResponse.DataBean dataBean;

        public addWxPop(@NonNull Context context, XbCourseListResponse.DataBean data) {
            super(context);
            this.context = context;
            this.dataBean = data;
        }

        @Override
        protected int getPopupLayoutId() {
            return R.layout.pop_wx;
        }

        @Override
        protected void onCreate() {
            super.onCreate();
            ImageView iv_rccode = findViewById(R.id.iv_rc_code);
            TextView tv_keep = findViewById(R.id.tv_keep);
            Glide.with(context).load(dataBean.getTeacherCode()).into(iv_rccode);
            tv_keep.setOnClickListener(view -> {
                DonwloadSaveImg.donwloadImg(context, dataBean.getTeacherCode());
                dismiss();
            });

        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.base_layout;
    }

    @Override
    public void getWallPaper(XbCourseListResponse wallPaperResponse) {
        if (wallPaperResponse.getCode() == 0) {
            page_layout.setPage(PageState.STATE_SUCCESS);
            data = wallPaperResponse.getData();
            XbCourseListAdapter courseTimeAdapter = new XbCourseListAdapter(wallPaperResponse.getData().getChapterList());
            courseTimeAdapter.notifyDataSetChanged();
            rv_xb_list.setLayoutManager(new LinearLayoutManager(context));
            rv_xb_list.setAdapter(courseTimeAdapter);

            tv_title_xb.setText(data.getCourseName());
            tv_date_xb.setText("课程周期：" + data.getCourseTime());

            if (!"".equals(data.getTeacherCode())) {
                rl_detail__message.setVisibility(View.VISIBLE);
            } else {
                rl_detail__message.setVisibility(View.GONE);
            }
        } else {
            page_layout.setPage(PageState.STATE_EMPTY);
        }

    }

    @Override
    public void getDkCourseList(DkCourseListResponse wallPaperResponse) {

    }

    @Override
    public void getKeepNote(CommitSuccessResponse wallPaperResponse) {

    }

    @Override
    public void getNoteDetail(NoteDetailResponse wallPaperResponse) {

    }
}