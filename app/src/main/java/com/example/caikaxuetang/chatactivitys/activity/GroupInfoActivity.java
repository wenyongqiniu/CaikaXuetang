package com.example.caikaxuetang.chatactivitys.activity;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;


import com.example.caikaxuetang.R;
import com.example.caikaxuetang.adapters.GroupInfoListAdapter;
import com.example.caikaxuetang.contract.GroupInfoContract;
import com.example.caikaxuetang.responses.GroupInfo;
import com.example.caikaxuetang.responses.GroupNoticeBean;
import com.example.caikaxuetang.responses.GroupNumberResponse;
import com.llw.mvplibrary.mvp.MvpActivity;
import com.llw.mvplibrary.network.utils.SpUtils;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.feature.mention.RongMentionManager;
import io.rong.imlib.model.UserInfo;

public class GroupInfoActivity extends MvpActivity<GroupInfoContract.MainPresenter> implements GroupInfoContract.IMainView {

    private android.widget.RelativeLayout mRlBack;
    private TextView tv_name_info;
    private TextView tv_notice_content;
    private TextView mTvGroupName;
    private TextView tv_climb;
    private androidx.recyclerview.widget.RecyclerView mRvGroupNumber;
    private ArrayList<GroupNumberResponse.DataBean.OrdinaryUserListBean> list;
    private GroupInfoListAdapter groupInfoListAdapter;
    private TextView topTitle;
    private String groupId;
    private ArrayList<UserInfo> userInfos;
    private GroupNoticeBean.DataBean data;


    @Override
    public void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        // String noticeContent = intent.getStringExtra("noticeContent");
        mRlBack = findViewById(R.id.rl_left);
        tv_name_info = findViewById(R.id.tv_name_info);
        tv_notice_content = findViewById(R.id.tv_notice_content);
        mRvGroupNumber = findViewById(R.id.rv_group_number);
        topTitle = findViewById(R.id.tv_title_top);
        tv_climb = findViewById(R.id.tv_climb);
        String title = SpUtils.getSpString(this, "title", "");
        groupId = SpUtils.getSpString(this, "targetId", "");
        mPresenter.getNotice(groupId);
        tv_name_info.setText(title);

        mPresenter.getGroupInfo(groupId);
        mPresenter.getWallPaper(groupId, 0);
        list = new ArrayList<>();
        groupInfoListAdapter = new GroupInfoListAdapter(list, this);
        mRvGroupNumber.setLayoutManager(new GridLayoutManager(this, 5));
        mRvGroupNumber.setAdapter(groupInfoListAdapter);
        groupInfoListAdapter.notifyDataSetChanged();


        mRlBack.setOnClickListener(v -> finish());
        //跳转公告界面
        tv_notice_content.setOnClickListener(v -> {
            Intent intent1 = new Intent(GroupInfoActivity.this, NoticeActivity.class);
            intent1.putExtra("notice", data.getAnnouncementContent());
            startActivity(intent1);
        });

        //爬楼
        tv_climb.setOnClickListener(v -> {
            Intent intent1 = new Intent(GroupInfoActivity.this, ClimbBuildingActivity.class);
            intent1.putExtra("targetId", groupId);
            startActivity(intent1);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_group_info;
    }


    @Override
    public void getWallPaper(GroupNumberResponse wallPaperResponse) {
        if (wallPaperResponse.getData() != null && wallPaperResponse.getData().getOrdinaryUserList() != null) {
            List<GroupNumberResponse.DataBean.RobotListBean> robotList = wallPaperResponse.getData().getRobotList();
            list.addAll(wallPaperResponse.getData().getOrdinaryUserList());
            for (int i = 0; i < robotList.size(); i++) {
                GroupNumberResponse.DataBean.OrdinaryUserListBean ordinaryUserListBean = new GroupNumberResponse.DataBean.OrdinaryUserListBean();
                ordinaryUserListBean.setHeadImage(robotList.get(i).getHeadImage());
                ordinaryUserListBean.setNickname(robotList.get(i).getNickname());
                list.add(ordinaryUserListBean);
            }
            topTitle.setText("聊天成员(" + list.size() + ")");
            groupInfoListAdapter.notifyDataSetChanged();

            userInfos = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                UserInfo userInfo = new UserInfo(i + "", list.get(i).getNickname(), Uri.parse(list.get(i).getHeadImage()));
                userInfos.add(userInfo);
            }

            RongMentionManager.getInstance().setGroupMembersProvider((groupId, callback) -> {
                //此处为应用层获取群组成员逻辑
                callback.onGetGroupMembersResult(userInfos); // 调用 callback 的 onGetGroupMembersResult 回传群组信息
            });
        }
    }

    @Override
    public void getGroupInfo(GroupInfo groupInfo) {

    }

    @Override
    public void getGroupNotice(GroupNoticeBean groupInfo) {
        if (groupInfo.getCode() == 0) {
            data = groupInfo.getData();
            if (data != null) {

                tv_notice_content.setText(Html.fromHtml(data.getAnnouncementContent() + ""));

            }

        }

    }

    @Override
    public void getFailed(Throwable e) {

        Log.e("TAG", "getFailed: " + e.getMessage());
    }

    @Override
    protected GroupInfoContract.MainPresenter createPresenter() {
        return new GroupInfoContract.MainPresenter();
    }
}