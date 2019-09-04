package com.example.testproject;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class updateDelete extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText itemName,unit,costPrice, totalCP,wholesalePrice, retailPrice, pUid;
    TextView key1;
    DatabaseReference ref;
    DatabaseReference ref1;
    String parentuid;
    String gsT_sel;
    String unit_sel;
    ArrayAdapter<CharSequence> adapter;
    ArrayAdapter<CharSequence> adapter1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);
        itemName = (EditText) findViewById(R.id.item);
        unit = (EditText) findViewById(R.id.typeUnit);

        costPrice = (EditText) findViewById(R.id.costPrice);
        totalCP = (EditText) findViewById(R.id.totalCp);
        wholesalePrice= (EditText) findViewById(R.id.wholesalePrice);
        retailPrice = (EditText) findViewById(R.id.retailPrice);
        key1= (TextView) findViewById(R.id.key);
        pUid= (EditText) findViewById(R.id.pUid);

        String key = getIntent().getExtras().get("key").toString();
        ref = FirebaseDatabase.getInstance().getReference().child("Items");
         ref1 = FirebaseDatabase.getInstance().getReference().child("Items").child(key);

        itemName.setText(getIntent().getStringExtra("itemName"));
        unit.setText(getIntent().getStringExtra("unit"));
        gsT_sel=getIntent().getStringExtra("gst");
        unit_sel=getIntent().getStringExtra("sunit");
        costPrice.setText(getIntent().getStringExtra("costPrice"));
        totalCP.setText(getIntent().getStringExtra("totalCP"));
        wholesalePrice.setText(getIntent().getStringExtra("wholesalePrice"));
        retailPrice.setText(getIntent().getStringExtra("retailPrice"));
        parentuid= getIntent().getStringExtra("pUid");

        key1.setText(key);



        List<String> gstss = Arrays.asList(getResources().getStringArray(R.array.gst));
        List<String> unitss = Arrays.asList(getResources().getStringArray(R.array.units));





        Spinner spinner = findViewById(R.id.unit);
         adapter= ArrayAdapter.createFromResource(this,R.array.units, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection( unitss.indexOf(unit_sel));
        spinner.setOnItemSelectedListener(this);

        Spinner spinner1 = findViewById(R.id.gst);
        adapter1 = ArrayAdapter.createFromResource(this,R.array.gst, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setSelection(gstss.indexOf(gsT_sel));
        spinner1.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(this,adapterView.getItemAtPosition(i).toString(),Toast.LENGTH_LONG).show();

        try {
            if (adapterView.getAdapter().equals(adapter)) {
                unit_sel = adapterView.getItemAtPosition(i).toString();
            } else if(adapterView.getAdapter().equals(adapter1)){
                gsT_sel = adapterView.getItemAtPosition(i).toString();
            }
        }catch (Exception e)
        {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void update(View view) {
     ref1.addListenerForSingleValueEvent(new ValueEventListener() {
         @Override

         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
             dataSnapshot.getRef().child("itemName").setValue(itemName.getText().toString());
             dataSnapshot.getRef().child("gst").setValue(gsT_sel);
             dataSnapshot.getRef().child("sunit").setValue(unit_sel);

             dataSnapshot.getRef().child("unit").setValue(unit.getText().toString());

             dataSnapshot.getRef().child("costPrice").setValue(costPrice.getText().toString());
             dataSnapshot.getRef().child("totalCP").setValue(totalCP.getText().toString());
             dataSnapshot.getRef().child("wholesalePrice").setValue(wholesalePrice.getText().toString());
             dataSnapshot.getRef().child("retailPrice").setValue(retailPrice.getText().toString());

             Toast.makeText(updateDelete.this,"Data Updated..", Toast.LENGTH_LONG).show();
             updateDelete.this.finish();
         }

         @Override
         public void onCancelled(@NonNull DatabaseError databaseError) {

         }
     });

    }

    public void delete(View view) {
        ref.child(parentuid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>(){
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()) {
                    Toast.makeText(updateDelete.this, "Record Deleted.", Toast.LENGTH_LONG).show();
                    updateDelete.this.finish();
                }else{
                    Toast.makeText(updateDelete.this, "Record not Deleted.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
