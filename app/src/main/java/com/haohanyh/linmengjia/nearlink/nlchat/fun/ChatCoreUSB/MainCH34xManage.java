/* 受Haohanyh Computer Software Products Open Source LICENSE保护 https://github.com/Hny0305Lin/LICENSE/blob/main/LICENSE */
package com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCoreUSB;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.haohanyh.linmengjia.nearlink.nlchat.ch34x.CH34xUARTDriver;
import com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCore.ChatUtilsForSettings;
import com.haohanyh.linmengjia.nearlink.nlchat.fun.MainAPP;
import com.haohanyh.linmengjia.nearlink.nlchat.fun.Uart.WCHUartSettings;

/**
 * 管理MainActivity里对CH34x的控制前置类，该类为开发者使用类，个人使用请勿对此类进行修改，该类对CH34x的管理已经得到验证。
 *
 * <p>如需要在MainActivity或其他活动类里，初始化CH34x，请使用如下代码：</p>
 * <code>CH34xInit.MainCH34xInitStart(context);<br>CH34xInit.MainCH34xBroadCast(context);<br>NearLinkChatReadData();</code>
 * <p>如需要在MainActivity或其他活动类里，重启CH34x，请使用如下代码：</p>
 * <code>MainAPP.CH34X.closeDevice();<br>CH34xInit.MainCH34xRestartInit(context);<br>NearLinkChatReadData();</code>
 * <p>目前NLChat星闪聊天软件，为正常Android设备使用，仅支持单CH34x设备保证通讯和供电正常。</p>
 * <p>如您为Android Dev设备，有丰富的外设IO接口，和完全的底层SDK资料，请通过Issue或联系方式联系我，我会提供免费且永久的CH34x API定制维护，以用于丰富该项目底层和前置相关仓库代码。</p>
 *
 * <p>NearLinkChatReadData()为主函数星闪聊天软件启动CH34x Listener线程，对CH34x做修改时请主动重启该方法，以恢复监听串口。<p>
 * <p>以上代码，开发者可以在任何情况下，进行测试调用。更多可公开的CH34x前置API，会在未来进行更好的更新以让大家进行底层调试。<p>
 */
public class MainCH34xManage extends Application {
    private static final String TAG = "CH34x前置 & NLChat";

    private static final String ACTION_USB_PERMISSION = "cn.wch.wchusbdriver.USB_PERMISSION";               //沁恒厂商自编写USB权限
    private static final String USB_DEVICE_ATTACHED = "android.hardware.usb.action.USB_DEVICE_ATTACHED";    //安卓监听USB连接成功
    private static final String USB_DEVICE_DETACHED = "android.hardware.usb.action.USB_DEVICE_DETACHED";    //安卓监听USB断开
    private Handler handler = new Handler(Looper.getMainLooper());

