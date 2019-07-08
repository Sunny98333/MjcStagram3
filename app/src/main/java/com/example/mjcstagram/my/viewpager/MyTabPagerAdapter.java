package com.example.mjcstagram.my.viewpager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class MyTabPagerAdapter extends FragmentStatePagerAdapter {

    public MyTabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        // Returning the current tabs
        switch (position) {
            case 0:
                return new MypostFragment();
            case 1:
                return new MyphotoFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}


