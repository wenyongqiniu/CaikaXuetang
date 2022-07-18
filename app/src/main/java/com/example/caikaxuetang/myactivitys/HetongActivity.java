package com.example.caikaxuetang.myactivitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.caikaxuetang.R;
import com.example.caikaxuetang.adapters.HetongAdapter;
import com.example.caikaxuetang.chatactivitys.activity.PdfActivity;
import com.example.caikaxuetang.contract.HetongContract;
import com.example.caikaxuetang.responses.HetongResponse;
import com.example.caikaxuetang.responses.MyClassResponse;
import com.github.barteksc.pdfviewer.PDFView;
import com.ljb.page.PageState;
import com.ljb.page.PageStateLayout;
import com.llw.mvplibrary.mvp.MvpActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HetongActivity extends MvpActivity<HetongContract.MainPresenter> implements HetongContract.IMainView {


    private PDFView pdfView;
    private RecyclerView rv_hetong;
    private PageStateLayout page_layout;

    @Override
    protected HetongContract.MainPresenter createPresenter() {
        return new HetongContract.MainPresenter();
    }

    @Override
    public void getWallPaper(HetongResponse wallPaperResponse) {
        if (wallPaperResponse.getCode() == 0) {
            List<String> data = wallPaperResponse.getData();
            HetongAdapter hetongAdapter = new HetongAdapter(R.layout.item_hetong, data);
            rv_hetong.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
            rv_hetong.setAdapter(hetongAdapter);
            hetongAdapter.notifyDataSetChanged();
            page_layout.setPage(PageState.STATE_SUCCESS);
        } else {
            page_layout.setPage(PageState.STATE_EMPTY);
        }

    }

    @Override
    public void getMyClass(MyClassResponse wallPaperResponse) {

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

    @Override
    public void getFailed(Throwable e) {
        page_layout.setPage(PageState.STATE_ERROR);
        Log.e("TAG", "getFailed: " + e.getMessage());
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        page_layout = findViewById(R.id.page_layout);

        page_layout.setContentView(R.layout.activity_hetong);

        TextView tv_title_top = findViewById(R.id.tv_title_top);
        RelativeLayout rl_left = findViewById(R.id.rl_left);
        tv_title_top.setText("我的合同");

        rl_left.setOnClickListener(view -> finish());
        pdfView = findViewById(R.id.pdf);
        rv_hetong = findViewById(R.id.rv_hetong);
        mPresenter.getHetong();
        page_layout.setPage(PageState.STATE_LOADING);
    }

    @Override
    public int getLayoutId() {
        return R.layout.base_layout;
    }
}