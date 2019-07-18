package com.example.mjcstagram.home;


import android.content.ClipData;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.mjcstagram.R;
import com.example.mjcstagram.databinding.HomeFragmentInnerBinding;
import com.example.mjcstagram.my.Data;
import com.example.mjcstagram.my.RecyclerAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class Home_RecyclerAdapter extends RecyclerView.Adapter<Home_RecyclerAdapter.ItemViewHolder> {

    HomeFragmentInnerBinding binding;
    private ArrayList<Home_Data> listData = new ArrayList<>();

    //view pager로 fragmet생성 위해
    private  FragmentManager fragmentManager;

    HashMap<Integer, Integer> mViewPagerState = new HashMap<>();

    public Home_RecyclerAdapter(FragmentManager fragmentManager, ArrayList<Home_Data> items){
        this.listData = listData;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.home_fragment_inner,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        BannerPagerAdapter bannerPagerAdapter = new BannerPagerAdapter(fragmentManager);
        holder.vp.setAdapter(bannerPagerAdapter);
        holder.vp.setId(position+1);

        if (mViewPagerState.containsKey(position)) {
            holder.vp.setCurrentItem(mViewPagerState.get(position));
        }

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    void addItem(Home_Data data){
        listData.add(data);
    }

    @Override
    public void onViewRecycled(@NonNull ItemViewHolder holder) {
        //mViewPagerState.put(holder.getAdapterPosition(),holder.imgV.getCurrentItem());
        super.onViewRecycled(holder);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        public ViewPager vp;
        private ImageView user_img;
        private AppCompatTextView user_name;
        private TextView likes;
        private TextView other_user_name;
        private TextView other_user_comment;
        private TextView comment_count;
        private ImageView my_img;
        private ImageView imgV;
        private ConstraintLayout contentlayout;

        public ItemViewHolder(View itemView) {
            super(itemView);

            vp = (ViewPager)itemView.findViewById(R.id.imgV);

            user_img = itemView.findViewById(R.id.user_profil);
            user_name = itemView.findViewById(R.id.userName);
           // imgV = itemView.findViewById(R.id.imgV);
            other_user_name = itemView.findViewById(R.id.comment_user);
            contentlayout = itemView.findViewById(R.id.contentlayout);
            my_img = itemView.findViewById(R.id.comment_user_profil);
            other_user_comment = itemView.findViewById(R.id.comment_contents);
            comment_count = itemView.findViewById(R.id.comment_count);
            likes = itemView.findViewById(R.id.like);

            user_img.setBackground(new ShapeDrawable(new OvalShape()));
            user_img.setClipToOutline(true);
            my_img.setBackground(new ShapeDrawable(new OvalShape()));
            my_img.setClipToOutline(true);


        }

        void onBind(Home_Data data) {

            user_name.setText(data.getUser_name());
            user_img.setImageResource(data.getUser_img());
            imgV.setImageResource(data.getImgV());
            imgV.getLayoutParams().height = data.getSize();
            other_user_name.setText(data.getOther_user_name());
            other_user_comment.setText(data.getOther_user_comment());
            comment_count.setText(data.getComment_count());
            likes.setText(data.getLikes());
            my_img.setImageResource(data.getMy_img());
        }
    } public class BannerPagerAdapter extends FragmentPagerAdapter {
        public BannerPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ImageFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 5;
        }
    }
}