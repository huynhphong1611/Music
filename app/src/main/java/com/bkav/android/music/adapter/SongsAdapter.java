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
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bkav.android.music.R;
import com.bkav.android.music.interfaces.ClickPlaySongRandom;
import com.bkav.android.music.interfaces.ItemClickLIstenerRecyclerView;
import com.bkav.android.music.interfaces.ItemClickListenerSong;
import com.bkav.android.music.object.Song;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

public class SongsAdapter extends BaseCursorAdapter<SongsAdapter.ViewHolder> {
    private Context mContext;
    private String mNameSong,mNameSinger,mPath,mAlbumArt,mTimeSong;
    private List<Song> mListSong;
    private ItemClickListenerSong mItemClickListenerSong;
    public void setmItemClickListenerSong(ItemClickListenerSong mItemClickListenerSong) {
        this.mItemClickListenerSong = mItemClickListenerSong;
    }

    public SongsAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public SongsAdapter(Cursor c, Context mContext) {
        super(c);
        this.mContext = mContext;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final Cursor cursor) {
        ImageLoader imageLoader=ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
        if (cursor != null) {
            final int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
            mNameSong = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
            mNameSinger = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
            mPath =cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
            mTimeSong =cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
            mAlbumArt=takeURIImgSong(cursor);
            holder.txtNameSong.setText(mNameSong);
            holder.txtNameSinger.setText(mNameSinger);

            /*Image Loader de load anh tranh viec lag khi nhieu anh*/
            imageLoader.loadImage(takeURIImgSong(cursor),new SimpleImageLoadingListener(){
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);
                    holder.imgSong.setImageBitmap(loadedImage);
                }
            });
            holder.setItemClickLIstenerRecyclerView(new ItemClickLIstenerRecyclerView() {
                @Override
                public void onClick(View v, int position) {
                    /*mỗi lần click sẽ xóa list song và thêm lại song lại và đưa position và listsong sang activity */
                    mListSong = new ArrayList<>();
                    int x=0;
                    cursor.moveToFirst();
                    do{
                        mListSong.add(getSongItem(x));
                        x++;
                    }while(cursor.moveToNext());
                    Song song=getSongItem(position);
                    mItemClickListenerSong.takeSongFromAdapter(mListSong,position);
                    Toast.makeText(mContext, song.getmNameSong(), Toast.LENGTH_SHORT).show();
                }
            });

            holder.imgMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(mContext,holder.imgMenu);
                    popupMenu.getMenuInflater().inflate(R.menu.popup_menu_item_song,popupMenu.getMenu());
                    popupMenu.show();
                }
            });

        }
    }
    public static Song getSongItem(int position){
        Cursor cursor=getItem(position);
        Song song=new Song (cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE))
                ,cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST))
                ,cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE))
                ,0
                ,cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA))
                ,null
                ,takeURIImgSong(cursor),null);
        return song;
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


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txtNameSong, txtNameSinger;
        ImageView imgSong, imgMenu;
        ItemClickLIstenerRecyclerView itemClickLIstenerRecyclerView;
        public ViewHolder(View itemView) {
            super(itemView);
            txtNameSong = (TextView) itemView.findViewById(R.id.txt_name_song);
            txtNameSinger = (TextView) itemView.findViewById(R.id.txt_name_singer);
            imgSong = (ImageView) itemView.findViewById(R.id.img_song);
            imgMenu = (ImageView) itemView.findViewById(R.id.img_menu_song);
            itemView.setOnClickListener(this);
            imgMenu.setOnClickListener(this);
        }

        public void setItemClickLIstenerRecyclerView(ItemClickLIstenerRecyclerView itemClickLIstenerRecyclerView) {
            this.itemClickLIstenerRecyclerView = itemClickLIstenerRecyclerView;
        }

        @Override
        public void onClick(View v) {
            itemClickLIstenerRecyclerView.onClick(v,getAdapterPosition());
        }
    }

    //lấy ảnh từ ablum ra thêm vào list
    public static String takeURIImgSong(Cursor cursor) {
        long albumId = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));
        final Uri ART_CONTENT_URI = Uri.parse("content://media/external/audio/albumart");
        Uri albumArtUri = ContentUris.withAppendedId(ART_CONTENT_URI, albumId);
        return String.valueOf(albumArtUri);
    }
}
