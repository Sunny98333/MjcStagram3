package com.example.mjcstagram.add.fragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import com.example.mjcstagram.databinding.AddBinding;
import com.example.mjcstagram.my.viewpager.MyphotoFragment;
import com.example.mjcstagram.my.viewpager.MypostFragment;

public class AddTabPagerAdapter extends FragmentStatePagerAdapter {


    public AddTabPagerAdapter(FragmentManager fm) {
        super(fm);
    }



    @Override
    public Fragment getItem(int position) {
        // Returning the current tabs
        switch (position) {
            case 0:
                return new GalleryFragment();
            case 1:
                return new CameraFragment();
            case 2:

                return new VideoFragment();
            default:
                return null;
        }
    }



    @Override
    public int getCount() {
        return 3;
    }
}


