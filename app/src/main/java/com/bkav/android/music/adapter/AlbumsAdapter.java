package com.bkav.android.music.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bkav.android.music.R;
import com.bkav.android.music.provider.SongContact;

public class AlbumsAdapter extends BaseCursorAdapter<AlbumsAdapter.ViewHolder> {

    private Context mContext;

    public AlbumsAdapter(Cursor c, Context mContext) {
        super(c);
        this.mContext = mContext;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, Cursor cursor) {
        //set cursor

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(viewGroup.getContext());
        final View itemView=layoutInflater.inflate(R.layout.item_album,viewGroup,false);
        return new ViewHolder(itemView);
    }
    @Override
    public void swapCursor(Cursor newCursor) {
        super.swapCursor(newCursor);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNameSong,txtNameSinger;
        ImageView imgSong,imgMenu;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameSong=(TextView) itemView.findViewById(R.id.txt_Name_Song_Album);
            txtNameSinger=(TextView) itemView.findViewById(R.id.txt_Name_Singer_Album);
            imgSong = (ImageView) itemView.findViewById(R.id.img_Song_Album);
            imgMenu = (ImageView) itemView.findViewById(R.id.img_Menu_Album);
        }
    }
}
