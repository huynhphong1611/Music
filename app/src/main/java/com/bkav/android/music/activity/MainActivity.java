package com.bkav.android.music.activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bkav.android.music.Fragment.FraAlbum;
import com.bkav.android.music.Fragment.FraArtists;
import com.bkav.android.music.Fragment.FraPlaylists;
import com.bkav.android.music.Fragment.FraSongs;

import com.bkav.android.music.R;
import com.bkav.android.music.object.Song;
import com.bkav.android.music.provider.SongContact;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static String TAG="trang thai";
    final static String NGHE_SI="Artists";
    final static String ALBUM="Album";
    final static String SONGS="Songs";
    final static String PLAYLISTS="Playlists";
    private FraArtists mFraArtists;
    private FraAlbum mFraAlbum;
    private FraSongs mFraSongs;
    private FraPlaylists mFraPlaylists;
    private LinearLayout mLinearLayoutPlayMusic;
    private SlidingUpPanelLayout mSlidingUpPanelLayout;
    private ImageView mPLay;
    private ImageView mPLayFull;
    private ImageView mPLayRandom;
    private ImageView mPlayLoop;
    private boolean mCheckPlay;
    private Toolbar mToolbar;
    private int mTempLoop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPLay = (ImageView) findViewById(R.id.img_play);
        mPLayFull = (ImageView) findViewById(R.id.image_play_song);
        mPLayRandom = (ImageView) findViewById(R.id.image_random_song);
        mPlayLoop = (ImageView) findViewById(R.id.image_loop_song);
        mLinearLayoutPlayMusic = (LinearLayout) findViewById(R.id.view_list_and_menu);
        mSlidingUpPanelLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        initFragmentArtists(NGHE_SI);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    protected void onResume() {
        //cuon va hien slidinglyaout
        if(mSlidingUpPanelLayout!=null){
            mSlidingUpPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
                @Override
                public void onPanelSlide(View panel, float slideOffset) {
                    Log.i(TAG,"panel");
                }

                @Override
                public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                    if(mSlidingUpPanelLayout.getPanelState()== SlidingUpPanelLayout.PanelState.EXPANDED){
                        mPLay.setVisibility(View.GONE);
                        mLinearLayoutPlayMusic.setVisibility(View.VISIBLE);
                    }else{
                        mPLay.setVisibility(View.VISIBLE);
                        mLinearLayoutPlayMusic.setVisibility(View.GONE);
                    }

                }
            });
        }
        //nut play o sliding dang cuon
        mPLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPLay.setSelected(!mPLay.isSelected());

                if(mPLay.isSelected()){
                    mPLay.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.ic_media_pause_light));
                    //TODO bat nhac
                }else mPLay.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.ic_media_play_light));
            }
        });
        //nut play o sliding dang k cuon
        mPLayFull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPLayFull.setSelected(!mPLayFull.isSelected());

                if(mPLayFull.isSelected()){
                    mPLayFull.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.ic_media_play_dark));
                    //TODO bat nhac
                }else mPLayFull.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.ic_media_pause_dark));
            }
        });

        //nut play radom
        mPLayRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPLayRandom.setSelected(!mPLayRandom.isSelected());

                if(mPLayRandom.isSelected()){
                    mPLayRandom.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.ic_play_shuffle_orange));
                    Toast.makeText(MainActivity.this, "Bật tính năng phát ngẫu nhiên", Toast.LENGTH_SHORT).show();
                }else{
                    mPLayRandom.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.ic_shuffle_dark));
                    Toast.makeText(MainActivity.this, "Tắt tính năng phát ngẫu nhiên", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //nut play loop
        mTempLoop=1;
        mPlayLoop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTempLoop += 1;
                if(mTempLoop>=1||mTempLoop<=3){
                    switch (mTempLoop){
                        case 1:{
                            mPlayLoop.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.ic_repeat_dark));
                            Toast.makeText(MainActivity.this, "Tắt lặp lại", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case 2:{
                            mPlayLoop.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.ic_repeat_dark_selected));
                            Toast.makeText(MainActivity.this, "Lặp lại tất cả bài hát", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case 3:{
                            mPlayLoop.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.ic_repeat_one_song_dark));
                            Toast.makeText(MainActivity.this, "Lặp lại bài hát hiện tại", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                }
                if(mTempLoop==3) mTempLoop=0;
            }
        });
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_search:{
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        switch (item.getItemId()){
            case R.id.nav_nghesi:{
                initFragmentArtists(NGHE_SI);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
            case R.id.nav_album:{
                initFragmentAlbum(ALBUM);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
            case R.id.nav_songs:{
                initFraSongs(SONGS);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
            case R.id.nav_playlists:{
                initFraPlaylists(PLAYLISTS);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
            case R.id.nav_setting:{
                //TODO intent setting
                Toast.makeText(this, "setting", Toast.LENGTH_SHORT).show();
                return true;
            }
            case R.id.nav_help:{
                //TODO intent help
                Toast.makeText(this, "help", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void initFragmentArtists(String titleActionBar) {

        setTitle(titleActionBar);
        mFraArtists = new FraArtists();
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment,mFraArtists);
        fragmentTransaction.commit();
    }
    private void initFragmentAlbum(String titleActionBar) {
        setTitle(titleActionBar);
        mFraAlbum = new FraAlbum();
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment,mFraAlbum);
        fragmentTransaction.commit();
    }
    private void initFraSongs(String titleActionBar) {
        setTitle(titleActionBar);
        mFraSongs = new FraSongs();
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment,mFraSongs);
        fragmentTransaction.commit();
    }
    private void initFraPlaylists(String titleActionBar) {
        setTitle(titleActionBar);
        mFraPlaylists = new FraPlaylists();
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment,mFraPlaylists);
        fragmentTransaction.commit();
    }
    public void getAllMediaMp3FileInDb(Context context){
        Uri uri= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Audio.AudioColumns.DATA,MediaStore.Audio.AudioColumns.TITLE ,MediaStore.Audio.AudioColumns.ALBUM, MediaStore.Audio.ArtistColumns.ARTIST};
        Cursor c = context.getContentResolver().query(uri, projection, MediaStore.Audio.Media.MIME_TYPE +"=?" ,new String[]{"audio/mpeg"}, null);

        if (c != null) {
            while (c.moveToNext()) {
                // Create a model object.
                Song song = new Song();

                String path = c.getString(0);   // Retrieve path.
                String name = c.getString(1);   // Retrieve name.
                String album = c.getString(2);  // Retrieve album name.
                String artist = c.getString(3); // Retrieve artist name.

                // Set data to the model object.
                song.setmNameSong(name);
                song.setmPath(path);
                song.setmNameSinger(artist);
                song.setmAlbum(album);
                Log.e("Name :" + name, " Album :" + album);
                Log.e("Path :" + path, " Artist :" + artist);

                // Add the model object to the db .
                ContentValues contentValues=new ContentValues();
                contentValues.put(SongContact.NAME_SONG,song.getmNameSong());
                contentValues.put(SongContact.NAME_SINGER,song.getmNameSinger());
                contentValues.put(SongContact.NAME_ALBUM,song.getmAlbum());
                contentValues.put(SongContact.PATH,song.getmPath());
                context.getContentResolver().insert(SongContact.CONTENT_URI,contentValues);
            }
            c.close();
        }

    }

}
