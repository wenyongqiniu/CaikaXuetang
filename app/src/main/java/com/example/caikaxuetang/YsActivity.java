package com.example.caikaxuetang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.caikaxuetang.contract.MainContract;
import com.example.caikaxuetang.responses.GroupInfo;
import com.example.caikaxuetang.responses.GroupNoticeBean;
import com.example.caikaxuetang.responses.VersionEntity;
import com.example.caikaxuetang.responses.WallPaperResponse;
import com.just.agentweb.WebViewClient;
import com.llw.mvplibrary.mvp.MvpActivity;

public class YsActivity extends MvpActivity<MainContract.MainPresenter> implements MainContract.IMainView {

    private WebView urlWebView;

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
        String ystype = intent.getStringExtra("ystype");
        urlWebView = findViewById(R.id.web);
        TextView tv_title_top = findViewById(R.id.tv_title_top);
        RelativeLayout rl_left = findViewById(R.id.rl_left);
        tv_title_top.setText("财咖学堂");

        rl_left.setOnClickListener(view -> finish());
        urlWebView.setWebViewClient(new AppWebViewClients());
        urlWebView.getSettings().setJavaScriptEnabled(true);
        urlWebView.getSettings().setUseWideViewPort(true);


        if (ystype.equals("ys")) {
            urlWebView.loadUrl("https://image.xicaishe.com/1657862338962_vQMyOQ.html");
        } else {
            urlWebView.loadUrl("https://image.xicaishe.com/index-2.html");

        }

    }

    public class AppWebViewClients extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(android.webkit.WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_ys;
    }
}