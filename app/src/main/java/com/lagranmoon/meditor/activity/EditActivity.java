package com.lagranmoon.meditor.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.lagranmoon.meditor.R;
import com.lagranmoon.meditor.adapter.ViewPagerAdapter;
import com.lagranmoon.meditor.base.BaseActivity;
import com.lagranmoon.meditor.fragment.Display_fragment;
import com.lagranmoon.meditor.fragment.Edit_fragment;
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
    private String filePath;
    private String fileName;

    private ViewPager mViewPager;//滑动效果

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);

        filePath = getIntent().getStringExtra("filePath");
        fileName = getIntent().getStringExtra("fileName");

        mViewPager = (ViewPager)findViewById(R.id.edit_View_Pager);
        InitViewPager();// 初始化ViewPager

        activityUtil = new ActivityUtil();
    }

    private void InitViewPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        // 查看是否是新建文件
        viewPagerAdapter.addFragment(Edit_fragment.getInstance(filePath, fileName,
                getIntent().getBooleanExtra("ifNew", true)));
        viewPagerAdapter.addFragment(Display_fragment.getInstance());

        mViewPager.setAdapter(viewPagerAdapter);
        //设置默认打开第一页
        mViewPager.setCurrentItem(EDIT_MODE);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            /**
             * 传递内容以及markdown解析
             * */
            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //菜单选项的逻辑
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_in_edit_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO 撤回恢复功能

        switch (item.getItemId()){
            // 撤回
            case R.id.withdraw_item:
                break;

            // 保存
            case R.id.save_item:
                saveAndExit();
                break;

            // 恢复
            case R.id.regain_item:
                break;

            // 分享
            case R.id.share_item:
                FileUtils.shareFiles(new
                        File(intent.getStringExtra("FilePath"),
                        intent.getStringExtra("FileName")), EditActivity.this);
                break;

            // PDF导出
            case R.id.export_item:
                Toast.makeText(EditActivity.this, "未完成", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
