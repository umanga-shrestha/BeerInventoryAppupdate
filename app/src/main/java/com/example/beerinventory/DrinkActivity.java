package com.example.beerinventory;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;

public class DrinkActivity extends AppCompatActivity {

    private String name;
    private String brand;
    private String location;
    private String style;
    private String barcode;
    private String volume;
    private String quantity;
    private String alcohol_percentage;
    private String imagePath;
    private Button addDrinkButton;
    private static int RESULT_LOAD_IMAGE = 1;
    // Define Text Field Filter

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

    private EditText editText_1;
    private EditText editText_10;
    private EditText editText_2;
    private EditText editText_4;
    private EditText editText_7;
    private EditText editText_9;
    private EditText editText_12;
    private EditText editText_11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);
        imagePath = "false";
        Intent intent = getIntent();
        barcode = intent.getStringExtra("barcode");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Filter out special characters
        editText_1 = findViewById(R.id.editText_1);
        editText_1.setFilters(new InputFilter[]{filter});
        editText_2 = findViewById(R.id.editText_2);
        editText_2.setFilters(new InputFilter[]{filter});
        editText_4 = findViewById(R.id.editText_4);
        editText_4.setFilters(new InputFilter[]{filter});
        editText_7 = findViewById(R.id.editText_7);
        editText_7.setFilters(new InputFilter[]{filter});
        editText_9 = findViewById(R.id.editText_9);
        editText_9.setFilters(new InputFilter[]{filter});
        editText_10 = findViewById(R.id.editText_10);
        editText_10.setFilters(new InputFilter[]{filter});
        editText_11 = findViewById(R.id.editText_11);
        editText_11.setFilters(new InputFilter[]{filter});
        editText_12 = findViewById(R.id.editText_12);
        editText_12.setFilters(new InputFilter[]{filter});

        editText_9.setText(barcode);

        addDrinkButton = findViewById(R.id.button_addDrink);
        addDrinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get values from text fields
                name = editText_1.getText().toString();
                brand = editText_2.getText().toString();
                location = editText_4.getText().toString();
                style = editText_7.getText().toString();
                barcode = editText_9.getText().toString();
                volume = editText_10.getText().toString();
                quantity = editText_11.getText().toString();
                alcohol_percentage = editText_12.getText().toString();
                // Uncomment for API 29
                //File inventory = new File(getExternalFilesDir(null), "data.txt");
                // Uncomment for API <= 28
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
                // Check for duplicate bar codes
                String barTemp;
                String[] temp;

                LinkedList<String> lits = new LinkedList<>();
                boolean exist = false;
                try (BufferedReader reader = new BufferedReader(new FileReader(inventory));) {
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        temp = line.split(",");
                        barTemp = temp[4];
                        if (barTemp.equals(barcode)) {
                            exist = true;
                        } else {
                            lits.add(line);
                        }
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String breweryInfo = name + "," + brand + "," + location + "," + style + "," + barcode + "," + volume + "," +
                        quantity + "," + alcohol_percentage + "," + imagePath;
                lits.add(breweryInfo);
                if (exist) {
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
                } else {
                    // write breweryinfo to file
                    try {
                        FileWriter fileWriter = new FileWriter(inventory, true);
                        fileWriter.write(breweryInfo);
                        fileWriter.write("\r\n");
                        fileWriter.flush();
                        fileWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                Intent intent = new Intent(DrinkActivity.this, MainActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("brand", brand);
                intent.putExtra("quantity", quantity);
                startActivity(intent);
                finish(); //goes back to the main activity screen
            }
        });
        try {
            // Uncomment for API 29
            //reader = new BufferedReader(new FileReader(new File(getExternalFilesDir(null), "data.txt")));
            // Uncomment for API <= 28
            BufferedReader reader = new BufferedReader(new FileReader(new File(Environment.getExternalStorageDirectory() + "/beerInventory/data.txt")));
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] temp = line.split(",", -1);
                if (temp[4].equals(barcode)) {
                    editText_1.setText(temp[0]);
                    editText_2.setText(temp[1]);
                    editText_4.setText(temp[2]);
                    editText_7.setText(temp[3]);
                    editText_9.setText(temp[4]);
                    editText_10.setText(temp[5]);
                    editText_11.setText(temp[6]);
                    editText_12.setText(temp[7]);
                    if (temp[8].equals("true")) {
                        ImageView image = findViewById(R.id.drinkImageView);
                        image.setImageBitmap(BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/beerInventory/" + barcode));
                        Button buttonImage = findViewById(R.id.button_addDrinkImage);
                        buttonImage.setVisibility(View.GONE);
                    }
                    imagePath = temp[8];
                }
            }
            reader.close();
        } catch (IOException ignored) {
        }
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

            if (barcode == null) {
                barcode = editText_9.getText().toString();
            }
            if (barcode.equals("")){
                Toast toast = Toast.makeText(getApplicationContext(),
                        "No barcode received!", Toast.LENGTH_SHORT);
                toast.show();
                return;

            }
            ImageView image = findViewById(R.id.drinkImageView);
            image.setImageBitmap(BitmapFactory.decodeFile(imagePath));
            Button buttonImage = findViewById(R.id.button_addDrinkImage);
            buttonImage.setVisibility(View.GONE);
            try {
                copy(new File(imagePath), new File(Environment.getExternalStorageDirectory() + "/beerInventory/" + barcode));
            } catch (IOException e) {
                e.printStackTrace();
            }
            imagePath = "true";// String picturePath contains the path of selected Image
        }
    }

    public static void copy(File src, File dst) throws IOException {
        try (InputStream in = new FileInputStream(src)) {
            try (OutputStream out = new FileOutputStream(dst)) {
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            }
        }
    }
}
