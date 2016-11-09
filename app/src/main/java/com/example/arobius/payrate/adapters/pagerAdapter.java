package com.example.arobius.payrate.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.arobius.payrate.fragments.clock_fragment;
import com.example.arobius.payrate.fragments.history_fragment;

public class pagerAdapter extends FragmentPagerAdapter {

    private int nTabs;
    private String[] titles = {"0000000", "ZZZZZZZ"};

    public pagerAdapter(FragmentManager fm, int nTabs, Context context) {
        super(fm);
        this.nTabs = nTabs;
    }

    @Override
    public Fragment getItem(int position) {
        {
            if (position == 0) return new clock_fragment();
            else return new clock_fragment();

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