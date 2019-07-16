package com.example.mjcstagram.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mjcstagram.R;

import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

    private Home_RecyclerAdapter adapter;

    public static HomeFragment newinstance(){
        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        init();
        getData();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    private void init() {
        RecyclerView recyclerView = getView().findViewById(R.id.home_re);

        adapter = new Home_RecyclerAdapter();
        recyclerView.setAdapter(adapter);

    }

    private void getData(){
        List<String> list_user_name = Arrays.asList("a","b","c");
        List<Integer> list_user_img = Arrays.asList(
                R.drawable.dog7,
                R.drawable.dog8,
                R.drawable.dog9
        );
        List<Integer> list_likes = Arrays.asList(1,2,3);
        List<String> list_other_user_name = Arrays.asList("a","b","c");
        List<String> list_other_user_comment = Arrays.asList("a","b","c");
        List<Integer> list_comment_count = Arrays.asList(1,2,3);
        List<Integer> list_my_img = Arrays.asList(
                R.drawable.dog7,
                R.drawable.dog8,
                R.drawable.dog9
        );
        for(int i=0; i< list_comment_count.size(); i++){
            Home_Data data = new Home_Data();
            data.setComment_count(list_comment_count.get(i));
            data.setLikes(list_likes.get(i));
            data.setMy_img(String.valueOf(list_my_img.get(i)));
            data.setUser_name(list_user_name.get(i));
            data.setOther_user_comment(list_other_user_comment.get(i));
            data.setOther_user_name(list_other_user_name.get(i));

            adapter.addItem(data);
        }
        adapter.notifyDataSetChanged();
    }

}
