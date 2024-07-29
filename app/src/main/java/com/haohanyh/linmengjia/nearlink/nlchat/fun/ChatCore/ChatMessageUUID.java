/* 受Haohanyh Computer Software Products Open Source LICENSE保护 https://github.com/Hny0305Lin/LICENSE/blob/main/LICENSE */
package com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCore;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChatMessageUUID {
    private static final String TAG = "ChatMessageUUID & NLChat";
    private List<String> uuidList;
    private String uuid;

    public ChatMessageUUID() {
        uuidList = new ArrayList<>();
    }

    public String generateUUID() {
        uuid = UUID.randomUUID().toString();
        uuidList.add(uuid);

        return uuid;
    }

    public List<String> getUUIDList() {
        return uuidList;
    }

    public String getUUID() {
        return uuid;
    }

    public void setUUID(String uuid) {
        this.uuid = uuid;
        if (!uuidList.contains(uuid)) {
            uuidList.add(uuid);
        }
    }
}