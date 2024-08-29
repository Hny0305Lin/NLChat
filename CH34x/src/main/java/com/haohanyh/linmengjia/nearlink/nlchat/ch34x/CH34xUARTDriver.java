/* 受Haohanyh Computer Software Products Open Source LICENSE保护 https://github.com/Hny0305Lin/LICENSE/blob/main/LICENSE */
/* 本源码为反编译代码，由浩瀚银河负责人译制并修改内容，二次开发请尊重浩瀚银河和上游作者版权，添加"浩瀚银河"署名和添加醒目文字以让用户知请使用反编译源码 */
package com.haohanyh.linmengjia.nearlink.nlchat.ch34x;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.hardware.usb.UsbRequest;
import android.widget.Toast;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

/* loaded from: CH34xUARTDriver.jar:cn/wch/ch34xuartdriver/CH34xUARTDriver.class */
/* 加载来自: CH34xUARTDriver.jar:cn/wch/ch34xuartdriver/CH34xUARTDriver.class */
public class CH34xUARTDriver {
    /* 系统上下文 */
    private Context mContext;

    /* USB管理器 */
    private UsbManager usbManager;

    /* 远程意图，处理USB广播和USB权限  */
    private PendingIntent pendingIntent;

    /* USB设备 */
    private UsbDevice usbDevice;

    /* USB接口 */
    private UsbInterface usbInterface;

    /* USB终点 */
    private UsbEndpoint usbEndpoint_E;

    /* USB终点 */
    private UsbEndpoint usbEndpoint_F;

    /* USB发送与接收控制器 */
    private UsbDeviceConnection usbDeviceConnection;

    /* USB权限标识（广播） */
    private String broadcastReceiverFilter;

    /* 读（接收）数据时同步（锁）对象 */
    private final Object readSynchronizedObject = new Object();

    /* 写（发送）数据时同步（锁）对象 */
    private final Object writeSynchronizedObject = new Object();

    /* 注册广播或者已经连接 */
    private boolean isRegisterReceiver = false;

    /* USB接口是否为空 */
    private boolean isNullUsb = false;

    /* USB读取线程 */
    private Ch34ReadThread readThread;

    /* 读（接收）容器 */
    private byte[] readBuffer = new byte[655360];

    /* 读（接收）容器 */
    private byte[] tempBuffer = new byte[8092];

    /* 最新拼接出来的下标 */
    private int newSpliceIndex = 0;
    
    /* 读到的下标_上次 */
    private int previousReadIndex = 0;

    /* 要读的长度_下次 */
    private int nextReadLength = 0;

    /* 芯片支持的USB转串口线集合 */
    private ArrayList supportVendorProduct = new ArrayList();

    /* 芯片支持的USB转串口种类个数 */
    private int supportTypeSize;

    /* 返回端点的最大数据包大小 */
    private int maxPacketSize;

    /* USB写（发送）数据的超时时间（增加的） */
    private int writeTimeOut = 10000;

    /* 初始化芯片默认超时（毫秒） */
    private int initTimeOut = 500;

    /* USB请求数据包的类 */
    UsbRequest[] usbRequests = new UsbRequest[this.REQUEST_COUNT];

    /* USB数据（读）byte[]缓冲区 */
    ByteBuffer[] byteBuffers = new ByteBuffer[this.REQUEST_COUNT];

    /* 同步（计数）信号量 */
    private Semaphore semaphore = new Semaphore(1);

    /* USB设备状态变化广播接收器 */
    private final BroadcastReceiver ch34BroadcastReceiver = new Ch34BroadcastReceiver(this);

    /* 每个USB请求数据包容量 */
    public final int _32 = 32;

    /* USB请求数据包个数 */
    public final int REQUEST_COUNT = 20;

    /* 字节监听提取 */
    private BytesExtract readBytes;

    /* 断开USB监听提取 */
    private Listener closeUsbListener;

    /* CH34x Chips */
    /* 来自: CH34XUARTDriver.jar:cn/wch/uartlib/chip/ChipType.class */
    private final CH34xChips chips = new CH34xChips();

