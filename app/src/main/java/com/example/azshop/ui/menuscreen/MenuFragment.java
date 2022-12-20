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

        addWomenCatheg();
        menuAdapter = new MenuAdapter(requireContext(), arrayListmenudata);
        menuAdapter.setGender("Woman");
        rv_cclothes = view.findViewById(R.id.rv_clothes);
        rv_cclothes.setAdapter(menuAdapter);
        rv_cclothes.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        tv_woman = view.findViewById(R.id.tv_woman);
        tv_woman.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"ResourceAsColor", "NotifyDataSetChanged"})
            @Override
            public void onClick(View v) {
                tv_woman.setTextColor(getResources().getColor(R.color.black));
                tv_man.setTextColor(getResources().getColor(R.color.white));
                menuAdapter.setGender("Woman");
                addWomenCatheg();
                menuAdapter.notifyDataSetChanged();
            }
        });
        tv_man = view.findViewById(R.id.tv_man);
        tv_man.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"ResourceAsColor", "NotifyDataSetChanged"})
            @Override
            public void onClick(View v) {
                tv_man.setTextColor(getResources().getColor(R.color.black));
                tv_woman.setTextColor(getResources().getColor(R.color.white));
                menuAdapter.setGender("Man");
                addManCatheg();
                menuAdapter.notifyDataSetChanged();
            }
        });

    }
    private void addWomenCatheg(){
        arrayListmenudata.clear();
        arrayListmenudata.add(new MenuDataModel("Clothes", R.drawable.img_cloth));
        arrayListmenudata.add(new MenuDataModel("Shoes", R.drawable.img_cloth));
        arrayListmenudata.add(new MenuDataModel("Bags", R.drawable.img_cloth));
        arrayListmenudata.add(new MenuDataModel("Accessories", R.drawable.img_cloth));
    }
    private void addManCatheg(){
        arrayListmenudata.clear();
        arrayListmenudata.add(new MenuDataModel("Clothes", R.drawable.img_cloth));
        arrayListmenudata.add(new MenuDataModel("Shoes", R.drawable.img_cloth));
        arrayListmenudata.add(new MenuDataModel("Accessories", R.drawable.img_cloth));
    }
}