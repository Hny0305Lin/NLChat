/* 受Haohanyh Computer Software Products Open Source LICENSE保护 https://github.com/Hny0305Lin/LICENSE/blob/main/LICENSE */
package com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCore;

public class ChatUtilsForSettings {
    //这里设置的是UI相关，是否做到消息滚动展示在UI上，而不是全部展示，全部展示会占用大量UI资源
    private static boolean showUartLog = false; // 控制是否启用显示串口Log
    private static final boolean setDebugLog = true; // 控制是否启用显示串口Log，打印在UI上（开发者使用）
    private static final boolean scrollingMessages = true; // 控制是否启用滚动消息功能
    private static boolean clipMessages = true; // 控制是否启用剪贴板功能
    //这里设置的是数据库相关
    private static boolean sqlitemanager = true; // 控制是否启用SQLite存储功能
    private static boolean sqlitehistory = false; // 控制是否启用SQLite历史记录显示功能
    private static boolean showsqlitehistory = true; // 控制是否启用SQLite历史记录显示，这里设置是没用的（因为不是Final）
    private static final boolean sqlitehistorymanagerlog = true; // 控制是否启用SQLite保存存储、历史记录显示Logcat（开发者使用）
    //这里设置的是阅后即焚功能相关
    private static boolean burnmessage = false; // 控制是否启用阅后即焚聊天功能
    private static final boolean burnshowword = false; // 控制是否启用阅后即焚文字显示（后续转为用户使用，false为不需要，true为需要点击显示3秒文字）
    private static final boolean burnalertdialog = false; // 控制是否启用阅后即焚AliertDialog（开发者使用，false为长按消失消息，true为长按显示开发者信息）
    private static final boolean burnmessagenotify = false; // 控制是否启用阅后即焚每条消息的通知
    private static final int burnwordshowtimer = 3000; // 控制阅后即焚阅读时间（推荐3秒钟）
    private static final int burntimer = 120000; // 控制阅后即焚的时间（2分钟,120000.如为开发者推荐全局2秒钟,2000）
    //这里设置的是跟C代码相关的，白名单获取聊天文本，当这些文本出现在串口通讯里面的时候，提取这String后者即可，期间过滤掉前者和大量串口log。
    private static final String PREFIX_SERVER = " Let's start chatting, This is the content of the server:";
    private static final String PREFIX_CLIENT = " Let's start chatting, This is the content of the client:";
    //这里设置的是跟C代码相关的，黑名单处理串口Log，当这些文本出现在串口通讯里面的时候，提取这String内容
    private static final String PREFIX_LOG_CONNECTED = "[Connected]";
    private static final String PREFIX_LOG_DISCONNECTED = "[Disconnected]";
    private static final String PREFIX_LOG_ACORE = "[ACore]";
    private static final String PREFIX_LOG_SLE_UART_SERVER = "[sle uart server]";
    private static final String PREFIX_LOG_SLE_UART_SERVER_BEARPI3863 = "[sle uart server bearpi3863]";
    private static final String PREFIX_LOG_CONNECT_STATE_CHANGED = "[sle uart server] connect state changed";
    private static final String PREFIX_LOG_CONNECT_STATE_CHANGED_BEARPI3863 = "[sle uart server bearpi3863] connect state changed";
    private static final String PREFIX_LOG_PAIR_COMPLETE = "[sle uart server] pair complete";
    private static final String PREFIX_LOG_PAIR_COMPLETE_BEARPI3863 = "[sle uart server bearpi3863] pair complete";
    private static final String PREFIX_LOG_NEARLINK_DEVICES_ADDR = "[sle uart server] pair complete addr:";
    private static final String PREFIX_LOG_NEARLINK_DEVICES_ADDR_BEARPI3863 = "[sle uart server bearpi3863] pair complete addr:";
    private static final String PREFIX_LOG_SSAPS_MTU_CHANGED = "[sle uart server] ssaps ssaps_mtu_changed_cbk";
    private static final String PREFIX_LOG_SSAPS_MTU_CHANGED_BEARPI3863 = "[sle uart server bearpi3863] ssaps ssaps_mtu_changed_cbk";
    private static final String PREFIX_LOG_SLE_ANNOUNCE_ENABLE_CALLBACK = "[sle uart server] sle announce enable callback";
    private static final String PREFIX_LOG_SLE_ANNOUNCE_ENABLE_CALLBACK_BEARPI3863 = "[sle uart server bearpi3863] sle announce enable callback";
    private static final String PREFIX_LOG_NOT_CONNECTED_SERVER = "[sle uart server] sle client is not connected! ";
    private static final String PREFIX_LOG_NOT_CONNECTED_SERVER_BEARPI3863 = "[sle uart server bearpi3863] sle client is not connected! ";