    public void MainCH34xInitStart(Context context) {
        //创建CH34x设备对象
        MainAPP.CH34X = new CH34xUARTDriver(
                (UsbManager) context.getSystemService(Context.USB_SERVICE), context,
                ACTION_USB_PERMISSION);
        //判断是否支持SB HOSTU，可行则开始链接
        if (!MainAPP.CH34X.usbFeatureSupported()) {
            //不支持，弹出提示窗口
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    MainCH34xInitStart(context);
                }
            }, ChatUtilsForSettings.getAutoConnectDelay());
        } else {
            //打开USB设备
            int retval = MainAPP.CH34X.resumeUsbList();
            if (retval == -1) {
                //打开失败，关闭设备，循环继续直至能打开成功
                MainAPP.CH34X.closeDevice();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        MainCH34xInitStart(context);
                    }
                }, 3000); // 3000毫秒 = 3秒
            } else if (retval == 0) {//打开成功
                //初始化CH34x串口
                if (MainAPP.CH34X.uartInit()) {//初始化成功
                    if (MainAPP.CH34X.setConfig(WCHUartSettings.needGetData().getBaudRate(),
                            WCHUartSettings.needGetData().getDataBit(), WCHUartSettings.needGetData().getStopBit(),
                            WCHUartSettings.needGetData().getParity(), WCHUartSettings.needGetData().getFlowControl())) {
                        // 启动成功的处理,显示成功通过UART接入星网+设备支持USBHost+设备已连接串口
                        Log.d(TAG, "自动启动，已启动!");
                        MainCH34xOK();
                    } else {
                        // 启动失败的处理,显示未接入星网+设备支持USBHost+设备已移除串口
                        Log.d(TAG, "自动启动，未启动!");
                        MainCH34xDelUart();
                    }
                } else {
                    // 初始化失败的处理,显示未接入星闪+设备支持USBHost+串口配置出问题
                    Log.d(TAG, "自动启动，未初始化!");
                    MainCH34xWarn();
                }
            } else {
                // 其他情况的处理,显示未接入星闪+设备不支持USBHost+串口配置出问题
                Log.d(TAG, "自动启动，出现问题!");
                MainCH34xError();
            }
        }
    }


    @SuppressLint("InlinedApi")
    public void MainCH34xBroadCast(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(USB_DEVICE_ATTACHED);
        filter.addAction(ACTION_USB_PERMISSION);
        filter.addAction(USB_DEVICE_DETACHED);

        BroadcastReceiver usbReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (USB_DEVICE_ATTACHED.equals(action)) {
                    Log.d(TAG, "USB启动");
                } else if (ACTION_USB_PERMISSION.equals(action)) {
                    Log.d(TAG, "USB权限判断");
                    synchronized (CH34xUARTDriver.class) {
                        UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                        if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                            // Logcat
                            Log.d(TAG, "USB权限存在");
                        } else {
                            // Logcat
                            Log.d(TAG, "USB权限拒绝!");
                        }
                    }
                } else if (USB_DEVICE_DETACHED.equals(action)) {
                    UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    String deviceName = device.getDeviceName();
                    for (int i = 0; i < MainAPP.CH34X.getSupportTypeSize(); ++i) {
                        if (String.format("%04x:%04x", device.getVendorId(), device.getProductId()).equals(MainAPP.CH34X.getSupportVendorProduct().get(i))) {
                            // Logcat
                            Log.d(TAG, "USB已移除，断开连接!" + deviceName);
                            // USB移除的处理,显示未接入星网+设备支持USBHost+设备已移除串口
                            MainCH34xDelUart();
                        }
                    }
                } else {
                    // Logcat
                    Log.d(TAG, "没有匹配到USB设备!" + action);
                    // USB未匹配的处理,显示未接入星闪+设备不支持USBHost+串口配置出问题
                    MainCH34xError();
                }
            }
        };
        context.registerReceiver(usbReceiver, filter, Context.RECEIVER_NOT_EXPORTED);
    }

    public void MainCH34xRestartInit(Context context) {
        //先false
        MainCH34xRestartReady();
        //判断是否支持SB HOSTU，可行则开始链接
        if (MainAPP.CH34X.usbFeatureSupported()) {
            //打开USB设备
            int retval = MainAPP.CH34X.resumeUsbList();
            if (retval == -1) {
                //打开失败，关闭设备，循环继续直至能打开成功
                MainAPP.CH34X.closeDevice();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        MainCH34xInitStart(context);
                    }
                }, 3000); // 3000毫秒 = 3秒
            } else if (retval == 0) {//打开成功
                //初始化CH34x串口
                if (MainAPP.CH34X.uartInit()) {//初始化成功
                    if (MainAPP.CH34X.setConfig(WCHUartSettings.needGetData().getBaudRate(),
                            WCHUartSettings.needGetData().getDataBit(), WCHUartSettings.needGetData().getStopBit(),
                            WCHUartSettings.needGetData().getParity(), WCHUartSettings.needGetData().getFlowControl())) {
                        // 重新启动成功的处理,显示成功通过UART接入星网+设备支持USBHost+设备已连接串口
                        Log.d(TAG, "重新启动，已启动!");
                        MainCH34xOK();
                    } else {
                        // 重新启动失败的处理,显示未接入星网+设备支持USBHost+设备已移除串口
                        Log.d(TAG, "重新启动，未启动!");
                        MainCH34xDelUart();
                    }
                } else {
                    // 重新启动失败的处理,显示未接入星闪+设备支持USBHost+串口配置出问题
                    Log.d(TAG, "重新启动，未初始化!");
                    MainCH34xWarn();
                }
            } else {
                // 其他情况的处理,显示未接入星闪+设备不支持USBHost+串口配置出问题
                Log.d(TAG, "重新启动，出现问题!");
                MainCH34xError();
            }
        }
    }

    /**
     *
     */
    private void MainCH34xOK() {
        ChatUtilsForSettings.setAutoConnectListener(true);
        ChatUtilsForSettings.setAutoConnectMessageCode10(true);ChatUtilsForSettings.setAutoConnectMessageCode11(false);
        ChatUtilsForSettings.setAutoConnectMessageCode20(true);ChatUtilsForSettings.setAutoConnectMessageCode21(false);
        ChatUtilsForSettings.setAutoConnectMessageCode30(true);ChatUtilsForSettings.setAutoConnectMessageCode31(false);
        ChatUtilsForSettings.setAutoConnectMessageCode32(false);ChatUtilsForSettings.setAutoConnectMessageCode33(false);
    }

    private void MainCH34xDelUart() {
        ChatUtilsForSettings.setAutoConnectListener(false);
        ChatUtilsForSettings.setAutoConnectMessageCode10(false);ChatUtilsForSettings.setAutoConnectMessageCode11(true);
        ChatUtilsForSettings.setAutoConnectMessageCode20(true);ChatUtilsForSettings.setAutoConnectMessageCode21(false);
        ChatUtilsForSettings.setAutoConnectMessageCode30(false);ChatUtilsForSettings.setAutoConnectMessageCode31(false);
        ChatUtilsForSettings.setAutoConnectMessageCode32(true);ChatUtilsForSettings.setAutoConnectMessageCode33(false);
    }


    private void MainCH34xWarn() {
        ChatUtilsForSettings.setAutoConnectListener(false);
        ChatUtilsForSettings.setAutoConnectMessageCode10(false);ChatUtilsForSettings.setAutoConnectMessageCode11(true);
        ChatUtilsForSettings.setAutoConnectMessageCode20(true);ChatUtilsForSettings.setAutoConnectMessageCode21(false);
        ChatUtilsForSettings.setAutoConnectMessageCode30(false);ChatUtilsForSettings.setAutoConnectMessageCode31(false);
        ChatUtilsForSettings.setAutoConnectMessageCode32(false);ChatUtilsForSettings.setAutoConnectMessageCode33(true);
    }

    private void MainCH34xError() {
        ChatUtilsForSettings.setAutoConnectListener(false);
        ChatUtilsForSettings.setAutoConnectMessageCode10(false);ChatUtilsForSettings.setAutoConnectMessageCode11(true);
        ChatUtilsForSettings.setAutoConnectMessageCode20(false);ChatUtilsForSettings.setAutoConnectMessageCode21(true);
        ChatUtilsForSettings.setAutoConnectMessageCode30(false);ChatUtilsForSettings.setAutoConnectMessageCode31(false);
        ChatUtilsForSettings.setAutoConnectMessageCode32(false);ChatUtilsForSettings.setAutoConnectMessageCode33(true);
    }

    private void MainCH34xRestartReady() {
        ChatUtilsForSettings.setAutoConnectMessageCode10(false);ChatUtilsForSettings.setAutoConnectMessageCode11(false);
        ChatUtilsForSettings.setAutoConnectMessageCode20(false);ChatUtilsForSettings.setAutoConnectMessageCode21(false);
        ChatUtilsForSettings.setAutoConnectMessageCode30(false);ChatUtilsForSettings.setAutoConnectMessageCode31(false);
        ChatUtilsForSettings.setAutoConnectMessageCode32(false);ChatUtilsForSettings.setAutoConnectMessageCode33(false);
    }
}
