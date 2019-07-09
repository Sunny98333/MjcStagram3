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

public class MyphotoFragment extends Fragment {
    private RecyclerAdapter adapter;
    int size;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.myphoto_fragment, container, false);

        Context context = fv.getContext();

        RecyclerView photoRecyclerView = fv.findViewById(R.id.photoRecyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        photoRecyclerView.setLayoutManager(gridLayoutManager);

        DisplayMetrics dm = getActivity().getApplicationContext().getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        size = width / 3;

        adapter = new RecyclerAdapter();
        photoRecyclerView.setAdapter(adapter);
        getData();

        return fv.getRootView();
    }

    private void getData() {
        // 임의의 데이터입니다.

        List<Integer> listResId = Arrays.asList(
                R.drawable.ic_add_box_black,
                R.drawable.ic_account_box_black,
                R.drawable.ic_person_black
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
