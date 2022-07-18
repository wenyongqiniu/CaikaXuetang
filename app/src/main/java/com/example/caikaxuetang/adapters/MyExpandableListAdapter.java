package com.example.caikaxuetang.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.caikaxuetang.MyApplication;
import com.example.caikaxuetang.R;
import com.example.caikaxuetang.fragments.learningcenterfragments.DakeCourseListFragment;
import com.example.caikaxuetang.learningActivitys.DoHomeWorkActivity;
import com.example.caikaxuetang.learningActivitys.VideoActivity;
import com.example.caikaxuetang.responses.DkCourseListResponse;
import com.example.caikaxuetang.responses.XbCourseListResponse;
import com.example.caikaxuetang.utils.CustomerToastUtils;
import com.example.caikaxuetang.utils.MyJieCaoVideoView;
import com.example.caikaxuetang.websocket.JWebSocketClient;
import com.llw.mvplibrary.BaseApplication;
import com.llw.mvplibrary.network.utils.SpUtils;

import java.net.URI;
import java.util.List;

import static com.example.caikaxuetang.fragments.learningcenterfragments.DakeCourseListFragment.videoList;
import static com.example.caikaxuetang.learningActivitys.VideoActivity.client;
import static com.example.caikaxuetang.learningActivitys.VideoActivity.getPlayTime;
import static com.example.caikaxuetang.learningActivitys.VideoActivity.playerStandard;
import static com.example.caikaxuetang.learningActivitys.VideoActivity.stringStringHashMap;
import static com.example.caikaxuetang.learningActivitys.VideoActivity.videoUrl;


/**
 * Created by CnPeng on 2017/1/21.
 * <p>
 * 自定义ExpandableListAdapter展示ExpandableListView的内容
 */

public class MyExpandableListAdapter extends BaseExpandableListAdapter {

    private List<DkCourseListResponse.DataBean.ChapterListBean> chapterListBeans;
    private Context context;
    public static int currentPosition = -1;
    private int currentPresonPosition = -1;
    public static String sectionId = "";


    public MyExpandableListAdapter(List<DkCourseListResponse.DataBean.ChapterListBean> chapterListBeans, Context context) {
        this.chapterListBeans = chapterListBeans;
        this.context = context;
    }

    @Override
    public int getGroupCount() {    //组的数量
        return chapterListBeans.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {    //某组中子项数量
        return chapterListBeans.get(groupPosition).getSectionList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {     //某组
        return chapterListBeans.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {  //某子项
        return chapterListBeans.get(groupPosition).getSectionList();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHold groupHold;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_dake_group, null);
            groupHold = new GroupHold();
            groupHold.tv_group_title = (TextView) convertView.findViewById(R.id.tv_group_title);
            groupHold.view_group = (View) convertView.findViewById(R.id.view_group);
            groupHold.iv_div = (ImageView) convertView.findViewById(R.id.iv_div);
            groupHold.iv_is_nowing = (ImageView) convertView.findViewById(R.id.iv_is_nowing);
            convertView.setTag(groupHold);
        } else {
            groupHold = (GroupHold) convertView.getTag();
        }
        DkCourseListResponse.DataBean.ChapterListBean chapterListBean = chapterListBeans.get(groupPosition);
        String videoChapterId = SpUtils.getSpString(context, "videoChapterId", "");
        if (currentPresonPosition == groupPosition || chapterListBean.getChapterId().equals(videoChapterId)) {
            groupHold.iv_is_nowing.setVisibility(View.VISIBLE);
        } else {
            groupHold.iv_is_nowing.setVisibility(View.INVISIBLE);
        }

        groupHold.tv_group_title.setText(chapterListBean.getChapterName());

        if (chapterListBean.getLockStatus() == 0) {//章节未解锁
            groupHold.iv_div.setBackgroundResource(R.mipmap.unlock);
            groupHold.tv_group_title.setTextColor(Color.parseColor("#B2B2B2"));

        } else {
            groupHold.tv_group_title.setTextColor(Color.parseColor("#3D3D3D"));

            if (isExpanded) {
                groupHold.view_group.setVisibility(View.INVISIBLE);
                groupHold.iv_div.setBackgroundResource(R.mipmap.up_div);
            } else {
                groupHold.view_group.setVisibility(View.VISIBLE);
                groupHold.iv_div.setBackgroundResource(R.mipmap.down_div);
            }

        }


        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView,
                             ViewGroup parent) {
        ChildHold childHold;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_dake_child, null);
            childHold = new ChildHold();
            childHold.tv_dake_child_title = (TextView) convertView.findViewById(R.id.tv_dake_child_title);
            childHold.tv_study_state = (TextView) convertView.findViewById(R.id.tv_study_state);
            childHold.rl_child = (RelativeLayout) convertView.findViewById(R.id.rl_child);
            childHold.gif_view = (ImageView) convertView.findViewById(R.id.gif_view);
            childHold.lock_state = (ImageView) convertView.findViewById(R.id.lock_state);
            convertView.setTag(childHold);
        } else {
            childHold = (ChildHold) convertView.getTag();
        }

        DkCourseListResponse.DataBean.ChapterListBean.SectionListBean sectionListBean = chapterListBeans.get(groupPosition).getSectionList().get(childPosition);
        childHold.tv_dake_child_title.setText(sectionListBean.getSectionName());

