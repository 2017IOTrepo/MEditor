package com.lagranmoon.meditor.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xmmmmovo on 2018/3/8.
 */

public class ActivityCollector {

    private static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity){
        activities.add(activity);
    }

    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }

    /*
    * 结束所有activity
    * */
    public static void finishiAll(){
        for (Activity activity :
            activities) {
            activity.finish();
        }
    }


}
