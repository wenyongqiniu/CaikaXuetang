package com.example.caikaxuetang.fragments;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.example.caikaxuetang.MainActivity;
import com.example.caikaxuetang.R;
import com.example.caikaxuetang.chatactivitys.activity.GroupConversationActivity;
import com.example.caikaxuetang.chatactivitys.activity.PersonalConversationActivity;
import com.example.caikaxuetang.contract.MainContract;
import com.example.caikaxuetang.responses.GroupInfo;
import com.example.caikaxuetang.responses.GroupNoticeBean;
import com.example.caikaxuetang.responses.VersionEntity;
import com.example.caikaxuetang.responses.WallPaperResponse;
import com.llw.mvplibrary.BaseApplication;
import com.llw.mvplibrary.mvp.MvpFragment;
import com.llw.mvplibrary.network.utils.SpUtils;
import com.llw.mvplibrary.network.utils.StatusBarUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import io.rong.imkit.IMCenter;
import io.rong.imkit.RongIM;
import io.rong.imkit.config.ConversationListBehaviorListener;
import io.rong.imkit.config.RongConfigCenter;
import io.rong.imkit.conversationlist.ConversationListFragment;
import io.rong.imkit.conversationlist.model.BaseUiConversation;
import io.rong.imkit.userinfo.RongUserInfoManager;
import io.rong.imkit.userinfo.UserDataProvider;
import io.rong.imkit.userinfo.model.GroupUserInfo;
import io.rong.imkit.utils.RouteUtils;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;
import io.rong.message.TextMessage;

import static com.example.caikaxuetang.MainActivity.countMessage;
import static com.example.caikaxuetang.MainActivity.tv_revice_message_count;


public class MessageFragment extends MvpFragment<MainContract.MainPresenter> implements MainContract.IMainView {
    private String mGroupId="";
    private String mUserId="";
    private SmartRefreshLayout sml_message;

