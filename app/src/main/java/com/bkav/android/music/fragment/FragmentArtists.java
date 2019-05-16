package com.bkav.android.music.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.bkav.android.music.R;
import com.bkav.android.music.adapter.ArtistsAdapter;


import java.util.HashMap;

public class FragmentArtists extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    final static int ID_LOADER_ARTIST=1;
    private CursorLoader mCursorLoader;
    private ExpandableListView mExpandableListView;
    private ArtistsAdapter mArtistsAdapter;

    public FragmentArtists(){

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(ID_LOADER_ARTIST,null,this);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_nghe_si,container,false);
        mExpandableListView = (ExpandableListView) view.findViewById(R.id.expand_list_view_artists);
        mArtistsAdapter= new ArtistsAdapter(this,getContext()
                ,R.layout.item_artists
                ,R.layout.item_song
                , new String[]{MediaStore.Audio.Artists.ARTIST}
                , new int []{R.id.txt_name_artists}
                , new String []{MediaStore.Audio.Albums.ALBUM}
                , new int []{R.id.txt_name_song});
        mExpandableListView.setAdapter(mArtistsAdapter);
        Loader<Cursor> loader = getLoaderManager().getLoader(-1);
        if (loader != null && !loader.isReset()) {
            getLoaderManager().restartLoader(ID_LOADER_ARTIST, null, this);
        } else {
            getLoaderManager().initLoader(ID_LOADER_ARTIST, null, this);
        }
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(ID_LOADER_ARTIST,null,this);
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {

        if(i==ID_LOADER_ARTIST){
            mCursorLoader=new CursorLoader(getContext(),MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI
                    ,null
                    ,null,null,null);
            return mCursorLoader;
        }else{
            String selection =MediaStore.Audio.Media.ARTIST_ID+"=?";
            mCursorLoader=new CursorLoader(getContext(),MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI
                    ,null
                    ,selection
                    ,new String[]{String.valueOf(i)},null);
            return mCursorLoader;
        }

    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        int id = loader.getId();
        if (id != ID_LOADER_ARTIST) {
            // child cursor
            if (!data.isClosed()) {

                HashMap<Integer, Integer> groupMap = mArtistsAdapter.getCursorHashMapChild();
                try {
                    int groupPos = groupMap.get(id);

                    mArtistsAdapter.setChildrenCursor(groupPos, data);
                } catch (NullPointerException e) {

                }
            }
        } else {
            mArtistsAdapter.setGroupCursor(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        // Called just before the cursor is about to be closed.
        int id = loader.getId();
        if (id != ID_LOADER_ARTIST) {
            // child cursor
            try {
                mArtistsAdapter.setChildrenCursor(id, null);
            } catch (NullPointerException e) {

            }
        } else {
            mArtistsAdapter.setGroupCursor(null);
        }
    }
}
