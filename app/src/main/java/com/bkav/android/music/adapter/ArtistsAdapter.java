package com.bkav.android.music.adapter;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bkav.android.music.R;
import com.bkav.android.music.expandablerecycleview.ExpandableLinearLayout;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class ArtistsAdapter extends BaseCursorAdapter<ArtistsAdapter.ViewHolder>  {
    private Context mContext;
    private int mLastExpandedCardPosition;

    public ArtistsAdapter(Cursor c, Context mContext) {
        super(c);
        this.mContext = mContext;
    }

    @Override
    public void onBindViewHolder(final ArtistsAdapter.ViewHolder holder, Cursor cursor) {
        //khoi tao imageLoader
        final ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));


        if (cursor != null) {
            String nameAlbum =cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Artists.ARTIST));
            String numAlbum=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Artists.NUMBER_OF_ALBUMS));
            holder.txtNameArtists.setText(nameAlbum);
            holder.countAlbum.setText(numAlbum+" albums");
            imageLoader.displayImage(takeImgSong(cursor), holder.imgOne, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.unknown_artists);
                    holder.gridLayout.setBackground(new BitmapDrawable(bitmap));
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.unknown_artists);
                    holder.gridLayout.setBackground(new BitmapDrawable(bitmap));
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    holder.imgOne.setImageBitmap(loadedImage);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                    Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.unknown_artists);
                    holder.gridLayout.setBackground(new BitmapDrawable(bitmap));
                }
            });
            imageLoader.loadImage(takeImgSong(cursor), new SimpleImageLoadingListener(){
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    holder.imgTwo.setImageBitmap(loadedImage);
                }
            });
            imageLoader.loadImage(takeImgSong(cursor), new SimpleImageLoadingListener(){
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    holder.imgThree.setImageBitmap(loadedImage);
                }
            });
            imageLoader.loadImage(takeImgSong(cursor), new SimpleImageLoadingListener(){
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    holder.imgFour.setImageBitmap(loadedImage);
                }
            });

        }
    }

    @Override
    public ArtistsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        final View itemView = layoutInflater.inflate(R.layout.item_artists, viewGroup, false);
        return new ArtistsAdapter.ViewHolder(itemView);
    }

    @Override
    public void swapCursor(Cursor newCursor) {
        super.swapCursor(newCursor);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView countAlbum,countSongm,txtNameArtists;
        ImageView imgSong,imgMenuArtists, imgMenu,imgOne,imgTwo,imgThree,imgFour;
        GridLayout gridLayout;
        LinearLayout linearLayout;
        private ExpandableLinearLayout mExpandableLinearLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            linearLayout=(LinearLayout) itemView.findViewById(R.id.linear_layout_item_artists);
            countAlbum=(TextView) itemView.findViewById(R.id.txt_count_album);
            gridLayout=(GridLayout) itemView.findViewById(R.id.img_grid_song);
            txtNameArtists =(TextView) itemView.findViewById(R.id.txt_name_artists);
            imgMenuArtists= (ImageView) itemView.findViewById(R.id.img_menu_artists);
            // su dung layout bên item_song
            countSongm = (TextView) itemView.findViewById(R.id.txt_name_singer);
            imgSong = (ImageView) itemView.findViewById(R.id.img_song);
            imgMenu =(ImageView) itemView.findViewById(R.id.img_menu_song);
            imgOne=(ImageView) itemView.findViewById(R.id.img_song_1);
            imgTwo=(ImageView) itemView.findViewById(R.id.img_song_2);
            imgThree=(ImageView) itemView.findViewById(R.id.img_song_3);
            imgFour=(ImageView) itemView.findViewById(R.id.img_song_4);

        }

    }

    //lấy ảnh từ ablum ra thêm vào list
    public String takeImgSong(Cursor cursor) {
        long albumId = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Artists._ID));

        final Uri ART_CONTENT_URI = Uri.parse("content://media/external/audio/albumart");
        Uri albumArtUri = ContentUris.withAppendedId(ART_CONTENT_URI, albumId);
        return String.valueOf(albumArtUri);
    }
}

