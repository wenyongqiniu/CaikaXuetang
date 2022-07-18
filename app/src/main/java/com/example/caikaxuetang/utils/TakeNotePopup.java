package com.example.caikaxuetang.utils;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.caikaxuetang.R;
import com.google.android.material.textfield.TextInputEditText;
import com.lxj.xpopup.core.BottomPopupView;


public class TakeNotePopup extends BottomPopupView {


    public static TextInputEditText ed_note;
    public static ImageView iv_close;
    public static TextView tv_keep_note;

    public TakeNotePopup(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_take_note_layout;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ed_note = findViewById(R.id.ed_note);
        tv_keep_note = findViewById(R.id.tv_keep_note);
        iv_close = findViewById(R.id.iv_close);
        iv_close.setOnClickListener(v -> dismiss());
    }

    //完全可见执行
    @Override
    protected void onShow() {
        super.onShow();
    }

    //完全消失执行
    @Override
    protected void onDismiss() {
        Log.e("tag", "知乎评论 onDismiss");
    }

   /* @Override
    protected int getMaxHeight() {
        return (int) (ScreenUtils.getScreenHeight(getContext()) * .1f);
    }*/

}