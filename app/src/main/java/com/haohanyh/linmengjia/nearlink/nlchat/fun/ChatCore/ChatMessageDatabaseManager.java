/* 受Haohanyh Computer Software Products Open Source LICENSE保护 https://github.com/Hny0305Lin/LICENSE/blob/main/LICENSE */
package com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCore;

import android.content.Context;
import android.util.Log;

import com.haohanyh.linmengjia.nearlink.nlchat.fun.R;
import com.haohanyh.linmengjia.nearlink.nlchat.fun.SQLite.SQLiteDataBaseAPP;

import java.util.UUID;

public class ChatMessageDatabaseManager {
    private static final String TAG = "ChatSaveMessageDatabaseManager & NLChat";

    //调用SQLite
    private static SQLiteDataBaseAPP dbHelper;

    private final ChatMessageUUID chatMessageUUID = new ChatMessageUUID();

    private Context context;

    public ChatMessageDatabaseManager(Context context) {
        this.context = context;

        dbHelper = SQLiteDataBaseAPP.SQLiteData();
        dbHelper.CreateSql(context.getFilesDir().getPath());

        if (ChatUtilsForSettings.isSqlitehistorymanagerlog())
            Log.i(TAG,  "当前数据库保存地址：" + context.getFilesDir().getPath()); // 打印储存位置到日志
    }

    public void saveMessageToDatabase(String timestamp, String message, String sender) {
        //检索是否有空消息，串口通讯时常有相关问题
        if (message == null || message.trim().isEmpty()) {
            return;
        }
        //如果有消息再保存，上面是没消息不予保存
        dbHelper.saveMessageToDatabase(message, sender, timestamp);
        dbHelper.saveVersionToDatabase(context.getString(R.string.app_version));

        if (ChatUtilsForSettings.isSqlitehistorymanagerlog())
            Log.i(TAG,  "当前数据库保存内容：\n消息内容:" + message + "\n用户名:" + sender + "\n时间戳:" + timestamp); // 打印内容到日志
    }

    public void saveMessageAndUUIDToDatabase(String timestamp, String message, String sender, String uuid) {
        //检索是否有空消息，串口通讯时常有相关问题
        if (message == null || message.trim().isEmpty()) {
            return;
        }

        String uuidlatest = "";
        if (uuid == null) {
            uuidlatest = UUID.randomUUID().toString();
            chatMessageUUID.setUUID(uuidlatest);
        } else {
            uuidlatest = uuid;
        }
        //如果有消息再保存，上面是没消息不予保存
        dbHelper.saveMessageToDatabase(message, sender, timestamp, uuidlatest);
        dbHelper.saveVersionToDatabase(context.getString(R.string.app_version));

        if (ChatUtilsForSettings.isSqlitehistorymanagerlog())
            Log.i(TAG,  "当前数据库保存内容：\n消息内容:" + message + "\n用户名:" + sender + "\n时间戳:" + timestamp + "\nUUID:" + uuidlatest); // 打印内容到日志
    }

    public void saveDebugMessageToDatabase(String timestamp, String message, String sender) {
        //检索是否有空消息，串口通讯时常有相关问题
        if (message == null || message.trim().isEmpty()) {
            return;
        }
        //如果有消息再保存，上面是没消息不予保存
        dbHelper.saveDebugToDatabase(message, sender, timestamp);

        if (ChatUtilsForSettings.isSqlitehistorymanagerlog())
            Log.i(TAG,  "当前数据库保存内容：\n消息内容:" + message + "\n用户名:" + sender + "\n时间戳:" + timestamp); // 打印内容到日志
    }

    // 在适当的时候（如应用退出时）调用此方法关闭数据库
    public void closeDatabase() {
        dbHelper.close();
    }

    // 新增方法：重新打开数据库
    public void openDatabase() {
        dbHelper.CreateSql(context.getFilesDir().getPath());
    }
}
