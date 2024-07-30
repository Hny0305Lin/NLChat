package com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCore;

public class EmojiClock {

    private static final String[] EMOJI_CLOCKS = {
            "🕛", "🕐", "🕑", "🕒", "🕓", "🕔", "🕕", "🕖", "🕗", "🕘", "🕙", "🕚"
    };

    public static String getEmojiForMinute(int minute) {
        return EMOJI_CLOCKS[minute % 12];
    }

    public static String getEmojiForSecond(int second) {
        return EMOJI_CLOCKS[second / 5];
    }
}
