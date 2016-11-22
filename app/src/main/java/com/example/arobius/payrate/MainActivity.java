package com.example.arobius.payrate;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.arobius.payrate.adapters.ViewPagerAdapter;
import com.example.arobius.payrate.fragments.clock_fragment;
import com.example.arobius.payrate.fragments.history_fragment;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity implements Communicator {
    SlidingTabLayout slidingTabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewViewPagerAdapter;
    clock_fragment clock;
    history_fragment history;
    ScheduledFuture scheduledFuture;
    ScheduledExecutorService scheduledExecutorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.e("VIVZ", Thread.currentThread().getName());
        clock = new clock_fragment();
        history = new history_fragment();
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 2, clock, history);

        viewPager.setAdapter(viewViewPagerAdapter);

        //TODO: refresh history page on save and delete
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //Log.e("VIVS", "ViewPager onPageScrolled()"+position);
            }

            @Override
            public void onPageSelected(int position) {
                //Log.e("VIVS", "ViewPager onPageSelected()");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //Log.e("VIVS", "ViewPager onPageScrollStateChanged()"+state);
            }

        });
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.tabs);
        slidingTabLayout.setDistributeEvenly(false);
        slidingTabLayout.setViewPager(viewPager);


    }


    @Override
    public ViewPagerAdapter getPagerAdapter() {
        return viewViewPagerAdapter;
    }

}




