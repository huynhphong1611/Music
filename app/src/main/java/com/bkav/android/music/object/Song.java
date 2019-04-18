package com.bkav.android.music.object;

public class Song {
    private long mId;
    private String mNameSong;
    private String mNameSinger;
    private String mTimeSong;
    public Song(){

    }
    public Song(String mNameSong, String mNameSinger, String mTimeSong,long mId) {
        this.mNameSong = mNameSong;
        this.mNameSinger = mNameSinger;
        this.mTimeSong = mTimeSong;
        this.mId=mId;
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
}
