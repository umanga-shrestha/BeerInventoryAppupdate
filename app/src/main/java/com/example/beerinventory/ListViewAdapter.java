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
    private List list = new ArrayList();

    ListViewAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public void add(Object object) { super.add(object); list.add(object); }

    @Override
    public int getCount() { return list.size(); }

    @Nullable @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView; Contactholder contactholder; LayoutInflater layoutInflater; ListItem item;

        if (row == null) {
            layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert layoutInflater != null;
            row = layoutInflater.inflate(R.layout.list_row, parent, false);
            contactholder = new Contactholder();

            contactholder.name = row.findViewById(R.id.name); contactholder.brand = row.findViewById(R.id.brand);
            contactholder.quantity = row.findViewById(R.id.quantity);

            row.setTag(contactholder);
        } else { contactholder = (Contactholder) row.getTag(); }

        item = (ListItem) this.getItem(position);
        assert item != null;
        contactholder.name.setText(item.getName()); contactholder.brand.setText(item.getBrand());
        contactholder.quantity.setText((item.getQuantity()));
        return row;
    }

    static class Contactholder { TextView name, brand, quantity; }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void clear() {
        list.clear();
    }
}
