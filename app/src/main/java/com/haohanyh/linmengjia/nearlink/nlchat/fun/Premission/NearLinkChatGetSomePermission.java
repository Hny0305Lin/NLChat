/* 受Haohanyh Computer Software Products Open Source LICENSE保护 https://github.com/Hny0305Lin/LICENSE/blob/main/LICENSE */
package com.haohanyh.linmengjia.nearlink.nlchat.fun.Premission;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class NearLinkChatGetSomePermission {

    private Object mObject;
    private String[] mPermissions;
    private int mRequestCode;

    /**
     * 以下代码仅保障Android老设备+Android新设备(存储权限10以下)的正常获取，存储权限10以上不在此获取。
     */
    public static final class Permission {
        private static final List<String> ListStorages = new ArrayList<>(1);
        private static final List<String> ListLocations = new ArrayList<>(2);

        public static final class Location {
            public static final String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
            public static final String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
        }

        public static final class Storage {
            public static final String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
            public static final String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        }

        static {
            //需要获取存储权限来保证：用户键入的内容可以得到保存处理和后续读取。
            ListStorages.add(Storage.READ_EXTERNAL_STORAGE);
            ListStorages.add(Storage.WRITE_EXTERNAL_STORAGE);
            //需要获取GPS权限来保证：正常启动时可以自动获取Wi-Fi SSID名称。
            ListLocations.add(Location.ACCESS_FINE_LOCATION);
            ListLocations.add(Location.ACCESS_COARSE_LOCATION);
        }
    }

    private NearLinkChatGetSomePermission(@NonNull Object object) {
        mObject = object;
    }

    public static NearLinkChatGetSomePermission with(@NonNull Activity activity) {
        return new NearLinkChatGetSomePermission(activity);
    }

    public NearLinkChatGetSomePermission permissions(@NonNull String... permissions) {
        mPermissions = permissions;
        return this;
    }

    public NearLinkChatGetSomePermission requestCode(int requestCode) {
        mRequestCode = requestCode;
        return this;
    }

    public NearLinkChatGetSomePermission request() {
        request(mObject, mRequestCode, mPermissions);
        return this;
    }

    public void request(Object object, int requestCode, String... permissions) {
        if (needRequest() && notGrantedAllPermissions(getActivity(object), permissions)) {
            List<String> unGrantedPermissionsList = createUnGrantedPermissionsList(object, permissions);
            //当申请权限时，申请多少就是多少，然后直接Over
            if (unGrantedPermissionsList.size() > 0) {
                requestPermissions(object, requestCode, listToStringArray(unGrantedPermissionsList));
                unGrantedPermissionsList.clear();
            }
        }
    }

    private List<String> createUnGrantedPermissionsList(Object object, String... permissions) {
        List<String> unGrantedPermissionsList = new ArrayList<>();
        for (String permission : permissions) {
            if (notGrantedPermission(getActivity(object), permission)) {
                unGrantedPermissionsList.add(permission);
            }
        }
        return unGrantedPermissionsList;
    }


    private void requestPermissions(Object object, int requestCode, String... permissions) {
        if (object instanceof Activity) {
            ActivityCompat.requestPermissions((Activity) object, permissions, requestCode);
        }
    }

    /**
     *  needRequest()为返回此时Android版本，大于23（Android 6.0）后基本上就得动态获取权限了
     */
    @SuppressLint("ObsoleteSdkInt")
    private boolean needRequest() {
        return Build.VERSION.SDK_INT >= 23;
    }

    /**
     *  getActivity()为返回此时Activity活动
     */
    private Activity getActivity(Object object) {
        Activity activity = null;
        if (object instanceof Activity) {
            activity = (Activity) object;
        }
        return activity;
    }

    public boolean grantedPermission(Activity activity, String permission) {
        return ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public boolean notGrantedPermission(Activity activity, String permission) {
        return !grantedPermission(activity, permission);
    }

    public boolean grantedAllPermissions(Activity activity, String... permissions) {
        for (String permission : permissions) {
            if (notGrantedPermission(activity, permission)) {
                return false;
            }
        }
        return true;
    }

    public boolean notGrantedAllPermissions(Activity activity, String... permissions) {
        return !grantedAllPermissions(activity, permissions);
    }

    /**
     *  listToStringArray()为List转String数组
     */
    private String[] listToStringArray(List<String> stringList) {
        return stringList.toArray(new String[stringList.size()]);
    }
}
