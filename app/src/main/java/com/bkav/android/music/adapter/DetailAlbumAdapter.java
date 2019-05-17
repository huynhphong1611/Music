package com.bkav.android.music.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class DetailAlbumAdapter extends BaseCursorAdapter<DetailAlbumAdapter.ViewHolder>{
    private Context mContext;
    private String mNameSong,mNameSinger,mPath,mAlbumArt,mTimeSong;
    public DetailAlbumAdapter(Context mContext){
        this.mContext=mContext;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, Cursor cursor) {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }




    /****************************************************************/
    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
