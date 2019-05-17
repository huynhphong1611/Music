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
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleCursorTreeAdapter;
import android.widget.TextView;

import com.bkav.android.music.R;
import com.bkav.android.music.expandablerecycleview.ExpandableLinearLayout;
import com.bkav.android.music.fragment.FragmentArtists;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.HashMap;

    public class ArtistsAdapter extends SimpleCursorTreeAdapter {
    private Context mContext;
    private Cursor mCursorHeader;
    private FragmentArtists mFragmentArtists;
    private HashMap<Integer, Integer> mCursorHashMapChild;

    public ArtistsAdapter(FragmentArtists fragmentArtists,Context context, int groupLayout,
                          int childLayout, String[] groupFrom, int[] groupTo,
                          String[] childrenFrom, int[] childrenTo) {
        super(context, null, groupLayout, groupFrom, groupTo, childLayout,
                childrenFrom, childrenTo);
        mContext=context;
        mFragmentArtists = fragmentArtists;
        mCursorHashMapChild = new HashMap<Integer,Integer>();

    }
    @Override
    protected Cursor getChildrenCursor(Cursor groupCursor) {
        int groupPos = groupCursor.getPosition();
        int groupId = groupCursor.getInt((groupCursor.getColumnIndexOrThrow
                (MediaStore.Audio.Artists._ID)));

        mCursorHashMapChild.put(groupId, groupPos);
        Loader<Cursor> loader = mFragmentArtists.getLoaderManager().getLoader(groupId);
        if (loader != null && !loader.isReset()) {
            mFragmentArtists.getLoaderManager()
                    .restartLoader(groupId, null, mFragmentArtists);
        } else {
            mFragmentArtists.getLoaderManager().initLoader(groupId, null, mFragmentArtists);
        }
        return null;
    }

        @Override
        protected void bindGroupView(View view, Context context, Cursor cursor, boolean isExpanded) {
            super.bindGroupView(view, context, cursor, isExpanded);
            TextView countAlbum=(TextView) view.findViewById(R.id.txt_count_album);
            String count_album=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Artists.NUMBER_OF_ALBUMS));
            countAlbum.setText(count_album+" album");
        }

        @Override
        protected void bindChildView(View view, Context context, Cursor cursor, boolean isLastChild) {
            super.bindChildView(view, context, cursor, isLastChild);

            TextView countSongm = (TextView) view.findViewById(R.id.txt_name_singer);
            String countSong=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.NUMBER_OF_SONGS));
            countSongm.setText(countSong+" bài hát");
            final ImageView imgSong = (ImageView) view.findViewById(R.id.img_song);
            /*Image Loader de load anh tranh viec lag khi nhieu anh*/
            initImageLoader().loadImage(takeImgSong(cursor),new SimpleImageLoadingListener(){
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);
                    imgSong.setImageBitmap(loadedImage);
                }
            });

        }

    //khoi tao image loader
        public ImageLoader initImageLoader(){
            ImageLoader imageLoader=ImageLoader.getInstance();
            imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
            return imageLoader;
        }
    //Accessor method
    public HashMap<Integer, Integer> getCursorHashMapChild() {
        return mCursorHashMapChild;
    }
    //lấy ảnh từ ablum ra thêm vào list
    public String takeImgSong(Cursor cursor) {
        long albumId = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Artists._ID));

        final Uri ART_CONTENT_URI = Uri.parse("content://media/external/audio/albumart");
        Uri albumArtUri = ContentUris.withAppendedId(ART_CONTENT_URI, albumId);
        return String.valueOf(albumArtUri);
    }
}

