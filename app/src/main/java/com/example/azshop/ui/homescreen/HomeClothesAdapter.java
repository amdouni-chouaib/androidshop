package com.example.azshop.ui.homescreen;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.azshop.R;
import com.example.azshop.data.model.Articlemodel.ArticleDataModel;
import com.example.azshop.ui.authscreens.UserProfileActivity;
import com.example.azshop.ui.detailsscreen.DetailsActivity;
import com.example.azshop.ui.sellscreen.SellActivity;

import java.util.ArrayList;

public class HomeClothesAdapter extends ArrayAdapter<ArticleDataModel> {
    //decalring attricbutes
    private Context context;
//constructor of the adapter
    public HomeClothesAdapter(@NonNull Context context, ArrayList<ArticleDataModel> courseModelArrayList) {
        super(context, 0, courseModelArrayList);
        this.context = context;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.item_clothe, parent, false);
        }

        ArticleDataModel articleDataModel = getItem(position);
        //getting access into XML elements
        TextView articlename = listitemView.findViewById(R.id.tv_item_name);
        TextView articleprice = listitemView.findViewById(R.id.tv_item_price);
        ImageView imgArticle = listitemView.findViewById(R.id.img_article);
        if (articleDataModel != null) {
            //if the model contains data then we will set the XML elements values
            articlename.setText(articleDataModel.getTitle());
            articleprice.setText(articleDataModel.getPrice().toString() + "$");
            Glide.with(context).load(articleDataModel.getImagPath()).into(imgArticle);
        }
        listitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // starting activity and sending the article information after clicking on the article
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("id",  articleDataModel.id);
                intent.putExtra("title",  articleDataModel.title);
                intent.putExtra("description",  articleDataModel.description);
                intent.putExtra("gender",  articleDataModel.gender);
                intent.putExtra("price",  articleDataModel.price.toString());
                intent.putExtra("image",  articleDataModel.imagPath);
                intent.putExtra("type",  articleDataModel.type);
                context.startActivity(intent);

            }
        });
        return listitemView;
    }
}
