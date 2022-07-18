package com.example.caikaxuetang.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.caikaxuetang.R;
import com.example.caikaxuetang.responses.GroupNumberResponse;


import java.util.ArrayList;

public class GroupInfoListAdapter extends RecyclerView.Adapter<GroupInfoListAdapter.ViewHolder> {
    private ArrayList<GroupNumberResponse.DataBean.OrdinaryUserListBean> list;
    private Context context;
    private onItemClickListener onItemClickListener;

    public void setOnItemClickListener(GroupInfoListAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public GroupInfoListAdapter(ArrayList<GroupNumberResponse.DataBean.OrdinaryUserListBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_firend, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        GroupNumberResponse.DataBean.OrdinaryUserListBean ordinaryUserListBean = list.get(position);
        if (ordinaryUserListBean != null) {
            Glide.with(context).load(ordinaryUserListBean.getHeadImage()).placeholder(R.drawable.rc_picture_icon_data_error).into(holder.mIvHead);
            if (ordinaryUserListBean.getNickname()!=null&&!ordinaryUserListBean.getNickname().equals("")){
                holder.mTvName.setText(ordinaryUserListBean.getNickname()+"");
            }else{
                holder.mTvName.setText("无昵称");
            }
            holder.mTvToken.setText(ordinaryUserListBean.getId() + "");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mIvHead;
        private TextView mTvName;
        private TextView mTvToken;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mIvHead = itemView.findViewById(R.id.iv_head);
            mTvName = itemView.findViewById(R.id.tv_name);
            mTvToken = itemView.findViewById(R.id.tv_token);
        }
    }

    public interface onItemClickListener {
        void onItemClick(int position);
    }

}
