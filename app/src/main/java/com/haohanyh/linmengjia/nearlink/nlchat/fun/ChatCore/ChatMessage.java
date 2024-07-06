/* 受Haohanyh Computer Software Products Open Source LICENSE保护 https://github.com/Hny0305Lin/LICENSE/blob/main/LICENSE */
package com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCore;

public class ChatMessage {
    private String message;
    private String timestamp;
    private boolean isSent;

    public ChatMessage(String message, String timestamp, boolean isSent) {
        this.message = message;
        this.timestamp = timestamp;
        this.isSent = isSent;
    }

    public String getMessage() {
        return message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public boolean isSent() {
        return isSent;
    }
}