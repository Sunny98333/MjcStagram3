package com.example.mjcstagram.home;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mjcstagram.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

    private Home_RecyclerAdapter adapter;
    private int width;

    public static HomeFragment newinstance(){
        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.home_fragment,container,false);
        Context context = fv.getContext();
        ArrayList<Home_Data> items = new ArrayList<>();


//recyclerview 부착
        RecyclerView recyclerView = fv.findViewById(R.id.home_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);


        adapter = new Home_RecyclerAdapter(getActivity().getSupportFragmentManager(),items);
        recyclerView.setAdapter(adapter);

        DisplayMetrics dm = getActivity().getApplicationContext().getResources().getDisplayMetrics();
        width = dm.widthPixels;

        getData();
        // Inflate the layout for this fragment
        return fv.getRootView();
    }

    private void getData(){
       List<String> list_user_name = Arrays.asList("good boy","곽","철");
        List<Integer> list_user_img = Arrays.asList(
                R.drawable.dog7,
                R.drawable.dog8,
                R.drawable.dog9
        );
        List<Integer> list_imgV = Arrays.asList(
                R.drawable.dog7,
                R.drawable.dog8,
                R.drawable.dog9
        );

        List<Integer> list_my_img = Arrays.asList(
                R.drawable.dog7,
                R.drawable.dog8,
                R.drawable.dog9
        );
        List<String> list_other_user_comment = Arrays.asList("피드가 넘 좋네요^^","선팔 맞팔~","너무 귀여워요!");
        List<String> list_comment_count = Arrays.asList("6","9","27");
        List<String> list_likes = Arrays.asList("12","21","33");
        List<String> list_other_user_name = Arrays.asList("asdf","wefsf","wsefsdf");

/*
        // Fragment로 넘길 Image Resource
        ArrayList<Integer> listImage = new ArrayList<>();
        listImage.add(R.drawable.dog9);
        listImage.add(R.drawable.dog8);
        listImage.add(R.drawable.dog7); */

        for(int i=0; i< list_user_img.size(); i++){
            Home_Data data = new Home_Data();

            data.setUser_img(list_user_img.get(i));
            data.setUser_name(list_user_name.get(i));
            data.setImgV(list_imgV.get(i));
            data.setOther_user_name(list_other_user_name.get(i));
            data.setMy_img(list_my_img.get(i));
            data.setOther_user_comment(list_other_user_comment.get(i));
            data.setSize(width);
            data.setComment_count("댓글 "+ list_comment_count.get(i)+"개 모두 보기");
            data.setLikes(list_likes.get(i)+" likes");
            adapter.addItem(data);
        }

/*        // FragmentAdapter에 Fragment 추가, Image 개수만큼 추가
        for (int i = 0; i < listImage.size(); i++) {
            Img_Fragment imageFragment = new Img_Fragment();
            Bundle bundle = new Bundle();
            bundle.putInt("imgRes", listImage.get(i));
            imageFragment.setArguments(bundle);
            adapter.addItem(imageFragment);
        }*/
        adapter.notifyDataSetChanged();
    }
/*
    class FragmentAdapter extends FragmentPagerAdapter{
        // ViewPager에 들어갈 Fragment들을 담을 리스트
        private ArrayList<Fragment> fragments = new ArrayList<>();

        // 필수 생성자
        FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        // List에 Fragment를 담을 함수
        void addItem(Fragment fragment) {
            fragments.add(fragment);
        }
    } */
}
