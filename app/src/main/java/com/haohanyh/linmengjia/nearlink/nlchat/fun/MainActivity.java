/* 受Haohanyh Computer Software Products Open Source LICENSE保护 https://github.com/Hny0305Lin/LICENSE/blob/main/LICENSE */
package com.haohanyh.linmengjia.nearlink.nlchat.fun;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.hardware.usb.UsbManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.haohanyh.linmengjia.nearlink.nlchat.ch34x.CH34xUARTDriver;
import com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCore.ChatUtils;
import com.haohanyh.linmengjia.nearlink.nlchat.fun.Premission.NearLinkChatGetSomePermission;
import com.haohanyh.linmengjia.nearlink.nlchat.fun.R.color;
import com.haohanyh.linmengjia.nearlink.nlchat.fun.R.drawable;
import com.haohanyh.linmengjia.nearlink.nlchat.fun.R.id;
import com.haohanyh.linmengjia.nearlink.nlchat.fun.String.StringUtils;
import com.haohanyh.linmengjia.nearlink.nlchat.fun.WCHUart.WCHUartSettings;

import java.text.SimpleDateFormat;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //Log需要的TAG
    private static final String TAG = "MainActivity & 浩瀚银河 & NLChat";
    //动态获取权限
    public NearLinkChatGetSomePermission hamosGetSomePermission;
    //CH34X相关
    private static final String ACTION_USB_PERMISSION = "cn.wch.wchusbdriver.USB_PERMISSION";
    //主要控件
    private FloatingActionButton btnGO;
    private FloatingActionMenu btnMenu;
    private com.github.clans.fab.FloatingActionButton btnNearLinkStatus,btnNearLinkSettings,btnNearlinkUart,btnNearlinkDev;
    private int clickCountButton_btnNearLinkStatus = 0;  //按钮计数
    private int clickCountButton_btnNearLinkSettings = 0;   //按钮计数
    private int clickCountButton_btnNearLinkUart = 0;   //按钮计数
    private int clickCountButton_btnNearLinkDev = 0;   //按钮计数
    private CardView CNearLinkStatus,CNearlinkUart,CNearLinkSettings,CNearlinkDev,CTHANKS;
    private CardView CNearLinkChat;

    private Message MessageTV_Text;
    private TextView APPRunResult,MobileUSBResult,UARTResult;
    private TextView NearLinkServerText,NearLinkClientText;
    private EditText EditChatSend;

    private Resources resources;
    private String[] UartSettingsBaud,UartSettingsData,UartSettingsStop,UartSettingsParity,UartSettingsParityII;
    private WCHUartSettings wchUartSettings = new WCHUartSettings();
    private AppCompatCheckBox CheckBoxUartWarn;
    private boolean NearLinkUartWarnToast = false;
    private RadioButton RadioButtonBaud4800,RadioButtonBaud9600,RadioButtonBaud19200,RadioButtonBaud38400,RadioButtonBaud57600,RadioButtonBaud115200,RadioButtonBaud921600;
    private RadioButton RadioButtonData5,RadioButtonData6,RadioButtonData7,RadioButtonData8;
    private RadioButton RadioButtonStop1,RadioButtonStop2;
    private RadioButton RadioButtonParityNone,RadioButtonParityOdd,RadioButtonParityEven,RadioButtonParityMark,RadioButtonParitySpace;
    //手机常量，代码里设置
    private final boolean MobileKeepScreenOn = false;



    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(id.MainUI), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (MobileKeepScreenOn) {getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);}
        Init();
    }

    /**
     * Init()为初始化软件控件、权限等内容，启动接下来的可使用的控件
     *
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    private void Init() {
        //获取所有权限    Android10以下存储读写、确切位置、大概位置
        hamosGetSomePermission = NearLinkChatGetSomePermission
                .with(this)
                .requestCode(0)
                .permissions(NearLinkChatGetSomePermission.Permission.Storage.READ_EXTERNAL_STORAGE, NearLinkChatGetSomePermission.Permission.Storage.WRITE_EXTERNAL_STORAGE,
                        NearLinkChatGetSomePermission.Permission.Location.ACCESS_FINE_LOCATION, NearLinkChatGetSomePermission.Permission.Location.ACCESS_COARSE_LOCATION)
                .request();
        //获取所有权限    Android10以上存储读写
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                Log.v(TAG,"Android 10以上设备是否获取最高读写文件权限?:" + Environment.isExternalStorageManager());
            } else {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);intent.setData(Uri.parse("package:" + this.getPackageName()));startActivityForResult(intent, 1024);
            }
        }

        //创建CH34x设备对象
        MainAPP.CH34X = new CH34xUARTDriver(
                (UsbManager) getSystemService(Context.USB_SERVICE), this,
                ACTION_USB_PERMISSION);
        MainAPP.CH34X.setCloseListener(this::close);
        //软件控件开始做处理
        btnGO = findViewById(id.btnGO);
        btnGO.setOnClickListener(this);
        btnGO.setEnabled(true);
        btnMenu = findViewById(id.menu_labels_right);
        btnNearLinkStatus = findViewById(id.menu_labels_right_btn_nearlink);
        btnNearLinkStatus.setOnClickListener(this);
        btnNearLinkSettings = findViewById(id.menu_labels_right_btn_nearlink_settings);
        btnNearLinkSettings.setOnClickListener(this);
        btnNearlinkUart = findViewById(id.menu_labels_right_btn_nearlink_uart);
        btnNearlinkUart.setOnClickListener(this);
        btnNearlinkDev = findViewById(id.menu_labels_right_btn_nearlink_dev);
        btnNearlinkDev.setOnClickListener(this);
        clickCountButton_btnNearLinkStatus = 0;
        btnNearLinkStatus.setImageDrawable(getResources().getDrawable(drawable.ic_baseline_done_all_24));
        btnNearLinkStatus.setImageResource(drawable.ic_baseline_done_all_24);
        clickCountButton_btnNearLinkUart = 1;
        btnNearlinkUart.setImageDrawable(getResources().getDrawable(drawable.ic_baseline_close_24));
        btnNearlinkUart.setImageResource(drawable.ic_baseline_close_24);
        clickCountButton_btnNearLinkSettings = 1;
        btnNearLinkSettings.setImageDrawable(getResources().getDrawable(drawable.ic_baseline_close_24));
        btnNearLinkSettings.setImageResource(drawable.ic_baseline_close_24);
        clickCountButton_btnNearLinkDev = 1;
        btnNearlinkDev.setImageDrawable(getResources().getDrawable(drawable.ic_baseline_close_24));
        btnNearlinkDev.setImageResource(drawable.ic_baseline_close_24);
        CNearLinkStatus = findViewById(id.CardI);
        CNearLinkStatus.setVisibility(View.VISIBLE);
        CNearlinkUart = findViewById(id.CardII);
        CNearlinkUart.setVisibility(View.GONE);
        CNearLinkSettings = findViewById(id.CardIII);
        CNearLinkSettings.setVisibility(View.GONE);
        CNearlinkDev = findViewById(id.CardIV);
        CNearlinkDev.setVisibility(View.GONE);
        CTHANKS = findViewById(id.CardAPP);
        CTHANKS.setVisibility(View.VISIBLE);
        CNearLinkChat = findViewById(id.CardIChat);
        CNearLinkChat.setVisibility(View.VISIBLE);
        APPRunResult = findViewById(id.appResult);
        MobileUSBResult = findViewById(id.mobileUsbResult);
        UARTResult = findViewById(id.uartResult);
        NearLinkServerText = findViewById(id.readText);
        NearLinkClientText = findViewById(id.writeText);
        EditChatSend = findViewById(id.editChatSend);

        CheckBoxUartWarn = findViewById(id.cbUartWarn);
        NearLinkUartWarnToast = CheckBoxUartWarn.isChecked();

        resources = getApplicationContext().getResources();
        UartSettingsBaud = resources.getStringArray(R.array.listBaud);
        UartSettingsData = resources.getStringArray(R.array.listData);
        UartSettingsStop = resources.getStringArray(R.array.listStop);
        UartSettingsParity = resources.getStringArray(R.array.listParity);
        UartSettingsParityII = resources.getStringArray(R.array.listParityNum);

        RadioButtonBaud4800 = findViewById(id.rbBaud4800);
        RadioButtonBaud4800.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    wchUartSettings.setBaudRate(Integer.parseInt(UartSettingsBaud[0]));
                    SnackBarToastForDebug("已设置波特率" + wchUartSettings.getBaudRate() + "!","设置成功",0,Snackbar.LENGTH_SHORT);
                } else {
                    if (CheckBoxUartWarn.isChecked())
                        Toast.makeText(MainActivity.this,"更改波特率中",Toast.LENGTH_SHORT).show();
                }
            }
        });
        RadioButtonBaud9600 = findViewById(id.rbBaud9600);
        RadioButtonBaud9600.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    wchUartSettings.setBaudRate(Integer.parseInt(UartSettingsBaud[1]));
                    SnackBarToastForDebug("已设置波特率" + wchUartSettings.getBaudRate() + "!","设置成功",0,Snackbar.LENGTH_SHORT);
                } else {
                    if (CheckBoxUartWarn.isChecked())
                        Toast.makeText(MainActivity.this,"更改波特率中",Toast.LENGTH_SHORT).show();
                }
            }
        });
        RadioButtonBaud19200 = findViewById(id.rbBaud19200);
        RadioButtonBaud19200.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    wchUartSettings.setBaudRate(Integer.parseInt(UartSettingsBaud[2]));
                    SnackBarToastForDebug("已设置波特率" + wchUartSettings.getBaudRate() + "!","设置成功",0,Snackbar.LENGTH_SHORT);
                } else {
                    if (CheckBoxUartWarn.isChecked())
                        Toast.makeText(MainActivity.this,"更改波特率中",Toast.LENGTH_SHORT).show();
                }
            }
        });
        RadioButtonBaud38400 = findViewById(id.rbBaud38400);
        RadioButtonBaud38400.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    wchUartSettings.setBaudRate(Integer.parseInt(UartSettingsBaud[3]));
                    SnackBarToastForDebug("已设置波特率" + wchUartSettings.getBaudRate() + "!","设置成功",0,Snackbar.LENGTH_SHORT);
                } else {
                    if (CheckBoxUartWarn.isChecked())
                        Toast.makeText(MainActivity.this,"更改波特率中",Toast.LENGTH_SHORT).show();
                }
            }
        });
        RadioButtonBaud57600 = findViewById(id.rbBaud57600);
        RadioButtonBaud57600.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    wchUartSettings.setBaudRate(Integer.parseInt(UartSettingsBaud[4]));
                    SnackBarToastForDebug("已设置波特率" + wchUartSettings.getBaudRate() + "!","设置成功",0,Snackbar.LENGTH_SHORT);
                } else {
                    if (CheckBoxUartWarn.isChecked())
                        Toast.makeText(MainActivity.this,"更改波特率中",Toast.LENGTH_SHORT).show();
                }
            }
        });
        RadioButtonBaud115200 = findViewById(id.rbBaud115200);
        RadioButtonBaud115200.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    wchUartSettings.setBaudRate(Integer.parseInt(UartSettingsBaud[5]));
                    SnackBarToastForDebug("已设置波特率" + wchUartSettings.getBaudRate() + "!","设置成功",0,Snackbar.LENGTH_SHORT);
                } else {
                    if (CheckBoxUartWarn.isChecked())
                        Toast.makeText(MainActivity.this,"更改波特率中",Toast.LENGTH_SHORT).show();
                }
            }
        });
        RadioButtonBaud921600 = findViewById(id.rbBaud921600);
        RadioButtonBaud921600.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    wchUartSettings.setBaudRate(Integer.parseInt(UartSettingsBaud[6]));
                    SnackBarToastForDebug("已设置波特率" + wchUartSettings.getBaudRate() + "!","设置成功",0,Snackbar.LENGTH_SHORT);
                } else {
                    if (CheckBoxUartWarn.isChecked())
                        Toast.makeText(MainActivity.this,"更改波特率中",Toast.LENGTH_SHORT).show();
                }
            }
        });
        RadioButtonData5 = findViewById(id.rbData5);
        RadioButtonData5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    wchUartSettings.setDataBit(Byte.parseByte(UartSettingsData[0]));
                    SnackBarToastForDebug("已设置数据位" + wchUartSettings.getDataBit() + "!","设置成功",0,Snackbar.LENGTH_SHORT);
                } else {
                    if (CheckBoxUartWarn.isChecked())
                        Toast.makeText(MainActivity.this,"更改数据位中",Toast.LENGTH_SHORT).show();
                }
            }
        });
        RadioButtonData6 = findViewById(id.rbData6);
        RadioButtonData6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    wchUartSettings.setDataBit(Byte.parseByte(UartSettingsData[1]));
                    SnackBarToastForDebug("已设置数据位" + wchUartSettings.getDataBit() + "!","设置成功",0,Snackbar.LENGTH_SHORT);
                } else {
                    if (CheckBoxUartWarn.isChecked())
                        Toast.makeText(MainActivity.this,"更改数据位中",Toast.LENGTH_SHORT).show();
                }
            }
        });
        RadioButtonData7 = findViewById(id.rbData7);
        RadioButtonData7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    wchUartSettings.setDataBit(Byte.parseByte(UartSettingsData[2]));
                    SnackBarToastForDebug("已设置数据位" + wchUartSettings.getDataBit() + "!","设置成功",0,Snackbar.LENGTH_SHORT);
                } else {
                    if (CheckBoxUartWarn.isChecked())
                        Toast.makeText(MainActivity.this,"更改数据位中",Toast.LENGTH_SHORT).show();
                }
            }
        });
        RadioButtonData8 = findViewById(id.rbData8);
        RadioButtonData8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    wchUartSettings.setDataBit(Byte.parseByte(UartSettingsData[3]));
                    SnackBarToastForDebug("已设置数据位" + wchUartSettings.getDataBit() + "!","设置成功",0,Snackbar.LENGTH_SHORT);
                } else {
                    if (CheckBoxUartWarn.isChecked())
                        Toast.makeText(MainActivity.this,"更改数据位中",Toast.LENGTH_SHORT).show();
                }
            }
        });
        RadioButtonStop1 = findViewById(id.rbStop1);
        RadioButtonStop1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    wchUartSettings.setStopBit(Byte.parseByte(UartSettingsStop[0]));
                    SnackBarToastForDebug("已设置停止位" + wchUartSettings.getStopBit() + "!","设置成功",0,Snackbar.LENGTH_SHORT);
                } else {
                    if (CheckBoxUartWarn.isChecked())
                        Toast.makeText(MainActivity.this,"更改停止位中",Toast.LENGTH_SHORT).show();
                }
            }
        });
        RadioButtonStop2 = findViewById(id.rbStop2);
        RadioButtonStop2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    wchUartSettings.setStopBit(Byte.parseByte(UartSettingsStop[1]));
                    SnackBarToastForDebug("已设置停止位" + wchUartSettings.getStopBit() + "!","设置成功",0,Snackbar.LENGTH_SHORT);
                } else {
                    if (CheckBoxUartWarn.isChecked())
                        Toast.makeText(MainActivity.this,"更改停止位中",Toast.LENGTH_SHORT).show();
                }
            }
        });
        RadioButtonParityNone = findViewById(id.rbParityNone);
        RadioButtonParityNone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    wchUartSettings.setParity(Byte.parseByte(UartSettingsParityII[0]));
                    SnackBarToastForDebug("已设置校验位" + wchUartSettings.getParity() + UartSettingsParity[0] + "!","设置成功",0,Snackbar.LENGTH_SHORT);
                } else {
                    if (CheckBoxUartWarn.isChecked())
                        Toast.makeText(MainActivity.this,"更改校验位中",Toast.LENGTH_SHORT).show();
                }
            }
        });
        RadioButtonParityOdd = findViewById(id.rbParityOdd);
        RadioButtonParityOdd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    wchUartSettings.setParity(Byte.parseByte(UartSettingsParityII[1]));
                    SnackBarToastForDebug("已设置校验位" + wchUartSettings.getParity() + UartSettingsParity[1] + "!","设置成功",0,Snackbar.LENGTH_SHORT);
                } else {
                    if (CheckBoxUartWarn.isChecked())
                        Toast.makeText(MainActivity.this,"更改校验位中",Toast.LENGTH_SHORT).show();
                }
            }
        });
        RadioButtonParityEven = findViewById(id.rbParityEven);
        RadioButtonParityEven.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    wchUartSettings.setParity(Byte.parseByte(UartSettingsParityII[2]));
                    SnackBarToastForDebug("已设置校验位" + wchUartSettings.getParity() + UartSettingsParity[2] + "!","设置成功",0,Snackbar.LENGTH_SHORT);
                } else {
                    if (CheckBoxUartWarn.isChecked())
                        Toast.makeText(MainActivity.this,"更改校验位中",Toast.LENGTH_SHORT).show();
                }
            }
        });
        RadioButtonParityMark = findViewById(id.rbParityMark);
        RadioButtonParityMark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    wchUartSettings.setParity(Byte.parseByte(UartSettingsParityII[3]));
                    SnackBarToastForDebug("已设置校验位" + wchUartSettings.getParity() + UartSettingsParity[3] + "!","设置成功",0,Snackbar.LENGTH_SHORT);
                } else {
                    if (CheckBoxUartWarn.isChecked())
                        Toast.makeText(MainActivity.this,"更改校验位中",Toast.LENGTH_SHORT).show();
                }
            }
        });
        RadioButtonParitySpace = findViewById(id.rbParitySpace);
        RadioButtonParitySpace.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    wchUartSettings.setParity(Byte.parseByte(UartSettingsParityII[4]));
                    SnackBarToastForDebug("已设置校验位" + wchUartSettings.getParity() + UartSettingsParity[4] + "!","设置成功",0,Snackbar.LENGTH_SHORT);
                } else {
                    if (CheckBoxUartWarn.isChecked())
                        Toast.makeText(MainActivity.this,"更改校验位中",Toast.LENGTH_SHORT).show();
                }
            }
        });

        HhandlerI.sendEmptyMessage(31);
    }

    private void InitToOpen() {
        //启动了就得把串口设置给关掉无法设置，否则会影响程序（调整就重启软件再启动即可）
        RadioButtonBaud4800.setEnabled(false);
        RadioButtonBaud9600.setEnabled(false);
        RadioButtonBaud19200.setEnabled(false);
        RadioButtonBaud38400.setEnabled(false);
        RadioButtonBaud57600.setEnabled(false);
        RadioButtonBaud115200.setEnabled(false);
        RadioButtonBaud921600.setEnabled(false);
        RadioButtonData5.setEnabled(false);
        RadioButtonData6.setEnabled(false);
        RadioButtonData7.setEnabled(false);
        RadioButtonData8.setEnabled(false);
        RadioButtonStop1.setEnabled(false);
        RadioButtonStop2.setEnabled(false);
        RadioButtonParityNone.setEnabled(false);
        RadioButtonParityOdd.setEnabled(false);
        RadioButtonParityEven.setEnabled(false);
        RadioButtonParityMark.setEnabled(false);
        RadioButtonParitySpace.setEnabled(false);
        //判断是否支持SB HOSTU
        if (!MainAPP.CH34X.usbFeatureSupported()) {//不支持，弹出提示窗口
            HhandlerI.sendEmptyMessage(21);
            HhandlerI.sendEmptyMessage(11);
            SnackBarToastForDebug("如果USB接入有问题，有可能是Android没有授予USB权限，请授予!","请注意Android弹窗",2,Snackbar.LENGTH_SHORT);
        } else {
            HhandlerI.sendEmptyMessage(20);
            //打开USB设备
            int retval = MainAPP.CH34X.resumeUsbList();
            if (retval == -1) {//打开失败，关闭设备
                MainAPP.CH34X.closeDevice();
                return;
            }

            if (retval == 0) {//打开成功
                //初始化CH34x串口
                if (!MainAPP.CH34X.uartInit()) {//初始化失败
                    HhandlerI.sendEmptyMessage(32);
                } else {
                    if (MainAPP.CH34X.setConfig(WCHUartSettings.needGetData().getBaudRate(),
                            WCHUartSettings.needGetData().getDataBit(), WCHUartSettings.needGetData().getStopBit(),
                            WCHUartSettings.needGetData().getParity(), WCHUartSettings.needGetData().getFlowControl())) {
                        CH34xReadData();//配置成功后读数据
                        HhandlerI.sendEmptyMessage(30);
                        SnackBarToastForDebug("请主动发送数据或静待接收数据!","谢谢",0,Snackbar.LENGTH_SHORT);
                    } else {
                        HhandlerI.sendEmptyMessage(32);
                        HhandlerI.sendEmptyMessage(11);
                    }
                }
            } else {
                HhandlerI.sendEmptyMessage(21);
                HhandlerI.sendEmptyMessage(11);
            }
        }
    }

    //读数据
//    public int totalrecv;
//    private void CH34xReadData(){
//        //创建一个新的线程来读取数据，将读取的数据通过handler发送出去进行处理
//        new Thread() {
//            @Override
//            public void run() {
//                super.run();
//                byte[] buffer = new byte[4096];
//                while (true) {
//                    Message msg = Message.obtain();
//                    int length = MainAPP.CH34X.readData(buffer, buffer.length);
//                    if (length > 0) {
//                        totalrecv += length;
//                        String content = StringUtils.needProcess().bytesToString(buffer);
//                        msg.obj += content+"";
//                        HhandlerI.sendEmptyMessage(10);
//                        HhandlerII.sendMessage(msg);
//                    }
//                }
//            }
//        }.start();
//    }
    public String TextOfServer = "";
    private void CH34xReadData() {
        //先播报星闪软件情况，已经UART接入星闪网络，再好好的处理字符
        HhandlerI.sendEmptyMessage(10);
        StringBuffer stringBuffer = new StringBuffer();
        MainAPP.CH34X.setReadListener(bytes -> {
            //字节转文本
            String string = StringUtils.needProcess().bytesToString(bytes);
            Log.v(TAG, "长度：bytes.length="+ bytes.length + "\t内容：" + string);
            //进行文本处理
            String processedString = CH34xProcessingForReadData(string);
            stringBuffer.append(processedString);
            //处理完再打印到UI上
            runOnUiThread(() -> {
                NearLinkServerText.append(processedString);
                if (NearLinkServerText.length() > 2048) {
                    String str = NearLinkServerText.getText().toString().substring(NearLinkServerText.getText().length() - 1024, NearLinkServerText.getText().length());
                    NearLinkServerText.setText("");
                    NearLinkServerText.append(str);
                }
            });
        });
    }

    private StringBuilder buffer = new StringBuilder();
    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
    private String CH34xProcessingForReadData(String string) {
        buffer.append(string);
        String result = buffer.toString();

        int endIndex = result.indexOf("\n");
        if (endIndex != -1) {
            String completeFirstData = result.substring(0, endIndex + 1);
            String completeSecondData = "";
            Log.v(TAG, "长度：completeFirstData.length="+ completeFirstData.length() + "\t内容：" + completeFirstData);
            buffer.delete(0, endIndex + 1);

            //去掉特定的前缀字符串，然后返回（聊天内容），只有当消息包含特定的前缀时才处理
            if (completeFirstData.contains(ChatUtils.getPrefixServer()) || completeFirstData.contains(ChatUtils.getPrefixClient())) {
                if (completeFirstData.startsWith(ChatUtils.getPrefixServer())) {
                    completeSecondData = completeFirstData.replace(ChatUtils.getPrefixServer(), "").trim();
                    Log.v(TAG, "长度：completeSecondData.length="+ completeSecondData.length() + "\t内容：" + completeSecondData);
                } else if (completeFirstData.startsWith(ChatUtils.getPrefixClient())) {
                    completeSecondData = completeFirstData.replace(ChatUtils.getPrefixClient(), "").trim();
                    Log.v(TAG, "长度：completeSecondData.length="+ completeSecondData.length() + "\t内容：" + completeSecondData);
                }
                //添加时间戳
                String timestamp = dateFormat.format(new java.util.Date());
                completeSecondData = timestamp + " - " + completeSecondData;

                //确保消息以换行符结尾
                if (!completeSecondData.endsWith("\n")) {
                    completeSecondData += "\n";
                }
                return completeSecondData;
            }
        } else {
            return "";
        }
        return "";
    }

    public String TextOfClient = "";
    public void nearlinkChatSend(View view) {
        HhandlerI.sendEmptyMessage(10);
        byte[] to_send = StringUtils.needProcess().toByteArray(String.valueOf(EditChatSend.getText()));		//以字符串方式发送
        int retval = MainAPP.CH34X.writeData(to_send, to_send.length);//写数据，第一个参数为需要发送的字节数组，第二个参数为需要发送的字节长度，返回实际发送的字节长度
        if (retval < 0) {
            SnackBarToastForDebug("向对方发送数据失败!","推荐重新配置",3,Snackbar.LENGTH_SHORT);
        } else {
            Toast.makeText(MainActivity.this, "发送成功!", Toast.LENGTH_SHORT).show();
            String processedString = CH34xProcessingForSendData(EditChatSend.getText().toString());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    NearLinkClientText.append(processedString);
                    if (NearLinkClientText.length() > 2048) {
                        String str = NearLinkClientText.getText().toString().substring(NearLinkClientText.getText().length() - 1024, NearLinkClientText.getText().length());
                        NearLinkClientText.setText("");
                        NearLinkClientText.append(str);
                    }
                }
            });
        }
    }

    private String CH34xProcessingForSendData(String string) {
        //添加时间戳
        String timestamp = dateFormat.format(new java.util.Date());
        string = timestamp + " - " + string;

        //确保消息以换行符结尾
        if (!string.endsWith("\n")) {
            string += "\n";
        }
        return string;
    }

    /*
     * Hhandler用于数据交互和UI修改
     */
    private final Handler HhandlerI = new Handler(msg -> {
        TextInformation(msg);
        return true;
    });

    /**
     *
     * TextInformation()与HhandlerI配合，修改页面情况，并且显示在页面
     * @param MessageTV_Text 消息类参数，进来的值可以对应到类中的{switch ... case}的内容
     */
    @SuppressLint("SetTextI18n")
    private void TextInformation(@NonNull Message MessageTV_Text) {
        switch (MessageTV_Text.what) {
            case 10: APPRunResult.setText("您成功通过UART接入星网!");break;
            case 11: APPRunResult.setText("您未接入星网!");break;
            case 20: MobileUSBResult.setText("您的设备支持USBHost!");break;
            case 21: MobileUSBResult.setText("您的设备不支持USBHost，请检查!");break;
            case 30: UARTResult.setText("您的设备已连接串口，请检查!");break;
            case 31: UARTResult.setText("您的设备第一次启动，请链接!");break;
            case 32: UARTResult.setText("您的设备已移除串口，请检查!");break;
            case 33: UARTResult.setText("您的设备串口配置出问题，请检查!");break;
        }
    }

    /**
     *
     * @param view
     */
    @SuppressLint({"NonConstantResourceId", "UseCompatLoadingForDrawables"})
    @Override
    public void onClick(View view) {
        if (view.getId() == id.btnGO) {
            btnGO.setEnabled(false);//一次点击后不可再次点击，因为已经把星闪网络给启动了
            InitToOpen();
        } else if (view.getId() == id.menu_labels_right_btn_nearlink) {
            Vibrate(this);
            if (clickCountButton_btnNearLinkStatus % 2 == 0) {
                CNearLinkStatus.setVisibility(View.GONE);
                btnGO.setVisibility(View.GONE);
                btnNearLinkStatus.setImageDrawable(getResources().getDrawable(drawable.ic_baseline_close_24));
                btnNearLinkStatus.setImageResource(drawable.ic_baseline_close_24);
                clickCountButton_btnNearLinkStatus = clickCountButton_btnNearLinkStatus + 1;
            } else {
                CNearLinkStatus.setVisibility(View.VISIBLE);
                btnGO.setVisibility(View.VISIBLE);
                btnNearLinkStatus.setImageDrawable(getResources().getDrawable(drawable.ic_baseline_done_all_24));
                btnNearLinkStatus.setImageResource(drawable.ic_baseline_done_all_24);
                clickCountButton_btnNearLinkStatus = 0;
            }

        } else if (view.getId() == id.menu_labels_right_btn_nearlink_settings) {
            Vibrate(this);
            if (clickCountButton_btnNearLinkSettings % 2 == 0) {
                CNearLinkSettings.setVisibility(View.GONE);
                btnNearLinkSettings.setImageDrawable(getResources().getDrawable(drawable.ic_baseline_close_24));
                btnNearLinkSettings.setImageResource(drawable.ic_baseline_close_24);
                clickCountButton_btnNearLinkSettings = clickCountButton_btnNearLinkSettings + 1;
            } else {
                CNearLinkSettings.setVisibility(View.VISIBLE);
                btnNearLinkSettings.setImageDrawable(getResources().getDrawable(drawable.ic_baseline_done_all_24));
                btnNearLinkSettings.setImageResource(drawable.ic_baseline_done_all_24);
                clickCountButton_btnNearLinkSettings = 0;
            }

        } else if (view.getId() == id.menu_labels_right_btn_nearlink_uart) {
            Vibrate(this);
            if (clickCountButton_btnNearLinkUart % 2 == 0) {
                CNearlinkUart.setVisibility(View.GONE);
                btnNearlinkUart.setImageDrawable(getResources().getDrawable(drawable.ic_baseline_close_24));
                btnNearlinkUart.setImageResource(drawable.ic_baseline_close_24);
                clickCountButton_btnNearLinkUart = clickCountButton_btnNearLinkUart + 1;
            } else {
                CNearlinkUart.setVisibility(View.VISIBLE);
                btnNearlinkUart.setImageDrawable(getResources().getDrawable(drawable.ic_baseline_done_all_24));
                btnNearlinkUart.setImageResource(drawable.ic_baseline_done_all_24);
                clickCountButton_btnNearLinkUart = 0;
            }

        } else if (view.getId() == id.menu_labels_right_btn_nearlink_dev) {
            Vibrate(this);
            if (clickCountButton_btnNearLinkDev % 2 == 0) {
                CNearlinkDev.setVisibility(View.GONE);
                btnNearlinkDev.setImageDrawable(getResources().getDrawable(drawable.ic_baseline_close_24));
                btnNearlinkDev.setImageResource(drawable.ic_baseline_close_24);
                clickCountButton_btnNearLinkDev = clickCountButton_btnNearLinkDev + 1;
            } else {
                CNearlinkDev.setVisibility(View.VISIBLE);
                btnNearlinkDev.setImageDrawable(getResources().getDrawable(drawable.ic_baseline_done_all_24));
                btnNearlinkDev.setImageResource(drawable.ic_baseline_done_all_24);
                clickCountButton_btnNearLinkDev = 0;
            }

        }
    }

    private void close() {
        SnackBarToastForDebug("检测到USB已接入，请完成初始化后使用!","推荐初始化操作",1,Snackbar.LENGTH_SHORT);
        MainAPP.CH34X.closeDevice();
    }

    /**
     * SnackBar通知 遵守Google Material
     * @param text  显示的文本
     * @param actiontext    按钮控件显示的文本
     * @param level 提示等级(0~4)，对应Android Log等级：Log.v  Log.d   Log.i   Log.w   Log.e
     * @param SnackbarLength SnackBar通知，通知滞留时间
     */
    private void SnackBarToastForDebug(String text,String actiontext,int level,int SnackbarLength) {
        //找View和Layout
        View decorView = getWindow().getDecorView();
        CoordinatorLayout coordinatorLayout = decorView.findViewById(id.MainUI);
        //设置显示时间、按钮颜色、背景颜色、按钮点击状态
        Snackbar snackbar = Snackbar.make(coordinatorLayout, text, SnackbarLength);
        snackbar.setActionTextColor(getResources().getColor(color.Pink_is_justice));
        switch (level) {
            case 0: //VERBOSE日志     不重要的提示          黑
                snackbar.getView().setBackgroundColor(getResources().getColor(color.snackbar_log_v));break;
            case 1: //DEBUG日志       稍微重要的提示         蓝
                snackbar.getView().setBackgroundColor(getResources().getColor(color.snackbar_log_d));break;
            case 2: //INFO日志        一般重要的提示         绿
                snackbar.getView().setBackgroundColor(getResources().getColor(color.snackbar_log_i));break;
            case 3: //WARN日志        已经算重要的提示          橙
                snackbar.getView().setBackgroundColor(getResources().getColor(color.snackbar_log_w));break;
            case 4: //ERROR日志       很重要的提示          红
                snackbar.getView().setBackgroundColor(getResources().getColor(color.snackbar_log_e));break;
            default: //随意啦
                snackbar.getView().setBackgroundColor(getResources().getColor(color.blue_biaozhun_logowai_transparent));break;
        }
        snackbar.setAction(actiontext, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrate(MainActivity.this);
                switch (level) { case 520: break; case 521: break; default: break;}
            }
        });
        //snackbar主动出击
        snackbar.show();
    }

    /**
     * 手机震动提醒
     * @param context 上下文
     */
    public void Vibrate(Context context) {
        Log.v(TAG, "浩瀚银河" + "手机震动提醒: " + "成功");
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            long[] pattern = {0, 75, 37, 50};
            vibrator.vibrate(pattern, -1);
        }
    }

    //没有人能够熄灭满天的星光，每一个开发者都是华为要汇聚的星星之火。星星之火，可以燎原。
    @SuppressLint("SetTextI18n")
    public void thanks3q(View view) {
        TextView textView = new TextView(this);
        textView.setText("Egg~");
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        textView.setPadding(16, 16, 0, 8);
        textView.setGravity(Gravity.LEFT);
        textView.setTextColor(ContextCompat.getColor(this, color.Pink_is_fancy));
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.HaohanyhDialog)
                .setMessage(R.string.egg)
                .setCustomTitle(textView)
                .setNegativeButton("确定!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton("取消!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setNeutralButton("备用!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Objects.requireNonNull(alertDialog.getWindow()).setLayout(900,900);
        //Objects.requireNonNull(alertDialog.getWindow()).setLayout(900,1600);
    }
}