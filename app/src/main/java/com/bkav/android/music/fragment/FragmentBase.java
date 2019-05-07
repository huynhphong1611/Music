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
import android.widget.Adapter;
import android.widget.LinearLayout;

import com.bkav.android.music.R;
import com.bkav.android.music.adapter.SongsAdapter;

public class FragmentBase extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    final static int ID_LOADER=1;
    public CursorLoader mCursorLoader;
    private Adapter mAdapter;
    public RecyclerView mRecyclerView;
    private LinearLayout mLinearLayout;

    public FragmentBase(Adapter mAdapter, RecyclerView mRecyclerView) {
        this.mAdapter = mAdapter;
        this.mRecyclerView = mRecyclerView;
    }

    public Adapter getmAdapter() {
        return mAdapter;
    }

    public void setmAdapter(Adapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    public RecyclerView getmRecyclerView() {
        return mRecyclerView;
    }

    public void setmRecyclerView(RecyclerView mRecyclerView) {
        this.mRecyclerView = mRecyclerView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(ID_LOADER,null,this);
    }
    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(ID_LOADER,null,this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_bai_hat,container,false);
        mRecyclerView=(RecyclerView) view.findViewById(R.id.recyclerViewSongs);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(view.getContext()
                ,LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        return view;
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        String where= MediaStore.Audio.Media.IS_MUSIC+"=?";
        if(i==ID_LOADER){
            mCursorLoader=new CursorLoader(getContext(),MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                    ,null
                    ,where,new String[]{"1"},null);
            return mCursorLoader;
        }
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
