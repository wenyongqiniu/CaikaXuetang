package com.example.caikaxuetang.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.caikaxuetang.R;
import com.example.caikaxuetang.responses.DateResponse;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class DateAdapter extends RecyclerView.Adapter<DateAdapter.ViewHolder> {
    private List<DateResponse> list;
    private Context context;
    private int month;

    public DateAdapter(int i, List<DateResponse> list, Context context) {
        this.month = i;
        this.list = list;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_day, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.tv_day.setText(list.get(position).getDate() + "");
        DateNumAdapter dateNumAdapter = new DateNumAdapter(month,list.get(position).getDay(), context);
        holder.rv_num.setLayoutManager(new GridLayoutManager(context, 7));
        holder.rv_num.setAdapter(dateNumAdapter);
        dateNumAdapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tv_day;
        private final RecyclerView rv_num;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tv_day = itemView.findViewById(R.id.tv_day);
            rv_num = itemView.findViewById(R.id.rv_num);
        }
    }
}
