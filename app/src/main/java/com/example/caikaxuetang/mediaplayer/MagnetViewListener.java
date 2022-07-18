package com.example.caikaxuetang.mediaplayer;

import android.view.MotionEvent;


public interface MagnetViewListener {

    void onRemove(FloatingMagnetView magnetView);

    void onClick(MotionEvent event);
}
