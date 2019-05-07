package com.bkav.android.music.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bkav.android.music.R;
import com.bkav.android.music.fragment.FragmentAlbum;
import com.bkav.android.music.fragment.FragmentArtists;
import com.bkav.android.music.fragment.FragmentPlaylists;
import com.bkav.android.music.fragment.FragmentSongs;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    final static String  LOG= "trang thai ";
    public static int MY_PERMISSIONS_REQUEST = 1;
    public static int[] NUMBER_MY_PERMISSIONS_REQUEST = {0, 1};
    public static String TAG = "trang thai";
    final static String NGHE_SI = "Artists";
    final static String ALBUM = "Album";
    final static String SONGS = "Songs";
    final static String PLAYLISTS = "Playlists";
    private FragmentArtists mFragmentArtists;
    private FragmentAlbum mFragmentAlbums;
    private FragmentSongs mFragmentSongs;
    private FragmentPlaylists mFragmentPlaylists;
    private LinearLayout mLinearLayoutPlayMusic;
    private SlidingUpPanelLayout mSlidingUpPanelLayout;
    private ImageView mPLay;
    private ImageView mPLayFull;
    private ImageView mPLayRandom;
    private ImageView mPlayLoop;
    private boolean mCheckPlay;
    private boolean checkRead;
    private boolean checkWrite;
    private Toolbar mToolbar;
    private int mTempLoop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPermission();
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
        showSlidingLayout();
        Log.v(LOG,"onCreate");

    }
    private void showSlidingLayout(){
        //cuon va hien slidinglyaout
        if (mSlidingUpPanelLayout != null) {
            mSlidingUpPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
                @Override
                public void onPanelSlide(View panel, float slideOffset) {
                    Log.i(TAG, "panel");
                }

                @Override
                public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                    if (mSlidingUpPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
                        mPLay.setVisibility(View.GONE);
                        mLinearLayoutPlayMusic.setVisibility(View.VISIBLE);
                    } else {
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

                if (mPLay.isSelected()) {
                    mPLay.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.ic_media_pause_light));
                    //TODO bat nhac
                } else
                    mPLay.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.ic_media_play_light));
            }
        });
        //nut play o sliding dang k cuon
        mPLayFull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPLayFull.setSelected(!mPLayFull.isSelected());

                if (mPLayFull.isSelected()) {
                    mPLayFull.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.ic_media_play_dark));
                    //TODO bat nhac
                } else
                    mPLayFull.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.ic_media_pause_dark));
            }
        });

        //nut play radom
        mPLayRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPLayRandom.setSelected(!mPLayRandom.isSelected());

                if (mPLayRandom.isSelected()) {
                    mPLayRandom.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.ic_play_shuffle_orange));
                    Toast.makeText(MainActivity.this, "Bật tính năng phát ngẫu nhiên", Toast.LENGTH_SHORT).show();
                } else {
                    mPLayRandom.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.ic_shuffle_dark));
                    Toast.makeText(MainActivity.this, "Tắt tính năng phát ngẫu nhiên", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //nut play loop
        mTempLoop = 1;
        mPlayLoop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTempLoop += 1;
                if (mTempLoop >= 1 || mTempLoop <= 3) {
                    switch (mTempLoop) {
                        case 1: {
                            mPlayLoop.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.ic_repeat_dark));
                            Toast.makeText(MainActivity.this, "Tắt lặp lại", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case 2: {
                            mPlayLoop.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.ic_repeat_dark_selected));
                            Toast.makeText(MainActivity.this, "Lặp lại tất cả bài hát", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case 3: {
                            mPlayLoop.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.ic_repeat_one_song_dark));
                            Toast.makeText(MainActivity.this, "Lặp lại bài hát hiện tại", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                }
                if (mTempLoop == 3) mTempLoop = 0;
            }
        });
    }

    @Override
    protected void onResume() {
        Log.v(LOG,"onResume");
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
        switch (item.getItemId()) {
            case R.id.action_search: {
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
        switch (item.getItemId()) {
            case R.id.nav_nghesi: {
                initFragmentArtists(NGHE_SI);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
            case R.id.nav_album: {
                initFragmentAlbum(ALBUM);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
            case R.id.nav_songs: {
                initFraSongs(SONGS);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
            case R.id.nav_playlists: {
                initFraPlaylists(PLAYLISTS);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
            case R.id.nav_setting: {
                //TODO intent setting
                Toast.makeText(this, "setting", Toast.LENGTH_SHORT).show();
                return true;
            }
            case R.id.nav_help: {
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
        mFragmentArtists = new FragmentArtists();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, mFragmentArtists);
        fragmentTransaction.commit();
    }

    private void initFragmentAlbum(String titleActionBar) {
        setTitle(titleActionBar);
        mFragmentAlbums = new FragmentAlbum();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, mFragmentAlbums);
        fragmentTransaction.commit();
    }

    private void initFraSongs(String titleActionBar) {
        setTitle(titleActionBar);
        mFragmentSongs = new FragmentSongs();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, mFragmentSongs);
        fragmentTransaction.commit();
    }

    private void initFraPlaylists(String titleActionBar) {
        setTitle(titleActionBar);
        mFragmentPlaylists = new FragmentPlaylists();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, mFragmentPlaylists);
        fragmentTransaction.commit();
    }

    private void initPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                //Permisson don't granted
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    Toast.makeText(MainActivity.this, "Permission isn't granted ", Toast.LENGTH_SHORT).show();
                }
                // Permisson don't granted and dont show dialog again.
                else {
                    Toast.makeText(MainActivity.this, "Permisson don't granted and dont show dialog again ", Toast.LENGTH_SHORT).show();
                }
                //Register permission
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST);

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            for (int i = 0; i < grantResults.length; i++) {
                switch (i) {
                    case 0:
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(MainActivity.this, "Permision Read File is Granted", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Permision Read File is Denied", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        break;
                    case 1:
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(MainActivity.this, "Permision Write File is Granted", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Permision Write File is Denied", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    default:
                        break;
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }



}
