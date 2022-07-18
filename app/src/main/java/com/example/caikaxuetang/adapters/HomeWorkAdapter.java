package com.example.caikaxuetang.adapters;

import android.graphics.Color;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.caikaxuetang.R;
import com.example.caikaxuetang.learningActivitys.DoHomeWorkActivity;
import com.example.caikaxuetang.responses.HomeWorkResponse;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Text;

import java.util.List;

public class HomeWorkAdapter extends BaseQuickAdapter<HomeWorkResponse.DataBean.TaskListBean, BaseViewHolder> {


    private List<HomeWorkResponse.DataBean.TaskListBean.AnswerListBean> answerList;

    public HomeWorkAdapter(int layoutResId, @Nullable List<HomeWorkResponse.DataBean.TaskListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, HomeWorkResponse.DataBean.TaskListBean dataBean) {

        TextView tv_choice_type = baseViewHolder.getView(R.id.tv_choice_type);
        TextView tv_question_title = baseViewHolder.getView(R.id.tv_question_title);
        RecyclerView rv_question_list = baseViewHolder.getView(R.id.rv_question_list);

        if (dataBean.getTaskType() == 1) {//	题目类型1单选 2多选 3填空题
            tv_choice_type.setText("单选");
        } else if (dataBean.getTaskType() == 2) {
            tv_choice_type.setText("多选");
        } else {
            tv_choice_type.setText("填空");
        }
        tv_question_title.setText("\t\t\t\t\t\t\t\t" + dataBean.getTaskTitle());
        if (dataBean.getTaskType() == 3) {
            answerList = dataBean.getAnswerList();
            answerList.clear();
            answerList.add(new HomeWorkResponse.DataBean.TaskListBean.AnswerListBean());
            //rv_question_list.setVisibility(View.GONE);
        } else {
            // rv_question_list.setVisibility(View.VISIBLE);
        }

        HomeWorkListAdapter homeWorkListAdapter = new HomeWorkListAdapter(R.layout.item_homework_list, dataBean.getAnswerList(), dataBean);
        rv_question_list.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_question_list.setAdapter(homeWorkListAdapter);
        homeWorkListAdapter.notifyDataSetChanged();
    }

}
