package com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCoreUSB;

import com.haohanyh.linmengjia.nearlink.nlchat.fun.MainAPP;
import com.haohanyh.linmengjia.nearlink.nlchat.fun.String.StringUtils;
import com.haohanyh.linmengjia.nearlink.nlchat.fun.Uart.NearLinkBoardSettings;
import com.haohanyh.linmengjia.nearlink.nlchat.fun.Uart.WCHUartSettings;

public class NearLinkBoardATManage {

    private String[] Hi2821BearPiAT = {};
    private String[] Hi2821BearPiSend = {"",""};
    private String[] WS63HihopeAT = {};
    private String[] WS63HihopeSend = {"",""};

    public NearLinkBoardATManage() {
        Hi2821BearPiAT = new String[]{
                "AT",
                "ATE0", "ATE1",
                "AT+HELP", "AT+RESET", "AT+RESTORESET",
                "AT+SETUARTCFG="+WCHUartSettings.needGetData().getBaudRate()+","+WCHUartSettings.needGetData().getDataBit()+","+WCHUartSettings.needGetData().getStopBit()+","+WCHUartSettings.needGetData().getParity()+","+WCHUartSettings.needGetData().getFlowControl(),
                "AT+SETUARTCFG?",
                "AT+SETUARTCFG=?",
                "AT+SETTXPOWER="+NearLinkBoardSettings.needGetData().getTXPOWER(),
                "AT+SETTXPOWER?",
                "AT+SETTXPOWER=?",
                "AT+SETSLEADDR="+NearLinkBoardSettings.needGetData().getMACAddr(),
                "AT+SETSLEADDR?",
                "AT+SETSLEADDR=?",
                "AT+SETMODE="+NearLinkBoardSettings.needGetData().getServerOrClient(),
                "AT+SETMODE?",
                "AT+SETMODE=?",
                "AT+SKEY="+NearLinkBoardSettings.needGetData().getEncrypt()+","+NearLinkBoardSettings.needGetData().getPwd(),
                "AT+SKEY?",
                "AT+SKEY=?",
                "AT+SSETNAME="+NearLinkBoardSettings.needGetData().getName(),
                "AT+SSETNAME?",
                "AT+SSETNAME=?",
                "AT+SSERVER="+NearLinkBoardSettings.needGetData().getBroadCast(),
                "AT+SSERVER?",
                "AT+SSERVER=?",
                "AT+SCLIST",
                "AT+SSEND="+Hi2821BearPiSend[0]+","+Hi2821BearPiSend[1],
                "AT+SSEND?",
                "AT+SSENDALL="+Hi2821BearPiSend[1],
                "AT+SSENDALL=?",
                "AT+SBLACK=0"+","+Hi2821BearPiSend[0],
                "AT+SBLACK=1"+","+Hi2821BearPiSend[0],
                "AT+SBLACK=?",
                "AT+SRADIOFRE="+NearLinkBoardSettings.needGetData().getBroadCastTimer(),
                "AT+SRADIOFRE?",
                "AT+SRADIOFRE=?",
                "AT+SKILLCLIENT="+Hi2821BearPiSend[0],
                "AT+SKILLCLIENT?",

                "AT+CKEY="+NearLinkBoardSettings.needGetData().getEncrypt()+","+NearLinkBoardSettings.needGetData().getPwd(),
                "AT+CKEY?",
                "AT+CKEY=?",
                "AT+CSETNAME="+NearLinkBoardSettings.needGetData().getName(),
                "AT+CSETNAME?",
                "AT+CSETNAME=?",
                "AT+CCONNECT="+NearLinkBoardSettings.needGetData().getName(),
                "AT+CCONNECT?",
                "AT+CCONNECT=?",
                "AT+CSLIST",
                "AT+CSEND="+Hi2821BearPiSend[1],
                "AT+CSEND?",
                "AT+CDISCONNECT",
        };
    }

    public int sendATCommandMessage(String messageSend) {




        byte[] to_send = StringUtils.needProcess().toByteArrayII(messageSend);
        int retval = MainAPP.CH34X.writeData(to_send, to_send.length); // 写数据，第一个参数为需要发送的字节数组，第二个参数为需要发送的字节长度，返回实际发送的字节长度
        return retval;
    }
























}
