package com.example.caikaxuetang.chatactivitys.activity;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.bumptech.glide.Glide;
import com.example.caikaxuetang.R;
import com.example.caikaxuetang.contract.MainContract;
import com.example.caikaxuetang.learningActivitys.XbSectionDetailActivity;
import com.example.caikaxuetang.responses.GroupInfo;
import com.example.caikaxuetang.responses.GroupNoticeBean;
import com.example.caikaxuetang.responses.VersionEntity;
import com.example.caikaxuetang.responses.WallPaperResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.llw.mvplibrary.mvp.MvpActivity;
import com.llw.mvplibrary.network.utils.SpUtils;
import com.wanglu.photoviewerlibrary.PhotoViewer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.regex.Pattern;

import io.rong.imkit.IMCenter;
import io.rong.imkit.RongIM;
import io.rong.imkit.activity.FilePreviewActivity;
import io.rong.imkit.config.ConversationClickListener;
import io.rong.imkit.config.RongConfigCenter;
import io.rong.imkit.conversation.ConversationFragment;
import io.rong.imkit.conversation.extension.RongExtensionManager;
import io.rong.imkit.userinfo.RongUserInfoManager;
import io.rong.imkit.userinfo.UserDataProvider;
import io.rong.imkit.userinfo.model.GroupUserInfo;
import io.rong.imkit.utils.RouteUtils;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.MentionedInfo;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;
import io.rong.message.FileMessage;
import io.rong.message.MediaMessageContent;

public class GroupConversationActivity extends MvpActivity<MainContract.MainPresenter> implements MainContract.IMainView, View.OnClickListener {


    private TextView mGroupGongGao;
    private GroupNoticeBean.DataBean data;
    private ImageView iv_big_img;
    private String mUserId = "";
    private String mGroupIdId = "";

    @Override
    public void initData(Bundle savedInstanceState) {

        RelativeLayout rl_group_info = findViewById(R.id.rl_group_info);
        RelativeLayout rl_left = findViewById(R.id.rl_left);
        TextView tv_title_top = findViewById(R.id.tv_title_top);
        mGroupGongGao = findViewById(R.id.tv_group_gonggao);
        iv_big_img = findViewById(R.id.iv_big_img);
        String title = SpUtils.getSpString(this, "title", "");
        String targetId = SpUtils.getSpString(this, "targetId", "");
        tv_title_top.setText(title);
        rl_group_info.setOnClickListener(this);
        rl_left.setOnClickListener(this);
        //mPresenter.getNotice(targetId);
        //点击查看群公告
        mGroupGongGao.setOnClickListener(v -> {
            mGroupGongGao.setVisibility(View.GONE);
            showNoticePop();
        });

        // 添加会话界面
        ConversationFragment conversationFragment = new ConversationFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, conversationFragment);
        transaction.commit();

        mPresenter.getNotice(targetId);
        IMCenter.getInstance().addOnReceiveMessageListener(new RongIMClient.OnReceiveMessageWrapperListener() {
            @Override
            public boolean onReceived(Message message, int i, boolean b, boolean b1) {
                if (message != null && message.getContent() != null && message.getContent().getMentionedInfo() != null) {
                    MentionedInfo.MentionedType type = message.getContent().getMentionedInfo().getType();
                    Log.e("content", "onReceived: " + message.getContent());
                    if (type.getValue() == 1) {
                        //mGroupGongGao.setVisibility(View.VISIBLE);
                        mPresenter.getNotice(targetId);
                    } else {
                        //mGroupGongGao.setVisibility(View.GONE);
                    }
                }
                return false;
            }
        });


        //事件监听
        IMCenter.setConversationClickListener(new ConversationClickListener() {
            @Override
            public boolean onUserPortraitClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo, String s) {
                return false;
            }

            @Override
            public boolean onUserPortraitLongClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo, String s) {
                return false;
            }

