package com.example.azshop.ui.searchscreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.azshop.R;
import com.example.azshop.data.model.Articlemodel.ArticleDataModel;
import com.example.azshop.ui.menuscreen.ClotheAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class SearchFragment extends Fragment {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private RecyclerView rvs_clothes;
    private ArrayList<ArticleDataModel> articleDataModel = new ArrayList<ArticleDataModel>();
    private TextInputLayout tisearch;

    public SearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);

    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        view.findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //return the fragmenFManager for interacting with fragments associated with this fragment activity.
                getFragmentManager().popBackStack();


            }
        });
        //getting access to the XML Elements
        rvs_clothes = view.findViewById(R.id.rv_clothes);
        tisearch = view.findViewById(R.id.ti_search);
        //getting realtime database istance
        firebaseDatabase = FirebaseDatabase.getInstance();
//getting  the reference
        databaseReference = firebaseDatabase.getReference("Azshop").child("clothes");
        //event listener for clicked icon
        tisearch.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting the xml element value then converting it to string
                String search =((TextView) view.findViewById(R.id.et_search)).getText().toString();
                //verifying if the title is the same for filtring
                Query query = databaseReference.child("title").startAt(search);


                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                    //clear the arraylist
                        articleDataModel.clear();
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            //looping the real time database values  then we get the value
                            ArticleDataModel articleDataModelitem = postSnapshot.getValue(ArticleDataModel.class);
                            //adding the values into out arraylist
                            articleDataModel.add(articleDataModelitem);


                        }
                         if (articleDataModel.size() != 0) {
                             //checking if the arraylist contains data or not

//calling the adapter
                        ClotheAdapter wishListAdapter = new ClotheAdapter(getContext(), articleDataModel);
                        //setting the layout manager
                        rvs_clothes.setLayoutManager(new GridLayoutManager(getActivity(), 2));


//linking the dapter into our recycle view to display the result

                             rvs_clothes.setAdapter(wishListAdapter);


                        }else{
                             //displaying that there is no data in a toast
                             Toast.makeText(getContext(), "no Data", Toast.LENGTH_SHORT).show();
                         }



                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }
}