package com.lagranmoon.meditor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lagranmoon.meditor.R;
import com.lagranmoon.meditor.bean.Files;
import com.lagranmoon.meditor.util.Units;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 13256 on 2018/3/2.
 */

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.FilesViewHolder> {

    private List<Files> mFiles;
    private LayoutInflater mInflater;
    private Context mcontext;
    private boolean requsetDelete = false;

    public FileAdapter(List<Files> files, Context context){
        mInflater = LayoutInflater.from(context);
        this.mcontext = context;
        mFiles = files;
    }

    //加删操作
    public void addFile(int position, Files files){
        mFiles.add(position, files);
        notifyItemInserted(position);
    }

    public void removeFile(Files files){
        int position = mFiles.indexOf(files);
        mFiles.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public FilesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.file_part_default, parent, false);
        FilesViewHolder viewHolder = new FilesViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final FilesViewHolder holder, int position) {
        holder.bindHolder(mFiles.get(position));
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

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    class FilesViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.file_name_text_view)
        TextView fileName;
        @BindView(R.id.file_time_text_view)
        TextView fileTime;
        @BindView(R.id.star_Image_View)
        ImageView starFiles;

        public FilesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
        }

        public void bindHolder(final Files files) {
            fileTime.setText(files.getTitle());
            fileTime.setText(Units.friendlyTime(files.getDate()));
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
