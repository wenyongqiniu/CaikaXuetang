package com.example.caikaxuetang.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.caikaxuetang.LoginActivity;
import com.example.caikaxuetang.R;
import com.example.caikaxuetang.adapters.ShowCourseListAdapter;
import com.example.caikaxuetang.adapters.ShowDakeCourseListAdapter;
import com.example.caikaxuetang.contract.ShowCourseListContract;
import com.example.caikaxuetang.learningActivitys.DakeStepActivity;
import com.example.caikaxuetang.learningActivitys.XbCourseListActivity;
import com.example.caikaxuetang.responses.LearningCenterResponse;
import com.example.caikaxuetang.responses.ShowCourseListResponse;
import com.example.caikaxuetang.responses.XbCourseListResponse;
import com.example.caikaxuetang.utils.CustomerToastUtils;
import com.example.caikaxuetang.utils.DonwloadSaveImg;
import com.ljb.page.PageState;
import com.ljb.page.PageStateLayout;
import com.llw.mvplibrary.mvp.MvpFragment;
import com.llw.mvplibrary.network.utils.SpUtils;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;


public class ShowFragment extends MvpFragment<ShowCourseListContract.ShowCourseListPresenter> implements ShowCourseListContract.ShowCourseListView {

    private RecyclerView rv_experience_class;
    private RecyclerView rv_advanced_course;
    private RelativeLayout rl_have_data;
    private RelativeLayout rl_no_data_x;
    private RelativeLayout rl_no_data;
    private RelativeLayout rl_dk_step;
    private RelativeLayout rl_go;
    private RelativeLayout rl_start_time;
    private RelativeLayout rl_add_teacher;
    private TextView tv_sign_the_contract;
    private TextView tv_sign_contract_tips;
    private TextView tv_come_on;
    private ImageView iv_go;
    private TextView tv_experience_class;
    private TextView tv_must_tips;
    private TextView tv_advanced_course;
    private TextView tv_start_time_course;
    private TextView tv_add_teacher;
    private PageStateLayout page_layout;
    private TextView retry;
    private SmartRefreshLayout sml_empty;
    private ShowCourseListResponse.DataBean.BigCourseBean bigCourse;
    private String typeCode;
    private ShowCourseListResponse.DataBean data;
    private ShowDakeCourseListAdapter showDakeCourseListAdapter;
    private ShowCourseListAdapter showCourseListAdapter;
    private ArrayList<ShowCourseListResponse.DataBean.BigCourseBean.BigCourseListBean> bigCourseListBeans;

    @Override
    public void onResume() {
        super.onResume();
        bigCourseListBeans.clear();
        mPresenter.getLearningCenterPaper();

    }

