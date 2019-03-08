package com.lagranmoon.meditor.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.ViewUtils;
import android.widget.Toast;

import com.lagranmoon.meditor.activity.EditActivity;
import com.lagranmoon.meditor.activity.MainActivity;
import com.lagranmoon.meditor.bean.Files;
import com.lagranmoon.meditor.fragment.EditFragment;

import java.io.File;
import java.util.Date;

/**
 * Created by xmmmmovo on 2018/3/9.
 */

public class FileUtils {
    //删除文件
    public static void deleteFile(File file){
        file.delete();
    }



    //将File转化为Files类
    public static Files getFile(File file){
        Files files = new Files();
        files.setTitle(file.getName());
        files.setPath(file.getAbsolutePath());
        files.setDate(new Date(file.lastModified()));
        System.out.println(files.getPath());
        return files;
    }

    /**
     * 照顾旧机型
     * 获取根目录
     *
     * @param context the context
     * @return the root path
     * @description 获取存储路径(如果有内存卡，这是内存卡根目录，如果没有内存卡，则是软件的包file目录)
     */
    public static String getRootFolder(@NonNull Context context) {
        String rootPath = null;

        if (android.os.Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            rootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            rootPath = context.getFilesDir().getAbsolutePath();
        }
        return rootPath;
    }

    /**
     * 判断给定字符串是否空白串。
     * 空白串是指由空格、制表符、回车符、换行符组成的字符串
     * 若输入字符串为null或空字符串，返回true
     * ViewRoot
     *
     * @param input 输入字符串
     * @return boolean 返回是否
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    public static void openFiles(Files files, Context context) {
        Intent intent = new Intent(context, EditActivity.class);
        intent.putExtra(EditFragment.FILE_NAME_KEY, files.getTitle());
        intent.putExtra(EditFragment.FILE_PATH_KEY, files.getPath());
        intent.setAction(Intent.ACTION_VIEW);
        //设置数据URI与数据类型匹配
        intent.setDataAndType(Uri.fromFile(new File(files.getPath())), "file");
        context.startActivity(intent);
    }

    /*
    * 分享文件
    * */
    public static void shareFiles(File file, Context mContext){
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        share.setType("*/*");
        mContext.startActivity(Intent.createChooser(share, "分享"));
    }
}
