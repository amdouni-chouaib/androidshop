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

        etemail = findViewById(R.id.et_mail);

        etfname = findViewById(R.id.et_fName);

        etlname = findViewById(R.id.et_lName);

        etphone = findViewById(R.id.et_phone);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Azshop").child("user");
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        Query query = databaseReference.orderByChild("id").equalTo(sh.getString("userId", ""));

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    userModellist = postSnapshot.getValue(userModel.class);
                    if (userModellist != null) {
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
        btnadarticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserProfileActivity.this, SellActivity.class));
            }
        });

        btnlogout = findViewById(R.id.btn_logout);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(UserProfileActivity.this, WelcomeScreen.class));

            }
        });

        btneditprofile = findViewById(R.id.btn_edit_profile);
        btneditprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etemail.getText().toString();
                fname = etfname.getText().toString();
                lname = etlname.getText().toString();
                phone = etphone.getText().toString();
                userModel userModel = new userModel(
                        sh.getString("userId", ""),
                        fname,
                        lname,
                        email,
                        phone

                );
                databaseReference.child(sh.getString("userId", "")).setValue(userModel);
                Toast.makeText(UserProfileActivity.this, "Success Update User",
                        Toast.LENGTH_SHORT).show();

            }
        });

        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}