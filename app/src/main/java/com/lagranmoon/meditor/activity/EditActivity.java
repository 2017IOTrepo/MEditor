package com.lagranmoon.meditor.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.lagranmoon.meditor.R;
import com.lagranmoon.meditor.adapter.ViewPagerAdapter;
import com.lagranmoon.meditor.base.BaseActivity;
import com.lagranmoon.meditor.fragment.Display_fragment_Activity;
import com.lagranmoon.meditor.fragment.Edit_fragment_Activity;
import com.lagranmoon.meditor.util.ActivityUtil;
import com.lagranmoon.meditor.util.FileUtils;

import java.io.File;
import java.io.IOException;

public class EditActivity extends BaseActivity{

    private ActivityUtil activityUtil;
    private Context context;
    public final String TAG = "EditActivity";
    public final int EDIT_MODE = 0;
    private Intent intent;
    private boolean ifNew;
    private String filePath;
    private String fileName;

    private Edit_fragment_Activity edit_fragment_activity;

    private ViewPager mViewPager;//滑动效果

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);

        filePath = getIntent().getStringExtra("filePath");
        fileName = getIntent().getStringExtra("fileName");
        edit_fragment_activity = Edit_fragment_Activity.getInstance(filePath, fileName);

        mViewPager = (ViewPager)findViewById(R.id.edit_View_Pager);
        InitViewPager();// 初始化ViewPager

        activityUtil = new ActivityUtil();
        ifNew = getIntent().getBooleanExtra("ifNew", true);
    }

    private void InitViewPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        if (!ifNew){
            viewPagerAdapter.addFragment(Edit_fragment_Activity.getInstance(filePath, fileName));
        }else {
            viewPagerAdapter.addFragment(new Edit_fragment_Activity());
        }
        viewPagerAdapter.addFragment(Display_fragment_Activity.getInstance());

        mViewPager.setAdapter(viewPagerAdapter);
        //设置默认打开第一页
        mViewPager.setCurrentItem(EDIT_MODE);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

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

    //菜单选项的逻辑
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_in_edit_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.withdraw_item:
                break;

            case R.id.save_item:
                saveAndExit();
                break;

            case R.id.regain_item:
                break;

            case R.id.share_item:
                FileUtils.shareFiles(new
                        File(intent.getStringExtra("FilePath"),
                        intent.getStringExtra("FileName")), EditActivity.this);
                break;

            case R.id.open_item:
                Toast.makeText(EditActivity.this, "未完成", Toast.LENGTH_SHORT).show();
                break;

            case R.id.export_item:
                Toast.makeText(EditActivity.this, "未完成", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveAndExit() {
        if (ifNew){
            File file = new File(filePath, fileName);
            if (file.exists()){
                Snackbar.make(mViewPager, "文件已存在", Snackbar.LENGTH_SHORT).show();
                return;
            }

            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
