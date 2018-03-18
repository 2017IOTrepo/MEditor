package com.lagranmoon.meditor.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lagranmoon.meditor.R;
import com.lagranmoon.meditor.util.ActivityUtil;

import butterknife.OnClick;

public class AboutActivity extends AppCompatActivity {

    private TextView textView;
    private ActivityUtil activityUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        textView = (TextView)findViewById(R.id.github_path_text_view);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://github.com/Lagranmoon/MEditor"));
                startActivity(intent);
            }
        });

        activityUtil = new ActivityUtil();
    }


    private SharedPreferences portrait_Pref;
    private SharedPreferences.Editor portrait_editor;
    private boolean ifPortrait = false;
    @Override
    protected void onResume() {
        super.onResume();
        super.onResume();
        portrait_Pref = getSharedPreferences("properties", MODE_PRIVATE);
        ifPortrait = portrait_Pref.getBoolean("ifPortrait", ifPortrait);
        if ((getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) && ifPortrait){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context, AboutActivity.class);
        context.startActivity(intent);
    }
}
