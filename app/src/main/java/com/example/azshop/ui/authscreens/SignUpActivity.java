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

import com.example.azshop.MainActivity;
import com.example.azshop.R;
import com.example.azshop.data.model.Articlemodel.ArticleDataModel;
import com.example.azshop.data.model.userModel;
import com.example.azshop.ui.sellscreen.SellActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;


public class SignUpActivity extends AppCompatActivity {
    //declaring our attricutes  so we can get acceess to the schema

    private EditText etpassword;
    private EditText etemail;
    private EditText etconfirmpassword;
    private EditText etphone;
    private EditText etfname;
    private EditText etlname;
    private Button btnsignup;
    private String email;
    private String password;
    private String confirmpassword;
    private String phone;
    private String fname;
    private String lname;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_signup);
        //declaring our firebase  authentication instance

        mAuth = FirebaseAuth.getInstance();
        //declaring our realtime database instance

        firebaseDatabase = FirebaseDatabase.getInstance();
        //decaring the reference where we can store our data later
        databaseReference = firebaseDatabase.getReference("Azshop").child("user");
// declaring our sharedpreferences to store some data presistently

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

//getting access to our attributes in the xml file

        etemail = findViewById(R.id.et_mail);

        etfname = findViewById(R.id.et_fName);

        etlname = findViewById(R.id.et_lName);

        etphone = findViewById(R.id.et_phone);

        etconfirmpassword = findViewById(R.id.et_psw2);

        etpassword = findViewById(R.id.et_psw1);


        btnsignup = findViewById(R.id.btn_signup);
        //event listner on the signup button


        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //converting variables into string
                email = etemail.getText().toString();
                fname = etfname.getText().toString();
                lname = etlname.getText().toString();
                phone = etphone.getText().toString();
                confirmpassword = etconfirmpassword.getText().toString();
                password = etpassword.getText().toString();
//checking if the inputs empty or not
                if (!etemail.getText().toString().isEmpty() && !etpassword.getText().toString().isEmpty())
                    //creating user into firebase authentication
                    mAuth.createUserWithEmailAndPassword(etemail.getText().toString(), etpassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //getting current firebase authentication  user
                                FirebaseUser user = mAuth.getCurrentUser();
                                //this method will get the current user then it will go to the home interface
                                updateUI(user);
                                //getting generated key
                                String id = databaseReference.push().getKey();
                                //we will send the Unique id of the signed in user in the intent
                                myEdit.putString("userId", id);
                                // writes its data to persistent storage immediately
                                myEdit.commit();
                                //inserting data into out object
                                userModel userModel = new userModel(
                                        id,
                                        fname,
                                        lname,
                                        email,
                                        phone

                                );
                                //save values into realtime database
                                databaseReference.child(id).setValue(userModel);
                                // success toast for saving data
                                Toast.makeText(SignUpActivity.this, "success add user", Toast.LENGTH_LONG).show();


                            } else {
                                //failer toast
                                Toast.makeText(SignUpActivity.this, "Sign Up failed.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });
            }
        });
            //if we click the button we will navigate to the login activity
        findViewById(R.id.tv_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));

            }
        });
    }

    private void updateUI(FirebaseUser user) {
        // moving up to the home activity
        startActivity(new Intent(SignUpActivity.this, MainActivity.class));

    }
}