package com.example.caikaxuetang.fragments.learningcenterfragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;


import com.example.caikaxuetang.MyApplication;
import com.example.caikaxuetang.R;
import com.example.caikaxuetang.adapters.MyExpandableListAdapter;
import com.example.caikaxuetang.contract.XbCourseListContract;
import com.example.caikaxuetang.learningActivitys.ExamActivity;
import com.example.caikaxuetang.learningActivitys.VideoActivity;
import com.example.caikaxuetang.responses.CommitSuccessResponse;
import com.example.caikaxuetang.responses.DkCourseListResponse;
import com.example.caikaxuetang.responses.NoteDetailResponse;
import com.example.caikaxuetang.responses.XbCourseListResponse;
import com.example.caikaxuetang.utils.CustomerToastUtils;
import com.example.caikaxuetang.utils.MyJieCaoVideoView;
import com.example.caikaxuetang.utils.NestedExpandableListView;
import com.example.caikaxuetang.websocket.JWebSocketClient;
import com.ljb.page.PageState;
import com.ljb.page.PageStateLayout;
import com.llw.mvplibrary.BaseApplication;
import com.llw.mvplibrary.mvp.MvpFragment;
import com.llw.mvplibrary.network.utils.SpUtils;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import static com.example.caikaxuetang.learningActivitys.VideoActivity.client;
import static com.example.caikaxuetang.learningActivitys.VideoActivity.getPlayTime;
import static com.example.caikaxuetang.learningActivitys.VideoActivity.playerStandard;

public class DakeCourseListFragment extends MvpFragment<XbCourseListContract.XbCourseListPresenter> implements XbCourseListContract.XbCourseListView {

    private String courseId;
    private NestedExpandableListView exp_dake;
    private PageStateLayout pageLayout;
    private TextView tv_last_study;
    private List<DkCourseListResponse.DataBean.ChapterListBean> chapterList;
    private MyExpandableListAdapter adapter;
    private Handler myHandler = new Handler();
    public static ArrayList<DkCourseListResponse.DataBean.ChapterListBean.SectionListBean> videoList;
    public static int currentVideo;

    public static HashMap<String, String> stringStringHashMap;

    public DakeCourseListFragment() {
        // Required empty public constructor
    }

