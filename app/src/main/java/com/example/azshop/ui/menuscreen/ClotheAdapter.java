package com.example.azshop.ui.menuscreen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.azshop.R;
import com.example.azshop.data.model.Articlemodel.ArticleDataModel;

import java.util.ArrayList;
import java.util.List;

public class ClotheAdapter extends RecyclerView.Adapter<ClotheAdapter.ViewHolder> {

    private ArrayList<ArticleDataModel> data = new ArrayList<ArticleDataModel>();
    private Context context;

    // data is passed into the constructor
    public ClotheAdapter(Context context, ArrayList<ArticleDataModel> data) {
        super();
        this.data = data;
        this.context = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ClotheAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishitemlayout, parent, false);
        return new ClotheAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ClotheAdapter.ViewHolder holder, int position) {
        ArticleDataModel myItem = data.get(position);
        holder.itemTitle.setText(myItem.title);
        holder.itemPrice.setText(String.valueOf(myItem.price));
        Glide.with(context).load(myItem.imagPath).into(holder.itemImage);

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
        TextView itemPrice;

        ViewHolder(View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.tv_item_name);
            itemPrice = itemView.findViewById(R.id.tv_item_price);
            itemImage = itemView.findViewById(R.id.img_article);

        }


    }

    // convenience method for getting data at click position
    ArticleDataModel getItem(int id) {
        return data.get(id);
    }

}

