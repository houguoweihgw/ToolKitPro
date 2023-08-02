package com.hgw.toolkitpro.activity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.hgw.toolkitpro.R;
import com.hgw.toolkitpro.model.MediaItem;
import com.hgw.toolkitpro.model.MediaType;

public class MediaDetailActivity extends AppCompatActivity {
    private ImageView imageView;
    private VideoView videoView;
    private boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_detail);

        // 初始化视图
        imageView = findViewById(R.id.imageView);
        videoView = findViewById(R.id.videoView);

        // 获取传递过来的 MediaItem 对象
        MediaItem mediaItem = getIntent().getParcelableExtra("media_item");
        if (mediaItem != null) {
            if (mediaItem.getMediaType() == MediaType.PHOTO) {
                // 如果是相片，则显示图片
                imageView.setVisibility(View.VISIBLE);
                videoView.setVisibility(View.GONE);

                // 使用 Glide 或其他图片加载库显示图片
                Glide.with(this).load(mediaItem.getFilePath()).into(imageView);
            } else if (mediaItem.getMediaType() == MediaType.VIDEO) {
                // 如果是视频，则显示视频
                imageView.setVisibility(View.GONE);
                videoView.setVisibility(View.VISIBLE);

                // 使用 VideoView 播放视频
                Uri videoUri = Uri.parse(mediaItem.getFilePath());
                videoView.setVideoURI(videoUri);
                videoView.start();
                isPlaying=true;
                // 设置视频点击事件
                videoView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isPlaying) {
                            // 视频正在播放，暂停视频
                            videoView.pause();
                        } else {
                            // 视频暂停中或未开始，开始播放视频
                            videoView.start();
                        }
                        isPlaying = !isPlaying;
                    }
                });
            }
        }
    }
}

