package com.example.testproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText itemName;
    private EditText unit;
    private Spinner sUnit;
    private EditText costPrice;
    private Spinner gst;
    private EditText totalCp;
    private EditText wholesalePrice;
    private EditText retailPrice;
    private EditText pUid;
    private Button addData;
    private Button dashboard;
    private Button updatepic;
    private Firebase mRootRef;
    public static String s1 = null;
    public static String s2 = null;
    private final int PICK_IMAGE_REQUEST = 71;
    FirebaseStorage storage;
    StorageReference storageReference;
    private StorageReference mStorage;

    FirebaseDatabase database;
    DatabaseReference ref;
    Items item;
    String parentuid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemName = (EditText) findViewById(R.id.item);
        unit = (EditText) findViewById(R.id.typeUnit);
        sUnit= (Spinner) findViewById(R.id.unit);
        costPrice = (EditText) findViewById(R.id.costPrice);
        gst = (Spinner) findViewById(R.id.gst);
        totalCp = (EditText) findViewById(R.id.totalCp);
        wholesalePrice = (EditText) findViewById(R.id.wholesalePrice);
        retailPrice = (EditText) findViewById(R.id.retailPrice);
        pUid = (EditText) findViewById(R.id.pUid);
        addData = (Button) findViewById(R.id.addData);
        dashboard = (Button) findViewById(R.id.dashboard);
        updatepic=(Button) findViewById(R.id.updatepic);
        database = FirebaseDatabase.getInstance();


        //storage
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        item= new Items();

        ref= FirebaseDatabase.getInstance().getReference().child("Items");
        parentuid= ref.push().getKey();
        pUid.setText(parentuid);


        final Spinner spinner = findViewById(R.id.unit);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.units, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        final Spinner spinner1 = findViewById(R.id.gst);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,R.array.gst, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(this);



        costPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                s2= spinner1.getSelectedItem().toString();

                try {
                    if(!s2.equals("Select GST")) {
                        Float tempGST = Float.parseFloat( s2.substring(0, s2.length() - 1));
                        Float cp = Float.parseFloat(costPrice.getText().toString());
                        if (cp != null) {
                            Float temp = (tempGST / 100) * cp;
                            totalCp.setText(String.valueOf(new DecimalFormat("##.##").format(temp + cp)));
                        }
                    }
                }
                catch (Exception e)
                {
                    String val=e.getMessage();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                s1= (String) spinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                s2= spinner1.getSelectedItem().toString();

                try {
                    if(!s2.equals("Select GST")) {
                        Float tempGST = Float.parseFloat( s2.substring(0, s2.length() - 1));
                        Float cp = Float.parseFloat(costPrice.getText().toString());
                        if (cp != null) {
                            Float temp = (tempGST / 100) * cp;
                            totalCp.setText(String.valueOf(new DecimalFormat("##.##").format(temp + cp)));
                        }
                    }
                }
                catch (Exception e)
                {
                    String val=e.getMessage();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getValues();
                ref.child(parentuid).setValue(item);
                Toast.makeText(MainActivity.this,"data inserted..",Toast.LENGTH_LONG).show();
                btnInsert();


            }
        });

        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dashboard();


            }
        });



       updatepic.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

            chooseImage();
           }
       });



    }



    private void chooseImage()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE_REQUEST);

    }








    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

       try {
           if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
               if (data.getData() != null) {

                   Uri fileUri = data.getData();

                   String fileName = getFileName(fileUri);
                   final StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
                   ref.putFile(fileUri)
                           .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                               @Override
                               public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                   Toast.makeText(MainActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                                   //getting url of uploaded pic from firebase
                                   ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                       @Override
                                       public void onSuccess(Uri u) {
                                           //upload file to real timme database

                                  //you'll get the uri here 'u' save this in database and retrieve whenever need to access pic


                                           Toast.makeText(MainActivity.this, u.toString(), Toast.LENGTH_LONG).show();

                                       }
                                   });

                               }
                           })
                           .addOnFailureListener(new OnFailureListener() {
                               @Override
                               public void onFailure(@NonNull Exception e) {
                                   Log.v("findind","uanvle "+e.getMessage());
                                   Toast.makeText(MainActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                               }
                           });


               }

           }
       }catch (Exception e)
       {
           Toast.makeText(MainActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();

       }
    }


    public String getFileName(Uri uri)
    {
        String result=null;
        if(uri.getScheme().equals("contents"))
        {
            Cursor cursor=getContentResolver().query(uri,null,null,null,null);
            try
            {
                if(cursor!=null && cursor.moveToFirst())
                {
                    result=cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }finally {
                cursor.close();

            }
        }
        if(result==null)
        {
            result=uri.getPath();
            int cut=result.lastIndexOf('/');
            if(cut!=-1)
            {
                result=result.substring(cut+1);
            }

        }

        return result;

    }

















    public void btnInsert()
    {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void dashboard()
    {
        Intent intent = new Intent(this,AdminDashboard.class);
        startActivity(intent);
    }







    private void getValues()
    {
        item.setItemName(itemName.getText().toString());
        item.setUnit(unit.getText().toString());
        item.setSUnit(s1);
        item.setCostPrice(costPrice.getText().toString());
        item.setGST(s2);
        item.setTotalCP(totalCp.getText().toString());
        item.setWholesalePrice(wholesalePrice.getText().toString());
        item.setRetailPrice(retailPrice.getText().toString());
        item.setpUid(pUid.getText().toString());
    }





    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

