package com.bkav.android.music.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bkav.android.music.R;
import com.bkav.android.music.fragmentpageradapter.ThuVienNhacViewPagerAdapter;

public class FraThuVienNhac extends Fragment {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    public FraThuVienNhac(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_thu_vien_nhac,container,false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getView()!=null)
        {
            addControl(getView());
        }

    }

    /*
                them cac tab viewpager tablayout
                 */
    private void addControl(View view) {
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        mTabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        ThuVienNhacViewPagerAdapter adapter = new ThuVienNhacViewPagerAdapter(getFragmentManager());
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setTabsFromPagerAdapter(adapter);//deprecated
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }
}
