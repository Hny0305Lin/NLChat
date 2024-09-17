package com.haohanyh.linmengjia.nearlink.nlchat.fun.Uart;

public class NearLinkBoardSettings {




    private int TXPOWER = 2;       //AT发射功率，(2,1,0,-1,-2,-3,-4,-5,-6,-7,-8,-9,-10,-11,-12,-13,-14,-15,-16)

    private String MACAddr = "ebef339b8d5d";

    private int ServerOrClient = 1;

    private int encrypt = 1;

    private String pwd = "7EC59F";

    private String Name = "SLE_TEST";

    private String BroadCast = "1";

    private int BroadCastTimer = 1;

    public NearLinkBoardSettings() {}
    public static NearLinkBoardSettings needGetData() { return NearLinkBoardSettings.getData.thing; }
    protected static class getData { private static final NearLinkBoardSettings thing = new NearLinkBoardSettings(); }

    public int getTXPOWER() {
        return TXPOWER;
    }

    public String getMACAddr() {
        return MACAddr;
    }

    public int getServerOrClient() {
        return ServerOrClient;
    }

    public int getEncrypt() {
        return encrypt;
    }

    public String getPwd() {
        return pwd;
    }

    public String getName() {
        return Name;
    }

    public String getBroadCast() {
        return BroadCast;
    }

    public int getBroadCastTimer() {
        return BroadCastTimer;
    }
}
