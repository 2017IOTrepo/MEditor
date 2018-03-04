package com.lagranmoon.meditor.bean;

import java.util.Date;

/**
 * Created by 13256 on 2018/2/27.
 */

//文件的实体类
public class Files {

    private String title;
    private String Path;
    private Date date;
    private long size;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
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

    @Override
    public String toString() {
        return "Files{" +
                "title='" + title + '\'' +
                ", Path='" + Path + '\'' +
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
