/* 受Haohanyh Computer Software Products Open Source LICENSE保护 https://github.com/Hny0305Lin/LICENSE/blob/main/LICENSE */
package com.haohanyh.linmengjia.nearlink.nlchat.fun;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Vibrator;

import com.haohanyh.linmengjia.nearlink.nlchat.ch34x.CH34xUARTDriver;

public class MainAPP extends Application {
    @SuppressLint("StaticFieldLeak")
    public static CH34xUARTDriver CH34X;         //需要将CH34x的驱动类写在APP类下面，使得帮助类的生命周期与整个应用程序的生命周期是相同的

    /**
     * 手机震动提醒
     */
    public static void Vibrate(Context context) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            long[] pattern = {0, 75, 37, 50};
            vibrator.vibrate(pattern, -1);
        }
    }
}
