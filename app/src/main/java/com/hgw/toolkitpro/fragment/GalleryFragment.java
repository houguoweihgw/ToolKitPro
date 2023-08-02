package com.hgw.toolkitpro.fragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hgw.toolkitpro.R;
import com.hgw.toolkitpro.activity.MediaDetailActivity;
import com.hgw.toolkitpro.adapter.GalleryAdapter;
import com.hgw.toolkitpro.model.MediaItem;
import com.hgw.toolkitpro.model.MediaType;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {
    private RecyclerView recyclerView;
    private GalleryAdapter galleryAdapter;
    private List<MediaItem> mediaItemList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        // 1. 找到RecyclerView控件的引用
        recyclerView = view.findViewById(R.id.recyclerView);

        // 2. 加载相片和视频缩略图数据
        loadMediaData();

        // 3. 创建GalleryAdapter适配器
        galleryAdapter = new GalleryAdapter(getActivity(),mediaItemList);

        // 4. 设置RecyclerView的布局管理器和适配器
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        recyclerView.setAdapter(galleryAdapter);

        // 设置点击事件监听器
        galleryAdapter.setOnItemClickListener(new GalleryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MediaItem mediaItem) {
                // 点击项的处理：跳转到MediaDetailActivity并传递选中的MediaItem
                Intent intent = new Intent(getContext(), MediaDetailActivity.class);
                intent.putExtra("media_item", mediaItem);
                startActivity(intent);
            }
        });
        return view;
    }

    private void loadMediaData() {
        // 获取ContentResolver对象
        ContentResolver contentResolver = requireContext().getContentResolver();

        // 查询系统相册中的相片数据
        String[] projectionPhotos = {MediaStore.Images.Media.DATA};
        Cursor cursorPhotos = contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projectionPhotos,
                null,
                null,
                null);

        // 查询系统相册中的视频数据
        String[] projectionVideos = {MediaStore.Video.Media.DATA};
        Cursor cursorVideos = contentResolver.query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                projectionVideos,
                null,
                null,
                null);

        // 将查询结果添加到相册数据源中
        if (cursorPhotos != null && cursorPhotos.moveToFirst()) {
            do {
                int columnIndex = cursorPhotos.getColumnIndex(MediaStore.Images.Media.DATA);
                String filePath = cursorPhotos.getString(columnIndex);
                mediaItemList.add(new MediaItem(filePath, MediaType.PHOTO));
            } while (cursorPhotos.moveToNext());
            cursorPhotos.close();
        }

        if (cursorVideos != null && cursorVideos.moveToFirst()) {
            do {
                int columnIndex = cursorVideos.getColumnIndex(MediaStore.Video.Media.DATA);
                String filePath = cursorVideos.getString(columnIndex);
                mediaItemList.add(new MediaItem(filePath, MediaType.VIDEO));
            } while (cursorVideos.moveToNext());
            cursorVideos.close();
        }
    }
}
