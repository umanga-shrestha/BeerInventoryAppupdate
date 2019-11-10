package com.example.beerinventory;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ListView listView; ListViewAdapter listViewAdapter;
    String path = Environment.getExternalStorageDirectory() + "/beerInventory/";
    boolean decision;
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.user_list);
        search = findViewById(R.id.searchdata);



        listViewAdapter = new ListViewAdapter(MainActivity.this, R.layout.list_row);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                ListItem user = (ListItem) listView.getItemAtPosition(position);
                Toast.makeText(MainActivity.this, "Selected :" + " " + user.getName() + "," + position + ", " + user.getBarcode(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, DrinkActivity.class);
                intent.putExtra("barcode", user.getBarcode());
                startActivity(intent);
            }
        });



        getData("no order");



        //search data when text changes in edit text
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Toast.makeText(MainActivity.this, "before", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Toast.makeText(MainActivity.this, "On change", Toast.LENGTH_SHORT).show();
                String fil = search.getText().toString();
                getFilterData(fil);

            }

            @Override
            public void afterTextChanged(Editable s) {
                Toast.makeText(MainActivity.this, "after", Toast.LENGTH_SHORT).show();

            }
        });


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void getData(String order) {
        String flag = order;
        listViewAdapter.clear();
        BufferedReader reader;
        ArrayList<ArrayList<String>> tempList = new ArrayList<>();
        String[] temp;

        try {
            reader = new BufferedReader(new FileReader(new File(path + "data.txt")));
            String line;
            while ((line = reader.readLine()) != null) {
                temp = line.split(",");
                tempList.add(new ArrayList<>(Arrays.asList(temp[0], temp[1], temp[6], temp[4])));
                //Please DONT delete the below lines of code even though they are commented out - Zohar
                /*if (!quantity.equals("0")) {
                    ListItem user = new ListItem(name, brand, quantity, barcode);
                    listViewAdapter.add(user);
                }*/
            }

            if (flag.equals("asc") || flag.equals("no order"))
            {
                sortedData(tempList);
            }

            if (flag.equals("des"))
            {
                descendingOrder(tempList);
            }
            //sortedData(tempList);
            clearData(tempList);
            reader.close();
        } catch (IOException e) { e.printStackTrace(); }
        listView.setAdapter(listViewAdapter);
    }


    public void getFilterData(String filter) {
        listViewAdapter.clear();
        BufferedReader reader;
        ArrayList<ArrayList<String>> tempList = new ArrayList<>();
        String[] temp;

        try {
            reader = new BufferedReader(new FileReader(new File(path + "data.txt")));
            String line;
            while ((line = reader.readLine()) != null) {
                temp = line.split(",");

                if(temp[0].contains(filter)){
                    tempList.add(new ArrayList<>(Arrays.asList(temp[0], temp[1], temp[6], temp[4])));
                    //Please DONT delete the below lines of code even though they are commented out - Zohar
                    /*if (!quantity.equals("0")) {
                    ListItem user = new ListItem(name, brand, quantity, barcode);
                    listViewAdapter.add(user);
                    }*/
                }

            }
            sortedData(tempList);
            clearData(tempList);
            reader.close();
        } catch (IOException e) { e.printStackTrace(); }
        listView.setAdapter(listViewAdapter);
    }


    public void sortedData(ArrayList<ArrayList<String>> dataList) {
        String name, brand, quantity, barcode;

        Collections.sort(dataList, new Comparator<ArrayList<String>>() {
            @Override
            public int compare(ArrayList<String> name1, ArrayList<String> name2) {
                return name1.get(0).compareTo(name2.get(0));
            }
        });

        for (int row = 0; row < dataList.size(); row++) {
            name = dataList.get(row).get(0);
            brand = dataList.get(row).get(1);
            quantity = dataList.get(row).get(2);
            barcode = dataList.get(row).get(3);

            if (!quantity.equals("0")) {
                ListItem user = new ListItem(name, brand, quantity, barcode);
                listViewAdapter.add(user);
            }
        }
    }

    public void descendingOrder(ArrayList<ArrayList<String>> dataList) {
        String name, brand, quantity, barcode;

        Collections.sort(dataList, new Comparator<ArrayList<String>>() {
            @Override
            public int compare(ArrayList<String> name1, ArrayList<String> name2) {
                return name2.get(0).compareTo(name1.get(0));
            }
        });

        for (int row = 0; row < dataList.size(); row++) {
            name = dataList.get(row).get(0);
            brand = dataList.get(row).get(1);
            quantity = dataList.get(row).get(2);
            barcode = dataList.get(row).get(3);

            if (!quantity.equals("0")) {
                ListItem user = new ListItem(name, brand, quantity, barcode);
                listViewAdapter.add(user);
            }
        }
    }

    public void clearData(ArrayList<ArrayList<String>> dataList) {
        for (int row = 0; row < dataList.size(); row++) { dataList.get(row).clear(); }
    }

    //**************** This Retrieves the Data form the Scan ****************************
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //retrieve scan result
        super.onActivityResult(requestCode, resultCode, intent);
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (scanningResult != null) { //we have a result
            final String scanContent = scanningResult.getContents(); // <------- BARCODE
            String[] temp; BufferedReader reader;

            final String codigo = scanContent.replaceAll("[\\n\\t\\s]", "");
            try {
                File myDir = new File(path);
                if (!myDir.exists()) { myDir.mkdir(); }
                File inventory = new File(path + "data.txt");
                try { inventory.createNewFile(); } catch (IOException e) { e.printStackTrace(); }

                reader = new BufferedReader(new FileReader(new File(path + "data.txt")));
                String line;
                while ((line = reader.readLine()) != null) {
                    temp = line.split(",");
                    String bar = temp[4].replaceAll("[\\n\\t\\s]", "");

                    if (bar.equals(codigo)) { decision = true; break; }
                    decision = false;
                }
                if (decision) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Barcode Found", Toast.LENGTH_SHORT);
                    toast.show();

                    Intent display = new Intent(MainActivity.this, DisplayDrink.class);
                    display.putExtra("barcode", codigo);
                    startActivity(display);
                }
                if (!decision) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Barcode Not Found", Toast.LENGTH_SHORT);
                    toast.show();

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                    alertDialogBuilder.setTitle("Would you like to add a new drink?");

                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //If this button is pressed then we go to new drink activity
                            Intent intent = new Intent(MainActivity.this, DrinkActivity.class);
                            intent.putExtra("barcode", codigo);
                            startActivity(intent);

                        }
                    });
                    alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel(); // if NO is pressed just close dialog
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create(); // create alert dialog
                    alertDialog.show(); // Show the dialog
                }
                reader.close();
            } catch (IOException e) { e.printStackTrace(); }
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    //**************** Navigation Control ************************
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) { drawer.closeDrawer(GravityCompat.START); }
        else { super.onBackPressed(); }
    }

    // Inflate the menu; this adds items to the action bar if it is present.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu); return true;
    }

    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //noinspection SimplifiableIfStatement
        if (item.getItemId() == R.id.action_sort_name_asc) {
            //startActivity(new Intent(MainActivity.this, DrinkActivity.class));
            getData("asc");
        }

        if (item.getItemId() == R.id.action_sort_name_des)
        {
            getData("des");
        }

        if (item.getItemId() == R.id.action_sort_quantity)
        {
            //getData("qty);
        }

        return super.onOptionsItemSelected(item);
    }

    // Handle navigation view item clicks here.
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) { // Handle the camera action
            IntentIntegrator scanIntegrator = new IntentIntegrator(this); scanIntegrator.initiateScan();
        } else if (id == R.id.nav_gallery) {
            startActivity(new Intent(MainActivity.this, DrinkActivity.class));
        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
