package com.bkav.android.music.interfaces;

import com.bkav.android.music.object.Song;

import java.util.List;

public interface ClickPlaySongRandom {
    void takeSongRamdom(List<Song> listSong, int position);
}
