package com.example.caikaxuetang.learningActivitys;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.caikaxuetang.R;
import com.example.caikaxuetang.adapters.ExamTaskListAdapter;

import com.example.caikaxuetang.contract.ExamContract;
import com.example.caikaxuetang.responses.CommitExamResponse;
import com.example.caikaxuetang.responses.CommitSuccessResponse;
import com.example.caikaxuetang.responses.ExamInfoResponse;
import com.example.caikaxuetang.responses.ExamTaskInfoResponse;
import com.example.caikaxuetang.responses.ResetExamResponse;
import com.example.caikaxuetang.utils.CustomerToastUtils;
import com.ljb.page.PageState;
import com.ljb.page.PageStateLayout;
import com.llw.mvplibrary.mvp.MvpActivity;
import com.llw.mvplibrary.network.utils.SpUtils;

import java.util.ArrayList;

public class ExamActivity extends MvpActivity<ExamContract.ExamPresenter> implements ExamContract.ExamView {


    private String chapterId = "";
    private PageStateLayout page_layout;
    private String taskId = "";
    private RecyclerView rv_vp2;
    private ArrayList<ExamTaskInfoResponse.DataBean> taskList;
    private ExamTaskInfoResponse.DataBean dataList;
    private TextView tv_question_title;
    private TextView tv_choice_type;
    private ExamTaskListAdapter homeWorkAdapter;
    private boolean isLast;
    private int currentPosition;
    private String content;
    public static TextView tv_next_exam;
    private TextView tv_last_exam;
    private TextView tv_question_num;
    private ExamInfoResponse.DataBean data;

    @Override
    protected ExamContract.ExamPresenter createPresenter() {
        return new ExamContract.ExamPresenter();
    }

    @Override
    public void getWallPaper(ExamInfoResponse wallPaperResponse) {
        if (wallPaperResponse.getCode() == 0) {
            data = wallPaperResponse.getData();
            if (data.getDoneNum() == data.getTotalNum()) {
                page_layout.setContentView(R.layout.activity_exam_score);
                scoreInfo(data);
            } else {
                page_layout.setContentView(R.layout.activity_exam);
                examInfo(data);
            }
            page_layout.setPage(PageState.STATE_SUCCESS);
        } else {
            page_layout.setPage(PageState.STATE_EMPTY);
        }

    }

