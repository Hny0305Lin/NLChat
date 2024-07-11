/* 受Haohanyh Computer Software Products Open Source LICENSE保护 https://github.com/Hny0305Lin/LICENSE/blob/main/LICENSE */
package com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCore;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;

import com.haohanyh.linmengjia.nearlink.nlchat.fun.R;

import com.haohanyh.linmengjia.nearlink.nlchat.fun.R.string;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //Log需要的TAG
    private static final String TAG = "ChatAdapter & NLChat";

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private static final int VIEW_TYPE_DEBUG_RECEIVED = 3;

    private List<ChatMessage> chatMessages;
    private Context context;

    // 构造函数，初始化消息列表
    public ChatAdapter(Context context, List<ChatMessage> chatMessages) {
        this.context = context;
        this.chatMessages = chatMessages;
    }

    // 根据消息的发送者类型返回不同的视图类型
    @Override
    public int getItemViewType(int position) {
        ChatMessage message = chatMessages.get(position);
        if (message.isUser()) {
            return VIEW_TYPE_MESSAGE_RECEIVED;
        } else if (message.isDebug()) {
            return VIEW_TYPE_DEBUG_RECEIVED;
        } else {
            return VIEW_TYPE_MESSAGE_SENT;
        }
    }

    // 创建不同类型的 ViewHolder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_chat_sent, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_DEBUG_RECEIVED) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_debug, parent, false);
            return new ReceivedDEBUGMessageHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_chat_received, parent, false);
            return new ReceivedMessageHolder(view);
        }
    }

    // 绑定数据到 ViewHolder
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChatMessage message = chatMessages.get(position);
        if (holder.getItemViewType() == VIEW_TYPE_MESSAGE_SENT) {
            ((SentMessageHolder) holder).bind(message);
        } else if (holder.getItemViewType() == VIEW_TYPE_DEBUG_RECEIVED) {
            ((ReceivedDEBUGMessageHolder) holder).bind(message);
        } else {
            ((ReceivedMessageHolder) holder).bind(message);
        }
    }

    // 返回消息列表的大小
    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    // 发送消息的ViewHolder
    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText,timestampText;

        SentMessageHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.text_message_body);
            timestampText = itemView.findViewById(R.id.text_message_time);

            // 设置自定义字体
            ChatUIFontUtils.applyCustomFont(context, messageText);
        }

        void bind(ChatMessage message) {
            messageText.setText(message.getMessage());
            timestampText.setText(message.getTimestamp());
        }
    }

    // 接收DEBUG消息的ViewHolder
    private class ReceivedDEBUGMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText;

        ReceivedDEBUGMessageHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.text_message_body);

            // 设置自定义字体
            ChatUIFontUtils.applyCustomFont(context, messageText, 3);

            messageText.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    String result = messageText.getText().toString().trim();
                    
                    if (result.equals(ChatUtils.getPrefixLogConnected())) {
                        ChatUIAlertDialog.showSerialLog(context, ChatUtils.getPrefixLogConnected(), context.getString(string.prefixLogConnected), "推荐开始聊天", "取消显示", "复制进剪贴板");
                    } else if (result.equals(ChatUtils.getPrefixLogDisconnected())) {
                        ChatUIAlertDialog.showSerialLog(context, ChatUtils.getPrefixLogDisconnected(), context.getString(string.prefixLogDisConnected), "推荐重启软件和星闪板", "取消显示", "复制进剪贴板");
                    } else if (result.equals(ChatUtils.getPrefixLogAcore())) {
                        //ACore暂时不写
                    } else if (result.startsWith(ChatUtils.getPrefixLogConnectStateChanged())) {
                        ChatUIAlertDialog.showSerialLog(context, ChatUtils.getPrefixLogConnectStateChanged(), context.getString(string.prefixLogConnectStateChanged), "推荐检查", "取消显示", "复制进剪贴板");
                    } else if (result.startsWith(ChatUtils.getPrefixLogNearlinkDevicesAddr())) {
                        ChatUIAlertDialog.showSerialLog(context, ChatUtils.getPrefixLogNearlinkDevicesAddr(), context.getString(string.prefixLogNearlinkDevicesAddr), "推荐开始聊天", "取消显示", "复制进剪贴板");
                    } else if (result.startsWith(ChatUtils.getPrefixLogPairComplete())) {
                        ChatUIAlertDialog.showSerialLog(context, ChatUtils.getPrefixLogPairComplete(), context.getString(string.prefixLogPairComplete), "推荐开始聊天", "取消显示", "复制进剪贴板");
                    } else if (result.startsWith(ChatUtils.getPrefixLogSsapsMtuChanged())) {
                        //MTU暂时不写
                    } else if (result.startsWith(ChatUtils.getPrefixLogSleAnnounceEnableCallback())) {
                        //CakkBack暂时不写
                    } else {
                        //分支暂时不写
                    }

                    // 在这里处理长按事件
                    Toast.makeText(context, messageText.getText().toString(), Toast.LENGTH_SHORT).show();

                    return false;
                }
            });
        }

        void bind(ChatMessage message) {
            messageText.setText(message.getMessage());

            switch (message.getLoglevel()) {
                case Log.VERBOSE:
                    messageText.setBackgroundResource(R.drawable.bg_debug_bubble_verbose);
                    messageText.setTextColor(context.getResources().getColor(R.color.white));
                    break;
                case Log.DEBUG:
                    messageText.setBackgroundResource(R.drawable.bg_debug_bubble_debug);
                    messageText.setTextColor(context.getResources().getColor(R.color.black));
                    break;
                case Log.INFO:
                    messageText.setBackgroundResource(R.drawable.bg_debug_bubble_info);
                    messageText.setTextColor(context.getResources().getColor(R.color.black));
                    break;
                case Log.WARN:
                    messageText.setBackgroundResource(R.drawable.bg_debug_bubble_warn);
                    messageText.setTextColor(context.getResources().getColor(R.color.black));
                    break;
                case Log.ERROR:
                    messageText.setBackgroundResource(R.drawable.bg_debug_bubble_error);
                    messageText.setTextColor(context.getResources().getColor(R.color.black));
                    break;
                default:
                    messageText.setBackgroundResource(R.drawable.bg_debug_bubble_error);
                    messageText.setTextColor(context.getResources().getColor(R.color.black));
                    break;
            }
        }
    }

    // 接收消息的ViewHolder
    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText,timestampText;

        ReceivedMessageHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.text_message_body);
            timestampText = itemView.findViewById(R.id.text_message_time);

            // 设置自定义字体
            ChatUIFontUtils.applyCustomFont(context, messageText);
        }

        void bind(ChatMessage message) {
            messageText.setText(message.getMessage());
            timestampText.setText(message.getTimestamp());
        }
    }

    // 更新消息列表并滚动到底部
    @SuppressLint("NotifyDataSetChanged")
    public void updateMessages(List<ChatMessage> newMessages, RecyclerView recyclerView) {
        this.chatMessages = newMessages;
        notifyDataSetChanged();
        recyclerView.scrollToPosition(chatMessages.size() - 1);
    }
}