package com.sohel.themelookassignment.model;

import android.media.Rating;

public class RatingModel {
    String productId,ratingId,comment,userId,userName;
    double rating;
    public RatingModel(){}

    public RatingModel(String productId,String ratingId, String comment, String userId, String userName, double rating) {
       this.productId=productId;
        this.ratingId = ratingId;
        this.comment = comment;
        this.userId = userId;
        this.userName = userName;
        this.rating = rating;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getRatingId() {
        return ratingId;
    }

    public void setRatingId(String ratingId) {
        this.ratingId = ratingId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
