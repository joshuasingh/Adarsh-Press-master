package com.example.testproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.w3c.dom.Text;

public class ViewItems extends AppCompatActivity {

    ListView lv;
    FirebaseListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_items);

        lv= (ListView) findViewById(R.id.listView);
        Query query= FirebaseDatabase.getInstance().getReference().child("Items");
        FirebaseListOptions<Items> options = new FirebaseListOptions.Builder<Items>().setLayout(R.layout.view_items).setQuery(query, Items.class).build();

        adapter= new FirebaseListAdapter(options) {
            @Override
            protected void populateView(@NonNull View v, @NonNull Object model, int position) {

            try {
                TextView itemName = v.findViewById(R.id.itemName);
                TextView unit = v.findViewById(R.id.typeUnit);
                TextView costPrice = v.findViewById(R.id.costPrice);
                TextView sUnit = v.findViewById(R.id.unit);
                TextView gst = v.findViewById(R.id.gst);
                TextView totalCP = v.findViewById(R.id.totalCp);
                TextView wholesalePrice = v.findViewById(R.id.wholesalePrice);
                TextView retailPrice = v.findViewById(R.id.retailPrice);


                Items item = (Items) model;
                itemName.setText("Item Name: " + item.getItemName().toString());
                unit.setText("Units: " + item.getUnit().toString());
                sUnit.setText("UnitType: " + item.getSUnit());
                costPrice.setText("Cost Price: " + item.getCostPrice().toString());
                gst.setText("GST: " + item.getGST());
                totalCP.setText("Total Cost Price: " + item.getTotalCP().toString());
                wholesalePrice.setText("WholeSale Price: " + item.getWholesalePrice().toString());
                retailPrice.setText("Retail Price: " + item.getRetailPrice().toString());

            }
            catch (Exception e)
            {
                Toast.makeText(ViewItems.this,e.getMessage(),Toast.LENGTH_LONG).show();
            }

            }
        };

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent updateDelete = new Intent(ViewItems.this,updateDelete.class);
                Items item = (Items) adapterView.getItemAtPosition(i);
                updateDelete.putExtra("itemName", item.getItemName());
                updateDelete.putExtra("unit", item.getUnit());
                updateDelete.putExtra("sunit", item.getSUnit());
                updateDelete.putExtra("gst", item.getGST());
                updateDelete.putExtra("costPrice", item.getCostPrice());
                updateDelete.putExtra("totalCP", item.getTotalCP());
                updateDelete.putExtra("wholesalePrice", item.getWholesalePrice());
                updateDelete.putExtra("retailPrice", item.getRetailPrice());
                updateDelete.putExtra("key", item.getpUid());
                updateDelete.putExtra("pUid", item.getpUid());
                startActivity(updateDelete);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
