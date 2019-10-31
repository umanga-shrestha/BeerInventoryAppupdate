package com.example.beerinventory;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.NoSuchElementException;
import java.io.FileNotFoundException;

public class ReadData {
    private String breweryName;
    private String brand;
    private String location;
    private String style;
    private String barcode;
    private String volume;
    private String quantity;
    private String alcoholVolume;
    private List<String> productList;

    public void splitData() {
        //Scanner read = new Scanner (new File(Environment.getExternalStorageDirectory()+"/beerInventory/data.txt"))
        try {
            Scanner read = new Scanner(new File(Environment.getExternalStorageDirectory() + "/beerInventory/data.txt"));

            while (read.hasNextLine()) {
                String line = read.nextLine();
                productList = Arrays.asList(line.split(","));
                break;
            }
        } catch (FileNotFoundException e) {
            ;
        }
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
