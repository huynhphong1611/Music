package com.bkav.android.music.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SongDataBaseHelper extends SQLiteOpenHelper {
    static final int FILE_DB_VERSION=1;
    static final String FILE_DB_NAME="file_db_song";
    static final String NAME_TABLE="song";
    static final String NAME_SONG="name_song";
    static final String NAME_SINGER="name_singer";
    static final String NAME_ALBUM="name_album";
    static final String PATH="path";
    static final String ID="id";
    public final String DATABASE_CREATE=" CREATE TABLE "
                    +NAME_TABLE
                    +" ( "
                    +ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    +NAME_SONG + " TEXT, "
                    +NAME_SINGER + " TEXT, "
                    +NAME_ALBUM + " TEXT, "
                    +PATH + " TEXT "
                    +" ); ";

    public SongDataBaseHelper(Context context) {
        super(context, FILE_DB_NAME, null, FILE_DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
