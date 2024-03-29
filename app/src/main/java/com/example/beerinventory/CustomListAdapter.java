package com.example.beerinventory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListAdapter extends BaseAdapter {
    private ArrayList<ListItem> listData;
    private LayoutInflater layoutInflater;

    public CustomListAdapter(Context aContext, ArrayList<ListItem> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View v, ViewGroup vg) {
        ViewHolder holder;
        if (v == null) {
            v = layoutInflater.inflate(R.layout.list_row, null);
            holder = new ViewHolder();
            holder.uName = v.findViewById(R.id.name);
            holder.uDesignation = v.findViewById(R.id.brand);
            holder.uLocation = v.findViewById(R.id.quantity);
            v.setTag(holder);
        } else { holder = (ViewHolder) v.getTag(); }
        holder.uName.setText(listData.get(position).getName());
        holder.uDesignation.setText(listData.get(position).getBrand());
        holder.uLocation.setText(listData.get(position).getQuantity());
        return v;
    }

    static class ViewHolder { TextView uName, uDesignation, uLocation;}
}