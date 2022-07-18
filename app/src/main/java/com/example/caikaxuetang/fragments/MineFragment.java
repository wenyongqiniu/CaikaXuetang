package com.example.caikaxuetang.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.caikaxuetang.LoginActivity;
import com.example.caikaxuetang.R;
import com.example.caikaxuetang.YsActivity;
import com.example.caikaxuetang.myactivitys.HetongActivity;
import com.example.caikaxuetang.myactivitys.MyClassActivity;
import com.example.caikaxuetang.utils.RoundImageView;
import com.llw.mvplibrary.network.utils.SpUtils;
import com.llw.mvplibrary.network.utils.StatusBarUtils;

import static com.example.caikaxuetang.MyApplication.context;


public class MineFragment extends Fragment {


    public MineFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_mine, container, false);
        StatusBarUtils.setColor(getActivity(), Color.parseColor("#FA6637"));
        StatusBarUtils.setTextDark(getActivity(), true);
        ImageView iv_mine = inflate.findViewById(R.id.iv_mine);
        TextView tv_my_class = inflate.findViewById(R.id.tv_my_class);
        TextView tv_my_contract = inflate.findViewById(R.id.tv_my_contract);
        TextView tv_my_classs = inflate.findViewById(R.id.tv_my_class);
        TextView tv_name_mine = inflate.findViewById(R.id.tv_name_mine);
        TextView tv_quiet_login = inflate.findViewById(R.id.tv_quiet_login);
        TextView tv_study_no = inflate.findViewById(R.id.tv_study_no);
        TextView tv_my_xieyi = inflate.findViewById(R.id.tv_my_xieyi);
        TextView tv_my_service = inflate.findViewById(R.id.tv_my_service);
        String headImg = SpUtils.getSpString(getActivity(), "headImg", "");
        String nickName = SpUtils.getSpString(getActivity(), "nickName", "");
        String studyNo = SpUtils.getSpString(getActivity(), "studyNo", "");
        Glide.with(getActivity()).load(headImg).apply(new RequestOptions().circleCrop()).into(iv_mine);
        tv_name_mine.setText(nickName);
        tv_study_no.setText(studyNo);

        tv_my_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, YsActivity.class);
                intent.putExtra("ystype", "service");
                startActivity(intent);
            }
        });
        tv_my_xieyi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, YsActivity.class);
                intent.putExtra("ystype","ys");
                startActivity(intent);
            }
        });
        //我的合同
        tv_my_contract.setOnClickListener(v -> startActivity(new Intent(getActivity(), HetongActivity.class)));
        tv_my_classs.setOnClickListener(v -> startActivity(new Intent(getActivity(), MyClassActivity.class)));

        //退出登录
        tv_quiet_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                SpUtils.remove(getActivity(), "token");
                startActivity(intent);
                getActivity().finish();
            }
        });
        return inflate;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            StatusBarUtils.setColor(getActivity(), Color.parseColor("#FA6637"));
        } else {
            StatusBarUtils.setColor(getActivity(), Color.parseColor("#F8F8F8"));
        }
        StatusBarUtils.setTextDark(getActivity(), true);
    }

}