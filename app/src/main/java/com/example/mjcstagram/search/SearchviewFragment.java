package com.example.mjcstagram.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.mjcstagram.R;

public class SearchviewFragment extends Fragment {

    public static SearchviewFragment newinstance(){
        return new SearchviewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.searchview_fragment, container, false);
    }

}
