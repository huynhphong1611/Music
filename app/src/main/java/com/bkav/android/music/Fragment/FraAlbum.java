package com.bkav.android.music.Fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bkav.android.music.R;
import com.bkav.android.music.adapter.AlbumsAdapter;
import com.bkav.android.music.adapter.SongsAdapter;

public class FraAlbum extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    final static int ID_LOADER=1;
    private RecyclerView mRecyclerViewAlbums;
    private AlbumsAdapter mAlbumsAdapter;
    public static CursorLoader sCursorLoader;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(ID_LOADER,null,this);
    }

    public void FraAlbum(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_album,container,false);
        mRecyclerViewAlbums=(RecyclerView) view.findViewById(R.id.recyclerViewAlbums);
        mRecyclerViewAlbums.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(view.getContext()
                ,2,GridLayoutManager.VERTICAL,false);
        mRecyclerViewAlbums.setLayoutManager(gridLayoutManager);
//        AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(getContext(), 500);
//        mRecyclerViewAlbums.setLayoutManager(layoutManager);
        mAlbumsAdapter=new AlbumsAdapter(null,getContext());
        mRecyclerViewAlbums.setAdapter(mAlbumsAdapter);
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
        String where= MediaStore.Audio.Media.IS_MUSIC +"=?";
        if(i==ID_LOADER){
            sCursorLoader=new CursorLoader(getContext(),MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                    ,null
                    ,where,new String[]{"1"},null);
            return sCursorLoader;
        }
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        mAlbumsAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mAlbumsAdapter.swapCursor(null);
    }
}
