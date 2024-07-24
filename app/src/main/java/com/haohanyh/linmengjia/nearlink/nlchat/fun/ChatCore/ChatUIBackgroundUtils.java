/* 受Haohanyh Computer Software Products Open Source LICENSE保护 https://github.com/Hny0305Lin/LICENSE/blob/main/LICENSE */
package com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCore;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;

import androidx.cardview.widget.CardView;

import java.io.InputStream;

public class ChatUIBackgroundUtils {

    // 设置启动时的背景
    public static void setSavedBackground(Context context, View mainUIView) {
        String backgroundPath = ChatUtilsForFiles.getBackgroundPath(context);
        if (backgroundPath != null) {
            Uri backgroundUri = Uri.parse(backgroundPath);
            try {
                InputStream inputStream = context.getContentResolver().openInputStream(backgroundUri);
                Drawable drawable = Drawable.createFromStream(inputStream, backgroundUri.toString());
                mainUIView.setBackground(drawable);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 设置 CardView 背景为半透明
    public static void setCardViewBackground(CardView cardView, int color) {
        if (cardView != null) {
            cardView.setBackground(new ColorDrawable(color)); // 设置半透明背景
        }
    }
}
