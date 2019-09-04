package com.example.testproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.testproject.MainActivity.s1;
import static com.example.testproject.MainActivity.s2;

public class id extends AppCompatActivity {

    EditText id, name, doj;
    Button assign;
    FirebaseDatabase database;
    DatabaseReference ref;
    Employee emp;
    String parentuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id);

        id= (EditText) findViewById(R.id.id);
        name= (EditText) findViewById(R.id.name);
        doj= (EditText) findViewById(R.id.doj);
        assign= (Button) findViewById(R.id.btn);
        database = FirebaseDatabase.getInstance();

        emp= new Employee();

        ref= FirebaseDatabase.getInstance().getReference().child("Employee");
        parentuid= ref.push().getKey();

        assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getValues();
                ref.child(parentuid).setValue(emp);
                Toast.makeText(id.this,"data inserted..",Toast.LENGTH_LONG).show();
                btnInsert();


            }
        });



    }



    private void getValues()
    {
        emp.setEmpId(id.getText().toString());
        emp.setName(name.getText().toString());
        emp.setDOJ(doj.getText().toString());
    }


    public void empView(View view) {
        Intent intent = new Intent(this,AssignID.class);
        startActivity(intent);
    }

    public void btnInsert()
    {
        Intent intent = new Intent(this,id.class);
        startActivity(intent);
    }
}
