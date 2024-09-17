/* 受Haohanyh Computer Software Products Open Source LICENSE保护 https://github.com/Hny0305Lin/LICENSE/blob/main/LICENSE */
package com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCore;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCore.Emoji.EmojiTimerManager;
import com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatService.MessageBurnNotificationService;
import com.haohanyh.linmengjia.nearlink.nlchat.fun.R;
import com.haohanyh.linmengjia.nearlink.nlchat.fun.R.string;
import com.haohanyh.linmengjia.nearlink.nlchat.fun.SQLite.SQLiteDataBaseAPP;
import com.haohanyh.linmengjia.nearlink.nlchat.fun.String.StringUtils;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //Log需要的TAG
    private static final String TAG = "ChatAdapter & NLChat";

    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;                                    //正常的消息，收
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;                                        //正常的消息，发

    private static final int VIEW_TYPE_MESSAGE_RECEIVED_BURN = 4;                               //正常的消息，收，阅后即焚
    private static final int VIEW_TYPE_MESSAGE_SENT_BURN = 3;                                   //正常的消息，发，阅后即焚

    private static final int VIEW_TYPE_DEBUG_RECEIVED = 5;                                      //Debug消息

    private static final int VIEW_TYPE_MESSAGE_RECEIVED_LATEST = -2;                            //数据库消息记录，收
    private static final int VIEW_TYPE_MESSAGE_SENT_LATEST = -1;                                //数据库消息记录，发

    private static final int VIEW_TYPE_HAOHANYH = 255;                                          //彩蛋Debug

    private List<ChatUtilsForMessage> chatUtilsForMessages;
    private Context context;

    private Set<String> notifiedMessages = new HashSet<>();

    // 构造函数，初始化消息列表
    public ChatAdapter(Context context, List<ChatUtilsForMessage> chatUtilsForMessages) {
        this.context = context;
        this.chatUtilsForMessages = chatUtilsForMessages;
    }

    // todo 1.4写一套更好的UI，适应星闪短距特性，开发一些群游戏、群聊、文字呼救等特性功能，目前开发进程从9月结束推进到12月结束
    // todo 1.4功能9月中旬会上线测试版

    // 根据消息的发送者类型返回不同的视图类型
    @Override
    public int getItemViewType(int position) {
        ChatUtilsForMessage message = chatUtilsForMessages.get(position);

        if (message.isUser()) {
            return VIEW_TYPE_MESSAGE_RECEIVED;
        } else if (message.isMe()) {
            return VIEW_TYPE_MESSAGE_SENT;
        } else if (message.isUserBurn()) {
            return VIEW_TYPE_MESSAGE_RECEIVED_BURN;
        } else if (message.isMeBurn()) {
            return VIEW_TYPE_MESSAGE_SENT_BURN;
        } else if (message.isDebug()) {
            return VIEW_TYPE_DEBUG_RECEIVED;
        } else if (message.isSQLiteUser()) {
            return VIEW_TYPE_MESSAGE_RECEIVED_LATEST;
        } else if (message.isSQLiteMe()) {
            return VIEW_TYPE_MESSAGE_SENT_LATEST;
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
            case VIEW_TYPE_MESSAGE_RECEIVED_BURN:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_chat_received_burn, parent, false);
                return new ReceivedMessageBurnHolder(view);
            case VIEW_TYPE_MESSAGE_SENT_BURN:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_chat_sent_burn, parent, false);
                return new SentMessageBurnHolder(view);
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
            default:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_haohanyh, parent, false);
                return new HaohanyhMessageHolder(view);
        }
    }

    // 绑定数据到 ViewHolder
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChatUtilsForMessage message = chatUtilsForMessages.get(position);
        int viewType = holder.getItemViewType();
        Log.d(TAG, "绑定数据到 ViewHolder: viewType = " + viewType + ", holder class = " + holder.getClass().getName());

        if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            ((ReceivedMessageHolder) holder).bind(message);
        } else if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            ((SentMessageHolder) holder).bind(message);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED_BURN) {
            ((ReceivedMessageBurnHolder) holder).bind(message);
        } else if (viewType == VIEW_TYPE_MESSAGE_SENT_BURN) {
            ((SentMessageBurnHolder) holder).bind(message);
        } else if (viewType == VIEW_TYPE_DEBUG_RECEIVED) {
            ((ReceivedDEBUGMessageHolder) holder).bind(message);
        } else if (viewType == VIEW_TYPE_MESSAGE_SENT_LATEST) {
            ((SentLatestMessageHolder) holder).bind(message);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED_LATEST) {
            ((ReceivedLatestMessageHolder) holder).bind(message);
        } else if (viewType == VIEW_TYPE_HAOHANYH) {
            ((HaohanyhMessageHolder) holder).bind(message);
        }
    }

    // 返回消息列表的大小
    @Override
    public int getItemCount() {
        return chatUtilsForMessages.size();
    }

    // todo 发送消息和接收消息每一方，如果消息有特殊情况比如enter，uuid会查询不到，目前这个bug打算后续修复。todo 暂时不影响功能
    // todo 1.4尝试修复

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

        void bind(ChatUtilsForMessage message) {
            messageText.setText(StringUtils.needProcess().msgSubEnter(message));
            timestampText.setText(message.getTimestamp());
        }
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

        void bind(ChatUtilsForMessage message) {
            messageText.setText(StringUtils.needProcess().msgSubEnter(message));
            timestampText.setText(message.getTimestamp());
        }
    }




    // todo 小熊派群员:阅后即焚可以做个动画，提示密信，点开弹窗，关闭不见 todo 目前暂时无法实现
    // todo 1.4 尝试解决

    // 接收消息的ViewHolder（阅后即焚）
    private class ReceivedMessageBurnHolder extends RecyclerView.ViewHolder {
        TextView messageText,timestampText,emojiText;

        ReceivedMessageBurnHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.text_message_body);
            timestampText = itemView.findViewById(R.id.text_message_time);
            emojiText = itemView.findViewById(R.id.text_message_emoji);

            // 设置自定义字体
            ChatUIFontUtils.applyCustomFont(context, messageText);
        }

        void bind(ChatUtilsForMessage message) {
            messageText.setText(StringUtils.needProcess().msgSubEnter(message));
            timestampText.setText(message.getTimestamp());

            // 使用EmojiTimerManager来管理和启动计时器
            EmojiTimerManager emojiTimerManager = new EmojiTimerManager();
            emojiTimerManager.startTimer(emojiText, ChatUtilsForSettings.getBurntimer()); // 2分钟倒计时

            // 使用同一个Handler来处理UI更新和消息删除
            Handler messageHandler = new Handler();
            ReceivedMessageBurnHandler burnHandler = new ReceivedMessageBurnHandler(this, message);

            // 提示用户收到密信
            if (!notifiedMessages.contains(message.getMessageId())) {
                MessageBurnNotificationService.notifyUserToCheckApp(context, message.getMessage());
                notifiedMessages.add(message.getMessageId());
            }

            // 设置消息2分钟后从数据源中删除并更新UI
            messageHandler.postDelayed(burnHandler, ChatUtilsForSettings.getBurntimer()); // 延迟时间为120000毫秒，即2分钟

            // 新增点击显示3秒钟文字（后续做出
            messageText.setOnClickListener(v -> {
                Toast.makeText(context, message.getMessage(), Toast.LENGTH_SHORT).show();
            });

            // 新增长按可以在2分钟内看完后手动删除密聊
            messageText.setOnLongClickListener(v -> {
                if (!ChatUtilsForSettings.isBurnalertdialog()) {
                    Toast.makeText(context, "已阅后即焚", Toast.LENGTH_SHORT).show();
                    burnHandler.run();
                } else {
                    Toast.makeText(context, messageText.getText().toString(), Toast.LENGTH_SHORT).show();

                    String messageb = messageText.getText().toString();
                    String sender = "User";                                                   // 这里需要替换为实际的发送者

                    String timestamp = ChatTimestamp.getLastTimestamp();                    // 获取缓存的时间戳

                    SQLiteDataBaseAPP dbApp = SQLiteDataBaseAPP.SQLiteData();
                    String uuid = dbApp.getUUIDForMessage(messageb, sender, timestamp);

                    String messageToShow = "\n消息内容: " + messageb;
                    if (uuid != null) {
                        messageToShow += "\nUUID: " + uuid;
                    } else {
                        messageToShow += "\nUUID: UUID not found.";
                    }

                    String burn = "\n阅后即焚:" + ChatUtilsForSettings.isBurnmessage();

                    ChatUIAlertDialog.showMessageLog(context,
                            "消息信息(Dev,仅开发者使用)",
                            messageToShow + "\n" + burn,
                            "推荐开发者使用", "取消显示", "复制进剪贴板");
                }
                return true;
            });
        }
    }

    private class ReceivedMessageBurnHandler implements Runnable {
        private final WeakReference<ReceivedMessageBurnHolder> holderWeakReference;
        private final ChatUtilsForMessage message;

        ReceivedMessageBurnHandler(ReceivedMessageBurnHolder holder, ChatUtilsForMessage message) {
            this.holderWeakReference = new WeakReference<>(holder);
            this.message = message;
        }

        @Override
        public void run() {
            ReceivedMessageBurnHolder holder = holderWeakReference.get();
            if (holder != null && message != null) {
                //Log
                Log.d(TAG, "ReceivedMessageBurnHandler is running");
                // 检查当前消息是否仍在数据源中
                int currentIndex = chatUtilsForMessages.indexOf(message);
                if (currentIndex != -1) {
                    // 从数据源中删除消息
                    chatUtilsForMessages.remove(currentIndex);
                    // 通知RecyclerView某项已被删除
                    notifyItemRemoved(currentIndex);
                    // 通知RecyclerView更新位置，防止位置错乱
                    notifyItemRangeChanged(currentIndex, chatUtilsForMessages.size());
                }
            }
        }
    }

    // 发送消息的ViewHolder（阅后即焚）
    private class SentMessageBurnHolder extends RecyclerView.ViewHolder {
        TextView messageText, timestampText, emojiText;

        SentMessageBurnHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.text_message_body);
            timestampText = itemView.findViewById(R.id.text_message_time);
            emojiText = itemView.findViewById(R.id.text_message_emoji);

            // 设置自定义字体
            ChatUIFontUtils.applyCustomFont(context, messageText);

        }

        void bind(ChatUtilsForMessage message) {
            messageText.setText(StringUtils.needProcess().msgSubEnter(message));
            timestampText.setText(message.getTimestamp());

            // 使用EmojiTimerManager来管理和启动计时器
            EmojiTimerManager emojiTimerManager = new EmojiTimerManager();
            emojiTimerManager.startTimer(emojiText, ChatUtilsForSettings.getBurntimer()); // 2分钟倒计时

            // 使用同一个Handler来处理UI更新和消息删除
            Handler messageHandler = new Handler();
            SentMessageBurnHandler burnHandler = new SentMessageBurnHandler(this, message);

            // 设置消息2分钟后从数据源中删除并更新UI
            messageHandler.postDelayed(burnHandler, ChatUtilsForSettings.getBurntimer()); // 延迟时间为120000毫秒，即2分钟

            // 新增点击显示3秒钟文字（后续做出
            messageText.setOnClickListener(v -> {
                Toast.makeText(context, message.getMessage(), Toast.LENGTH_SHORT).show();
            });

            // 新增长按可以在2分钟内看完后手动删除密聊
            messageText.setOnLongClickListener(v -> {
                if (!ChatUtilsForSettings.isBurnalertdialog()) {
                    Toast.makeText(context, "已阅后即焚", Toast.LENGTH_SHORT).show();
                    burnHandler.run();
                } else {
                    Toast.makeText(context, messageText.getText().toString(), Toast.LENGTH_SHORT).show();

                    String messageb = messageText.getText().toString();
                    String sender = "Me";                                                   // 这里需要替换为实际的发送者

                    String timestamp = ChatTimestamp.getLastTimestamp();                    // 获取缓存的时间戳

                    SQLiteDataBaseAPP dbApp = SQLiteDataBaseAPP.SQLiteData();
                    String uuid = dbApp.getUUIDForMessage(messageb, sender, timestamp);

                    String messageToShow = "\n消息内容: " + messageb;
                    if (uuid != null) {
                        messageToShow += "\nUUID: " + uuid;
                    } else {
                        messageToShow += "\nUUID: UUID not found.";
                    }

                    String burn = "\n阅后即焚:" + ChatUtilsForSettings.isBurnmessage();

                    ChatUIAlertDialog.showMessageLog(context,
                            "消息信息(Dev,仅开发者使用)",
                            messageToShow + "\n" + burn,
                            "推荐开发者使用", "取消显示", "复制进剪贴板");

                }
                return true;
            });
        }
    }

    private class SentMessageBurnHandler implements Runnable {
        private final WeakReference<SentMessageBurnHolder> holderWeakReference;
        private final ChatUtilsForMessage message;

        SentMessageBurnHandler(SentMessageBurnHolder holder, ChatUtilsForMessage message) {
            this.holderWeakReference = new WeakReference<>(holder);
            this.message = message;
        }

        @Override
        public void run() {
            SentMessageBurnHolder holder = holderWeakReference.get();
            if (holder != null && message != null) {
                //Log
                Log.d(TAG, "SentMessageBurnHandler is running");
                // 检查当前消息是否仍在数据源中
                int currentIndex = chatUtilsForMessages.indexOf(message);
                if (currentIndex != -1) {
                    // 从数据源中删除消息
                    chatUtilsForMessages.remove(currentIndex);
                    // 通知RecyclerView某项已被删除
                    notifyItemRemoved(currentIndex);
                    // 通知RecyclerView更新位置，防止位置错乱
                    notifyItemRangeChanged(currentIndex, chatUtilsForMessages.size());
                }
            }
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

                    if (result.equals(ChatUtilsForSettings.getPrefixLogConnected())) {
                        ChatUIAlertDialog.showSerialLog(context,
                                ChatUtilsForSettings.getPrefixLogConnected(),
                                context.getString(string.prefixLogConnected),
                                "推荐开始聊天", "取消显示", "复制进剪贴板");
                    } else if (result.equals(ChatUtilsForSettings.getPrefixLogDisconnected())) {
                        ChatUIAlertDialog.showSerialLog(context,
                                ChatUtilsForSettings.getPrefixLogDisconnected(),
                                context.getString(string.prefixLogDisConnected),
                                "推荐重启软件和星闪板", "取消显示", "复制进剪贴板");
                    } else if (result.equals(ChatUtilsForSettings.getPrefixLogAcore())) {
                        //ACore暂时不写
                    } else if (result.startsWith(ChatUtilsForSettings.getPrefixLogConnectStateChanged())) {
                        ChatUIAlertDialog.showSerialLog(context,
                                ChatUtilsForSettings.getPrefixLogConnectStateChanged(),
                                context.getString(string.prefixLogConnectStateChanged),
                                "推荐检查", "取消显示", "复制进剪贴板");
                    } else if (result.startsWith(ChatUtilsForSettings.getPrefixLogConnectStateChangedBearpi3863())) {
                        ChatUIAlertDialog.showSerialLog(context,
                                ChatUtilsForSettings.getPrefixLogConnectStateChangedBearpi3863(),
                                context.getString(string.prefixLogConnectStateChanged),
                                "推荐检查", "取消显示", "复制进剪贴板");
                    } else if (result.startsWith(ChatUtilsForSettings.getPrefixLogNearlinkDevicesAddr())) {
                        ChatUIAlertDialog.showSerialLog(context,
                                ChatUtilsForSettings.getPrefixLogNearlinkDevicesAddr(),
                                context.getString(string.prefixLogNearlinkDevicesAddr),
                                "推荐开始聊天", "取消显示", "复制进剪贴板");
                    } else if (result.startsWith(ChatUtilsForSettings.getPrefixLogNearlinkDevicesAddrBearpi3863())) {
                        ChatUIAlertDialog.showSerialLog(context,
                                ChatUtilsForSettings.getPrefixLogNearlinkDevicesAddrBearpi3863(),
                                context.getString(string.prefixLogNearlinkDevicesAddr),
                                "推荐开始聊天", "取消显示", "复制进剪贴板");
                    }else if (result.startsWith(ChatUtilsForSettings.getPrefixLogPairComplete())) {
                        ChatUIAlertDialog.showSerialLog(context,
                                ChatUtilsForSettings.getPrefixLogPairComplete(),
                                context.getString(string.prefixLogPairComplete),
                                "推荐开始聊天", "取消显示", "复制进剪贴板");
                    } else if (result.startsWith(ChatUtilsForSettings.getPrefixLogPairCompleteBearpi3863())) {
                        ChatUIAlertDialog.showSerialLog(context,
                                ChatUtilsForSettings.getPrefixLogPairCompleteBearpi3863(),
                                context.getString(string.prefixLogPairComplete),
                                "推荐开始聊天", "取消显示", "复制进剪贴板");
                    } else if (result.startsWith(ChatUtilsForSettings.getPrefixLogSsapsMtuChanged())) {
                        //服务板MTU暂时不写
                    } else if (result.startsWith(ChatUtilsForSettings.getPrefixLogSsapsMtuChangedBearpi3863())) {
                        //服务板MTU暂时不写
                    } else if (result.startsWith(ChatUtilsForSettings.getPrefixLogSleAnnounceEnableCallback())) {
                        //服务板CallBack暂时不写
                    } else if (result.startsWith(ChatUtilsForSettings.getPrefixLogSleAnnounceEnableCallbackBearpi3863())) {
                        //服务板CallBack暂时不写
                    } else if (result.startsWith(ChatUtilsForSettings.getPrefixLogClientConnectStateChanged())) {
                        ChatUIAlertDialog.showSerialLog(context,
                                ChatUtilsForSettings.getPrefixLogClientConnectStateChanged(),
                                context.getString(string.prefixLogClientConnectStateChanged),
                                "推荐检查", "取消显示", "复制进剪贴板");
                    } else if (result.startsWith(ChatUtilsForSettings.getPrefixLogClientConnectStateChangedBearpi3863())) {
                        ChatUIAlertDialog.showSerialLog(context,
                                ChatUtilsForSettings.getPrefixLogClientConnectStateChangedBearpi3863(),
                                context.getString(string.prefixLogClientConnectStateChanged),
                                "推荐检查", "取消显示", "复制进剪贴板");
                    } else if (result.startsWith(ChatUtilsForSettings.getPrefixLogClientNearlinkDevicesAddr())) {
                        ChatUIAlertDialog.showSerialLog(context,
                                ChatUtilsForSettings.getPrefixLogClientNearlinkDevicesAddr(),
                                context.getString(string.prefixLogClientNearlinkDevicesAddr),
                                "推荐开始聊天", "取消显示", "复制进剪贴板");
                    } else if (result.startsWith(ChatUtilsForSettings.getPrefixLogClientNearlinkDevicesAddrBearpi3863())) {
                        ChatUIAlertDialog.showSerialLog(context,
                                ChatUtilsForSettings.getPrefixLogClientNearlinkDevicesAddrBearpi3863(),
                                context.getString(string.prefixLogClientNearlinkDevicesAddr),
                                "推荐开始聊天", "取消显示", "复制进剪贴板");
                    } else if (result.startsWith(ChatUtilsForSettings.getPrefixLogClientPairComplete())) {
                        ChatUIAlertDialog.showSerialLog(context,
                                ChatUtilsForSettings.getPrefixLogClientPairComplete(),
                                context.getString(string.prefixLogClientPairComplete),
                                "推荐开始聊天", "取消显示", "复制进剪贴板");
                    } else if (result.startsWith(ChatUtilsForSettings.getPrefixLogClientPairCompleteBearpi3863())) {
                        ChatUIAlertDialog.showSerialLog(context,
                                ChatUtilsForSettings.getPrefixLogClientPairCompleteBearpi3863(),
                                context.getString(string.prefixLogClientPairComplete),
                                "推荐开始聊天", "取消显示", "复制进剪贴板");
                    } else if (result.startsWith(ChatUtilsForSettings.getPrefixLogClientMtu())) {
                        //客户板MTU暂时不写
                    } else if (result.startsWith(ChatUtilsForSettings.getPrefixLogClientMtuBearpi3863())) {
                        //客户板MTU暂时不写
                    } else if (result.startsWith(ChatUtilsForSettings.getPrefixLogClientSleAnnounceEnableCallback())) {
                        //客户板CallBack暂时不写
                    } else if (result.startsWith(ChatUtilsForSettings.getPrefixLogClientSleAnnounceEnableCallbackBearpi3863())) {
                        //客户板CallBack暂时不写
                    }

                    // 在这里处理长按事件
                    Toast.makeText(context, messageText.getText().toString(), Toast.LENGTH_SHORT).show();

                    return false;
                }
            });
        }

        void bind(ChatUtilsForMessage message) {
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

        void bind(ChatUtilsForMessage message) {
            messageText.setText(StringUtils.needProcess().msgSubEnter(message));
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

        void bind(ChatUtilsForMessage message) {
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

        void bind(ChatUtilsForMessage message) {

        }
    }

    // 更新消息列表并滚动到底部
    @SuppressLint("NotifyDataSetChanged")
    public void updateMessages(List<ChatUtilsForMessage> newMessages, RecyclerView recyclerView) {
        this.chatUtilsForMessages = newMessages;
        //notifyDataSetChanged();
        recyclerView.scrollToPosition(chatUtilsForMessages.size() - 1);
    }
}