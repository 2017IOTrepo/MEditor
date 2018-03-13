package com.lagranmoon.meditor.util;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;

import com.lagranmoon.meditor.bean.Files;

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

        return files;
    }

    /**
     * 照顾旧机型
     *
     * Gets root path.
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
     * @param input
     * @return boolean
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
}
