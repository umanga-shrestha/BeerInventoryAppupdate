package com.example.beerinventory;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.text.Spanned;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import java.io.*;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class NewDrinkActitvity extends AppCompatActivity {
    private static final String TAG = NewDrinkActitvity.class.getSimpleName();

    final String PATH = "/storage/emulated/0/Documents/swift_scan_data/";
    //File dir = new File("/storage/emulated/0/Documents/swift_scan_data/");
    private static int RESULT_LOAD_IMAGE = 1;

    private String name;
    private String brand;
    private String location;
    private String style;
    private String barcode;
    private String volume;
    private String quantity;
    private String alcohol_percentage;
    private String breweryInfo;
    private String imagePath;
    private Button addDrinkButton;

    // Define Text Field Filter
    private EditText editText;
    private String blockCharacterSet = "<>,~#^|$%&*!?@+=;:{}[]`";
    private InputFilter filter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
            if (charSequence != null && blockCharacterSet.contains(("" + charSequence))) {
                return "";
            }
            return null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        File myDir = new File(Environment.getExternalStorageDirectory() + "/beerInventory");

        if (!myDir.exists()) {
            myDir.mkdir();
        }

        File stockFile = new File(Environment.getExternalStorageDirectory() + "/beerInventory/data.txt");

        try {
            stockFile.createNewFile();
        } catch (IOException e) {
            ;
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_drink);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        String extre = getIntent().getStringExtra("barcode");

        // Filter out special characters
        editText = (EditText) findViewById(R.id.editText_1);
        editText.setFilters(new InputFilter[]{filter});
        editText = (EditText) findViewById(R.id.editText_2);
        editText.setFilters(new InputFilter[]{filter});
        editText = (EditText) findViewById(R.id.editText_4);
        editText.setFilters(new InputFilter[]{filter});
        editText = (EditText) findViewById(R.id.editText_7);
        editText.setFilters(new InputFilter[]{filter});
        editText = (EditText) findViewById(R.id.editText_9);
        if (extre != null) {
            if (!extre.equals("")) {
                editText.setText(extre);
            }
        }
        editText.setFilters(new InputFilter[]{filter});
        editText = (EditText) findViewById(R.id.editText_10);
        editText.setFilters(new InputFilter[]{filter});
        editText = (EditText) findViewById(R.id.editText_11);
        editText.setFilters(new InputFilter[]{filter});
        editText = (EditText) findViewById(R.id.editText_12);
        editText.setFilters(new InputFilter[]{filter});


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

        breweryInfo = name + "," + brand + "," + location + "," + style + "," + barcode + "," + volume + "," +
                quantity + "," + alcohol_percentage + "," + imagePath;

        final File inventory = new File(Environment.getExternalStorageDirectory() + "/beerInventory/data.txt");
        //FileWriter fileWriter = new FileWriter(breweryInfo, true);

        try {
            FileWriter fileWriter = new FileWriter(inventory, true);
            fileWriter.write(breweryInfo);
            fileWriter.write("\r\n");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {

        }
        System.out.println();
        Intent intent = new Intent(NewDrinkActitvity.this, MainActivity.class);
        startActivity(intent);
    /*    addDrinkButton = findViewById(R.id.button_addDrink);
        addDrinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FileWriter fileWriter = new FileWriter(inventory, true);
                    fileWriter.write(breweryInfo);
                    fileWriter.write("\r\n");
                    fileWriter.flush();
                    fileWriter.close();

                    finish(); //goes back to the main activity screen
                } catch (IOException e) {

                }
            }
        });*/

    }

    public void addThisDrinkImage(View view) {
        Intent i = new Intent(
                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            imagePath = cursor.getString(columnIndex);
            cursor.close();
            ImageView image = findViewById(R.id.drinkImageView);
            image.setImageBitmap(BitmapFactory.decodeFile(imagePath));
            Button buttonImage=findViewById(R.id.button_addDrinkImage);
            buttonImage.setVisibility(View.GONE);
            // String picturePath contains the path of selected Image
        }
    }
}
