package com.bkav.android.music.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bkav.android.music.R;
import com.bkav.android.music.adapter.AlbumsAdapter;

public class FraArtists extends Fragment {
    private AlbumsAdapter mRecentSongsAdapter;

    public FraArtists(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_nghe_si,container,false);
        return view;
    }


}
