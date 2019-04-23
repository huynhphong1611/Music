package com.bkav.android.music.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.bkav.android.music.Fragment.FraAlbum;
import com.bkav.android.music.Fragment.FraArtists;
import com.bkav.android.music.Fragment.FraPlaylists;
import com.bkav.android.music.Fragment.FraSongs;

import com.bkav.android.music.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    final static String NGHE_SI="Artists";
    final static String ALBUM="Album";
    final static String SONGS="Songs";
    final static String PLAYLISTS="Playlists";
    private FraArtists mFraArtists;
    private FraAlbum mFraAlbum;
    private FraSongs mFraSongs;
    private FraPlaylists mFraPlaylists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initFragmentArtists(NGHE_SI);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

}
