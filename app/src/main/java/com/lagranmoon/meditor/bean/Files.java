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
    private boolean ifStar = false;

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

    public boolean isIfStar() {
        return ifStar;
    }

    public void setIfStar(boolean ifStar) {
        this.ifStar = ifStar;
    }

    public Files(String title, String path, Date date, long size, boolean ifStar){
        this.title = title;
        this.path = path;
        this.date = date;
        this.size = size;
        this.ifStar = ifStar;
    }

    public Files() {
    }

    @Override
    public String toString() {
        return "Files{" +
                "title='" + title + '\'' +
                ", path='" + path + '\'' +
                ", date=" + date +
                ", size=" + size +
                ", ifStar=" + ifStar +
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
