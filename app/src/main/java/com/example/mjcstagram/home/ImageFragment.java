package com.example.mjcstagram.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mjcstagram.R;

public class ImageFragment extends Fragment {

    private static  final String ARG_PARAM1 = "param1";
    private  int mParam1;

    public ImageFragment(){

    }

    public static ImageFragment newInstance(int param1){
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1,param1);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() !=null){
            mParam1 = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_img_fragment, container, false);
        TextView tv = (TextView) v.findViewById(R.id.textview);
        tv.setText(mParam1 + "");

        return v;
    }
}
