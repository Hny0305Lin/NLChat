package com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCore;

import android.os.CountDownTimer;
import android.widget.TextView;

public class EmojiTimer {

    private TextView textView;
    private CountDownTimer countDownTimer;

    public EmojiTimer(TextView textView) {
        this.textView = textView;
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
            }
        };
        countDownTimer.start();
    }

    private void updateTextView(long millisUntilFinished) {
        int totalSeconds = (int) (millisUntilFinished / 1000);
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;

        String minuteEmoji = EmojiClock.getEmojiForMinute(minutes);
        String secondEmoji = EmojiClock.getEmojiForSecond(seconds);

        textView.setText(minuteEmoji + secondEmoji);
    }

    public void stopTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
