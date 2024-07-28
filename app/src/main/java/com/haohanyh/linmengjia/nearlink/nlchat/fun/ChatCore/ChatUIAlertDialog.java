/* 受Haohanyh Computer Software Products Open Source LICENSE保护 https://github.com/Hny0305Lin/LICENSE/blob/main/LICENSE */
package com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCore;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.haohanyh.linmengjia.nearlink.nlchat.fun.R;

public class ChatUIAlertDialog {

    private static boolean b = true;

    public static boolean showNormal(Context context, String title, String message, CompoundButton compoundButton) {
        new AlertDialog.Builder(context, R.style.HaohanyhDialog)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false) // 设置为 false 以防止点击对话框外部时消失
                .setPositiveButton("确认停止", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // 用户确认，允许取消勾选
                        compoundButton.setChecked(false);
                        b = false;
                    }
                })
                .setNegativeButton("取消停止", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // 用户取消，恢复勾选状态
                        compoundButton.setChecked(true);
                        b = true;
                    }
                })
                .show();
        return b;
    }

    /**
     * 常用于ChatAdapter，展示串口内容，并做出开发者功能以助于下一步处理
     * @param context   上下文，捆绑MainActivity
     * @param title     标题
     * @param message       捆绑R.string
     * @param positiveButtonText    普遍为对话框Yes按钮
     * @param negativeButtonText    普遍为对话框No按钮
     * @param neutralButtonText     普通按钮，这里做剪贴板功能
     */
    public static void showSerialLog(Context context, String title, String message, String positiveButtonText, String negativeButtonText, String neutralButtonText) {
        new AlertDialog.Builder(context, R.style.HaohanyhDialog)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton(negativeButtonText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNeutralButton(neutralButtonText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        copyToClipboard(context, message);
                    }
                })
                .show();
    }

    /**
     * 常用于ChatAdapter，展示聊天开发者内容，并做出开发者功能以助于下一步处理
     * @param context   上下文，捆绑MainActivity
     * @param title     标题
     * @param message       捆绑UI内消息
     * @param positiveButtonText    普遍为对话框Yes按钮
     * @param negativeButtonText    普遍为对话框No按钮
     * @param neutralButtonText     普通按钮，这里做剪贴板功能
     */
    public static void showMessageLog(Context context, String title, String message, String positiveButtonText, String negativeButtonText, String neutralButtonText) {
        new AlertDialog.Builder(context, R.style.HaohanyhDialog)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Positive button action
                    }
                })
                .setNegativeButton(negativeButtonText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Negative button action
                    }
                })
                .setNeutralButton(neutralButtonText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 复制进剪贴板
                        copyToClipboard(context, message);
                    }
                })
                .show();
    }

    private static void copyToClipboard(Context context, String text) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Copied Text", text);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(context, "已复制到剪贴板", Toast.LENGTH_SHORT).show();
    }
}
