package com.example.caikaxuetang.adapters;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.caikaxuetang.R;
import com.example.caikaxuetang.responses.CourseTimeResponse;
import com.example.caikaxuetang.utils.CustomerToastUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CourseTimeAdapter extends BaseQuickAdapter<CourseTimeResponse.DataBean, BaseViewHolder> {
    private   int position=-1;
    public static String classId="";
    public static  String time="";
    public CourseTimeAdapter(int layoutResId, @Nullable List<CourseTimeResponse.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, CourseTimeResponse.DataBean showCourseListResponse) {
        TextView tv_course_time = baseViewHolder.getView(R.id.tv_course_time);
        TextView tv_full_already = baseViewHolder.getView(R.id.tv_full_already);
        RelativeLayout rl_time = baseViewHolder.getView(R.id.rl_time);
        ImageView iv_selected = baseViewHolder.getView(R.id.iv_selected);
        if (showCourseListResponse.getFullStatus()==1){//已满
            tv_full_already.setVisibility(View.VISIBLE);
            tv_course_time.setTextColor(Color.parseColor("#CBCBCB"));
        }else{
            tv_full_already.setVisibility(View.INVISIBLE);
            tv_course_time.setTextColor(Color.parseColor("#2A2A2A"));
        }
        tv_course_time.setText(showCourseListResponse.getDate());
        if (position==baseViewHolder.getAdapterPosition()){
            iv_selected.setVisibility(View.VISIBLE);
            rl_time.setBackgroundResource(R.drawable.rl_selected_time_shape);
        }else{
            iv_selected.setVisibility(View.INVISIBLE);
            rl_time.setBackgroundResource(R.drawable.course_time_shape);
        }

        rl_time.setOnClickListener(v -> {
            if (showCourseListResponse.getFullStatus()==0){//未满
                position=baseViewHolder.getAdapterPosition();
                time=showCourseListResponse.getDate();
                classId=showCourseListResponse.getClassId();
                CourseTimeAdapter.this.notifyDataSetChanged();
            }else{
                CustomerToastUtils.toastShow(getContext()).show();
                CustomerToastUtils.tv_toast.setText("人数已满");
            }
        });
    }

}
