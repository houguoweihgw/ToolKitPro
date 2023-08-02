package com.hgw.toolkitpro.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hgw.toolkitpro.R;
import com.hgw.toolkitpro.model.MediaItem;
import com.hgw.toolkitpro.model.MediaType;
import com.bumptech.glide.Glide;


import java.io.IOException;
import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
    private List<MediaItem> mediaItemList;
    private Context context;
    private OnItemClickListener onItemClickListener;
    public GalleryAdapter(Context context, List<MediaItem> mediaItemList) {
        this.context = context;
        this.mediaItemList = mediaItemList;
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(MediaItem mediaItem);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryAdapter.ViewHolder holder, int position) {
        MediaItem mediaItem = mediaItemList.get(position);
        if (mediaItem.getMediaType() == MediaType.PHOTO) {
            Glide.with(holder.itemView.getContext())
                    .load(mediaItem.getFilePath())
                    .placeholder(R.drawable.ic_photo_placeholder)
                    .into(holder.imageView);
        } else if (mediaItem.getMediaType() == MediaType.VIDEO) {
            // 获取视频文件的Uri
            Uri videoUri = Uri.parse(mediaItem.getFilePath());
            // 使用MediaMetadataRetriever获取视频的第一帧图像
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(context, videoUri);
            // 获取第一帧图像
            Bitmap frameBitmap = retriever.getFrameAtTime(0);
            try {
                retriever.release();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Glide.with(holder.itemView.getContext())
                    .load(frameBitmap)
                    .into(holder.imageView);
        }

        // 设置点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 触发点击事件回调
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(mediaItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mediaItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);

        }
    }
}
