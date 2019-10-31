package com.example.beerinventory;

public class ListItem {
    private String name;
    private String brand;
    private String quantity;
    private String barcode;

    public ListItem (String name, String brand, String quantity, String barcode) {
        this.name = name;
        this.brand = brand;
        this.quantity = quantity;
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) { this.brand = brand; }

    public String getQuantity() {
        return quantity;
    }
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getBarcode() {return barcode; }
}