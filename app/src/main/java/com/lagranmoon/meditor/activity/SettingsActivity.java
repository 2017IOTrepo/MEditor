package com.lagranmoon.meditor.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.lagranmoon.meditor.R;

public class SettingsActivity extends AppCompatActivity {

    private Switch aSwitch;
    private SharedPreferences.Editor editor;
    private SharedPreferences portrait_Pref;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        editor = getSharedPreferences("properties", MODE_PRIVATE).edit();

        aSwitch = (Switch)findViewById(R.id.portrait_switch);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                switch (compoundButton.getId()){
                    case R.id.portrait_switch:
                        editor.putBoolean("ifPortrait", b);
                        editor.apply();
                        break;
                }
            }
        });

        portrait_Pref = getSharedPreferences("properties", MODE_PRIVATE);
        aSwitch.setChecked(portrait_Pref.getBoolean("ifPortrait", false));
    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context, SettingsActivity.class);
        context.startActivity(intent);
    }
}
