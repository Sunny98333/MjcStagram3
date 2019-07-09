package com.example.mjcstagram.add;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mjcstagram.R;
import com.google.android.material.bottomappbar.BottomAppBar;

public class addActivity extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);

        BottomAppBar bottomAppBar = findViewById(R.id.bar);
        setSupportActionBar(bottomAppBar);



    }
}
