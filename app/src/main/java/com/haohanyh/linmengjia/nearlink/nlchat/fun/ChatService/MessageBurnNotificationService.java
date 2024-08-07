/* 受Haohanyh Computer Software Products Open Source LICENSE保护 https://github.com/Hny0305Lin/LICENSE/blob/main/LICENSE */
package com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatService;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.haohanyh.linmengjia.nearlink.nlchat.fun.R;

public class MessageBurnNotificationService extends Service {
    private static final String TAG = "MessageBurnNotificationService";
    private static final String CHANNEL_ID = "BurnMessageChannel";
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable stopSelfRunnable = new Runnable() {
        @Override
        public void run() {
            Log.d(TAG, "5秒已过，停止服务");
            stopSelf(); // 停止服务
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "服务已创建");
        createNotificationChannel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "服务已启动");

        if (intent != null) {
            String message = intent.getStringExtra("message");
            Log.d(TAG, "收到的消息: " + message);
            Notification notification = createNotification("收到密信，请前往APP查看", message);
            startForeground(1, notification);
        } else {
            Log.d(TAG, "Intent为空");
        }

        // 5秒后自动停止服务
        handler.postDelayed(stopSelfRunnable, 5000);

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "服务已销毁");
        handler.removeCallbacks(stopSelfRunnable);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null; // We don't provide binding, so return null
    }

    private void createNotificationChannel() {
        Log.d(TAG, "创建通知渠道");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Burn Message Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(serviceChannel);
                Log.d(TAG, "通知渠道已创建");
            } else {
                Log.d(TAG, "通知管理器为空，无法创建通知渠道");
            }
        }
    }

    private Notification createNotification(String title, String contentText) {
        Log.d(TAG, "创建通知: " + title + " - " + contentText);
        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(contentText)
                .setSmallIcon(R.drawable.app_icon_huawei) // 替换为你的通知图标
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build();
    }

    public static void notifyUserToCheckApp(Context context, String message) {
        Log.d(TAG, "准备启动服务以通知用户检查应用");
        Intent serviceIntent = new Intent(context, MessageBurnNotificationService.class);
        serviceIntent.putExtra("message", message);
        context.startService(serviceIntent);
    }
}