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

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Azshop").child("Cart");
        btn = view.findViewById(R.id.btn_checkout);
        rv_fav = view.findViewById(R.id.rv_cart);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setTitle("Thanks For The Trust");
                builder1.setMessage("Your Product will be delivered in 48h You can pay there");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });
        WishListAdapter wishListAdapter = new WishListAdapter(requireContext(), articleDataModel);

        SharedPreferences sh =  this.getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        Query query = databaseReference.orderByChild("userId").equalTo(sh.getString("userId", ""));

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    CartArticleDataModel articleDataModelitem = postSnapshot.getValue(CartArticleDataModel.class);
                    totalitemprice += articleDataModelitem.price;
                    articleDataModel.add(articleDataModelitem);
                }
                totalprice = totalitemprice+4;

                if (articleDataModel.size() != 0) {
                    view.findViewById(R.id.cl_nodata).setVisibility(View.GONE);
                    view.findViewById(R.id.cl_data).setVisibility(View.VISIBLE);
                    ((TextView)view.findViewById(R.id.tv_total)).setText(totalitemprice + "$");
                    ((TextView)view.findViewById(R.id.tv_Totalsum)).setText(totalprice + "$");
                    wishListAdapter.notifyDataSetChanged();
                    rv_fav.setAdapter(wishListAdapter);
                    rv_fav.setLayoutManager(new LinearLayoutManager(getActivity(),  LinearLayoutManager.VERTICAL, false));
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}