package com.example.mjcstagram;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mjcstagram.databinding.Main;
import com.example.mjcstagram.home.HomeFragment;
import com.example.mjcstagram.my.MyFragment;
import com.example.mjcstagram.news.NewsFragment;
import com.example.mjcstagram.search.SearchFragment;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Main binding;
    int check;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         binding= DataBindingUtil.setContentView(this,R.layout.activity_main);
         binding.setActivity(this);
         fragmentManager = getSupportFragmentManager();
         setStartFragment(new HomeFragment().newinstance());

     }
    public void setFragment(View view) {
        switch (view.getId()) {
            case R.id.homeIcon:
                setIcons(R.drawable.ic_home_black, R.drawable.ic_search_white, R.drawable.heart, R.drawable.ic_person_outline_black);
                setStartFragment(new HomeFragment().newinstance());
                check=0;
                break;
            case R.id.searchIcon:
                setIcons(R.drawable.ic_home_white, R.drawable.ic_search_black, R.drawable.heart, R.drawable.ic_person_outline_black);
                setStartFragment(new SearchFragment().newinstance());
                check=1;
                break;
            case R.id.newsIcon:
                setIcons(R.drawable.ic_home_white, R.drawable.ic_search_white, R.drawable.heart2, R.drawable.ic_person_outline_black);
                setStartFragment(new NewsFragment().newinstance());
                check=2;
                break;
            case R.id.myIcon:
                setIcons(R.drawable.ic_home_white, R.drawable.ic_search_white, R.drawable.heart, R.drawable.ic_person_black);
                setStartFragment(new MyFragment().newinstance());
                check=3;
                break;
        }

    }
    public void setIcons(int... icons){
         binding.homeIcon.setImageResource(icons[0]);
         binding.searchIcon.setImageResource(icons[1]);
         binding.newsIcon.setImageResource(icons[2]);
         binding.myIcon.setImageResource(icons[3]);
    }

    private void setStartFragment(Fragment fragment){
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, fragment).commit();
    }
    @Override
    public void onBackPressed() {

        if(check==0){
            super.onBackPressed();
        }else{
            setIcons(R.drawable.ic_home_black, R.drawable.ic_search_white, R.drawable.heart, R.drawable.ic_person_outline_black);
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new HomeFragment().newinstance()).commit();
            check=0;
        }
    }

}