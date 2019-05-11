package com.bkav.android.music.object;

import android.content.Context;
import android.media.MediaPlayer;

public class MyPlayer {
    private MediaPlayer mMediaPlayer;
    private Context mContext;
    private String mPath;
    public MyPlayer(Context mContext,String mPath) {
        this.mPath=mPath;
        this.mContext = mContext;
    }


}
