package com.example.beerinventory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends ArrayAdapter {
    List list = new ArrayList();

    public ListViewAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public void add(Object object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row = convertView;
        Contactholder contactholder;

        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.list_row, parent, false);

            contactholder = new Contactholder();

            contactholder.name = (TextView) row.findViewById(R.id.name);
            contactholder.brand = (TextView) row.findViewById(R.id.brand);
            contactholder.quantity = (TextView) row.findViewById(R.id.quantity);

            row.setTag(contactholder);
        } else {
            contactholder = (Contactholder) row.getTag();
        }

        ListItem item = (ListItem) this.getItem(position);
        contactholder.name.setText(item.getName());
        contactholder.brand.setText(item.getBrand());
        contactholder.quantity.setText((item.getQuantity()));
        return row;
    }

    static class Contactholder {
        TextView name;
        TextView brand;
        TextView quantity;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void clear() {
        list.clear();
    }
}
