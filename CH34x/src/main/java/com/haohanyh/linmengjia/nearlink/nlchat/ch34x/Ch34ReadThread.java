/* 受Haohanyh Computer Software Products Open Source LICENSE保护 https://github.com/Hny0305Lin/LICENSE/blob/main/LICENSE */
/* 本源码为反编译代码，由浩瀚银河负责人译制并修改内容，二次开发请尊重浩瀚银河和上游作者版权，添加"浩瀚银河"署名和添加醒目文字以让用户知请使用反编译源码 */
package com.haohanyh.linmengjia.nearlink.nlchat.ch34x;

import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbRequest;
import android.util.Log;

import java.nio.ByteBuffer;
import java.util.Arrays;

/* 重命名来自: cn.wch.ch34xuartdriver.b */
/* 加载来自: CH34xUARTDriver.jar:cn/wch/ch34xuartdriver/b.class */
public final class Ch34ReadThread extends Thread {
    private static final String TAG = "CH34xUART驱动 & NLChat";
    /* USB端点 */
    private UsbEndpoint usbEndpoint;

    /* USB接收控制器，此类用于向USB设备发送和接收数据以及控制消息。 */
    private UsbDeviceConnection usbDeviceConnection;

    /* 厂家芯片操作主类 */
    private CH34xUARTDriver ch34xUARTDriver;

    /* 字节缓冲区 */
    //private ByteBuffer[] byteBuffers = new ByteBuffer[ch34xUARTDriver.REQUEST_COUNT];

    /* 字节监听提取接口 */
    private BytesExtract bytesExtract;

    /* 构造方法 */
    public Ch34ReadThread(CH34xUARTDriver cH34xUARTDriver, UsbEndpoint usbEndpoint, UsbDeviceConnection usbDeviceConnection) {
        super();
        this.ch34xUARTDriver = cH34xUARTDriver;
        this.usbEndpoint = usbEndpoint;
        this.usbDeviceConnection = usbDeviceConnection;
        for (int i = 0; i < this.ch34xUARTDriver.REQUEST_COUNT; i++) {
            cH34xUARTDriver.getUsbRequests()[i] = new UsbRequest();
            cH34xUARTDriver.getUsbRequests()[i].initialize(this.usbDeviceConnection, this.usbEndpoint);

            cH34xUARTDriver.getByteBuffers()[i] = ByteBuffer.allocate(this.ch34xUARTDriver._32);
        }
        this.setPriority(10);
    }

    /* JADX WARN: Incorrect condition in loop: B:39:0x00fa */
    /* JADX WARN: Incorrect condition in loop: B:7:0x019b */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v21, types: [int] */
    /* JADX WARN: Type inference failed for: r0v46, types: [java.util.concurrent.Semaphore] */
    /* JADX WARN: Type inference failed for: r0v58 */
    /* JADX WARN: Type inference failed for: r0v7, types: [int] */
    @Override // java.lang.Thread, java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void run() {
        super.run();

        for (int i = 0; i < this.ch34xUARTDriver.REQUEST_COUNT; ++i) {
            /*
             * 读取数据，初始化32位到缓冲区
             *
             * UsbRequest#queue()：将请求排队以在其端点上发送或接收数据。
             *
             * <p>对于OUT端点，给定的缓冲区数据将在端点上发送。对于IN端点，端点将尝试将给定数量的字节读取到指定的缓冲区中。
             * 如果排队操作成功，则返回true。结果将通过{@link UsbDeviceConnection#requestWait}返回
             * </p>
             * @param buffer 该缓冲区包含要写入的字节或用于存储读取结果的位置。
             *               位置和数组偏移将被忽略，并假定为0。极限和容量将被忽略。
             *               一旦请求{@link UsbDeviceConnection#requestWait()得到处理}，位置将被设置为读/写的字节数。
             * @param length 要读取或写入的字节数。在{@value Build.VERSION_CODES#P}之前，
             *               大于16384字节的值将被截断为16384。在API {@value Build.VERSION_CODES#P}中及之后的任何长度值都是有效的。
             * @return true，如果排队操作成功
             * @deprecated 已过时， {@link UsbRequest#queue(ByteBuffer)} 代替。
             */
            this.ch34xUARTDriver.getUsbRequests()[i].queue(this.ch34xUARTDriver.getByteBuffers()[i], this.ch34xUARTDriver._32);
        }
        int count = 0;
        root:
        while (!Thread.interrupted()) {
            do {
                //没有打开USB则返回
                if (!this.ch34xUARTDriver.isNullUsb()) break;
            } while (this.usbEndpoint == null && !Thread.interrupted());

            for (int i = 0; i < this.ch34xUARTDriver.REQUEST_COUNT; ++i) {
                UsbRequest usbRequest = this.usbDeviceConnection.requestWait();
                UsbRequest usbRequestI = this.ch34xUARTDriver.getUsbRequests()[i];
                if (!this.ch34xUARTDriver.isNullUsb() && count++ > 20) {
                    Log.v(TAG, "USB已经关闭");
                    break root;
                }
                if (usbRequest == null) break;
                if (usbRequest != usbRequestI) {
                    continue;
                }
                count = 0;
                byte[] temp = this.ch34xUARTDriver.getByteBuffers()[i].array();
                int tempLength = this.ch34xUARTDriver.getByteBuffers()[i].position();
                if (tempLength > 0) {
                    try {
                        this.ch34xUARTDriver.getSemaphore().acquire();
                        byte[] bytes = Arrays.copyOf(temp, tempLength);

                        if (bytesExtract != null) bytesExtract.value(bytes);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        this.ch34xUARTDriver.getSemaphore().release();
                    }
                }
                // 将请求重新排队
                this.ch34xUARTDriver.getUsbRequests()[i].queue(this.ch34xUARTDriver.getByteBuffers()[i], this.ch34xUARTDriver._32);
            }
        }
        /*
            Method dump skipped, instructions count: 415
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        //throw new UnsupportedOperationException("Method not decompiled: cn.wch.ch34xuartdriver.C0001b.run():void");
    }

    /**
     *
     *
     * @param bytesExtract
     */
    public void setListener(BytesExtract bytesExtract) {
        this.bytesExtract = bytesExtract;
    }
}
