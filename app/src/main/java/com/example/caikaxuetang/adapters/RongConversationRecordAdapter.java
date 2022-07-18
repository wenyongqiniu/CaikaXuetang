package com.example.caikaxuetang.adapters;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.caikaxuetang.R;
import com.example.caikaxuetang.utils.RoundImageView;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.rong.imkit.userinfo.RongUserInfoManager;
import io.rong.imkit.utils.RouteUtils;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;

public class RongConversationRecordAdapter extends RecyclerView.Adapter<RongConversationRecordAdapter.ViewHolder> {

    private ArrayList<Message> list;
    private Context context;


    public RongConversationRecordAdapter(ArrayList<Message> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_conversation_list, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Message message = list.get(position);
        MessageContent content = list.get(position).getContent();
        UserInfo userInfo1 = content.getUserInfo();
        if (userInfo1 != null && userInfo1.getUserId() != null) {
            UserInfo userInfo = RongUserInfoManager.getInstance().getUserInfo(userInfo1.getUserId());
            Glide.with(context).load(userInfo.getPortraitUri()+"").into(holder.mConversationHead);
            holder.mTvConversationName.setText(userInfo.getName());
        }
        holder.mTvMessageTime.setText(timeStamp2Date(message.getSentTime(), ""));
        JSONObject jsonObject = null;
        try {
            String json = new Gson().toJson(content);
            jsonObject = new JSONObject(json);
            String content1 = (String) jsonObject.get("content");
            holder.mTvConversationContent.setText(content1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle1 = new Bundle();
                bundle1.putLong(RouteUtils.INDEX_MESSAGE_TIME, message.getSentTime()); //打开会话页面时的默认跳转位置，如果不配置将跳转到消息列表底部
                RouteUtils.routeToConversationActivity(context, Conversation.ConversationType.GROUP, message.getTargetId(), bundle1);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public static String timeStamp2Date(long time, String format) {
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(time));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RoundImageView mConversationHead;
        private TextView mTvConversationName;
        private TextView mTvConversationContent;
        private TextView mTvMessageTime;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            mConversationHead = itemView.findViewById(R.id.conversation_head);

            mTvConversationName = itemView.findViewById(R.id.tv_conversation_name);
            mTvConversationContent = itemView.findViewById(R.id.tv_conversation_content);
            mTvMessageTime = itemView.findViewById(R.id.tv_message_time);
        }
    }
}
