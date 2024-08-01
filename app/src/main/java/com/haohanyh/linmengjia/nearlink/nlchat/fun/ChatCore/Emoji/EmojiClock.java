/* 受Haohanyh Computer Software Products Open Source LICENSE保护 https://github.com/Hny0305Lin/LICENSE/blob/main/LICENSE */
package com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCore.Emoji;

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
