package com.example.caikaxuetang;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;

import com.example.caikaxuetang.chatactivitys.activity.FilePreviewActivity;
import com.example.caikaxuetang.mediaplayer.FloatWindow;
import com.example.caikaxuetang.mediaplayer.MediaPlayerHolder;
import com.example.caikaxuetang.mediaplayer.OpenMusicActivity;
import com.example.caikaxuetang.utils.MyTextMessageItemProvider;
import com.example.caikaxuetang.utils.ToastUtils;
import com.example.caikaxuetang.utils.UpdateService;
import com.llw.mvplibrary.ActivityManager;
import com.llw.mvplibrary.BaseApplication;
import com.llw.mvplibrary.network.NetworkApi;
import com.llw.mvplibrary.network.utils.SpUtils;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;
import com.scwang.smartrefresh.header.PhoenixHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;
import com.xuexiang.xupdate.XUpdate;
import com.xuexiang.xupdate.entity.UpdateError;
import com.xuexiang.xupdate.listener.OnUpdateFailureListener;
import com.xuexiang.xupdate.utils.UpdateUtils;

import java.util.HashMap;

import io.rong.imkit.RongIM;
import io.rong.imkit.config.RongConfigCenter;
import io.rong.imkit.conversation.extension.RongExtensionManager;
import io.rong.imkit.conversation.messgelist.provider.TextMessageItemProvider;
import io.rong.imkit.userinfo.RongUserInfoManager;
import io.rong.imkit.userinfo.model.GroupUserInfo;
import io.rong.imkit.utils.RouteUtils;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.UserInfo;
import io.rong.push.RongPushClient;
import io.rong.push.pushconfig.PushConfig;
import io.rong.sight.SightExtensionModule;

public class MyApplication extends Application {


    public static Context context;

    private String appKey = "uwd1c0sxukbo1";//融云appkey
    public static MediaPlayerHolder mediaPlayerIngHolder;
    public static LoadingPopupView loadingPopupView;
    public static IWXAPI api;
    public static final String APP_ID = "wx9d916aaeda94c3b9";//微信
    private static ActivityManager activityManager;
    public static final String WEB_SOCKET = "ws://ycapi.xicaishe.com/studyrecord/";

    @Override
    public void onCreate() {
        super.onCreate();
        activityManager = new ActivityManager();

        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.tab_unseclet, android.R.color.black);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
        regToWx();
        //初始化
        NetworkApi.init(new NetworkRequiredInfo(this));
        //悬浮播放器
        FloatWindow.with(this)//application上下文
                .setLayoutId(R.layout.easy_float)//悬浮布局
                .setFilter(OpenMusicActivity.class)
                .build();
        loadingPopupView = new XPopup.Builder(this)
                .asLoading()
                .setTitle("加载中");
        mediaPlayerIngHolder = new MediaPlayerHolder();
        PushConfig config = new PushConfig.Builder()
                .build();
        RongPushClient.setPushConfig(config);
        RongIM.init(this, appKey);
        RongExtensionManager.getInstance().registerExtensionModule(new SightExtensionModule());
        RongConfigCenter.conversationConfig().replaceMessageProvider(TextMessageItemProvider.class, new MyTextMessageItemProvider());
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }


    private void regToWx() {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, APP_ID, true);

        //建议动态监听微信启动广播进行注册到微信
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // 将该app注册到微信
                api.registerApp(APP_ID);
            }
        }, new IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP));

    }

    //微信登录
    public static void wxLogin() {
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo_test";
        api.sendReq(req);
    }

    public static ActivityManager getActivityManager() {
        return activityManager;
    }
}
