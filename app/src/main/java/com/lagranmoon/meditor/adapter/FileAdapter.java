package com.lagranmoon.meditor.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lagranmoon.meditor.R;
import com.lagranmoon.meditor.bean.Files;
import com.lagranmoon.meditor.util.Unitsutils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 13256 on 2018/3/2.
 */

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.FilesViewHolder> {

    private List<Files> mFiles = new ArrayList<>();
    private LayoutInflater mInflater;
    private boolean requsetDelete = false;

    private OnItemClickLitener mOnItemClickLitener;

    public FileAdapter(List<Files> files){
        this.mFiles = files;
    }

    //加删操作
    public void addFile(List<Files> files){
        mFiles.addAll(files);
    }

    public void removeFile(Files files){
        int position = mFiles.indexOf(files);
        mFiles.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public FilesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_part_default, parent, false);
        return new FilesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FilesViewHolder holder, int position) {
        holder.bindHolder(mFiles.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mFiles.size();
    }


    public interface OnItemClickLitener
    {
        void onItemClick(Files files);
        void onItemLongClick(Files files);
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    class FilesViewHolder extends RecyclerView.ViewHolder{
        private TextView fileName;
        private TextView fileTime;
        private TextView fileNumber;
        private ImageView starFiles;

        public FilesViewHolder(View itemView) {
            super(itemView);
            fileName = itemView.findViewById(R.id.file_name_text_view);
            fileTime = itemView.findViewById(R.id.file_time_text_view);
            fileNumber = itemView.findViewById(R.id.item_number_text_view);
            starFiles = itemView.findViewById(R.id.star_Image_View);
        }

        public void bindHolder(final Files files, int position) {
            fileNumber.setText((position + 1) + "");
            fileName.setText(files.getTitle());
            fileTime.setText(Unitsutils.friendlyTime(files.getDate()));
            if (!files.isIfStar())
                starFiles.setVisibility(View.GONE);
            if (mOnItemClickLitener != null) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickLitener.onItemClick(files);
                    }
                });

                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        mOnItemClickLitener.onItemLongClick(files);
                        return !requsetDelete;
                    }
                });
            }
        }
    }

}
