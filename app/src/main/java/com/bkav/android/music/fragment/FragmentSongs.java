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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bkav.android.music.R;
import com.bkav.android.music.adapter.AlbumsAdapter;
import com.bkav.android.music.adapter.SongsAdapter;
import com.bkav.android.music.interfaces.ItemClickListenerSong;
import com.bkav.android.music.interfaces.OnSelectedListener;
import com.bkav.android.music.object.Song;

import java.util.List;

public class FragmentSongs extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,ItemClickListenerSong {
    final static int ID_LOADER=1;
    private CursorLoader mCursorLoader;
    private RecyclerView mRecyclerViewSongs;
    private SongsAdapter mSongsAdapter;

    public void setmOnSelectedListener(OnSelectedListener mOnSelectedListener) {
        this.mOnSelectedListener = mOnSelectedListener;
    }

    private OnSelectedListener mOnSelectedListener;

    @Override
    public void takeSongFromAdapter(List<Song> listSong,int positon) {
        mOnSelectedListener.onSelectedListener(listSong,positon);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(ID_LOADER,null,this);
    }

    public void FragmentSongs(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_bai_hat,container,false);
        mRecyclerViewSongs=(RecyclerView) view.findViewById(R.id.recycler_view_songs);
        mRecyclerViewSongs.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(view.getContext()
                ,LinearLayoutManager.VERTICAL,false);
        mRecyclerViewSongs.setLayoutManager(linearLayoutManager);
        mSongsAdapter=new SongsAdapter(getContext());
        mSongsAdapter.setmItemClickListenerSong(this);
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
        mSongsAdapter.swapCursor(cursor);
        mRecyclerViewSongs.setAdapter(mSongsAdapter);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mSongsAdapter.swapCursor(null);
    }
}
