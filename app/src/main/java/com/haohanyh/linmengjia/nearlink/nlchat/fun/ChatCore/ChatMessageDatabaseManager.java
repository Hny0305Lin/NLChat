/* 受Haohanyh Computer Software Products Open Source LICENSE保护 https://github.com/Hny0305Lin/LICENSE/blob/main/LICENSE */
package com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCore;

import android.content.Context;
import android.util.Log;

import com.haohanyh.linmengjia.nearlink.nlchat.fun.R;
import com.haohanyh.linmengjia.nearlink.nlchat.fun.SQLite.SQLiteDataBaseAPP;

public class ChatMessageDatabaseManager {
    private static final String TAG = "ChatSaveMessageDatabaseManager & NLChat";

    //调用SQLite
    private static SQLiteDataBaseAPP dbHelper;

    private Context context;

    public ChatMessageDatabaseManager(Context context) {
        this.context = context;

        dbHelper = SQLiteDataBaseAPP.SQLiteData();
        dbHelper.CreateSql(context.getFilesDir().getPath());

        if (ChatUtils.isSqlitehistorymanagerlog())
            Log.i(TAG,  "当前数据库保存地址：" + context.getFilesDir().getPath()); // 打印储存位置到日志
    }

    public void saveMessageToDatabase(String timestamp, String message, String sender) {
        //检索是否有空消息，串口通讯时常有相关问题
        if (message == null || message.trim().isEmpty()) {
            return;
        }
        //如果有消息再保存，上面是没消息不予保存
        try {
            dbHelper.saveMessageToDatabase(message, sender, timestamp);
            dbHelper.saveVersionToDatabase(context.getString(R.string.app_version));
        } finally {
            dbHelper.close(); // 确保数据库在操作后关闭
        }

        if (ChatUtils.isSqlitehistorymanagerlog())
            Log.i(TAG,  "当前数据库保存内容：\n消息内容" + message + "\n用户名" + sender + "\n时间戳" + timestamp); // 打印内容到日志
    }

    public void saveDebugMessageToDatabase(String timestamp, String message, String sender) {
        //检索是否有空消息，串口通讯时常有相关问题
        if (message == null || message.trim().isEmpty()) {
            return;
        }
        //如果有消息再保存，上面是没消息不予保存
        try {
            dbHelper.saveDebugToDatabase(message, sender, timestamp);
        } finally {
            dbHelper.close(); // 确保数据库在操作后关闭
        }

        if (ChatUtils.isSqlitehistorymanagerlog())
            Log.i(TAG,  "当前数据库保存内容：\n消息内容" + message + "\n用户名" + sender + "\n时间戳" + timestamp); // 打印内容到日志
    }
}
