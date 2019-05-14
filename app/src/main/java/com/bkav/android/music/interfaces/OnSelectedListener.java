package com.bkav.android.music.interfaces;

import android.database.Cursor;

import com.bkav.android.music.object.Song;

public interface OnSelectedListener {
    void onSelectedListener(Cursor cursor, int position);
}