    /**
     * 构造方法
     *
     * @param usbManager USB管理器
     * @param context    上下文
     * @param filter     广播过滤
     */
    public CH34xUARTDriver(UsbManager usbManager, Context context, String filter) {
        this.usbManager = usbManager;
        this.mContext = context;
        this.broadcastReceiverFilter = filter;
        this.addSupportVendorProduct(chips.getCH340());           //CH340     小熊派（在此项目
        this.addSupportVendorProduct(chips.getCH340K());          //CH340     润和（在此项目
        this.addSupportVendorProduct(chips.getCH341A_1());        //CH341     Serial mode
        this.addSupportVendorProduct(chips.getCH341A());          //CH341     EPP/MEM/I2C mode
    }

    /**
     * 枚举 CH34x 设备
     *
     * @return 返回枚举到的 CH34x 的设备，若无设备则返回 null
     * @see #resumeUsbList() 已经实现
     */
    public UsbDevice enumerateDevice() {
        this.usbManager = (UsbManager) this.mContext.getSystemService(Context.USB_SERVICE);
        this.pendingIntent = PendingIntent.getBroadcast(this.mContext, 0, new Intent(this.broadcastReceiverFilter), 0);
        HashMap<String, UsbDevice> deviceList = this.usbManager.getDeviceList();
        if (deviceList.isEmpty()) {
            Toast.makeText(this.mContext, "没有设备或设备不匹配! No Device Or Device Not Match!", Toast.LENGTH_LONG).show();
            return null;
        }
        for (UsbDevice usbDevice : deviceList.values()) {
            for (int i = 0; i < this.supportTypeSize; i++) {
                if (String.format("%04x:%04x", usbDevice.getVendorId(), usbDevice.getProductId()).equals(this.supportVendorProduct.get(i))) {   //判断什么USB芯片设备
                    IntentFilter intentFilter = new IntentFilter(this.broadcastReceiverFilter);
                    intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_DETACHED");
                    this.mContext.registerReceiver(this.ch34BroadcastReceiver, intentFilter);
                    this.isRegisterReceiver = true;
                    return usbDevice;
                }
            }
        }
        return null;
    }

    /**
     * 打开 CH34x 设备
     *
     * @param usbDevice 需要打开的 CH34x 设备
     * @see #resumeUsbList() 已经实现
     */
    public void openDevice(UsbDevice usbDevice) {
        this.pendingIntent = PendingIntent.getBroadcast(this.mContext, 0, new Intent(this.broadcastReceiverFilter), 0);
        if (this.usbManager.hasPermission(usbDevice)) {
            connectionDevice(usbDevice);
        } else {
            synchronized (this.ch34BroadcastReceiver) {
                this.usbManager.requestPermission(usbDevice, this.pendingIntent);
            }
        }
    }

    /**
     * 页面重启时检查权限
     */
    public int resumeUsbList() {
        this.usbManager = (UsbManager) this.mContext.getSystemService(Context.USB_SERVICE);
        this.pendingIntent = PendingIntent.getBroadcast(this.mContext, 0, new Intent(this.broadcastReceiverFilter), PendingIntent.FLAG_IMMUTABLE);
        HashMap<String, UsbDevice> deviceList = this.usbManager.getDeviceList();
        if (deviceList.isEmpty()) {
            Toast.makeText(this.mContext, "没有设备或设备不匹配! No Device Or Device Not Match!", Toast.LENGTH_LONG).show();
        }
        for (UsbDevice usbDevice : deviceList.values()) {
            for (int i = 0; i < this.supportTypeSize; i++) {
                if (String.format("%04x:%04x", usbDevice.getVendorId(), usbDevice.getProductId()).equals(this.supportVendorProduct.get(i))) {
                    IntentFilter intentFilter = new IntentFilter(this.broadcastReceiverFilter);
                    intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_DETACHED");
                    this.mContext.registerReceiver(this.ch34BroadcastReceiver, intentFilter);
                    this.isRegisterReceiver = true;
                    if (this.usbManager.hasPermission(usbDevice)) {
                        connectionDevice(usbDevice);
                        return 0;
                    }
                    Toast.makeText(this.mContext, "没有权限! No Perssion!", Toast.LENGTH_LONG).show();
                    return -2;
                }
            }
        }
        return -1;
    }

