package com.sohel.themelookassignment.model;

import java.util.List;

public class Product {
    String productId,shopId,productName,productDescription,brand,category;
    double rating;
    List<ProductPrice> productPrice;
    List<ImageModel> productImages;
    int quantity=1;
    public Product(){}

    public Product(String productId, String shopId, String productName, String productDescription, String brand, String category, List<ProductPrice> productPrice, double rating, List<ImageModel> productImages) {
        this.productId = productId;
        this.shopId = shopId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.brand = brand;
        this.category = category;
        this.productPrice = productPrice;
        this.rating = rating;
        this.productImages = productImages;
    }
    public Product(String productId, String shopId, String productName, String productDescription, String brand, String category, List<ProductPrice> productPrice, double rating, List<ImageModel> productImages,int quantity) {
        this.productId = productId;
        this.shopId = shopId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.brand = brand;
        this.category = category;
        this.productPrice = productPrice;
        this.rating = rating;
        this.productImages = productImages;
        this.quantity=quantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<ProductPrice> getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(List<ProductPrice> productPrice) {
        this.productPrice = productPrice;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<ImageModel> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ImageModel> productImages) {
        this.productImages = productImages;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public static Double getMaximumPrice(List<ProductPrice> productPriceList){
        Double maximumPrice=0.0;
        if(productPriceList!=null && productPriceList.size()>0){
           maximumPrice=productPriceList.get(0).getPrice();
            for(ProductPrice productPrice:productPriceList){
                if(maximumPrice< productPrice.getPrice()){
                    maximumPrice =productPrice.getPrice();
                }
            }

        }
        return maximumPrice;
    }
    public static Double getMinimumPrice(List<ProductPrice> productPriceList){
        Double minimumPrice=0.0;
        if(productPriceList!=null && productPriceList.size()>0){
           minimumPrice=productPriceList.get(0).getPrice();
            for(ProductPrice productPrice:productPriceList){
                if(minimumPrice> productPrice.getPrice()){
                    minimumPrice =productPrice.getPrice();
                }
            }

        }
        return minimumPrice;
    }


}
