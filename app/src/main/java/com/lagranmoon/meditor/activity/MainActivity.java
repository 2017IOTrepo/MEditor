package com.lagranmoon.meditor.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
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

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, FileAdapter.OnItemClickLitener {

    private Toolbar toolbar;
    private FloatingActionButton add_button;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private TextView emptyContent;
    private boolean ifPortrait = false;

    private SearchView searchView;
    private boolean IsSearchViewShow = false;
    private List<Files> SearchResultFilesList = null;

    // 刷新
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    // 文件列表
    private List<Files> mFiles = new ArrayList<>();
    private Files tempFiles;
    // rerecyclerview的Adapter
    private FileAdapter fileAdapter;
    private Context mContext;
    private SharedPreferences portrait_Pref;
    private SharedPreferences.Editor portrait_editor;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private File file;
    private String rootFilePath;

    private long customTime = 0;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        setSupportActionBar(toolbar);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //仅是跳转到edit_activity 未完成文件操作
                creatNote();
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        mSwipeRefreshLayout.setColorSchemeColors(R.color.colorDefault);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mFiles.clear();
                loadFileList();
                finshRefresh();
            }
        });

        //权限申请相关
        RequestPermissions.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                new RequestPermissions.OnPermissionsRequestListener() {
                    @Override
                    public void onGranted() {
                        rootFilePath = FileUtils.getRootFolder(mContext);
                        editor = pref.edit();
                        loadFileList();
                    }

                    @Override
                    public void onDenied(List<String> deniedList) {
                        ActivityCollector.finishiAll();
                    }
                });

        //新建存放markdown文件的文件夹
        file = new File(rootFilePath + "/MEditor_works");
        if (!file.exists()) {
            file.mkdir();
        }

        //portrait_editor = getSharedPreferences("properties", MODE_PRIVATE).edit();
        portrait_Pref = getSharedPreferences("properties", MODE_PRIVATE);
        ifPortrait = portrait_Pref.getBoolean("ifPortrait", ifPortrait);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        if (!(searchView != null && searchView.isShown() && IsSearchViewShow)){
            mFiles.clear();
            loadFileList();
        }

        ifPortrait = portrait_Pref.getBoolean("ifPortrait", ifPortrait);

    }

    /**
     * 初始化
     * */
    void initView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        add_button = (FloatingActionButton) findViewById(R.id.add_button_in_mainactivity);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refres);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /*
    * 新建文本
    * */
    private void creatNote() {
        mContext = MainActivity.this;
        Intent intent = new Intent(mContext, EditActivity.class);
        intent.putExtra("ifNew", true);
        intent.setAction(Intent.ACTION_VIEW);
        //设置数据URI与数据类型匹配
        mContext.startActivity(intent);
   }

    /*
    * 读取文件
    *
    * */
    private void loadFileList() {
        getAllFiles(rootFilePath + "/MEditor_works");
        getFileListSucceed();
        initRecyclerView(mFiles);
    }

    /*
    * 列表获取完成
    *
    * */
    private void getFileListSucceed() {
        emptyContent = (TextView) findViewById(R.id.empty_content);
        if (mFiles.isEmpty()) {
            emptyContent.setVisibility(View.VISIBLE);
        } else {
            emptyContent.setVisibility(View.GONE);
        }

        finshRefresh();
    }

    /*
    * 停止刷新
    *
    * */
    private void finshRefresh() {

        if (!mSwipeRefreshLayout.isRefreshing()) {
            return;
        }

        mSwipeRefreshLayout.setRefreshing(false);
        return;
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
        } else if (searchView != null && searchView.isShown() && IsSearchViewShow) {
            searchView.onActionViewCollapsed();
            searchView.setQuery("", false);
            IsSearchViewShow = false;
            initRecyclerView(mFiles);
        } else {

            //软件退出逻辑
            if (Math.abs(customTime - System.currentTimeMillis()) < 2000) {
                finish();
            } else {// 提示用户退出
                customTime = System.currentTimeMillis();
                Snackbar.make(toolbar, "再按一次返回键退出", Snackbar.LENGTH_SHORT).show();
            }

        }


    }

    /*
    * 读取指定文件夹内文件
    * 利用递归
    * */
    private int getAllFiles(String FilePath) {
        String fileName;
        String suf;//文件后缀名
        File dir = new File(FilePath);
        File[] files = dir.listFiles();//获取文件夹下的所有文件及文件夹

        if (files == null)
            return 0;

        for (int i = 0; i < files.length; i++) {

            if (files[i].isDirectory()) {
                getAllFiles(files[i].getAbsolutePath());//递归获取下一级文件(夹)
            } else {
                //下面为获取文件后缀并存储
                fileName = files[i].getName();
                int j = fileName.lastIndexOf(".");
                suf = fileName.substring(j + 1);//.后面即为文件后缀

                if (suf.equalsIgnoreCase("md") || suf.equalsIgnoreCase("mdown") ||
                        suf.equalsIgnoreCase("markdown")) {
                    tempFiles = FileUtils.getFile(files[i]);
                    mFiles.add(tempFiles);

                    Log.d("FileName", files[i].getName());
                }
            }

        }
        return 1;
    }

    //初始化recyclerView
    private void initRecyclerView(List<Files> mFiles) {
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
            SettingsActivity.startActivity(MainActivity.this);
        } else if (id == R.id.diaryUI) {
            Toast.makeText(MainActivity.this, "未完成", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_about) {
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
        SearchViewUsing(menu);
        return super.onCreateOptionsMenu(menu);
    }

    /*
    * 搜索逻辑
    *
    * */
    private void SearchViewUsing(Menu menu) {

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search_item).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!FileUtils.isEmpty(query)) {
                    SearchResultFilesList = new ArrayList<>();

                    //文件搜索逻辑
                    for (Files containFile:
                            mFiles) {
                        if (containFile.getTitle().contains(query))
                            SearchResultFilesList.add(containFile);
                    }
                    getFileListSucceed();
                    initRecyclerView(SearchResultFilesList);
                }else if (query == ""){
                    initRecyclerView(mFiles);
                }
                searchView.setIconified(false);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        /**
         * 查询视图逻辑
         * */
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b)
                    IsSearchViewShow = true;
            }
        });
    }

    /*
    * 文件短按逻辑
    * 打开
    * */
    @Override
    public void onItemClick(Files files) {
        FileUtils.openFiles(files, MainActivity.this);
    }

    /*
    * 文件长按逻辑
    * 删除or分享
    * */
    @Override
    public void onItemLongClick(final Files files) {
        String[] choice = {"删除", "分享"};

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setItems(choice, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                new File(files.getPath()).delete();
                                mFiles.remove(files);
//                              initRecyclerView(mFiles);
//                              getFileListSucceed();
                                Toast.makeText(MainActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                break;

                            case 1:
                                FileUtils.shareFiles(new File(files.getPath(), files.getTitle()), MainActivity.this);
                                break;
                        }

                    }
                })
                .show();
    }
}

