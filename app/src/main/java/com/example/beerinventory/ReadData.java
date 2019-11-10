package com.example.beerinventory;

import android.os.Environment;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class ReadData {
    private String breweryName, brand, location, style, barcode, volume, quantity, alcoholVolume;
    private List<String> productList;

    public void splitData() {
        String line;
        //Scanner read = new Scanner (new File(Environment.getExternalStorageDirectory()+"/beerInventory/data.txt"))
        try {
            Scanner read = new Scanner(new File(Environment.getExternalStorageDirectory() + "/beerInventory/data.txt"));

            while (read.hasNextLine()) {
                line = read.nextLine();
                productList = Arrays.asList(line.split(","));
                break;
            }
        } catch (FileNotFoundException e) { e.printStackTrace(); }
    }

    public void readList() {
        breweryName = productList.get(0);
        brand = productList.get(1);
    }

    public String getBreweryName() {
        return breweryName;
    }

    public String getBrand() {
        return brand;
    }
}
