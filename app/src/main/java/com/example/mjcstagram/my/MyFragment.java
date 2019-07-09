package com.example.mjcstagram.my;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mjcstagram.Mjcapplication;
import com.example.mjcstagram.R;
import com.example.mjcstagram.my.viewpager.MyTabPagerAdapter;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class MyFragment extends Fragment {
    private RecyclerAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    Data data;

    public static MyFragment newinstance(){
        return new MyFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.my_fragment, container, false);


        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) fv.findViewById(R.id.collpaseBar);
        collapsingToolbarLayout.setTitle("테스트타이틀");

        TextView postText;
        AppCompatTextView userEmail;
        TextView userName;
        ImageView profil;
        androidx.appcompat.widget.Toolbar toolbar;

        postText = fv.findViewById(R.id.post);
        toolbar = fv.findViewById(R.id.toolbar);
        AppBarLayout appBarLayout = (AppBarLayout) fv.findViewById(R.id.appBarLayout);
        viewPager = fv.findViewById(R.id.pager);

        //유저 정보 부분
        FirebaseUser user = Mjcapplication.getUserInfo();
        assert user != null;

        Uri profiluri = user.getPhotoUrl();

        userEmail = (AppCompatTextView) fv.findViewById(R.id.userEmail);
        userEmail.setText(user.getEmail());
        userName = (TextView) fv.findViewById(R.id.userName);
        userName.setText(user.getDisplayName());
        profil = (ImageView)fv.findViewById(R.id.profil);
        profil.setBackground(new ShapeDrawable(new OvalShape()));
        profil.setClipToOutline(true);

        Glide.with(this).load(profiluri).apply(new RequestOptions().circleCrop()).into(profil);

        //AppBarLayout, 아래부분 같이 터치하는 부분
        int dpHeight = getResources().getDisplayMetrics().heightPixels;
        int tbHeight = toolbar.getHeight();
        int myInfoTopHeight = (int)(((dpHeight - tbHeight) / 10) * 2.62);
        appBarLayout.getLayoutParams().height = myInfoTopHeight;
        viewPager.getLayoutParams().height = dpHeight - myInfoTopHeight;

        //뷰페이퍼 선언부분
        tabLayout = fv.findViewById(R.id.tabLayout);
        viewPager.setAdapter(new MyTabPagerAdapter(getChildFragmentManager()));
        viewPager.setOffscreenPageLimit(2);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_view_quilt_black),0,true);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_account_box_gray),1);


        // Set TabSelectedListener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition()==0){
                    tabLayout.getTabAt(0).setIcon(R.drawable.ic_view_quilt_black);
                    tabLayout.getTabAt(1).setIcon(R.drawable.ic_account_box_gray);
                }else if(tab.getPosition()==1){
                    tabLayout.getTabAt(0).setIcon(R.drawable.ic_view_quilt_gray);
                    tabLayout.getTabAt(1).setIcon(R.drawable.ic_account_box_black);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //리사이클뷰에 데이터 불러오는 부분

        return fv.getRootView();
    }


    private void setIcon(int... icons){

    }
}

