package com.example.caikaxuetang.adapters;


import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.caikaxuetang.R;
import com.example.caikaxuetang.responses.XbCourseListResponse;
import com.example.caikaxuetang.utils.MyDecoration;
import com.example.caikaxuetang.utils.ViewUtils;


import java.util.List;

/**
 * 壁纸适配器
 * @author llw
 */
public class XbCourseListAdapter extends BaseQuickAdapter<XbCourseListResponse.DataBean.ChapterListBean, BaseViewHolder> {

    public XbCourseListAdapter(List<XbCourseListResponse.DataBean.ChapterListBean> data) {
        super(R.layout.item_xb_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, XbCourseListResponse.DataBean.ChapterListBean item) {

        TextView tv_day = helper.getView(R.id.tv_day);
        RecyclerView rv_xb_list_title = helper.getView(R.id.rv_xb_list_title);
        tv_day.setText(item.getChapterName());

        XbCourseTitleAdapter xbCourseTitleAdapter = new XbCourseTitleAdapter(item.getSectionList(),item.getChapterId());
        xbCourseTitleAdapter.notifyDataSetChanged();
        MyDecoration myDecoration = new MyDecoration();

        myDecoration.setColor(ContextCompat.getColor(getContext(),R.color.divider)).setMargin(ViewUtils.dip2px(getContext(), 21)).setDividerHeight(ViewUtils.dip2px(getContext(),1));

        rv_xb_list_title.addItemDecoration(myDecoration);

        rv_xb_list_title.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_xb_list_title.setAdapter(xbCourseTitleAdapter);


    }
}
