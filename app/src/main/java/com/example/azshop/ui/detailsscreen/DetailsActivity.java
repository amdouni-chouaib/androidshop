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
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        imagPath = intent.getStringExtra("image");
        price = intent.getStringExtra("price");
        type = intent.getStringExtra("type");
        gender = intent.getStringExtra("gender");
        description = intent.getStringExtra("description");
        title = intent.getStringExtra("title");

        firebaseDatabase = FirebaseDatabase.getInstance();
        // on below line creating our database reference.
        databaseReferenceCart = firebaseDatabase.getReference("Azshop").child("Cart");
        databaseReferenceFav = firebaseDatabase.getReference("Azshop").child("Fav");


        imgArticle = findViewById(R.id.img_article_details);
        imgBack = findViewById(R.id.btn_back);
        ((TextView) findViewById(R.id.tv_description)).setText(description);
        btnradiogroup_size = findViewById(R.id.btngrp_size);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ((TextView) findViewById(R.id.item_name)).setText(title);
        ((TextView) findViewById(R.id.item_price)).setText(price + "$");
        Glide.with(this).load(imagPath).into(imgArticle);

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        if (!sh.getString("userId", "").equals("")) {
            findViewById(R.id.btn_addtocart).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int selectedId = btnradiogroup_size.getCheckedRadioButtonId();
                    size = ((RadioButton) findViewById(selectedId)).getText().toString();

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
                    databaseReferenceCart.child(id).setValue(cartarticleDateModel);

                }
            });

            findViewById(R.id.btn_addtofav).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
                    databaseReferenceFav.child(id).setValue(favarticleDateModel);
                    Toast.makeText(DetailsActivity.this, "Item added to Fav", Toast.LENGTH_LONG).show();

                }
            });

        }
    }
}