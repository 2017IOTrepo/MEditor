package com.lagranmoon.meditor.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.lagranmoon.meditor.R;
import com.lagranmoon.meditor.adapter.ViewPagerAdapter;
import com.lagranmoon.meditor.base.BaseActivity;
import com.lagranmoon.meditor.fragment.DisplayFragment;
import com.lagranmoon.meditor.fragment.EditFragment;
import com.lagranmoon.meditor.util.ActivityUtil;
import com.lagranmoon.meditor.util.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.LinkedList;

public class EditActivity extends BaseActivity{

    private ActivityUtil activityUtil;
    private Context context = this;
    public final String TAG = "EditActivity";
    public final int EDIT_MODE = 0;
    private Intent intent;
    private String filePath;
    private String fileName;
    private boolean isNew = false;

    EditFragment editFragment;
    DisplayFragment displayFragment;

    private ViewPager mViewPager;//滑动效果

    private LinkedList<String> backList = new LinkedList<>(); // 回退队列

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);

        filePath = getIntent().getStringExtra(EditFragment.FILE_PATH_KEY);
        fileName = getIntent().getStringExtra(EditFragment.FILE_NAME_KEY);
        isNew = getIntent().getBooleanExtra(EditFragment.IF_NEW, false);

        mViewPager = (ViewPager)findViewById(R.id.edit_View_Pager);
        InitViewPager();// 初始化ViewPager

        activityUtil = new ActivityUtil();
    }

    /**
     * 初始化viewpager
     * */
    private void InitViewPager() {
        editFragment = EditFragment.getInstance(filePath, fileName, isNew);
        displayFragment = DisplayFragment.getInstance();
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.addFragment(editFragment);
        viewPagerAdapter.addFragment(displayFragment);

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
                displayFragment.setMarkdownContent(editFragment.getTextContent());
                displayFragment.setMarkdownTitle(editFragment.getFileTitle());
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
        // TODO 撤回恢复功能BUG(原因：死循环撤回修改)

        switch (item.getItemId()){
            // 撤回
            case R.id.withdraw_item:
                if (editFragment.beforeString.isEmpty()){
                    Toast.makeText(this, "没有可以撤回的东西了！", Toast.LENGTH_LONG).show();
                }else {
                    String beString = editFragment.beforeString.removeLast();
                    editFragment.setTextContent(beString);
                    backList.add(beString);
                }
                break;

            // 保存
            case R.id.save_item:
                save();
                break;

            // 恢复
            case R.id.regain_item:
                if (backList.isEmpty()){
                    Toast.makeText(this, "没有可以恢复的东西了！", Toast.LENGTH_LONG).show();
                }else{
                    String baString = backList.removeLast();
                    editFragment.setTextContent(baString);
                    editFragment.beforeString.add(baString);
                }
                break;

            // 分享
            case R.id.share_item:
                FileUtils.shareFiles(filePath, EditActivity.this);
                break;

            // PDF导出
            // TODO 利用iTEXT
            case R.id.export_item:
                Toast.makeText(EditActivity.this, "未完成", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 保存
     * 流写入
     * */
    void save(){
        if (isNew){
            filePath += "/" + editFragment.getFileTitle() + ".md";
        }
        System.out.println(filePath);
        if (editFragment.isTitleChanged && !isNew){
            filePath = FileUtils.changeFileName(filePath, editFragment.getFileTitle() + ".md", fileName);
        }
        try {
            FileWriter fileWriter = new FileWriter(new File(filePath));
            fileWriter.write(editFragment.getTextContent());
            fileWriter.close();
            editFragment.isTextChange = false;
            editFragment.isTitleChanged = false;
            Toast.makeText(this, "保存成功！", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "保存失败！", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(this, "保存失败！", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        return;
    }

    /**
     * 这里退出时检测是否有修改没有保存
     * */
    @Override
    public void onBackPressed() {
        if (editFragment.isTextChange || editFragment.isTitleChanged){
            askIsSave();
        }else {
            super.onBackPressed();
        }
    }

    /**
     * 询问是否保存
     * */
    private void askIsSave() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("提醒！")
                .setMessage("您有为保存的部分，是否为您保存？")
                .setCancelable(true)
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editFragment.isTextChange = false;
                        save();
                        onBackPressed();
                    }
                })
                .setNegativeButton("不用", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editFragment.isTitleChanged = false;
                        editFragment.isTextChange = false;
                        onBackPressed(); // 重新调用用以退出
                    }
                })
                .show();
    }
}
