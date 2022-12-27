package com.example.azshop.ui.sellscreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.azshop.R;
import com.example.azshop.data.model.Articlemodel.ArticleDataModel;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SellActivity extends AppCompatActivity {
//declaring attibutes
    private ArticleDataModel articleDataModel;
    private EditText ettitle;
    private EditText etdescription;
    private EditText etgender;
    private EditText ettype;
    private EditText etprice;
    private ImageView imgItem;
    private Button btnConfirm,btnCancel;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Uri mImageUri = null;
    private StorageReference mStorage;
    private Spinner spinnergender;
    private Spinner spinnertype;
    private SpinnerAdapter spinnertypeAdapter;
    private SpinnerAdapter spinnergenderAdapter;
    private List<String> itemsList = new ArrayList<>();
    private List<String> itemsGender = new ArrayList<>();
    String gender = "";
    String type = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        //getting access to the XML Values
        ettitle = findViewById(R.id.et_title);
        etdescription = findViewById(R.id.et_description);
        etprice = findViewById(R.id.et_price);
        btnConfirm = findViewById(R.id.btn_confirm);
        imgItem = findViewById(R.id.img_article_details);
        btnCancel = findViewById(R.id.btn_cancel);

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // all inputs get empty again when we click cancel
                    ettitle.setText("");
                    etprice.setText("");
                    etdescription.setText("");
                    imgItem.setImageURI(Uri.parse(""));
                }
            });
            //adding values to array list for the spinner later
        itemsList.add("Clothes");
        itemsList.add("Shoes");
        itemsList.add("Bags");
        itemsList.add("Accessories");
        //calling the adapter of the pinner the we give it the arraylist as a value
        spinnertypeAdapter = new SpinnerAdapter(this, itemsList);
        //getting access to the XML element
        spinnertype = findViewById(R.id.spinnerTypeProduct);
        //setting the adapter to the spinner
        spinnertype.setAdapter(spinnertypeAdapter);

        spinnertype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                type = spinnertypeAdapter.getItem(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
            //adding gender value into the arraylist
        itemsGender.add("Woman");
        itemsGender.add("Man");
        //calling the spinner gender adapter
        spinnergenderAdapter = new SpinnerAdapter(this, itemsGender);
        //getting xml element access
        spinnergender = findViewById(R.id.spinnergender);
        //linking adapter to the spinner
        spinnergender.setAdapter(spinnergenderAdapter);

        spinnergender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = spinnergenderAdapter.getItem(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //getting realtime database reference
        firebaseDatabase = FirebaseDatabase.getInstance();
        // on below line creating our database reference.
        databaseReference = firebaseDatabase.getReference("Azshop").child("clothes");
        mStorage = FirebaseStorage.getInstance().getReference().child("images");
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        imgItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if we click the pickture he will give you the right to make a picture then crop it if u want
                ImagePicker.with(SellActivity.this)
                        .crop().start();
            }
        });

            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        //getting key for the inserted element in the database
                    String id = databaseReference.push().getKey();
                    //getting the file path in the Firebase Storage
                    StorageReference filepath = mStorage.child("Blog_Images").child(mImageUri.getLastPathSegment());

                    filepath.putFile(mImageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                //if there is a problem with the picture path in the firebase storage it will throw exception
                                throw task.getException();
                            }

                            // Continue with the task to get the download URL
                            return filepath.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                //getting the result uri witch is picture
                                Uri downloadUri = task.getResult();
                                //adding it to our object
                                articleDataModel = new ArticleDataModel(
                                        id,
                                        downloadUri.toString(),
                                        ettitle.getText().toString(),
                                        etdescription.getText().toString(),
                                        type,
                                        Float.parseFloat(etprice.getText().toString()),
                                        gender,
                                        Calendar.getInstance().getTime().toString(),
                                        sh.getString("userId", "")
                                );
                                //pushing it to the realtime database
                                databaseReference.child(id).setValue(articleDataModel);
                                //displaying toast for success added article
                                Toast.makeText(SellActivity.this, "Article added successfully ", Toast.LENGTH_LONG).show();


                                }
                        }
                    });
                }
            });
        }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImagePicker.REQUEST_CODE) {
            //if there is a bidirectional intent then we get the picture uri then we set the picture
            Uri uri = data.getData();
            imgItem.setImageURI(uri);
            mImageUri = uri;

        }
    }
}