    private void scoreInfo(ExamInfoResponse.DataBean data) {
        TextView tv_result_title = findViewById(R.id.tv_result_title);
        TextView tv_score_result = findViewById(R.id.tv_score_result);
        TextView tv_corrent_answer = findViewById(R.id.tv_corrent_answer);
        TextView tv_view_resolution = findViewById(R.id.tv_view_resolution);
        TextView tv_redo = findViewById(R.id.tv_redo);
        RelativeLayout rl_left = findViewById(R.id.rl_left);
        TextView tv_title_top = findViewById(R.id.tv_title_top);
        tv_title_top.setText("考试结果");
        rl_left.setOnClickListener(v -> finish());
        ImageView iv_head_score = findViewById(R.id.iv_head_score);
        Intent intent = getIntent();
        String chapterId = intent.getStringExtra("chapterId");

        tv_result_title.setText(data.getCoursePackageName());
        String headImg = SpUtils.getSpString(context, "headImg", "");
        Glide.with(context).load(headImg).apply(new RequestOptions().circleCrop()).into(iv_head_score);

        String content = data.getScore() + "<font size='10px'>" + "分" + "</font>";

        tv_score_result.setText(Html.fromHtml(content));

        String contents = "总题数" + data.getTotalNum() + "     答对" + "<font color=\"#FE8000\">" + data.getRightNum() + "</font>" + "题";

        tv_corrent_answer.setText(Html.fromHtml(contents));

        //查看答案
        tv_view_resolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AnswerActivity.class);
                intent.putExtra("chapterId", chapterId);
                intent.putExtra("type", "chapterType");
                startActivity(intent);
            }
        });
        if (data.isCanGetCard()) {
            tv_redo.setText("领取毕业证");
        } else {
            tv_redo.setText("重新考试");
        }
        //重新考试
        tv_redo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!data.isCanGetCard()) {//重新考试
                    mPresenter.getResetExam(chapterId);
                    page_layout.setPage(PageState.STATE_LOADING);
                    isLast = false;
                } else {//领取毕业证
                    Intent intent1 = new Intent(context, StudyCardActivity.class);
                    intent1.putExtra("chapterId", chapterId);
                    startActivity(intent1);
                }
            }
        });
    }

    @Override
    public void getExamTaskInfo(ExamTaskInfoResponse wallPaperResponse) {
        if (wallPaperResponse.getCode() == 0) {
            dataList = wallPaperResponse.getData();
            if (dataList.getTaskType() == 3) {
                ArrayList<ExamTaskInfoResponse.DataBean.AnswerListBean> answerListBeans = new ArrayList<>();
                answerListBeans.add(new ExamTaskInfoResponse.DataBean.AnswerListBean());
                dataList.setAnswerList(answerListBeans);

            }
            // taskList.add(dataList);
            homeWorkAdapter = new ExamTaskListAdapter(R.layout.item_homework_list, dataList.getAnswerList(), dataList);
            rv_vp2.setLayoutManager(new LinearLayoutManager(ExamActivity.this));
            rv_vp2.setAdapter(homeWorkAdapter);
            homeWorkAdapter.notifyDataSetChanged();
            tv_question_title.setText("\t\t\t\t\t\t\t\t" + dataList.getTaskTitle());
            if (dataList.getTaskType() == 1) {//	题目类型1单选 2多选 3填空题
                tv_choice_type.setText("单选");
            } else if (dataList.getTaskType() == 2) {
                tv_choice_type.setText("多选");
            } else {
                tv_choice_type.setText("填空");
            }

        }

    }

    @Override
    public void getCommitExam(CommitSuccessResponse wallPaperResponse) {
        if (wallPaperResponse.getCode() == 0) {
            currentPosition++;
            tv_last_exam.setBackgroundResource(R.drawable.last_exam);
            tv_last_exam.setTextColor(Color.parseColor("#FE8000"));
            tv_next_exam.setBackgroundResource(R.drawable.un_exam_next);
            isLast = true;
            content = "当前答题数" + "<font color=\"#FE8000\">" + currentPosition + "</font>" + "        题目总数" + "<font color=\"#FE8000\">" + data.getTotalNum() + "</font>";
            tv_question_num.setText(Html.fromHtml(content));
            homeWorkAdapter.isNext = false;
            taskId = dataList.getNextTaskId();
            if (currentPosition - 1 == data.getTotalNum()) {
                mPresenter.getWallPaper(chapterId);
            } else {
                mPresenter.getExamTaskInfo(taskId);
            }
        }

    }

    @Override
    public void getResetExam(ResetExamResponse wallPaperResponse) {
        if (wallPaperResponse.getCode() == 0) {
            page_layout.setContentView(R.layout.activity_exam);
            mPresenter.getWallPaper(chapterId);
            page_layout.setPage(PageState.STATE_SUCCESS);
        } else {
            page_layout.setPage(PageState.STATE_EMPTY);
        }
    }

    //进入考试页面
    private void examInfo(ExamInfoResponse.DataBean data) {

        RelativeLayout rl_left = findViewById(R.id.rl_left);
        TextView tv_title_top = findViewById(R.id.tv_title_top);
        tv_question_num = findViewById(R.id.tv_question_num);
        tv_last_exam = findViewById(R.id.tv_last_exam);
        tv_next_exam = findViewById(R.id.tv_next_exam);
        tv_question_title = findViewById(R.id.tv_question_title);
        tv_choice_type = findViewById(R.id.tv_choice_type);
        ViewPager2 vp2_exam = findViewById(R.id.vp2_exam);
        rv_vp2 = findViewById(R.id.rv_vp2);
        tv_title_top.setText("毕业考试");
        rl_left.setOnClickListener(v -> finish());
        taskId = data.getTaskId();
        mPresenter.getExamTaskInfo(taskId);
        currentPosition = data.getDoneNum() + 1;
        if (data.getDoneNum() > 0) {
            isLast = true;
            tv_last_exam.setBackgroundResource(R.drawable.last_exam);
            tv_last_exam.setTextColor(Color.parseColor("#FE8000"));
        }
        content = "当前答题数" + "<font color=\"#FE8000\">" + currentPosition + "</font>" + "        题目总数" + "<font color=\"#FE8000\">" + data.getTotalNum() + "</font>";
        tv_question_num.setText(Html.fromHtml(content));
        if (currentPosition == data.getTotalNum()) {
            tv_next_exam.setText("提交");
        }
        //上一题
        tv_last_exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLast) {
                    taskId = dataList.getPrveTaskId();
                    mPresenter.getExamTaskInfo(taskId);
                    currentPosition--;
                    content = "当前答题数" + "<font color=\"#FE8000\">" + currentPosition + "</font>" + "        题目总数" + "<font color=\"#FE8000\">" + data.getTotalNum() + "</font>";
                    tv_question_num.setText(Html.fromHtml(content));
                    if (currentPosition == 1) {
                        isLast = false;
                        tv_last_exam.setBackgroundResource(R.drawable.un_exam_last);
                        tv_last_exam.setTextColor(Color.parseColor("#E2E2E2"));
                    }
                    tv_next_exam.setText("下一题");

                } else {
                    CustomerToastUtils.toastShow(context).show();
                    CustomerToastUtils.tv_toast.setText("已是第一题");
                }
            }
        });
        //下一题
        tv_next_exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentPosition == data.getTotalNum()) {
                    tv_next_exam.setText("提交");
                }
                if (homeWorkAdapter.isNext) {
                    commitExam();
                } else {
                    CustomerToastUtils.toastShow(context).show();
                    CustomerToastUtils.tv_toast.setText("未作答");
                }
            }
        });


    }

    //提交每一题答案
    private void commitExam() {
        CommitExamResponse commitExamResponse = new CommitExamResponse();
        commitExamResponse.setTaskId(taskId);
        ArrayList<String> answerList = new ArrayList<>();

        for (int i = 0; i < dataList.getAnswerList().size(); i++) {
            if (dataList.getTaskType() == 3) {//填空
                commitExamResponse.setContent(homeWorkAdapter.ed_content);
            } else {
                if (dataList.getAnswerList().get(i).isSelected()) {
                    answerList.add(dataList.getAnswerList().get(i).getAnswerCode());
                }
            }
        }
        commitExamResponse.setAnswerCodeList(answerList);
        mPresenter.getCommitExam(commitExamResponse);

    }

    @Override
    public void getFailed(Throwable e) {
        page_layout.setPage(PageState.STATE_ERROR);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        Intent intent = getIntent();
        chapterId = intent.getStringExtra("chapterId");
        mPresenter.getWallPaper(chapterId);
        page_layout = findViewById(R.id.page_layout);
        page_layout.setPage(PageState.STATE_LOADING);

    }

    @Override
    public int getLayoutId() {
        return R.layout.base_layout;
    }

    /*
     *
     * 点击空白区域隐藏键盘.
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent me) {
        if (me.getAction() == MotionEvent.ACTION_DOWN) {  //把操作放在用户点击的时候
            View v = getCurrentFocus();      //得到当前页面的焦点,ps:有输入框的页面焦点一般会被输入框占据
            if (isShouldHideKeyboard(v, me)) { //判断用户点击的是否是输入框以外的区域
                hideKeyboard(v.getWindowToken());   //收起键盘
            }
        }
        return super.dispatchTouchEvent(me);
    }

    /*    *
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return*/

    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {  //判断得到的焦点控件是否包含EditText
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],    //得到输入框在屏幕中上下左右的位置
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击位置如果是EditText的区域，忽略它，不收起键盘。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略
        return false;
    }

    /* *
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token*/

    public void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /* *
     * 获取InputMethodManager，弹出软键盘
     *
     * @param view*/

    public void showKeyboard(View view) {
        if (view != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.showSoftInput(view, 0);
        }
    }


}