package com.example.mjcstagram.my.viewpager;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mjcstagram.R;
import com.example.mjcstagram.my.Data;
import com.example.mjcstagram.my.RecyclerAdapter;

import java.util.Arrays;
import java.util.List;

public class MypostFragment extends Fragment {
    private RecyclerAdapter adapter;
    View fv;
    int size;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fv = inflater.inflate(R.layout.mypost_fragment, container, false);
        View myfrag = getLayoutInflater().inflate(R.layout.my_fragment, null, false);

        //리사이클러 컨텐츠 적용부분

        RecyclerView postRecyclerView = fv.findViewById(R.id.postRecyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),3);
        postRecyclerView.setLayoutManager(gridLayoutManager);

        DisplayMetrics dm = getActivity().getApplicationContext().getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        size = width/3-2;
        adapter = new RecyclerAdapter();
        postRecyclerView.setAdapter(adapter);
        getData();
        return fv.getRootView();
    }

    private void getData() {
        // 임의의 데이터입니다.
        List<Integer> listResId = Arrays.asList(
                R.drawable.desert,
                R.drawable.penguins,
                R.drawable.tulips,
                R.drawable.desert,
                R.drawable.desert,
                R.drawable.penguins,
                R.drawable.tulips,
                R.drawable.desert,
                R.drawable.desert,
                R.drawable.penguins,
                R.drawable.tulips,
                R.drawable.desert
        );
        for (int i = 0; i < listResId.size(); i++) {
            // 각 List의 값들을 data 객체에 set 해줍니다.
            Data data = new Data();
            data.setResId(listResId.get(i));
            data.setSize(size);
            // 각 값이 들어간 data를 adapter에 추가합니다.
            adapter.addItem(data);
        }
        adapter.notifyDataSetChanged();
    }
}