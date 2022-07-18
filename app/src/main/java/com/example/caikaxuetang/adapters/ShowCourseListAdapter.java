package com.example.caikaxuetang.adapters;

import android.content.Intent;
import android.text.Html;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.caikaxuetang.R;
import com.example.caikaxuetang.learningActivitys.XbCourseListActivity;
import com.example.caikaxuetang.responses.ShowCourseListResponse;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ShowCourseListAdapter extends BaseQuickAdapter<ShowCourseListResponse.DataBean.XbCourseListBean, BaseViewHolder> {
    public ShowCourseListAdapter(int layoutResId, @Nullable List<ShowCourseListResponse.DataBean.XbCourseListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ShowCourseListResponse.DataBean.XbCourseListBean showCourseListResponse) {
        baseViewHolder.setText(R.id.tv_course_name, showCourseListResponse.getCourseName());
        //首先是拼接字符串
        String content = "<font color=\"#FE8000\">" + showCourseListResponse.getCurrentDay() + "</font>";
        //然后直接setText()
        String tvContent = "当前第  " + content + "  天 / 共" + showCourseListResponse.getTotalDay() + "天";
        baseViewHolder.setText(R.id.tv_day, Html.fromHtml(tvContent));
        baseViewHolder.setText(R.id.tv_sign_up, showCourseListResponse.getCustomerNum() + "人已报名");

        baseViewHolder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), XbCourseListActivity.class);
            intent.putExtra("courseId",showCourseListResponse.getCourseId());
            getContext().startActivity(intent);
        });


        RecyclerView rv_head_view = baseViewHolder.getView(R.id.rv_head_view);



        ShowHeadViewAdapter showCourseListAdapter = new ShowHeadViewAdapter(R.layout.headview_item, showCourseListResponse.getHeadImages());

        rv_head_view.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rv_head_view.setAdapter(showCourseListAdapter);
        showCourseListAdapter.notifyDataSetChanged();

    }

}
