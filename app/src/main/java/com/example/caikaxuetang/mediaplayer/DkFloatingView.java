package com.example.caikaxuetang.mediaplayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.Region;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@SuppressLint("ViewConstructor")
public class DkFloatingView extends FloatingMagnetView implements MagnetViewListener {

    private final View mInflate;
    public DkFloatingView(@NonNull Context context, @LayoutRes int resource, ViewGroup.LayoutParams layoutParam) {
        super(context, null);
        mInflate = inflate(context, resource, this);
        setLayoutParams(layoutParam == null ? getParams(): layoutParam);
    }

    private LayoutParams getParams() {
        LayoutParams params = new LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM | Gravity.START;
        params.setMargins(10, params.topMargin, params.rightMargin, 250);
        return params;
    }

    private final HashMap<View, Region> regionHashMap = new HashMap<>();
    private void clickView(View view){
        if (view == null){
            return;
        }
        if (view instanceof ViewGroup){
            view.setClickable(false);
            int childCount = ((ViewGroup) view).getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = ((ViewGroup) view).getChildAt(i);
                clickView(childAt);
            }
        }else {
            view.setClickable(false);
            Rect rect = new Rect();
            view.getHitRect(rect);
            Region region = new Region(rect);
            regionHashMap.put(view, region);
        }
    }

    public View getView(){
        return mInflate;
    }

    private ViewClickListener mListener;
    public void setOnClickListener(ViewClickListener listener) {
        this.mListener = listener;
        mInflate.post(new Runnable() {
            @Override
            public void run() {
                regionHashMap.clear();
                clickView(mInflate);
                setMagnetViewListener(DkFloatingView.this);
            }
        });
    }

    @Override
    public void onRemove(FloatingMagnetView magnetView) {
        regionHashMap.clear();
    }

    @Override
    public void onClick(MotionEvent event) {
        Set<Map.Entry<View, Region>> entries = regionHashMap.entrySet();
        for (Map.Entry<View, Region> entry : entries) {
            if (entry.getValue().contains((int)event.getX(), (int)event.getY())){
                if (mListener != null){
                    mListener.onClick(entry.getKey());
                }
                break;
            }
        }
    }

    public interface ViewClickListener {
        void onClick(View viewId);
    }
}
