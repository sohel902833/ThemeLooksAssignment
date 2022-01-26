package com.sohel.themelookassignment.model;

public class ProductPrice {
    String color,size;
    double price;
    public ProductPrice(){}

    public ProductPrice(String color, String size, double price) {
        this.color = color;
        this.size = size;
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
