package com.bkav.android.music.interfaces;

import android.database.Cursor;

import com.bkav.android.music.object.Song;

import java.util.List;

public interface OnSelectedListener {
    void onSelectedListener(List<Song> listSong, int position);
}
