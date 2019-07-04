package com.example.mjcstagram.search.viewpager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class SearchTabPagerAdapter extends FragmentStatePagerAdapter {

    public SearchTabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        // Returning the current tabs
        switch (position) {
            case 0:
                return new PopularFragment();
            case 1:
                return new AccountFragment();
            case 2:
                return new TagFragment();
            case 3:
                return new PlaceFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}


