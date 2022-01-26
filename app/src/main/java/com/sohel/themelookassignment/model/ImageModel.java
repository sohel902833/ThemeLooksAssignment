package com.sohel.themelookassignment.model;

public class ImageModel {
    String imageUrl;

    public ImageModel(){

    }
    public ImageModel(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
