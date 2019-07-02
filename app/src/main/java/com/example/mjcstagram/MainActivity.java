package com.example.mjcstagram;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.mjcstagram.databinding.Main;

public class MainActivity extends AppCompatActivity {

    Main binding;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         binding= DataBindingUtil.setContentView(this,R.layout.activity_main);
         binding.setActivity(this);

     }
    public void homeFragment(View view) {

    }
    public void searchFragment(View view) {

    }
    public void likeFragment(View view) {

    }
    public void myFragment(View view) {

    }


}