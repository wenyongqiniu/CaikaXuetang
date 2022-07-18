package com.example.caikaxuetang.chatactivitys.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.caikaxuetang.R;
import com.just.agentweb.AgentWebView;
import com.llw.mvplibrary.BaseApplication;


public class BuyDaKeActivity extends AppCompatActivity {

    private AgentWebView mIvPost;
    private RelativeLayout rl_back;
    private String user_token;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_da_ke);
        initView();
    }

    @SuppressLint("JavascriptInterface")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initView() {
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(this.getResources().getColor(R.color.white));
        Intent intent = getIntent();
        String postUrl = intent.getStringExtra("postUrl");
        String title = intent.getStringExtra("title");
        mIvPost = findViewById(R.id.iv_post);
        rl_back = findViewById(R.id.rl_left);
        TextView topTitle = findViewById(R.id.tv_title_top);
        topTitle.setText(title);

        //允许JavaScript执行
        WebSettings settings = mIvPost.getSettings();
        settings.setJavaScriptEnabled(true);

        settings.setDomStorageEnabled(false);
        settings.setDatabaseEnabled(false);
        settings.setAppCacheEnabled(false);

        settings.setUseWideViewPort(true); //-> 缩放至屏幕大小
        settings.setLoadWithOverviewMode(true); //-> 缩放至屏幕大小
        settings.setSupportZoom(false);// -> 是否支持缩放
        settings.setBuiltInZoomControls(false);// -> 是否支持缩放变焦，前提是支持缩放
        settings.setDisplayZoomControls(true); //-> 是否隐藏缩放控件
        settings.setMediaPlaybackRequiresUserGesture(false); //-> 是否要手势触发媒体

        mIvPost.setOnLongClickListener(v -> true);//禁用长按复制
        mIvPost.setLongClickable(false);

        //不加这个图片显示不出来
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            mIvPost.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mIvPost.getSettings().setBlockNetworkImage(false);

        //找到Html文件，也可以用网络上的文件

        mIvPost.loadUrl(postUrl);


        settings.setJavaScriptCanOpenWindowsAutomatically(false);


        rl_back.setOnClickListener(v -> finish());
        mIvPost.setWebViewClient(new MyWebViewClient());
        user_token = BaseApplication.token;
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView webView, String url) {
            //执行代码
            mIvPost.loadUrl("javascript:getapptoken('" + user_token + "','" + 2 + "')");
        }
    }
}