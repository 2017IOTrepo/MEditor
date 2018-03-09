package com.lagranmoon.meditor.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 13256 on 2018/3/7.
 */

public class RequestPermissions {

    public static final int PERMISSION_REQUEST_CODE = 10;
    private static OnPermissionsRequestListener sListener;

    public static void requestPermissions(Activity activity, String[] permissions,
                                     OnPermissionsRequestListener listener){
        sListener = listener;

        List<String> permissionList = new ArrayList<>();

        for (int i = 0; i < permissions.length; i++) {

            if (ContextCompat.checkSelfPermission(activity, permissions[i])
                    != PackageManager.PERMISSION_GRANTED){
                permissionList.add(permissions[i]);
            }

        }

        if (!permissionList.isEmpty()){
            ActivityCompat.requestPermissions(activity, permissionList.toArray(new String[permissionList.size()])
            , PERMISSION_REQUEST_CODE);
        }else {
            listener.onGranted();
        }

    }

    public static void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                                  @NonNull int[] grantResults){

        if (grantResults.length > 0) {
            List<String> deniedPermissions = new ArrayList<>();
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    deniedPermissions.add(permissions[i]);
                }
            }

            if (deniedPermissions.isEmpty()) {
                sListener.onGranted();
            } else {
                sListener.onDenied(deniedPermissions);
            }
        }
    }


    public interface OnPermissionsRequestListener {

        // 权限被同意的时候调用
        public void onGranted();

        // 被拒绝的时候调用
        public void onDenied(List<String> deniedList);
    }

}
