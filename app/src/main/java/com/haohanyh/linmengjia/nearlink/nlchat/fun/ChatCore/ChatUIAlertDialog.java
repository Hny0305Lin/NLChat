package com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCore;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.CompoundButton;

import androidx.appcompat.app.AlertDialog;

import com.haohanyh.linmengjia.nearlink.nlchat.fun.R;

public class ChatUIAlertDialog {

    private static boolean b = true;

    public static boolean show(Context context, String title, String message, CompoundButton compoundButton) {
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
}