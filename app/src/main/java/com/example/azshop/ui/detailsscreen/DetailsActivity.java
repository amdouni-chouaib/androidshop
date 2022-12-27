package com.example.azshop.ui.detailsscreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.azshop.R;
import com.example.azshop.data.model.CartArticleDataModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailsActivity extends AppCompatActivity {
    //declaring attributes
    private String id;
    private String imagPath;
    private String title;
    private String description;
    private String type;
    private String price;
    private String gender;
    private String size;
    private RadioGroup btnradiogroup_size;
    private ImageView imgArticle;
    private ImageView imgBack;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReferenceCart;
    private DatabaseReference databaseReferenceFav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        //getting intent
        Intent intent = getIntent();
        //getting the extra from the intent
        id = intent.getStringExtra("id");
        imagPath = intent.getStringExtra("image");
        price = intent.getStringExtra("price");
        type = intent.getStringExtra("type");
        gender = intent.getStringExtra("gender");
        description = intent.getStringExtra("description");
        title = intent.getStringExtra("title");
        //declaring realtime database instance
        firebaseDatabase = FirebaseDatabase.getInstance();
        // on below line creating our database reference.
        databaseReferenceCart = firebaseDatabase.getReference("Azshop").child("Cart");
        databaseReferenceFav = firebaseDatabase.getReference("Azshop").child("Fav");

        // pointing on the XML elements
        imgArticle = findViewById(R.id.img_article_details);
        imgBack = findViewById(R.id.btn_back);
        ((TextView) findViewById(R.id.tv_description)).setText(description);
        btnradiogroup_size = findViewById(R.id.btngrp_size);
            //if we click the picture we get back to the precious activity
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        // pointing on the XML elements

        ((TextView) findViewById(R.id.item_name)).setText(title);
        ((TextView) findViewById(R.id.item_price)).setText(price + "$");
        Glide.with(this).load(imagPath).into(imgArticle);
// declaring our sharedpreferences to store some data presistently

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        //checking if the is an User ID
        if (!sh.getString("userId", "").equals("")) {
            findViewById(R.id.btn_addtocart).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //checking the selected radio button
                    int selectedId = btnradiogroup_size.getCheckedRadioButtonId();
                    //getting the radio button value
                    size = ((RadioButton) findViewById(selectedId)).getText().toString();
                        //adding the values to out instance
                    CartArticleDataModel cartarticleDateModel = new CartArticleDataModel(
                            id,
                            imagPath,
                            title,
                            description,
                            type,
                            Float.parseFloat(price),
                            gender,
                            size
                            ,
                            sh.getString("userId", "")

                    );
                    //save it to the realtime database
                    databaseReferenceCart.child(id).setValue(cartarticleDateModel);
                    //displaying toast that the product added to backet
                Toast.makeText(getApplicationContext(),"Product Added To Basket",Toast.LENGTH_LONG).show();
                }
            });

            findViewById(R.id.btn_addtofav).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //adding the values to out instance
                    CartArticleDataModel favarticleDateModel = new CartArticleDataModel(
                            id,
                            imagPath,
                            title,
                            description,
                            type,
                            Float.parseFloat(price),
                            gender,
                            "X",
                            sh.getString("userId", "")
                    );
                    //adding value to the realtime database
                    databaseReferenceFav.child(id).setValue(favarticleDateModel);
                    //displaying toast that the product added to favorit
                    Toast.makeText(DetailsActivity.this, "Item added to Favorit", Toast.LENGTH_LONG).show();

                }
            });

        }
    }
}