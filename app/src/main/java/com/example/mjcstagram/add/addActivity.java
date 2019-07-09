package com.example.mjcstagram.add;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.example.mjcstagram.R;
import com.example.mjcstagram.add.fragment.AddTabPagerAdapter;
import com.example.mjcstagram.databinding.AddBinding;
import com.example.mjcstagram.news.viewpager.TabPagerAdapter;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.tabs.TabLayout;

public class addActivity extends AppCompatActivity {


    AddBinding addBinding;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addBinding = DataBindingUtil.setContentView(this,R.layout.add);

        addBinding.pager.setAdapter(new AddTabPagerAdapter(getSupportFragmentManager()));
        addBinding.pager.setOffscreenPageLimit(3);
        addBinding.tabLayout.addTab(addBinding.tabLayout.newTab().setText("라이브러리"),0,true);
        addBinding.tabLayout.addTab(addBinding.tabLayout.newTab().setText("사진"),1);
        addBinding.tabLayout.addTab(addBinding.tabLayout.newTab().setText("동영상"),2);

        addBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==0){
                    addBinding.list.setText("갤러리");
                    addBinding.next.setText("다음");
                }else if(tab.getPosition()==1){
                    addBinding.list.setText("사진");
                    addBinding.next.setText("");
                }else{
                    addBinding.list.setText("동영상");
                    addBinding.next.setText("다음");
                }
                addBinding.pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        addBinding.pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(addBinding.tabLayout));




    }
}
