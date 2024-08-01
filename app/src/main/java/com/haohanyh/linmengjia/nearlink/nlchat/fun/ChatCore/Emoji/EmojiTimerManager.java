/* 受Haohanyh Computer Software Products Open Source LICENSE保护 https://github.com/Hny0305Lin/LICENSE/blob/main/LICENSE */
package com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCore.Emoji;

import android.os.CountDownTimer;
import android.widget.TextView;
import java.util.HashMap;
import java.util.Map;

public class EmojiTimerManager {
    private Map<TextView, EmojiTimer> activeTimers = new HashMap<>();

    public void startTimer(TextView textView, long durationInMillis) {
        // 停止当前正在textView上运行的计时器（如果有）
        if (activeTimers.containsKey(textView)) {
            activeTimers.get(textView).stopTimer();
        }

        EmojiTimer timer = new EmojiTimer(textView, this);
        activeTimers.put(textView, timer);
        timer.startTimer(durationInMillis);
    }

    void removeTimer(EmojiTimer timer) {
        activeTimers.remove(timer.getTextView());
    }

    public class EmojiTimer {
        private TextView textView;
        private CountDownTimer countDownTimer;
        private EmojiTimerManager manager;

        public EmojiTimer(TextView textView, EmojiTimerManager manager) {
            this.textView = textView;
            this.manager = manager;
        }

        public void startTimer(long durationInMillis) {
            countDownTimer = new CountDownTimer(durationInMillis, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTextView(millisUntilFinished);
                }

                @Override
                public void onFinish() {
                    textView.setText(EmojiClock.getEmojiForMinute(0) + EmojiClock.getEmojiForSecond(0));
                    manager.removeTimer(EmojiTimer.this); // 自动从管理器中移除
                }
            };
            countDownTimer.start();
        }

        public void stopTimer() {
            if (countDownTimer != null) {
                countDownTimer.cancel();
                countDownTimer = null;
            }
        }

        private void updateTextView(long millisUntilFinished) {
            int totalSeconds = (int) (millisUntilFinished / 1000);
            int minutes = totalSeconds / 60;
            int seconds = totalSeconds % 60;

            String minuteEmoji = EmojiClock.getEmojiForMinute(minutes);
            String secondEmoji = EmojiClock.getEmojiForSecond(seconds);

            textView.setText(minuteEmoji + secondEmoji);
        }

        public TextView getTextView() {
            return textView;
        }
    }
}
