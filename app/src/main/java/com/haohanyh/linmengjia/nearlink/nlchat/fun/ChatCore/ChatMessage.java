/* 受Haohanyh Computer Software Products Open Source LICENSE保护 https://github.com/Hny0305Lin/LICENSE/blob/main/LICENSE */
package com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCore;

public class ChatMessage {
    private String message;
    private String timestamp;
    private boolean isUser;
    private boolean isDebug;
    private boolean isMe;

    public ChatMessage(String message, String timestamp, boolean isUser) {
        this.message = message;
        this.timestamp = timestamp;
        this.isUser = isUser;
    }

    public ChatMessage(String message, boolean isDebug) {
        this.message = message;
        this.isDebug = isDebug;
    }

    public ChatMessage(String message, boolean isMe, String timestamp) {
        this.message = message;
        this.timestamp = timestamp;
        this.isMe = isMe;
    }

    public String getMessage() {
        return message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public boolean isUser() {
        return isUser;
    }

    public boolean isDebug() { return isDebug; }

    public boolean isMe() { return isMe; }
}