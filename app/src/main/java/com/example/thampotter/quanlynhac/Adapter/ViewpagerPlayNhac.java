package com.example.thampotter.quanlynhac.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ViewpagerPlayNhac extends FragmentPagerAdapter {
    public final ArrayList<Fragment> listfragment = new ArrayList<>();

    public ViewpagerPlayNhac(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return listfragment.get(position);
    }

    @Override
    public int getCount() {
        return listfragment.size();
    }

    public void addfragment(Fragment fragment){
        listfragment.add(fragment);
    }
}
