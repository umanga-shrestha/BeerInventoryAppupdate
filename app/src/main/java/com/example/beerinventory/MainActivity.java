package com.example.beerinventory;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;

import android.util.Log;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import static java.util.Collections.*;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    ListView listView;
    ListViewAdapter listViewAdapter;
    boolean decision;

    //**************** Main Activity Shit******************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.user_list);
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


        getData();

        //Alex's code for ListView
        /*ArrayList userList = getListData();
        final ListView lv = (ListView) findViewById(R.id.user_list);
        lv.setAdapter(new CustomListAdapter(this, userList));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                ListItem user = (ListItem) lv.getItemAtPosition(position);
                Toast.makeText(MainActivity.this, "Selected :" + " " + user.getName()+", "+ user.getLocation(), Toast.LENGTH_SHORT).show();
            }
        });*/

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

    public void getData() {
        listViewAdapter.clear();
        getListData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String name = bundle.getString("name");
            String brand = bundle.getString("brand");
            String quantity = bundle.getString("quantity");
        }
        listView.setAdapter(listViewAdapter);
    }

    public void getListData() {
        String name;
        String brand;
        String quantity;
        String barcode;
        String[] temp;
        BufferedReader reader;
        ArrayList<ArrayList<String>> tempList = new ArrayList<ArrayList<String>>();

        try {
            reader = new BufferedReader(new FileReader(new File(Environment.getExternalStorageDirectory() + "/beerInventory/data.txt")));
            String line = null;
            while ((line = reader.readLine()) != null) {
                temp = line.split(",");
                name = temp[0];
                brand = temp[1];
                quantity = temp[6];
                barcode = temp[4];

                tempList.add(new ArrayList<String>(Arrays.asList(name, brand, quantity, barcode)));

                //Please DONT delete the below lines of code even though they are commented out - Zohar
                /*if (!quantity.equals("0")) {
                    ListItem user = new ListItem(name, brand, quantity, barcode);
                    listViewAdapter.add(user);
                }*/
            }

            sortedData(tempList);
            clearData(tempList);
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sortedData(ArrayList<ArrayList<String>> dataList)
    {
        String name;
        String brand;
        String quantity;
        String barcode;
        int row;

        Collections.sort(dataList, new Comparator<ArrayList<String>>() {
            @Override
            public int compare(ArrayList<String> name1, ArrayList<String> name2) {
                return name1.get(0).compareTo(name2.get(0));
            }
        });

        for (row = 0; row < dataList.size(); row++)
        {
            int col = 0;

            name = dataList.get(row).get(col);
            brand = dataList.get(row).get(col+=1);
            quantity = dataList.get(row).get(col+=1);
            barcode = dataList.get(row).get(col+=1);

            if (!quantity.equals("0")) {
                ListItem user = new ListItem(name, brand, quantity, barcode);
                listViewAdapter.add(user);
            }
        }
    }

    public void clearData(ArrayList<ArrayList<String>> dataList)
    {
        int row;

        for (row = 0; row < dataList.size(); row++)
        {
            dataList.get(row).clear();
        }
    }

//**************** This Retrieves the Data form the Scan ****************************

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
//retrieve scan result
        super.onActivityResult(requestCode, resultCode, intent);
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (scanningResult != null) {
//we have a result
            final String scanContent = scanningResult.getContents(); // <------- BARCODE
            // Log.d("scanContent", scanContent);
            // String scanFormat = scanningResult.getFormatName();
            // Log.d("scanFormat", scanFormat);
            String[] temp;
            BufferedReader reader;

            final String codigo = scanContent.replaceAll("[\\n\\t\\s]", "");
            try {
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


                reader = new BufferedReader(new FileReader(new File(Environment.getExternalStorageDirectory() + "/beerInventory/data.txt")));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    temp = line.split(",");
                    String bar = temp[4].replaceAll("[\\n\\t\\s]", "");

                    if (bar.equals(codigo)) {
                        decision = true;
                        break;
                    }
                    if (!bar.equals(codigo)) {
                        decision = false;
                    }
                }


                if (decision) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Barcode Found", Toast.LENGTH_SHORT);
                    toast.show();

                    Intent display = new Intent(MainActivity.this, DisplayDrink.class);
                    display.putExtra("barcode", codigo);
                    startActivity(display);

                }

                if (!decision) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Barcode Not Found", Toast.LENGTH_SHORT);
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

                            //If NO is pressed just close dialog
                            dialog.cancel();
                        }
                    });


                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    // Show the dialog
                    alertDialog.show();
                }


                reader.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    //**************OTHER STUFF***********************************
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, DrinkActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();

        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(MainActivity.this, DrinkActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
