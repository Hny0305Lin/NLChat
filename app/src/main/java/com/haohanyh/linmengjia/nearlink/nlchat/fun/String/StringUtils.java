/* 受Haohanyh Computer Software Products Open Source LICENSE保护 https://github.com/Hny0305Lin/LICENSE/blob/main/LICENSE */
package com.haohanyh.linmengjia.nearlink.nlchat.fun.String;

import com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCore.ChatUtilsForMessage;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class StringUtils {
    public StringUtils() {}
    public static StringUtils needProcess() { return StringUtils.process.thing; }
    protected static class process { private static final StringUtils thing = new StringUtils(); }

    /**
     * 将String转化为byte[]数组
     * @param arg 需要转换的String对象
     * @return 转换后的byte[]数组
     */
    public byte[] toByteArray(String arg) {
        if (arg != null) {
            try {
                // 去除String中的空格并转换为UTF-8编码的byte数组
                return arg.replace(" ", "").getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return new byte[]{};
    }

    public byte[] toByteArrayII(String arg) {
        if (arg != null) {
            try {
                // 使用UTF-8编码将字符串转换为字节数组
                return arg.getBytes(StandardCharsets.UTF_8);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new byte[0];
    }

    /**
     * 将byte[]数组转化为String
     * @param bytes 需要转换的byte[]数组
     * @return 转换后的String对象
     */
    public String toString(byte[] bytes) {
        if (bytes != null) {
            try {
                // 使用UTF-8编码将byte数组转换为String
                return new String(bytes, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * byte[]数组到String的转换
     * @param str 需要转换的Byte对象
     * @return 转换后的String数据
     */
    public String bytesToString(byte[] str) {
//        String keyword = null;
//        keyword = new String(str, StandardCharsets.UTF_8);
//        return keyword;
        return new String(str, StandardCharsets.UTF_8);
    }

    /**
     * 调整消息后String有\n强制换行符号，调整ViewHolder最后字符换行问题
     */
    public String msgSubEnter(ChatUtilsForMessage message) {
        String msg = message.getMessage();
        // 检查消息的最后一个字符是否为换行符，是则去除最后一个换行符
        if (msg.endsWith("\n"))
            msg = msg.substring(0, msg.length() - 1);
        return msg;
    }
}
