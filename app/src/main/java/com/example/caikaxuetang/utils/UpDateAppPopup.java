package com.example.caikaxuetang.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caikaxuetang.LoginActivity;
import com.example.caikaxuetang.R;
import com.example.caikaxuetang.adapters.MusicListAdapter;
import com.example.caikaxuetang.mediaplayer.OpenMusicActivity;
import com.example.caikaxuetang.responses.SectionDetailResponse;
import com.example.caikaxuetang.responses.VersionEntity;
import com.llw.mvplibrary.BaseApplication;
import com.lxj.xpopup.core.BottomPopupView;

import java.util.ArrayList;

import static com.example.caikaxuetang.LoginActivity.token;
import static com.example.caikaxuetang.MyApplication.getActivityManager;
import static com.example.caikaxuetang.MyApplication.mediaPlayerIngHolder;


public class UpDateAppPopup extends BottomPopupView {
    private VersionEntity.DataBean data;
    private Context context;
    private UpdateService updateService;
    private Handler myHandler = new Handler();

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            int downloadPercent = getDownloadPercent(updateService.downloadId);
            progress.setProgress(downloadPercent);
            myHandler.postDelayed(this, 1000);
        }
    };
    private ProgressBar progress;

    public UpDateAppPopup(@NonNull Context context, VersionEntity.DataBean list) {
        super(context);
        this.context = context;
        this.data = list;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.update_app;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        TextView tv_cancel = findViewById(R.id.tv_cancel);
        TextView tv_ok = findViewById(R.id.tv_ok);
        TextView tv_desc = findViewById(R.id.tv_desc);
        progress = findViewById(R.id.progress);
        tv_desc.setText(data.getUpdateContent());

        if (data.getUpdateType() == 0) {//非强制更新，用户可以选择不更新
            tv_cancel.setText("下次再说");
        } else {//强制更新
            tv_cancel.setText("退出应用");
        }
        updateService = new UpdateService(context);

        tv_ok.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                updateService.download(data.getDownloadLink(), "财咖学堂");
                progress.setVisibility(VISIBLE);
                runnable.run();
            }
        });


        tv_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.getUpdateType() == 0) {//非强制更新，用户可以选择不更新
                    dismiss();
                   /* if (!token.equals("")) {
                        LoginActivity.startMain();
                    }*/
                } else {//强制更新
                    System.exit(0);
                }
            }
        });

    }

    private int getDownloadPercent(long downloadId) {
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        Cursor c = updateService.mDownloadManager.query(query);
        if (c.moveToFirst()) {
            int downloadBytesIdx = c.getColumnIndexOrThrow(
                    DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);
            int totalBytesIdx = c.getColumnIndexOrThrow(
                    DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
            long totalBytes = c.getLong(totalBytesIdx);
            long downloadBytes = c.getLong(downloadBytesIdx);
            return (int) (downloadBytes * 100 / totalBytes);
        }
        return 0;
    }

    //完全可见执行
    @Override
    protected void onShow() {
        super.onShow();
        Log.e("tag", "知乎评论 onShow");
    }

    //完全消失执行
    @Override
    protected void onDismiss() {
        Log.e("tag", "知乎评论 onDismiss");
    }

   /* @Override
    protected int getMaxHeight() {
        return (int) (ScreenUtils.getScreenHeight(getContext()) * .5f);
    }*/
}