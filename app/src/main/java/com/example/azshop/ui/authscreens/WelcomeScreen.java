package com.example.azshop.ui.authscreens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.azshop.MainActivity;
import com.example.azshop.R;
import com.example.azshop.utils.Utils;

public class WelcomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        //pointing on the inputs and buttons action
        Button btnlogin = findViewById(R.id.btn_login);
        //moving to the activity login
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeScreen.this, LoginActivity.class));

            }
        });
        Button btnguest = findViewById(R.id.btn_guest);

        btnguest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //starting the home activity
                startActivity(new Intent(WelcomeScreen.this, MainActivity.class));
                // declaring our sharedpreferences to store some data presistently

                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                //sending  the persistance value
                myEdit.putString("userId", "");
                // writes its data to persistent storage immediately

                myEdit.commit();

            }
        });

        Button btnsignup = findViewById(R.id.btn_signup);
        //starting signup activity
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeScreen.this, SignUpActivity.class));

            }
        });
    }
}
