package com.bkav.android.music.object;

public class Song {
    private String mNameSong;
    private String mNameSinger;
    private String mTimeSong;
    public Song(){

    }
    public Song(String mNameSong, String mNameSinger, String mTimeSong) {
        this.mNameSong = mNameSong;
        this.mNameSinger = mNameSinger;
        this.mTimeSong = mTimeSong;
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
}