    /**
     * 设置初始化CH34x芯片
     *
     * @return 若初始化失败，则返回 false，成功返回 true
     */
    public boolean uartInit() {
        byte[] var = new byte[8];
        this.controlTransfer(161, 0, 0);
        if (controlTransfer(95, 0, 0, var, 2) < 0) {
            return false;
        } else {
            this.controlTransfer(154, 4882, 55682);
            this.controlTransfer(154, 3884, 4);
            if (controlTransfer(149, 9496, 0, var, 2) < 0) {
                return false;
            } else {
                this.controlTransfer(154, 10023, 0);
                this.controlTransfer(164, 255, 0);
                return true;
            }
        }
    }

    /**
     * 设置 UART 接口的波特率、数据位、停止位、奇偶校验位以及流控 。
     *
     * @param baudRate    波特率                       (300，600，1200、2400、4800、9600、19200、38400、57600、115200、230400、460800、921600)
     *                                                (华为星闪设备默认是115200，其余默认应该是9600)
     *                                                (该软件UI里仅支持调整至如下波特率：
     *                                                  <item>4800</item>
     *                                                  <item>9600</item>
     *                                                  <item>19200</item>
     *                                                  <item>38400</item>
     *                                                  <item>57600</item>
     *                                                  <item>115200</item>
     *                                                  <item>921600</item>
     *                                                (如上举例的其余的波特率，代码里可以设置，UI里无法设置）
     *
     * @param dataBits    数据位                       (5 个数据位、6 个数据位、7 个数据位、8 个数据位）
     *                                                (默认为8）
     * @param stopBits    停止位                       (1 个停止位，2 个停止位）
     *                                                (默认为1)
     * @param parity      校验位                       (0：none，1：add，2：even，3：mark 和 4：space)
     *                                                (默认为0)
     * @param flowControl 串口流控制，也称流控            (0：none，1：cts/rts)
     *                                                (默认为0)
     * @return 若设置失败，则返回 false，成功返回 true
     */
    public boolean setConfig(int baudRate, byte dataBits, byte stopBits, byte parity, byte flowControl) {
        char c;
        char c2;
        int i2;
        int i3;
        switch (parity) {
            case 0:
                c = 0;
                break;
            case 1:
                c = '\b';
                break;
            case 2:
                c = 24;
                break;
            case 3:
                c = '(';
                break;
            case 4:
                c = '8';
                break;
            default:
                c = 0;
                break;
        }
        if (stopBits == 2) {
            c = (char) (c | 4);
        }
        switch (dataBits) {
            case 5:
                c2 = c;
                break;
            case 6:
                c2 = (char) (c | 1);
                break;
            case 7:
                c2 = (char) (c | 2);
                break;
            case 8:
                c2 = (char) (c | 3);
                break;
            default:
                c2 = (char) (c | 3);
                break;
        }
        int i4 = 156 | (((char) (c2 | 192)) << '\b');
        switch (baudRate) {
            case 50:
                i2 = 0;
                i3 = 22;
                break;
            case 75:
                i2 = 0;
                i3 = 100;
                break;
            case 110:
                i2 = 0;
                i3 = 150;
                break;
            case 135:
                i2 = 0;
                i3 = 169;
                break;
            case 150:
                i2 = 0;
                i3 = 178;
                break;
            case 300:
                i2 = 0;
                i3 = 217;
                break;
            case 600:
                i2 = 1;
                i3 = 100;
                break;
            case 1200:
                i2 = 1;
                i3 = 178;
                break;
            case 1800:
                i2 = 1;
                i3 = 204;
                break;
            case 2400:
                i2 = 1;
                i3 = 217;
                break;
            case 4800:
                i2 = 2;
                i3 = 100;
                break;
            case 9600:
                i2 = 2;
                i3 = 178;
                break;
            case 19200:
                i2 = 2;
                i3 = 217;
                break;
            case 38400:
                i2 = 3;
                i3 = 100;
                break;
            case 57600:
                i2 = 3;
                i3 = 152;
                break;
            case 115200:
                i2 = 3;
                i3 = 204;
                break;
            case 230400:
                i2 = 3;
                i3 = 230;
                break;
            case 460800:
                i2 = 3;
                i3 = 243;
                break;
            case 500000:
                i2 = 3;
                i3 = 244;
                break;
            case 921600:
                i2 = 7;
                i3 = 243;
                break;
            case 1000000:
                i2 = 3;
                i3 = 250;
                break;
            case 2000000:
                i2 = 3;
                i3 = 253;
                break;
            case 3000000:
                i2 = 3;
                i3 = 254;
                break;
            default:
                i2 = 2;
                i3 = 178;
                break;
        }
        int m2a = controlTransfer(161, i4, 0 | 136 | i2 | (i3 << 8));
        if (flowControl == 1) {
            controlTransfer(164, 96 ^ (-1), 0);
        }
        return m2a >= 0;
    }

