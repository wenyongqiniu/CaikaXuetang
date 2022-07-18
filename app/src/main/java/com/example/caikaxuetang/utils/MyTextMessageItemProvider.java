package com.example.caikaxuetang.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.LayoutDirection;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.text.TextUtilsCompat;


import com.example.caikaxuetang.R;

import java.util.List;
import java.util.Locale;

import io.rong.imkit.config.RongConfigCenter;
import io.rong.imkit.conversation.extension.component.emoticon.AndroidEmoji;
import io.rong.imkit.conversation.messgelist.provider.BaseMessageItemProvider;
import io.rong.imkit.model.UiMessage;
import io.rong.imkit.utils.RouteUtils;
import io.rong.imkit.utils.TextViewUtils;
import io.rong.imkit.widget.ILinkClickListener;
import io.rong.imkit.widget.LinkTextViewMovementMethod;
import io.rong.imkit.widget.adapter.IViewProviderListener;
import io.rong.imkit.widget.adapter.ViewHolder;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.message.TextMessage;

//修改会话界面发送和接受文字颜色背景
public class MyTextMessageItemProvider extends BaseMessageItemProvider<TextMessage> {
    private static final String TAG = "TextMessageItemProvider";

    public MyTextMessageItemProvider() {
        mConfig.showReadState = false;
        //隐藏融云默认的气泡
        mConfig.showContentBubble = false;
    }

    @Override
    protected ViewHolder onCreateMessageContentViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rc_item_destruct_text_message_my, null);
        return new ViewHolder(viewGroup.getContext(), view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @SuppressLint("ResourceAsColor")
    @Override
    protected void bindMessageContentViewHolder(ViewHolder holder, ViewHolder viewHolder1, TextMessage message, UiMessage uiMessage, int i, List<UiMessage> list, IViewProviderListener<UiMessage> iViewProviderListener) {
        final TextView view = holder.getView(R.id.rc_text);

        if (TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault()) == LayoutDirection.RTL) {
            view.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
        }
        view.setTag(uiMessage.getMessageId());
        if (uiMessage.getContentSpannable() == null) {
            SpannableStringBuilder spannable = TextViewUtils.getSpannable(message.getContent(), new TextViewUtils.RegularCallBack() {
                @Override
                public void finish(SpannableStringBuilder spannable) {
                    uiMessage.setContentSpannable(spannable);
                    if (view.getTag().equals(uiMessage.getMessageId())) {
                        view.post(new Runnable() {
                            @Override
                            public void run() {
                                view.setText(uiMessage.getContentSpannable());
                            }
                        });
                    }
                }
            });
            uiMessage.setContentSpannable(spannable);
        }
        view.setText(uiMessage.getContentSpannable());
        if (uiMessage.getMessage().getMessageDirection() == Message.MessageDirection.SEND) {
            view.setTextColor(Color.parseColor("#000000"));
            view.setBackgroundResource(R.drawable.shape_right_conversation);
        } else {
            view.setTextColor(Color.parseColor("#000000"));
            view.setBackgroundResource(R.drawable.shape_left_conversation);
        }
        view.setMovementMethod(new LinkTextViewMovementMethod(new ILinkClickListener() {
            @Override
            public boolean onLinkClick(String link) {
                boolean result = false;
                if (RongConfigCenter.conversationConfig().getConversationClickListener() != null) {
                    result = RongConfigCenter.conversationConfig().getConversationClickListener().onMessageLinkClick(holder.getContext(), link, uiMessage.getMessage());
                }
                if (result)
                    return true;
                String str = link.toLowerCase();
                if (str.startsWith("http") || str.startsWith("https")) {
                    RouteUtils.routeToWebActivity(view.getContext(), link);
                    result = true;
                }

                return result;
            }
        }));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewParent parent = view.getParent();
                if (parent instanceof View) {
                    ((View) parent).performClick();
                }
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ViewParent parent = view.getParent();
                if (parent instanceof View) {
                    return ((View) parent).performLongClick();
                }
                return false;
            }
        });
    }

    @Override
    protected boolean onItemClick(ViewHolder viewHolder, TextMessage textMessage, UiMessage uiMessage, int i, List<UiMessage> list, IViewProviderListener<UiMessage> iViewProviderListener) {
        return false;
    }

    @Override
    protected boolean isMessageViewType(MessageContent messageContent) {
        return messageContent instanceof TextMessage && !messageContent.isDestruct();
    }


    @Override
    public Spannable getSummarySpannable(Context context, TextMessage message) {
        if (message != null && !TextUtils.isEmpty(message.getContent())) {
            String content = message.getContent();
            content = content.replace("\n", " ");
            if (content.length() > 100) {
                content = content.substring(0, 100);
            }
            return new SpannableString(AndroidEmoji.ensure(content));
        } else {
            return new SpannableString("");
        }
    }
}
