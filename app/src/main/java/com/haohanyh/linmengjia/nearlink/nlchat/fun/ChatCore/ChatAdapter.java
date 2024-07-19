/* 受Haohanyh Computer Software Products Open Source LICENSE保护 https://github.com/Hny0305Lin/LICENSE/blob/main/LICENSE */
package com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCore;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.haohanyh.linmengjia.nearlink.nlchat.fun.R;
import com.haohanyh.linmengjia.nearlink.nlchat.fun.R.string;

import java.util.List;
import java.util.Random;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //Log需要的TAG
    private static final String TAG = "ChatAdapter & NLChat";

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;                                        //正常的消息，发
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;                                    //正常的消息，收

    private static final int VIEW_TYPE_DEBUG_RECEIVED = 3;                                      //Debug消息

    private static final int VIEW_TYPE_MESSAGE_SENT_LATEST = -1;                                //数据库消息记录，发
    private static final int VIEW_TYPE_MESSAGE_RECEIVED_LATEST = -2;                            //数据库消息记录，收

    private static final int VIEW_TYPE_DEBUG_LATEST = 0;                                        //历史Debug消息

    private static final int VIEW_TYPE_HAOHANYH = 255;                                          //彩蛋Debug

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
        } else if (message.isMe()) {
            return VIEW_TYPE_MESSAGE_SENT;
        } else if (message.isDebug()) {
            return VIEW_TYPE_DEBUG_RECEIVED;
        } else if (message.isSQLiteUser()) {
            return VIEW_TYPE_MESSAGE_RECEIVED_LATEST;
        } else if (message.isSQLiteMe()) {
            return VIEW_TYPE_MESSAGE_SENT_LATEST;
        } else if (message.isSQLiteDebug()) {
            return VIEW_TYPE_DEBUG_LATEST;
        } else {
            return VIEW_TYPE_HAOHANYH;
        }
    }

    // 创建不同类型的 ViewHolder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case VIEW_TYPE_MESSAGE_RECEIVED:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_chat_received, parent, false);
                return new ReceivedMessageHolder(view);
            case VIEW_TYPE_MESSAGE_SENT:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_chat_sent, parent, false);
                return new SentMessageHolder(view);
            case VIEW_TYPE_DEBUG_RECEIVED:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_debug, parent, false);
                return new ReceivedDEBUGMessageHolder(view);
            case VIEW_TYPE_MESSAGE_RECEIVED_LATEST:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_chat_received_history, parent, false);
                return new ReceivedLatestMessageHolder(view);
            case VIEW_TYPE_MESSAGE_SENT_LATEST:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_chat_sent_history, parent, false);
                return new SentLatestMessageHolder(view);
            case VIEW_TYPE_DEBUG_LATEST:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_debug, parent, false);
                return new ReceivedDEBUGMessageHolder(view);
            default:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_haohanyh, parent, false);
                return new HaohanyhMessageHolder(view);
        }
    }

    // 绑定数据到 ViewHolder
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChatMessage message = chatMessages.get(position);
        int viewType = holder.getItemViewType();

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            ((SentMessageHolder) holder).bind(message);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            ((ReceivedMessageHolder) holder).bind(message);
        } else if (viewType == VIEW_TYPE_DEBUG_RECEIVED) {
            ((ReceivedDEBUGMessageHolder) holder).bind(message);
        } else if (viewType == VIEW_TYPE_MESSAGE_SENT_LATEST) {
            ((SentLatestMessageHolder) holder).bind(message);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED_LATEST) {
            ((ReceivedLatestMessageHolder) holder).bind(message);
        } else if (viewType == VIEW_TYPE_DEBUG_LATEST) {
            ((ReceivedDEBUGMessageHolder) holder).bind(message);
        } else if (viewType == VIEW_TYPE_HAOHANYH) {
            ((HaohanyhMessageHolder) holder).bind(message);
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
                        ChatUIAlertDialog.showSerialLog(context,
                                ChatUtils.getPrefixLogConnected(),
                                context.getString(string.prefixLogConnected),
                                "推荐开始聊天", "取消显示", "复制进剪贴板");
                    } else if (result.equals(ChatUtils.getPrefixLogDisconnected())) {
                        ChatUIAlertDialog.showSerialLog(context,
                                ChatUtils.getPrefixLogDisconnected(),
                                context.getString(string.prefixLogDisConnected),
                                "推荐重启软件和星闪板", "取消显示", "复制进剪贴板");
                    } else if (result.equals(ChatUtils.getPrefixLogAcore())) {
                        //ACore暂时不写
                    } else if (result.startsWith(ChatUtils.getPrefixLogConnectStateChanged())) {
                        ChatUIAlertDialog.showSerialLog(context,
                                ChatUtils.getPrefixLogConnectStateChanged(),
                                context.getString(string.prefixLogConnectStateChanged),
                                "推荐检查", "取消显示", "复制进剪贴板");
                    } else if (result.startsWith(ChatUtils.getPrefixLogNearlinkDevicesAddr())) {
                        ChatUIAlertDialog.showSerialLog(context,
                                ChatUtils.getPrefixLogNearlinkDevicesAddr(),
                                context.getString(string.prefixLogNearlinkDevicesAddr),
                                "推荐开始聊天", "取消显示", "复制进剪贴板");
                    } else if (result.startsWith(ChatUtils.getPrefixLogPairComplete())) {
                        ChatUIAlertDialog.showSerialLog(context,
                                ChatUtils.getPrefixLogPairComplete(),
                                context.getString(string.prefixLogPairComplete),
                                "推荐开始聊天", "取消显示", "复制进剪贴板");
                    } else if (result.startsWith(ChatUtils.getPrefixLogSsapsMtuChanged())) {
                        //服务板MTU暂时不写
                    } else if (result.startsWith(ChatUtils.getPrefixLogSleAnnounceEnableCallback())) {
                        //服务板CallBack暂时不写
                    } else if (result.startsWith(ChatUtils.getPrefixLogClientConnectStateChanged())) {
                        ChatUIAlertDialog.showSerialLog(context,
                                ChatUtils.getPrefixLogClientConnectStateChanged(),
                                context.getString(string.prefixLogClientConnectStateChanged),
                                "推荐检查", "取消显示", "复制进剪贴板");
                    } else if (result.startsWith(ChatUtils.getPrefixLogClientNearlinkDevicesAddr())) {
                        ChatUIAlertDialog.showSerialLog(context,
                                ChatUtils.getPrefixLogClientNearlinkDevicesAddr(),
                                context.getString(string.prefixLogClientNearlinkDevicesAddr),
                                "推荐开始聊天", "取消显示", "复制进剪贴板");
                    } else if (result.startsWith(ChatUtils.getPrefixLogClientPairComplete())) {
                        ChatUIAlertDialog.showSerialLog(context,
                                ChatUtils.getPrefixLogClientPairComplete(),
                                context.getString(string.prefixLogClientPairComplete),
                                "推荐开始聊天", "取消显示", "复制进剪贴板");
                    } else if (result.startsWith(ChatUtils.getPrefixLogClientMtu())) {
                        //客户板MTU暂时不写
                    } else if (result.startsWith(ChatUtils.getPrefixLogClientSleAnnounceEnableCallback())) {
                        //客户板CallBack暂时不写
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

    // 存储于数据库的历史发送消息的ViewHolder
    private class SentLatestMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText,timestampText;

        SentLatestMessageHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.text_message_body);
            timestampText = itemView.findViewById(R.id.text_message_time);

            // 设置自定义字体
            ChatUIFontUtils.applyCustomFont(context, messageText, 2);

            // 设置长按监听器
            timestampText.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(context, "这个历史消息是:" + messageText.getText().toString() + "\n" + timestampText.getText().toString(), Toast.LENGTH_LONG).show();
                    return true; // 返回true表示事件已处理
                }
            });

            // 设置随机颜色
            setRandomTextColor(timestampText);
        }

        void bind(ChatMessage message) {
            messageText.setText(message.getMessage());
            timestampText.setText(message.getTimestamp());
        }

        private void setRandomTextColor(TextView textView) {
            Random random = new Random();
            int red = random.nextInt(156) + 100; // 100-255
            int green = random.nextInt(156) + 100; // 100-255
            int blue = random.nextInt(156) + 100; // 100-255
            int color = Color.rgb(red, green, blue);
            textView.setTextColor(color);
        }
    }

    // 存储于数据库的历史接收消息的ViewHolder
    private class ReceivedLatestMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText,timestampText;

        ReceivedLatestMessageHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.text_message_body);
            timestampText = itemView.findViewById(R.id.text_message_time);

            // 设置自定义字体
            ChatUIFontUtils.applyCustomFont(context, messageText, 2);

            // 设置长按监听器
            timestampText.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(context, "这个历史消息是:" + messageText.getText().toString() + "\n" + timestampText.getText().toString(), Toast.LENGTH_LONG).show();
                    return true; // 返回true表示事件已处理
                }
            });

            // 设置随机颜色
            setRandomTextColor(timestampText);
        }

        void bind(ChatMessage message) {
            messageText.setText(message.getMessage());
            timestampText.setText(message.getTimestamp());
        }

        private void setRandomTextColor(TextView textView) {
            Random random = new Random();
            int red = random.nextInt(156) + 100; // 100-255
            int green = random.nextInt(156) + 100; // 100-255
            int blue = random.nextInt(156) + 100; // 100-255
            int color = Color.rgb(red, green, blue);
            textView.setTextColor(color);
        }
    }

    // 浩瀚银河预留的ViewHolder
    private class HaohanyhMessageHolder extends RecyclerView.ViewHolder {

        HaohanyhMessageHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(ChatMessage message) {

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