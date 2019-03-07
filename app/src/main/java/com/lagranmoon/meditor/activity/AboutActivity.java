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
import com.lagranmoon.meditor.base.BaseActivity;
import com.lagranmoon.meditor.util.ActivityUtil;

public class AboutActivity extends BaseActivity {

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

    public static void startActivity(Context context){
        Intent intent = new Intent(context, AboutActivity.class);
        context.startActivity(intent);
    }
}
