package com.example.caikaxuetang.adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.caikaxuetang.R;
import com.example.caikaxuetang.responses.DateResponse;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.List;

import io.rong.imkit.utils.RouteUtils;
import io.rong.imlib.model.Conversation;

public class DateNumAdapter extends RecyclerView.Adapter<DateNumAdapter.ViewHolder> {
    private List<DateResponse.dayBean> list;
    private Context context;
    private int months;

    public DateNumAdapter(int month, List<DateResponse.dayBean> list, Context context) {
        this.months = month;
        this.list = list;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_day_num, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        DateResponse.dayBean dayBean = list.get(position);
        if (dayBean.getEvery_day() == 0) {
            holder.tv_day.setVisibility(View.GONE);
        } else {
            holder.tv_day.setText(dayBean.getEvery_day() + "");
            holder.tv_day.setVisibility(View.VISIBLE);
        }

        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (dayBean.isHaveMessage() == false) {
            holder.tv_day.setTextColor(Color.parseColor("#BAB7B7"));
        } else {
            holder.tv_day.setTextColor(Color.parseColor("#000000"));
        }
        if (dayBean.isToday() == true) {
            holder.tv_day.setBackgroundResource(R.drawable.shape_green);
            holder.tv_day.setTextColor(Color.parseColor("#FFFFFF"));
            holder.tv_today.setVisibility(View.VISIBLE);
        } else {
            holder.tv_today.setVisibility(View.GONE);
            // holder.tv_day.setBackgroundResource(R.drawable.shape_white);
            //holder.tv_day.setTextColor(Color.parseColor("#BAB7B7"));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dayBean.isHaveMessage() == true) {
                    Bundle bundle1 = new Bundle();
                    bundle1.putLong(RouteUtils.INDEX_MESSAGE_TIME, list.get(position).getTaspTime()); //打开会话页面时的默认跳转位置，如果不配置将跳转到消息列表底部
                    RouteUtils.routeToConversationActivity(context, Conversation.ConversationType.GROUP, dayBean.getTargetId(), bundle1);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tv_day;
        private final TextView tv_today;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tv_day = itemView.findViewById(R.id.tv_day);
            tv_today = itemView.findViewById(R.id.tv_today);
        }
    }
}
