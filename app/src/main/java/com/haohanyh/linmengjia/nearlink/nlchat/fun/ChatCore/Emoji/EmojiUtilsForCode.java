package com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCore.Emoji;

public class EmojiUtilsForCode {
    private Emoji[] emojis;

    public EmojiUtilsForCode() {
        emojis = new Emoji[] {


                new Emoji("U+1F600", "E00000"), // 😀

        };
    }

    public Emoji[] getEmojis() {
        return emojis;
    }

    public Emoji getEmojiByShortUUID(String shortUUID) {
        for (Emoji emoji : emojis) {
            if (emoji.getShortUUID().equals(shortUUID)) {
                return emoji;
            }
        }
        return null;
    }
}

