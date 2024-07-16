/* 受Haohanyh Computer Software Products Open Source LICENSE保护 https://github.com/Hny0305Lin/LICENSE/blob/main/LICENSE */
package com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCore;

import static com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCore.ChatUIToast.SnackBarToastForDebug;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.material.snackbar.Snackbar;
import com.haohanyh.linmengjia.nearlink.nlchat.fun.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatProcessorForExtract {
    private static final String TAG = "ChatProcessorForExtract & NLChat";
    public static Handler HhandlerClipBoard;

    //用于存储提取到的内容
    private static StringBuilder extractedNumbers = new StringBuilder();
    private static StringBuilder extractedUrls = new StringBuilder();
    private static StringBuilder extractedEmails = new StringBuilder();
    private static StringBuilder extractedPhones = new StringBuilder();

    private static StringBuilder extractedLogForNearLinkAddr = new StringBuilder();

    //Handler初始化
    public static void initializeHandler() {
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }
        HhandlerClipBoard = new Handler();
    }

    //处理聊天数据
    public static void processChat(Context context, String string) {
        if (ChatUtils.isClipMessages()) {
            // 清空 extractedNumbers 等String以确保每次都是最新的提取结果
            extractedNumbers.setLength(0);
            extractedUrls.setLength(0);
            extractedEmails.setLength(0);
            extractedPhones.setLength(0);

            extractedLogForNearLinkAddr.setLength(0);

            // 提取四位和六位数字，但排除年份相关的四位数字
            Pattern pattern = Pattern.compile("\\b(?!19\\d{2}|20\\d{2})\\d{4}\\b|\\b\\d{6}\\b");
            Matcher matcher = pattern.matcher(string);
            while (matcher.find()) {
                Log.v(TAG, "找到疑似验证码，提取中");
                String foundNumber = matcher.group();
                extractedNumbers.append(foundNumber).append("\n");
            }

            // 提取链接
            Pattern urlPattern = Pattern.compile(
                    "(https?://(?:www\\.|(?!www))[^\\s\\.]+\\.[^\\s]{2,}|www\\.[^\\s]+\\.[^\\s]{2,})",
                    Pattern.CASE_INSENSITIVE);
            Matcher urlMatcher = urlPattern.matcher(string);
            while (urlMatcher.find()) {
                Log.v(TAG, "找到互联网链接，提取中");
                String foundUrl = urlMatcher.group();
                extractedUrls.append(foundUrl).append("\n");
            }

            // 提取电子邮件地址
            Pattern emailPattern = Pattern.compile(
                    "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}",
                    Pattern.CASE_INSENSITIVE);
            Matcher emailMatcher = emailPattern.matcher(string);
            while (emailMatcher.find()) {
                String foundEmail = emailMatcher.group();
                extractedEmails.append(foundEmail).append("\n");
            }

            // 提取符合条件的中国大陆电话号码（11位数字，第一位是1，第二位是3、5、7、8、9）
            Pattern phonePattern = Pattern.compile("\\b1[35789]\\d{9}\\b");
            Matcher phoneMatcher = phonePattern.matcher(string);
            while (phoneMatcher.find()) {
                String foundPhone = phoneMatcher.group();
                extractedPhones.append(foundPhone).append("\n");
            }

            // 提取符合条件的星闪设备Mac地址（Log）
            //开头是两位十六进制，接下来是三组 :**，然后是两位十六进制，最后是两到五位十六进制
            //Pattern macPattern = Pattern.compile("addr:([0-9a-fA-F]{2}(:\\*{2}){3}:[0-9a-fA-F]{2}:[0-9a-fA-F]{2,5})");
            //前四组是俩位十六进制或星号，第五组是俩位十六进制，最后一组是俩到伍位十六进制
            Pattern macPattern = Pattern.compile("addr:((?:[0-9a-fA-F]{2}|\\*{2})(?::(?:[0-9a-fA-F]{2}|\\*{2})){3}:[0-9a-fA-F]{2}:[0-9a-fA-F]{2,5})");
            Matcher macMatcher = macPattern.matcher(string);
            while (macMatcher.find()) {
                String foundMac = macMatcher.group();
                extractedLogForNearLinkAddr.append(foundMac).append("\n");
            }

            // 将提取到的数字和链接分别复制到剪贴板
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            if (extractedNumbers.length() > 0) {
                ClipData clip = ClipData.newPlainText("extractedNumbers", extractedNumbers.toString().trim());
                clipboard.setPrimaryClip(clip);
                SnackBarToastForDebug(context,"提取到疑似验证码，已复制到剪贴板!", "推荐去粘贴", 0, Snackbar.LENGTH_INDEFINITE);

                // 取消之前的清空任务并重新设置定时任务
                HhandlerClipBoard.removeCallbacksAndMessages(null);
                HhandlerClipBoard.postDelayed(new ClipboardRunnable(context), ClipboardRunnable.DELAY_NORMAL); // 验证码可以60秒以上，到达时间后清空剪贴板
            }

            // 将提取到的链接复制到剪贴板
            if (extractedUrls.length() > 0) {
                ClipData clipUrls = ClipData.newPlainText("extractedUrls", extractedUrls.toString().trim());
                clipboard.setPrimaryClip(clipUrls);
                SnackBarToastForDebug(context,"提取到链接，已复制到剪贴板!", "推荐去浏览器", 0, Snackbar.LENGTH_LONG);

                // 取消之前的清空任务并重新设置定时任务
                HhandlerClipBoard.removeCallbacksAndMessages(null);
                HhandlerClipBoard.postDelayed(new ClipboardRunnable(context), ClipboardRunnable.DELAY_LONG); // 链接可以90秒后清空剪贴板
            }

            // 将提取到的电子邮件地址复制到剪贴板
            if (extractedEmails.length() > 0) {
                ClipData clipEmails = ClipData.newPlainText("extractedEmails", extractedEmails.toString().trim());
                clipboard.setPrimaryClip(clipEmails);
                SnackBarToastForDebug(context,"提取到电子邮件地址，已复制到剪贴板!", "推荐去发邮件", 0, Snackbar.LENGTH_LONG);

                // 取消之前的清空任务并重新设置定时任务
                HhandlerClipBoard.removeCallbacksAndMessages(null);
                HhandlerClipBoard.postDelayed(new ClipboardRunnable(context), ClipboardRunnable.DELAY_SHORT); // 电子邮件可以30秒、60秒后清空剪贴板
            }

            // 将提取到的电话号码复制到剪贴板并调用电话应用程序
            if (extractedPhones.length() > 0) {
                String phoneNumber = extractedPhones.toString().trim();
                ClipData clipPhones = ClipData.newPlainText("extractedPhones", phoneNumber);
                clipboard.setPrimaryClip(clipPhones);

                // 取消之前的清空任务并重新设置定时任务
                HhandlerClipBoard.removeCallbacksAndMessages(null);
                HhandlerClipBoard.postDelayed(new ClipboardRunnable(context), ClipboardRunnable.DELAY_VERY_SHORT); // 电话号码有点特殊，15秒后清空剪贴板，或者注释掉不用清空

                // 调用电话应用程序拨打号码
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                context.startActivity(intent);

                // 在主线程上显示Toast提示
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "已添加电话到安卓软件上，号码为:" + phoneNumber, Toast.LENGTH_LONG).show();
                    }
                });
            }

            // 将提取到的星闪MAC地址复制到剪贴板
            if (extractedLogForNearLinkAddr.length() > 0) {
                ((Activity) context).runOnUiThread(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
                        AppCompatTextView compatTextView = ((Activity) context).findViewById(R.id.userTitleNewUI);
                        compatTextView.setText("New UI, opposite mac" + extractedLogForNearLinkAddr.toString().trim());
                    }
                });

                ClipData clipNearLinkAddr = ClipData.newPlainText("extractedLogForNearLinkAddr", extractedLogForNearLinkAddr.toString().trim());
                clipboard.setPrimaryClip(clipNearLinkAddr);
                SnackBarToastForDebug(context,"提取到星闪对方设备地址，已复制到剪贴板!", "推荐去保存", 0, Snackbar.LENGTH_LONG);

                // 取消之前的清空任务并重新设置定时任务
                HhandlerClipBoard.removeCallbacksAndMessages(null);
                HhandlerClipBoard.postDelayed(new ClipboardRunnable(context), ClipboardRunnable.DELAY_VERY_LONG); // 星闪设备地址可以保存久点
            }
        }
    }

    public static class ClipboardRunnable implements Runnable {
        // 定义静态全局变量来管理延迟时间
        public static final long DELAY_VERY_SHORT = 15000; // 15秒
        public static final long DELAY_SHORT = 30000; // 30秒
        public static final long DELAY_NORMAL = 60000; // 60秒
        public static final long DELAY_LONG = 90000;  // 90秒
        public static final long DELAY_VERY_LONG = 180000;  // 180秒
        public static final long DELAY_VERY_VERY_LONG = 600000; // 600秒，根据自己想要的时间来做决定，这里保留复现接口
        public static final long DELAY_SAFETY_TIMER = 1800000; // 1800秒，1/2小时，剪贴板功能为安全起见，请务必对剪贴板信息保存勿超过30分钟

        private Context context;

        //构造方法，接收Context对象，使用应用上下文避免遇到内存泄露问题
        ClipboardRunnable(Context context) {
            this.context = context.getApplicationContext(); // 使用应用上下文避免内存泄漏
        }

        @Override
        public void run() {
            //获取剪贴板杠两千，创建一个空的剪贴板对象，将空的剪贴板设置为有内容的剪贴板对象里去，记录日志。（清空过程）
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData emptyClip = ClipData.newPlainText("", "");
            clipboard.setPrimaryClip(emptyClip);
            Log.v(TAG, "剪贴板已清空");
        }
    }
}
