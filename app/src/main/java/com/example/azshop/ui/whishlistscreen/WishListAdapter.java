package com.example.azshop.ui.whishlistscreen;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.azshop.R;
import com.example.azshop.data.model.CartArticleDataModel;
import com.example.azshop.ui.detailsscreen.DetailsActivity;

import java.util.List;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.ViewHolder> {

    private List<CartArticleDataModel> data;
    private Context context;

    // data is passed into the constructor
    public WishListAdapter(Context context, List<CartArticleDataModel> data) {
        super();
        this.data = data;
        this.context = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishitemlayout, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CartArticleDataModel myItem = data.get(position);
        holder.itemTitle.setText(myItem.title);
        holder.itemPrice.setText(String.valueOf(myItem.price));
        Glide.with(context).load(myItem.imagPath).into(holder.itemImage);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("id",  myItem.id);
                intent.putExtra("title",  myItem.title);
                intent.putExtra("description",  myItem.description);
                intent.putExtra("gender",  myItem.gender);
                intent.putExtra("price",  myItem.price.toString());
                intent.putExtra("image",  myItem.imagPath);
                intent.putExtra("type",  myItem.type);
                context.startActivity(intent);
            }
        });

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
        View item;

        ViewHolder(View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.tv_item_name);
            itemPrice = itemView.findViewById(R.id.tv_item_price);
            itemImage = itemView.findViewById(R.id.img_article);
            item = itemView;

        }


    }

    // convenience method for getting data at click position
    CartArticleDataModel getItem(int id) {
        return data.get(id);
    }

}

