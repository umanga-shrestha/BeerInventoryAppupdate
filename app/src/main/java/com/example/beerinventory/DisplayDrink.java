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
import java.security.PrivateKey;


public class DisplayDrink extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_drink);

        Intent intent = getIntent();
        String bar = intent.getStringExtra("barcode");

        BufferedReader reader;
        String[] temp;
        String name;
        String brand;
        String type;
        String alcohol;
        String barcode;
        String volume;
        String quantity;
        String location;

        Name = findViewById(R.id.textView2);
        Brand = findViewById(R.id.textView3);
        Type = findViewById(R.id.textView4);
        Barcode = findViewById(R.id.textView5);
        Volume = findViewById(R.id.textView6);
        Alcohol = findViewById(R.id.textView7);
        Quantity = findViewById(R.id.editText);
        Location = findViewById(R.id.editText2);

        try {
            // Uncomment for API 29
            //reader = new BufferedReader(new FileReader(new File(getExternalFilesDir(null), "data.txt")));
            // Uncomment for API <= 28
            reader = new BufferedReader(new FileReader(new File(Environment.getExternalStorageDirectory() + "/beerInventory/data.txt")));
            String line = null;
            while ((line = reader.readLine()) != null) {
                temp = line.split(",");
                name = temp[0];
                brand = temp[1];
                location = temp[2];
                type = temp[3];
                barcode = temp[4];
                volume = temp[5];
                quantity = temp[6];
                alcohol = temp[7];
                if (barcode.equals(bar)) {
                    Name.setText(name);
                    Brand.setText(brand);
                    Type.setText(type);
                    Barcode.setText(barcode);
                    Volume.setText(volume);
                    Alcohol.setText(alcohol);
                    Quantity.setText(quantity);
                    Location.setText(location);
                    break;
                }

            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    private EditText Quantity;
    private EditText Location;
    private TextView Name;
    private TextView Brand;
    private TextView Type;
    private TextView Barcode;
    private TextView Volume;
    private TextView Alcohol;

}



