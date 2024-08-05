package com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCore.Emoji;

public class Emoji {
    private String code;
    private String shortUUID;

    public Emoji(String code, String shortUUID) {
        this.code = code;
        this.shortUUID = shortUUID;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getShortUUID() {
        return shortUUID;
    }

    public void setShortUUID(String shortUUID) {
        this.shortUUID = shortUUID;
    }
}
