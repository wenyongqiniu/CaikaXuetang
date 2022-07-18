package com.example.caikaxuetang.adapters;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.caikaxuetang.R;
import com.example.caikaxuetang.learningActivitys.EditActivity;
import com.example.caikaxuetang.responses.AddressListResponse;
import com.example.caikaxuetang.utils.HidePhone;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AddressListAdapter extends BaseQuickAdapter<AddressListResponse.DataBean, BaseViewHolder> {

    private int mPosition = -1;
    public static String name = "";
    public static String phone = "";

    public AddressListAdapter(int layoutResId, @Nullable List<AddressListResponse.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, AddressListResponse.DataBean dataBean) {
        TextView tv_address_item = baseViewHolder.getView(R.id.tv_address_item);
        TextView tv_name_phone_item = baseViewHolder.getView(R.id.tv_name_phone_item);
        ImageView iv_is_default = baseViewHolder.getView(R.id.iv_is_default);
        TextView tv_default = baseViewHolder.getView(R.id.tv_default);
        ImageView iv_edit_address = baseViewHolder.getView(R.id.iv_edit_address);


        tv_address_item.setText(dataBean.getAddressDetail());
        String phone = HidePhone.phoneNumber(dataBean.getMobile());
        tv_name_phone_item.setText(dataBean.getRealName() + "      " + phone);
        if (dataBean.getIsDefault() == 0) {
            tv_default.setVisibility(View.GONE);
        } else {
            tv_default.setVisibility(View.VISIBLE);
        }

        if (dataBean.getSelected() == 1 || mPosition == baseViewHolder.getAdapterPosition()) {
            iv_is_default.setBackgroundResource(R.mipmap.select_addreaa);
            iv_is_default.setVisibility(View.VISIBLE);
        } else {
            iv_is_default.setBackground(null);
            iv_is_default.setVisibility(View.GONE);
        }

        iv_edit_address.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), EditActivity.class);
            intent.putExtra("addressData", dataBean);
            getContext().startActivity(intent);
        });

    }

}
