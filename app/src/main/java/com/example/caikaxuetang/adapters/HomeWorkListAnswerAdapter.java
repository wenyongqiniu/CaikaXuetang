package com.example.caikaxuetang.adapters;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.caikaxuetang.R;
import com.example.caikaxuetang.responses.HomeWorkResponse;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HomeWorkListAnswerAdapter extends BaseQuickAdapter<HomeWorkResponse.DataBean.TaskListBean.AnswerListBean, BaseViewHolder> {

    private int checkedPosition = -1;
    public static String ed_content = "";
    private HomeWorkResponse.DataBean.TaskListBean dataBeans;


    public HomeWorkListAnswerAdapter(int layoutResId, @Nullable List<HomeWorkResponse.DataBean.TaskListBean.AnswerListBean> data, HomeWorkResponse.DataBean.TaskListBean dataBean) {
        super(layoutResId, data);
        this.dataBeans = dataBean;

    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, HomeWorkResponse.DataBean.TaskListBean.AnswerListBean dataBean) {

        ImageView radio_checked_single = baseViewHolder.getView(R.id.radio_checked_single);
        ImageView iv_state = baseViewHolder.getView(R.id.iv_state);
        RelativeLayout rl_checked = baseViewHolder.getView(R.id.rl_checked);
        EditText ed_text = baseViewHolder.getView(R.id.ed_text);
        TextView tv_question_list_title = baseViewHolder.getView(R.id.tv_question_list_title);
        tv_question_list_title.setText(dataBean.getAnswerCode()+"."+dataBean.getContent());

        if (dataBeans.getTaskType() == 3) {//	题目类型1单选 2多选 3填空题
            ed_text.setVisibility(View.VISIBLE);
            rl_checked.setVisibility(View.GONE);
        } else if (dataBeans.getTaskType() == 2) {//多选

            String[] split = dataBeans.getCustomerAnswerCode().split(",");
            for (String s : split) {
                if (s.equals(dataBean.getAnswerCode())) {
                    rl_checked.setBackgroundResource(R.drawable.answer_shape);
                    radio_checked_single.setBackgroundResource(R.mipmap.checked_double);
                    iv_state.setVisibility(View.VISIBLE);
                    if (dataBean.getRightAnswer() == 1) {//并且是正确答案
                        iv_state.setBackgroundResource(R.mipmap.right_answer);
                    } else {
                        iv_state.setBackgroundResource(R.mipmap.error_answer);
                    }
                }
            }
            if (dataBeans.getCustomerAnswerCode().equals(dataBean.getAnswerCode())) {
                rl_checked.setBackgroundResource(R.drawable.answer_shape);
                radio_checked_single.setBackgroundResource(R.mipmap.checked_double);
                iv_state.setVisibility(View.VISIBLE);
            }
            rl_checked.setVisibility(View.VISIBLE);
            ed_text.setVisibility(View.GONE);

        } else {//单选
            ed_text.setVisibility(View.GONE);
            radio_checked_single.setVisibility(View.VISIBLE);
            if ((dataBeans.getCustomerAnswerCode()+"").equals(dataBean.getAnswerCode())) {//用户选的答案
                rl_checked.setBackgroundResource(R.drawable.answer_shape);
                if (dataBean.getRightAnswer() == 1) {//并且是正确答案
                    iv_state.setBackgroundResource(R.mipmap.right_answer);
                } else {
                    iv_state.setBackgroundResource(R.mipmap.error_answer);
                }
                iv_state.setVisibility(View.VISIBLE);
                radio_checked_single.setBackgroundResource(R.mipmap.checked_single);
            } else {
                rl_checked.setBackgroundResource(R.drawable.answer_shape2);
                radio_checked_single.setBackgroundResource(R.mipmap.un_checked_single);
                iv_state.setVisibility(View.INVISIBLE);
            }

        }
        ed_text.setText(dataBeans.getCustomerAnswerCode());

    }

}
