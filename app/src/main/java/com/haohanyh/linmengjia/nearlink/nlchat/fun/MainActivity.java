/* 受Haohanyh Computer Software Products Open Source LICENSE保护 https://github.com/Hny0305Lin/LICENSE/blob/main/LICENSE */
package com.haohanyh.linmengjia.nearlink.nlchat.fun;

import static com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCore.ChatUIToast.SnackBarToastForDebug;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.hardware.usb.UsbManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.haohanyh.linmengjia.nearlink.nlchat.ch34x.CH34xUARTDriver;
import com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCore.ChatAdapter;
import com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCore.ChatFileUtils;
import com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCore.ChatMessage;
import com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCore.ChatMessageQueueUpdater;
import com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCore.ChatProcessorForExtract;
import com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCore.ChatSaveMessageDatabaseManager;
import com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCore.ChatTimestamp;
import com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCore.ChatUIAlertDialog;
import com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCore.ChatUIAnimationUtils;
import com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCore.ChatUIBackgroundUtils;
import com.haohanyh.linmengjia.nearlink.nlchat.fun.ChatCore.ChatUtils;
import com.haohanyh.linmengjia.nearlink.nlchat.fun.Premission.NearLinkChatGetSomePermission;
import com.haohanyh.linmengjia.nearlink.nlchat.fun.R.array;
import com.haohanyh.linmengjia.nearlink.nlchat.fun.R.color;
import com.haohanyh.linmengjia.nearlink.nlchat.fun.R.drawable;
import com.haohanyh.linmengjia.nearlink.nlchat.fun.R.id;
import com.haohanyh.linmengjia.nearlink.nlchat.fun.R.string;
import com.haohanyh.linmengjia.nearlink.nlchat.fun.R.style;
import com.haohanyh.linmengjia.nearlink.nlchat.fun.SQLite.SQLiteDataBaseAPP;
import com.haohanyh.linmengjia.nearlink.nlchat.fun.String.StringUtils;
import com.haohanyh.linmengjia.nearlink.nlchat.fun.WCHUart.WCHUartSettings;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //Log需要的TAG
    private static final String TAG = "MainActivity & NLChat";
    //动态获取权限
    public NearLinkChatGetSomePermission hamosGetSomePermission;
    //CH34X相关
    private static final String ACTION_USB_PERMISSION = "cn.wch.wchusbdriver.USB_PERMISSION";
    //主要控件
    private FloatingActionButton btnGO;
    private FloatingActionMenu btnMenu;
    private com.github.clans.fab.FloatingActionButton btnNearLinkStatus,btnNearLinkSettings,btnNearLinkUIChanges,btnNearlinkUart,btnNearlinkDev;
    private int clickCountButton_btnNearLinkStatus = 0;  //按钮计数
    private int clickCountButton_btnNearLinkSettings = 0;   //按钮计数
    private int clickCountButton_btnNearLinkUIChanges = 0;  //按钮计数，切换新旧UI
    private int clickCountButton_btnNearLinkUart = 0;   //按钮计数
    private int clickCountButton_btnNearLinkDev = 0;   //按钮计数
    private CardView CNearLinkStatus,CNearlinkUart,CNearLinkSettings,CNearlinkDev,CTHANKS;
    private CardView CNearLinkChat;

    private CardView CNearLinkChatNewUI;
    private AppCompatTextView NearLinkNewUIUserTitle;
    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;
    private List<ChatMessage> chatMessages = new ArrayList<>();

    private Message MessageTV_Text;
    private TextView APPRunResult,MobileUSBResult,UARTResult;
    private AppCompatTextView NearLinkUserTitle;
    private TextView NearLinkUserText,NearLinkMeText;
    private EditText EditChatSend,EditChatSendNewUI;

    private Resources resources;
    private String[] UartSettingsBaud,UartSettingsData,UartSettingsStop,UartSettingsParity,UartSettingsParityII;
    private WCHUartSettings wchUartSettings = new WCHUartSettings();
    private AppCompatCheckBox CheckBoxUartWarn;
    private boolean NearLinkUartWarnToast = false;
    private RadioButton RadioButtonBaud4800,RadioButtonBaud9600,RadioButtonBaud19200,RadioButtonBaud38400,RadioButtonBaud57600,RadioButtonBaud115200,RadioButtonBaud230400,RadioButtonBaud921600,RadioButtonBaud1000000, RadioButtonBaud2000000;
    private RadioButton RadioButtonData5,RadioButtonData6,RadioButtonData7,RadioButtonData8;
    private RadioButton RadioButtonStop1,RadioButtonStop2;
    private RadioButton RadioButtonParityNone,RadioButtonParityOdd,RadioButtonParityEven,RadioButtonParityMark,RadioButtonParitySpace;
    private final int[] baudRateIds = {id.rbBaud4800, id.rbBaud9600, id.rbBaud19200, id.rbBaud38400, id.rbBaud57600, id.rbBaud115200, id.rbBaud230400, id.rbBaud921600, id.rbBaud1000000, id.rbBaud2000000};
    private final int[] dataBitIds = {id.rbData5, id.rbData6, id.rbData7, id.rbData8};
    private final int[] stopBitIds = {id.rbStop1, id.rbStop2};
    private final int[] parityIds = {id.rbParityNone, id.rbParityOdd, id.rbParityEven, id.rbParityMark, id.rbParitySpace};

    private AppCompatCheckBox SettingsForShowLog,SettingsForSaveSQL,SettingsForDelSQL,SettingsForHistory,SettingsForClearSCR,SettingsForEncryption,SettingsForClip,SettingsForPush,SettingsForBackground,SettingsForBackup,SettingsForNFC;

    //Context
    private Context context = MainActivity.this;

    //手机常量，代码里设置
    private final boolean MobileKeepScreenOn = false;

    //聊天相关
    private Queue<String> serverMessageQueue = new LinkedList<>();
    private Queue<String> clientMessageQueue = new LinkedList<>();
    private static final int MAX_MESSAGES = 8; // 设置最大消息数量
    private ChatMessageQueueUpdater serverUpdater;
    private ChatMessageQueueUpdater clientUpdater;
    private ChatSaveMessageDatabaseManager chatSaveMessageDatabaseManager;

    //聊天时间戳
    private ChatTimestamp chatTimestamp = new ChatTimestamp();

    //调用SQLite
    private SQLiteDataBaseAPP dbHelper;

    //背景
    private ActivityResultLauncher<Intent> pickImageLauncher;

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
        //设置关闭监听器并合并 close 方法的逻辑
        MainAPP.CH34X.setCloseListener(() -> {
            SnackBarToastForDebug(context,"检测到USB已接入，请完成初始化后使用!","推荐初始化操作",1,Snackbar.LENGTH_SHORT);
            MainAPP.CH34X.closeDevice();
        });
        //软件控件开始做处理
        btnGO = findViewById(id.btnGO);
        btnGO.setOnClickListener(this);
        btnGO.setEnabled(true);
        btnMenu = findViewById(id.menu_labels_right);
        btnNearLinkStatus = findViewById(id.menu_labels_right_btn_nearlink);
        btnNearLinkStatus.setOnClickListener(this);
        btnNearLinkSettings = findViewById(id.menu_labels_right_btn_nearlink_settings);
        btnNearLinkSettings.setOnClickListener(this);
        btnNearLinkUIChanges = findViewById(id.menu_labels_right_btn_nearlink_uichanges);
        btnNearLinkUIChanges.setOnClickListener(this);
        btnNearlinkUart = findViewById(id.menu_labels_right_btn_nearlink_uart);
        btnNearlinkUart.setOnClickListener(this);
        btnNearlinkDev = findViewById(id.menu_labels_right_btn_nearlink_dev);
        btnNearlinkDev.setOnClickListener(this);
        clickCountButton_btnNearLinkStatus = 0;
        btnNearLinkStatus.setImageDrawable(getResources().getDrawable(drawable.ic_baseline_done_all_24));
        btnNearLinkStatus.setImageResource(drawable.ic_baseline_done_all_24);
        if (ChatUtils.isUiNewOrOld()) {
            clickCountButton_btnNearLinkUIChanges = 0;
            btnNearLinkUIChanges.setImageDrawable(getResources().getDrawable(drawable.ic_baseline_nearlink_24));
            btnNearLinkUIChanges.setImageResource(drawable.ic_baseline_nearlink_24);
        } else {
            clickCountButton_btnNearLinkUIChanges = 1;
            btnNearLinkUIChanges.setImageDrawable(getResources().getDrawable(drawable.ic_baseline_done_all_24));
            btnNearLinkUIChanges.setImageResource(drawable.ic_baseline_done_all_24);
        }
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
        CNearLinkChatNewUI = findViewById(id.CardIChatNewUI);
        if (ChatUtils.isUiNewOrOld()) {
            CNearLinkChatNewUI.setVisibility(View.VISIBLE);
            CNearLinkChat.setVisibility(View.GONE);
        } else {
            CNearLinkChat.setVisibility(View.VISIBLE);
            CNearLinkChatNewUI.setVisibility(View.GONE);
        }

        NearLinkNewUIUserTitle = findViewById(id.userTitleNewUI);                       //实现长按事件监听器,打开相册文件窗口设置背景用
        NearLinkNewUIUserTitle.setOnLongClickListener(v -> {
            Toast.makeText(context, "跳转相册文件中", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            pickImageLauncher.launch(intent);
            return true; //返回true表示事件已处理
        });

        recyclerView = findViewById(id.recycler_view);
        APPRunResult = findViewById(id.appResult);
        MobileUSBResult = findViewById(id.mobileUsbResult);
        UARTResult = findViewById(id.uartResult);
        NearLinkUserTitle = findViewById(id.userTitle);
        NearLinkUserText = findViewById(id.readText);
        NearLinkMeText = findViewById(id.writeText);

        TextView.OnEditorActionListener editorActionListenerForChatSend = new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                        (keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN)) {
                    String text = textView.getText().toString().trim();
                    if (!text.isEmpty()) {
                        NearLinkChatSendData(textView);
                        return true;
                    } else {
                        SnackBarToastForDebug(context,"您发送的消息为空!","推荐编辑好再发送",0,Snackbar.LENGTH_SHORT);
                    }
                }
                return false;
            }
        };
        EditChatSend = findViewById(id.editChatSend);
        EditChatSend.setOnEditorActionListener(editorActionListenerForChatSend);
        EditChatSendNewUI = findViewById(id.editChatSendNewUI);
        EditChatSendNewUI.setOnEditorActionListener(editorActionListenerForChatSend);


        CheckBoxUartWarn = findViewById(id.cbUartWarn);
        NearLinkUartWarnToast = CheckBoxUartWarn.isChecked();

        resources = getApplicationContext().getResources();
        UartSettingsBaud = resources.getStringArray(array.listBaud);
        UartSettingsData = resources.getStringArray(array.listData);
        UartSettingsStop = resources.getStringArray(array.listStop);
        UartSettingsParity = resources.getStringArray(array.listParity);
        UartSettingsParityII = resources.getStringArray(array.listParityNum);

        RadioButtonBaud4800 = findViewById(id.rbBaud4800);
        RadioButtonBaud9600 = findViewById(id.rbBaud9600);
        RadioButtonBaud19200 = findViewById(id.rbBaud19200);
        RadioButtonBaud38400 = findViewById(id.rbBaud38400);
        RadioButtonBaud57600 = findViewById(id.rbBaud57600);
        RadioButtonBaud115200 = findViewById(id.rbBaud115200);
        RadioButtonBaud230400 = findViewById(id.rbBaud230400);
        RadioButtonBaud921600 = findViewById(id.rbBaud921600);
        RadioButtonBaud1000000 = findViewById(id.rbBaud1000000);
        RadioButtonBaud2000000 = findViewById(id.rbBaud2000000);
        RadioButtonData5 = findViewById(id.rbData5);
        RadioButtonData6 = findViewById(id.rbData6);
        RadioButtonData7 = findViewById(id.rbData7);
        RadioButtonData8 = findViewById(id.rbData8);
        RadioButtonStop1 = findViewById(id.rbStop1);
        RadioButtonStop2 = findViewById(id.rbStop2);
        RadioButtonParityNone = findViewById(id.rbParityNone);
        RadioButtonParityOdd = findViewById(id.rbParityOdd);
        RadioButtonParityEven = findViewById(id.rbParityEven);
        RadioButtonParityMark = findViewById(id.rbParityMark);
        RadioButtonParitySpace = findViewById(id.rbParitySpace);

        // 设置波特率的 RadioButton
        for (int i = 0; i < baudRateIds.length; i++) {
            RadioButton radioButton = findViewById(baudRateIds[i]);
            radioButton.setOnCheckedChangeListener(createCheckedChangeListener(i, "BaudRate"));
        }

        // 设置数据位的 RadioButton
        for (int i = 0; i < dataBitIds.length; i++) {
            RadioButton radioButton = findViewById(dataBitIds[i]);
            radioButton.setOnCheckedChangeListener(createCheckedChangeListener(i, "DataBit"));
        }

        // 设置停止位的 RadioButton
        for (int i = 0; i < stopBitIds.length; i++) {
            RadioButton radioButton = findViewById(stopBitIds[i]);
            radioButton.setOnCheckedChangeListener(createCheckedChangeListener(i, "StopBit"));
        }

        // 设置校验位的 RadioButton
        for (int i = 0; i < parityIds.length; i++) {
            RadioButton radioButton = findViewById(parityIds[i]);
            radioButton.setOnCheckedChangeListener(createCheckedChangeListener(i, "Parity"));
        }

        //聊天NewUI
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatAdapter = new ChatAdapter(this, chatMessages);
        recyclerView.setAdapter(chatAdapter);

        //聊天初始化
        serverUpdater = new ChatMessageQueueUpdater(NearLinkUserText, serverMessageQueue, chatMessages, chatAdapter, "User: ", recyclerView);
        clientUpdater = new ChatMessageQueueUpdater(NearLinkMeText, clientMessageQueue, chatMessages, chatAdapter,"Me: ", recyclerView);
        //聊天数据库初始化
        chatSaveMessageDatabaseManager = new ChatSaveMessageDatabaseManager(MainActivity.this);

        //星闪网络相关设置初始化，目前多数还不允许UI设置，敬请期待
        CompoundButton.OnCheckedChangeListener SettingsChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (compoundButton.isEnabled()) {
                    if (compoundButton.getId() == id.cbSettingsForShowLog) {
                        SnackBarToastForDebug(context,"敬请期待!","如有不适，那没办法，做的慢怪我咯o(*^＠^*)o",0,Snackbar.LENGTH_SHORT);
                    } else if (compoundButton.getId() == id.cbSettingsForSaveSQL) {
                        if (isChecked) {
                                ChatUtils.setSqlitemanager(true);
                            SnackBarToastForDebug(context,"您已开始保存您的聊天记录啦!","目前为" + ChatUtils.isSqlitemanager(),0,Snackbar.LENGTH_SHORT);
                        } else {
                            if (ChatUIAlertDialog.showNormal(compoundButton.getContext(), "聊天保存(SQLite)", "您确定要停止保存聊天数据吗？停止保存您的聊天，将会在接下来聊天时无法保存内容，可能会造成聊天记录丢失。", compoundButton))
                                ChatUtils.setSqlitemanager(false);
                            SnackBarToastForDebug(context,"已为您取消保存聊天记录!","目前为" + ChatUtils.isSqlitemanager(),0,Snackbar.LENGTH_SHORT);
                        }
                    } else if (compoundButton.getId() == id.cbSettingsForDelSQL) {
                        SnackBarToastForDebug(context,"敬请期待!","如有不适，那没办法，做的慢怪我咯o(*^＠^*)o",0,Snackbar.LENGTH_SHORT);
                    } else if (compoundButton.getId() == id.cbSettingsForHistory) {
                        if (isChecked) {
                            ChatUtils.setSqliteHistory(true);
                            SnackBarToastForDebug(context,"您已开始展示您的聊天记录啦!","目前为" + ChatUtils.isSqliteHistory(),0,Snackbar.LENGTH_SHORT);
                        } else {
                            if (ChatUIAlertDialog.showNormal(compoundButton.getContext(), "历史设备记录(SQLite)", "您确定要停止展示聊天数据在UI上吗？", compoundButton))
                                ChatUtils.setSqliteHistory(false);
                            SnackBarToastForDebug(context,"已为您取消保存聊天记录!","目前为" + ChatUtils.isSqliteHistory(),0,Snackbar.LENGTH_SHORT);
                        }
                    } else if (compoundButton.getId() == id.cbSettingsForClearSCR) {
                        SnackBarToastForDebug(context,"敬请期待!","如有不适，那没办法，做的慢怪我咯o(*^＠^*)o",0,Snackbar.LENGTH_SHORT);
                    } else if (compoundButton.getId() == id.cbSettingsForEncryption) {
                        SnackBarToastForDebug(context,"敬请期待!","如有不适，那没办法，做的慢怪我咯o(*^＠^*)o",0,Snackbar.LENGTH_SHORT);
                    } else if (compoundButton.getId() == id.cbSettingsForClip) {
                        if (isChecked) {
                            ChatUtils.setClipMessages(true);
                            SnackBarToastForDebug(context,"您已开启剪贴板功能!","目前为" + ChatUtils.isClipMessages(),0,Snackbar.LENGTH_SHORT);
                        } else {
                            if (ChatUIAlertDialog.showNormal(compoundButton.getContext(), "聊天文本进入剪贴板", "您确定要停止剪贴板吗？剪贴板功能可以帮您自动按规则捕获内容，可以很大程度上帮助到您手动任务耗时的情况，取消则需要您自行处理屏幕上的UI信息。", compoundButton))
                                ChatUtils.setClipMessages(false);
                            SnackBarToastForDebug(context,"已为您取消剪贴板功能!","目前为" + ChatUtils.isClipMessages(),0,Snackbar.LENGTH_SHORT);
                        }
                    } else if (compoundButton.getId() == id.cbSettingsForPush) {
                        SnackBarToastForDebug(context,"敬请期待!","如有不适，那没办法，做的慢怪我咯o(*^＠^*)o",0,Snackbar.LENGTH_SHORT);
                    } else if (compoundButton.getId() == id.cbSettingsForBackground) {
                        SnackBarToastForDebug(context,"敬请期待!","如有不适，那没办法，做的慢怪我咯o(*^＠^*)o",0,Snackbar.LENGTH_SHORT);
                    } else if (compoundButton.getId() == id.cbSettingsForBackup) {
                        SnackBarToastForDebug(context,"敬请期待!","如有不适，那没办法，做的慢怪我咯o(*^＠^*)o",0,Snackbar.LENGTH_SHORT);
                    } else if (compoundButton.getId() == id.cbSettingsForNFC) {
                        SnackBarToastForDebug(context,"敬请期待!","如有不适，那没办法，做的慢怪我咯o(*^＠^*)o",0,Snackbar.LENGTH_SHORT);

                    }
                }
            }
        };
        SettingsForShowLog = findViewById(id.cbSettingsForShowLog);
        SettingsForShowLog.setEnabled(false);
        SettingsForShowLog.setOnCheckedChangeListener(SettingsChangeListener);
        SettingsForSaveSQL = findViewById(id.cbSettingsForSaveSQL);
        SettingsForSaveSQL.setEnabled(true);
        SettingsForSaveSQL.setChecked(true);
        SettingsForSaveSQL.setOnCheckedChangeListener(SettingsChangeListener);
        SettingsForDelSQL = findViewById(id.cbSettingsForDelSQL);
        SettingsForDelSQL.setEnabled(false);
        SettingsForDelSQL.setOnCheckedChangeListener(SettingsChangeListener);
        SettingsForHistory = findViewById(id.cbSettingsForHistory);
        SettingsForHistory.setEnabled(true);
        SettingsForHistory.setChecked(false);
        SettingsForHistory.setOnCheckedChangeListener(SettingsChangeListener);
        SettingsForClearSCR = findViewById(id.cbSettingsForClearSCR);
        SettingsForClearSCR.setEnabled(false);
        SettingsForClearSCR.setOnCheckedChangeListener(SettingsChangeListener);
        SettingsForEncryption = findViewById(id.cbSettingsForEncryption);
        SettingsForEncryption.setEnabled(false);
        SettingsForEncryption.setOnCheckedChangeListener(SettingsChangeListener);
        SettingsForClip = findViewById(id.cbSettingsForClip);
        SettingsForClip.setEnabled(true);
        SettingsForClip.setChecked(true);
        SettingsForClip.setOnCheckedChangeListener(SettingsChangeListener);
        SettingsForPush = findViewById(id.cbSettingsForPush);
        SettingsForPush.setEnabled(false);
        SettingsForPush.setOnCheckedChangeListener(SettingsChangeListener);
        SettingsForBackground = findViewById(id.cbSettingsForBackground);
        SettingsForBackground.setEnabled(false);
        SettingsForBackground.setOnCheckedChangeListener(SettingsChangeListener);
        SettingsForBackup = findViewById(id.cbSettingsForBackup);
        SettingsForBackup.setEnabled(false);
        SettingsForBackup.setOnCheckedChangeListener(SettingsChangeListener);
        SettingsForNFC = findViewById(id.cbSettingsForNFC);
        SettingsForNFC.setEnabled(false);
        SettingsForNFC.setOnCheckedChangeListener(SettingsChangeListener);

        //初始化完成，软件第一次启动必须提示（这里写的第一次启动是软件启动的第一次，而不是使用频率的第一次
        HhandlerI.sendEmptyMessage(31);
        //如果SQLite有记录，可以显示在UI上
        if (ChatUtils.isSqliteHistory()) loadMessagesFromDatabase();

        //背景处理
        //注册图片选择器的启动器
        pickImageLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Uri selectedImageUri = result.getData().getData();
                try {
                    InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                    Drawable drawable = Drawable.createFromStream(inputStream, selectedImageUri.toString());
                    findViewById(R.id.MainUI).setBackground(drawable);

                    ChatFileUtils.saveBackgroundPath(this, selectedImageUri.toString());

                    // 设置 新旧聊天卡片布局 CardView 背景为半透明
                    if (ChatUtils.isUiNewOrOld()) {
                        ChatUIBackgroundUtils.setCardViewBackground(findViewById(id.CardIChatNewUI), 0x80FFFFFF);
                    } else {
                        ChatUIBackgroundUtils.setCardViewBackground(findViewById(id.CardIChat), 0x80FFFFFF);
                    }
                    // 设置 星闪状态卡片布局 CardView 背景为75%透明
                    ChatUIBackgroundUtils.setCardViewBackground(findViewById(id.CardI), 0x40FFFFFF);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // 设置启动时的背景
        ChatUIBackgroundUtils.setSavedBackground(context, findViewById(R.id.MainUI));
    }

    private void InitToOpen() {
        //启动了就得把串口设置给关掉无法设置，否则会影响程序（调整就重启软件再启动即可）
        RadioButtonBaud4800.setEnabled(false);
        RadioButtonBaud9600.setEnabled(false);
        RadioButtonBaud19200.setEnabled(false);
        RadioButtonBaud38400.setEnabled(false);
        RadioButtonBaud57600.setEnabled(false);
        RadioButtonBaud115200.setEnabled(false);
        RadioButtonBaud230400.setEnabled(false);
        RadioButtonBaud921600.setEnabled(false);
        RadioButtonBaud1000000.setEnabled(false);
        RadioButtonBaud2000000.setEnabled(false);
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
            SnackBarToastForDebug(context,"如果USB接入有问题，有可能是Android没有授予USB权限，请授予!","请注意Android弹窗",2,Snackbar.LENGTH_SHORT);
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
                        NearLinkChatReadData();//配置成功后读数据
                        HhandlerI.sendEmptyMessage(30);
                        SnackBarToastForDebug(context,"请主动发送数据或静待接收数据!","谢谢",0,Snackbar.LENGTH_SHORT);
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

    private void loadMessagesFromDatabase() {
        Cursor cursor = dbHelper.getAllMessages();
        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    @SuppressLint("Range") String message = cursor.getString(cursor.getColumnIndex("message"));
                    @SuppressLint("Range") String sender = cursor.getString(cursor.getColumnIndex("sender"));
                    // 根据sender区分消息显示
                    if ("User".equals(sender)) {
                        NearLinkUserText.append(message + "\n");
                    } else {
                        NearLinkMeText.append(message + "\n");
                    }
                }
            } finally {
                cursor.close();
            }
        }
    }

    private StringBuilder buffer = new StringBuilder();
    private void NearLinkChatReadData() {
        //先播报星闪软件情况，已经UART接入星闪网络，再好好的处理字符
        HhandlerI.sendEmptyMessage(10);
        StringBuffer stringBuffer = new StringBuffer();
        MainAPP.CH34X.setReadListener(bytes -> {
            //字节转文本
            //String string = StringUtils.needProcess().bytesToString(bytes);
            String string = new String(bytes, StandardCharsets.UTF_8);
            Log.v(TAG, "长度：bytes.length="+ bytes.length + "\t内容：" + string);
            //进行文本处理
            String processedString = CH34xProcessingForReadData(string);
            stringBuffer.append(processedString);
            //处理完再打印到UI上
            runOnUiThread(() -> {
                //如果需要存储到数据库中
                if (ChatUtils.isSqlitemanager()) {
                    String timestamp = chatTimestamp.saveCurrentTimestamp();
                    chatSaveMessageDatabaseManager.saveMessageToDatabase(timestamp, processedString, "User");
                }
                //如果需要UI滚动消息
                if (ChatUtils.isScrollingMessages()) {
                    if (serverMessageQueue.size() >= MAX_MESSAGES) {
                        serverMessageQueue.poll();
                    }
                    serverMessageQueue.add(processedString);
                    serverUpdater.updateTextView();
                    MainAPP.Vibrate(this);
                } else {
                    NearLinkUserText.append(processedString);
                    if (NearLinkUserText.length() > 2048) {
                        String str = NearLinkUserText.getText().toString().substring(NearLinkUserText.getText().length() - 1024, NearLinkUserText.getText().length());
                        NearLinkUserText.setText("");
                        NearLinkUserText.append(str);
                    }
                    MainAPP.Vibrate(this);
                }
            });
        });
    }

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
                //确保消息以换行符结尾
                if (!completeSecondData.endsWith("\n")) {
                    completeSecondData += "\n";
                }

                //聊天进入剪贴板
                if (ChatUtils.isClipMessages()) {
                    ChatProcessorForExtract.initializeHandler();
                    ChatProcessorForExtract.processChat(context, completeSecondData);
                }

                return completeSecondData;
            } else if (completeFirstData.contains(ChatUtils.getPrefixLogNotConnectedServer())) {
                Log.w(TAG, "串口Log内容：" + completeFirstData);
                if (completeFirstData.startsWith(ChatUtils.getPrefixLogNotConnectedServer()))
                    if (ChatUtils.isShowUartLog())
                        SnackBarToastForDebug(context,"发送失败!\n" + ChatUtils.getPrefixLogNotConnectedServer(),"推荐检查星闪网络",3,Snackbar.LENGTH_SHORT);
            } else {
                Log.d(TAG, "忽略的消息内容：" + completeFirstData);
                // 处理忽略消息内容
            }
            //连接日志，以下与星闪连接相关
            if (completeFirstData.contains(ChatUtils.getPrefixLogConnected())) {
                Log.d(TAG, "连接日志：" + completeFirstData);
                // 处理连接日志
            }
            if (completeFirstData.contains(ChatUtils.getPrefixLogDisconnected())) {
                Log.d(TAG, "断开连接日志：" + completeFirstData);
                // 处理断开连接日志
            }
            if (completeFirstData.contains(ChatUtils.getPrefixLogAcore())) {
                Log.d(TAG, "ACore日志：" + completeFirstData);
                // 处理ACore日志
            }
            //UART服务器日志，以下可以读取星闪日志
            if (completeFirstData.contains(ChatUtils.getPrefixLogSleUartServer())) {
                Log.d(TAG, "UART服务器日志：" + completeFirstData);
                // 处理UART服务器日志
            }
            if (completeFirstData.contains(ChatUtils.getPrefixLogConnectStateChanged())) {
                Log.d(TAG, "连接状态改变日志：" + completeFirstData);
                // 处理连接状态改变日志
            }
            if (completeFirstData.contains(ChatUtils.getPrefixLogPairComplete())) {
                Log.d(TAG, "配对完成日志：" + completeFirstData);
                // 处理配对完成日志
            }
            if (completeFirstData.contains(ChatUtils.getPrefixLogNearlinkDevicesAddr())) {
                // 处理采集到星闪MAC地址完成日志
                if (ChatUtils.isClipMessages()) {
                    Log.d(TAG, "采集到星闪MAC地址日志：" + completeFirstData + "，将进入剪贴板!");
                    ChatProcessorForExtract.initializeHandler();
                    ChatProcessorForExtract.processChat(context, completeFirstData);
                }
            }
            if (completeFirstData.contains(ChatUtils.getPrefixLogSsapsMtuChanged())) {
                Log.d(TAG, "MTU改变日志：" + completeFirstData);
                // 处理MTU改变日志
            }
            if (completeFirstData.contains(ChatUtils.getPrefixLogSleAnnounceEnableCallback())) {
                Log.d(TAG, "启用回调日志：" + completeFirstData);
                // 处理启用回调日志
            }
        }
        return "";
    }

    public void NearLinkChatSendData(View view) {
        HhandlerI.sendEmptyMessage(10);
        //这里写的是用户是否使用新UI和旧UI。
        byte[] to_send;
        String messageSend;
        if (!EditChatSend.getText().toString().isEmpty()) {
            messageSend = EditChatSend.getText().toString();
            to_send = StringUtils.needProcess().toByteArray(EditChatSend.getText().toString());
        } else {
            messageSend = EditChatSendNewUI.getText().toString();
            to_send = StringUtils.needProcess().toByteArray(EditChatSendNewUI.getText().toString());
        }
        //byte[] to_send = StringUtils.needProcess().toByteArray(String.valueOf(EditChatSend.getText()));		//以字符串方式发送
        int retval = MainAPP.CH34X.writeData(to_send, to_send.length);//写数据，第一个参数为需要发送的字节数组，第二个参数为需要发送的字节长度，返回实际发送的字节长度
        if (retval < 0) {
            SnackBarToastForDebug(context,"向对方发送数据失败!","推荐重新配置",3,Snackbar.LENGTH_SHORT);
        } else {
            String TextOfClient = CH34xProcessingForSendData(messageSend);
            runOnUiThread(() -> {
                //如果需要存储到数据库中
                if (ChatUtils.isSqlitemanager()) {
                    String timestamp = chatTimestamp.saveCurrentTimestamp();
                    chatSaveMessageDatabaseManager.saveMessageToDatabase(timestamp, messageSend, "Me");
                }
                //如果需要UI滚动消息
                if (ChatUtils.isScrollingMessages()) {
                    if (clientMessageQueue.size() >= MAX_MESSAGES) {
                        clientMessageQueue.poll(); // 移除最早的消息
                    }
                    clientMessageQueue.add(TextOfClient);
                    //updateClientTextView();
                    clientUpdater.updateTextView();
                    MainAPP.Vibrate(this);
                } else {
                    NearLinkMeText.append(messageSend);
                    if (NearLinkMeText.length() > 2048) {
                        String str = NearLinkMeText.getText().toString().substring(NearLinkMeText.getText().length() - 1024, NearLinkMeText.getText().length());
                        NearLinkMeText.setText("");
                        NearLinkMeText.append(str);
                    }
                    MainAPP.Vibrate(this);
                }
                //发送完消息清空待发送文本
                EditChatSend.setText("");
                EditChatSendNewUI.setText("");
            });
        }
    }

    private String CH34xProcessingForSendData(String string) {
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
        MainAPP.Vibrate(this);
        if (view.getId() == id.btnGO) {
            MainAPP.Vibrate(this);
            btnGO.setEnabled(false);//一次点击后不可再次点击，因为已经把星闪网络给启动了
            btnGO.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(color.nearlinkgreen_deep)));
            ChatUIAnimationUtils.animateBackgroundColorChange(MainActivity.this, btnGO, color.nearlinkgreen_deep, color.nearlinkgreen_light);
            InitToOpen();
        } else if (view.getId() == id.menu_labels_right_btn_nearlink) {
            MainAPP.Vibrate(this);
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
        } else if (view.getId() == id.menu_labels_right_btn_nearlink_uichanges) {
            MainAPP.Vibrate(this);
            if (clickCountButton_btnNearLinkUIChanges % 2 == 0) {
                CNearLinkChat.setVisibility(View.VISIBLE);
                CNearLinkChatNewUI.setVisibility(View.GONE);
                EditChatSendNewUI.setText("");

                btnNearLinkUIChanges.setImageDrawable(getResources().getDrawable(drawable.ic_baseline_done_all_24));
                btnNearLinkUIChanges.setImageResource(drawable.ic_baseline_done_all_24);
                clickCountButton_btnNearLinkUIChanges = clickCountButton_btnNearLinkUIChanges + 1;
            } else {
                CNearLinkChat.setVisibility(View.GONE);
                CNearLinkChatNewUI.setVisibility(View.VISIBLE);
                EditChatSend.setText("");

                btnNearLinkUIChanges.setImageDrawable(getResources().getDrawable(drawable.ic_baseline_nearlink_24));
                btnNearLinkUIChanges.setImageResource(drawable.ic_baseline_nearlink_24);
                clickCountButton_btnNearLinkUIChanges = 0;
            }
        } else if (view.getId() == id.menu_labels_right_btn_nearlink_settings) {
            MainAPP.Vibrate(this);
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
            MainAPP.Vibrate(this);
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
            MainAPP.Vibrate(this);
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

    /**
     * 创建一个 CompoundButton.OnCheckedChangeListener 对象，用于监听复选框的状态变化。
     *
     * @param index 参数索引，用于从数组中获取相应的设置值。
     * @param type  参数类型，可以是 "BaudRate"、"DataBit"、"StopBit" 或 "Parity"。
     * @return 返回一个 CompoundButton.OnCheckedChangeListener 对象。
     */
    private CompoundButton.OnCheckedChangeListener createCheckedChangeListener(final int index, final String type) {
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    switch (type) {
                        case "BaudRate":
                            wchUartSettings.setBaudRate(Integer.parseInt(UartSettingsBaud[index]));
                            SnackBarToastForDebug(context,"已设置波特率" + wchUartSettings.getBaudRate() + "!", "设置成功", 0, Snackbar.LENGTH_SHORT);
                            break;
                        case "DataBit":
                            wchUartSettings.setDataBit(Byte.parseByte(UartSettingsData[index]));
                            SnackBarToastForDebug(context,"已设置数据位" + wchUartSettings.getDataBit() + "!", "设置成功", 0, Snackbar.LENGTH_SHORT);
                            break;
                        case "StopBit":
                            wchUartSettings.setStopBit(Byte.parseByte(UartSettingsStop[index]));
                            SnackBarToastForDebug(context,"已设置停止位" + wchUartSettings.getStopBit() + "!", "设置成功", 0, Snackbar.LENGTH_SHORT);
                            break;
                        case "Parity":
                            wchUartSettings.setParity(Byte.parseByte(UartSettingsParityII[index]));
                            SnackBarToastForDebug(context,"已设置校验位" + wchUartSettings.getParity() + UartSettingsParity[index] + "!", "设置成功", 0, Snackbar.LENGTH_SHORT);
                            break;
                    }
                } else {
                    if (CheckBoxUartWarn.isChecked())
                        Toast.makeText(context, "更改" + type + "中", Toast.LENGTH_SHORT).show();
                }
            }
        };
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this,style.HaohanyhDialog)
                .setMessage(string.egg)
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