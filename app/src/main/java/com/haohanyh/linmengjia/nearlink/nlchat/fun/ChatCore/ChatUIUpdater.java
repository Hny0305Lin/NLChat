/* 受Haohanyh Computer Software Products Open Source LICENSE保护 https://github.com/Hny0305Lin/LICENSE/blob/main/LICENSE */
package com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCore;

import android.app.Activity;
import android.content.Context;

import com.haohanyh.linmengjia.nearlink.nlchat.fun.MainAPP;

import java.util.Queue;

public class ChatUIUpdater {
    private Context context;
    private ChatMessageDatabaseManager chatMessageDatabaseManager;
    private ChatTimestamp chatTimestamp;
    private Queue<String> serverMessageQueue;
    private Queue<String> serverDebugQueue;
    private ChatMessageQueueUpdater serverUpdater;
    private ChatMessageQueueUpdater serverDebugUpdater;
    private ChatMessageUUID chatMessageUUID;
    private final int MAX_MESSAGES = 8; // 假设的最大消息数

    public ChatUIUpdater(Context context,
                         ChatMessageDatabaseManager chatMessageDatabaseManager,
                         ChatTimestamp chatTimestamp,
                         Queue<String> serverMessageQueue,
                         Queue<String> serverDebugQueue,
                         ChatMessageQueueUpdater serverUpdater,
                         ChatMessageQueueUpdater serverDebugUpdater) {
        this.context = context;
        this.chatMessageDatabaseManager = chatMessageDatabaseManager;
        this.chatTimestamp = chatTimestamp;
        this.serverMessageQueue = serverMessageQueue;
        this.serverDebugQueue = serverDebugQueue;
        this.serverUpdater = serverUpdater;
        this.serverDebugUpdater = serverDebugUpdater;

        chatMessageUUID = new ChatMessageUUID();
    }

    public void updateUI(String processedString) {
        // 处理完再打印到UI上
        ((Activity) context).runOnUiThread(() -> {
            // 如果需要存储到数据库中
            if (ChatUtilsForSettings.isSqlitemanager()) {
                String timestamp = chatTimestamp.saveCurrentTimestamp();
                if (ChatUtilsForSettings.isShowUartLog() && ChatUtilsForSettings.isSetDebugLog()) {
                    // 如果是debuglog，则分开存储
                    chatMessageDatabaseManager.saveDebugMessageToDatabase(timestamp, processedString, "UserDebug");
                }

                chatMessageDatabaseManager.saveMessageToDatabase(timestamp, processedString, "User");
                chatMessageDatabaseManager.saveMessageAndUUIDToDatabase(timestamp, processedString, "User", chatMessageUUID.getUUID());
            }
            // 如果需要UI滚动消息
            if (ChatUtilsForSettings.isScrollingMessages()) {
                if (ChatUtilsForSettings.isShowUartLog() && ChatUtilsForSettings.isSetDebugLog()) {
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
                if (ChatUtilsForSettings.isShowUartLog() && ChatUtilsForSettings.isSetDebugLog()) {
                    serverDebugQueue.add(processedString);
                    serverDebugUpdater.updateTextView();
                    MainAPP.Vibrate(context);
                } else {
                    serverMessageQueue.add(processedString);
                    serverUpdater.updateTextView();
                    MainAPP.Vibrate(context);
                }
            }
        });
    }

    public final int getMAX_MESSAGES() {
        return MAX_MESSAGES;
    }
}



//TODO 第一版草稿
//                NearLinkUserText.append(processedString);
//                if (NearLinkUserText.length() > 2048) {
//                    String str = NearLinkUserText.getText().toString().substring(NearLinkUserText.getText().length() - 1024, NearLinkUserText.getText().length());
//                    NearLinkUserText.setText("");
//                    NearLinkUserText.append(str);
//                }
//                MainAPP.Vibrate(context);
