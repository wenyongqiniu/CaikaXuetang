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
import com.example.caikaxuetang.responses.ExamTaskInfoResponse;
import com.example.caikaxuetang.responses.HomeWorkResponse;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.example.caikaxuetang.learningActivitys.ExamActivity.tv_next_exam;

public class ExamTaskListAdapter extends BaseQuickAdapter<ExamTaskInfoResponse.DataBean.AnswerListBean, BaseViewHolder> {

    private int checkedPosition = -1;
    public static String ed_content = "";
    public static boolean isNext;
    List<ExamTaskInfoResponse.DataBean.AnswerListBean> data;
    ExamTaskInfoResponse.DataBean dataBeans;


    public ExamTaskListAdapter(int layoutResId, @Nullable List<ExamTaskInfoResponse.DataBean.AnswerListBean> data, ExamTaskInfoResponse.DataBean dataBean) {
        super(layoutResId, data);
        this.data = data;
        this.dataBeans = dataBean;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ExamTaskInfoResponse.DataBean.AnswerListBean dataBean) {

        ImageView radio_checked_single = baseViewHolder.getView(R.id.radio_checked_single);
        RelativeLayout rl_checked = baseViewHolder.getView(R.id.rl_checked);
        EditText ed_text = baseViewHolder.getView(R.id.ed_text);
        TextView tv_question_list_title = baseViewHolder.getView(R.id.tv_question_list_title);
        TextView tv_tetx_num = baseViewHolder.getView(R.id.tv_tetx_num);

        tv_question_list_title.setText(dataBean.getAnswerCode()+"."+dataBean.getContent());


        if (dataBeans.getTaskType() == 3) {//	题目类型1单选 2多选 3填空题
            ed_text.setVisibility(View.VISIBLE);
            rl_checked.setVisibility(View.GONE);
            ed_text.setText(dataBeans.getContent());
            if (dataBeans.getContent() != null) {
                tv_tetx_num.setText(dataBeans.getContent().length() + "/140");
            }
            if (dataBeans.getContent() == null) {
                isNext = false;
                tv_next_exam.setBackgroundResource(R.drawable.un_exam_next);
            } else {
                isNext = true;
                tv_next_exam.setBackgroundResource(R.drawable.login_phone_shape);
            }

        } else if (dataBeans.getTaskType() == 2) {//多选
            radio_checked_single.setBackgroundResource(R.mipmap.un_checked_double);
            ed_text.setVisibility(View.GONE);
            if (dataBean.getSelectStatus() == 1) {
                radio_checked_single.setBackgroundResource(R.mipmap.checked_double);
                dataBean.setSelected(true);
                dataBean.setChecked(true);
            } else {
                if (dataBean.isChecked()) {
                    radio_checked_single.setBackgroundResource(R.mipmap.checked_double);
                    dataBean.setSelected(true);
                } else {
                    radio_checked_single.setBackgroundResource(R.mipmap.un_checked_double);
                    dataBean.setSelected(false);
                }
            }


            for (int i = 0; i < data.size(); i++) {
                if (!data.get(i).isSelected()) {
                    isNext = false;
                    tv_next_exam.setBackgroundResource(R.drawable.un_exam_next);
                } else {
                    isNext = true;
                    tv_next_exam.setBackgroundResource(R.drawable.login_phone_shape);
                    break;
                }
            }

            rl_checked.setVisibility(View.VISIBLE);

        } else {//单选
            radio_checked_single.setBackgroundResource(R.mipmap.un_checked_single);
            ed_text.setVisibility(View.GONE);
            if (dataBean.getSelectStatus() == 1) {
                dataBean.setSelectStatus(0);
                radio_checked_single.setBackgroundResource(R.mipmap.checked_single);
                dataBean.setSelected(true);
                isNext = true;
                tv_next_exam.setBackgroundResource(R.drawable.login_phone_shape);
            } else {
                radio_checked_single.setBackgroundResource(R.mipmap.un_checked_single);
                dataBean.setSelected(false);
                if (baseViewHolder.getLayoutPosition() == checkedPosition) {
                    radio_checked_single.setBackgroundResource(R.mipmap.checked_single);
                    dataBean.setSelected(true);
                    isNext = true;
                    tv_next_exam.setBackgroundResource(R.drawable.login_phone_shape);
                } else {
                    radio_checked_single.setBackgroundResource(R.mipmap.un_checked_single);
                    dataBean.setSelected(false);

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
            dataBean.setSelectStatus(0);
            ExamTaskListAdapter.this.notifyDataSetChanged();

        });

        ed_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ed_content = charSequence.toString().trim();
                tv_tetx_num.setText(ed_content.length() + "/140");

                if (ed_content.equals("")) {
                    isNext = false;
                    tv_next_exam.setBackgroundResource(R.drawable.un_exam_next);
                } else {
                    isNext = true;
                    tv_next_exam.setBackgroundResource(R.drawable.login_phone_shape);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

}
