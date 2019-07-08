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
import com.example.mjcstagram.news.viewpager.TabPagerAdapter;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class MyFragment_backup extends Fragment {
    private RecyclerAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static MyFragment_backup newinstance(){
        return new MyFragment_backup();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.my_fragment, container, false);


        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) fv.findViewById(R.id.collpaseBar);
        collapsingToolbarLayout.setTitle("테스트타이틀");

        AppCompatTextView userEmail;
        TextView userName;
        ImageView profil;
        androidx.appcompat.widget.Toolbar toolbar;

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
        viewPager.setAdapter(new TabPagerAdapter(getChildFragmentManager()));
        viewPager.setOffscreenPageLimit(2);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_view_quilt_black),0,true);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_account_box_gray),1);

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

        //리사이클뷰에 데이터 불러오는 부분

        return fv.getRootView();
    }
    private void getData() {
        // 임의의 데이터입니다.
        List<String> listTitle = Arrays.asList("국화", "사막", "수국");
        List<String> listContent = Arrays.asList(
                "이 꽃은 국화입니다.",
                "여기는 사막입니다.",
                "이 꽃은 수국입니다."
        );
        List<Integer> listResId = Arrays.asList(
                R.drawable.ic_add_box_black,
                R.drawable.ic_account_box_black,
                R.drawable.ic_person_black
        );
        for (int i = 0; i < listTitle.size(); i++) {
            // 각 List의 값들을 data 객체에 set 해줍니다.
            Data data = new Data();
            data.setTitle(listTitle.get(i));
            data.setContent(listContent.get(i));
            data.setResId(listResId.get(i));

            // 각 값이 들어간 data를 adapter에 추가합니다.
            adapter.addItem(data);
        }
        adapter.notifyDataSetChanged();
    }
}
