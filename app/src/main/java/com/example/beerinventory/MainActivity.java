package com.example.beerinventory;

import android.os.Bundle;
import java.io.*;

import android.os.Environment;
import android.util.Log;
import android.view.View;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;




public  class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {



    //**************** Main Activity Shit******************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ArrayList userList = getListData();
        final ListView lv = (ListView) findViewById(R.id.user_list);
        lv.setAdapter(new CustomListAdapter(this, userList));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                ListItem user = (ListItem) lv.getItemAtPosition(position);
                Toast.makeText(MainActivity.this, "Selected :" + " " + user.getName()+", "+ user.getLocation(), Toast.LENGTH_SHORT).show();
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




    private ArrayList getListData() {
        ArrayList<ListItem> results = new ArrayList<>();

        String name;
        String brand;
        String quant;
        String [] temp;

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(Environment.getExternalStorageDirectory()+"/beerInventory/data.txt"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                ListItem user = new ListItem();
                temp = line.split(",");
                name = temp[0];
                brand = temp[1];
                quant = temp[6];
                user.setName(name);
                user.setDesignation(brand);
                user.setLocation(quant);
                results.add(user);
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return results;



    }




//**************** This Retrieves the Data form the Scan ****************************

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
//retrieve scan result
        super.onActivityResult(requestCode, resultCode, intent);
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (scanningResult != null) {
//we have a result

            String scanContent = scanningResult.getContents(); // <------- BARCODE
            Log.d("scanContent", scanContent);
            String scanFormat = scanningResult.getFormatName();
            Log.d("scanFormat", scanFormat);
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
            return true;
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
            Intent intent = new Intent(MainActivity.this, NewDrinkActitvity.class);
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
