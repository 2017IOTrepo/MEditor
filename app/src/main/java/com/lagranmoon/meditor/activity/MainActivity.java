package com.lagranmoon.meditor.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.lagranmoon.meditor.R;
import com.lagranmoon.meditor.adapter.FileAdapter;
import com.lagranmoon.meditor.base.BaseActivity;
import com.lagranmoon.meditor.bean.Files;
import com.lagranmoon.meditor.util.ActivityCollector;
import com.lagranmoon.meditor.util.FileUtils;
import com.lagranmoon.meditor.util.RequestPermissions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, FileAdapter.OnItemClickLitener {

    @BindView(R.id.empty_content)
    protected View emptyContent;

    private SearchView searchView;
    private boolean IsSearchViewShow = false;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private RecyclerView mRecyclerView;
    private List<Files> mFiles = new ArrayList<>();
    private FileAdapter fileAdapter;
    private Context mContext;

    private File file;
    private String rootFilePath;
    private List<Files> filesList = new ArrayList<>();


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
                creatNote();
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



        //权限申请相关
        RequestPermissions.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                new RequestPermissions.OnPermissionsRequestListener() {
                    @Override
                    public void onGranted() {
                        mFiles = getAllFiles(rootFilePath);
                        getFileListSucceed(mFiles);
                        initRecyclerView(mFiles);
                    }

                    @Override
                    public void onDenied(List<String> deniedList) {
                        ActivityCollector.finishiAll();
                    }
                });

        //新建存放markdown文件的文件夹
        rootFilePath = FileUtils.getRootFolder(mContext);
        file = new File(rootFilePath + "/MEditor_works");
        if (!file.exists()){
            file.mkdir();
        }

    }

    /*
    * 新建文本
    *
    * */
    private void creatNote() {

        //按下新建按钮先新建一个文件（类似缓存）
        Intent intent = new Intent(MainActivity.this, EditActivity.class);
        startActivity(intent);
        File newFile = new File(rootFilePath + "/MEditor_works/will_used.md");
        try {
            newFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //打开文件
        String path = newFile.getPath();
        intent.setDataAndType(Uri.fromFile(new File(path)), "file");
        mContext.startActivity(intent);
    }

    /*
    * 列表获取完成
    *
    * */
    public void getFileListSucceed(List<Files> files){
        fileAdapter.notifyDataSetChanged();

        if (files.isEmpty()){
            emptyContent.setVisibility(View.VISIBLE);
        }else {
            emptyContent.setVisibility(View.GONE);
        }

        finshRefresh();
    }

    /*
    * 停止刷新
    *
    * */
    private void finshRefresh() {

        if (!mSwipeRefreshLayout.isRefreshing()){
            return;
        }

        mSwipeRefreshLayout.setRefreshing(false);
        return;
    }

    /*
    * 开始刷新
    *
    * */
    public void refresh(){
        if (mSwipeRefreshLayout.isRefreshing()){
            return;
        }

        mSwipeRefreshLayout.setRefreshing(true);
        onRefreshFiles();
        return;
    }

    /*
    * 刷新文件
    *
    * */
    private void onRefreshFiles() {
    }

    /*
    * 返回键逻辑
    *
    * */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else if (searchView != null && searchView.isShown() && IsSearchViewShow){
            searchView.onActionViewCollapsed();
            searchView.setQuery("", false);
            IsSearchViewShow = false;
        } else {
            super.onBackPressed();
        }


    }


    //获取文件
    private void getFiles() {

        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();

    }

    /*
    * 获取sd卡中的所有.md文件
    *
    * */
    public List<Files> getAllFiles(String FilePath){
        String fileName;
        String suf;//文件后缀名
        File dir = new File(FilePath);
        File[] files = dir.listFiles();//获取文件夹下的所有文件及文件夹

        if (files == null)
            return null;

        for (int i = 0; i < files.length; i++) {

            if (files[i].isDirectory()){
                getAllFiles(files[i].getAbsolutePath());//递归获取下一级文件(夹)
            }else {
                //下面为获取文件后缀并存储
                fileName = files[i].getName();
                int j = fileName.lastIndexOf(".");
                suf = fileName.substring(j+1);//.后面即为文件后缀

                if (suf.equalsIgnoreCase("md") || suf.equalsIgnoreCase("mdown")||
                        suf.equalsIgnoreCase("markdown")){
                    filesList.add(FileUtils.getFile(files[i]));
                }
            }

        }
        return filesList;
    }

    //初始化recyclerView
    private void initRecyclerView(List<Files> mFiles) {
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(fileAdapter = new FileAdapter(mFiles));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLongClickable(true);
        fileAdapter.setOnItemClickLitener(this);

    }


    /*
    * 侧边栏菜单逻辑
    *
    * */
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
        initSearchView(menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_settings:
                break;

            case R.id.star_files_item:
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    /*
    * 搜索逻辑
    *
    * */
    private void initSearchView(Menu menu) {

        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView)menu.findItem(R.id.search_item).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b)
                    IsSearchViewShow = true;
            }
        });
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
                        getFiles();
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
