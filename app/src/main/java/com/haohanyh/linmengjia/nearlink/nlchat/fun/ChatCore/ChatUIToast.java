/* 受Haohanyh Computer Software Products Open Source LICENSE保护 https://github.com/Hny0305Lin/LICENSE/blob/main/LICENSE */
package com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCore;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.view.View;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;
import com.haohanyh.linmengjia.nearlink.nlchat.fun.MainAPP;
import com.haohanyh.linmengjia.nearlink.nlchat.fun.R;

public class ChatUIToast {

    /**
     * SnackBar通知 遵守Google Material
     * @param context 上下文
     * @param text  显示的文本
     * @param actiontext    按钮控件显示的文本
     * @param level 提示等级(0~4)，对应Android Log等级：Log.v  Log.d   Log.i   Log.w   Log.e
     * @param SnackbarLength SnackBar通知，通知滞留时间
     */
    public static void SnackBarToastForDebug(Context context, String text, String actiontext, int level, int SnackbarLength) {
        //找View和Layout
        View decorView = ((Activity) context).getWindow().getDecorView();
        CoordinatorLayout coordinatorLayout = decorView.findViewById(R.id.MainUI);
        //设置显示时间、按钮颜色、背景颜色、按钮点击状态
        Snackbar snackbar = Snackbar.make(coordinatorLayout, text, SnackbarLength);
        snackbar.setActionTextColor(context.getResources().getColor(R.color.Pink_is_justice));

        //APP夜间模式
        int nightModeFlags = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        boolean isNightMode = (nightModeFlags == Configuration.UI_MODE_NIGHT_YES);

        // 设置文本颜色为白色
        if (isNightMode) {
            View snackbarView = snackbar.getView();
            TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
            textView.setTextColor(context.getResources().getColor(android.R.color.white));
        }

        switch (level) {
            case 0: // VERBOSE日志 不重要的提示 黑
                snackbar.getView().setBackgroundColor(context.getResources().getColor(isNightMode ? R.color.snackbar_log_v_night : R.color.snackbar_log_v));
                break;
            case 1: // DEBUG日志 稍微重要的提示 蓝
                snackbar.getView().setBackgroundColor(context.getResources().getColor(isNightMode ? R.color.snackbar_log_d_night : R.color.snackbar_log_d));
                break;
            case 2: // INFO日志 一般重要的提示 绿
                snackbar.getView().setBackgroundColor(context.getResources().getColor(isNightMode ? R.color.snackbar_log_i_night : R.color.snackbar_log_i));
                break;
            case 3: // WARN日志 已经算重要的提示 橙
                snackbar.getView().setBackgroundColor(context.getResources().getColor(isNightMode ? R.color.snackbar_log_w_night : R.color.snackbar_log_w));
                break;
            case 4: // ERROR日志 很重要的提示 红
                snackbar.getView().setBackgroundColor(context.getResources().getColor(isNightMode ? R.color.snackbar_log_e_night : R.color.snackbar_log_e));
                break;
            default: // 随意啦
                snackbar.getView().setBackgroundColor(context.getResources().getColor(isNightMode ? R.color.blue_biaozhun_logowai_transparent_night : R.color.blue_biaozhun_logowai_transparent));
                break;
        }
        snackbar.setAction(actiontext, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainAPP.Vibrate(context);
                switch (level) {
                    case 520:
                        break;
                    case 521:
                        break;
                    default:
                        break;
                }
            }
        });
        // snackbar主动出击
        snackbar.show();
    }
}
