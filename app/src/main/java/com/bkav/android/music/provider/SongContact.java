package com.bkav.android.music.provider;

import android.net.Uri;

public class SongContact {
    public static final String FILE_DB_NAME="file_db_song";
    public static final String NAME_TABLE="song";
    public static final String NAME_SONG="name_song";
    public static final String NAME_SINGER="name_singer";
    public static final String NAME_ALBUM="name_album";
    public static final String PATH="path";
    public static final String ID="id";
    static final String SINGLE_NOTE_MIME_TYPE =
            "vnd.android.cursor.item/vnd.com.bkav.android.music.song";
    static final String MULTIPLE_NOTES_MIME_TYPE =
            "vnd.android.cursor.dir/vnd.com.bkav.android.music.song";

    public static final String AUTHORITY = "com.bkav.android.music.provider.SongProvider";
    public static final String CONTENT_PATH =  "song";
    public static final String URL = "content://" + AUTHORITY + "/" + CONTENT_PATH;
    public static final Uri CONTENT_URI = Uri.parse(URL);
}
