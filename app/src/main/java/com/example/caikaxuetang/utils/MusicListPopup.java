package com.example.caikaxuetang.utils;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caikaxuetang.R;
import com.example.caikaxuetang.adapters.MusicListAdapter;
import com.example.caikaxuetang.mediaplayer.OpenMusicActivity;
import com.example.caikaxuetang.responses.SectionDetailResponse;
import com.lxj.xpopup.core.BottomPopupView;

import java.util.ArrayList;

import static com.example.caikaxuetang.MyApplication.mediaPlayerIngHolder;


public class MusicListPopup extends BottomPopupView {
    RecyclerView recyclerView;
    private ArrayList<SectionDetailResponse.AudioList> data;
    private Context context;
    public static MusicListAdapter musicListAdapter;


    public MusicListPopup(@NonNull Context context, ArrayList<SectionDetailResponse.AudioList> list) {
        super(context);
        this.context = context;
        this.data = list;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_window_layout;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        recyclerView = findViewById(R.id.rv_music_list);
        musicListAdapter = new MusicListAdapter(R.layout.item_music_list, data);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(musicListAdapter);

        musicListAdapter.setOnItemClickListener((adapter, view, position) -> {
            mediaPlayerIngHolder.loadMedia(data.get(position).getAudioUrl());
            for (int i = 0; i < data.size(); i++) {
                data.get(i).setPlayStatus(0);
            }
            data.get(position).setPlayStatus(1);
            OpenMusicActivity.tv_musci_title.setText(data.get(position).getSectionName());
            musicListAdapter.notifyDataSetChanged();
        });
    }

    //完全可见执行
    @Override
    protected void onShow() {
        super.onShow();
        Log.e("tag", "知乎评论 onShow");
    }

    //完全消失执行
    @Override
    protected void onDismiss() {
        Log.e("tag", "知乎评论 onDismiss");
    }

   /* @Override
    protected int getMaxHeight() {
        return (int) (ScreenUtils.getScreenHeight(getContext()) * .5f);
    }*/
}