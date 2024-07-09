/* 受Haohanyh Computer Software Products Open Source LICENSE保护 https://github.com/Hny0305Lin/LICENSE/blob/main/LICENSE */
package com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCore;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;

import com.haohanyh.linmengjia.nearlink.nlchat.fun.MainAPP;

import java.util.Queue;

public class ChatUIUpdater {
    private Context context;
    private ChatSaveMessageDatabaseManager chatSaveMessageDatabaseManager;
    private ChatTimestamp chatTimestamp;
    private Queue<String> serverMessageQueue;
    private Queue<String> serverDebugQueue;
    private ChatMessageQueueUpdater serverUpdater;
    private ChatMessageQueueUpdater serverDebugUpdater;
    private TextView NearLinkUserText;
    private static final int MAX_MESSAGES = 8; // 假设的最大消息数

    public ChatUIUpdater(Context context,
                         ChatSaveMessageDatabaseManager chatSaveMessageDatabaseManager,
                         ChatTimestamp chatTimestamp,
                         Queue<String> serverMessageQueue,
                         Queue<String> serverDebugQueue,
                         ChatMessageQueueUpdater serverUpdater,
                         ChatMessageQueueUpdater serverDebugUpdater,
                         TextView NearLinkUserText) {
        this.context = context;
        this.chatSaveMessageDatabaseManager = chatSaveMessageDatabaseManager;
        this.chatTimestamp = chatTimestamp;
        this.serverMessageQueue = serverMessageQueue;
        this.serverDebugQueue = serverDebugQueue;
        this.serverUpdater = serverUpdater;
        this.serverDebugUpdater = serverDebugUpdater;
        this.NearLinkUserText = NearLinkUserText;
    }

    public void updateUI(String processedString) {
        // 处理完再打印到UI上
        ((Activity) context).runOnUiThread(() -> {
            // 如果需要存储到数据库中
            if (ChatUtils.isSqlitemanager()) {
                String timestamp = chatTimestamp.saveCurrentTimestamp();
                if (ChatUtils.isShowUartLog()) {
                    // 如果是debuglog，则分开存储
                    chatSaveMessageDatabaseManager.saveDebugMessageToDatabase(timestamp, processedString, "UserDebug");
                } else {
                    chatSaveMessageDatabaseManager.saveMessageToDatabase(timestamp, processedString, "User");
                }
            }
            // 如果需要UI滚动消息
            if (ChatUtils.isScrollingMessages()) {
                if (ChatUtils.isShowUartLog()) {
                    if (serverDebugQueue.size() >= MAX_MESSAGES) {
                        serverDebugQueue.poll();
                    }
                    serverDebugQueue.add(processedString);
                    serverDebugUpdater.updateTextView();
                    MainAPP.Vibrate(context);
                } else {
                    if (serverMessageQueue.size() >= MAX_MESSAGES) {
                        serverMessageQueue.poll();
                    }
                    serverMessageQueue.add(processedString);
                    serverUpdater.updateTextView();
                    MainAPP.Vibrate(context);
                }
            } else {
                NearLinkUserText.append(processedString);
                if (NearLinkUserText.length() > 2048) {
                    String str = NearLinkUserText.getText().toString().substring(NearLinkUserText.getText().length() - 1024, NearLinkUserText.getText().length());
                    NearLinkUserText.setText("");
                    NearLinkUserText.append(str);
                }
                MainAPP.Vibrate(context);
            }
        });
    }
}
