package com.example.thampotter.quanlynhac.Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class AdapterMainViewPager extends FragmentPagerAdapter {
    private ArrayList<Fragment> arrFrag = new ArrayList<>();
    private ArrayList<String> arrTitle = new ArrayList<>();

    public AdapterMainViewPager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return arrFrag.get(position);
    }

    @Override
    public int getCount() {
        return arrFrag.size();
    }

    public void addFragment(Fragment fragment, String title){
        arrFrag.add(fragment);
        arrTitle.add(title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return arrTitle.get(position);
    }
}
