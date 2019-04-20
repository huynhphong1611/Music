package com.bkav.android.music.fragmentpageradapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bkav.android.music.Fragment.FraAlbum;
import com.bkav.android.music.Fragment.FraBaiHat;
import com.bkav.android.music.Fragment.FraDanhSachPhat;
import com.bkav.android.music.Fragment.FraNgheSi;
import com.bkav.android.music.Fragment.FraTheLoai;
import com.bkav.android.music.R;

public class ThuVienNhacViewPagerAdapter extends FragmentPagerAdapter {
    public static final int NUM_PAGER = 5;
    public static final String  danh_sach_phat="DANH SÁCH PHÁT";
    public static final String nghe_si ="NGHỆ SĨ";
    public static final String ablum ="ALBUM";
    public static final String bai_hat="BÀI HÁT";
    public static final String the_loai="THỂ LOẠI";
    public ThuVienNhacViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
        switch (position) {
            case 0:{
                fragment=new FraDanhSachPhat();
                break;
            }
            case 1:{
                fragment=new FraNgheSi();
                break;
            }
            case 2:{
                fragment=new FraAlbum();
                break;
            }
            case 3:{
                fragment=new FraBaiHat();
                break;
            }
            case 4:{
                fragment=new FraTheLoai();
                break;
            }
            default:{
                fragment=new FraDanhSachPhat();
                break;
            }

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return NUM_PAGER;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = danh_sach_phat;
                break;
            case 1:
                title = nghe_si;
                break;
            case 2:
                title = ablum;
                break;
            case 3:
                title = bai_hat;
                break;
            case 4:
                title = the_loai;
                break;
             default:
                 title = danh_sach_phat;
                 break;
        }
        return title;
    }
}
