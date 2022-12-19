package com.example.azshop.ui.menuscreen;

import static com.example.azshop.utils.Utils.setCurrentFragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.azshop.MainActivity;
import com.example.azshop.R;
import com.example.azshop.data.model.CartArticleDataModel;
import com.example.azshop.data.model.MenuDataModel;
import com.example.azshop.ui.homescreen.HomeFragment;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private List<MenuDataModel> data;
    private  Context context;
    private String gender;

    // data is passed into the constructor
    public MenuAdapter(Context context, List<MenuDataModel> data) {
        super();
        this.data = data;
        this.context = context;


    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    // inflates the row layout from xml when needed
    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menuitemlayout, parent, false);
        return new MenuAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(MenuAdapter.ViewHolder holder, int position) {
        MenuDataModel myItem = data.get(position);
        holder.itemTitle.setText(myItem.name);
        holder.itemImage.setImageDrawable(context.getResources().getDrawable(myItem.res));
        Fragment clothefragment = new ClothesFragment();
        Bundle args = new Bundle();
        args.putString("gender", gender);
        args.putString("type", getItem(position).getName());
        clothefragment.setArguments(args);
        holder.item.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((FragmentActivity) v.getContext()).getFragmentManager().beginTransaction()
                                .replace(R.id.flFragment,clothefragment).addToBackStack(clothefragment.getClass().getName())
                                .commit();
                    }
                }
        );

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return data.size();
    }


    // stores and recycles views as they are scrolled off screen
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemTitle;
        View item;

        ViewHolder(View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.tv_item_name);
            itemImage = itemView.findViewById(R.id.img_article);
            item = itemView;

        }


    }

    // convenience method for getting data at click position
    MenuDataModel getItem(int id) {
        return data.get(id);
    }

}
