package com.example.azshop.ui.sellscreen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.azshop.R;

import java.util.List;

public class SpinnerAdapter extends BaseAdapter {

    private Context context;
    private List<String> productTypeList;

    public SpinnerAdapter(Context context, List<String> productTypeList) {
        this.context = context;
        this.productTypeList = productTypeList;
    }

    @Override
    public int getCount() {
        return productTypeList.size();
    }

    @Override
    public Object getItem(int position) {
        return productTypeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View item_spiner = LayoutInflater.from(context)
                .inflate(R.layout.spinneritem, parent, false);


            TextView nameType = item_spiner.findViewById(R.id.tv);


            nameType.setText(productTypeList.get(position));

        return item_spiner;
    }
}

