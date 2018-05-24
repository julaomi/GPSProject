package com.example.tadje.gpsproject.ViewPager;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.tadje.gpsproject.MainActivity;
import com.example.tadje.gpsproject.MapsFragment;
import com.example.tadje.gpsproject.SettingFragment;
import com.example.tadje.gpsproject.UserFragment;

/**
 * Created by tadje on 03.05.2018.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */

    private static int NUM_ITEMS = 3;



    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class
        // below).
        switch (position) {
            case 0:
                return new MapsFragment();
            case 1:
                return UserFragment.newInstance(1, "User");
            case 2:
                return SettingFragment.newInstance(2, "Setting");
            default:
                return null;
        }
    }



    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }

    }

