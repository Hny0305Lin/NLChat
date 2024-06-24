/* 受Haohanyh Computer Software Products Open Source LICENSE保护 https://github.com/Hny0305Lin/LICENSE/blob/main/LICENSE */
package com.haohanyh.linmengjia.nearlink.nlchat.fun.String;

import java.nio.charset.StandardCharsets;

public class StringUtils {
    public StringUtils() {}
    public static StringUtils needProcess() { return StringUtils.process.thing; }
    protected static class process { private static final StringUtils thing = new StringUtils(); }

    //数据转换
    /**
     * 将String转化为byte[]数组
     * @param arg 需要转换的String对象
     * @return 转换后的byte[]数组
     */
    public byte[] toByteArray(String arg) {
        if (arg != null) {
            /* 1.先去除String中的' '，然后将String转换为char数组 */
            char[] NewArray = new char[1000];
            char[] array = arg.toCharArray();
            int length = 0;
            for (char c : array) {
                if (c != ' ') {
                    NewArray[length] = c;
                    length++;
                }
            }

            byte[] byteArray = new byte[length];
            for (int i = 0; i < length; i++) {
                byteArray[i] = (byte)NewArray[i];
            }
            return byteArray;

        }
        return new byte[]{};
    }


    /**
     * byte[]数组到String的转换
     * @param str 需要转换的Byte对象
     * @return 转换后的String数据
     */
    public String bytesToString(byte[] str) {
        String keyword = null;
        keyword = new String(str, StandardCharsets.UTF_8);
        return keyword;
    }
}
