package com.example.caikaxuetang.adapters;

import android.content.Intent;
import android.graphics.Color;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.caikaxuetang.R;
import com.example.caikaxuetang.learningActivitys.VideoActivity;
import com.example.caikaxuetang.responses.ShowCourseListResponse;
import com.example.caikaxuetang.utils.CustomerToastUtils;
import com.example.caikaxuetang.utils.RoundImageView;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ShowDakeCourseListAdapter extends BaseQuickAdapter<ShowCourseListResponse.DataBean.BigCourseBean.BigCourseListBean, BaseViewHolder> {
    public ShowDakeCourseListAdapter(int layoutResId, @Nullable List<ShowCourseListResponse.DataBean.BigCourseBean.BigCourseListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ShowCourseListResponse.DataBean.BigCourseBean.BigCourseListBean showCourseListResponse) {
        RoundImageView iv_img = baseViewHolder.getView(R.id.iv_img);
        TextView tv_dake_title = baseViewHolder.getView(R.id.tv_dake_title);
        TextView tv_state = baseViewHolder.getView(R.id.tv_state);
        TextView tv_lock = baseViewHolder.getView(R.id.tv_lock);
        TextView tv_course_style = baseViewHolder.getView(R.id.tv_course_style);
        Glide.with(getContext()).load(showCourseListResponse.getCoverImage()).into(iv_img);
        tv_dake_title.setText(showCourseListResponse.getCourseName());
        //tv_lock.setText(showCourseListResponse.);
        if (showCourseListResponse.getStudyStatus() == 0) {
            tv_state.setText("待开课");
            tv_state.setTextColor(Color.parseColor("#B2B2B2"));
        } else if (showCourseListResponse.getStudyStatus() == 1) {
            tv_state.setText("已开课");
        } else {
            tv_state.setTextColor(Color.parseColor("#FE8000"));
            tv_state.setText("上课中");
        }

        if (showCourseListResponse.getContentType() == 2) {//视频课
            tv_course_style.setText("视频课");
        } else {
            tv_course_style.setText("社群+图文");
        }
        baseViewHolder.itemView.setOnClickListener(v -> {
            if (showCourseListResponse.getStudyStatus() == 0) {//未开课
                CustomerToastUtils.toastShow(getContext()).show();
                CustomerToastUtils.tv_toast.setText("未开课");
            } else {
                if (showCourseListResponse.getContentType() == 2) {//视频课

                    Intent intent = new Intent(getContext(

                    ), VideoActivity.class);
                    intent.putExtra("courseId", showCourseListResponse.getCourseId());
                    intent.putExtra("courseName", showCourseListResponse.getCourseName());
                    getContext().startActivity(intent);
                }
            }
        });
    }

}
