package com.emergencysavior.emergencysaviorwatch;

import android.location.Location;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.pixelcan.inkpageindicator.InkPageIndicator;

/**
 * Created by RSA on 04-06-2016.
 */
public class Dashboard extends FragmentActivity {
    InkPageIndicator mIndicator;
    static final int NUM_ITEMS = 3;
    double latitude, longitude;
    public static Location loc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mIndicator = (InkPageIndicator) findViewById(R.id.indicator);
        mIndicator.setViewPager(pager);


    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch (pos) {
                case 0:
                    return Dasboard_Activity_two.newInstance("FirstFragment, Instance 1");
                case 1:
                    return Dashboard_actvity_three.newInstance("SecondFragment, Instance 1");
                case 2:
                    return Preference.newInstance("thirdFragment, Instance 1");
                default:
                    return null;

            }

        }


        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }
    }


}
