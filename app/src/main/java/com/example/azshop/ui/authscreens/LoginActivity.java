package com.example.azshop.ui.authscreens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.azshop.MainActivity;
import com.example.azshop.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {
        //declaring our attricutes  so we can get acceess to the schema
    private EditText etpassword ;
    private EditText etemail ;
    private Button btnlogin;
    private TextView tvlogin;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);
        //declaring our firebase  authentication instance
        mAuth = FirebaseAuth.getInstance();
// declaring our sharedpreferences to store some data presistently
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
//getting access to our attributes in the xml file
        etemail = findViewById(R.id.et_mail);

        etpassword = findViewById(R.id.et_psw1);

        btnlogin = findViewById(R.id.btn_login);
        //declaring event listener
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // we will check if the email and password that we passed exist or no
                mAuth.signInWithEmailAndPassword( etemail.getText().toString(), etpassword.getText().toString()).addOnCompleteListener( new OnCompleteListener<AuthResult>(){

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            // if the email and password are true we will store the current user signed in
                            FirebaseUser user = mAuth.getCurrentUser();
                            //this method will get the current user then it will go to the home interface
                            updateUI(user);
                            //we will send the Unique id of the signed in user in the intent
                            myEdit.putString("userId", user.getUid());
                           // writes its data to persistent storage immediately
                            myEdit.commit();

                        }
                        else{
                            //else  a toast will appear with a failed message
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //u will stay ath the same interface
                            updateUI(null);
                        }
                    }
                });
            }
        });

        tvlogin = findViewById(R.id.tv_login);
        //if u click it u will go to the activity signup
        tvlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        // we will get here the current user when the activity is started
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //then we go to the home again if he didn't signup
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        //check if the current firebase user exist then go to the home activity
        if(currentUser != null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));

        }
    }
}