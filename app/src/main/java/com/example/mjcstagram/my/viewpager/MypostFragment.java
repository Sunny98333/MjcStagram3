package com.example.mjcstagram.my.viewpager;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fv = inflater.inflate(R.layout.mypost_fragment, container, false);

        //리사이클러 컨텐츠 적용부분

        Context context = fv.getContext();

        RecyclerView postRecyclerView = fv.findViewById(R.id.postRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        postRecyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerAdapter();
        postRecyclerView.setAdapter(adapter);
        getData();

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