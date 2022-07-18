package com.example.caikaxuetang.adapters;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.caikaxuetang.R;
import com.example.caikaxuetang.responses.CommitAnswerResponse;
import com.example.caikaxuetang.responses.HomeWorkResponse;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.example.caikaxuetang.learningActivitys.ExamActivity.tv_next_exam;

public class HomeWorkListAdapter extends BaseQuickAdapter<HomeWorkResponse.DataBean.TaskListBean.AnswerListBean, BaseViewHolder> {

    private int checkedPosition = -1;
    public static String ed_content = "";
    private HomeWorkResponse.DataBean.TaskListBean dataBeans;
    public static EditText ed_text;
    public static RelativeLayout rl_view;


    public HomeWorkListAdapter(int layoutResId, @Nullable List<HomeWorkResponse.DataBean.TaskListBean.AnswerListBean> data, HomeWorkResponse.DataBean.TaskListBean dataBean) {
        super(layoutResId, data);
        this.dataBeans = dataBean;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, HomeWorkResponse.DataBean.TaskListBean.AnswerListBean dataBean) {

        ImageView radio_checked_single = baseViewHolder.getView(R.id.radio_checked_single);
        RelativeLayout rl_checked = baseViewHolder.getView(R.id.rl_checked);
        rl_view = baseViewHolder.getView(R.id.rl_view);
        ed_text = baseViewHolder.getView(R.id.ed_text);
        TextView tv_question_list_title = baseViewHolder.getView(R.id.tv_question_list_title);
        TextView tv_tetx_num = baseViewHolder.getView(R.id.tv_tetx_num);
        tv_question_list_title.setText(dataBean.getAnswerCode()+"."+dataBean.getContent());
        if (dataBeans.getTaskType() == 3) {//	题目类型1单选 2多选 3填空题
            ed_text.setVisibility(View.VISIBLE);
            rl_checked.setVisibility(View.GONE);
            if (dataBeans.getMyEd() != null) {
                ed_text.setText(dataBeans.getMyEd());
            }

        } else if (dataBeans.getTaskType() == 2) {//多选
            radio_checked_single.setBackgroundResource(R.mipmap.un_checked_double);
            ed_text.setVisibility(View.GONE);

            if (dataBean.isChecked()) {
                dataBean.setSelected(true);
                radio_checked_single.setBackgroundResource(R.mipmap.checked_double);
            } else {
                radio_checked_single.setBackgroundResource(R.mipmap.un_checked_double);
                dataBean.setSelected(false);
            }
            rl_checked.setVisibility(View.VISIBLE);

        } else {//单选
            radio_checked_single.setBackgroundResource(R.mipmap.un_checked_single);
            ed_text.setVisibility(View.GONE);

            if (checkedPosition != -1) {
                if (baseViewHolder.getLayoutPosition() == checkedPosition) {
                    radio_checked_single.setBackgroundResource(R.mipmap.checked_single);
                    dataBean.setSelected(true);
                    dataBean.setChecked(true);
                } else {
                    radio_checked_single.setBackgroundResource(R.mipmap.un_checked_single);
                    dataBean.setSelected(false);
                    dataBean.setChecked(false);
                }

            } else {
                if (dataBean.isChecked()) {
                    radio_checked_single.setBackgroundResource(R.mipmap.checked_single);
                } else {
                    radio_checked_single.setBackgroundResource(R.mipmap.un_checked_single);
                }
            }

            rl_checked.setVisibility(View.VISIBLE);

        }

        //	题目类型1单选 2多选 3填空题
        rl_checked.setOnClickListener(view -> {
            if (dataBeans.getTaskType() == 1) {
                checkedPosition = baseViewHolder.getLayoutPosition();
            } else if (dataBeans.getTaskType() == 2) {
                dataBean.setChecked(!dataBean.isChecked());
            }
            HomeWorkListAdapter.this.notifyDataSetChanged();
        });

        ed_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ed_content = charSequence.toString().trim();
                tv_tetx_num.setText(ed_content.length() + "/140");
                dataBeans.setMyEd(ed_content);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

}