    public MessageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            StatusBarUtils.setColor(getActivity(), Color.parseColor("#FFFFFF"));
        } else {
            StatusBarUtils.setColor(getActivity(), Color.parseColor("#F8F8F8"));
        }
        StatusBarUtils.setTextDark(getActivity(), true);
    }

    @Override
    public void getWallPaper(WallPaperResponse wallPaperResponse) {
        if (wallPaperResponse.getCode() == 0) {
            WallPaperResponse.DataBean data = wallPaperResponse.getData();
            UserInfo userInfo = new UserInfo(data.getId(), data.getNickname(), Uri.parse(data.getHeadImage()));
            RongUserInfoManager.getInstance().refreshUserInfoCache(userInfo);
        }
        if (sml_message!=null){
            sml_message.finishRefresh();
        }
    }

    @Override
    public void getGroupInfo(GroupInfo groupInfo) {
        if (groupInfo.getCode() == 0) {
            GroupInfo.DataBean data = groupInfo.getData();
            Group groupinfo = new Group(mGroupId, data.getGroupName(), Uri.parse(data.getGroupImg()));
            RongIM.getInstance().refreshGroupInfoCache(groupinfo);
        }
        if (sml_message!=null){
            sml_message.finishRefresh();
        }
    }

    @Override
    public void getGroupNotice(GroupNoticeBean groupInfo) {

    }

    @Override
    public void getCheckUpdate(VersionEntity groupInfo) {

    }

    @Override
    public void getFailed(Throwable e) {

        Log.e("TAG", "getFailed: " + e.getMessage());
    }

    @Override
    protected MainContract.MainPresenter createPresent() {
        return new MainContract.MainPresenter();
    }


    @Override
    public void initData(Bundle savedInstanceState) {
        NotificationManagerCompat notification = NotificationManagerCompat.from(context);
        boolean isEnabled = notification.areNotificationsEnabled();

        if (!isEnabled) {
            /**
             * 跳到通知栏设置界面
             * @param context
             */
            Intent localIntent = new Intent();
            //直接跳转到应用通知设置的代码：
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                localIntent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                localIntent.putExtra("app_package", context.getPackageName());
                localIntent.putExtra("app_uid", context.getApplicationInfo().uid);
                // for Android 8 and above
                localIntent.putExtra("android.provider.extra.APP_PACKAGE", context.getPackageName());

            } else if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
                localIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                localIntent.addCategory(Intent.CATEGORY_DEFAULT);
                localIntent.setData(Uri.parse("package:" + context.getPackageName()));
            } else {
                //4.4以下没有从app跳转到应用通知设置页面的Action，可考虑跳转到应用详情页面,
                localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (Build.VERSION.SDK_INT >= 9) {
                    localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                    localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
                } else if (Build.VERSION.SDK_INT <= 8) {
                    localIntent.setAction(Intent.ACTION_VIEW);
                    localIntent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails");
                    localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
                }
            }
            context.startActivity(localIntent);
        }
        StatusBarUtils.setColor(context, Color.parseColor("#FFFFFF"));
        StatusBarUtils.setTextDark(context, true);
        ConversationListFragment conversationListFragment = new ConversationListFragment();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fr_chat, conversationListFragment);
        transaction.commit();
        String ryToken = SpUtils.getSpString(context, "rytoken", "");

       /* sml_message = rootView.findViewById(R.id.sml_message);
        sml_message.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getGroupInfo(mGroupId);
            }

        });*/

        RongUserInfoManager.getInstance().setGroupInfoProvider(new UserDataProvider.GroupInfoProvider() {
            @Override
            public Group getGroupInfo(String groupId) {
                // 异步请求逻辑.
                mGroupId = groupId;
                mPresenter.getGroupInfo(groupId);
                return null;
            }
        }, true);

        RongUserInfoManager.getInstance().setUserInfoProvider(new UserDataProvider.UserInfoProvider() {
            /*  *
             * 获取设置用户信息. 通过返回的 userId 来封装生产用户信息.
             * @param userId 用户 ID*/

            @Override
            public UserInfo getUserInfo(String userId) {
                mUserId = userId;
                mPresenter.getWallPaper(userId);
                return null;
            }
        }, true);

        RongConfigCenter.conversationListConfig().setBehaviorListener(new ConversationListBehaviorListener() {
            @Override
            public boolean onConversationPortraitClick(Context context, Conversation.ConversationType conversationType, String s) {
                return false;
            }

            @Override
            public boolean onConversationPortraitLongClick(Context context, Conversation.ConversationType conversationType, String s) {
                return false;
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public boolean onConversationLongClick(Context context, View view, BaseUiConversation baseUiConversation) {
                return false;
            }

            @Override
            public boolean onConversationClick(Context context, View view, BaseUiConversation baseUiConversation) {
                countMessage = countMessage - baseUiConversation.mCore.getUnreadMessageCount();
                if (countMessage > 0) {
                    tv_revice_message_count.setVisibility(View.VISIBLE);
                } else {
                    tv_revice_message_count.setVisibility(View.GONE);
                }
                if (countMessage >= 99) {
                    tv_revice_message_count.setText(99 + "+");
                } else {
                    tv_revice_message_count.setText(countMessage + "");
                }
                String name = baseUiConversation.mCore.getConversationType().getName();
                String targetId = baseUiConversation.mCore.getTargetId();
                String conversationTitle = baseUiConversation.mCore.getConversationTitle();
                if (name.equals("group")) {//这里代表是群聊
                    RouteUtils.registerActivity(RouteUtils.RongActivityType.ConversationActivity, GroupConversationActivity.class);//需要注册在此activity
                    Conversation.ConversationType conversationType = Conversation.ConversationType.GROUP;
                    Bundle bundle = new Bundle();
                    if (!TextUtils.isEmpty(conversationTitle)) {
                        bundle.putString(RouteUtils.TITLE, conversationTitle); //会话页面标题
                    }
                    //开启消息免打扰
                    IMCenter.getInstance().setConversationNotificationStatus(conversationType, targetId, Conversation.ConversationNotificationStatus.DO_NOT_DISTURB, new RongIMClient.ResultCallback<Conversation.ConversationNotificationStatus>() {
                        @Override
                        public void onSuccess(Conversation.ConversationNotificationStatus conversationNotificationStatus) {

                        }

                        @Override
                        public void onError(RongIMClient.ErrorCode errorCode) {

                        }
                    });
                    RouteUtils.routeToConversationActivity(context, conversationType, targetId, bundle);
                } else {
                    RouteUtils.registerActivity(RouteUtils.RongActivityType.ConversationActivity, PersonalConversationActivity.class);
                    Conversation.ConversationType conversationType = Conversation.ConversationType.PRIVATE;
                    Bundle bundle = new Bundle();
                    if (!TextUtils.isEmpty(conversationTitle)) {
                        bundle.putString(RouteUtils.TITLE, conversationTitle); //会话页面标题
                    }
                    RouteUtils.routeToConversationActivity(context, conversationType, targetId, bundle);
                }
                SpUtils.putSpString(context, "title", conversationTitle);
                SpUtils.putSpString(context, "targetId", targetId);
                RongIM.setGroupInfoProvider(new UserDataProvider.GroupInfoProvider() {
                    /*  *
                     * 群信息回调
                     * @param groupId 群组 ID
                     * @return 群组信息*/

                    @Override
                    public Group getGroupInfo(String groupId) {
                        // 异步请求逻辑.
                        mGroupId = groupId;
                        mPresenter.getGroupInfo(groupId);
                        return null;
                    }
                }, true);
                // RouteUtils.routeToConversationActivity(context,  baseUiConversation.mCore.getConversationType(), targetId);

                return true;
            }
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_message;
    }
}


