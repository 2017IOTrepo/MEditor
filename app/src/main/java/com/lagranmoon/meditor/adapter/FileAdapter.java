package com.lagranmoon.meditor.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lagranmoon.meditor.bean.Files;

import java.util.List;

/**
 * Created by 13256 on 2018/3/2.
 */

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder> {

    private List<Files> mFiles;
    private LayoutInflater minflater;
    private Context mcontext;
    String folder;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);

        }
    }

    @Override
    public FileAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(FileAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
