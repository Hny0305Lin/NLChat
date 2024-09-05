/* 受Haohanyh Computer Software Products Open Source LICENSE保护 https://github.com/Hny0305Lin/LICENSE/blob/main/LICENSE */
package com.haohanyh.linmengjia.nearlink.nlchat.fun.WCHUart;

public class WCHUartSettings {
    //小熊派的星闪开发板，如果没有主动手动修改的话，默认都是115200波特率、8数据位、1停止位、0校验和0流控，故这里代码锁死，当然可以通过UI修改（但不推荐
    //润和的星闪开发板，如果没有主动手动修改的话，请以Hispark Studio等开发软件里默认设置为主，或者使用小熊派一样的如上设置。
    private int baudRate = 115200;      //波特率，华为星闪板一般为115200
    private byte dataBit = 8;           //数据位，一般为8
    private byte stopBit = 1;           //停止位，一般为1
    private byte parity = 0;            //校验，一般为0
    private byte flowControl = 0;       //流控，一般为0，流控不参与UI设置，请代码里直接修改

    public WCHUartSettings() {}
    public static WCHUartSettings needGetData() { return getData.thing; }
    protected static class getData { private static final WCHUartSettings thing = new WCHUartSettings(); }

    public int getBaudRate() {
        return baudRate;
    }

    public void setBaudRate(int baudRate) {
        this.baudRate = baudRate;
    }

    public byte getDataBit() {
        return dataBit;
    }

    public void setDataBit(byte dataBit) {
        this.dataBit = dataBit;
    }

    public byte getStopBit() {
        return stopBit;
    }

    public void setStopBit(byte stopBit) {
        this.stopBit = stopBit;
    }

    public byte getParity() {
        return parity;
    }

    public void setParity(byte parity) {
        this.parity = parity;
    }

    public byte getFlowControl() {
        return flowControl;
    }

//    public void setFlowControl(byte flowControl) {
//        this.flowControl = flowControl;
//    }
}
