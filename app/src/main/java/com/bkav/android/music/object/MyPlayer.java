package com.bkav.android.music.object;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

public class MyPlayer {
    private MediaPlayer mMediaPlayer;
    private Context mContext;
    private String mPath;

    public void setmMediaPlayer(MediaPlayer mMediaPlayer) {
        this.mMediaPlayer = mMediaPlayer;
    }

    public Context getmContext() {
        return this.mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public MediaPlayer getmMediaPlayer() {
        return this.mMediaPlayer;
    }

    public MyPlayer(Context mContext, String mPath) {
        this.mContext = mContext;
        this.mMediaPlayer = MediaPlayer.create(mContext, Uri.parse(this.mPath = mPath));
    }

    // Tua bài hát (phát tiếp bài hát từ vị trí pos trở đi)
    public void fastForward(int pos) {
        mMediaPlayer.seekTo(pos);
    }

    // phát nhạc
    public void play() {
        if (mMediaPlayer != null) {
            mMediaPlayer.start();
        }
    }

    public void pause() {
        if (mMediaPlayer != null) {
            mMediaPlayer.pause();
        }
    }

    // dừng phát nhạc
    public void stop() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
        }
    }

    //end huy mediplayer
    public void end() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
        }
    }


}