    private static final String PREFIX_LOG_SLE_UART_CLIENT = "[sle uart client]";
    private static final String PREFIX_LOG_SLE_UART_CLIENT_BEARPI3863 = "[sle uart client bearpi3863]";
    private static final String PREFIX_LOG_CLIENT_CONNECT_STATE_CHANGED = "[sle uart client] conn state changed";
    private static final String PREFIX_LOG_CLIENT_CONNECT_STATE_CHANGED_BEARPI3863 = "[sle uart client bearpi3863] conn state changed";
    private static final String PREFIX_LOG_CLIENT_STATE_CONNECTED = "[sle uart client] SLE_ACB_STATE_CONNECTED";
    private static final String PREFIX_LOG_CLIENT_STATE_CONNECTED_BEARPI3863 = "[sle uart client bearpi3863] SLE_ACB_STATE_CONNECTED";
    private static final String PREFIX_LOG_CLIENT_LOW_RX = "[sle uart client] sle_low_latency_rx_enable";
    private static final String PREFIX_LOG_CLIENT_LOW_RX_BEARPI3863 = "[sle uart client bearpi3863] sle_low_latency_rx_enable";
    private static final String PREFIX_LOG_CLIENT_PAIR_COMPLETE = "[sle uart client] pair complete";
    private static final String PREFIX_LOG_CLIENT_PAIR_COMPLETE_BEARPI3863 = "[sle uart client bearpi3863] pair complete";
    private static final String PREFIX_LOG_CLIENT_NEARLINK_DEVICES_ADDR = "[sle uart client] pair complete conn_id:0, addr:";
    private static final String PREFIX_LOG_CLIENT_NEARLINK_DEVICES_ADDR_BEARPI3863 = "[sle uart client bearpi3863] pair complete conn_id:0, addr:";
    private static final String PREFIX_LOG_CLIENT_SSAPC_EXCHANGE = "ssapc exchange info";
    private static final String PREFIX_LOG_CLIENT_SLE_ANNOUNCE_ENABLE_CALLBACK = "[sle uart client] exchange_info_cbk";
    private static final String PREFIX_LOG_CLIENT_SLE_ANNOUNCE_ENABLE_CALLBACK_BEARPI3863 = "[sle uart client bearpi3863] exchange_info_cbk";
    private static final String PREFIX_LOG_CLIENT_SLE_DISCOVERY = "discovery character cbk";
    private static final String PREFIX_LOG_CLIENT_MTU = "[sle uart client] exchange mtu";
    private static final String PREFIX_LOG_CLIENT_MTU_BEARPI3863 = "[sle uart client bearpi3863] exchange mtu";
    private static final String PREFIX_LOG_CLIENT_SAMPLE_FIND_P_CBK = "[sle uart client] sle_uart_client_sample_find_property_cbk";
    private static final String PREFIX_LOG_CLIENT_SAMPLE_FIND_P_CBK_BEARPI3863 = "[sle uart client bearpi3863] sle_uart_client_sample_find_property_cbk";
    private static final String PREFIX_LOG_CLIENT_SAMPLE_FIND_SD_CBK = "[sle uart client] sle_uart_client_sample_find_structure_cmp_cbk";
    private static final String PREFIX_LOG_CLIENT_SAMPLE_FIND_SD_CBK_BEARPI3863 = "[sle uart client bearpi3863] sle_uart_client_sample_find_structure_cmp_cbk";



    // todo Hi3863 Log还没适配全（润和目前为还未成功）

    // 控制是否启用显示串口Log，打印在UI上（开发者使用）
    public static boolean isShowUartLog() { return showUartLog; }

