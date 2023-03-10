package com.example.azshop.ui.whishlistscreen;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.azshop.R;
import com.example.azshop.data.model.Articlemodel.ArticleDataModel;
import com.example.azshop.data.model.CartArticleDataModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class WhishListFragment extends Fragment {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<CartArticleDataModel> articleDataModel = new ArrayList<CartArticleDataModel>();
    private RecyclerView rv_fav;

    public WhishListFragment() {
        // Required empty public constructor
    }

    public static WhishListFragment newInstance(String param1, String param2) {
        WhishListFragment fragment = new WhishListFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_whish_list, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
                //getting realtime database Instance
        firebaseDatabase = FirebaseDatabase.getInstance();
        //getting database reference
        databaseReference = firebaseDatabase.getReference("Azshop").child("Fav");
            //access XML element
        rv_fav = view.findViewById(R.id.rv_favdate);
        //calling adapter
        WishListAdapter wishListAdapter = new WishListAdapter(getContext(),articleDataModel);

        SharedPreferences sh =  this.getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        // checking if the ID  is the same for logged in user
        Query query = databaseReference.orderByChild("userId").equalTo(sh.getString("userId", ""));

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    //looping the values the geeting all values
                    CartArticleDataModel articleDataModelitem = postSnapshot.getValue(CartArticleDataModel.class);
                    //aading values to the arraylist
                    articleDataModel.add(articleDataModelitem);
                }

                if (articleDataModel.size() != 0) {
                    //check if the arraylist is not empty

                    view.findViewById(R.id.cl_nodata).setVisibility(View.GONE);
                    rv_fav.setVisibility(View.VISIBLE);
                    wishListAdapter.notifyDataSetChanged();
                    //checking if there is any changes in the adapter  the linking it with the recycle view
                    rv_fav.setAdapter(wishListAdapter);
                            //setting the layout for the recycle view
                    rv_fav.setLayoutManager(new LinearLayoutManager(getActivity(),  LinearLayoutManager.VERTICAL, false));
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}