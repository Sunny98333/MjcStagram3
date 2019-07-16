package com.example.mjcstagram.home;


import android.content.ClipData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mjcstagram.R;
import com.example.mjcstagram.databinding.HomeFragmentInnerBinding;
import com.example.mjcstagram.my.Data;
import com.example.mjcstagram.my.RecyclerAdapter;

import java.util.ArrayList;

public class Home_RecyclerAdapter extends RecyclerView.Adapter<Home_RecyclerAdapter.ItemViewHolder> {

    HomeFragmentInnerBinding binding;
    private ArrayList<Home_Data> listData = new ArrayList<>();

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.home_fragment_inner,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.onbind(listData.get(position));

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    void addItem(Home_Data data){
        listData.add(data);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{

        private ImageView user_img = binding.userProfil;
        private AppCompatTextView user_name = binding.userName;
        private TextView likes = binding.like;
        private TextView other_user_name = binding.commentUser;
        private  TextView other_user_comment =binding.commentContents;
        private TextView comment_count = binding.commentCount;
        private ImageView my_img = binding.commentUserProfil;
        public ItemViewHolder(View itemView){
            super(itemView);
        }
        void onbind(Home_Data data){
            user_name.setText(data.getUser_name());
            user_img.setImageResource(Integer.parseInt(data.getUser_img()));
            likes.setText(data.getLikes());
            other_user_name.setText(data.getOther_user_name());
            other_user_comment.setText(data.getOther_user_comment());
            comment_count.setText(data.getComment_count());
            my_img.setImageResource(Integer.parseInt(data.getMy_img()));
        }
    }
}