package com.lagranmoon.meditor.bean;

import java.util.Date;

/**
 * Created by 13256 on 2018/2/27.
 */

//文件的实体类
public class Files {

    private String title;
    private String path;
    private Date date;
    private long size;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Files(String title, String path, Date date, long size){
        this.title = title;
        this.path = path;
        this.date = date;
        this.size = size;
    }

    public Files() {
    }

    @Override
    public String toString() {
        return  "标题: " + title +
                "\n路径:" + path +
                "\n日期" + date +
                "\n大小" + size ;
    }
}
