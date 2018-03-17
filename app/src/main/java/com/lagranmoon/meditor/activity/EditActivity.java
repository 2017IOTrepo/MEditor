package com.lagranmoon.meditor.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.lagranmoon.meditor.R;
import com.lagranmoon.meditor.adapter.ViewPagerAdapter;
import com.lagranmoon.meditor.base.BaseActivity;
import com.lagranmoon.meditor.fragment.Display_fragment_Activity;
import com.lagranmoon.meditor.fragment.Edit_fragment_Activity;
import com.lagranmoon.meditor.util.FileUtils;

import java.io.File;

public class EditActivity extends BaseActivity {

    private Context context;
    public final String TAG = "EditActivity";
    public final int EDIT_MODE = 0;
    public final int DISPLAY_MODE = 1;
    private Intent intent;

    private ViewPager mViewPager;//滑动效果

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);
        intent = getIntent();

        mViewPager = (ViewPager)findViewById(R.id.edit_View_Pager);
        InitViewPager();// 初始化ViewPager
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

    private void InitViewPager() {

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(Edit_fragment_Activity.newInstance());
        viewPagerAdapter.addFragment(Display_fragment_Activity.newInstance());

        mViewPager.setAdapter(viewPagerAdapter);
        //设置默认打开第一页
        mViewPager.setCurrentItem(EDIT_MODE);

    }

}
