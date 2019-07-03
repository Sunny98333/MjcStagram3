package com.example.mjcstagram.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.mjcstagram.R;
import com.example.mjcstagram.news.viewpager.TabPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class NewsFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fv = inflater.inflate(R.layout.news_fragment, container, false);


        viewPager = fv.findViewById(R.id.pager);
        tabLayout = fv.findViewById(R.id.tabLayout);
        viewPager.setAdapter(new TabPagerAdapter(getChildFragmentManager()));
        viewPager.setOffscreenPageLimit(2);
        tabLayout.addTab(tabLayout.newTab().setText("팔로잉"),0,true);
        tabLayout.addTab(tabLayout.newTab().setText("내 소식"),1);

        // Initializing ViewPager


        // Creating TabPagerAdapter adapter



        // Set TabSelectedListener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        return fv.getRootView();
    }
}
