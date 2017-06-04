package com.softspec.transights.transights;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by macbook on 6/5/2017 AD.
 */

public class SectionPagerAdapter extends FragmentPagerAdapter {

    private final ArrayList<Fragment> fragmentList;

    public SectionPagerAdapter(FragmentManager fa){
        super(fa);
        this.fragmentList = new ArrayList<>();
    }

    public void addFragment(Fragment f){
        fragmentList.add(f);
    }
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
