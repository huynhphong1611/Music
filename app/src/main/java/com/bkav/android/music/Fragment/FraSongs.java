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
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bkav.android.music.R;
import com.bkav.android.music.adapter.SongsAdapter;
import com.bkav.android.music.object.Song;
import com.bkav.android.music.provider.SongContact;

public class FraSongs extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    final static int ID_LOADER=1;
    private RecyclerView mRecyclerViewSongs;
    private SongsAdapter mSongsAdapter;
    public static CursorLoader sCursorLoader;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(ID_LOADER,null,this);
    }

    public void FraSongs(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_bai_hat,container,false);
        mRecyclerViewSongs=(RecyclerView) view.findViewById(R.id.recyclerViewSongs);
        mRecyclerViewSongs.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(view.getContext()
                ,LinearLayoutManager.VERTICAL,false);
        mRecyclerViewSongs.setLayoutManager(linearLayoutManager);
        mSongsAdapter=new SongsAdapter(null,getContext());
        mRecyclerViewSongs.setAdapter(mSongsAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(ID_LOADER,null,this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        if(i==ID_LOADER){
            sCursorLoader=new CursorLoader(getContext(),SongContact.CONTENT_URI
                    ,new String[]{SongContact.ID,SongContact.NAME_SONG,SongContact.NAME_SINGER}
                    ,null,null,null);
            return sCursorLoader;
        }
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        mSongsAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mSongsAdapter.swapCursor(null);
    }
}
