package com.example.caikaxuetang.adapters;


import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.caikaxuetang.R;
import com.example.caikaxuetang.myactivitys.HetongActivity;
import com.example.caikaxuetang.responses.HetongResponse;
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

public class HetongAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public HetongAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String dataBean) {

        PDFView pdfView = baseViewHolder.getView(R.id.pdf);

        String path = getContext().getFilesDir().getAbsolutePath();
        String outFilePath = path + System.currentTimeMillis() + ".pdf";
        HetongActivity.HttpOkhUtils.getInstance().download(dataBean,
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        //下载功能
                        InputStream inputStream = response.body().byteStream();
                        FileOutputStream outputStream = new FileOutputStream(new File(outFilePath));
                        byte[] by = new byte[2048];
                        int len = 0;
                        while ((len = inputStream.read(by)) != -1) {
                            outputStream.write(by, 0, len);
                        }
                        outputStream.flush();
                        pdfView.fromFile(new File(outFilePath))
                                //默认加载第0页
                                .defaultPage(0)
                                //支持印章等 格式
                                .enableAnnotationRendering(true)
                                .scrollHandle(null)
                                .load();
                        pdfView.resetZoom();
                    }
                });

    }

}
