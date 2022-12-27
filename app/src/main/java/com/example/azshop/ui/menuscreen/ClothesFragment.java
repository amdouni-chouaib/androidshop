package com.example.azshop.ui.menuscreen;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.azshop.R;
import com.example.azshop.data.model.Articlemodel.ArticleDataModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ClothesFragment extends Fragment {
    private String type;
    private String gender;
    private TextView tvtype;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private RecyclerView rv_clothes;
    private ArrayList<ArticleDataModel> articleDataModel = new ArrayList<ArticleDataModel>();
    public ClothesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_clothes, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // getting bundle values
        Bundle arguments = getArguments();
        type = arguments.getString("type");
        gender = arguments.getString("gender");
//pointing into XML elements
        rv_clothes = view.findViewById(R.id.rv_clothes);
        tvtype = view.findViewById(R.id.tv_type);
        //setting values on the XML element
        tvtype.setText(type);
//getting back on the previous activity
        view.findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
//declaring realtimedataase instance
        firebaseDatabase = FirebaseDatabase.getInstance();
        //declaring the reference
        databaseReference = firebaseDatabase.getReference("Azshop").child("clothes");
        //checking the gender male or female
        Query query = databaseReference.orderByChild("gender").equalTo(gender);
        //event listener
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //clearing the arraylist
                articleDataModel.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    //looping the data in the realtime database then putting it to our object
                    ArticleDataModel articleDataModelitem = postSnapshot.getValue(ArticleDataModel.class);
                    // checking the type then we add it to the list if true
                    if(articleDataModelitem.type.equals(type))

                         articleDataModel.add(articleDataModelitem);
                }

                if (articleDataModel.size() != 0) {
                    // if the list contains data then we call the adapter link it with
                    // the recycle view then set the layout display
                    ClotheAdapter wishListAdapter = new ClotheAdapter(getContext(),articleDataModel);
                    rv_clothes.setAdapter(wishListAdapter);
                    rv_clothes.setLayoutManager(new LinearLayoutManager(getActivity(),  LinearLayoutManager.VERTICAL, false));
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}