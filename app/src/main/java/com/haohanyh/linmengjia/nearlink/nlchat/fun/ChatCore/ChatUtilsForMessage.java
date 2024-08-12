/* 受Haohanyh Computer Software Products Open Source LICENSE保护 https://github.com/Hny0305Lin/LICENSE/blob/main/LICENSE */
package com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCore;

public class ChatUtilsForMessage {
    private static final String TAG = "ChatMessage & NLChat";

    private ChatMessageUUID chatMessageUUID;

    private String message;
    private String timestamp;
    private int loglevel;

    private boolean isUser;
    private boolean isMe;
    private boolean isDebug;
    private boolean isSQLiteUser;
    private boolean isSQLiteMe;
    private boolean isSQLiteDebug;
    private boolean isUserBurn;
    private boolean isMeBurn;

    /**
     * 构造方法，适用于User消息
     * @param message   User消息
     * @param timestamp User消息时间
     * @param isUser    传参
     */
    public ChatUtilsForMessage(String message, String timestamp, boolean isUser) {
        this.message = message;
        this.timestamp = timestamp;
        this.isUser = isUser;
    }

    /**
     * 构造方法，适用于Me消息
     * @param message   Me消息
     * @param isMe      传参
     * @param timestamp Me消息时间
     */
    public ChatUtilsForMessage(String message, boolean isMe, String timestamp) {
        this.message = message;
        this.timestamp = timestamp;
        this.isMe = isMe;
    }

    /**
     * 构造方法，适用于User消息（阅后即焚）
     * @param message   User消息
     * @param timestamp User消息时间
     * @param isUserBurn    传参
     */
    public ChatUtilsForMessage(String message, String timestamp, boolean isUserBurn, String uuid) {
        this.message = message;
        this.timestamp = timestamp;
        this.isUserBurn = isUserBurn;

        chatMessageUUID = new ChatMessageUUID();
        chatMessageUUID.setUUID(uuid);
    }

    /**
     * 构造方法，适用于Me消息（阅后即焚）
     * @param message   Me消息
     * @param isMeBurn    传参
     * @param timestamp Me消息时间
     */
    public ChatUtilsForMessage(String message, boolean isMeBurn, String timestamp, String uuid) {
        this.message = message;
        this.timestamp = timestamp;
        this.isMeBurn = isMeBurn;

        chatMessageUUID = new ChatMessageUUID();
        chatMessageUUID.setUUID(uuid);
    }

    /**
     * 构造方法，适用于Debug日志消息
     * @param message   Debug日志内容
     * @param isDebug   传参
     * @param loglevel  日志等级
     */
    public ChatUtilsForMessage(String message, boolean isDebug, int loglevel) {
        this.message = message;
        this.isDebug = isDebug;
        this.loglevel = loglevel;
    }

    /**
     * 构造方法，适用于数据库历史消息
     * @param message   内容，历史消息
     * @param timestamp 历史消息时间
     * @param latest    判断
     * @param sqlite    识别为who，1为user历史，2为me历史，3为debug历史
     */
    public ChatUtilsForMessage(String message, String timestamp, boolean latest, int sqlite) {
        this.message = message;
        this.timestamp = timestamp;

        if (sqlite == 1) {
            isSQLiteUser = latest;
            isSQLiteMe = false;
            isSQLiteDebug = false;
        } else if (sqlite == 2) {
            isSQLiteUser = false;
            isSQLiteMe = latest;
            isSQLiteDebug = false;
        } else if (sqlite == 3) {
            isSQLiteUser = false;
            isSQLiteMe = false;
            isSQLiteDebug = latest;
        } else {
            isSQLiteUser = false;
            isSQLiteMe = false;
            isSQLiteDebug = false;
        }
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

    public boolean isMe() {
        return isMe;
    }

    public boolean isDebug() {
        return isDebug;
    }

    public boolean isSQLiteUser() {
        return isSQLiteUser;
    }

    public boolean isSQLiteMe() {
        return isSQLiteMe;
    }

    public boolean isSQLiteDebug() {
        return isSQLiteDebug;
    }

    public boolean isUserBurn() {
        return isUserBurn;
    }

    public boolean isMeBurn() {
        return isMeBurn;
    }

    public String getMessageId() {
        return chatMessageUUID.getUUID();
    }
}