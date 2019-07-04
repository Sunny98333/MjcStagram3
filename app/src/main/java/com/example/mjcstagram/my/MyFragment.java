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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mjcstagram.Mjcapplication;
import com.example.mjcstagram.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.auth.FirebaseUser;

public class MyFragment extends Fragment {

    public static MyFragment newinstance(){
        return new MyFragment();
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
        RecyclerView recyclerView = (RecyclerView) fv.findViewById(R.id.racyclerView);

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

        int dpHeight = getResources().getDisplayMetrics().heightPixels;
        int tbHeight = toolbar.getHeight();
        int myInfoTopHeight = (int)(((dpHeight - tbHeight) / 10) * 3.5);
        appBarLayout.getLayoutParams().height = myInfoTopHeight;
        recyclerView.getLayoutParams().height = dpHeight - myInfoTopHeight;


        return fv.getRootView();
    }

}
