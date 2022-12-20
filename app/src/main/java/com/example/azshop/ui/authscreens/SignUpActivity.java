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
import com.example.azshop.data.model.userModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignUpActivity extends AppCompatActivity {
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

        mAuth = FirebaseAuth.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Azshop").child("user");

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();


        etemail = findViewById(R.id.et_mail);

        etfname = findViewById(R.id.et_fName);

        etlname = findViewById(R.id.et_lName);

        etphone = findViewById(R.id.et_phone);

        etconfirmpassword = findViewById(R.id.et_psw2);

        etpassword = findViewById(R.id.et_psw1);


        btnsignup = findViewById(R.id.btn_signup);
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = etemail.getText().toString();
                fname = etfname.getText().toString();
                lname = etlname.getText().toString();
                phone = etphone.getText().toString();
                confirmpassword = etconfirmpassword.getText().toString();
                password = etpassword.getText().toString();

                if (!etemail.getText().toString().isEmpty() && !etpassword.getText().toString().isEmpty())
                    mAuth.createUserWithEmailAndPassword(etemail.getText().toString(), etpassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                                myEdit.putString("userId", user.getUid());
                                myEdit.commit();
                                userModel userModel = new userModel(
                                        user.getUid(),
                                        fname,
                                        lname,
                                        email,
                                        phone

                                );
                                databaseReference.child(user.getUid()).setValue(userModel);
                                Toast.makeText(SignUpActivity.this, "success add user", Toast.LENGTH_LONG).show();


                            } else {
                                Toast.makeText(SignUpActivity.this, "Sign Up failed.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });
            }
        });

        findViewById(R.id.tv_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));

            }
        });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null)
            startActivity(new Intent(SignUpActivity.this, MainActivity.class));

    }
}