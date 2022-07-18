package com.example.caikaxuetang.adapters;

import android.graphics.Color;
import android.widget.TextView;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.caikaxuetang.R;
import com.example.caikaxuetang.responses.SectionDetailResponse;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MusicListAdapter extends BaseQuickAdapter<SectionDetailResponse.AudioList, BaseViewHolder> {

    private int selectedPosition = -1;

    public MusicListAdapter(int layoutResId, @Nullable List<SectionDetailResponse.AudioList> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, SectionDetailResponse.AudioList dataBean) {
        TextView tv_music_name = baseViewHolder.getView(R.id.tv_music_name);
        tv_music_name.setText(dataBean.getSectionName());

        if (dataBean.getPlayStatus() == 1 ) {
            tv_music_name.setTextColor(Color.parseColor("#FEA400"));
        } else {
            tv_music_name.setTextColor(Color.parseColor("#000000"));
        }


    }

}
