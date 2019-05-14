package com.bkav.android.music.object;

import android.os.Parcel;
import android.os.Parcelable;

public class Song implements Parcelable {
    private long mId;
    private String mNameSong;
    private String mNameSinger;
    private String mTimeSong;
    private String mPath;
    private String mAlbum;
    private String mAlbumArt;
    private String mAlbumId;

    public String getmAlbumId() {
        return mAlbumId;
    }

    public void setmAlbumId(String mAlbumId) {
        this.mAlbumId = mAlbumId;
    }

    public String getmAlbumArt() {
        return mAlbumArt;
    }

    public void setmAlbumArt(String mAlbumArt) {
        this.mAlbumArt = mAlbumArt;
    }

    public Song() {

    }

    public Song(String mNameSong, String mNameSinger, String mTimeSong, long mId
            , String mPath, String mAlbum,String mAlbumArt,String mAlbumId) {
        this.mNameSong = mNameSong;
        this.mNameSinger = mNameSinger;
        this.mTimeSong = mTimeSong;
        this.mId = mId;
        this.mPath = mPath;
        this.mAlbum = mAlbum;
        this.mAlbumArt=mAlbumArt;
        this.mAlbumId=mAlbumId;
    }

    public String getmNameSong() {
        return mNameSong;
    }

    public void setmNameSong(String mNameSong) {
        this.mNameSong = mNameSong;
    }

    public String getmNameSinger() {
        return mNameSinger;
    }

    public void setmNameSinger(String mNameSinger) {
        this.mNameSinger = mNameSinger;
    }

    public String getmTimeSong() {
        return mTimeSong;
    }

    public void setmTimeSong(String mTimeSong) {
        this.mTimeSong = mTimeSong;
    }

    public long getmId() {
        return mId;
    }

    public void setmId(long mId) {
        this.mId = mId;
    }

    public String getmPath() {
        return mPath;
    }

    public void setmPath(String mPath) {
        this.mPath = mPath;
    }

    public String getmAlbum() {
        return mAlbum;
    }

    public void setmAlbum(String mAlbum) {
        this.mAlbum = mAlbum;
    }

    protected Song(Parcel in) {
        mId = in.readLong();
        mNameSong = in.readString();
        mNameSinger = in.readString();
        mTimeSong = in.readString();
        mPath = in.readString();
        mAlbum = in.readString();
        mAlbumArt = in.readString();
        mAlbumId = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeString(mNameSong);
        dest.writeString(mNameSinger);
        dest.writeString(mTimeSong);
        dest.writeString(mPath);
        dest.writeString(mAlbum);
        dest.writeString(mAlbumArt);
        dest.writeString(mAlbumId);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Song> CREATOR = new Parcelable.Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };
}
