package com.example.azshop.ui.authscreens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.azshop.R;
import com.example.azshop.data.model.userModel;
import com.example.azshop.ui.sellscreen.SellActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class UserProfileActivity extends AppCompatActivity {
    //declaring our attricutes  so we can get acceess to the schema

    private Button btnadarticle;
    private Button btnlogout;
    private Button btneditprofile;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private userModel userModellist;
    private EditText etemail;
    private EditText etphone;
    private EditText etfname;
    private EditText etlname;
    private Button btnsignup;
    private String email;
    private String phone;
    private String fname;
    private String lname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_profile);
//getting access to values in the inputs
        etemail = findViewById(R.id.et_mail);

        etfname = findViewById(R.id.et_fName);

        etlname = findViewById(R.id.et_lName);

        etphone = findViewById(R.id.et_phone);
//declaring firebase authentication instance
        mAuth = FirebaseAuth.getInstance();
        //decalring real time database instance
        firebaseDatabase = FirebaseDatabase.getInstance();
        //referencing the path to save data into
        databaseReference = firebaseDatabase.getReference("Azshop").child("user");
        // declaring our sharedpreferences to store some data presistently

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        // checking if te id of the user is equal so we can get the information
        Query query = databaseReference.orderByChild("id").equalTo(sh.getString("userId", ""));
    //event listener  for getting the informations
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //looping the realtime database  the getting the values
                    userModellist = postSnapshot.getValue(userModel.class);
                    if (userModellist != null) {
                        //if there is values then we will change inputes content
                        etemail.setText(userModellist.email);
                        etfname.setText(userModellist.fname);
                        etlname.setText(userModellist.lname);
                        etphone.setText(userModellist.phone);
                    }
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnadarticle = findViewById(R.id.btn_addarticle);
        //starting activity sell if we click the button
        btnadarticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserProfileActivity.this, SellActivity.class));
            }
        });

        btnlogout = findViewById(R.id.btn_logout);
        //starting activity welcome if we click the button and we logout

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(UserProfileActivity.this, WelcomeScreen.class));

            }
        });

        btneditprofile = findViewById(R.id.btn_edit_profile);
        //updating values in the database if we click on the button

        btneditprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting input values into string
                email = etemail.getText().toString();
                fname = etfname.getText().toString();
                lname = etlname.getText().toString();
                phone = etphone.getText().toString();
                //adding values to our object
                userModel userModel = new userModel(
                        sh.getString("userId", ""),
                        fname,
                        lname,
                        email,
                        phone

                );
                //updating values in the real time database
                databaseReference.child(sh.getString("userId", "")).setValue(userModel);
                //suuceess toast will appear
                Toast.makeText(UserProfileActivity.this, "Success Update User",
                        Toast.LENGTH_SHORT).show();

            }
        });

        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            //getting back to the previous activity
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}