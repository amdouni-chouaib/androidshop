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
import java.util.Locale;


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
                getFragmentManager().popBackStack();


            }
        });

        rvs_clothes = view.findViewById(R.id.rv_clothes);
        tisearch = view.findViewById(R.id.ti_search);

        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference("Azshop").child("clothes");
        tisearch.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search =((TextView) view.findViewById(R.id.et_search)).getText().toString();
                Query query = databaseReference.child("title").equalTo(search).startAt(search);


                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        articleDataModel.clear();
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            ArticleDataModel articleDataModelitem = postSnapshot.getValue(ArticleDataModel.class);
                            articleDataModel.add(articleDataModelitem);
                            // here you can access to name property like university.name

                        }
                         if (articleDataModel.size() != 0) {
                             Toast.makeText(getContext(), articleDataModel.size()+"!!", Toast.LENGTH_SHORT).show();

                        ClotheAdapter wishListAdapter = new ClotheAdapter(getContext(), articleDataModel);

                        rvs_clothes.setLayoutManager(new GridLayoutManager(getActivity(), 2));



                             rvs_clothes.setAdapter(wishListAdapter);


                        }else{
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