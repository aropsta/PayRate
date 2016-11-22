package com.example.arobius.payrate.adapters;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.arobius.payrate.fragments.clock_fragment;
import com.example.arobius.payrate.fragments.history_fragment;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private int nTabs;
    private Fragment clock, history;
    private String[] titles = {"0000000", "ZZZZZZZ"};

    public ViewPagerAdapter(FragmentManager fm, int nTabs, Fragment clock, Fragment history) {
        super(fm);
        this.clock = clock;
        this.history = history;
        this.nTabs = nTabs;
    }


    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return clock;
            case 1:
                return history;
            default:
                return clock;
        }


    }


    @Override
    public int getCount() {
        return nTabs;
    }


    @Override
    public String getPageTitle(int position) {
        return titles[position];
    }
}