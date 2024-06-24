/* 受Haohanyh Computer Software Products Open Source LICENSE保护 https://github.com/Hny0305Lin/LICENSE/blob/main/LICENSE */
/* 本源码为反编译代码，由浩瀚银河负责人译制并修改内容，二次开发请尊重浩瀚银河和上游作者版权，添加"浩瀚银河"署名和添加醒目文字以让用户知请使用反编译源码 */
package com.haohanyh.linmengjia.nearlink.nlchat.ch34x;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.util.Log;

/* 重命名来自: cn.wch.ch34xuartdriver.a */
/* 加载来自: CH34xUARTDriver.jar:cn/wch/ch34xuartdriver/a.class */
final class Ch34BroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "CH34xUART驱动 & NLChat";

    private CH34xUARTDriver ch34xUARTDriver;

    /* JADX INFO: Access modifiers changed from: package-private */
    public Ch34BroadcastReceiver(CH34xUARTDriver cH34xUARTDriver) {
        super();
        this.ch34xUARTDriver = cH34xUARTDriver;
    }

    /* 广播的代码，实质上是解决Android设备调用系统底层的USB权限这一块，相当于获取权限的内容在这里面已经实现了，外面实现的是应用主动获取的相关正确权限 */
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        //首先得监测设备是否接入USB，代码的四种状态就是判断后的结果
        if ("android.hardware.usb.action.USB_DEVICE_ATTACHED".equals(action)) {                     //接入状态
            Log.d(TAG, "USB启动");

        } else if (this.ch34xUARTDriver.getBroadcastReceiverFilter().equals(action)) {              //弹窗状态
            Log.d(TAG, "USB权限判断");
            Class CH34X = CH34xUARTDriver.class;
            synchronized (CH34X) {
                UsbDevice u = intent.getParcelableExtra("device");
                if (intent.getBooleanExtra("permession", false)) {
                    Log.d(TAG, "USB权限存在");
                    this.ch34xUARTDriver.openDevice(u);
                } else {
                    Log.d(TAG, "USB权限拒绝!");
                }
            }

        } else if ("android.hardware.usb.action.USB_DEVICE_DEATTACHED".equals(action)) {            //移除状态
            UsbDevice u = intent.getParcelableExtra("device");
            String string = u.getDeviceName();
            for (int i = 0; i < this.ch34xUARTDriver.getSupportTypeSize(); ++i) {
                if (String.format("%04x:%04x", u.getVendorId(), u.getProductId()).equals(this.ch34xUARTDriver.getSupportVendorProduct().get(i))) {
                    Log.d(TAG, "USB已移除，断开连接!" + string);
                    this.ch34xUARTDriver.closeDevice();
                }
            }

        } else {                                                                                    //未知状态
            Log.d(TAG, "没有匹配到USB设备!" + action);
        }
        /*
            Method dump skipped, instructions count: 252
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        //throw new UnsupportedOperationException("Method not decompiled: cn.wch.ch34xuartdriver.C0000a.onReceive(android.content.Context, android.content.Intent):void");
    }
}