    /**
     * 发送数据
     *
     * @param data       数据（发送缓冲区）
     * @param dataLength 数据长度（发送的字节数）
     * @return 返回值为写成功的字节数。是否成功（返回传输数据的长度（或0）表示成功，或返回负值表示失败）
     */
    public int writeData(byte[] data, int dataLength) {
        int result = -1;
        try {
            result = this.writeData(data, dataLength, this.writeTimeOut);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            Toast.makeText(this.mContext, "发送数据出错! 如下:" + throwable.getMessage(), Toast.LENGTH_LONG).show();
        }
        return result;
    }

    /**
     * 发送数据
     *
     * @param data       数据（发送缓冲区）
     * @param dataLength 数据长度（发送的字节数）
     * @param timeOut    USB写（发送）数据的超时时间
     * @return 是否成功（返回传输数据的长度（或0）表示成功，或返回负值表示失败）
     */
    public int writeData(byte[] data, int dataLength, int timeOut) {
        synchronized (this.writeSynchronizedObject) {
            int i3 = 0;
            int i4 = dataLength;
            if (this.usbEndpoint_F == null) {
                return -1;
            }
            while (i3 < dataLength) {
                int min = Math.min(i4, this.maxPacketSize);
                byte[] bArr2 = new byte[min];
                if (i3 == 0) {
                    System.arraycopy(data, 0, bArr2, 0, min);
                } else {
                    System.arraycopy(data, i3, bArr2, 0, min);
                }
                int bulkTransfer = this.usbDeviceConnection.bulkTransfer(this.usbEndpoint_F, bArr2, min, timeOut);
                if (bulkTransfer < 0) {
                    return -2;
                }
                i3 += bulkTransfer;
                i4 -= bulkTransfer;
            }
            return i3;
        }
    }

