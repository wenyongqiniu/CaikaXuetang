package com.example.caikaxuetang.mediaplayer;

/**
 * @date: 2019/6/21 0021
 * @author: gaoxiaoxiong
 * @description:播放回调
 **/
public interface PlaybackInfoListener {
    void onTotalDuration(int duration);//总时长

    void onPositionChanged(int position);//当前时长进度

    void onStateChanged(int state);//记录当前的状态

    void onPlaybackCompleted();//播放完成回调

    void onLoadPrepared();//播放完成回调
}
