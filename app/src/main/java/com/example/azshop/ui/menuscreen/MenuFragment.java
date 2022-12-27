package com.example.azshop.ui.menuscreen;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.azshop.R;
import com.example.azshop.data.model.MenuDataModel;

import java.util.ArrayList;

public class MenuFragment extends Fragment {

    private RecyclerView rv_cclothes;
    ArrayList<MenuDataModel> arrayListmenudata = new ArrayList<>();
    MenuAdapter menuAdapter;
    TextView tv_woman;
    TextView tv_man;
    String gender = "Man";

    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //calling the methos that we made to affect data to out model then we put it later in the recycle view
        addWomenCatheg();
        //calling the adapter
        menuAdapter = new MenuAdapter(requireContext(), arrayListmenudata);
        //setting adapter gender
        menuAdapter.setGender("Woman");
        //pointing on XML elements
        rv_cclothes = view.findViewById(R.id.rv_clothes);
        //linking the recycle view to the adapter
        rv_cclothes.setAdapter(menuAdapter);
        //setting the layout manager
        rv_cclothes.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        tv_woman = view.findViewById(R.id.tv_woman);

        tv_woman.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"ResourceAsColor", "NotifyDataSetChanged"})
            @Override
            public void onClick(View v) {
              // setting text color when button clicked
                tv_woman.setTextColor(getResources().getColor(R.color.black));
                tv_man.setTextColor(getResources().getColor(R.color.white));
                //setting gender for the adapter
                menuAdapter.setGender("Woman");
                //calling the method if the button clicked so we change the recycle view value
                addWomenCatheg();
                //checking if the  adapter has changed the value
                menuAdapter.notifyDataSetChanged();
            }
        });
        tv_man = view.findViewById(R.id.tv_man);
        tv_man.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"ResourceAsColor", "NotifyDataSetChanged"})
            @Override
            public void onClick(View v) {
                // setting text color when button clicked

                tv_man.setTextColor(getResources().getColor(R.color.black));
                tv_woman.setTextColor(getResources().getColor(R.color.white));
                //setting gender for the adapter

                menuAdapter.setGender("Man");
                //calling the method if the button clicked so we change the recycle view value
                addManCatheg();
                //checking if the  adapter has changed the value

                menuAdapter.notifyDataSetChanged();
            }
        });

    }
    private void addWomenCatheg(){
        //clear arraylist
        arrayListmenudata.clear();
        //adding values to the arraylist for the recycle view later
        arrayListmenudata.add(new MenuDataModel("Clothes", R.drawable.img_cloth));
        arrayListmenudata.add(new MenuDataModel("Shoes", R.drawable.womenshoes));
        arrayListmenudata.add(new MenuDataModel("Bags", R.drawable.bag));
        arrayListmenudata.add(new MenuDataModel("Accessories", R.drawable.accwomen));
    }
    private void addManCatheg(){
        //clear the arraylist
        arrayListmenudata.clear();
        //adding values to the arraylist for the recycle view later
        arrayListmenudata.add(new MenuDataModel("Clothes", R.drawable.clothes));
        arrayListmenudata.add(new MenuDataModel("Shoes", R.drawable.shoesman));
        arrayListmenudata.add(new MenuDataModel("Accessories", R.drawable.accman));
    }
}