        String videoSectionId = SpUtils.getSpString(context, "videoSectionId", "");

        //首先是拼接字符串
        if ((currentPosition == childPosition && groupPosition == currentPresonPosition) || videoSectionId.equals(sectionListBean.getSectionId())) {

            String content = "<font color=\"#FD8103\">" + "正在播放｜" + "</font>" + sectionListBean.getStudyNum() + "人学习过";
            childHold.tv_study_state.setText(Html.fromHtml(content));
            childHold.gif_view.setVisibility(View.VISIBLE);
        } else {
            childHold.gif_view.setVisibility(View.INVISIBLE);
            if (sectionListBean.getStudyProcessRate() != null) {
                String content = "<font color=\"#FD8103\">" + "已学习" + sectionListBean.getStudyProcessRate() + "</font>" + "｜" + sectionListBean.getStudyNum() + "人学习过";
                childHold.tv_study_state.setText(Html.fromHtml(content));
            } else {
                childHold.tv_study_state.setTextColor(Color.parseColor("#717171"));
                childHold.tv_study_state.setText("未学习｜" + sectionListBean.getStudyNum() + "人学习过");
            }
        }

        if (sectionListBean.getSectionType() == 2) {//课后测试
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) childHold.tv_dake_child_title.getLayoutParams();
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            childHold.tv_dake_child_title.setLayoutParams(layoutParams);
            childHold.tv_study_state.setVisibility(View.INVISIBLE);
        } else {
            childHold.tv_study_state.setVisibility(View.VISIBLE);
        }
        if (sectionListBean.getLockStatus() == 0) {//小节为解锁
            childHold.lock_state.setVisibility(View.VISIBLE);
            childHold.tv_study_state.setTextColor(Color.parseColor("#717171"));
            childHold.tv_study_state.setText("未学习｜" + sectionListBean.getStudyNum() + "人学习过");
        } else {
            childHold.lock_state.setVisibility(View.GONE);
        }

        childHold.rl_child.setOnClickListener(v -> {
            if (sectionListBean.getLockStatus() == 1) {//已解锁
                if (sectionListBean.getSectionType() == 1) {
                    if (playerStandard != null) {
                        for (int i = 0; i < videoList.size(); i++) {
                            if (videoList.get(i).getSectionId().equals(sectionListBean.getSectionId())) {
                                DakeCourseListFragment.currentVideo = i;
                            }
                        }
                        videoUrl = sectionListBean.getVideoUrl();
                        playerStandard.setUp(videoUrl, MyJieCaoVideoView.SCREEN_LAYOUT_NORMAL, "");
                        playerStandard.startVideo();
                        currentPosition = childPosition;
                        currentPresonPosition = groupPosition;
                        MyExpandableListAdapter.this.notifyDataSetChanged();
                        SpUtils.remove(context, "videoSectionId");
                        SpUtils.putSpString(context, "videoSectionId", sectionListBean.getSectionId());
                        SpUtils.putSpString(context, "videoChapterId", chapterListBeans.get(groupPosition).getChapterId());
                        SpUtils.putSpString(context, "currentSectionName", sectionListBean.getSectionName());
                        sectionId = sectionListBean.getSectionId();
                        VideoActivity.sectionId = sectionListBean.getSectionId();

                        client.close();
                        long playTime = getPlayTime(videoUrl);
                        stringStringHashMap.put("token", BaseApplication.token);
                        stringStringHashMap.put("sectionId", sectionListBean.getSectionId());
                        stringStringHashMap.put("totalTime", playTime / 1000 + "");
                        StringBuffer stringBuffer = new StringBuffer();
                        for (String o : stringStringHashMap.keySet()) {
                            System.out.println("key" + o + " value" + stringStringHashMap.get(o));
                            stringBuffer.append(o + "=" + stringStringHashMap.get(o) + "&");
                        }
                        StringBuffer delete = stringBuffer.delete(stringBuffer.length() - 1, stringBuffer.length());
                        Log.e("delete", "getDkCourseList: " + MyApplication.WEB_SOCKET + delete);

                        //连接websocket
                        URI uri = URI.create(MyApplication.WEB_SOCKET + delete);
                        client = new JWebSocketClient(uri) {
                            @Override
                            public void onMessage(String message) {
                                //message就是接收到的消息
                                Log.e("JWebSClientService", message);
                            }
                        };
                        try {
                            client.connectBlocking();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (sectionListBean.getSectionType() == 2) {//练习
                    Intent intent = new Intent(context, DoHomeWorkActivity.class);
                    intent.putExtra("sectionId", sectionListBean.getSectionId());
                    intent.putExtra("examType", "课后测试");
                    context.startActivity(intent);
                }

            } else {
                CustomerToastUtils.toastShow(context).show();
                CustomerToastUtils.tv_toast.setText("小节未解锁");
                CustomerToastUtils.iv_warm.setBackgroundResource(R.mipmap.warm);
            }

        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;    //默认返回false,改成true表示组中的子条目可以被点击选中
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    class GroupHold {
        TextView tv_group_title;
        View view_group;
        ImageView iv_div;
        ImageView iv_is_nowing;
    }

    class ChildHold {
        TextView tv_dake_child_title;
        TextView tv_study_state;
        ImageView gif_view;
        ImageView lock_state;
        RelativeLayout rl_child;
    }
}