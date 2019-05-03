package com.bkav.android.music.object;

public class Song {
    private long mId;
    private String mNameSong;
    private String mNameSinger;
    private String mTimeSong;
    private String mPath;
    private String mAlbum;
    private String mAlbumArt;
    private long mAlbumId;

    public long getmAlbumId() {
        return mAlbumId;
    }

    public void setmAlbumId(long mAlbumId) {
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

    public Song(String mNameSong, String mNameSinger, String mTimeSong, long mId, String mPath, String mAlbum,String mAlbumArt,long mAlbumId) {
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
}
