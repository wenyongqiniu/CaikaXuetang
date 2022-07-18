package com.example.caikaxuetang.adapters;


import android.app.Service;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.caikaxuetang.R;
import com.example.caikaxuetang.myactivitys.HetongActivity;
import com.example.caikaxuetang.responses.MyClassResponse;
import com.example.caikaxuetang.utils.DonwloadSaveImg;
import com.github.barteksc.pdfviewer.PDFView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MyclassAdapter extends BaseQuickAdapter<MyClassResponse.DataBean, BaseViewHolder> {


    public MyclassAdapter(int layoutResId, @Nullable List<MyClassResponse.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, MyClassResponse.DataBean dataBean) {
        TextView tv_time = baseViewHolder.getView(R.id.tv_time);
        TextView tv_class = baseViewHolder.getView(R.id.tv_class);
        ImageView iv_rc_code = baseViewHolder.getView(R.id.iv_rc_code);

        tv_time.setText("有效期：" + dataBean.getStartTime() + "至" + dataBean.getEndTime());
        tv_class.setText(dataBean.getCampName() + dataBean.getClassName());
        Glide.with(getContext()).load(dataBean.getTeacherQrCode()).into(iv_rc_code);

        //保存图片
        iv_rc_code.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Vibrator vib = (Vibrator) getContext().getSystemService(Service.VIBRATOR_SERVICE);
                vib.vibrate(200);//只震动一秒，一次
                DonwloadSaveImg.donwloadImg(getContext(), dataBean.getTeacherQrCode());
                return false;
            }
        });

    }

}
