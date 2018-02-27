package com.lagranmoon.meditor.activity;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;

import com.lagranmoon.meditor.R;
import com.lagranmoon.meditor.fragment.Display_fragment_Activity;
import com.lagranmoon.meditor.fragment.Edit_fragment_Activity;

import java.util.ArrayList;
import java.util.List;

public class EditActivity extends AppCompatActivity {

    private Context context;
    public final String TAG = "EditActivity";
    public final int EDIT_MODE = 0;
    public final int DISPLAY_MODE = 1;

    private ViewPager mViewPager;//滑动效果
    private int currentIndex = 0;//当前页卡标号
    private ArrayList<Fragment> fragmentArrayList;//存放fragment数组
    private FragmentManager fragmentManager;//管理fragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);

        mViewPager = (ViewPager)findViewById(R.id.edit_View_Pager);
        InitFragment();//初始化fragment
        InitViewPager();// 初始化ViewPager
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
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

            case R.id.open_item:
                break;

            case R.id.export_item:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void InitFragment() {
        fragmentArrayList = new ArrayList<Fragment>();
        fragmentArrayList.add(new Edit_fragment_Activity());
        fragmentArrayList.add(new Display_fragment_Activity());

        fragmentManager = getSupportFragmentManager();
    }

    private void InitViewPager() {

        //fragment适配器
        mViewPager.setAdapter(new FragmentPagerAdapter(fragmentManager) {

            @Override
            public Fragment getItem(int position) {
                return fragmentArrayList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentArrayList.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                super.destroyItem(container, position, object);
            }
        });

        //ViewPager缓存2个页面
        mViewPager.setOffscreenPageLimit(2);
        //设置默认打开第一页
        mViewPager.setCurrentItem(EDIT_MODE);

    }

}
