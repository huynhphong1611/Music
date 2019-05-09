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

import com.bkav.android.music.R;
import com.bkav.android.music.adapter.ArtistsAdapter;

public class FragmentArtists extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    final static int ID_LOADER_ARTIST=1;
    final static int ID_LOADER_ALBUM=2;
    private RecyclerView mRecyclerViewArtists;
    private ArtistsAdapter mArtistsAdapter;
    private CursorLoader mCursorLoaderArtists;
    private CursorLoader mCursorLoaderAlbum;

    public FragmentArtists(){

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(ID_LOADER_ARTIST,null,this);
    }
    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(ID_LOADER_ARTIST,null,this);

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_nghe_si,container,false);
        mRecyclerViewArtists=(RecyclerView) view.findViewById(R.id.recycler_view_artists);
        mRecyclerViewArtists.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(view.getContext()
                ,LinearLayoutManager.VERTICAL,false);
        mRecyclerViewArtists.setLayoutManager(linearLayoutManager);
        //chô này anh bảo null k can truyền tham số đầu vào nhưng nó là cursor em để tham so nó lỗi ạ//
        mArtistsAdapter=new ArtistsAdapter(null,getContext());
        mRecyclerViewArtists.setAdapter(mArtistsAdapter);
        return view;
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {

        if(i==ID_LOADER_ARTIST){
            mCursorLoaderArtists=new CursorLoader(getContext(),MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI
                    ,null
                    ,null,null,null);
            return mCursorLoaderArtists;
        }
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        mArtistsAdapter.swapCursor(cursor);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mArtistsAdapter.swapCursor(null);
    }

}
