/* 受Haohanyh Computer Software Products Open Source LICENSE保护 https://github.com/Hny0305Lin/LICENSE/blob/main/LICENSE */
package com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCore;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

/**
 * ChatMessageQueueUpdater 类用于更新消息队列并将其内容显示在指定的 TextView 上。
 */
public class ChatMessageQueueUpdater {
    private static final String TAG = "ChatMessageQueueUpdater & NLChat";

    private ChatTimestamp chatTimestamp = new ChatTimestamp(); // 聊天时间戳

    private ChatMessageUUID chatMessageUUID = new ChatMessageUUID(); // UUID 生成器

    private Queue<String> messageQueue; // 消息队列
    private List<ChatUtilsForMessage> chatUtilsForMessages; // 聊天消息列表
    private ChatAdapter chatAdapter; // 聊天适配器
    private String logPrefix; // 日志前缀，用于区分不同的消息队列
    private Queue<String> timesHistory; // 历史消息时间戳队列，用于打印历史消息时间
    private int loglevel;
    private RecyclerView recyclerView; // RecyclerView实例

    /**
     * 构造函数，适用于User和Me消息
     * @param messageQueue 消息队列
     * @param logPrefix 日志前缀
     */
    public ChatMessageQueueUpdater(Queue<String> messageQueue, List<ChatUtilsForMessage> chatUtilsForMessages, ChatAdapter chatAdapter, String logPrefix, RecyclerView recyclerView) {
        this.messageQueue = messageQueue;
        this.chatUtilsForMessages = chatUtilsForMessages;
        this.chatAdapter = chatAdapter;
        this.logPrefix = logPrefix;
        this.recyclerView = recyclerView;
    }

    /**
     * 构造函数，适用于Debug日志消息
     * @param messageQueue 消息队列
     * @param loglevel 日志等级
     */
    public ChatMessageQueueUpdater(Queue<String> messageQueue, List<ChatUtilsForMessage> chatUtilsForMessages, ChatAdapter chatAdapter, String logPrefix, RecyclerView recyclerView, int loglevel) {
        this.messageQueue = messageQueue;
        this.chatUtilsForMessages = chatUtilsForMessages;
        this.chatAdapter = chatAdapter;
        this.logPrefix = logPrefix;
        this.recyclerView = recyclerView;
        this.loglevel = loglevel;
    }

    /**
     * 构造函数，适用于User和Me历史消息
     * @param messageQueue 消息队列
     * @param logPrefix 日志前缀
     */
    public ChatMessageQueueUpdater(Queue<String> messageQueue, List<ChatUtilsForMessage> chatUtilsForMessages, ChatAdapter chatAdapter, String logPrefix, Queue<String> timesHistory, RecyclerView recyclerView) {
        this.messageQueue = messageQueue;
        this.chatUtilsForMessages = chatUtilsForMessages;
        this.chatAdapter = chatAdapter;
        this.logPrefix = logPrefix;
        this.timesHistory = timesHistory;
        this.recyclerView = recyclerView;
    }

    /**
     * 更新 TextView 的内容，将消息队列中的所有消息显示在 TextView 上。
     * 同时，移除消息队列中的空消息。
     */
    @SuppressLint("NotifyDataSetChanged")
    public void updateTextView() {
        StringBuilder allMessages = new StringBuilder();
        List<String> newMessages = new ArrayList<>(); // 使用临时列表，服务NewUI

        Iterator<String> iterator = messageQueue.iterator();
        while (iterator.hasNext()) {
            String message = iterator.next();
            Log.i(TAG, logPrefix + "当前队列消息内容：" + message); // 打印每个消息到日志
            if (!message.trim().isEmpty()) {
                allMessages.append(message);
                newMessages.add(message);
            } else {
                Log.i(TAG, logPrefix + "忽略空消息，因此消息队列无改动"); // 打印忽略空消息到日志
                iterator.remove(); // 从队列中移除空消息
                return;
            }
        }

        // 新UI处理，将新消息添加到 chatMessages 列表中
        for (String newMessage : newMessages) {
            boolean isUser = logPrefix.equals("User: ");
            boolean isMe = logPrefix.equals("Me: ");
            boolean isDebug = logPrefix.equals("Debug: ");
            Log.v(TAG, "isUser：" + isUser);
            Log.v(TAG, "isMe：" + isMe);
            Log.v(TAG, "isDebug：" + isDebug);

            boolean isHistoryUser = logPrefix.equals("UserHistory: ");
            boolean isHistoryMe = logPrefix.equals("MeHistory: ");
            boolean isHistoryDebug = logPrefix.equals("DebugHistory: ");
            Log.v(TAG, "isUser, but History：" + isHistoryUser);
            Log.v(TAG, "isMe, but History：" + isHistoryMe);
            Log.v(TAG, "isDebug, but History：" + isHistoryDebug);


            String timestamp = chatTimestamp.getCurrentTimestamp(); // 获取当前时间戳，展示在ChatUI上
            String historyTime = timesHistory != null && !timesHistory.isEmpty() ? timesHistory.poll() : ""; // 获取并移除历史消息记录时间戳队列中的第一个时间数据
            String uuid = chatMessageUUID.generateUUID(); // 生成UUID

            if (isUser) {
                chatUtilsForMessages.add(new ChatUtilsForMessage(newMessage, timestamp, isUser, uuid));
            } else if (isMe) {
                chatUtilsForMessages.add(new ChatUtilsForMessage(newMessage, isMe, timestamp, uuid));
            } else if (isDebug) {
                chatUtilsForMessages.add(new ChatUtilsForMessage(newMessage, isDebug, loglevel));
            } else if (isHistoryUser) {
                chatUtilsForMessages.add(new ChatUtilsForMessage(newMessage, "History User,\n " + historyTime, true, 1));
            } else if (isHistoryMe) {
                chatUtilsForMessages.add(new ChatUtilsForMessage(newMessage, "History Me,\n " + historyTime, true, 2));
            } else if (isHistoryDebug) {
                chatUtilsForMessages.add(new ChatUtilsForMessage(newMessage, "", true, 3));
            }
        }

        // 使用Handler在主线程中更新RecyclerView
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                chatAdapter.updateMessages(chatUtilsForMessages, recyclerView); // 更新消息并滚动到底部
            }
        });

        // Log.i(TAG, logPrefix + "消息队列有改动");

        // 在处理完所有消息后，清空 messageQueue，确保不会重复处理相同的消息。
        messageQueue.clear();
    }

}
