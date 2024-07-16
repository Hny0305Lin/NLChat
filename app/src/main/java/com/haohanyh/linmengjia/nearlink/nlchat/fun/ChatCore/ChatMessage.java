/* 受Haohanyh Computer Software Products Open Source LICENSE保护 https://github.com/Hny0305Lin/LICENSE/blob/main/LICENSE */
package com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCore;

public class ChatMessage {
    private static final String TAG = "ChatMessage & NLChat";
    private String message;
    private String timestamp;
    private int loglevel;
    private boolean isUser;
    private boolean isDebug;
    private boolean isMe;

    /**
     * 构造方法，适用于User消息
     * @param message   User消息
     * @param timestamp User消息时间
     * @param isUser    传参
     */
    public ChatMessage(String message, String timestamp, boolean isUser) {
        this.message = message;
        this.timestamp = timestamp;
        this.isUser = isUser;
    }

    /**
     * 构造方法，适用于Debug日志消息
     * @param message   Debug日志内容
     * @param isDebug   传参
     * @param loglevel  日志等级
     */
    public ChatMessage(String message, boolean isDebug, int loglevel) {
        this.message = message;
        this.isDebug = isDebug;
        this.loglevel = loglevel;
    }

    /**
     * 构造方法，适用于Me消息
     * @param message   Me消息
     * @param isMe      传参
     * @param timestamp Me消息时间
     */
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

    public int getLoglevel() {
        return loglevel;
    }

    public boolean isUser() {
        return isUser;
    }

    public boolean isDebug() {
        return isDebug;
    }

    public boolean isMe() {
        return isMe;
    }
}