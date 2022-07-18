package com.example.caikaxuetang.learningActivitys;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.caikaxuetang.R;
import com.example.caikaxuetang.adapters.CourseTimeAdapter;
import com.example.caikaxuetang.adapters.DakeNStepListAdapter;
import com.example.caikaxuetang.adapters.DakeStepListAdapter;
import com.example.caikaxuetang.contract.DakeStepContract;

import com.example.caikaxuetang.responses.AddressResponse;
import com.example.caikaxuetang.responses.ConfirmCourseTineResponse;
import com.example.caikaxuetang.responses.ContractResponse;
import com.example.caikaxuetang.responses.CourseTimeResponse;
import com.example.caikaxuetang.responses.DakeStepResponse;
import com.example.caikaxuetang.responses.SealContractResponse;
import com.example.caikaxuetang.responses.ShowCourseListResponse;
import com.example.caikaxuetang.responses.SubmitAddressResponse;
import com.example.caikaxuetang.utils.CoustomerDialog;
import com.example.caikaxuetang.utils.CustomerToastUtils;
import com.example.caikaxuetang.utils.HidePhone;
import com.example.caikaxuetang.utils.PhoneFormatCheckUtils;
import com.example.caikaxuetang.utils.PhoneNumberUtils;
import com.example.caikaxuetang.utils.ToastUtils;
import com.ljb.page.PageState;
import com.ljb.page.PageStateLayout;
import com.llw.mvplibrary.mvp.MvpActivity;
import com.llw.mvplibrary.network.utils.DateUtil;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.List;


public class DakeStepActivity extends MvpActivity<DakeStepContract.DakeStepPresenter> implements DakeStepContract.DakeStepView {


    private RecyclerView rv_dake_step;
    private RecyclerView rv_dake_step2;
    private RecyclerView rv_time;
    private TextView tv_part_a_name;
    private TextView tv_part_a_address;
    private TextView tv_start_time;
    private WebView web_contract;
    private RelativeLayout contract_include;
    private RelativeLayout hava_address_include;
    private RelativeLayout no_address_include;
    private RelativeLayout select_time_include;
    private RelativeLayout start_study_include;
    private String contractId = "";
    private String realName = "";
    private String certNo = "";
    private String mobile = "";
    private String address = "";
    private PageStateLayout page_layout;
    private String packageId;
    private TextView tv_sure;
    private TextView tv_address_des;
    private TextView tv_name_phone;
    private TextView retry;
    private String classId = "";
    private String addressId = "";
    private AddressResponse.DataBean data;
    private ShowCourseListResponse.DataBean.BigCourseBean bigCourse;
    private String shAddress = "";
    private List<DakeStepResponse.DataBean.ProcessListBean> processList;

