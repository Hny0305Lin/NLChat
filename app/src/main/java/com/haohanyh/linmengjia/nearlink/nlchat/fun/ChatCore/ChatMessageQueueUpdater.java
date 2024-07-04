/* 受Haohanyh Computer Software Products Open Source LICENSE保护 https://github.com/Hny0305Lin/LICENSE/blob/main/LICENSE */
package com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCore;

import android.util.Log;
import android.widget.TextView;

import java.util.Iterator;
import java.util.Queue;

/**
 * ChatMessageQueueUpdater 类用于更新消息队列并将其内容显示在指定的 TextView 上。
 */
public class ChatMessageQueueUpdater {
    private static final String TAG = "ChatMessageQueueUpdater & NLChat";

    private Queue<String> messageQueue; // 消息队列
    private TextView textView; // 显示消息的 TextView
    private String logPrefix; // 日志前缀，用于区分不同的消息队列

    /**
     * 构造函数
     *
     * @param messageQueue 消息队列
     * @param textView 显示消息的 TextView
     * @param logPrefix 日志前缀
     */
    public ChatMessageQueueUpdater(TextView textView, Queue<String> messageQueue, String logPrefix) {
        this.messageQueue = messageQueue;
        this.textView = textView;
        this.logPrefix = logPrefix;
    }

    /**
     * 更新 TextView 的内容，将消息队列中的所有消息显示在 TextView 上。
     * 同时，移除消息队列中的空消息。
     */
    public void updateTextView() {
        StringBuilder allMessages = new StringBuilder();
        Iterator<String> iterator = messageQueue.iterator();
        while (iterator.hasNext()) {
            String message = iterator.next();
            Log.i(TAG, logPrefix + "当前队列消息内容：" + message); // 打印每个消息到日志
            if (!message.trim().isEmpty()) {
                allMessages.append(message);
            } else {
                Log.i(TAG, logPrefix + "忽略空消息，因此消息队列无改动"); // 打印忽略空消息到日志
                iterator.remove(); // 从队列中移除空消息
                return;
            }
        }
        textView.setText(allMessages.toString());
        Log.i(TAG, logPrefix + "消息队列有改动");
    }
}