    //自动播放下一节
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (videoList.size() > 0 && null != playerStandard) {
                if (playerStandard.currentState == MyJieCaoVideoView.CURRENT_STATE_AUTO_COMPLETE ||
                        playerStandard.currentState == MyJieCaoVideoView.CURRENT_STATE_ERROR) {
                    currentVideo++;
                    if (currentVideo == videoList.size()) {
                        currentVideo = 0;
                    }
                    client.close();
                    SpUtils.remove(context, "videoSectionId");
                    SpUtils.putSpString(context, "videoSectionId", videoList.get(currentVideo).getSectionId());
                    SpUtils.putSpString(context, "videoChapterId", videoList.get(currentVideo).getChapternId());
                    SpUtils.putSpString(context, "currentSectionName", videoList.get(currentVideo).getSectionName());
                    VideoActivity.sectionId = videoList.get(currentVideo).getSectionId();
                    MyExpandableListAdapter.sectionId = "";
                    stringStringHashMap = new HashMap<>();
                    playerStandard.setUp(videoList.get(currentVideo).getVideoUrl(), MyJieCaoVideoView.SCREEN_LAYOUT_NORMAL, "");
                    playerStandard.startVideo();

                    MyExpandableListAdapter.currentPosition = -1;
                    adapter.notifyDataSetChanged();
                    long playTime = getPlayTime(videoList.get(currentVideo).getVideoUrl() + "");
                    stringStringHashMap.put("token", BaseApplication.token);
                    stringStringHashMap.put("sectionId", videoList.get(currentVideo).getSectionId());
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
            }
            myHandler.postDelayed(this, 1000);
        }
    };

    @Override
    public void getWallPaper(XbCourseListResponse wallPaperResponse) {

    }

    @Override
    public void getDkCourseList(DkCourseListResponse wallPaperResponse) {
        if (wallPaperResponse.getCode() == 0) {
            tv_last_study.setText("上次学到：" + wallPaperResponse.getData().getLastStudySectionName());
            pageLayout.setPage(PageState.STATE_SUCCESS);
            chapterList = wallPaperResponse.getData().getChapterList();
            //创建并设置适配器
            adapter = new MyExpandableListAdapter(chapterList, getActivity());
            exp_dake.setAdapter(adapter);
            String videoSectionId = SpUtils.getSpString(context, "videoSectionId", "");
            if (videoSectionId.equals("")) {
                SpUtils.putSpString(context, "videoSectionId", wallPaperResponse.getData().getDefaultSectionId());
                SpUtils.putSpString(context, "videoChapterId", chapterList.get(0).getChapterId());
            }

            videoList = new ArrayList<>();
            for (int i = 0; i < chapterList.size(); i++) {
                for (int j = 0; j < chapterList.get(i).getSectionList().size(); j++) {
                    if (chapterList.get(i).getSectionList().get(j).getLockStatus() == 1 && chapterList.get(i).getSectionList().get(j).getVideoUrl() != null) {
                        DkCourseListResponse.DataBean.ChapterListBean.SectionListBean sectionListBean = new DkCourseListResponse.DataBean.ChapterListBean.SectionListBean();
                        sectionListBean.setSectionId(chapterList.get(i).getSectionList().get(j).getSectionId());
                        sectionListBean.setVideoUrl(chapterList.get(i).getSectionList().get(j).getVideoUrl() + "");
                        sectionListBean.setChapternId(chapterList.get(i).getChapterId());
                        sectionListBean.setSectionName(chapterList.get(i).getSectionList().get(j).getSectionName());
                        videoList.add(sectionListBean);

                    }
                }
            }
            for (int i = 0; i < videoList.size(); i++) {
                if (videoList.get(i).getSectionId().equals(wallPaperResponse.getData().getDefaultSectionId())) {
                    currentVideo = i;
                    break;
                }
            }
            runnable.run();
        } else {
            pageLayout.setPage(PageState.STATE_EMPTY);
        }
    }

    @Override
    public void getKeepNote(CommitSuccessResponse wallPaperResponse) {

    }

    @Override
    public void getNoteDetail(NoteDetailResponse wallPaperResponse) {

    }

    @Override
    public void getFailed(Throwable e) {
        pageLayout.setPage(PageState.STATE_ERROR);
    }

    @Override
    protected XbCourseListContract.XbCourseListPresenter createPresent() {
        return new XbCourseListContract.XbCourseListPresenter();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        pageLayout = rootView.findViewById(R.id.page_layout);
        pageLayout.setContentView(R.layout.fragment_dake_course_list);
        assert getArguments() != null;
        courseId = getArguments().getString("courseId");
        pageLayout.setPage(PageState.STATE_LOADING);
        mPresenter.getDkCourseList(courseId);
        exp_dake = rootView.findViewById(R.id.exp_dake);
        tv_last_study = rootView.findViewById(R.id.tv_last_study);
        TextView retey = rootView.findViewById(R.id.retry);

        exp_dake.setOnGroupClickListener((expandableListView, view, i, l) -> {
            if (chapterList.get(i).getLockStatus() == 0) {
                CustomerToastUtils.toastShow(context).show();
                CustomerToastUtils.tv_toast.setText("章节未解锁");
                CustomerToastUtils.iv_warm.setBackgroundResource(R.mipmap.warm);
                return true;
            } else {
                //1:课程章节 2:课程习题 3:毕业考试
                if (chapterList.get(i).getChapterType() == 1) {//正常展开小节
                    return false;
                } else if (chapterList.get(i).getChapterType() == 2) {

                    return true;
                } else {
                    Intent intent = new Intent(getActivity(), ExamActivity.class);
                    intent.putExtra("chapterId", chapterList.get(i).getChapterId());
                    startActivity(intent);
                    return true;
                }
            }
        });

        exp_dake.setOnChildClickListener((expandableListView, view, i, i1, l) -> {
            if (chapterList.get(i).getSectionList().get(i1).getLockStatus() == 0) {
                CustomerToastUtils.toastShow(context).show();
                CustomerToastUtils.tv_toast.setText("章节未解锁");
                CustomerToastUtils.iv_warm.setBackgroundResource(R.mipmap.warm);
                return true;
            } else {
                return false;
            }
        });

        //点击重试
        retey.setOnClickListener(v -> {
            pageLayout.setPage(PageState.STATE_LOADING);
            mPresenter.getXbCourseList(courseId);
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.base_layout;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        myHandler.removeCallbacks(runnable);
    }
}