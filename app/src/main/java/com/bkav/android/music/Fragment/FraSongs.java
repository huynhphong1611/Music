package com.bkav.android.music.Fragment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bkav.android.music.R;
import com.bkav.android.music.object.Song;
import com.bkav.android.music.provider.SongContact;

public class FraSongs extends Fragment {
    public void FraSongs(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_bai_hat,container,false);
        return view;
    }


    public void getAllMediaMp3FileInDb(Context context){
        Uri uri= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.Audio.AudioColumns.DATA,MediaStore.Audio.AudioColumns.TITLE ,MediaStore.Audio.AudioColumns.ALBUM, MediaStore.Audio.ArtistColumns.ARTIST};
        Cursor c = context.getContentResolver().query(uri, projection, MediaStore.Audio.Media.MIME_TYPE +"=?" ,new String[]{"audio/mpeg"}, null);

        if (c != null) {
            while (c.moveToNext()) {
                // Create a model object.
                Song song = new Song();

                String path = c.getString(0);   // Retrieve path.
                String name = c.getString(1);   // Retrieve name.
                String album = c.getString(2);  // Retrieve album name.
                String artist = c.getString(3); // Retrieve artist name.

                // Set data to the model object.
                song.setmNameSong(name);
                song.setmPath(path);
                song.setmNameSinger(artist);
                song.setmAlbum(album);
                Log.e("Name :" + name, " Album :" + album);
                Log.e("Path :" + path, " Artist :" + artist);

                // Add the model object to the db .
                ContentValues contentValues=new ContentValues();
                contentValues.put(SongContact.NAME_SONG,song.getmNameSong());
                contentValues.put(SongContact.NAME_SINGER,song.getmNameSinger());
                contentValues.put(SongContact.NAME_ALBUM,song.getmAlbum());
                contentValues.put(SongContact.PATH,song.getmPath());
                context.getContentResolver().insert(SongContact.CONTENT_URI,contentValues);
            }
            c.close();
        }

    }
}
