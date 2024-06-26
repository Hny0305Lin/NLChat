/* 受Haohanyh Computer Software Products Open Source LICENSE保护 https://github.com/Hny0305Lin/LICENSE/blob/main/LICENSE */
/* NLChat保存数据库文件和管理增删改查数据库区域
 * 获取最高文件存储权限后，即可在手机的Android软件目录下保存SQLite文件了，非ROOT用户无法获取本文件。
 * 开发者如果拥有的设备没有ROOT，请在MainActivity.java里修改保存路径到Android SD卡目录（用户目录）
 */
package com.haohanyh.linmengjia.nearlink.nlchat.fun.SQLite;

import android.content.ContentValues;
import android.database.Cursor;

import io.requery.android.database.sqlite.SQLiteDatabase;

public class SQLiteDataBaseAPP {

    SQLiteDatabase sqLiteDataBaseForAPP,sqLiteDataBaseForUser,sqLiteDataBaseForChat,sqLiteDataBaseForDevice;

    protected SQLiteDataBaseAPP() { }
    public static SQLiteDataBaseAPP SQLiteData() { return SQLiteDataBaseAPP.data.shuju; }
    protected static class data { private static final SQLiteDataBaseAPP shuju = new SQLiteDataBaseAPP(); }

    public void CreateSql(String SDPath) {
        sqLiteDataBaseForAPP = SQLiteDatabase.openOrCreateDatabase(SDPath + "/NLChat.db",null);
        sqLiteDataBaseForUser = SQLiteDatabase.openOrCreateDatabase(SDPath + "/NLChatUser.db", null);

        sqLiteDataBaseForChat = SQLiteDatabase.openOrCreateDatabase(SDPath + "/NLChatChat.db",null);
        createChatTable();
        sqLiteDataBaseForDevice = SQLiteDatabase.openOrCreateDatabase(SDPath + "/NLChatDevice.db",null);
    }

    private void createChatTable() {
        String TABLE_CREATE =
                "CREATE TABLE IF NOT EXISTS messages (" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "message TEXT, " +
                        "sender TEXT, " +
                        "timestamp TEXT);";
        sqLiteDataBaseForChat.execSQL(TABLE_CREATE);
    }

    public void saveMessageToDatabase(String message, String sender, String timestamp) {
        ContentValues values = new ContentValues();
        values.put("message", message);
        values.put("sender", sender);
        values.put("timestamp", timestamp);
        sqLiteDataBaseForChat.insert("messages", null, values);
    }

    public Cursor getAllMessages() {
        return sqLiteDataBaseForChat.query("messages", null, null, null, null, null, "timestamp ASC");
    }
}
