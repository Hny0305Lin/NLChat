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
    private Queue<String> clientMessageQueue;
    private Queue<String> serverMessageQueue;
    private Queue<String> serverDebugQueue;
    private ChatMessageQueueUpdater clientUpdater;
    private ChatMessageQueueUpdater serverUpdater;
    private ChatMessageQueueUpdater serverDebugUpdater;
    private ChatMessageUUID chatMessageUUID;
    private final int MAX_MESSAGES = 8; // 假设的最大消息数

    public ChatUIUpdater(Context context,
                         ChatMessageDatabaseManager chatMessageDatabaseManager,
                         ChatTimestamp chatTimestamp,
                         Queue<String> clientMessageQueue,
                         Queue<String> serverMessageQueue,
                         Queue<String> serverDebugQueue,
                         ChatMessageQueueUpdater clientUpdater,
                         ChatMessageQueueUpdater serverUpdater,
                         ChatMessageQueueUpdater serverDebugUpdater) {
        this.context = context;
        this.chatMessageDatabaseManager = chatMessageDatabaseManager;
        this.chatTimestamp = chatTimestamp;
        this.clientMessageQueue = clientMessageQueue;
        this.serverMessageQueue = serverMessageQueue;
        this.serverDebugQueue = serverDebugQueue;
        this.clientUpdater = clientUpdater;
        this.serverUpdater = serverUpdater;
        this.serverDebugUpdater = serverDebugUpdater;

        chatMessageUUID = new ChatMessageUUID();
    }

    public void updateUserUI(String processedString) {
        // 处理完再打印到UI上
        ((Activity) context).runOnUiThread(() -> {
            // 如果需要存储到数据库中
            if (ChatUtilsForSettings.isSqlitemanager()) {
                String timestamp = chatTimestamp.saveCurrentTimestamp();
                if (ChatUtilsForSettings.isShowUartLog() && ChatUtilsForSettings.isSetDebugLog()) {
                    // 如果是debuglog，则分开存储
                    chatMessageDatabaseManager.saveDebugMessageToDatabase(timestamp, processedString, "UserDebug");
                }

                //阅后即焚功能判断是否需要开启
                if (ChatUtilsForSettings.isBurnmessage()) {
                    chatMessageDatabaseManager.saveMessageAndUUIDToDatabase(timestamp, processedString, "User", chatMessageUUID.getUUID());
                } else {
                    chatMessageDatabaseManager.saveMessageToDatabase(timestamp, processedString, "User");
                }
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

    public void updateMeUI(String processedString) {
        ((Activity) context).runOnUiThread(() -> {
            // 如果需要存储到数据库中
            if (ChatUtilsForSettings.isSqlitemanager()) {
                String timestamp = chatTimestamp.saveCurrentTimestamp();

                //阅后即焚功能判断是否需要开启
                if (ChatUtilsForSettings.isBurnmessage()) {
                    chatMessageDatabaseManager.saveMessageAndUUIDToDatabase(timestamp, processedString, "Me", chatMessageUUID.getUUID());
                } else {
                    chatMessageDatabaseManager.saveMessageToDatabase(timestamp, processedString, "Me");
                }
            }
            //如果需要UI滚动消息
            if (ChatUtilsForSettings.isScrollingMessages()) {
                if (clientMessageQueue.size() >= MAX_MESSAGES) {
                    clientMessageQueue.poll(); // 移除最早的消息
                }
                clientMessageQueue.add(processedString);
                clientUpdater.updateTextView();
                MainAPP.Vibrate(context);
            } else {
                clientUpdater.updateTextView();
                MainAPP.Vibrate(context);
            }
        });
    }

    // todo 1.4写一套更好的UI，适应星闪短距特性，开发一些群游戏、群聊、文字呼救等特性功能，目前开发进程从9月结束推进到12月结束
    // todo 1.4功能9月中旬会上线测试版
}
