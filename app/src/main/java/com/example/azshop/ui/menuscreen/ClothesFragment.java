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

        Bundle arguments = getArguments();
        type = arguments.getString("type");
        gender = arguments.getString("gender");

        rv_clothes = view.findViewById(R.id.rv_clothes);
        tvtype = view.findViewById(R.id.tv_type);
        tvtype.setText(type);

        view.findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Azshop").child("clothes");
        Query query = databaseReference.orderByChild("gender").equalTo(gender);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                articleDataModel.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    ArticleDataModel articleDataModelitem = postSnapshot.getValue(ArticleDataModel.class);
                    if(articleDataModelitem.type.equals(type))
                         articleDataModel.add(articleDataModelitem);
                }
                if (articleDataModel.size() != 0) {
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