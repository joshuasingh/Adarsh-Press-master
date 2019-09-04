package com.example.testproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AdminDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
    }

    public void addItems(View view)
    {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void viewItems(View view)
    {
        Intent intent = new Intent(this,ViewItems.class);
        startActivity(intent);
    }

    public void assignID(View view)
    {
        Intent intent = new Intent(this,id.class);
        startActivity(intent);
    }


}
