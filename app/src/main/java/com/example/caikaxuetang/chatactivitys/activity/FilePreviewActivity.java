//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.example.caikaxuetang.chatactivitys.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.content.FileProvider;
import io.rong.common.FileUtils;
import io.rong.common.RLog;
import io.rong.imkit.IMCenter;
import io.rong.imkit.R.color;
import io.rong.imkit.R.drawable;
import io.rong.imkit.R.id;
import io.rong.imkit.R.layout;
import io.rong.imkit.R.string;
import io.rong.imkit.activity.RongBaseActivity;
import io.rong.imkit.activity.RongWebviewActivity;
import io.rong.imkit.event.actionevent.BaseMessageEvent;
import io.rong.imkit.event.actionevent.DownloadEvent;
import io.rong.imkit.event.actionevent.MessageEventListener;
import io.rong.imkit.utils.FileTypeUtils;
import io.rong.imkit.utils.PermissionCheckUtil;
import io.rong.imlib.RongCoreClient;
import io.rong.imlib.IRongCallback.IDownloadMediaMessageCallback;
import io.rong.imlib.IRongCoreCallback.ResultCallback;
import io.rong.imlib.IRongCoreEnum.CoreErrorCode;
import io.rong.imlib.RongIMClient.OnRecallMessageListener;
import io.rong.imlib.RongIMClient.OperationCallback;
import io.rong.imlib.RongIMClient.ConnectionStatusListener.ConnectionStatus;
import io.rong.imlib.model.DownloadInfo;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.Message.MessageDirection;
import io.rong.message.FileMessage;
import io.rong.message.MediaMessageContent;
import io.rong.message.RecallNotificationMessage;
import io.rong.message.ReferenceMessage;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FilePreviewActivity extends RongBaseActivity implements OnClickListener {
    private static final String TAG = "FilePreviewActivity";
    public static final int NOT_DOWNLOAD = 0;
    public static final int DOWNLOADED = 1;
    public static final int DOWNLOADING = 2;
    public static final int DELETED = 3;
    public static final int DOWNLOAD_ERROR = 4;
    public static final int DOWNLOAD_CANCEL = 5;
    public static final int DOWNLOAD_SUCCESS = 6;
    public static final int DOWNLOAD_PAUSE = 7;
    public static final int REQUEST_CODE_PERMISSION = 104;
    private static final String TXT_FILE = ".txt";
    private static final String APK_FILE = ".apk";
    private ImageView mFileTypeImage;
    private TextView mFileNameView;
    private TextView mFileSizeView;
    private Button mFileButton;
    protected FilePreviewActivity.FileDownloadInfo mFileDownloadInfo;
    protected FileMessage mFileMessage;
    protected Message mMessage;
    private int mProgress;
    private String mFileName;
    private long mFileSize;
    private List<Toast> mToasts;
    private FrameLayout contentContainer;
    private DownloadInfo info = null;
    private long downloadedFileLength;
    private MessageEventListener mEventListener = new BaseMessageEvent() {
        public void onDownloadMessage(DownloadEvent event) {
            FilePreviewActivity.this.updateDownloadStatus(event);
        }
    };
    private ResultCallback<DownloadInfo> callback = new FilePreviewActivity.DownloadInfoCallBack(this);
    private OnRecallMessageListener mRecallListener = new OnRecallMessageListener() {
        public boolean onMessageRecalled(Message message, RecallNotificationMessage recallNotificationMessage) {
            if (FilePreviewActivity.this.mMessage == null) {
                return false;
            } else {
                int messageId = FilePreviewActivity.this.mMessage.getMessageId();
                if (messageId == message.getMessageId()) {
                    (new Builder(FilePreviewActivity.this, 5)).setMessage(FilePreviewActivity.this.getString(string.rc_recall_success)).setPositiveButton(FilePreviewActivity.this.getString(string.rc_dialog_ok), new android.content.DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            FilePreviewActivity.this.finish();
                        }
                    }).setCancelable(false).show();
                }

                return false;
            }
        }
    };
    private boolean getInfoNow = false;

    public FilePreviewActivity() {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(layout.rc_ac_file_download);
        this.initStatusBar(color.app_color_white);
        this.initView();
        this.initData();
        this.initListener();
        this.getFileMessageStatus();
    }

    private void initListener() {
        IMCenter.getInstance().addMessageEventListener(this.mEventListener);
        IMCenter.getInstance().addOnRecallMessageListener(this.mRecallListener);
    }

    private void getFileMessageStatus() {
        Uri fileUrl = this.mFileMessage.getFileUrl();
        Uri localUri = this.mFileMessage.getLocalPath();
        boolean isLocalPathExist = false;
        if (localUri != null && FileUtils.isFileExistsWithUri(this, localUri)) {
            isLocalPathExist = true;
        }

        if (!isLocalPathExist && fileUrl != null && !TextUtils.isEmpty(fileUrl.toString())) {
            String url = fileUrl.toString();
            long init = System.currentTimeMillis();
            this.mFileButton.setEnabled(false);
            this.mFileButton.setText(string.rc_picture_please);
            this.mFileButton.setBackgroundResource(drawable.rc_ac_btn_file_download_open_uncheck);
            RLog.d("test", "init time" + init + ",url" + url);
            this.getFileDownloadInfoInSubThread();
        } else {
            this.setViewStatus();
            this.getFileDownloadInfo();
        }

    }

    public void setContentView(int resId) {
        this.contentContainer.removeAllViews();
        View view = LayoutInflater.from(this).inflate(resId, (ViewGroup)null);
        this.contentContainer.addView(view);
    }

    private void initData() {
        Intent intent = this.getIntent();
        if (intent != null) {
            this.mFileDownloadInfo = new FilePreviewActivity.FileDownloadInfo();
            this.mFileMessage = (FileMessage)this.getIntent().getParcelableExtra("FileMessage");
            this.mMessage = (Message)this.getIntent().getParcelableExtra("Message");
            this.mProgress = this.getIntent().getIntExtra("Progress", 0);
            this.mToasts = new ArrayList();
            this.mFileName = this.mFileMessage.getName();
            this.mFileTypeImage.setImageResource(FileTypeUtils.fileTypeImageId(this, this.mFileName));
            this.mFileNameView.setText(this.mFileName);
            this.mFileSize = this.mFileMessage.getSize();
            this.mFileSizeView.setText(FileTypeUtils.formatFileSize(this.mFileSize));
            this.mFileButton.setOnClickListener(this);
        }
    }

    private void initView() {
        this.contentContainer = (FrameLayout)this.findViewById(id.rc_ac_ll_content_container);
        View view = LayoutInflater.from(this).inflate(layout.rc_ac_file_preview_content, (ViewGroup)null);
        this.contentContainer.addView(view);
        this.mFileTypeImage = (ImageView)this.findViewById(id.rc_ac_iv_file_type_image);
        this.mFileNameView = (TextView)this.findViewById(id.rc_ac_tv_file_name);
        this.mFileSizeView = (TextView)this.findViewById(id.rc_ac_tv_file_size);
        this.mFileButton = (Button)this.findViewById(id.rc_ac_btn_download_button);
        this.mTitleBar.setTitle(string.rc_ac_file_download_preview);
        this.mTitleBar.setRightVisible(false);
    }

    @SuppressLint("WrongConstant")
    private void setViewStatus() {
        if (this.mMessage.getMessageDirection() == MessageDirection.RECEIVE) {
            if (this.mProgress == 0) {
                this.mFileButton.setVisibility(0);
            } else if (this.mProgress == 100) {
                this.mFileButton.setVisibility(0);
            } else {
                this.mFileButton.setVisibility(8);
            }
        }

    }

    @SuppressLint("WrongConstant")
    private void setViewStatusForResumeTransfer() {
        this.mFileButton.setVisibility(0);
    }

    @SuppressLint("WrongConstant")
    public void onClick(View v) {
        if (v == this.mFileButton) {
            switch(this.mFileDownloadInfo.state) {
            case 0:
            case 3:
            case 4:
            case 5:
                this.startToDownload();
                break;
            case 1:
            case 6:
                this.openFile(this.mFileName, this.mFileMessage.getLocalPath());
                break;
            case 2:
                this.mFileDownloadInfo.state = 7;
                IMCenter.getInstance().pauseDownloadMediaMessage(this.mMessage, (OperationCallback)null);
                this.downloadedFileLength = (long)((double)this.mFileMessage.getSize() * ((double)this.mFileDownloadInfo.progress / 100.0D) + 0.5D);
                this.mFileSizeView.setText(this.getString(string.rc_ac_file_download_progress_pause) + "(" + FileTypeUtils.formatFileSize(this.downloadedFileLength) + "/" + FileTypeUtils.formatFileSize(this.mFileSize) + ")");
                this.mFileButton.setText(this.getResources().getString(string.rc_ac_file_preview_download_resume));
                break;
            case 7:
                if (IMCenter.getInstance().getCurrentConnectionStatus() == ConnectionStatus.NETWORK_UNAVAILABLE) {
                    Toast.makeText(this, this.getString(string.rc_notice_network_unavailable), 0).show();
                    return;
                }

                this.mFileDownloadInfo.state = 2;
                this.downloadFile();
                if (this.mFileDownloadInfo.state != 4 && this.mFileDownloadInfo.state != 5) {
                    this.mFileButton.setText(this.getResources().getString(string.rc_cancel));
                }
            }
        }

    }

    @SuppressLint("WrongConstant")
    private void startToDownload() {
        if (this.mMessage.getContent() instanceof MediaMessageContent) {
            this.resetMediaMessageLocalPath();
            if (IMCenter.getInstance().getCurrentConnectionStatus() != ConnectionStatus.CONNECTED) {
                Toast.makeText(this, this.getString(string.rc_notice_network_unavailable), 0).show();
            } else {
                MediaMessageContent mediaMessage = (MediaMessageContent)((MediaMessageContent)this.mMessage.getContent());
                if (mediaMessage == null || mediaMessage.getMediaUrl() != null && !TextUtils.isEmpty(mediaMessage.getMediaUrl().toString())) {
                    if (this.mFileDownloadInfo.state == 0 || this.mFileDownloadInfo.state == 4 || this.mFileDownloadInfo.state == 3 || this.mFileDownloadInfo.state == 5) {
                        this.downloadFile();
                    }

                } else {
                    Toast.makeText(this, this.getString(string.rc_ac_file_url_error), 0).show();
                    this.finish();
                }
            }
        } else {
            this.refreshDownloadState();
        }
    }

    protected void resetMediaMessageLocalPath() {
        FileMessage fileMessage = null;
        if (this.mMessage.getContent() instanceof FileMessage) {
            fileMessage = (FileMessage)this.mMessage.getContent();
        } else if (this.mMessage.getContent() instanceof ReferenceMessage) {
            ReferenceMessage referenceMessage = (ReferenceMessage)this.mMessage.getContent();
            fileMessage = (FileMessage)referenceMessage.getReferenceContent();
        }

        if (fileMessage != null && fileMessage.getLocalPath() != null && !TextUtils.isEmpty(fileMessage.getLocalPath().toString())) {
            fileMessage.setLocalPath((Uri)null);
            this.mFileMessage.setLocalPath((Uri)null);
            IMCenter.getInstance().refreshMessage(this.mMessage);
        }

    }

    @SuppressLint("WrongConstant")
    public void openFile(String fileName, Uri fileSavePath) {
        try {
            if (!this.openInsidePreview(fileName, fileSavePath)) {
                Intent intent = FileTypeUtils.getOpenFileIntent(this, fileName, fileSavePath);
                if (intent != null) {
                    intent.addFlags(1);
                    this.startActivity(intent);
                } else {
                    Toast.makeText(this, this.getString(string.rc_ac_file_preview_can_not_open_file), 0).show();
                }
            }
        } catch (Exception var4) {
            RLog.e("FilePreviewActivity", "openFile", var4);
            Toast.makeText(this, this.getString(string.rc_ac_file_preview_can_not_open_file), 0).show();
        }

    }

    protected boolean openInsidePreview(String fileName, Uri uri) {
        String fileSavePath = uri.toString();
        if (fileSavePath.endsWith(".txt")) {
            this.processTxtFile(fileName, uri);
            return true;
        } else if (fileSavePath.endsWith(".apk")) {
            this.processApkFile(uri);
            return true;
        } else {
            return false;
        }
    }

    private void processTxtFile(String fileName, Uri uri) {
        Intent webIntent = new Intent(this, RongWebviewActivity.class);
        webIntent.setPackage(this.getPackageName());
        if (FileUtils.uriStartWithContent(uri)) {
            webIntent.putExtra("url", uri);
        } else {
            String path = uri.toString();
            if (FileUtils.uriStartWithFile(uri)) {
                path = path.substring(7);
            }

            if (VERSION.SDK_INT >= 24) {
                Uri txtUri = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + this.getResources().getString(string.rc_authorities_fileprovider), new File(path));
                webIntent.putExtra("url", txtUri.toString());
            } else {
                webIntent.putExtra("url", "file://" + path);
            }
        }

        webIntent.putExtra("title", fileName);
        this.startActivity(webIntent);
    }

    @SuppressLint("WrongConstant")
    private void processApkFile(Uri uri) {
        if (FileUtils.uriStartWithContent(uri)) {
            Intent intent = (new Intent("android.intent.action.VIEW")).setDataAndType(uri, "application/vnd.android.package-archive");
            intent.addFlags(1);
            this.startActivity(intent);
        } else {
            String path = uri.toString();
            if (FileUtils.uriStartWithFile(uri)) {
                path = path.substring(7);
            }

            File file = new File(path);
            if (!file.exists()) {
                Toast.makeText(this, this.getString(string.rc_file_not_exist), 0).show();
                return;
            }

            if (VERSION.SDK_INT >= 24) {
                Uri downloaded_apk;
                try {
                    downloaded_apk = FileProvider.getUriForFile(this, this.getPackageName() + this.getString(string.rc_authorities_fileprovider), file);
                } catch (Exception var6) {
                    throw new RuntimeException("Please check IMKit Manifest FileProvider config.");
                }

                Intent intent = (new Intent("android.intent.action.VIEW")).setDataAndType(downloaded_apk, "application/vnd.android.package-archive");
                intent.addFlags(1);
                this.startActivity(intent);
            } else {
                Intent installIntent = new Intent("android.intent.action.VIEW");
                installIntent.setFlags(268435456);
                installIntent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                this.startActivity(installIntent);
            }
        }

    }

    @TargetApi(23)
    private void downloadFile() {
        String[] permission = new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"};
        if (!PermissionCheckUtil.checkPermissions(this, permission)) {
            PermissionCheckUtil.requestPermissions(this, permission, 104);
        } else {
            this.mFileDownloadInfo.state = 2;
            this.mFileButton.setText(this.getResources().getString(string.rc_cancel));
            this.downloadedFileLength = (long)((double)this.mFileMessage.getSize() * ((double)this.mFileDownloadInfo.progress / 100.0D) + 0.5D);
            this.mFileSizeView.setText(this.getString(string.rc_ac_file_download_progress_tv) + "(" + FileTypeUtils.formatFileSize(this.downloadedFileLength) + "/" + FileTypeUtils.formatFileSize(this.mFileSize) + ")");
            IMCenter.getInstance().downloadMediaMessage(this.mMessage, (IDownloadMediaMessageCallback)null);
        }
    }

    private void getFileDownloadInfo() {
        if (this.mFileMessage.getLocalPath() != null) {
            if (FileUtils.isFileExistsWithUri(this, this.mFileMessage.getLocalPath())) {
                this.mFileDownloadInfo.state = 1;
            } else {
                this.mFileDownloadInfo.state = 3;
            }
        } else if (this.mProgress > 0 && this.mProgress < 100) {
            this.mFileDownloadInfo.state = 2;
            this.mFileDownloadInfo.progress = this.mProgress;
        } else {
            this.mFileDownloadInfo.state = 0;
        }

        this.refreshDownloadState();
    }

    private void getFileDownloadInfoForResumeTransfer() {
        if (this.mFileMessage != null) {
            Uri path = this.mFileMessage.getLocalPath();
            if (path != null) {
                boolean exists = FileUtils.isFileExistsWithUri(this, path);
                if (exists) {
                    this.mFileDownloadInfo.state = 1;
                } else {
                    this.mFileDownloadInfo.state = 3;
                }
            } else if (this.info != null) {
                if (this.info.isDownLoading()) {
                    this.mFileDownloadInfo.state = 2;
                } else {
                    this.mFileDownloadInfo.state = 7;
                }
            } else {
                this.mFileDownloadInfo.state = 0;
            }
        } else {
            this.mFileDownloadInfo.state = 0;
        }

        this.refreshDownloadState();
    }
    @SuppressLint("WrongConstant")
    protected void refreshDownloadState() {
        long downloadedFileLength;
        switch(this.mFileDownloadInfo.state) {
        case 0:
            this.mFileButton.setText(this.getString(string.rc_ac_file_preview_begin_download));
            break;
        case 1:
            this.mFileButton.setText(this.getString(string.rc_ac_file_download_open_file_btn));
            break;
        case 2:
            this.downloadedFileLength = (long)((double)this.mFileMessage.getSize() * ((double)this.mFileDownloadInfo.progress / 100.0D) + 0.5D);
            this.mFileSizeView.setText(this.getString(string.rc_ac_file_download_progress_tv) + "(" + FileTypeUtils.formatFileSize(this.downloadedFileLength) + "/" + FileTypeUtils.formatFileSize(this.mFileSize) + ")");
            this.mFileButton.setText(this.getString(string.rc_cancel));
            break;
        case 3:
            this.mFileSizeView.setText(FileTypeUtils.formatFileSize(this.mFileSize));
            this.mFileButton.setText(this.getString(string.rc_ac_file_preview_begin_download));
            break;
        case 4:
            if (this.info != null) {
                this.mFileDownloadInfo.progress = (int)(100L * this.info.currentFileLength() / this.info.getLength());
            }

            downloadedFileLength = (long)((double)this.mFileMessage.getSize() * ((double)this.mFileDownloadInfo.progress / 100.0D) + 0.5D);
            this.mFileSizeView.setText(this.getString(string.rc_ac_file_download_progress_pause) + "(" + FileTypeUtils.formatFileSize(downloadedFileLength) + "/" + FileTypeUtils.formatFileSize(this.mFileSize) + ")");
            this.mFileButton.setText(this.getString(string.rc_ac_file_preview_download_resume));
             Toast toast = Toast.makeText(this, this.getString(string.rc_ac_file_preview_download_error), 0);
            if (this.mFileDownloadInfo.state != 5) {
                toast.show();
            }

            this.mToasts.add(toast);
            break;
        case 5:
            this.mFileButton.setVisibility(0);
            this.mFileButton.setText(this.getString(string.rc_ac_file_preview_begin_download));
            this.mFileSizeView.setText(FileTypeUtils.formatFileSize(this.mFileSize));
            Toast.makeText(this, this.getString(string.rc_ac_file_preview_download_cancel), 0).show();
            break;
        case 6:
            this.mFileButton.setVisibility(0);
            this.mFileButton.setText(this.getString(string.rc_ac_file_download_open_file_btn));
            this.mFileSizeView.setText(FileTypeUtils.formatFileSize(this.mFileSize));
            Toast.makeText(this, this.getString(string.rc_ac_file_preview_downloaded) + this.mFileDownloadInfo.path, 0).show();
            break;
        case 7:
            downloadedFileLength = (long)((double)this.mFileMessage.getSize() * ((double)this.mFileDownloadInfo.progress / 100.0D) + 0.5D);
            this.mFileSizeView.setText(this.getString(string.rc_ac_file_download_progress_pause) + "(" + FileTypeUtils.formatFileSize(downloadedFileLength) + "/" + FileTypeUtils.formatFileSize(this.mFileSize) + ")");
            this.mFileButton.setText(this.getString(string.rc_ac_file_preview_download_resume));
        }

    }

    protected void onResume() {
        super.onResume();
    }

    protected void onRestart() {
        super.onRestart();
        this.getFileDownloadInfoInSubThread();
    }

    private void getFileDownloadInfoInSubThread() {
        this.getInfoNow = true;
        this.getFileInfo();
    }

    protected void onStop() {
        super.onStop();
    }

    protected void onDestroy() {
        try {
            Iterator var1 = this.mToasts.iterator();

            while(var1.hasNext()) {
                Toast toast = (Toast)var1.next();
                toast.cancel();
            }
        } catch (Exception var3) {
            RLog.e("FilePreviewActivity", "onDestroy", var3);
        }

        IMCenter.getInstance().removeMessageEventListener(this.mEventListener);
        IMCenter.getInstance().removeOnRecallMessageListener(this.mRecallListener);
        super.onDestroy();
    }

    public void updateDownloadStatus(DownloadEvent event) {
        if (this.mMessage.getMessageId() == event.getMessage().getMessageId()) {
            switch(event.getEvent()) {
            case 0:
                if (this.mFileDownloadInfo.state == 5) {
                    break;
                }

                if (event.getMessage() != null && event.getMessage().getContent() != null) {
                    if (event.getMessage().getContent() instanceof FileMessage) {
                        this.mFileMessage = (FileMessage)event.getMessage().getContent();
                        this.mFileMessage.setLocalPath(Uri.parse(this.mFileMessage.getLocalPath().toString()));
                        this.mFileDownloadInfo.path = this.mFileMessage.getLocalPath().toString();
                    } else {
                        ReferenceMessage referenceMessage = (ReferenceMessage)event.getMessage().getContent();
                        this.mFileMessage.setLocalPath(Uri.parse(referenceMessage.getLocalPath().toString()));
                        this.mFileDownloadInfo.path = referenceMessage.getLocalPath().toString();
                    }

                    this.mFileDownloadInfo.state = 6;
                    this.refreshDownloadState();
                    break;
                }

                return;
            case 1:
                if (this.info == null && !this.getInfoNow) {
                    this.getFileDownloadInfoInSubThread();
                }

                if (this.mFileDownloadInfo.state != 5 && this.mFileDownloadInfo.state != 7) {
                    this.mFileDownloadInfo.state = 2;
                    this.mFileDownloadInfo.progress = event.getProgress();
                    this.refreshDownloadState();
                }
                break;
            case 2:
                if (this.mFileDownloadInfo.state != 5) {
                    this.mFileDownloadInfo.state = 4;
                    this.refreshDownloadState();
                }
                break;
            case 3:
                this.mFileDownloadInfo.state = 5;
                this.refreshDownloadState();
            }
        }

    }

    public Message getMessage() {
        return this.mMessage;
    }

    private void getFileInfo() {
        RLog.d("getDownloadInfo", "getFileInfo start");
        RongCoreClient.getInstance().getDownloadInfo(String.valueOf(this.mMessage.getMessageId()), this.callback);
    }

    public class FileDownloadInfo {
        public int state;
        public int progress;
        public String path;

        public FileDownloadInfo() {
        }
    }

    private static class DownloadInfoCallBack extends ResultCallback<DownloadInfo> {
        WeakReference<FilePreviewActivity> weakActivity;

        public DownloadInfoCallBack(FilePreviewActivity activity) {
            this.weakActivity = new WeakReference(activity);
        }

        public void onSuccess(DownloadInfo downloadInfo) {
            if (this.weakActivity.get() != null) {
                FilePreviewActivity activity = (FilePreviewActivity)this.weakActivity.get();
                activity.info = downloadInfo;
                if (downloadInfo != null) {
                    activity.mFileDownloadInfo.progress = downloadInfo.currentProgress();
                }

                activity.getInfoNow = false;
                activity.setViewStatusForResumeTransfer();
                activity.getFileDownloadInfoForResumeTransfer();
                activity.mFileButton.setBackgroundResource(drawable.rc_ac_btn_file_download_open_button);
                activity.mFileButton.setEnabled(true);
                RLog.d("getDownloadInfo", "getFileInfo finish");
            }
        }

        public void onError(CoreErrorCode e) {
        }
    }
}
