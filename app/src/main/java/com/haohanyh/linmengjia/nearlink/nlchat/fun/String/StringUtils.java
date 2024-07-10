/* 受Haohanyh Computer Software Products Open Source LICENSE保护 https://github.com/Hny0305Lin/LICENSE/blob/main/LICENSE */
package com.haohanyh.linmengjia.nearlink.nlchat.fun.String;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class StringUtils {
    public StringUtils() {}
    public static StringUtils needProcess() { return StringUtils.process.thing; }
    protected static class process { private static final StringUtils thing = new StringUtils(); }

//    //数据转换
//    /**
//     * 将String转化为byte[]数组
//     * @param arg 需要转换的String对象
//     * @return 转换后的byte[]数组
//     */
//    public byte[] toByteArray(String arg) {
//        if (arg != null) {
//            /* 1.先去除String中的' '，然后将String转换为char数组 */
//            char[] NewArray = new char[1000];
//            char[] array = arg.toCharArray();
//            int length = 0;
//            for (char c : array) {
//                if (c != ' ') {
//                    NewArray[length] = c;
//                    length++;
//                }
//            }
//
//            byte[] byteArray = new byte[length];
//            for (int i = 0; i < length; i++) {
//                byteArray[i] = (byte)NewArray[i];
//            }
//            return byteArray;
//
//        }
//        return new byte[]{};
//    }

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
}
