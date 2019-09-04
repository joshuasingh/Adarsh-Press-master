package com.example.testproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class AssignID extends AppCompatActivity {

    ListView lv;
    FirebaseListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_id);

        lv = findViewById(R.id.EmployeeList);
        Query query = FirebaseDatabase.getInstance().getReference().child("Employee");
        FirebaseListOptions<Employee> options = new FirebaseListOptions.Builder<Employee>().setLayout(R.layout.view_emp).setQuery(query, Employee.class).build();


        adapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(@NonNull View v, @NonNull Object model, int position) {

                TextView id = v.findViewById(R.id.id);
                TextView name = v.findViewById(R.id.Name);
                TextView doj = v.findViewById(R.id.doj);

                Employee emp = (Employee) model;
                id.setText("Employee Id :" + emp.getEmpId());
                name.setText("Employee Name: " + emp.getName());
                doj.setText("Date of Joining: " + emp.getDOJ());

            }
        };

        lv.setAdapter(adapter);


    }



    }
