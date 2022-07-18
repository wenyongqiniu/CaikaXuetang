package com.example.caikaxuetang.fragments;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;


import com.example.caikaxuetang.R;
import com.example.caikaxuetang.contract.LearningCenterContract;
import com.example.caikaxuetang.responses.LearningCenterResponse;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.ljb.page.PageState;
import com.llw.mvplibrary.mvp.MvpFragment;
import com.ljb.page.PageStateLayout;
import com.llw.mvplibrary.network.utils.StatusBarUtils;
import com.stx.xhb.xbanner.XBanner;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LearningCenterFragment extends MvpFragment<LearningCenterContract.LearningCenterPresenter> implements LearningCenterContract.LearningCenterView {


    private XBanner mXBanner;
    private TabLayout mTabLayout;
    private ViewPager2 mVp2;
    private TextView retry;
    private TabLayoutMediator mediator;
    private SwipeRefreshLayout sml_learning;
    private ArrayList<Fragment> fragments;
    private List<LearningCenterResponse.DataBean.CourseTypeListBean> courseTypeList;
    private PageStateLayout page_layout;

    public LearningCenterFragment() {
        // Required empty public constructor
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            StatusBarUtils.setColor(getActivity(), Color.parseColor("#FFF7ED"));
        } else {
            StatusBarUtils.setColor(getActivity(), Color.parseColor("#F8F8F8"));
        }
        StatusBarUtils.setTextDark(getActivity(), true);
    }
    @Override
    public void getLearningCenter(LearningCenterResponse wallPaperResponse) {
        if (wallPaperResponse.getCode() == 0) {
            page_layout.setPage(PageState.STATE_SUCCESS);
            courseTypeList = wallPaperResponse.getData().getCourseTypeList();
            fragments = new ArrayList<>();
            for (int i = 0; i < courseTypeList.size(); i++) {
                fragments.add(new ShowFragment());
            }
            mVp2.setAdapter(new FragmentStateAdapter(this) {
                @NonNull
                @Override
                public Fragment createFragment(int position) {
                    return fragments.get(position);
                }

                @Override
                public int getItemCount() {
                    return courseTypeList.size();
                }
            });

            mediator = new TabLayoutMediator(mTabLayout, mVp2, new TabLayoutMediator.TabConfigurationStrategy() {
                @Override
                public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                    tab.setText(courseTypeList.get(position).getName());
                }
            });
            mediator.attach();
            for (int i = 0; i < courseTypeList.size(); i++) {
                Objects.requireNonNull(mTabLayout.getTabAt(i)).setCustomView(R.layout.main_top_item);
                TextView toMyTextView = Objects.requireNonNull(Objects.requireNonNull(mTabLayout.getTabAt(i)).getCustomView()).findViewById(R.id.tv_top_item);
                //默认选择第一个tab,设置字体大小和默认风格为加粗
                toMyTextView.setText(courseTypeList.get(i).getName());
            }

            TextView toMyTextView = Objects.requireNonNull(Objects.requireNonNull(mTabLayout.getTabAt(0)).getCustomView()).findViewById(R.id.tv_top_item);
            View tab_item_indicator = Objects.requireNonNull(Objects.requireNonNull(mTabLayout.getTabAt(0)).getCustomView()).findViewById(R.id.tab_item_indicator);
            tab_item_indicator.setVisibility(View.VISIBLE);
            toMyTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            toMyTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            toMyTextView.setTextColor(Color.parseColor("#2A2A2A"));
            Bundle bundle = new Bundle();
            bundle.putString("typeCode", courseTypeList.get(0).getType() + "");
            fragments.get(0).setArguments(bundle);
            //看这里看这里看这里
            mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    if (tab.getCustomView() != null) {
                        int position = tab.getPosition();
                        TextView tv = Objects.requireNonNull(tab.getCustomView()).findViewById(R.id.tv_top_item);
                        tv.setText(courseTypeList.get(position).getName());
                        tv.setTextColor(Color.parseColor("#2A2A2A"));
                        tv.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
                        tv.setTextSize(20);
                        View tab_item_indicator = Objects.requireNonNull(Objects.requireNonNull(mTabLayout.getTabAt(position)).getCustomView()).findViewById(R.id.tab_item_indicator);
                        tab_item_indicator.setVisibility(View.VISIBLE);
                        tv.invalidate();
                        Bundle bundle = new Bundle();
                        bundle.putString("typeCode", courseTypeList.get(position).getType() + "");
                        fragments.get(position).setArguments(bundle);
                        tab.getCustomView().findViewById(R.id.tv_top_item).setSelected(true);
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                    int position = tab.getPosition();
                    TextView tv = Objects.requireNonNull(tab.getCustomView()).findViewById(R.id.tv_top_item);
                    tv.setText(courseTypeList.get(position).getName());
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                    tv.setTextColor(Color.parseColor("#B5B5B5"));
                    View tab_item_indicator = Objects.requireNonNull(Objects.requireNonNull(mTabLayout.getTabAt(position)).getCustomView()).findViewById(R.id.tab_item_indicator);
                    tab_item_indicator.setVisibility(View.INVISIBLE);
                    tv.invalidate();
                    tab.getCustomView().findViewById(R.id.tv_top_item).setSelected(false);
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        } else {
            page_layout.setPage(PageState.STATE_EMPTY);
        }
        //sml_learning.setRefreshing(false);
    }

    @Override
    public void getFailed(Throwable e) {
        Log.e("TAG", "getFailed: "+e.getMessage() );
       // sml_learning.setRefreshing(false);
        page_layout.setPage(PageState.STATE_ERROR);
    }

    @Override
    public void onDestroy() {
        mediator.detach();
        super.onDestroy();
    }

    @Override
    protected LearningCenterContract.LearningCenterPresenter createPresent() {
        return new LearningCenterContract.LearningCenterPresenter();
    }


    @Override
    public void initData(Bundle savedInstanceState) {
        initView();
    }


    private void initView() {
        page_layout = rootView.findViewById(R.id.page_layout);
        page_layout.setContentView(R.layout.fragment_learning_center);
        mXBanner = rootView.findViewById(R.id.xbanner);
        mTabLayout = rootView.findViewById(R.id.tab_layout);
        mVp2 = rootView.findViewById(R.id.vp2_lc);
        retry = rootView.findViewById(R.id.retry);
        //sml_learning = rootView.findViewById(R.id.sml_learning);

        page_layout.setPage(PageState.STATE_LOADING);
        mPresenter.getLearningCenterPaper();

      /*  //刷新
        sml_learning.setOnRefreshListener(() -> {
            mPresenter.getLearningCenterPaper();
            page_layout.setPage(PageState.STATE_LOADING);
        });*/
        //点击重试
        retry.setOnClickListener(v -> {
            page_layout.setPage(PageState.STATE_LOADING);
            mPresenter.getLearningCenterPaper();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.base_layout;
    }
}