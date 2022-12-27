package com.example.azshop.ui.bashetscreen;

import static android.content.Context.MODE_PRIVATE;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.azshop.R;
import com.example.azshop.data.model.CartArticleDataModel;
import com.example.azshop.ui.whishlistscreen.WishListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class BasketFragment extends Fragment {

    // declaring attributes
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Button btn;
    private ArrayList<CartArticleDataModel> articleDataModel = new ArrayList<CartArticleDataModel>();
    private RecyclerView rv_fav;
    private float totalitemprice=0;
    private float totalprice=0;

    public BasketFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_basket, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
            //declaring firebase realtime database instance
        firebaseDatabase = FirebaseDatabase.getInstance();
        //decalring the refence where where we have the data
        databaseReference = firebaseDatabase.getReference("Azshop").child("Cart");
        //pointing on the XML values
        btn = view.findViewById(R.id.btn_checkout);
        rv_fav = view.findViewById(R.id.rv_cart);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //declaring dialog
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                //setting dialog title
                builder1.setTitle("Thanks For The Trust");
                //setting dialog message that will appear to the user
                builder1.setMessage("Your Product will be delivered in 48h You can pay there");
                //sets whether the dialog is cancelable or not
                builder1.setCancelable(true);
                //seeting dialog possitive display so when he click it the dialog will go away
                builder1.setPositiveButton(
                        "Ok",
                        new DialogInterface.OnClickListener() {
                            //if we click it it will go away
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                //creating the dialog
                AlertDialog alert11 = builder1.create();
                // display it for user
                alert11.show();
            }
        });
        // making instance of the adapter
        WishListAdapter wishListAdapter = new WishListAdapter(requireContext(), articleDataModel);
        // declaring our sharedpreferences to store some data presistently

        SharedPreferences sh =  this.getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        //query for verifiying if the user id is it or not
        Query query = databaseReference.orderByChild("userId").equalTo(sh.getString("userId", ""));
        //making event lister for the query
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    //looping from the realtime database information of the article  needed
                    CartArticleDataModel articleDataModelitem = postSnapshot.getValue(CartArticleDataModel.class);
                    //operation for the total of the price
                    totalitemprice += articleDataModelitem.price;
                    //adding it to the array list so we can use it later for the recycle view
                    articleDataModel.add(articleDataModelitem);
                }
                //adding taxes to the total
                totalprice = totalitemprice+4;

                if (articleDataModel.size() != 0) {
                    //checking if the arraylist if empty or not
                    //else we put value in the adapter then we put it in the recycle view
                    view.findViewById(R.id.cl_nodata).setVisibility(View.GONE);
                    view.findViewById(R.id.cl_data).setVisibility(View.VISIBLE);
                    ((TextView)view.findViewById(R.id.tv_total)).setText(totalitemprice + "$");
                    ((TextView)view.findViewById(R.id.tv_Totalsum)).setText(totalprice + "$");
                    //check if the adapter data changed or not
                    wishListAdapter.notifyDataSetChanged();
                    //linking the adapter to the recycle view
                    rv_fav.setAdapter(wishListAdapter);
                    //setting the layout display
                    rv_fav.setLayoutManager(new LinearLayoutManager(getActivity(),  LinearLayoutManager.VERTICAL, false));
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}