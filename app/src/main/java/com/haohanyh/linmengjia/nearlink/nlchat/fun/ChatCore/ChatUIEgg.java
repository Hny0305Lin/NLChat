/* 受Haohanyh Computer Software Products Open Source LICENSE保护 https://github.com/Hny0305Lin/LICENSE/blob/main/LICENSE */
package com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCore;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.haohanyh.linmengjia.nearlink.nlchat.fun.R;

import java.util.Objects;

public class ChatUIEgg {

    //没有人能够熄灭满天的星光，每一个开发者都是华为要汇聚的星星之火。星星之火，可以燎原。
    @SuppressLint("SetTextI18n")
    public void thanks3q(Context context) {
        TextView textView = new TextView(context);
        textView.setText("Egg~");
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        textView.setPadding(16, 16, 0, 8);
        textView.setGravity(Gravity.LEFT);
        textView.setTextColor(ContextCompat.getColor(context, R.color.Pink_is_fancy));
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.HaohanyhDialog)
                .setMessage(R.string.egg)
                .setCustomTitle(textView)
                .setNegativeButton("确定!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton("取消!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setNeutralButton("备用!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Objects.requireNonNull(alertDialog.getWindow()).setLayout(900,900);
        //Objects.requireNonNull(alertDialog.getWindow()).setLayout(900,1600);
    }
}
