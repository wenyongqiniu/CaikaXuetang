package com.example.caikaxuetang.adapters;


import android.content.Context;
import android.graphics.Color;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.caikaxuetang.R;
import com.example.caikaxuetang.responses.DakeStepResponse;

import java.util.List;

public class DakeStepListAdapter extends RecyclerView.Adapter<DakeStepListAdapter.ViewHolder> {
    private final Context context;
    private final List<DakeStepResponse.DataBean.ProcessListBean> list;
    private int mPosition = -1;

    public DakeStepListAdapter(Context context, List<DakeStepResponse.DataBean.ProcessListBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_dake_step2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DakeStepResponse.DataBean.ProcessListBean processListBean = list.get(position);
        holder.tv_step2.setText(processListBean.getName());
        if (list.size() > 3) {
            holder.tv_step2.getLayoutParams().width = getScreenWidth(context) / list.size();
        } else {
            holder.tv_step2.getLayoutParams().width = getScreenWidth(context) / list.size();
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.tv_step2.getLayoutParams();
            layoutParams.setMarginStart(15);
            holder.tv_step2.setLayoutParams(layoutParams);
        }


        if (list.get(position).getStatus() == 0 && mPosition == -1) {
            list.get(position).setStatus(2);
            mPosition++;
        }
        if (processListBean.getStatus() == 0) {
            holder.tv_step2.setTextColor(Color.parseColor("#B7B7B7"));
        } else {
            holder.tv_step2.setTextColor(Color.parseColor("#FE8000"));
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_step2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_step2 = itemView.findViewById(R.id.tv_step2);

        }
    }

    //获取屏幕的宽度
    public static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getWidth();
    }

}
