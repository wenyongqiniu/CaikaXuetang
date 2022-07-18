package com.example.caikaxuetang.chatactivitys.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.caikaxuetang.R;
import com.example.caikaxuetang.contract.MainContract;
import com.example.caikaxuetang.responses.GroupInfo;
import com.example.caikaxuetang.responses.GroupNoticeBean;
import com.example.caikaxuetang.responses.VersionEntity;
import com.example.caikaxuetang.responses.WallPaperResponse;
import com.github.barteksc.pdfviewer.PDFView;
import com.llw.mvplibrary.mvp.MvpActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PdfActivity extends MvpActivity<MainContract.MainPresenter> implements MainContract.IMainView {


    @Override
    protected MainContract.MainPresenter createPresenter() {
        return new MainContract.MainPresenter();
    }

    @Override
    public void getWallPaper(WallPaperResponse wallPaperResponse) {

    }

    @Override
    public void getGroupInfo(GroupInfo groupInfo) {

    }

    @Override
    public void getGroupNotice(GroupNoticeBean groupInfo) {

    }

    @Override
    public void getCheckUpdate(VersionEntity groupInfo) {

    }

    @Override
    public void getFailed(Throwable e) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String mName = intent.getStringExtra("mName");

        TextView tv_title_top = findViewById(R.id.tv_title_top);
        RelativeLayout rl_left = findViewById(R.id.rl_left);
        tv_title_top.setText("财咖学堂");

        rl_left.setOnClickListener(view -> finish());
        PDFView pdfView = findViewById(R.id.pdf);
        String path = this.getFilesDir().getAbsolutePath();
        String outFilePath = path + System.currentTimeMillis() + ".pdf";
        HttpOkhUtils.getInstance().download(mName,
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

    @Override
    public int getLayoutId() {
        return R.layout.activity_pdf;
    }

    public static class HttpOkhUtils {
        // 网络请求超时时间值(s)
        private static final int DEFAULT_TIMEOUT = 30;
        private static HttpOkhUtils okhUtils;
        private OkHttpClient client;

        private HttpOkhUtils() {
            client = new OkHttpClient.Builder()
                    .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .build();
        }

        public static HttpOkhUtils getInstance() {
            if (okhUtils == null) {
                synchronized (HttpOkhUtils.class) {
                    if (okhUtils == null)
                        okhUtils = new HttpOkhUtils();
                }
            }
            return okhUtils;
        }

        public void download(String url, Callback callback) {
            Request request = new Request.Builder().url(url).get().build();
            Call call = client.newCall(request);
            call.enqueue(callback);
        }
    }
}