package com.example.caikaxuetang.chatactivitys.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.caikaxuetang.R;
import com.example.caikaxuetang.adapters.DateAdapter;
import com.example.caikaxuetang.contract.MainContract;
import com.example.caikaxuetang.responses.DateResponse;
import com.example.caikaxuetang.responses.GroupInfo;
import com.example.caikaxuetang.responses.GroupNoticeBean;
import com.example.caikaxuetang.responses.VersionEntity;
import com.example.caikaxuetang.responses.WallPaperResponse;
import com.example.caikaxuetang.utils.TestGetWeek;
import com.llw.mvplibrary.mvp.MvpActivity;
import com.llw.mvplibrary.network.utils.SpUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.rong.imlib.IRongCoreCallback;
import io.rong.imlib.IRongCoreEnum;
import io.rong.imlib.RongCoreClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;

public class DateSearchActivity extends MvpActivity<MainContract.MainPresenter> implements MainContract.IMainView {

    private int daysByYearMonth;
    private String targetId;
    private long startOfDayInMillis;
    private long endOfDayInMillis;
    private int month1;
    private RecyclerView rv_calendar;



    /**
     * 根据当前日期获得是星期几
     * time=yyyy-MM-dd
     *
     * @return
     */
    public static String getWeek(String time) {
        String Week = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int wek = c.get(Calendar.DAY_OF_WEEK);

        if (wek == 1) {
            Week += "星期日";
        }
        if (wek == 2) {
            Week += "星期一";
        }
        if (wek == 3) {
            Week += "星期二";
        }
        if (wek == 4) {
            Week += "星期三";
        }
        if (wek == 5) {
            Week += "星期四";
        }
        if (wek == 6) {
            Week += "星期五";
        }
        if (wek == 7) {
            Week += "星期六";
        }
        return Week;
    }

    /* *
             *
     @param
     date the
     date in
     the format "yyyy-MM-dd"
 */
    public long getStartOfDayInMillis(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(format.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

  /*  *
            *
    @param
    date the
    date in
    the format "yyyy-MM-dd"*/

    public long getEndOfDayInMillis(String date) {
        // Add one day's time to the beginning of the day.
        // 24 hours * 60 minutes * 60 seconds * 1000 milliseconds = 1 day
        return getStartOfDayInMillis(date) + (24 * 60 * 60 * 1000);
    }

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
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(this.getResources().getColor(R.color.white));
        Intent intent = getIntent();
        targetId = intent.getStringExtra("targetId");
        rv_calendar = findViewById(R.id.rv_calendar);
        TextView topTitle = findViewById(R.id.tv_title_top);
        RelativeLayout mRlBack = findViewById(R.id.rl_left);
        mRlBack.setOnClickListener(v -> finish());
        topTitle.setText("查找聊天记录");

        new Thread(new Runnable() {

            private ArrayList<DateResponse> list;

            @Override
            public void run() {

                Calendar calendar = Calendar.getInstance();
                //年
                int year = calendar.get(Calendar.YEAR);
                //月
                int month = calendar.get(Calendar.MONTH) + 1;

                int day = calendar.get(Calendar.DAY_OF_MONTH);

                month1 = (int) SpUtils.getSpInt(DateSearchActivity.this, "month", 0);
                if (month1 == 0) {
                    SpUtils.putSpInt(DateSearchActivity.this, "month", month);
                }

                month1 = (int) SpUtils.getSpInt(DateSearchActivity.this, "month", 0);
                daysByYearMonth = TestGetWeek.getDaysByYearMonth(year, month1);
                list = new ArrayList<>();

                for (int i = 1; i <= month1; i++) {
                    DateResponse dateBean = new DateResponse();
                    ArrayList<DateResponse.dayBean> dayBeans1 = new ArrayList<>();
                    dateBean.setDate(+year + "年" + i + "月");
                    daysByYearMonth = TestGetWeek.getDaysByYearMonth(year, i);
                    int finalI = i;
                    for (int j = 1; j <= daysByYearMonth; j++) {
                        String dateString = year + "-" + i + "-" + j;
                        startOfDayInMillis = getStartOfDayInMillis(dateString);
                        endOfDayInMillis = getEndOfDayInMillis(dateString);
                        Log.e("timess", "initView: " + startOfDayInMillis + "---" + endOfDayInMillis);
                        int finalJ = j;
                        RongCoreClient.getInstance().searchMessages(Conversation.ConversationType.GROUP, targetId, "", startOfDayInMillis, endOfDayInMillis, 0, 10, new IRongCoreCallback.ResultCallback<List<Message>>() {
                            @Override
                            public void onSuccess(List<Message> messages) {
                                DateResponse.dayBean dayBean = new DateResponse.dayBean();
                                dayBean.setEvery_day(finalJ);
                                dayBean.setTargetId(targetId);
                                if (messages != null && messages.size() != 0) {
                                    dayBean.setHaveMessage(true);
                                    dayBean.setTaspTime(messages.get(0).getSentTime());
                                } else {
                                    dayBean.setHaveMessage(false);
                                }
                                if (finalI==month&&finalJ==day){
                                    dayBean.setToday(true);
                                }else{
                                    dayBean.setToday(false);
                                }
                                dayBeans1.add(dayBean);
                            }
                            @Override
                            public void onError(IRongCoreEnum.CoreErrorCode coreErrorCode) {
                                Log.e("error", "onError: " + coreErrorCode);
                            }
                        });
                    }
                    String dateStr = calendar.get(Calendar.YEAR) + "-" + i + "-" + 1;
                    String week = getWeek(dateStr);
                    int listSize = 0;
                    if ("星期一".equals(week)) {
                        listSize = 1;
                    } else if ("星期二".equals(week)) {
                        listSize = 2;
                    } else if ("星期三".equals(week)) {
                        listSize = 3;
                    } else if ("星期四".equals(week)) {
                        listSize = 4;
                    } else if ("星期五".equals(week)) {
                        listSize = 5;
                    } else if ("星期六".equals(week)) {
                        listSize = 6;
                    }
                    for (int s = 0; s < listSize; s++) {
                        DateResponse.dayBean dayBean = new DateResponse.dayBean();
                        dayBeans1.add(dayBean);
                    }
                    dateBean.setDay(dayBeans1);
                    list.add(dateBean);

                    runOnUiThread(() -> {
                        DateAdapter dateAdapter = new DateAdapter(finalI,list, DateSearchActivity.this);
                        rv_calendar.setLayoutManager(new LinearLayoutManager(DateSearchActivity.this));
                        rv_calendar.setAdapter(dateAdapter);
                        dateAdapter.notifyDataSetChanged();
                    });
                }
            }
        }).start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_date_search;
    }
}