    public static void setShowUartLog(boolean showUartLog) { ChatUtilsForSettings.showUartLog = showUartLog; }

    public static boolean isSetDebugLog() { return setDebugLog;}
    //控制是否启用滚动消息功能
    public static boolean isScrollingMessages() { return scrollingMessages; }
    //控制是否启用剪贴板功能和设置
    public static boolean isClipMessages() { return clipMessages; }

    public static void setClipMessages(boolean clipMessages) { ChatUtilsForSettings.clipMessages = clipMessages; }
    //控制是否启用SQLite功能
    public static boolean isSqlitemanager() { return sqlitemanager; }

    public static void setSqlitemanager(boolean sqlitemanager) { ChatUtilsForSettings.sqlitemanager = sqlitemanager; }
    //控制是否启用SQLite历史记录功能
    public static boolean isSqliteHistory() { return sqlitehistory; }

    public static void setSqliteHistory(boolean sqlitehistory) { ChatUtilsForSettings.sqlitehistory = sqlitehistory; }

    public static boolean isShowSqliteHistory() { return showsqlitehistory; }

    public static void setShowSqliteHistory(boolean showsqlitehistory) { ChatUtilsForSettings.showsqlitehistory = showsqlitehistory; }
    //控制是否启用SQLite保存存储、历史记录显示Logcat
    public static boolean isSqlitehistorymanagerlog() { return sqlitehistorymanagerlog; }
    //控制是否启动阅后即焚功能
    public static boolean isBurnmessage() { return burnmessage; }

    public static boolean isBurnshowword() { return burnshowword; }

    public static boolean isBurnalertdialog() { return burnalertdialog; }

    public static void setBurnmessage(boolean burnmessage) { ChatUtilsForSettings.burnmessage = burnmessage; }

    public static int getBurnwordshowtimer() { return burnwordshowtimer; }

    public static int getBurntimer() { return burntimer; }

    //对方为星闪服务端（User）
    public static String getPrefixServer() {
        return PREFIX_SERVER;
    }

    //对方为星闪客户端（Me）
    public static String getPrefixClient() {
        return PREFIX_CLIENT;
    }

    /* 如下为串口Log内容，有服务板和客户板的
     * 如果不懂怎么判断该方法是否为两者其中之一，请多用Ctrl键定位。
     */
    public static String getPrefixLogConnected() {
        return PREFIX_LOG_CONNECTED;
    }

    public static String getPrefixLogDisconnected() {
        return PREFIX_LOG_DISCONNECTED;
    }

    public static String getPrefixLogAcore() {
        return PREFIX_LOG_ACORE;
    }

    public static String getPrefixLogSleUartServer() {
        return PREFIX_LOG_SLE_UART_SERVER;
    }

    public static String getPrefixLogSleUartServerBearpi3863() {
        return PREFIX_LOG_SLE_UART_SERVER_BEARPI3863;
    }

    public static String getPrefixLogConnectStateChanged() {
        return PREFIX_LOG_CONNECT_STATE_CHANGED;
    }

    public static String getPrefixLogConnectStateChangedBearpi3863() {
        return PREFIX_LOG_CONNECT_STATE_CHANGED_BEARPI3863;
    }

    public static String getPrefixLogPairComplete() {
        return PREFIX_LOG_PAIR_COMPLETE;
    }

    public static String getPrefixLogPairCompleteBearpi3863() {
        return PREFIX_LOG_PAIR_COMPLETE_BEARPI3863;
    }

    public static String getPrefixLogNotConnectedServer() {
        return PREFIX_LOG_NOT_CONNECTED_SERVER;
    }

    public static String getPrefixLogNotConnectedServerBearpi3863() {
        return PREFIX_LOG_NOT_CONNECTED_SERVER_BEARPI3863;
    }

    public static String getPrefixLogNearlinkDevicesAddr() {
        return PREFIX_LOG_NEARLINK_DEVICES_ADDR;
    }

    public static String getPrefixLogNearlinkDevicesAddrBearpi3863() {
        return PREFIX_LOG_NEARLINK_DEVICES_ADDR_BEARPI3863;
    }

    public static String getPrefixLogSsapsMtuChanged() {
        return PREFIX_LOG_SSAPS_MTU_CHANGED;
    }

