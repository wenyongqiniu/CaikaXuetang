package com.example.caikaxuetang.adapters;

import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.caikaxuetang.R;
import com.example.caikaxuetang.responses.ExamTaskInfoResponse;
import com.example.caikaxuetang.responses.HomeWorkResponse;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ExamTaskkAdapter extends BaseQuickAdapter<ExamTaskInfoResponse.DataBean, BaseViewHolder> {


    public ExamTaskkAdapter(int layoutResId, @Nullable List<ExamTaskInfoResponse.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ExamTaskInfoResponse.DataBean dataBean) {

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
        tv_question_title.setText("\t\t\t\t\t\t\t\t"+dataBean.getTaskTitle());
        if (dataBean.getTaskType()==3){
            dataBean.getAnswerList().add(new ExamTaskInfoResponse.DataBean.AnswerListBean());
            //rv_question_list.setVisibility(View.GONE);
        }else{
           // rv_question_list.setVisibility(View.VISIBLE);
        }

        ExamTaskListAdapter homeWorkListAdapter = new ExamTaskListAdapter(R.layout.item_homework_list,dataBean.getAnswerList(),dataBean);
        rv_question_list.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_question_list.setAdapter(homeWorkListAdapter);
        homeWorkListAdapter.notifyDataSetChanged();

    }

}
