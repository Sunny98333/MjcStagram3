package com.example.mjcstagram;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Mjcapplication extends Application {

    public static FirebaseAuth mAuth;
    public static FirebaseUser mUser;

    public static FirebaseUser getUserInfo() {
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        return mUser;
    }


}