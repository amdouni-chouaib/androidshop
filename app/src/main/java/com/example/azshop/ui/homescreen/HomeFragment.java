package com.example.azshop.ui.homescreen;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.azshop.R;
import com.example.azshop.data.model.Articlemodel.ArticleDataModel;
import com.example.azshop.ui.authscreens.UserProfileActivity;
import com.example.azshop.ui.authscreens.WelcomeScreen;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private GridView grItems;
    private ImageView imgUser;
    private HomeClothesAdapter homeClothesAdapter;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<ArticleDataModel> articleDataModel = new ArrayList<ArticleDataModel>();

    public HomeFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment62
        return inflater.inflate(R.layout.activity_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //getting access to the XML element
        grItems = view.findViewById(R.id.idGVcourses);
        //declating Realtime database instance
        firebaseDatabase = FirebaseDatabase.getInstance();
        // on below line creating our database reference.
        databaseReference = firebaseDatabase.getReference("Azshop").child("clothes");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //getting realtime database values
                ArticleDataModel articleDataModelitem = snapshot.getValue(ArticleDataModel.class);
                //adding the values into arraylist
                articleDataModel.add(articleDataModelitem);
                //making instance of the adapter
                homeClothesAdapter = new HomeClothesAdapter(requireContext(), articleDataModel);
                //linking the adapter to the GridView
                grItems.setAdapter(homeClothesAdapter);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        imgUser = view.findViewById(R.id.img_user);
        SharedPreferences sh =  this.getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        if (sh.getString("userId", "").equals("")) {
           //if the ID of the user is equals to the logged in user then
            //he will navigate to the welcome activity
            imgUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(requireContext(), WelcomeScreen.class));
                }
            });
        } else
            //else he will navigate to the user profile activity
            imgUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(requireContext(), UserProfileActivity.class));
                }
            });
    }


}