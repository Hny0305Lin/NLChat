/* 受Haohanyh Computer Software Products Open Source LICENSE保护 https://github.com/Hny0305Lin/LICENSE/blob/main/LICENSE */
/* NLChat保存数据库文件和管理增删改查数据库区域
 * 获取最高文件存储权限后，即可在手机的Android软件目录下保存SQLite文件了，非ROOT用户无法获取本文件。
 * 开发者如果拥有的设备没有ROOT，请在MainActivity.java里修改保存路径到Android SD卡目录（用户目录）
 */
package com.haohanyh.linmengjia.nearlink.nlchat.fun.SQLite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCore.ChatUtilsForSettings;

import io.requery.android.database.sqlite.SQLiteDatabase;

public class SQLiteDataBaseAPP {
    private static final String TAG = "SQLiteDataBaseAPP & NLChat";

    SQLiteDatabase sqLiteDataBaseForAPP;

    public SQLiteDataBaseAPP() { }
    public static SQLiteDataBaseAPP SQLiteData() { return SQLiteDataBaseAPP.data.shuju; }
    protected static class data { private static final SQLiteDataBaseAPP shuju = new SQLiteDataBaseAPP(); }

    public void CreateSql(String SDPath) {
        sqLiteDataBaseForAPP = SQLiteDatabase.openOrCreateDatabase(SDPath + "/NLChat.db",null);
        createChatTable();
        createChatUUIDTable();
        createDebugTable();
        createVersionTable();
    }

    //创建消息表，存储消息、相关用户、聊天时间，绑定ID
    private void createChatTable() {
        String TABLE_CREATE =
                "CREATE TABLE IF NOT EXISTS messages (" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "message TEXT, " +
                        "sender TEXT, " +
                        "timestamp TEXT);";
        sqLiteDataBaseForAPP.execSQL(TABLE_CREATE);
    }

    //创建带UUID的消息表，存储消息、相关用户、聊天时间，绑定UUID
    private void createChatUUIDTable() {
        String TABLE_CREATE =
                "CREATE TABLE IF NOT EXISTS messagesuuid (" +
                        "message TEXT, " +
                        "sender TEXT, " +
                        "timestamp TEXT, " +
                        "uuid TEXT PRIMARY KEY);"; // 使用 UUID 作为主键
        sqLiteDataBaseForAPP.execSQL(TABLE_CREATE);
    }

    //创建日志表，存储日志消息、相关用户、聊天时间
    private void createDebugTable() {
        String TABLE_CREATE =
                "CREATE TABLE IF NOT EXISTS debug (" +
                        "message TEXT, " +
                        "sender TEXT, " +
                        "timestamp TEXT);";
        sqLiteDataBaseForAPP.execSQL(TABLE_CREATE);
    }

    //创建版本表，存储聊天消息时使用该软件的版本号，绑定ID
    private void createVersionTable() {
        String TABLE_CREATE =
                "CREATE TABLE IF NOT EXISTS versions (" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "version TEXT);";
        sqLiteDataBaseForAPP.execSQL(TABLE_CREATE);
    }

    //保存消息到数据库
    public void saveMessageToDatabase(String message, String sender, String timestamp) {
        ContentValues values = new ContentValues();
        values.put("message", message);
        values.put("sender", sender);
        values.put("timestamp", timestamp);
        sqLiteDataBaseForAPP.insert("messages", null, values);
    }

    //保存带UUID消息到数据库
    public void saveMessageToDatabase(String message, String sender, String timestamp, String uuid) {
        // 检查消息是否包含特定字符串
        if (message.contains(ChatUtilsForSettings.getPrefixLogSleUartServer())) {
            return; // 如果包含，直接返回不执行保存
        }

        ContentValues values = new ContentValues();
        values.put("message", message);
        values.put("sender", sender);
        values.put("timestamp", timestamp);
        values.put("uuid", uuid);
        sqLiteDataBaseForAPP.insertWithOnConflict("messagesuuid", null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    //保存此时日志到数据库
    public void saveDebugToDatabase(String message, String sender, String timestamp) {
        ContentValues values = new ContentValues();
        values.put("message", message);
        values.put("sender", sender);
        values.put("timestamp", timestamp);
        sqLiteDataBaseForAPP.insert("debug", null, values);
    }

    //保存版本号到数据库
    public void saveVersionToDatabase(String version) {
        ContentValues values = new ContentValues();
        values.put("version", version);
        sqLiteDataBaseForAPP.insert("versions", null, values);
    }

    //获取表中消息，按时间戳升序排序
    public Cursor getAllMessages() {
        return sqLiteDataBaseForAPP.query("messages", null, null, null, null, null, "timestamp ASC");
    }

    //获取表中聊天信息捆绑的UUID
    @SuppressLint("Range")
    public String getUUIDForMessage(String message, String sender, String timestamp) {
        String uuid = null;
        Cursor cursor = null;
        try {
            String[] columns = {"uuid"};
            String selection = "message = ? AND sender = ? AND timestamp = ?";
            String[] selectionArgs = {message, sender, timestamp};
            cursor = sqLiteDataBaseForAPP.query("messagesuuid", columns, selection, selectionArgs, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                uuid = cursor.getString(cursor.getColumnIndex("uuid"));
            }
        } catch (Exception e) {
            Log.e(TAG, "查找UUID时发生错误", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return uuid;
    }


    // 关闭数据库连接
    public void close() {
        if (sqLiteDataBaseForAPP != null) {
            try {
                sqLiteDataBaseForAPP.close();
            } catch (Exception e) {
                Log.e(TAG, "关闭数据库时发生错误", e);
            }
        }
    }

    // todo 1.4数据库重构一下，目前这样的多表肯定是行不通了，会存在一些交互上的问题
}