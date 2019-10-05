package com.example.beerinventory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.nio.file.Files;
import java.io.*;
import androidx.appcompat.app.AppCompatActivity;

public class NewDrinkActitvity extends AppCompatActivity {
    private static final String TAG = NewDrinkActitvity.class.getSimpleName();

    final String PATH = "/storage/emulated/0/Documents/swift_scan_data/";
    //File dir = new File("/storage/emulated/0/Documents/swift_scan_data/");

    private String name;
    private String brand;
    private String location;
    private String style;
    private String barcode;
    private String volume;
    private String quantity;
    private String alcohol_percentage;
    private String breweryInfo;
    private Button addDrinkButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        File myDir = new File(Environment.getExternalStorageDirectory()+"/beerInventory");

        if (!myDir.exists()) {
            myDir.mkdir();
        }

        File stockFile = new File (Environment.getExternalStorageDirectory()+"/beerInventory/data.txt");

        try
        {
            stockFile.createNewFile();
        }

        catch (IOException e)
        {
            ;
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_drink);
    }

    public void addThisDrink(View view) {

        final EditText editText_1 = findViewById(R.id.editText_1);
        final EditText editText_10 = findViewById(R.id.editText_10);
        final EditText editText_2 = findViewById(R.id.editText_2);
        final EditText editText_4 = findViewById(R.id.editText_4);
        final EditText editText_7 = findViewById(R.id.editText_7);
        final EditText editText_9 = findViewById(R.id.editText_9);
        final EditText editText_12 = findViewById(R.id.editText_12);
        final EditText editText_11 = findViewById(R.id.editText_11);

        name = editText_1.getText().toString();
        brand = editText_2.getText().toString();
        location = editText_4.getText().toString();
        style = editText_7.getText().toString();
        barcode = editText_9.getText().toString();
        volume = editText_10.getText().toString();
        quantity = editText_11.getText().toString();
        alcohol_percentage = editText_12.getText().toString();

        breweryInfo = name + " " + brand + " " + location + " " + style + " " + barcode + " " + volume + " " +
                quantity + " " + alcohol_percentage;

        final File inventory = new File(Environment.getExternalStorageDirectory()+"/beerInventory/data.txt");
        //FileWriter fileWriter = new FileWriter(breweryInfo, true);

        /*try
        {
            FileWriter fileWriter = new FileWriter(inventory, true);
            fileWriter.write(breweryInfo);
            fileWriter.write("\r\n");
            fileWriter.flush();
            fileWriter.close();
        }

        catch (IOException e) {

        }*/

        addDrinkButton = findViewById(R.id.button_addDrink);
        addDrinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                try
                {
                    FileWriter fileWriter = new FileWriter(inventory, true);
                    fileWriter.write(breweryInfo);
                    fileWriter.write("\r\n");
                    fileWriter.flush();
                    fileWriter.close();
                    
                    finish(); //goes back to the main activity screen
                }

                catch (IOException e) {

                }
            }
        });

    }

}
