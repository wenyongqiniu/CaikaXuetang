package com.example.caikaxuetang.fragments.learningcenterfragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.caikaxuetang.R;
import com.example.caikaxuetang.adapters.CourseTimeAdapter;
import com.example.caikaxuetang.adapters.NoteListAdapter;
import com.example.caikaxuetang.contract.FindNoteContract;
import com.example.caikaxuetang.learningActivitys.VideoActivity;
import com.example.caikaxuetang.responses.AddAddressResponse;
import com.example.caikaxuetang.responses.AddressListResponse;
import com.example.caikaxuetang.responses.CommitSuccessResponse;
import com.example.caikaxuetang.responses.FindNoteResponse;
import com.example.caikaxuetang.utils.CoustomerDialog;
import com.example.caikaxuetang.utils.CustomerToastUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.ljb.page.PageState;
import com.llw.mvplibrary.mvp.MvpFragment;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.core.BottomPopupView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.picture.tools.ScreenUtils;


public class DakeNotesFragment extends MvpFragment<FindNoteContract.FindNotePresenter> implements FindNoteContract.FindNoteView {


    private String courseId;
    private RecyclerView rv_note_list;
    public static List<FindNoteResponse.DataBean> data;
    public static NoteListAdapter noteListAdapter;
    public static ArrayList<FindNoteResponse.DataBean> list;
    public static BasePopupView noteShow;
    public static TextInputEditText ed_note;
    public static ImageView iv_close;
    public static TextView tv_keep_note;
    public static TextView tv_now_lesson;
    public String sectionId = "";
    public static String edContent = "";
    private String noteId = "";

    public DakeNotesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        assert getArguments() != null;
        courseId = getArguments().getString("courseId");
        list.clear();
        mPresenter.getFindNote(courseId);
    }

    @Override
    public void getFindNote(FindNoteResponse wallPaperResponse) {
        if (wallPaperResponse.getCode() == 0) {
            data = wallPaperResponse.getData();
            list.addAll(data);
            noteListAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void getKeepNote(CommitSuccessResponse wallPaperResponse) {
        if (wallPaperResponse.getCode() == 0) {
            CustomerToastUtils.toastShow(context).show();
            CustomerToastUtils.tv_toast.setText("修改成功");
            CustomerToastUtils.iv_warm.setBackgroundResource(R.drawable.ic_baseline_check_circle_outline_24);
            if (noteShow != null) {
                noteShow.dismiss();
            }
            list.clear();
            mPresenter.getFindNote(courseId);
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(String event) {//事件在videoactivity
        list.clear();
        mPresenter.getFindNote(courseId);
    }


    @Override
    public void getFailed(Throwable e) {

    }

    @Override
    protected FindNoteContract.FindNotePresenter createPresent() {
        return new FindNoteContract.FindNotePresenter();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        rv_note_list = rootView.findViewById(R.id.rv_note_list);

        list = new ArrayList<>();
        noteListAdapter = new NoteListAdapter(R.layout.item_notelist, list);
        rv_note_list.setLayoutManager(new LinearLayoutManager(context));
        rv_note_list.setAdapter(noteListAdapter);

        noteListAdapter.addChildClickViewIds(R.id.tv_delete_note, R.id.rl_note);

        noteListAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {

                noteId = list.get(position).getId();
                if (view.getId() == R.id.tv_delete_note) {
                    //弹窗确认是否删除
                    CoustomerDialog.Builder builder = new CoustomerDialog.Builder(context);
                    builder.setTitle("是否删除该笔记？");
                    builder.setMessage("");
                    builder.setPositiveButton("删除", (dialog, which) -> {
                        dialog.dismiss();
                        mPresenter.deleteNote(noteId);
                    });
                    builder.setNegativeButton("取消",
                            (dialog, which) -> dialog.dismiss());
                    builder.create().show();
                } else {
                    //修改笔记
                    showNotePop(position);
                }

            }
        });
    }

    //记笔记弹框
    public void showNotePop(int position) {
        TakeNotePopup takeNotePopup = new TakeNotePopup(context, position);
        noteShow = new XPopup.Builder(context)
                .maxHeight(ScreenUtils.getScreenHeight(context) * 12 / 13)
                .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                .enableDrag(true)
                .asCustom(takeNotePopup)
                .show();
    }

    public class TakeNotePopup extends BottomPopupView {

        private int position;

        public TakeNotePopup(@NonNull Context context, int position) {
            super(context);
            this.position = position;
        }

        @Override
        protected int getImplLayoutId() {
            return R.layout.popup_take_note_layout;
        }

        @Override
        protected void onCreate() {
            super.onCreate();
            ed_note = findViewById(R.id.ed_note);
            tv_keep_note = findViewById(R.id.tv_keep_note);
            tv_now_lesson = findViewById(R.id.tv_now_lesson);
            iv_close = findViewById(R.id.iv_close);
            iv_close.setOnClickListener(v -> dismiss());
            ed_note.setText(list.get(position).getContent());
            edContent = ed_note.getText().toString();
            ed_note.setSelection(list.get(position).getContent().length());
            ed_note.setFocusableInTouchMode(true);
            ed_note.requestFocus();

            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ed_note.getLayoutParams();

            layoutParams.height = ScreenUtils.getScreenHeight(context) / 3;
            ed_note.setLayoutParams(layoutParams);

            InputMethodManager inputMethodManager = (InputMethodManager) ed_note.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(ed_note, 0);
            tv_now_lesson.setText("当前课程：" + list.get(position).getSectionName());
            ed_note.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    edContent = charSequence.toString().trim();
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            //保存笔记
            tv_keep_note.setOnClickListener(view -> {
                if ("".equals(edContent)) {
                    CustomerToastUtils.toastShow(context).show();
                    CustomerToastUtils.tv_toast.setText("笔记为空");
                    CustomerToastUtils.iv_warm.setBackgroundResource(R.mipmap.warm);
                } else {
                    noteId = list.get(position).getId();
                    sectionId = list.get(position).getSectionId();
                    mPresenter.keepNote(sectionId, edContent, noteId);
                }

            });

        }

        //完全可见执行
        @Override
        protected void onShow() {
            super.onShow();
        }

        //完全消失执行
        @Override
        protected void onDismiss() {
            Log.e("tag", "知乎评论 onDismiss");
        }


    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_dake_notes;
    }
}