            @Override
            public boolean onMessageClick(Context context, View view, Message message) {
                MessageContent content = message.getContent();
                String s = new Gson().toJson(content);
                String mType = "";
                String mName = "";
                String mediaUrl = "";

                try {
                    JSONObject jsonObject1 = new JSONObject(s);
                    mType = jsonObject1.getString("mType");
                    mName = jsonObject1.getString("mName");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if ("mp4".equals(mType) || "mp3".equals(mType) || "mkv".equals(mType) || "rmvb".equals(mType) || "wmv".equals(mType) || "wav".equals(mType) || "video/mp4".equals(mType) || "video/x-ms-wmv".equals(mType)) {
                    if (message != null) {
                        MediaMessageContent mContent = (MediaMessageContent) message.getContent();
                        mediaUrl = mContent.getMediaUrl().toString();
                    }
                    Intent intent = new Intent(GroupConversationActivity.this, Mp4Activity.class);
                    intent.putExtra("mName", mediaUrl);
                    startActivity(intent);
                    return true;
                } else if ("jpg".equals(mType) || "png".equals(mType) || "jpeg".equals(mType)) {
                    if (message != null) {
                        MediaMessageContent mContent = (MediaMessageContent) message.getContent();
                        mediaUrl = mContent.getMediaUrl().toString();
                    }
                    String finalMediaUrl = mediaUrl;
                    runOnUiThread(() -> PhotoViewer.INSTANCE
                            .setClickSingleImg(finalMediaUrl, iv_big_img)
                            .setShowImageViewInterface((imageView, url) -> {
                                //使用Glide显示图片
                                Glide.with(GroupConversationActivity.this)
                                        .load(finalMediaUrl)
                                        .into(imageView);
                            }).start(GroupConversationActivity.this));
                    return true;
                } else if ("".equals(mType)) {

                    return false;
                } else if ("pdf".equals(mType) || "application/pdf".equals(mType)) {
                    if (message != null) {
                        MediaMessageContent mContent = (MediaMessageContent) message.getContent();
                        mediaUrl = mContent.getMediaUrl().toString();
                    }
                    Intent intent = new Intent(GroupConversationActivity.this, PdfActivity.class);
                    intent.putExtra("mName", mediaUrl);
                    startActivity(intent);

                    return true;

                } else {
                    if (message != null) {
                        MediaMessageContent mContent = (MediaMessageContent) message.getContent();
                        mediaUrl = mContent.getMediaUrl().toString();
                    }
                    Intent intent = new Intent(GroupConversationActivity.this, ChatWebViewActivity.class);
                    intent.putExtra("mName", mediaUrl);
                    startActivity(intent);

                    return true;
                }

            }

            @Override
            public boolean onMessageLongClick(Context context, View view, Message message) {
                return false;
            }

            @Override
            public boolean onMessageLinkClick(Context context, String s, Message message) {
                if (message.getContent() != null) {

                }
                return false;
            }

            @Override
            public boolean onReadReceiptStateClick(Context context, Message message) {
                return false;
            }
        });
        String nickName = SpUtils.getSpString(context, "nickName", "");
        String headImg = SpUtils.getSpString(context, "headImg", "");
        String ryId = SpUtils.getSpString(context, "ryId", "");
        RongIM.getInstance().setMessageAttachedUserInfo(true);
       /* UserInfo userInfo = new UserInfo(ryId, nickName, Uri.parse(headImg));
        RongIM.getInstance().setCurrentUserInfo(userInfo);*/

        //设置用户个人信息
        /*  *
         * 获取设置用户信息. 通过返回的 userId 来封装生产用户信息.
         * @param userId 用户 ID*/
        RongUserInfoManager.getInstance().setUserInfoProvider(userId -> {
            mPresenter.getWallPaper(userId);

            return null;
        }, true);

    }


    private void showNoticePop() {
        View view = LayoutInflater.from(this).inflate(R.layout.notice_pop, null);
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView tv_notice_content = view.findViewById(R.id.tv_notice_content);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams param = getWindow().getAttributes();
                param.alpha = 1f;
                getWindow().setAttributes(param);
            }
        });
        tv_notice_content.setText(data.getAnnouncementContent());

        CharSequence text = tv_notice_content.getText();
        if (text instanceof Spannable) {
            int end = text.length();
            Spannable sp = (Spannable) text;
            URLSpan urls[] = sp.getSpans(0, end, URLSpan.class);
            SpannableStringBuilder style = new SpannableStringBuilder(text);
            style.clearSpans();
            for (URLSpan urlSpan : urls) {
                MyURLSpan myURLSpan = new MyURLSpan(urlSpan.getURL());
                style.setSpan(myURLSpan, sp.getSpanStart(urlSpan),
                        sp.getSpanEnd(urlSpan),
                        Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

            }
            tv_notice_content.setText(style);
        }
        backgroundAlpha(0.5f);
        popupWindow.showAtLocation(this.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        TextView tvGetIt = view.findViewById(R.id.tv_get_it);
        tvGetIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    private class MyURLSpan extends ClickableSpan {

        private String url;

        public MyURLSpan(String url) {
            this.url = url;
        }

        @Override
        public void onClick(View arg0) {
            Intent intent1 = new Intent(GroupConversationActivity.this, BuyDaKeActivity.class);
            intent1.putExtra("postUrl", url);
            intent1.putExtra("title", "财咖学堂");
            startActivity(intent1);
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_conversation;
    }


    @Override
    public void getWallPaper(WallPaperResponse wallPaperResponse) {
        if (wallPaperResponse.getCode() == 0) {
            WallPaperResponse.DataBean datas = wallPaperResponse.getData();
            UserInfo userInfo = new UserInfo(datas.getId(), datas.getNickname(), Uri.parse(datas.getHeadImage()));
            RongUserInfoManager.getInstance().refreshUserInfoCache(userInfo);
        }

    }

    @Override
    public void getGroupInfo(GroupInfo groupInfo) {

    }

    @Override
    public void getGroupNotice(GroupNoticeBean groupInfo) {
        if (groupInfo.getCode() == 0) {
            data = groupInfo.getData();
            if (!data.getHaveViewed()) {//需要展示群公告
                mGroupGongGao.setVisibility(View.VISIBLE);
                mGroupGongGao.setText(Html.fromHtml(data.getAnnouncementContent() + ""));
            } else {
                mGroupGongGao.setVisibility(View.GONE);
            }

        }

    }

    @Override
    public void getCheckUpdate(VersionEntity groupInfo) {

    }

    @Override
    public void getFailed(Throwable e) {

        Log.e("TAG", "getFailed: " + e.getMessage());
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    /**
     * 当天晚上8点的毫秒时间戳
     *
     * @return
     */
    private Long getMillisNextEarlyNight() {
        Calendar cal = Calendar.getInstance();
        //日期+1为第二天，0为当天，-1为前一天
        cal.add(Calendar.DAY_OF_YEAR, 0);
        //时间设定到8点整
        cal.set(Calendar.HOUR_OF_DAY, 20);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    /**
     * 当天早上8点的毫秒时间戳
     *
     * @return
     */
    private Long getMillisNextEarlyMorning() {
        Calendar cal = Calendar.getInstance();
        //日期+1为第二天，0为当天，-1为前一天
        cal.add(Calendar.DAY_OF_YEAR, 0);
        //时间设定到8点整
        cal.set(Calendar.HOUR_OF_DAY, 8);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    /**
     * 当天中午12点的毫秒时间戳
     *
     * @return
     */
    private Long getMillisNextEarlyNoon() {
        Calendar cal = Calendar.getInstance();
        //日期+1为第二天，0为当天，-1为前一天
        cal.add(Calendar.DAY_OF_YEAR, 0);
        //时间设定到8点整
        cal.set(Calendar.HOUR_OF_DAY, 12);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    //域名后缀，需要可以添加
    private static final String[] come = {
            "top", "com.cn", "com", "net", "cn", "cc", "gov", "cn", "hk"
    };

    public static boolean isWebUrl(String url) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (String anExt : come) {
            sb.append(anExt);
            sb.append("|");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");
        String pattern = "((https?|s?ftp|irc[6s]?|git|afp|telnet|smb)://)?((\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})|((www\\.|[a-zA-Z\\.\\-]+\\.)?[a-zA-Z0-9\\-]+\\." + sb.toString() + "(:[0-9]{1,5})?))((/[a-zA-Z0-9\\./,;\\?'\\+&%\\$#=~_\\-]*)|([^\\u4e00-\\u9fa5\\s0-9a-zA-Z\\./,;\\?'\\+&%\\$#=~_\\-]*))";
        return Pattern.compile(pattern).matcher(url).matches();
    }

    @Override
    protected MainContract.MainPresenter createPresenter() {
        return new MainContract.MainPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        IMCenter.getInstance().removeOnReceiveMessageListener(new RongIMClient.OnReceiveMessageWrapperListener() {
            @Override
            public boolean onReceived(Message message, int i, boolean b, boolean b1) {

                return false;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_group_info:
                Intent intent = new Intent(GroupConversationActivity.this, GroupInfoActivity.class);
                if (data != null) {
                    intent.putExtra("noticeContent", data.getAnnouncementContent() + "");
                } else {
                    intent.putExtra("noticeContent", "");
                }
                startActivity(intent);
                break;
            case R.id.rl_left:
                finish();
                break;
        }

    }
}
