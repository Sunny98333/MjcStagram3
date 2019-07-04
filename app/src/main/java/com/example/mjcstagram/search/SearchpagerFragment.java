package com.example.mjcstagram.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.mjcstagram.R;
import com.example.mjcstagram.search.viewpager.SearchTabPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class SearchpagerFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    public static SearchpagerFragment newInstance(){
        return new SearchpagerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.searchpager_fragment, container, false);


        viewPager = fv.findViewById(R.id.pager);
        tabLayout = fv.findViewById(R.id.tabLayout);
        viewPager.setAdapter(new SearchTabPagerAdapter(getChildFragmentManager()));
        viewPager.setOffscreenPageLimit(4);
        tabLayout.addTab(tabLayout.newTab().setText("인기"),0,true);
        tabLayout.addTab(tabLayout.newTab().setText("계정"),1);
        tabLayout.addTab(tabLayout.newTab().setText("태그"),2);
        tabLayout.addTab(tabLayout.newTab().setText("장소"),3);

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
