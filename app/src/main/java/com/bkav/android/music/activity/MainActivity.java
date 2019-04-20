package com.bkav.android.music.activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.PermissionRequest;
import android.widget.Toast;

import com.bkav.android.music.Fragment.FraNgheNgay;
import com.bkav.android.music.Fragment.FraThuVienNhac;

import com.bkav.android.music.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    final static String NGHE_NGAY="Nghe Ngay";
    final static String THU_VIEN_NHAC="Thư viện nhạc";
    private FraNgheNgay mFraNgheNgay;
    private FraThuVienNhac mFraThuVienNhac;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initFragmentNgheNgay(NGHE_NGAY);
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
            case R.id.nav_nghengay:{
                initFragmentNgheNgay(NGHE_NGAY);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
            case R.id.nav_listmusic:{
                initFragmentThuVienNhac(THU_VIEN_NHAC);
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
    private void initFragmentNgheNgay(String titleActionBar) {

        setTitle(titleActionBar);
        mFraNgheNgay = new FraNgheNgay();
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment,mFraNgheNgay);
        fragmentTransaction.commit();
    }
    private void initFragmentThuVienNhac(String titleActionBar) {
        setTitle(titleActionBar);
        mFraThuVienNhac = new FraThuVienNhac();
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment,mFraThuVienNhac);
        fragmentTransaction.commit();
    }

}
