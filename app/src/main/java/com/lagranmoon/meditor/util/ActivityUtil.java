package com.lagranmoon.meditor.util;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
     * Created by lagranmoon on 2017/8/21.
     * This provides methods to help Activities load their UI.
 * */

public class ActivityUtil {
    /**
     * 添加Fragment到布局容器中
     * @param fragmentManager 传入的FragmentManager
     * @param fragment 要添加的Fragment
     * @param containerId 要添加到的布局容器ID
     */
    public static void addFragment(@NonNull FragmentManager fragmentManager,
                                   @NonNull Fragment fragment, int containerId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(containerId, fragment);
        transaction.commit();
    }

    /**
     * 替换布局容器里的Fragment
     * @param fragmentManager 传入的FragmentManager
     * @param fragment 要替换成的Fragment
     * @param containerId 被替换的布局容器
     */

    public static void replaceFragment(@NonNull FragmentManager fragmentManager,
                                       @NonNull Fragment fragment, int containerId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(containerId, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
