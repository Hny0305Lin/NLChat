/* 受Haohanyh Computer Software Products Open Source LICENSE保护 https://github.com/Hny0305Lin/LICENSE/blob/main/LICENSE */
package com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatService;

import android.annotation.SuppressLint;
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

public class MyForegroundService extends Service {
    private static final String TAG = "MyForegroundService & NLChat";
    private static final String CHANNEL_ID = "ForegroundServiceChannel";
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable stopSelfRunnable = new Runnable() {
        @Override
        public void run() {
            stopSelf();
            showCompletionNotification();
            exitApp();
        }
    };
    private Runnable updateNotificationRunnable;
    private long endTime;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "服务已创建");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "服务已启动");

        createNotificationChannel();
        endTime = System.currentTimeMillis() + 10 * 60 * 1000; // 10分钟后停止服务
        startForeground(1, createNotification("保持软件后台运行，已启用，请注意电池消耗，消耗过快请关闭本程序。"));

        // 10分钟后自动停止服务
        handler.postDelayed(stopSelfRunnable, 10 * 60 * 1000);

        // 每秒更新通知
        updateNotificationRunnable = new Runnable() {
            @Override
            public void run() {
                long remainingTime = endTime - System.currentTimeMillis();
                if (remainingTime > 0) {
                    startForeground(1, createNotification("剩余时间: " + remainingTime / 1000 + "秒"));
                    handler.postDelayed(this, 1000);
                }
            }
        };
        handler.post(updateNotificationRunnable);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "服务已销毁");
        handler.removeCallbacks(stopSelfRunnable);
        handler.removeCallbacks(updateNotificationRunnable);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null; // We don't provide binding, so return null
    }

    @SuppressLint("ObsoleteSdkInt")
    private void createNotificationChannel() {
        Log.d(TAG, "背景服务通知已创建，可查看Android通知栏");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "背景服务通知",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(serviceChannel);
            }
        }
    }

    private Notification createNotification(String contentText) {
        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("NLChat保活活动")
                .setContentText(contentText)
                .setSmallIcon(R.drawable.app_icon_new)
                .build();
    }

    private void showCompletionNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification completionNotification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("NLChat保活活动")
                .setContentText("软件已在后台运行超过10分钟，已自动关闭并销毁Service和MainActivity。")
                .setSmallIcon(R.drawable.app_icon_new)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build();
        if (notificationManager != null) {
            notificationManager.notify(2, completionNotification);
        }
    }

    private void exitApp() {
        // 关闭所有Activity
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("com.haohanyh.linmengjia.nearlink.nlchat.fun.ACTION_EXIT_APP");
        sendBroadcast(broadcastIntent);

        // 终止进程
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}