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

    public ArtistsAdapter(Context context, int groupLayout,
                          int childLayout, String[] groupFrom, int[] groupTo,
                          String[] childrenFrom, int[] childrenTo) {
        super(context, null, groupLayout, groupFrom, groupTo, childLayout,
                childrenFrom, childrenTo);
        mCursorHashMapChild = new HashMap<Integer,Integer>();
    }
    @Override
    protected Cursor getChildrenCursor(Cursor groupCursor) {
        int groupPos = groupCursor.getPosition();
        int groupId = groupCursor.getInt((groupCursor.getColumnIndexOrThrow
                (MediaStore.Audio.Artists.ARTIST)));
        mCursorHashMapChild.put(groupId, groupPos);
        /*Loader<Cursor> loader = mFragmentArtists.getLoaderManager().getLoader(groupId);
        if (loader != null && !loader.isReset()) {
            mFragmentArtists.getLoaderManager()
                    .restartLoader(groupId, null, mFragmentArtists);
        } else {
            mFragmentArtists.getLoaderManager().initLoader(groupId, null, mFragmentArtists);
        }*/
        return null;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView countAlbum,countSongm,txtNameArtists;
        ImageView imgSong,imgMenuArtists, imgMenu,imgOne,imgTwo,imgThree,imgFour;
        GridLayout gridLayout;
        LinearLayout linearLayout;
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

