package com.example.testproject;

import android.app.Application;

import com.firebase.client.Firebase;

public class TestProject extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);
    }
}
