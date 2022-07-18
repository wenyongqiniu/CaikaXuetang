package com.example.caikaxuetang.myactivitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.caikaxuetang.R;
import com.example.caikaxuetang.adapters.HetongAdapter;
import com.example.caikaxuetang.adapters.MyclassAdapter;
import com.example.caikaxuetang.contract.HetongContract;
import com.example.caikaxuetang.responses.HetongResponse;
import com.example.caikaxuetang.responses.MyClassResponse;
import com.ljb.page.PageState;
import com.ljb.page.PageStateLayout;
import com.llw.mvplibrary.mvp.MvpActivity;

import java.util.List;

public class MyClassActivity extends MvpActivity<HetongContract.MainPresenter> implements HetongContract.IMainView {


    private RecyclerView rv_class;
    private PageStateLayout page_layout;

    @Override
    protected HetongContract.MainPresenter createPresenter() {
        return new HetongContract.MainPresenter();
    }

    @Override
    public void getWallPaper(HetongResponse wallPaperResponse) {

    }

    @Override
    public void getMyClass(MyClassResponse wallPaperResponse) {
        if (wallPaperResponse.getCode() == 0) {
            List<MyClassResponse.DataBean> data = wallPaperResponse.getData();
            MyclassAdapter hetongAdapter = new MyclassAdapter(R.layout.item_myclass, data);
            rv_class.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
            rv_class.setAdapter(hetongAdapter);
            hetongAdapter.notifyDataSetChanged();
            page_layout.setPage(PageState.STATE_SUCCESS);
        } else {
            page_layout.setPage(PageState.STATE_EMPTY);
        }
    }

    @Override
    public void getFailed(Throwable e) {
        page_layout.setPage(PageState.STATE_ERROR);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        page_layout = findViewById(R.id.page_layout);
        page_layout.setContentView(R.layout.activity_my_class);
        TextView tv_title_top = findViewById(R.id.tv_title_top);
        RelativeLayout rl_left = findViewById(R.id.rl_left);
        tv_title_top.setText("我的班级");

        rl_left.setOnClickListener(view -> finish());
        rv_class = findViewById(R.id.rv_class);
        TextView retry = findViewById(R.id.retry);
        mPresenter.getMyClass();
        page_layout.setPage(PageState.STATE_LOADING);

        retry.setOnClickListener(v -> mPresenter.getMyClass());
    }

    @Override
    public int getLayoutId() {
        return R.layout.base_layout;
    }
}