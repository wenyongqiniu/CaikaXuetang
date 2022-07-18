package com.example.caikaxuetang.mediaplayer;

import android.view.View;


public interface IFloatingView {

    void show();
    void hide();

    View getView();

    void setOnClickListener(DkFloatingView.ViewClickListener listener);

}
