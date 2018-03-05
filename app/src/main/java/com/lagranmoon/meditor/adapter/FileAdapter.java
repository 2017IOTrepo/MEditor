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

    public FileAdapter(Context context, List<Files> files){
        mInflater = LayoutInflater.from(context);
        this.mcontext = context;
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


    /*
    * 下面的接口和方法是从某些文件扒下来的
    * 不需要管太多 用的时候再用
    * */
    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view , int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }


    @Override
    public void onBindViewHolder(final FilesViewHolder holder, int position) {

        Files file = mFiles.get(position);

        holder.fileName.setText(file.getTitle());
        holder.fileTime.setText(Units.friendlyTime(file.getDate()));

        if (mOnItemClickLitener != null){

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int nowPos = holder.getAdapterPosition();
                    mOnItemClickLitener.onItemClick(holder.fileName, nowPos);
                }
            });

//            holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
//
//                @Override
//                public boolean onLongClick(View view) {
//                    int nowPos = holder.getAdapterPosition();
//                    mOnItemClickLitener.onItemClick(holder.itemView, nowPos);
//                    return !requsetDelete;
//                }
//            });
        }
    }

    @Override
    public int getItemCount() {
        return mFiles.size();
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
    }

}
