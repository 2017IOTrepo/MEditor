package com.lagranmoon.meditor.base;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.lagranmoon.meditor.util.ActivityCollector;

/**
 * Created by xmmmmovo on 2018/3/8.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    private SharedPreferences portrait_Pref;
    private SharedPreferences.Editor portrait_editor;
    private boolean ifPortrait = false;
    @Override
    protected void onResume() {
        super.onResume();
        portrait_Pref = getSharedPreferences("properties", MODE_PRIVATE);
        ifPortrait = portrait_Pref.getBoolean("ifPortrait", ifPortrait);
        if ((getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) && ifPortrait){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
}
