package com.example.caikaxuetang.chatactivitys.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.caikaxuetang.R;
import com.example.caikaxuetang.adapters.RongConversationRecordAdapter;
import com.example.caikaxuetang.utils.EditTextWithDelView;

import java.util.ArrayList;
import java.util.List;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;

public class ClimbBuildingActivity extends AppCompatActivity {
    private EditTextWithDelView ed_input_info;
    private TextView tv_cancel;
    private TextView tv_date;
    private RecyclerView rv_info;
    private String keyword;
    private ArrayList<Message> list;
    private RongConversationRecordAdapter rongConversationRecordAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_climb_buiding);
        initView();
    }

    private void initView() {
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#F3F3F3"));
        Intent intent = getIntent();
        String targetId = intent.getStringExtra("targetId");
        ed_input_info = findViewById(R.id.ed_input_info);
        tv_cancel = findViewById(R.id.tv_cancel);
        rv_info = findViewById(R.id.rv_info);
        tv_date = findViewById(R.id.tv_date);

        list = new ArrayList<>();
        rongConversationRecordAdapter = new RongConversationRecordAdapter(list, this);
        rv_info.setLayoutManager(new LinearLayoutManager(this));
        rv_info.setAdapter(rongConversationRecordAdapter);

        ed_input_info.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                keyword = s.toString();
                if (keyword != null && !TextUtils.isEmpty(keyword.trim())) {
                    tv_cancel.setText("搜索");
                } else {
                    tv_cancel.setText("取消");
                    list.clear();
                    rongConversationRecordAdapter.notifyDataSetChanged();
                    rv_info.setVisibility(View.GONE);
                }
            }
        });

        //根据日期搜索
        tv_date.setOnClickListener(v -> {
            Intent intent1 = new Intent(ClimbBuildingActivity.this, DateSearchActivity.class);
            intent1.putExtra("targetId", targetId);
            startActivity(intent1);
        });
        //确认或取消
        tv_cancel.setOnClickListener(v -> {
            if ("取消".equals(tv_cancel.getText().toString())) {
                finish();
            } else {
                RongIMClient.getInstance().searchMessages(Conversation.ConversationType.GROUP, targetId, keyword, 20, 0, new RongIMClient.ResultCallback<List<Message>>() {
                    @Override
                    public void onSuccess(List<Message> messages) {
                        if (messages != null) {
                            list.addAll(messages);
                            rongConversationRecordAdapter.notifyDataSetChanged();
                            rv_info.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {

                    }
                });
            }
        });
    }
}