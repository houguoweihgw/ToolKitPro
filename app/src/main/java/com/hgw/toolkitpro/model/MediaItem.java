package com.hgw.toolkitpro.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class MediaItem implements Parcelable {
    private String filePath;
    private MediaType mediaType;

    public MediaItem(String filePath, MediaType mediaType) {
        this.filePath = filePath;
        this.mediaType = mediaType;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(filePath);
        parcel.writeInt(mediaType.ordinal());
    }

    // 从 Parcel 创建 MediaItem 的构造方法
    protected MediaItem(Parcel in) {
        filePath = in.readString();
        mediaType = MediaType.values()[in.readInt()];
    }

    public static final Creator<MediaItem> CREATOR = new Creator<MediaItem>() {
        @Override
        public MediaItem createFromParcel(Parcel in) {
            return new MediaItem(in);
        }

        @Override
        public MediaItem[] newArray(int size) {
            return new MediaItem[size];
        }
    };
}