    public static String getPrefixLogSsapsMtuChangedBearpi3863() {
        return PREFIX_LOG_SSAPS_MTU_CHANGED_BEARPI3863;
    }

    public static String getPrefixLogSleAnnounceEnableCallback() {
        return PREFIX_LOG_SLE_ANNOUNCE_ENABLE_CALLBACK;
    }

    public static String getPrefixLogSleAnnounceEnableCallbackBearpi3863() {
        return PREFIX_LOG_SLE_ANNOUNCE_ENABLE_CALLBACK_BEARPI3863;
    }

    public static String getPrefixLogSleUartClient() {
        return PREFIX_LOG_SLE_UART_CLIENT;
    }

    public static String getPrefixLogSleUartClientBearpi3863() {
        return PREFIX_LOG_SLE_UART_CLIENT_BEARPI3863;
    }

    public static String getPrefixLogClientConnectStateChanged() {
        return PREFIX_LOG_CLIENT_CONNECT_STATE_CHANGED;
    }

    public static String getPrefixLogClientConnectStateChangedBearpi3863() {
        return PREFIX_LOG_CLIENT_CONNECT_STATE_CHANGED_BEARPI3863;
    }

    public static String getPrefixLogClientStateConnected() {
        return PREFIX_LOG_CLIENT_STATE_CONNECTED;
    }

    public static String getPrefixLogClientStateConnectedBearpi3863() {
        return PREFIX_LOG_CLIENT_STATE_CONNECTED_BEARPI3863;
    }

    public static String getPrefixLogClientLowRx() {
        return PREFIX_LOG_CLIENT_LOW_RX;
    }

    public static String getPrefixLogClientLowRxBearpi3863() {
        return PREFIX_LOG_CLIENT_LOW_RX_BEARPI3863;
    }

    public static String getPrefixLogClientPairComplete() {
        return PREFIX_LOG_CLIENT_PAIR_COMPLETE;
    }

    public static String getPrefixLogClientPairCompleteBearpi3863() {
        return PREFIX_LOG_CLIENT_PAIR_COMPLETE_BEARPI3863;
    }

    public static String getPrefixLogClientNearlinkDevicesAddr() {
        return PREFIX_LOG_CLIENT_NEARLINK_DEVICES_ADDR;
    }

    public static String getPrefixLogClientNearlinkDevicesAddrBearpi3863() {
        return PREFIX_LOG_CLIENT_NEARLINK_DEVICES_ADDR_BEARPI3863;
    }

    public static String getPrefixLogClientSsapcExchange() {
        return PREFIX_LOG_CLIENT_SSAPC_EXCHANGE;
    }

    public static String getPrefixLogClientSleAnnounceEnableCallback() {
        return PREFIX_LOG_CLIENT_SLE_ANNOUNCE_ENABLE_CALLBACK;
    }

    public static String getPrefixLogClientSleAnnounceEnableCallbackBearpi3863() {
        return PREFIX_LOG_CLIENT_SLE_ANNOUNCE_ENABLE_CALLBACK_BEARPI3863;
    }

    public static String getPrefixLogClientSleDiscovery() {
        return PREFIX_LOG_CLIENT_SLE_DISCOVERY;
    }

    public static String getPrefixLogClientMtu() {
        return PREFIX_LOG_CLIENT_MTU;
    }

    public static String getPrefixLogClientMtuBearpi3863() {
        return PREFIX_LOG_CLIENT_MTU_BEARPI3863;
    }

    public static String getPrefixLogClientSampleFindPCbk() {
        return PREFIX_LOG_CLIENT_SAMPLE_FIND_P_CBK;
    }

    public static String getPrefixLogClientSampleFindPCbkBearpi3863() {
        return PREFIX_LOG_CLIENT_SAMPLE_FIND_P_CBK_BEARPI3863;
    }

    public static String getPrefixLogClientSampleFindSdCbk() {
        return PREFIX_LOG_CLIENT_SAMPLE_FIND_SD_CBK;
    }

    public static String getPrefixLogClientSampleFindSdCbkBearpi3863() {
        return PREFIX_LOG_CLIENT_SAMPLE_FIND_SD_CBK_BEARPI3863;
    }
}
