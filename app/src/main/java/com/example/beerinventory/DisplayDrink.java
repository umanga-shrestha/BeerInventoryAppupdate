package com.example.beerinventory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class DisplayDrink extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_drink);

        Intent intent = getIntent();
        String bar = intent.getStringExtra("barcode");

        BufferedReader reader;
        String[] temp;

        TextView name = findViewById(R.id.textView2);
        TextView brand = findViewById(R.id.textView3);
        TextView Type = findViewById(R.id.textView4);
        TextView Barcode = findViewById(R.id.textView5);
        TextView Volume = findViewById(R.id.textView6);
        TextView Alcohol = findViewById(R.id.textView7);
        EditText quantity = findViewById(R.id.editText);
        EditText location = findViewById(R.id.editText2);

        try {
            // Uncomment for API 29
            //reader = new BufferedReader(new FileReader(new File(getExternalFilesDir(null), "data.txt")));
            // Uncomment for API <= 28
            reader = new BufferedReader(new FileReader(new File(Environment.getExternalStorageDirectory() + "/beerInventory/data.txt")));
            String line;
            while ((line = reader.readLine()) != null) {
                temp = line.split(",");
                if (temp[4].equals(bar)) {
                    name.setText(temp[0]);
                    brand.setText(temp[1]);
                    Type.setText(temp[3]);
                    Barcode.setText(temp[4]);
                    Volume.setText(temp[5]);
                    Alcohol.setText(temp[7]);
                    quantity.setText(temp[6]);
                    location.setText(temp[2]);
                    break;
                }
            }
            reader.close();
        } catch (IOException e) { e.printStackTrace(); }
    }
}



