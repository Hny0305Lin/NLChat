/* 受Haohanyh Computer Software Products Open Source LICENSE保护 https://github.com/Hny0305Lin/LICENSE/blob/main/LICENSE */
package com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCore;

import android.annotation.SuppressLint;
import android.content.Context;

import com.haohanyh.linmengjia.nearlink.nlchat.fun.R;
import com.haohanyh.linmengjia.nearlink.nlchat.fun.SQLite.SQLiteDataBaseAPP;

public class ChatSaveMessageDatabaseManager {

    //调用SQLite
    private static SQLiteDataBaseAPP dbHelper;

    private Context context;

    public ChatSaveMessageDatabaseManager(Context context) {
        this.context = context;
    }

    public void saveMessageToDatabase(String timestamp, String message, String sender) {
        //检索是否有空消息，串口通讯时常有相关问题
        if (message == null || message.trim().isEmpty()) {
            return;
        }
        //如果有消息再保存，上面是没消息不予保存
        dbHelper.saveMessageToDatabase(message, sender, timestamp);
        dbHelper.saveVersionToDatabase(context.getString(R.string.app_version));
    }
}