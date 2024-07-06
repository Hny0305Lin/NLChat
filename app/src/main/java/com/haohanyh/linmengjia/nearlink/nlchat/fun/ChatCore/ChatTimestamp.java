/* 受Haohanyh Computer Software Products Open Source LICENSE保护 https://github.com/Hny0305Lin/LICENSE/blob/main/LICENSE */
package com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ChatTimestamp {

    public String getCurrentTimestamp() {                   //使用展示时间戳时，只需要展示今天日期和时间即可
        // 获取当前时间戳的逻辑
        return new SimpleDateFormat("MM-dd,HH:mm", Locale.getDefault()).format(new Date());
    }

    public String saveCurrentTimestamp() {                  //数据库写入时间戳时，写入完整
        // 获取当前时间戳的逻辑
        return new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.getDefault()).format(new Date());
    }
}
