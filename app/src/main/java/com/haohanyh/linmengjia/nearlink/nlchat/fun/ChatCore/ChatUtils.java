/* 受Haohanyh Computer Software Products Open Source LICENSE保护 https://github.com/Hny0305Lin/LICENSE/blob/main/LICENSE */
package com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCore;

public class ChatUtils {
    //这里设置的是UI相关，是否做到消息滚动展示在UI上，而不是全部展示，全部展示会占用大量UI资源
    private static boolean showUartLog = true; // 控制是否启用显示串口Log，打印在UI上（开发者使用）
    private static final boolean scrollingMessages = true; // 控制是否启用滚动消息功能
    private static final boolean clipMessages = true; // 控制是否启用剪贴板功能
    //这里设置的是数据库相关
    private static boolean sqlitemanager = true; // 控制是否启用SQLite存储功能
    private static boolean sqlitehistory = false; // 控制是否启用SQLite历史记录显示功能
    //这里设置的是跟C代码相关的，白名单获取聊天文本，当这些文本出现在串口通讯里面的时候，提取这String后者即可，期间过滤掉前者和大量串口log。
    private static final String PREFIX_SERVER = " Let's start chatting, This is the content of the server:";
    private static final String PREFIX_CLIENT = " Let's start chatting, This is the content of the client:";
    //这里设置的是跟C代码相关的，黑名单处理串口Log，当这些文本出现在串口通讯里面的时候，提取这String内容
    private static final String PREFIX_LOG_NOT_CONNECTED_SERVER = "[sle uart server] sle client is not connected! ";
    private static final String PREFIX_LOG_NEARLINK_DEVICES_ADDR = "[sle uart server] pair complete addr:";
    // 控制是否启用显示串口Log，打印在UI上（开发者使用）
    public static boolean isShowUartLog() { return showUartLog; }

    public static void setShowUartLog(boolean showUartLog) { ChatUtils.showUartLog = showUartLog; }
    //控制是否启用滚动消息功能
    public static boolean isScrollingMessages() { return scrollingMessages; }
    //控制是否启用剪贴板功能和设置
    public static boolean isClipMessages() { return clipMessages; }

    public static void setSqlitehistory(boolean sqlitehistory) { ChatUtils.sqlitehistory = sqlitehistory; }
    //控制是否启用SQLite功能
    public static boolean isSqlitemanager() { return sqlitemanager; }

    public static void setSqlitemanager(boolean sqlitemanager) { ChatUtils.sqlitemanager = sqlitemanager; }

    public static boolean isSqliteHistory() { return sqlitehistory; }

    public static void setSqliteHistory(boolean sqlitehistory) { ChatUtils.sqlitehistory = sqlitehistory; }

    //对方为星闪服务端
    public static String getPrefixServer() {
        return PREFIX_SERVER;
    }

    //对方为星闪客户端
    public static String getPrefixClient() {
        return PREFIX_CLIENT;
    }

    //串口Log
    public static String getPrefixLogNotConnectedServer() {
        return PREFIX_LOG_NOT_CONNECTED_SERVER;
    }

    public static String getPrefixLogNearlinkDevicesAddr() {
        return PREFIX_LOG_NEARLINK_DEVICES_ADDR;
    }
}
