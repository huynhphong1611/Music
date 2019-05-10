package com.bkav.android.music.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
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
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bkav.android.music.R;
import com.bkav.android.music.fragment.FragmentAlbum;
import com.bkav.android.music.fragment.FragmentArtists;
import com.bkav.android.music.fragment.FragmentPlaylists;
import com.bkav.android.music.fragment.FragmentSongs;
import com.bkav.android.music.interfaces.ItemClickListenerSong;
import com.bkav.android.music.interfaces.OnSelectedListener;
import com.bkav.android.music.object.Song;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener
        ,SlidingUpPanelLayout.PanelSlideListener, OnSelectedListener {
    final static String  LOG= "trang thai ";
    public static final int LOOP_SONG_OFF=1;
    public static final int LOOP_SONG_ALLLIST=2;
    public static final int LOOP_SONG_PRESENT=3;
    public static int MY_PERMISSIONS_REQUEST = 1;
    public static int[] NUMBER_MY_PERMISSIONS_REQUEST = {0, 1};
    public static String TAG = "trang thai";
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
    private ImageView mImgSongSmall;
    private LinearLayout mImgSongFull;
    private TextView mNameSong;
    private TextView mNameSinger;
    private TextView mTimeSong;
    private TextView mTimeCurrenSong;
    private Toolbar mToolbar;
    private SeekBar mSeekBar;
    private int mTempLoop;
    private MediaPlayer mMediaPlayer;
    private Handler threadHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPermission();

        init();
        initFragmentArtists(R.string.artists);


        setSupportActionBar(mToolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        /*****lang nghe onCLick*********/
        mPLay.setOnClickListener(this);
        mPLayRandom.setOnClickListener(this);
        mPLayFull.setOnClickListener(this);
        mTempLoop = 1;
        mPlayLoop.setOnClickListener(this);
        mSlidingUpPanelLayout.addPanelSlideListener(this);
        /***********************************/

        Log.v(LOG,"onCreate");

    }
    public void init(){
        mPLay = (ImageView) findViewById(R.id.img_play);
        mPLayFull = (ImageView) findViewById(R.id.image_play_song);
        mPLayRandom = (ImageView) findViewById(R.id.image_random_song);
        mPlayLoop = (ImageView) findViewById(R.id.image_loop_song);
        mLinearLayoutPlayMusic = (LinearLayout) findViewById(R.id.view_list_and_menu);
        mSlidingUpPanelLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mNameSong =(TextView)findViewById(R.id.txt_name_song_play);
        mNameSinger = (TextView)findViewById(R.id.txt_name_singer_play);
        mImgSongSmall =(ImageView) findViewById(R.id.image_view_song);
        mImgSongFull= (LinearLayout) findViewById(R.id.layout_img_background);
        mSeekBar = (SeekBar) findViewById(R.id.seek_bar_time_play);
        mMediaPlayer =new MediaPlayer();
        mTimeSong = (TextView) findViewById(R.id.txt_time_end);
        mTimeCurrenSong = (TextView) findViewById(R.id.txt_time_start);
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
                initFragmentArtists(R.string.artists);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
            case R.id.nav_album: {
                initFragmentAlbum(R.string.album);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
            case R.id.nav_songs: {
                initFraSongs(R.string.songs);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
            case R.id.nav_playlists: {
                initFraPlaylists(R.string.playlist);
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

    private void initFragmentArtists(int titleActionBar) {

        setTitle(titleActionBar);
        mFragmentArtists = new FragmentArtists();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, mFragmentArtists);
        fragmentTransaction.commit();
    }

    private void initFragmentAlbum(int titleActionBar) {
        setTitle(titleActionBar);
        mFragmentAlbums = new FragmentAlbum();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, mFragmentAlbums);
        fragmentTransaction.commit();
    }

    private void initFraSongs(int titleActionBar) {
        setTitle(titleActionBar);
        mFragmentSongs = new FragmentSongs();
        mFragmentSongs.setmOnSelectedListener(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, mFragmentSongs);
        fragmentTransaction.commit();
    }

    private void initFraPlaylists(int titleActionBar) {
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
                    Toast.makeText(MainActivity.this, R.string.permission_isnt_granted, Toast.LENGTH_SHORT).show();
                }
                // Permisson don't granted and dont show dialog again.
                else {
                    Toast.makeText(MainActivity.this, R.string.dont_granted_and_dont_show_dialog_again, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(MainActivity.this, R.string.read_is_granted, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, R.string.read_is_deied, Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        break;
                    case 1:
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(MainActivity.this, R.string.write_is_granted, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, R.string.write_is_deied, Toast.LENGTH_SHORT).show();
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

    //thuc hien onClick cua cac phim play loop random
    @Override
    public void onClick(View v) {
        int x=0;
        switch(v.getId()){
            case R.id.img_play:{
                //nut play o sliding dang cuon
                mPLay.setSelected(!mPLay.isSelected());

                if (mPLay.isSelected()) {
                    mPLay.setImageDrawable(getBaseContext().getResources()
                            .getDrawable(R.drawable.ic_media_pause_light));
                    if(x!=0){
                        mMediaPlayer.seekTo(x);
                        mMediaPlayer.start();
                    }else{

                        mMediaPlayer.start();
                    }
                } else{
                    x=mMediaPlayer.getCurrentPosition();
                    mPLay.setImageDrawable(getBaseContext().getResources()
                            .getDrawable(R.drawable.ic_media_play_light));
                    mMediaPlayer.pause();
                }

                break;
            }
            case R.id.image_play_song:{
                mPLayFull.setSelected(!mPLayFull.isSelected());

                if (mPLayFull.isSelected()) {
                    mPLayFull.setImageDrawable(getBaseContext().getResources()
                            .getDrawable(R.drawable.ic_media_pause_dark));
                    if(x!=0){
                        mMediaPlayer.seekTo(x);
                        mMediaPlayer.start();
                    }else{

                        mMediaPlayer.start();
                    }
                } else{
                    x=mMediaPlayer.getCurrentPosition();
                    mPLayFull.setImageDrawable(getBaseContext().getResources()
                            .getDrawable(R.drawable.ic_media_play_dark));
                    mMediaPlayer.pause();
                }

                break;
            }
            case R.id.image_loop_song:{
                mTempLoop += 1;
                if (mTempLoop >= 1 || mTempLoop <= 3) {
                    switch (mTempLoop) {
                        case LOOP_SONG_OFF: {
                            mPlayLoop.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.ic_repeat_dark));
                            Toast.makeText(MainActivity.this, R.string.loop_song_off, Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case LOOP_SONG_ALLLIST: {
                            mPlayLoop.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.ic_repeat_dark_selected));
                            Toast.makeText(MainActivity.this, R.string.loop_song_alllist, Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case LOOP_SONG_PRESENT: {
                            mPlayLoop.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.ic_repeat_one_song_dark));
                            Toast.makeText(MainActivity.this, R.string.loop_song_present, Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                }
                if (mTempLoop == 3) mTempLoop = 0;
                break;
            }
            case R.id.image_random_song:{
                mPLayRandom.setSelected(!mPLayRandom.isSelected());

                if (mPLayRandom.isSelected()) {
                    mPLayRandom.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.ic_play_shuffle_orange));
                    Toast.makeText(MainActivity.this, R.string.on_random_play, Toast.LENGTH_SHORT).show();
                } else {
                    mPLayRandom.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.ic_shuffle_dark));
                    Toast.makeText(MainActivity.this, R.string.off_random_play, Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }


    /*phan lang nghe cua SlidingUpPanelLayout*/
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

    @Override
    public void onSelectedListener(Song song) {
        threadHandler = new Handler();
        /****************cho bai hat chạy*************/
        ImageLoader imageLoader=ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getBaseContext()));
        mNameSong.setText(song.getmNameSong());
        mNameSinger.setText(song.getmNameSinger());
        mPLay.setImageDrawable(getBaseContext().getResources()
                .getDrawable(R.drawable.ic_media_pause_light));
        mPLayFull.setImageDrawable(getBaseContext().getResources()
                .getDrawable(R.drawable.ic_media_pause_dark));
        mPLay.setSelected(true);
        mPLayFull.setSelected(true);
        imageLoader.displayImage(song.getmAlbumArt(),mImgSongSmall);
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(song.getmAlbumArt()));
            mImgSongFull.setBackground(new BitmapDrawable(getResources(),bitmap));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(mMediaPlayer.isPlaying()){
            mMediaPlayer.pause();
            mMediaPlayer = MediaPlayer.create(this,Uri.parse(song.getmPath()));
            mMediaPlayer.start();
        }else{
            mMediaPlayer = MediaPlayer.create(this,Uri.parse(song.getmPath()));
            mMediaPlayer.start();
        }
        int duration = mMediaPlayer.getDuration();
        int currentPosition = mMediaPlayer.getCurrentPosition();
        mSeekBar.setMax(duration);
        if(currentPosition==0){

            mTimeSong.setText(millisecondsToString(duration));
        }else if(currentPosition==duration){
            //TODO chay lai hoac next
            mMediaPlayer.pause();
        }
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress=0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressVulue, boolean fromUser) {
                progress=progressVulue;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mMediaPlayer.seekTo(progress);
                mMediaPlayer.start();
            }
        });
        // Tạo một thread để update trạng thái của SeekBar.
        UpdateSeekBarThread updateSeekBarThread= new UpdateSeekBarThread();
        threadHandler.postDelayed(updateSeekBarThread,50);

    }
    // Chuyển số lượng milli giây thành một String có ý nghĩa.
    private String millisecondsToString(int milliseconds)  {
        long minutes = (milliseconds / 1000) / 60;
        long seconds = (milliseconds / 1000) % 60;
        return minutes+":"+ seconds;
    }
    // Thread sử dụng để Update trạng thái cho SeekBar.
    class UpdateSeekBarThread implements Runnable {

        public void run()  {
            int currentPosition = mMediaPlayer.getCurrentPosition();
            mTimeCurrenSong.setText(millisecondsToString(currentPosition));

            mSeekBar.setProgress(currentPosition);

            // Ngừng thread 50 mili giây.
            threadHandler.postDelayed(this, 50);
        }
    }
    /**********************************************/
}
