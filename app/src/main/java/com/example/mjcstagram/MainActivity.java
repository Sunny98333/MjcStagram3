package com.example.mjcstagram;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mjcstagram.add.addActivity;
import com.example.mjcstagram.databinding.Main;
import com.example.mjcstagram.home.HomeFragment;
import com.example.mjcstagram.my.MyFragment;
import com.example.mjcstagram.news.NewsFragment;
import com.example.mjcstagram.search.SearchFragment;
import com.facebook.login.LoginManager;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;


public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Main binding;
    int check;
    int loginWay;
    private GoogleApiClient mGoogleApiClient;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         binding= DataBindingUtil.setContentView(this, R.layout.activity_main);
         binding.setActivity(this);
         Intent intent=getIntent();
         loginWay=intent.getIntExtra("login",2);
         fragmentManager = getSupportFragmentManager();
         setStartFragment(new HomeFragment().newinstance());

         binding.addIcon.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent addintent = new Intent(getApplicationContext(), addActivity.class);
                 startActivity(addintent);
             }
         });

     }
    public void setFragment(View view) {
        switch (view.getId()) {
            case R.id.homeIcon:
                setIcons(R.drawable.ic_home_black, R.drawable.ic_search_white, R.drawable.ic_favorite_border_black_24dp, R.drawable.ic_person_outline_black);
                setStartFragment(new HomeFragment().newinstance());
                check=0;
                break;
            case R.id.searchIcon:
                setIcons(R.drawable.ic_home_white, R.drawable.ic_search_black, R.drawable.ic_favorite_border_black_24dp, R.drawable.ic_person_outline_black);
                setStartFragment(new SearchFragment().newInstance());
                check=1;
                break;
            case R.id.newsIcon:
                setIcons(R.drawable.ic_home_white, R.drawable.ic_search_white, R.drawable.ic_favorite_black, R.drawable.ic_person_outline_black);
                setStartFragment(new NewsFragment());
                check=2;
                break;
            case R.id.myIcon:
                setIcons(R.drawable.ic_home_white, R.drawable.ic_search_white, R.drawable.ic_favorite_border_black_24dp, R.drawable.ic_person_black);
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
    private void signOut() {
        FirebaseUser user = Mjcapplication.getUserInfo();
        UserInfo userInfo = user.getProviderData().get(1);
        if (userInfo.getProviderId().contains("facebook")) {
            LoginManager.getInstance().logOut();
        }
        Toast.makeText(MainActivity.this, "로그아웃", Toast.LENGTH_SHORT).show();
        Mjcapplication.mAuth.signOut();
        Mjcapplication.mAuth = null;
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
    @Override
    public void onBackPressed() {

        if(check==0){
            signOut();
        }else{
            setIcons(R.drawable.ic_home_black, R.drawable.ic_search_white, R.drawable.heart, R.drawable.ic_person_outline_black);
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new HomeFragment().newinstance()).commit();
            check=0;
        }
    }

}