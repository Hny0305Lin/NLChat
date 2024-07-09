/* 受Haohanyh Computer Software Products Open Source LICENSE保护 https://github.com/Hny0305Lin/LICENSE/blob/main/LICENSE */
package com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCore;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ChatUIAnimationUtils {
    // 定义静态全局变量来管理时间
    public static final long DURATION_SHORT = 1000; // 1秒渐变
    public static final double DURATION_LONG = 2022.1104; // 2秒左右渐变，星闪规范发布日

    public static void animateBackgroundColorChange(Context context, FloatingActionButton button, int startColorRes, int endColorRes) {
        int startColor = context.getResources().getColor(startColorRes);
        int endColor = context.getResources().getColor(endColorRes);

        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), startColor, endColor);
        colorAnimation.setDuration((long) DURATION_LONG); // 动画持续时间，单位为毫秒
        colorAnimation.setRepeatCount(ValueAnimator.INFINITE); // 无限循环
        colorAnimation.setRepeatMode(ValueAnimator.REVERSE); // 反转颜色渐变
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                button.setBackgroundTintList(ColorStateList.valueOf((int) animator.getAnimatedValue()));
            }
        });
        colorAnimation.start();
    }
}
