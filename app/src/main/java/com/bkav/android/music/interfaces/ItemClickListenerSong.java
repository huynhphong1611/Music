package com.bkav.android.music.interfaces;

import android.database.Cursor;

import com.bkav.android.music.object.Song;

import java.util.List;

public interface ItemClickListenerSong {
    void takeSongFromAdapter(List<Song> listSong, int positon);
}