    @Override
    public void getShowCourseListCenter(ShowCourseListResponse wallPaperResponse) {
        if (wallPaperResponse.getCode() == 0) {
            page_layout.setPage(PageState.STATE_SUCCESS);
            data = wallPaperResponse.getData();

            if (data.getBigCourse() != null) {
                if (data.getBigCourse().getBigCourseList() != null && data.getBigCourse().getBigCourseList().size() > 0) {
                    bigCourseListBeans.addAll(data.getBigCourse().getBigCourseList());
                    showDakeCourseListAdapter.notifyDataSetChanged();
                }
            }

            if (data.getXbCourseList() != null && data.getXbCourseList().size() > 0) {
                showCourseListAdapter = new ShowCourseListAdapter(R.layout.item_xb, data.getXbCourseList());
                showCourseListAdapter.notifyDataSetChanged();
                rv_experience_class.setLayoutManager(new LinearLayoutManager(context));
                rv_experience_class.setAdapter(showCourseListAdapter);

                rv_experience_class.setVisibility(View.VISIBLE);
                tv_advanced_course.setVisibility(View.VISIBLE);
                rl_no_data_x.setVisibility(View.INVISIBLE);
            } else {
                rv_experience_class.setVisibility(View.GONE);
                rl_no_data_x.setVisibility(View.VISIBLE);
            }
            bigCourse = data.getBigCourse();
            if (bigCourse != null) {
                rl_dk_step.setVisibility(View.VISIBLE);
                rl_no_data.setVisibility(View.INVISIBLE);
                if (bigCourse.getContractStatus() != null && bigCourse.getContractStatus() == 0) {
                    tv_sign_the_contract.setText(getString(R.string.sign_the_contract));
                    tv_sign_contract_tips.setText(getString(R.string.sign_contract_tips));
                    tv_come_on.setText(getString(R.string.now_sign));
                    iv_go.setBackgroundResource(R.mipmap.ht);
                    rl_dk_step.setBackgroundResource(R.drawable.rl_contract);
                } else if (bigCourse.getAddressStatus() != null && bigCourse.getAddressStatus() == 0) {
                    tv_sign_the_contract.setText(getString(R.string.fill_in_address));
                    tv_sign_contract_tips.setText(getString(R.string.fill_in_address_tips));
                    tv_come_on.setText(getString(R.string.now_fill_in));
                    rl_dk_step.setBackgroundResource(R.drawable.rl_address);
                    iv_go.setBackgroundResource(R.mipmap.address_icon);
                } else if (bigCourse.getSelectClassStatus() != null && bigCourse.getSelectClassStatus() == 0) {
                    tv_sign_the_contract.setText(getString(R.string.select_time));
                    tv_sign_contract_tips.setText(getString(R.string.select_appropriate_time));
                    tv_come_on.setText(getString(R.string.now_select));
                    rl_dk_step.setBackgroundResource(R.drawable.rl_select_time);
                    iv_go.setBackgroundResource(R.mipmap.time_icon);
                } else if (bigCourse.getShowTeacherStatus() != null && bigCourse.getShowTeacherStatus() == 0) {
                    tv_start_time_course.setText(String.format("%s%s", getString(R.string.start_time1), bigCourse.getStartTime()));
                    rl_start_time.setVisibility(View.VISIBLE);
                    rl_dk_step.setVisibility(View.INVISIBLE);

                } else {
                    tv_start_time_course.setText(R.string.add_teacher);
                    tv_must_tips.setText(R.string.add_teacher_must_tips);
                    rl_start_time.setVisibility(View.VISIBLE);
                    rl_dk_step.setVisibility(View.INVISIBLE);
                    rl_add_teacher.setVisibility(View.VISIBLE);
                }
            } else {
                rl_no_data.setVisibility(View.VISIBLE);
                rl_dk_step.setVisibility(View.INVISIBLE);
                rl_start_time.setVisibility(View.INVISIBLE);
                if (data.getXbCourseList() != null && data.getXbCourseList().size() > 0) {
                } else {
                    tv_advanced_course.setVisibility(View.INVISIBLE);
                    page_layout.setPage(PageState.STATE_EMPTY);
                }
            }
        }
    }

    @Override
    public void getLearningCenter(LearningCenterResponse wallPaperResponse) {

        if (wallPaperResponse.getCode() == 0) {
            if (wallPaperResponse.getData().getCourseTypeList() != null && wallPaperResponse.getData().getCourseTypeList().size() != 0) {
                data = new ShowCourseListResponse.DataBean();
                mPresenter.getShowCourseListPaper(5 + "");
            } else {
                page_layout.setPage(PageState.STATE_EMPTY);
            }
        } else if (wallPaperResponse.getCode() == 1017) {
            SpUtils.remove(getActivity(), "token");
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            CustomerToastUtils.toastShow(getActivity()).show();
            CustomerToastUtils.tv_toast.setText("登录超时");
            getActivity().finish();
        } else {
            page_layout.setPage(PageState.STATE_EMPTY);
        }

    }

    @Override
    public void getFailed(Throwable e) {
        page_layout.setPage(PageState.STATE_ERROR);
    }

