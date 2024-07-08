/* 受Haohanyh Computer Software Products Open Source LICENSE保护 https://github.com/Hny0305Lin/LICENSE/blob/main/LICENSE */
package com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCore;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.widget.TextView;

public class ChatFontUtils {

    private static final String TAG = "ChatFontUtils & NLChat";

    private static int fontPathNum = 0;    //默认，思源字体

    // getter and settertiao
    public static int getFontPathNum() {
        return fontPathNum;
    }

    public static void setFontPathNum(int fontPathNum) {
        ChatFontUtils.fontPathNum = fontPathNum;
    }

    // 加载并应用自定义字体
    public static void applyCustomFont(Context context, TextView textView, int fontType) {
        String fontPath = getFontPath(fontType);
        try {
            Typeface customFont = Typeface.createFromAsset(context.getAssets(), fontPath);
            textView.setTypeface(customFont);
            Log.i(TAG, "Font asset found: " + fontPath);
        } catch (RuntimeException e) {
            Log.e(TAG, "Font asset not found: " + fontPath, e);
        }
    }

    // 加载并应用自定义字体
    public static void applyCustomFont(Context context, TextView textView) {
        //int fontType = new Random().nextInt(3) + 1; // 生成1到3之间的随机数
        String fontPath = getFontPath(fontPathNum);
        try {
            Typeface customFont = Typeface.createFromAsset(context.getAssets(), fontPath);
            textView.setTypeface(customFont);
            Log.i(TAG, "Font asset found: " + fontPath);
        } catch (RuntimeException e) {
            Log.e(TAG, "Font asset not found: " + fontPath, e);
        }
    }

    // 根据传入的整数值返回对应的字体路径
    private static String getFontPath(int fontType) {
        switch (fontType) {
            case 1:
                return "fonts/dingtalk_jinbuti.ttf";                        //来源iconfont 钉钉进步体
            case 2:
                return "fonts/alimama_dongfangdakai_regular.ttf";           //来源iconfont 阿里巴巴东方体
            case 3:
                return "fonts/smileysans_oblique.ttf";                      //得意黑字体
            case 4:
                return "fonts/haohanyhfont_regular.otf";                    //浩瀚银河内测字体
            case 5:
                return "fonts/source_han_sans_sc_regular.otf";              //思源宋体
            case 6:
                return "fonts/taipeisanstcbeta_regular.ttf";                //台北黑体
            default:
                return "fonts/source_han_sans_sc_regular.otf";              //默认思源宋体
        }
    }
}
