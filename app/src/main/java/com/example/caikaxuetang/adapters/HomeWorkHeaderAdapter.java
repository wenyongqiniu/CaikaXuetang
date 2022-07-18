package com.example.caikaxuetang.adapters;

import android.graphics.Color;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.caikaxuetang.R;
import com.example.caikaxuetang.responses.HomeWorkResponse;
import com.example.caikaxuetang.responses.SectionDetailResponse;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HomeWorkHeaderAdapter extends BaseQuickAdapter<HomeWorkResponse.DataBean.TaskRecordListBean, BaseViewHolder> {


    public HomeWorkHeaderAdapter(int layoutResId, @Nullable List<HomeWorkResponse.DataBean.TaskRecordListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, HomeWorkResponse.DataBean.TaskRecordListBean dataBean) {

        TextView tv_day = baseViewHolder.getView(R.id.tv_day);
        RelativeLayout rl_back_homework = baseViewHolder.getView(R.id.rl_back_homework);
        TextView tv_complete_state = baseViewHolder.getView(R.id.tv_complete_state);
        tv_day.setText(dataBean.getDayDesc());


        if (dataBean.getIsCurrent() == 1) {//是当前小节
            tv_complete_state.setBackgroundResource(R.mipmap.current_homework);
            rl_back_homework.setBackgroundResource(R.drawable.homework_header_yes_shape);
            tv_complete_state.setText("");
            tv_day.setTextColor(Color.parseColor("#FFFFFF"));

        } else {
            if (dataBean.getStatus() == 0) {//未完成
                tv_complete_state.setText("缺");
                tv_complete_state.setBackgroundResource(R.drawable.homework_uncomplete);

            } else {
                tv_complete_state.setText("完");
                tv_complete_state.setBackgroundResource(R.drawable.login_phone_shape);

            }

        }


    }

}