    @Override
    protected ShowCourseListContract.ShowCourseListPresenter createPresent() {
        return new ShowCourseListContract.ShowCourseListPresenter();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        page_layout = rootView.findViewById(R.id.page_layout);
        page_layout.setContentView(R.layout.fragment_show);

        rv_experience_class = rootView.findViewById(R.id.rv_experience_class);
        rv_advanced_course = rootView.findViewById(R.id.rv_advanced_course);
        rl_have_data = rootView.findViewById(R.id.rl_have_data);
        rl_no_data = rootView.findViewById(R.id.rl_no_data);
        rl_no_data_x = rootView.findViewById(R.id.rl_no_data_x);
        rl_dk_step = rootView.findViewById(R.id.rl_dk_step);
        rl_go = rootView.findViewById(R.id.rl_go);
        rl_start_time = rootView.findViewById(R.id.rl_start_time);
        rl_add_teacher = rootView.findViewById(R.id.rl_add_teacher);
        tv_add_teacher = rootView.findViewById(R.id.tv_add_teacher);
        tv_sign_the_contract = rootView.findViewById(R.id.tv_sign_the_contract);
        tv_sign_contract_tips = rootView.findViewById(R.id.tv_sign_contract_tips);
        tv_come_on = rootView.findViewById(R.id.tv_come_on);
        tv_experience_class = rootView.findViewById(R.id.tv_experience_class);
        tv_advanced_course = rootView.findViewById(R.id.tv_advanced_course);
        iv_go = rootView.findViewById(R.id.iv_go);
        tv_start_time_course = rootView.findViewById(R.id.tv_start_time_course);
        tv_must_tips = rootView.findViewById(R.id.tv_must_tips);
        page_layout.setPage(PageState.STATE_LOADING);
        retry = rootView.findViewById(R.id.retry);
        sml_empty = rootView.findViewById(R.id.sml_empty);

        //添加老师微信
        tv_add_teacher.setOnClickListener(view -> showTeacherPop());

        SmartRefreshLayout sml_show = rootView.findViewById(R.id.sml_show);

        sml_show.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                sml_show.finishLoadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                data = new ShowCourseListResponse.DataBean();
                bigCourse = new ShowCourseListResponse.DataBean.BigCourseBean();
                bigCourseListBeans.clear();
                mPresenter.getLearningCenterPaper();
                sml_show.finishRefresh();
                showDakeCourseListAdapter.notifyDataSetChanged();

            }
        });

        sml_empty.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                sml_empty.finishLoadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                data = new ShowCourseListResponse.DataBean();
                bigCourse = new ShowCourseListResponse.DataBean.BigCourseBean();
                bigCourseListBeans.clear();
                mPresenter.getLearningCenterPaper();
                page_layout.setPage(PageState.STATE_LOADING);
                sml_empty.finishRefresh();
                showDakeCourseListAdapter.notifyDataSetChanged();
            }
        });

        //加载页面出现错误监听
        retry.setOnClickListener(v -> {
            page_layout.setPage(PageState.STATE_LOADING);
            mPresenter.getShowCourseListPaper(5 + "");
        });

        rl_go.setOnClickListener(v -> {
            Intent intent = new Intent(context, DakeStepActivity.class);
            intent.putExtra("bigCourse", bigCourse);
            intent.putExtra("packageId", bigCourse.getPackageId());
            startActivity(intent);
        });

        bigCourseListBeans = new ArrayList<>();
        showDakeCourseListAdapter = new ShowDakeCourseListAdapter(R.layout.item_dake, bigCourseListBeans);
        rv_advanced_course.setLayoutManager(new LinearLayoutManager(context));
        rv_advanced_course.setAdapter(showDakeCourseListAdapter);
    }

    //弹出老师二维码
    private void showTeacherPop() {
        new XPopup.Builder(context)
                .enableDrag(true)
                .asCustom(new addWxPop(context, data))
                .show();
    }

    public static class addWxPop extends BasePopupView {
        private Context context;
        private ShowCourseListResponse.DataBean dataBean;

        public addWxPop(@NonNull Context context, ShowCourseListResponse.DataBean data) {
            super(context);
            this.context = context;
            this.dataBean = data;
        }

        @Override
        protected int getPopupLayoutId() {
            return R.layout.pop_wx;
        }

        @Override
        protected void onCreate() {
            super.onCreate();
            ImageView iv_rccode = findViewById(R.id.iv_rc_code);
            TextView tv_keep = findViewById(R.id.tv_keep);
            Glide.with(context).load(dataBean.getBigCourse().getTeacherQrCode()).into(iv_rccode);
            tv_keep.setOnClickListener(view -> {
                DonwloadSaveImg.donwloadImg(context, dataBean.getBigCourse().getTeacherQrCode());
                dismiss();
            });

        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.base_layout;
    }
}
