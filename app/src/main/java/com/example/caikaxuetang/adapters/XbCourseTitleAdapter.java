package com.example.caikaxuetang.adapters;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.caikaxuetang.R;
import com.example.caikaxuetang.learningActivitys.ExamActivity;
import com.example.caikaxuetang.learningActivitys.XbSectionDetailActivity;
import com.example.caikaxuetang.responses.XbCourseListResponse;
import com.example.caikaxuetang.utils.CustomerToastUtils;

import java.util.List;

/**
 * 壁纸适配器
 *
 * @author llw
 */
public class XbCourseTitleAdapter extends BaseQuickAdapter<XbCourseListResponse.DataBean.ChapterListBean.SectionListBean, BaseViewHolder> {

    private String chapterId;

    public XbCourseTitleAdapter(List<XbCourseListResponse.DataBean.ChapterListBean.SectionListBean> data, String chapterId) {
        super(R.layout.item_xb_title, data);
        this.chapterId = chapterId;
    }

    @Override
    protected void convert(BaseViewHolder helper, XbCourseListResponse.DataBean.ChapterListBean.SectionListBean item) {
        TextView tv_xb_title = helper.getView(R.id.tv_xb_title);
        TextView tv_people = helper.getView(R.id.tv_people);
        TextView tv_study = helper.getView(R.id.tv_study);
        ImageView iv_unlock = helper.getView(R.id.iv_unlock);
        View point = helper.getView(R.id.point);

        tv_xb_title.setText("" + item.getSectionName());
        tv_people.setText(item.getStudyNum() + "人学过");


        if (item.getSectionType() == 3) {
            tv_study.setVisibility(View.INVISIBLE);
            tv_people.setVisibility(View.INVISIBLE);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) tv_xb_title.getLayoutParams();
            RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) point.getLayoutParams();
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            layoutParams1.addRule(RelativeLayout.CENTER_VERTICAL);
            tv_xb_title.setLayoutParams(layoutParams);
        } else {
            tv_people.setVisibility(View.VISIBLE);
            tv_study.setVisibility(View.VISIBLE);
        }

        if (item.getLockStatus() == 0) {//未解锁
            iv_unlock.setVisibility(View.VISIBLE);
            tv_study.setText("");
            tv_people.setCompoundDrawablesWithIntrinsicBounds(getContext().getDrawable(R.mipmap.pople), null, null, null);
            tv_xb_title.setTextColor(Color.parseColor("#939393"));
            point.setBackgroundResource(R.drawable.point2_shape);
        } else {
            iv_unlock.setVisibility(View.INVISIBLE);
            tv_people.setCompoundDrawablesWithIntrinsicBounds(getContext().getDrawable(R.mipmap.h_people), null, null, null);
            tv_xb_title.setTextColor(Color.parseColor("#2A2A2A"));
            point.setBackgroundResource(R.drawable.point_shape);
            if (item.isHasStudy()) {
                tv_study.setText("已学习");
                tv_study.setTextColor(Color.parseColor("#FE8000"));
            } else {
                tv_xb_title.setTextColor(Color.parseColor("#939393"));
                tv_study.setText("未学习");
            }
        }


        helper.itemView.setOnClickListener(v -> {
            if (item.getLockStatus() != 0) {
                if (item.getSectionType() == 1) {//普通小节
                    Intent intent = new Intent(getContext(), XbSectionDetailActivity.class);
                    intent.putExtra("sectionId", item.getSectionId());
                    intent.putExtra("title", item.getSectionName());
                    getContext().startActivity(intent);
                } else if (item.getSectionType() == 2) {//课后习题

                } else if (item.getSectionType() == 3) {
                    Intent intent = new Intent(getContext(), ExamActivity.class);
                    intent.putExtra("chapterId", chapterId);
                    getContext().startActivity(intent);
                }
            } else {
                CustomerToastUtils.toastShow(getContext()).show();
                CustomerToastUtils.tv_toast.setText("课程未解锁");
                CustomerToastUtils.iv_warm.setBackgroundResource(R.mipmap.warm);
            }
        });
    }
}
