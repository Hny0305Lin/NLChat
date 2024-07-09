/* 受Haohanyh Computer Software Products Open Source LICENSE保护 https://github.com/Hny0305Lin/LICENSE/blob/main/LICENSE */
package com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCore;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.TextView;

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

    private Queue<String> messageQueue; // 消息队列
    private TextView textView; // 显示消息的 TextView
    private List<ChatMessage> chatMessages; // 聊天消息列表
    private ChatAdapter chatAdapter; // 聊天适配器
    private String logPrefix; // 日志前缀，用于区分不同的消息队列
    private RecyclerView recyclerView; // RecyclerView实例

    /**
     * 构造函数
     *
     * @param messageQueue 消息队列
     * @param textView 显示消息的 TextView
     * @param logPrefix 日志前缀
     */
    public ChatMessageQueueUpdater(TextView textView, Queue<String> messageQueue, List<ChatMessage> chatMessages, ChatAdapter chatAdapter, String logPrefix, RecyclerView recyclerView) {
        this.messageQueue = messageQueue;
        this.textView = textView;
        this.chatMessages = chatMessages;
        this.chatAdapter = chatAdapter;
        this.logPrefix = logPrefix;
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
            boolean isDebug = logPrefix.equals("Debug: ");
            boolean isMe = logPrefix.equals("Me: ");
            Log.v(TAG, "isUser：" + isUser);
            Log.v(TAG, "isDebug：" + isDebug);
            Log.v(TAG, "isMe：" + isMe);
            if (isUser) {
                String timestamp = chatTimestamp.getCurrentTimestamp(); // 获取当前时间戳
                chatMessages.add(new ChatMessage(newMessage, timestamp, isUser));
            } else if (isDebug) {
                chatMessages.add(new ChatMessage(newMessage, isDebug));
            } else if (isMe) {
                String timestamp = chatTimestamp.getCurrentTimestamp(); // 获取当前时间戳
                chatMessages.add(new ChatMessage(newMessage, isMe, timestamp));
            }
        }
        chatAdapter.updateMessages(chatMessages, recyclerView); // 更新消息并滚动到底部
        // 旧UI处理
        // textView.setText(allMessages.toString());
        // Log.i(TAG, logPrefix + "消息队列有改动");
        // 在处理完所有消息后，清空 messageQueue，确保不会重复处理相同的消息。
        messageQueue.clear();
    }

}
