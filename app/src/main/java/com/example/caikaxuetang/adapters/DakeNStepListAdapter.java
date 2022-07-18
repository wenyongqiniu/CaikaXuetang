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

public class DakeNStepListAdapter extends RecyclerView.Adapter<DakeNStepListAdapter.ViewHolder> {
    private final Context context;
    private final List<DakeStepResponse.DataBean.ProcessListBean> list;
    private int mPosition = -1;

    public DakeNStepListAdapter(Context context, List<DakeStepResponse.DataBean.ProcessListBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_dake_step, parent, false);

        return new ViewHolder(view);

    }

    //获取屏幕的宽度
    public static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getWidth();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DakeStepResponse.DataBean.ProcessListBean processListBean = list.get(position);


        if (list.size() > 3) {
            if (position == 0) {
                holder.view_step.setVisibility(View.GONE);
            } else {
                holder.rl_step.getLayoutParams().width = getScreenWidth(context) / list.size();
                holder.view_step.setVisibility(View.VISIBLE);
            }
        } else {
            //  holder.rl_step.getLayoutParams().width = getScreenWidth(context) / list.size();
            holder.view_step.getLayoutParams().width = getScreenWidth(context) * 7 / 24 - 20;
            if (position == 0) {
                holder.view_step.setVisibility(View.GONE);
            } else {
                holder.view_step.setVisibility(View.VISIBLE);
            }

        }

        if (processListBean.getStatus() == 0) {
            holder.tv_step.setText(position + 1 + "");
            holder.tv_step.setBackgroundResource(R.drawable.step_shape);
            holder.tv_step.setTextColor(Color.parseColor("#B7B7B7"));
            holder.view_step.setBackgroundColor(Color.parseColor("#B7B7B7"));
        } else if (processListBean.getStatus() == 1) {
            holder.tv_step.setBackgroundResource(R.mipmap.selected_step);
            holder.tv_step.setTextColor(Color.parseColor("#FFFFFF"));
            holder.view_step.setBackgroundColor(Color.parseColor("#FE8000"));
        } else {
            holder.tv_step.setText(position + 1 + "");
            holder.tv_step.setBackgroundResource(R.drawable.login_phone_shape);
            holder.tv_step.setTextColor(Color.parseColor("#FFFFFF"));
            holder.view_step.setBackgroundColor(Color.parseColor("#FE8000"));
        }
        if (list.get(position).getStatus() == 0 && mPosition == -1) {
            list.get(position).setStatus(2);
            mPosition = 100;
        }

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tv_step;
        private final View view_step;
        private final RelativeLayout rl_step;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_step = itemView.findViewById(R.id.tv_step);
            view_step = itemView.findViewById(R.id.view_step);
            rl_step = itemView.findViewById(R.id.rl_step);
        }
    }


}
