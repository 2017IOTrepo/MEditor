package com.lagranmoon.meditor.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.lagranmoon.meditor.R;
import com.lagranmoon.meditor.adapter.FileAdapter;
import com.lagranmoon.meditor.bean.Files;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FileAdapter.OnItemClickLitener {

    private SearchView searchView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private RecyclerView mRecyclerView;
    private List<Files> mFiles = new ArrayList<>();
    private FileAdapter fileAdapter;
    private Context mContext;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton add_button = (FloatingActionButton) findViewById(R.id.add_button_in_mainactivity);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //仅是跳转到edit_activity 未完成文件操作
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refres);
        mSwipeRefreshLayout.setColorSchemeColors(R.color.colorDefault);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFiles();
            }
        });

//        //初始化recyclerView
//        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.setAdapter(fileAdapter = new FileAdapter(mFiles, mContext));
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        mRecyclerView.setLongClickable(true);
//        fileAdapter.setOnItemClickLitener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }


    }

    private void initFiles() {
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nightTheme) {
            Toast.makeText(MainActivity.this, "未完成", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.action_settings) {
            Toast.makeText(MainActivity.this, "未完成", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.diaryUI){
            Toast.makeText(MainActivity.this, "未完成", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_share) {
            Toast.makeText(MainActivity.this, "未完成", Toast.LENGTH_SHORT).show();

        }else if (id == R.id.nav_about){
            AboutActivity.startActivity(MainActivity.this);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /*
     * 以下为菜单栏的逻辑
     * 2018.02.07 仅作出搜索item逻辑
    */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //使用菜单填充器获取menu下的菜单
        getMenuInflater().inflate(R.menu.menu_in_main_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_settings:
                break;

            case R.id.search_item:
//                //获取搜索组件（按钮）
//                searchView = (SearchView) MenuItemCompat.getActionView(item);
//                //设置搜索事件
//                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//                    @Override
//                    public boolean onQueryTextSubmit(String query) {
//                        return false;
//                    }//搜索时逻辑
//
//                    @Override
//                    public boolean onQueryTextChange(String newText) {
//                        return false;
//                    }//文本更改时逻辑（即时搜索）
//                });
//          ！！未完成！！

            case R.id.star_files_item:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void refreshFiles() {

        //刷新搜索文件逻辑
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initFiles();
                    }
                });
            }
        });
    }

    @Override
    public void onItemClick(Files files) {

    }

    @Override
    public void onItemLongClick(Files files) {

    }
}
