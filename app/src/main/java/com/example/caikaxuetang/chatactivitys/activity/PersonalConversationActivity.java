package com.example.caikaxuetang.chatactivitys.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.bumptech.glide.Glide;
import com.example.caikaxuetang.R;
import com.example.caikaxuetang.contract.MainContract;
import com.example.caikaxuetang.responses.GroupInfo;
import com.example.caikaxuetang.responses.GroupNoticeBean;
import com.example.caikaxuetang.responses.VersionEntity;
import com.example.caikaxuetang.responses.WallPaperResponse;
import com.google.gson.Gson;
import com.llw.mvplibrary.mvp.MvpActivity;
import com.llw.mvplibrary.network.utils.SpUtils;
import com.wanglu.photoviewerlibrary.PhotoViewer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

import io.rong.imkit.IMCenter;
import io.rong.imkit.RongIM;
import io.rong.imkit.config.ConversationClickListener;
import io.rong.imkit.config.RongConfigCenter;
import io.rong.imkit.conversation.ConversationFragment;
import io.rong.imkit.conversation.extension.RongExtensionManager;
import io.rong.imkit.userinfo.RongUserInfoManager;
import io.rong.imkit.userinfo.UserDataProvider;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;
import io.rong.message.MediaMessageContent;

public class PersonalConversationActivity extends MvpActivity<MainContract.MainPresenter> implements MainContract.IMainView {

    private ImageView iv_big_img;

    //域名后缀，需要可以添加
    private static final String[] come = {
            "top", "com.cn", "com", "net", "cn", "cc", "gov", "cn", "hk"
    };
    private String mUserId="";

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
    public void getWallPaper(WallPaperResponse wallPaperResponse) {
        if (wallPaperResponse.getCode() == 0) {
            WallPaperResponse.DataBean data = wallPaperResponse.getData();
            UserInfo userInfo = new UserInfo(mUserId, data.getNickname(), Uri.parse(data.getHeadImage()));
            RongUserInfoManager.getInstance().refreshUserInfoCache(userInfo);
        }
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
        String title = SpUtils.getSpString(this, "title", "");
        iv_big_img = findViewById(R.id.iv_big_img);
        TextView tv_title_top = findViewById(R.id.tv_title_top);
        RelativeLayout rl_left = findViewById(R.id.rl_left);
        tv_title_top.setText(title);

        rl_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        RongIM.getInstance().setMessageAttachedUserInfo(true);
        // 添加会话界面
        ConversationFragment conversationFragment = new ConversationFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container_p, conversationFragment);
        transaction.commit();
        RongUserInfoManager.getInstance().setUserInfoProvider(new UserDataProvider.UserInfoProvider() {//设置用户个人信息
            /*  *
             * 获取设置用户信息. 通过返回的 userId 来封装生产用户信息.
             * @param userId 用户 ID*/

            @Override
            public UserInfo getUserInfo(String userId) {
                mUserId=userId;
                mPresenter.getWallPaper(userId);
                return null;
            }
        }, true);

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
                if ("mp4".equals(mType)||"mp3".equals(mType)||"mkv".equals(mType)||"rmvb".equals(mType)||"wmv".equals(mType)||"wav".equals(mType)) {
                    if (message != null) {
                        MediaMessageContent mContent = (MediaMessageContent) message.getContent();
                        mediaUrl = mContent.getMediaUrl().toString();
                    }
                    Intent intent = new Intent(PersonalConversationActivity.this, Mp4Activity.class);
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
                                Glide.with(PersonalConversationActivity.this)
                                        .load(finalMediaUrl)
                                        .into(imageView);
                            }).start(PersonalConversationActivity.this));
                    return true;
                }else if ("".equals(mType)) {

                    return false;
                } else if ("pdf".equals(mType) ||"application/pdf".equals(mType)) {
                    if (message != null) {
                        MediaMessageContent mContent = (MediaMessageContent) message.getContent();
                        mediaUrl = mContent.getMediaUrl().toString();
                    }
                    Intent intent = new Intent(PersonalConversationActivity.this, PdfActivity.class);
                    intent.putExtra("mName", mediaUrl);
                    startActivity(intent);

                    return true;

                } else {
                    if (message != null) {
                        MediaMessageContent mContent = (MediaMessageContent) message.getContent();
                        mediaUrl = mContent.getMediaUrl().toString();
                    }
                    Intent intent = new Intent(PersonalConversationActivity.this, ChatWebViewActivity.class);
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
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_personal_conversation;
    }
}