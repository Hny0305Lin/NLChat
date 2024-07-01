/* 受Haohanyh Computer Software Products Open Source LICENSE保护 https://github.com/Hny0305Lin/LICENSE/blob/main/LICENSE */
package com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCore;

public class ChatUtils {
    //这里设置的是UI相关，是否做到消息滚动展示在UI上，而不是全部展示，全部展示会占用大量UI资源
    private static final boolean scrollingMessages = false; // 控制是否启用滚动消息功能
    //这里设置的是数据库相关，是否做到消息保存进SQLite里
    private static boolean sqlite = true; // 控制是否启用SQLite功能
    //这里设置的是跟C代码相关的，白名单获取聊天文本，当这些文本出现在串口通讯里面的时候，提取这String后者即可，期间过滤掉前者和大量串口log。
    private static final String PREFIX_SERVER = " Let's start chatting, This is the content of the server:";
    private static final String PREFIX_CLIENT = " Let's start chatting, This is the content of the client:";
    //控制是否启用滚动消息功能
    public static boolean isScrollingMessages() { return scrollingMessages; }

    //控制是否启用SQLite功能
    public static boolean isSqlite() { return sqlite; }

    public static void setSqlite(boolean sqlite) { ChatUtils.sqlite = sqlite; }

    //对方为星闪服务端
    public static String getPrefixServer() {
        return PREFIX_SERVER;
    }

    //对方为星闪客户端
    public static String getPrefixClient() {
        return PREFIX_CLIENT;
    }
}
