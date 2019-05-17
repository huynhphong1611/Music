package com.bkav.android.music.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.bkav.android.music.R;
import com.bkav.android.music.adapter.ArtistsAdapter;
import com.bkav.android.music.adapter.DetailAlbumAdapter;

public class FragmentDetailAlbum  extends Fragment {
    final static int ID_LOADER_ARTIST=1;
    private CursorLoader mCursorLoader;
    private ExpandableListView mExpandableListView;
    private DetailAlbumAdapter mDetailAlbumAdapter;
    public FragmentDetailAlbum() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_album_detail,container,false);

        return view;
    }
}
