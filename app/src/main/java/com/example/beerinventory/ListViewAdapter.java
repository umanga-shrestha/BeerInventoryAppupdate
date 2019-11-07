package com.example.beerinventory;

import android.content.Intent;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListViewAdapter extends ArrayAdapter {
    List list = new ArrayList();
    ListViewAdapter global;

    public ListViewAdapter(Context context, int resource) {
        super(context, resource);
        global = this;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row;
        row = convertView;
        final Contactholder contactholder;

        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.list_row, parent, false);
            contactholder = new Contactholder();
            contactholder.name = row.findViewById(R.id.name);
            contactholder.brand = row.findViewById(R.id.brand);
            contactholder.quantity = row.findViewById(R.id.quantity);
            contactholder.delete = row.findViewById(R.id.buttonDelete);
            contactholder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println();
                    File myDir = new File(Environment.getExternalStorageDirectory() + "/beerInventory");
                    if (!myDir.exists()) {
                        myDir.mkdir();
                    }
                    File inventory = new File(Environment.getExternalStorageDirectory() + "/beerInventory/data.txt");
                    try {
                        inventory.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String[] temp;
                    LinkedList<String> lits = new LinkedList<>();
                    try (BufferedReader reader = new BufferedReader(new FileReader(inventory));) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            temp = line.split(",");
                            if (!contactholder.name.getText().toString().equals(temp[0]) ||
                                    !contactholder.brand.getText().toString().equals(temp[1]) ||
                                    !contactholder.quantity.getText().toString().equals(temp[6])) {
                                lits.add(line);
                            }
                        }

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        FileWriter fileWriter = new FileWriter(inventory, false);
                        for (String s : lits) {
                            fileWriter.write(s);
                            fileWriter.write("\r\n");
                        }
                        fileWriter.flush();
                        fileWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(view.getContext(), MainActivity.class);
                    view.getContext().startActivity(intent);
                }
            });
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
        Button delete;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void clear() {
        list.clear();
    }
}
