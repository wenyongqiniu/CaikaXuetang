package com.example.caikaxuetang.chatactivitys.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Picture;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caikaxuetang.R;
import com.example.caikaxuetang.contract.MainContract;
import com.example.caikaxuetang.responses.GroupInfo;
import com.example.caikaxuetang.responses.GroupNoticeBean;
import com.example.caikaxuetang.responses.VersionEntity;
import com.example.caikaxuetang.responses.WallPaperResponse;
import com.example.caikaxuetang.utils.WebViewSeetings;
import com.github.barteksc.pdfviewer.PDFView;
import com.just.agentweb.AgentWebView;
import com.just.agentweb.WebViewClient;
import com.llw.mvplibrary.mvp.MvpActivity;
import com.tencent.smtt.sdk.WebSettings;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ChatWebViewActivity extends MvpActivity<MainContract.MainPresenter> implements MainContract.IMainView  {

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
        urlWebView = findViewById(R.id.web);
        Intent intent = getIntent();
        String mName = intent.getStringExtra("mName");
        TextView tv_title_top = findViewById(R.id.tv_title_top);
        RelativeLayout rl_left = findViewById(R.id.rl_left);
        tv_title_top.setText("财咖学堂");

        rl_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        urlWebView.setWebViewClient(new AppWebViewClients());
        urlWebView.getSettings().setJavaScriptEnabled(true);
        urlWebView.getSettings().setUseWideViewPort(true);
        // https://view.officeapps.live.com/op/view.aspx?src
        String htt = "https://view.officeapps.live.com/op/view.aspx?src="+mName;
        urlWebView.loadUrl(htt);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_chat_web_view;
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



}