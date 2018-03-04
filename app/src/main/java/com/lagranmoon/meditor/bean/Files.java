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

    public Files(String title, String path, Date date, long size){
        this.title = title;
        this.path = path;
        this.date = date;
        this.size = size;
    }

    @Override
    public String toString() {
        return "Files{" +
                "title='" + title + '\'' +
                ", path='" + path + '\'' +
                ", date=" + date +
                ", size=" + size +
                '}';
    }

    @Override
    public boolean equals(Object o) {

        if (o == null)
            return false;

        if (o == this)
            return true;

        if (o instanceof  Files){
            Files files = (Files)o;
            return title.equals(files.title) && date.equals(files.date);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return this.title.hashCode();
    }
}
