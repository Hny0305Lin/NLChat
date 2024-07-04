## [SLE串口透传测试](#sle串口透传测试)

该教程演示如何通过两块开发板实现SLE数据传输测试，A开发板通过串口接收数据，然后通过SLE传输给B开发板，B开发板通过串口将接收到的数据打印出来；同样，B开发板通过串口接收数据，然后通过SLE传输给A开发板，A开发板通过串口将接收到的数据打印出来。在测试中需要一块开发板做为Server端，另外一块开发板做为Client端，两块开发板配对后就可以互发消息了。

![图片](https://raw.githubusercontent.com/Hny0305Lin/NLChat/master/DevSummary/BearPi_Device_Configure_Tutorial/image-18-DoDrYUKD.png)

![图片](https://raw.githubusercontent.com/Hny0305Lin/NLChat/master/DevSummary/NLChat.jpg)

## [一、准备工作](#一、准备工作)

+   准备2块BearPi-Pico H2821开发板
+   准备2根USB Type-C双头数据线，方便开发板连入手机等设备
+   准备好硬件侧端项目，因为需要编译，[点我前往硬件侧端](https://github.com/Hny0305Lin/Bearpi_Hi2821_Pico_NLChat)

## [二、编译 SLE UART Server代码](#二、编译-sle-uart-server代码)

在Windows下编译操作

1.  点击左侧的“KConfig”,打开配置界面.
    
    ![Alt text](https://raw.githubusercontent.com/Hny0305Lin/NLChat/master/DevSummary/BearPi_Device_Configure_Tutorial/image-4-CqlRac9C.png)
    
2.  选择Enable SLE UART Server sample
    
    ```text
    Application  --->
        [*] Enable Sample.
        [ ]     Enable the Sample of peripheral.
        [*]     Enable the Sample of products.
        [ ]         Enable all the sample of product, it's just for build.
        [ ]         Support BLE UART sample.
        [*]         Support SLE UART sample.
                        SLE UART Sample Configuration  --->
                            Select sle uart type (Enable SLE UART Server sample.)  --->
                                    (X) Enable SLE UART Server sample.
                                    ( ) Enable SLE UART Client sample.
    ```
    
    ![Alt text](https://raw.githubusercontent.com/Hny0305Lin/NLChat/master/DevSummary/BearPi_Device_Configure_Tutorial/image-DjSheyV_.png)![Alt text](https://raw.githubusercontent.com/Hny0305Lin/NLChat/master/DevSummary/BearPi_Device_Configure_Tutorial/image-1-BBMTCLDP.png)
    
3.  选择协议
    
    ```text
    Protocol  --->
        Select sle ble support  --->
            ( ) Select sle ble central default.
            (X) Select sle ble peripheral.
            ( ) Select sle peripheral.
            ( ) Select sle central.
            ( ) Select ble peripheral.
    
    
    ```
    
    ![Alt text](https://raw.githubusercontent.com/Hny0305Lin/NLChat/master/DevSummary/BearPi_Device_Configure_Tutorial/image-2-DikMheki.png)
    
4.  按下"Save"键保存配置。
    
    ![Alt text](https://raw.githubusercontent.com/Hny0305Lin/NLChat/master/DevSummary/BearPi_Device_Configure_Tutorial/image-3-sb4fwZla.png)
    
5.  编译烧录固件
    
    参考[环境搭建](https://bearpi.cn/core_board/bearpi/pico/h2821/software/%E7%8E%AF%E5%A2%83%E6%90%AD%E5%BB%BAwindows.html)教程编译烧录代码
    

在Ubuntu下编译操作

1.  在MobaXterm中输入:
    
2.  选择Enable SLE UART Server sample
    
    ```text
    Application  --->
        [*] Enable Sample.
        [ ]     Enable the Sample of peripheral.
        [*]     Enable the Sample of products.
        [ ]         Enable all the sample of product, it's just for build.
        [ ]         Support BLE UART sample.
        [*]         Support SLE UART sample.
                        SLE UART Sample Configuration  --->
                            Select sle uart type (Enable SLE UART Server sample.)  --->
                                    (X) Enable SLE UART Server sample.
                                    ( ) Enable SLE UART Client sample.
    ```
    
    ![Alt text](https://raw.githubusercontent.com/Hny0305Lin/NLChat/master/DevSummary/BearPi_Device_Configure_Tutorial/image-DjSheyV_.png)![Alt text](https://raw.githubusercontent.com/Hny0305Lin/NLChat/master/DevSummary/BearPi_Device_Configure_Tutorial/image-1-BBMTCLDP.png)
    
3.  选择协议
    
    ```text
    Protocol  --->
        Select sle ble support  --->
            ( ) Select sle ble central default.
            (X) Select sle ble peripheral.
            ( ) Select sle peripheral.
            ( ) Select sle central.
            ( ) Select ble peripheral.
    
    
    ```
    
    ![Alt text](https://raw.githubusercontent.com/Hny0305Lin/NLChat/master/DevSummary/BearPi_Device_Configure_Tutorial/image-2-DikMheki.png)
    
4.  按下"ESC"键退出并保存配置。
    
5.  编译烧录固件
    
    参考[环境搭建](https://bearpi.cn/core_board/bearpi/pico/h2821/software/%E7%8E%AF%E5%A2%83%E6%90%AD%E5%BB%BAubuntu.html)教程编译烧录代码
    

## [三、编译 SLE UART Client代码](#三、编译-sle-uart-client代码)

在Windows下编译操作

1.  点击左侧的“KConfig”,打开配置界面.
    
    ![Alt text](https://raw.githubusercontent.com/Hny0305Lin/NLChat/master/DevSummary/BearPi_Device_Configure_Tutorial/image-4-CqlRac9C.png)
    
2.  选择Enable SLE UART Client sample
    
    ```text
    Application  --->
        [*] Enable Sample.
        [ ]     Enable the Sample of peripheral.
        [*]     Enable the Sample of products.
        [ ]         Enable all the sample of product, it's just for build.
        [ ]         Support BLE UART sample.
        [*]         Support SLE UART sample.
                        SLE UART Sample Configuration  --->
                            Select sle uart type (Enable SLE UART Server sample.)  --->
                                    ( ) Enable SLE UART Server sample.
                                    (X) Enable SLE UART Client sample.
    ```
    
    ![Alt text](https://raw.githubusercontent.com/Hny0305Lin/NLChat/master/DevSummary/BearPi_Device_Configure_Tutorial/image-DjSheyV_.png)![Alt text](https://raw.githubusercontent.com/Hny0305Lin/NLChat/master/DevSummary/BearPi_Device_Configure_Tutorial/image-4-D0rs0cxE.png)
    
3.  选择协议
    
    ```text
    Protocol  --->
        Select sle ble support  --->
            (X) Select sle ble central default.
            ( ) Select sle ble peripheral.
            ( ) Select sle peripheral.
            ( ) Select sle central.
            ( ) Select ble peripheral.
    
    
    ```
    
    ![Alt text](https://raw.githubusercontent.com/Hny0305Lin/NLChat/master/DevSummary/BearPi_Device_Configure_Tutorial/image-3-tVTLrmeU.png)
    
4.  按下"Save"键保存配置。
    
    ![Alt text](https://raw.githubusercontent.com/Hny0305Lin/NLChat/master/DevSummary/BearPi_Device_Configure_Tutorial/image-3-sb4fwZla.png)
    
5.  编译烧录固件
    
    参考[环境搭建](https://bearpi.cn/core_board/bearpi/pico/h2821/software/%E7%8E%AF%E5%A2%83%E6%90%AD%E5%BB%BAwindows.html)教程编译烧录代码
    

在Ubuntu下编译操作

1.  在MobaXterm中输入:
    
2.  选择Enable SLE UART Client sample
    
    ```text
    Application  --->
        [*] Enable Sample.
        [ ]     Enable the Sample of peripheral.
        [*]     Enable the Sample of products.
        [ ]         Enable all the sample of product, it's just for build.
        [ ]         Support BLE UART sample.
        [*]         Support SLE UART sample.
                        SLE UART Sample Configuration  --->
                            Select sle uart type (Enable SLE UART Server sample.)  --->
                                    ( ) Enable SLE UART Server sample.
                                    (X) Enable SLE UART Client sample.
    ```
    
    ![Alt text](https://github.com/Hny0305Lin/NLChat/blob/master/DevSummary/BearPi_Devichttps://raw.githubusercontent.com/Hny0305Lin/NLChat/master/DevSummary/BearPi_Device_Configure_Tutorial/image-DjSheyV_.png)![Alt text](https://raw.githubusercontent.com/Hny0305Lin/NLChat/master/DevSummary/BearPi_Device_Configure_Tutorial/image-4-D0rs0cxE.png)
    
3.  选择协议
    
    ```text
    Protocol  --->
        Select sle ble support  --->
            (X) Select sle ble central default.
            ( ) Select sle ble peripheral.
            ( ) Select sle peripheral.
            ( ) Select sle central.
            ( ) Select ble peripheral.
    
    
    ```
    
    ![Alt text](https://raw.githubusercontent.com/Hny0305Lin/NLChat/master/DevSummary/BearPi_Device_Configure_Tutorial/image-3-tVTLrmeU.png)
    
4.  按下"ESC"键退出并保存配置。
    
5.  编译烧录固件
    
    参考[环境搭建](https://bearpi.cn/core_board/bearpi/pico/h2821/software/%E7%8E%AF%E5%A2%83%E6%90%AD%E5%BB%BAubuntu.html)教程编译烧录代码
    

## [四、测试](#四、测试)

烧录固件后先启动server设备,再启动client设备，在串口工具的输入框中输入数据并发送，测试server和client设备之间的数据收发，如下图所示。

![Alt text](https://raw.githubusercontent.com/Hny0305Lin/NLChat/master/DevSummary/Screenshot_20240624_180105.png)

![Alt text](https://raw.githubusercontent.com/Hny0305Lin/NLChat/master/DevSummary/1719839124110.jpg)

![Alt text](https://raw.githubusercontent.com/Hny0305Lin/NLChat/master/DevSummary/1719938242362.jpg)