    /**
     * 读取（接收）数据
     *
     * @param dataBuffer 用户的接收容器（接收缓冲区，数据类型为byte）
     * @param dataLength 数据长度（读取的字节数）
     * @return 是否成功。返回实际读取的字节数
     */
    public int readData(byte[] dataBuffer, int dataLength) {
        synchronized (this.readSynchronizedObject) {
            try {
                this.semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (dataLength <= 0 || this.nextReadLength == 0) {
                this.semaphore.release();
                return 0;
            }
            if (dataLength > this.nextReadLength) {
                dataLength = this.nextReadLength;
            }
            this.nextReadLength -= dataLength;
            for (int i3 = 0; i3 < dataLength; i3++) {
                dataBuffer[i3] = this.readBuffer[this.previousReadIndex];
                this.previousReadIndex++;
                this.previousReadIndex %= 655360;
            }
            this.semaphore.release();
            return dataLength;
        }
    }

    /**
     * 关闭（串口）USB设备
     */
    public void closeDevice() {
        //监听
        if (closeUsbListener != null) {
            Listener temp = closeUsbListener;
            setCloseListener(null);
            temp.value();
        }

        //释放USB接口；关闭USB设备连接器
        if (this.usbDeviceConnection != null) {
            if (this.usbInterface != null) {
                this.usbDeviceConnection.releaseInterface(this.usbInterface);
                this.usbInterface = null;
            }
            this.usbDeviceConnection.close();
        }

        //停止读取线程
        setReadListener(null);
        if (readThread != null) {
            readThread.interrupt();
            readThread = null;
        }

        //设置空USB接入
        if (this.usbDevice != null) {
            this.usbDevice = null;
        }

        //设置空USB管理器
        if (this.usbManager != null) {
            this.usbManager = null;
        }

        //判断是否已经连接，否就不连接
        if (this.isNullUsb) {
            this.isNullUsb = false;
        }

        //注销广播
        if (this.isRegisterReceiver) {
            this.mContext.unregisterReceiver(this.ch34BroadcastReceiver);
            this.isRegisterReceiver = false;
        }
    }

    /**
     * 判断USB是否已连接。判断设备是否已经连接到 Android 系统
     *
     * @return 是否连。返回为 false 时表示设备未连接到系统，true 表示设备已连接
     */
    public boolean isConnected() {
        return this.usbDevice != null && this.usbInterface != null && this.usbDeviceConnection != null;
    }

    /**
     * 设置超时。根据自己的设备来设置读写超时时间
     *
     * @param timeOut 设置写超时时间，默认为 10000ms。USB写（发送）数据的超时时间
     * @param i       设置读超时时间，默认为 10000ms。
     * @return 是否成功
     */
    public boolean setTimeOut(int timeOut, int i) {
        this.writeTimeOut = timeOut;
        return true;
    }

    /**
     * 添加支持的供应商ID和产品ID
     *
     * @param vendorAndProduct 供应商ID和产品ID
     *
     *                         目前解决了润和WS63、小熊派Hi3863芯片
     *                         如果设备无法完成初始化，目前最佳的解决办法，如果您的板子设备使用了CH340/CH341 USB芯片，请查询沁恒资料，获取板子相关VID PID或主动使用沁恒官方工具查询
     *                         润和VID PID：1A86 7522（CH340K
     *                         小熊派VID PID：1A86 7523(CH340
     *
     *                         1A86代指WCH沁恒USB产品
     *                         PID请具体查询
     *                         可参考资料：
     *                         <p>https://devicehunt.com/all-usb-vendors</p>
     *                         <p>https://devicehunt.com/view/type/usb/vendor/1A86/device/7522</p>
     *                         <p>https://devicehunt.com/view/type/usb/vendor/1A86/device/7523</p>
     *                         可使用沁恒工具配置软件查询单板：
     *                         <p>https://www.wch.cn/downloads/CH34xSerCfg_ZIP.html</p>
     *                         可在沁恒官方Jar库中，定位到CH34XUARTDriver.jar:cn/wch/uartlib/chip/ChipType.class，查询板子设备使用的USB芯片对应的十进制码，手动转换至十六进制码填写
     *                         例如：
     *                         this.addSupportVendorProduct("1a86:7522");
     *                         对应CH34XUARTDriver.jar:cn/wch/uartlib/chip/ChipType.class里的：
     *                         public enum ChipType {
     *                              CH340K(6790, 29986, 1, "CH340K"),
     *                         }
     *                         如上6790对应VID，是1A86；29986对应PID，是7522
     */
    private void addSupportVendorProduct(String vendorAndProduct) {
        this.supportVendorProduct.add(vendorAndProduct);
        this.supportTypeSize = this.supportVendorProduct.size();
    }

    /**
     * 创建新的USB设备、USB接口、声明连接；启动接收（读）的线程
     *
     * @param usbDevice USB设备。
     */
    public void connectionDevice(UsbDevice usbDevice) {
        UsbInterface usbInterface;
        UsbDeviceConnection openDevice;
        boolean z;
        if (usbDevice == null) {
            return;
        }
        if (this.usbDeviceConnection != null) {
            if (this.usbInterface != null) {
                this.usbDeviceConnection.releaseInterface(this.usbInterface);
                this.usbInterface = null;
            }
            this.usbDeviceConnection.close();
            this.usbDevice = null;
            this.usbInterface = null;
        }
        if (usbDevice != null) {
            int i = 0;
            while (true) {
                if (i >= usbDevice.getInterfaceCount()) {
                    usbInterface = null;
                    break;
                }
                UsbInterface usbInterface2 = usbDevice.getInterface(i);
                if (usbInterface2.getInterfaceClass() == 255 && usbInterface2.getInterfaceSubclass() == 1 && usbInterface2.getInterfaceProtocol() == 2) {
                    usbInterface = usbInterface2;
                    break;
                }
                i++;
            }
        } else {
            usbInterface = null;
        }
        UsbInterface usbInterface3 = usbInterface;
        if (usbDevice == null || usbInterface3 == null || (openDevice = this.usbManager.openDevice(usbDevice)) == null || !openDevice.claimInterface(usbInterface3, true)) {
            return;
        }
        this.usbDevice = usbDevice;
        this.usbDeviceConnection = openDevice;
        this.usbInterface = usbInterface3;
        if (usbInterface3 == null) {
            z = false;
        } else {
            for (int numberOfUSBPotrs = 0; numberOfUSBPotrs < usbInterface3.getEndpointCount(); numberOfUSBPotrs++) {
                UsbEndpoint endpoint = usbInterface3.getEndpoint(numberOfUSBPotrs);
                if (endpoint.getType() == 2 && endpoint.getMaxPacketSize() == 32) {
                    /*
                     * getDirection()方法为返回端点的方向。
                     * 如果方向是主机到设备，则返回{@link UsbConstants#USB_DIR_OUT}，如果方向是设备到主机，则返回{@link UsbConstants#USB_DIR_IN}。
                     *
                     * @see UsbConstants#USB_DIR_IN     128
                     * @see UsbConstants#USB_DIR_OUT    0
                     * @返回端点的方向
                     */
                    if (endpoint.getDirection() == UsbConstants.USB_DIR_IN) {
                        this.usbEndpoint_E = endpoint;
                    } else {
                        this.usbEndpoint_F = endpoint;
                    }
                    this.maxPacketSize = endpoint.getMaxPacketSize();
                } else {
                    endpoint.getType();
                }
            }
            z = true;
        }
        if (z) {
            Toast.makeText(this.mContext, "设备已连接至 Android! Device Has Attached to Android! ", Toast.LENGTH_LONG).show();
            if (this.isNullUsb) {
                return;
            }
            this.isNullUsb = true;
            this.readThread = new Ch34ReadThread(this, this.usbEndpoint_E, this.usbDeviceConnection);
            this.readThread.setListener(readBytes);
            this.readThread.start();
        }
    }

    /**
     * USB模式是否支持
     * @return 是否支持
     */
    public boolean usbFeatureSupported() {
        return this.mContext.getPackageManager().hasSystemFeature("android.hardware.usb.host");
    }

    /**
     * 配置使用USB的UART设置，该函数常被UartInit()调用
     * Google开源文档对此相关近似函数的解释：在该设备的端点零上执行控制事务。传输的方向由请求类型决定。如果 requestType & {@link UsbConstants#USB_ENDPOINT_DIR_MASK} 为 {@link UsbConstants#USB_DIR_OUT}，则传输为写入；如果为 {@link UsbConstants#USB_DIR_IN}，则传输为读取。
     * <p>
     * 来源：<a href="https://android.googlesource.com/platform/frameworks/base/+/master/core/java/android/hardware/usb/UsbDeviceConnection.java">点我</a>
     * <p>
     * 可参考资料：
     * <a href="https://stackoverflow.com/questions/52542934/android-usb-understanding-controltransfer-method">点我前往Stackoverflow</a>
     * <a href="https://blog.csdn.net/gd6321374/article/details/78014255">点我前往CSDN</a>
     */
    private int controlTransfer(int i, int i2, int i3) {
        return this.usbDeviceConnection.controlTransfer(64, i, i2, i3, null, 0, this.initTimeOut);
    }

    /**
     * 配置使用USB的UART设置，设备使用控制管道和端点，该函数常被UartInit()调用
     * Google开源文档对此相关近似函数的解释：在该设备的端点零上执行控制事务。传输的方向由请求类型决定。如果 requestType & {@link UsbConstants#USB_ENDPOINT_DIR_MASK} 为 {@link UsbConstants#USB_DIR_OUT}，则传输为写入；如果为 {@link UsbConstants#USB_DIR_IN}，则传输为读取。
     * <p>
     * 来源：<a href="https://android.googlesource.com/platform/frameworks/base/+/master/core/java/android/hardware/usb/UsbDeviceConnection.java">点我</a>
     * <p>
     * 可参考资料：
     * <a href="https://stackoverflow.com/questions/52542934/android-usb-understanding-controltransfer-method">点我前往Stackoverflow</a>
     * <a href="https://blog.csdn.net/gd6321374/article/details/78014255">点我前往CSDN</a>
     */
    private int controlTransfer(int i, int i2, int i3, byte[] bArr, int i4) {
        return this.usbDeviceConnection.controlTransfer(192, i, i2, i3, bArr, 2, this.initTimeOut);
    }

    /**
     * 获取USB发送与接收控制器
     * @return 控制器
     */
    public UsbDeviceConnection getUsbDeviceConnection() {
        return usbDeviceConnection;
    }

    /**
     * 获取USB所有请求数据包
     * @return 所有请求数据包
     */
    public UsbRequest[] getUsbRequests() {
        return usbRequests;
    }

    /**
     * 获取USB接收（读）缓冲区
     * @return 缓冲区byte数组
     */
    public ByteBuffer[] getByteBuffers() {
        return byteBuffers;
    }

    /**
     * 获取获取当前USB设备
     * @return USB设备
     */
    protected UsbDevice getUsbDevice() {
        return this.usbDevice;
    }

    /**
     * 获取上下文
     * @return 上下文
     */
    public Context getContext() {
        return mContext;
    }

    /**
     * 判断USB接口是否打开
     * @return 是否打开
     */
    public boolean isNullUsb() {
        return isNullUsb;
    }

    /**
     * 获取芯片支持的USB转串口种类个数
     * @return 种类个数
     */
    public int getSupportTypeSize() {
        return supportTypeSize;
    }

    /**
     * 获取芯片支持的USB转串口线集合线集合
     * @return 支持的串口线集合
     */
    public ArrayList getSupportVendorProduct() {
        return supportVendorProduct;
    }

    /**
     * 获取获取权限标识
     * @return 权限标识
     */
    public String getBroadcastReceiverFilter() { return broadcastReceiverFilter == null ? "" : broadcastReceiverFilter; }

    /**
     * 获取同步（计数）信号量
     * @return 信号量
     */
    public Semaphore getSemaphore() {
        return semaphore;
    }

    /** 数据监听函数 */
    public void setReadListener(BytesExtract readBytes) {
        if (readThread != null) readThread.setListener(readBytes);
        this.readBytes = readBytes;
    }

    /** USB断开监听函数 */
    public void setCloseListener(Listener closeUsbListener) {
        this.closeUsbListener = closeUsbListener;
    }

    /**
     * 获取读（接收）缓冲区
     * @return 取读（接收）byte数组
     */
    public byte[] getReadBuffer() { return readBuffer; }

    /**
     * 获取上次读到的下标（已经返回给用户）
     * @return 下标
     */
    public int getPreviousReadIndex() { return previousReadIndex; }

    /**
     * 获取读线程最新拼接出来的下标
     * @return 下标
     */
    public int getNewSpliceIndex() { return newSpliceIndex; }

    /**
     * 获取最新拼接出来的下标
     * @param newSpliceIndex 下标
     */
    public void setNewSpliceIndex(int newSpliceIndex) { this.newSpliceIndex = newSpliceIndex; }

    /**
     * 获取要读的长度_下次 = 最新拼接 - 上次读到（已经返回给用户）
     * @param nextReadLength 长度
     */
    public void setNextReadLength(int nextReadLength) { this.nextReadLength = nextReadLength; }
}