    @Override
    protected DakeStepContract.DakeStepPresenter createPresenter() {
        return new DakeStepContract.DakeStepPresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void getDakeStep(DakeStepResponse wallPaperResponse) {
        if (wallPaperResponse.getCode() == 0) {
            page_layout.setPage(PageState.STATE_SUCCESS);
            processList = wallPaperResponse.getData().getProcessList();
            DakeNStepListAdapter showCourseListAdapter = new DakeNStepListAdapter(DakeStepActivity.this, processList);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false) {
                @Override
                public boolean canScrollHorizontally() {
                    return false;
                }
            };
            rv_dake_step.setLayoutManager(layoutManager);
            rv_dake_step.setAdapter(showCourseListAdapter);
            showCourseListAdapter.notifyDataSetChanged();

            DakeStepListAdapter showCourseListAdapter2 = new DakeStepListAdapter(DakeStepActivity.this, wallPaperResponse.getData().getProcessList());
            LinearLayoutManager layoutManager2 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false) {
                @Override
                public boolean canScrollHorizontally() {
                    return false;
                }
            };
            rv_dake_step2.setLayoutManager(layoutManager2);
            rv_dake_step2.setAdapter(showCourseListAdapter2);
            showCourseListAdapter2.notifyDataSetChanged();

        }

    }

    @Override
    public void getContract(ContractResponse wallPaperResponse) {
        if (wallPaperResponse.getCode() == 0) {
            page_layout.setPage(PageState.STATE_SUCCESS);
            ContractResponse.DataBean data = wallPaperResponse.getData();
            contractId = data.getId();
            tv_part_a_name.setText(data.getCompany());
            tv_part_a_address.setText(data.getCompanyAddress());
            web_contract.loadDataWithBaseURL(null, data.getContent(), "text/html", "utf-8", null);
        } else {
            page_layout.setPage(PageState.STATE_ERROR);
        }
    }

    @Override
    public void getSealContract(SealContractResponse wallPaperResponse) {
        if (wallPaperResponse.getCode() == 0) {
            page_layout.setPage(PageState.STATE_SUCCESS);
            mPresenter.getDakeStep(packageId);
            CustomerToastUtils.toastShow(DakeStepActivity.this).show();
            CustomerToastUtils.tv_toast.setText(getString(R.string.commit_success));
            bigCourse.setContractStatus(1);
            contract_include.setVisibility(View.INVISIBLE);
            for (int i = 0; i < processList.size(); i++) {
                if (processList.get(i).getProcessType().equals("DZ")) {
                    bigCourse.setAddressStatus(processList.get(i).getStatus());
                    break;
                }
            }
            if (bigCourse.getAddressStatus() == 0) {
                mPresenter.getAddress();//查询是否有地址
            } else {
                mPresenter.getCourseTime(packageId);
            }
        } else {
            page_layout.setPage(PageState.STATE_SUCCESS);
            CustomerToastUtils.toastShow(context).show();
            CustomerToastUtils.tv_toast.setText("验证失败");
        }
    }

    @Override
    public void getAddress(AddressResponse wallPaperResponse) {
        if (wallPaperResponse.getCode() == 0) {
            page_layout.setPage(PageState.STATE_SUCCESS);
            data = wallPaperResponse.getData();
            if (data.getAddress() == null) {
                tv_sure.setText("补充地址");
                no_address_include.setVisibility(View.VISIBLE);
                hava_address_include.setVisibility(View.GONE);
            } else {
                tv_sure.setText("确认提交");
                hava_address_include.setVisibility(View.VISIBLE);
                String phone = HidePhone.phoneNumber(data.getMobile());
                tv_name_phone.setText(data.getRealName() + "    " + phone);
                tv_address_des.setText(data.getAddress());
                shAddress = data.getAddress();
            }
        } else {
            page_layout.setPage(PageState.STATE_ERROR);
            CustomerToastUtils.toastShow(context).show();
            CustomerToastUtils.tv_toast.setText("加载失败");
        }

    }

    @Override
    public void getCourseTime(CourseTimeResponse wallPaperResponse) {
        if (wallPaperResponse.getCode() == 0) {
            page_layout.setPage(PageState.STATE_SUCCESS);
            CourseTimeAdapter courseTimeAdapter = new CourseTimeAdapter(R.layout.item_course_time, wallPaperResponse.getData());
            courseTimeAdapter.notifyDataSetChanged();
            rv_time.setLayoutManager(new GridLayoutManager(context, 2));
            rv_time.setAdapter(courseTimeAdapter);
            select_time_include.setVisibility(View.VISIBLE);
        } else {
            page_layout.setPage(PageState.STATE_ERROR);
            CustomerToastUtils.toastShow(context).show();
            CustomerToastUtils.tv_toast.setText("加载失败");
        }

    }

    @Override
    public void getConfirmTime(ConfirmCourseTineResponse wallPaperResponse) {
        if (wallPaperResponse.getCode() == 0) {
            page_layout.setPage(PageState.STATE_SUCCESS);
            CustomerToastUtils.toastShow(this).show();
            CustomerToastUtils.tv_toast.setText("选择成功");
            bigCourse.setSelectClassStatus(1);
            mPresenter.getDakeStep(packageId);
            select_time_include.setVisibility(View.INVISIBLE);
            start_study_include.setVisibility(View.VISIBLE);
            tv_sure.setText("去学习");
            tv_start_time.setText("开课时间：" + CourseTimeAdapter.time);
        } else {
            page_layout.setPage(PageState.STATE_SUCCESS);
            CustomerToastUtils.toastShow(context).show();
            CustomerToastUtils.tv_toast.setText("选择失败");
        }
    }

    @Override
    public void getSubmitAddress(SubmitAddressResponse wallPaperResponse) {
        if (wallPaperResponse.getCode() == 0) {
            mPresenter.getDakeStep(packageId);
            hava_address_include.setVisibility(View.INVISIBLE);
            no_address_include.setVisibility(View.INVISIBLE);
            mPresenter.getCourseTime(packageId);//查询可选时间
            select_time_include.setVisibility(View.VISIBLE);
            tv_sure.setText("确认提交");
            bigCourse.setAddressStatus(1);
        } else {
            bigCourse.setAddressStatus(0);
            page_layout.setPage(PageState.STATE_SUCCESS);
            CustomerToastUtils.toastShow(context).show();
            CustomerToastUtils.tv_toast.setText("提交失败");
        }

    }

    @Override
    public void getFailed(Throwable e) {
        page_layout.setPage(PageState.STATE_ERROR);
        ToastUtils.showShort(this, e.getMessage() + "");
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        page_layout = findViewById(R.id.page_layout);
        page_layout.setContentView(R.layout.activity_dake_step);
        contract_include = findViewById(R.id.contract_include);
        hava_address_include = findViewById(R.id.hava_address_include);
        no_address_include = findViewById(R.id.no_address_include);
        select_time_include = findViewById(R.id.select_time_include);
        start_study_include = findViewById(R.id.start_study_include);
        tv_address_des = findViewById(R.id.tv_address_des);
        tv_name_phone = findViewById(R.id.tv_name_phone);
        retry = findViewById(R.id.retry);
        MaterialEditText ed_b_name = findViewById(R.id.ed_b_name);
        MaterialEditText ed_b_id = findViewById(R.id.ed_b_id);
        EditText ed_b_phone = findViewById(R.id.ed_b_phone);
        MaterialEditText ed_b_address = findViewById(R.id.ed_b_address);
        TextView tv_yf_name = findViewById(R.id.tv_yf_name);
        TextView tv_date = findViewById(R.id.tv_date);
        tv_sure = findViewById(R.id.tv_sure);
        TextView tv_use_other_address = findViewById(R.id.tv_use_other_address);

        Intent intent = getIntent();
        packageId = intent.getStringExtra("packageId");
        bigCourse = (ShowCourseListResponse.DataBean.BigCourseBean) intent.getSerializableExtra("bigCourse");
        mPresenter.getDakeStep(packageId);

        if (bigCourse.getContractStatus() == 0) {
            mPresenter.getContract(packageId);//查询合同
            contract_include.setVisibility(View.VISIBLE);
            tv_sure.setText("确认提交");
        } else if (bigCourse.getAddressStatus() == 0) {
            mPresenter.getAddress();//查询是否有地址
        } else if (bigCourse.getSelectClassStatus() == 0) {
            mPresenter.getCourseTime(packageId);//查询可选时间
            select_time_include.setVisibility(View.VISIBLE);
        } else {
            tv_sure.setText("去学习");
            start_study_include.setVisibility(View.VISIBLE);
        }

        rv_dake_step = findViewById(R.id.rv_dake_step);
        rv_dake_step2 = findViewById(R.id.rv_dake_step2);
        rv_time = findViewById(R.id.rv_time);
        tv_part_a_name = findViewById(R.id.tv_part_a_name);
        tv_part_a_address = findViewById(R.id.tv_part_a_address);
        tv_start_time = findViewById(R.id.tv_start_time);
        web_contract = findViewById(R.id.web_contract);


        page_layout.setPage(PageState.STATE_LOADING);

        String nowDate = DateUtil.getNowDate();
        tv_date.setText(nowDate);


        ed_b_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                realName = s.toString().trim();
                tv_yf_name.setText(realName);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ed_b_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                certNo = s.toString().trim();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ed_b_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mobile = s.toString().trim();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ed_b_address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                address = s.toString().trim();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        //使用其他地址
        tv_use_other_address.setOnClickListener(v -> {
            Intent intent1 = new Intent(DakeStepActivity.this, OtherAddressActivity.class);
            startActivityForResult(intent1, 0);
        });

        //根据不同状态状态执行对应事件
        tv_sure.setOnClickListener(v -> {
            if (bigCourse.getContractStatus() == 0) {
                if (contractId.length() > 0 && realName.length() > 0 && certNo.length() > 0 && mobile.length() > 0 && address.length() > 0) {
                    if (PhoneNumberUtils.isMobile(mobile)) {
                        mPresenter.getSealContract(contractId, realName, certNo, mobile, address);
                        page_layout.setPage(PageState.STATE_LOADING);

                    } else {
                        CustomerToastUtils.toastShow(context).show();
                        CustomerToastUtils.tv_toast.setText("手机号不正确");
                    }
                } else {
                    CustomerToastUtils.toastShow(context).show();
                    CustomerToastUtils.tv_toast.setText("信息未填写完整");
                }
            } else if (bigCourse.getAddressStatus() == 0) {
                if (!"".equals(shAddress)) {//地址不为空
                    if ("".equals(addressId)) {
                        page_layout.setPage(PageState.STATE_LOADING);
                        mPresenter.submitAddress(data.getId(), packageId);
                    } else {
                        page_layout.setPage(PageState.STATE_LOADING);
                        mPresenter.submitAddress(addressId, packageId);
                    }
                    bigCourse.setSelectClassStatus(0);
                } else {
                    Intent intentAddress = new Intent(DakeStepActivity.this, EditActivity.class);
                    intentAddress.putExtra("packageId", packageId);
                    startActivityForResult(intentAddress, 0);
                }
            } else if (bigCourse.getSelectClassStatus() == 0) {
                classId = CourseTimeAdapter.classId;
                if ("".equals(classId)) {
                    ToastUtils.showShort(DakeStepActivity.this, "请选择时间");
                } else {
                    CoustomerDialog.Builder builder = new CoustomerDialog.Builder(context);
                    builder.setTitle("是否选择该时间上课");
                    builder.setMessage(CourseTimeAdapter.time);
                    builder.setPositiveButton("确定", (dialog, which) -> {
                        dialog.dismiss();
                        page_layout.setPage(PageState.STATE_LOADING);
                        mPresenter.getSelectTime(classId);
                    });

                    builder.setNegativeButton("重选",
                            (dialog, which) -> dialog.dismiss());
                    builder.create().show();

                }
            } else {
                finish();
            }
        });

        //加载页面出现错误监听
        retry.setOnClickListener(v -> {
            page_layout.setPage(PageState.STATE_LOADING);
            mPresenter.getDakeStep(packageId);
            if (bigCourse.getContractStatus() == 0) {
                mPresenter.getContract(packageId);//查询合同
                contract_include.setVisibility(View.VISIBLE);
                tv_sure.setText("确认提交");
            } else if (bigCourse.getAddressStatus() == 0) {
                mPresenter.getAddress();//查询是否有地址
            } else if (bigCourse.getSelectClassStatus() == 0) {
                mPresenter.getCourseTime(packageId);//查询可选时间
                select_time_include.setVisibility(View.VISIBLE);
            }
        });
    }

    //结果处理函数，当从secondActivity中返回时调用此函数
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                shAddress = bundle.getString("address");
                String name = bundle.getString("name");
                String mobile = bundle.getString("phone");
                addressId = bundle.getString("addressId");

                tv_address_des.setText(shAddress);
                String phone = HidePhone.phoneNumber(mobile);
                tv_name_phone.setText(name + "    " + phone);
                no_address_include.setVisibility(View.GONE);
                hava_address_include.setVisibility(View.VISIBLE);
                tv_sure.setText("确认提交");
            }
        } else if (requestCode == 0 && resultCode == 0) {
            shAddress = "";
            mPresenter.getAddress();//查询是否有地址
        }
    }


    @Override
    public int getLayoutId() {
        return R.layout.base_layout;
    }
}