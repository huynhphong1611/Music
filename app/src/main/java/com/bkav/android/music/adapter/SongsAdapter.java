package com.bkav.android.music.adapter;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bkav.android.music.R;

public class SongsAdapter extends BaseCursorAdapter<SongsAdapter.ViewHolder> {
    private Context mContext;

    public SongsAdapter(Cursor c, Context mContext) {
        super(c);
        this.mContext = mContext;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, Cursor cursor) {
        if (cursor != null) {
            final int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
            final String nameSong = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
            final String nameSinger = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
            holder.txtNameSong.setText(nameSong);
            holder.txtNameSinger.setText(nameSinger);
            holder.imgSong.setImageBitmap(takeImgSong(cursor));
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        final View itemView = layoutInflater.inflate(R.layout.item_song, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void swapCursor(Cursor newCursor) {
        super.swapCursor(newCursor);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNameSong, txtNameSinger;
        ImageView imgSong, imgMenu;

        public ViewHolder(View itemView) {
            super(itemView);
            txtNameSong = (TextView) itemView.findViewById(R.id.txt_name_song);
            txtNameSinger = (TextView) itemView.findViewById(R.id.txt_name_singer);
            imgSong = (ImageView) itemView.findViewById(R.id.img_song);
            imgMenu = (ImageView) itemView.findViewById(R.id.img_menu_song);
        }
    }

    //lấy ảnh từ ablum ra thêm vào list
    public Bitmap takeImgSong(Cursor cursor) {
        long albumId = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));
        final Uri ART_CONTENT_URI = Uri.parse("content://media/external/audio/albumart");
        Uri albumArtUri = ContentUris.withAppendedId(ART_CONTENT_URI, albumId);

        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), albumArtUri);
        } catch (Exception exception) {
            // log error
        }
        return bitmap;
    }
}
