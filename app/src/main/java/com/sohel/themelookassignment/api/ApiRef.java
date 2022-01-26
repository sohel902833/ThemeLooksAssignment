package com.sohel.themelookassignment.api;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ApiRef {
    private static final DatabaseReference userRef= FirebaseDatabase.getInstance().getReference().child("Users");
    private static final DatabaseReference brandRef= FirebaseDatabase.getInstance().getReference().child("Brands");
    private static final DatabaseReference categoryRef= FirebaseDatabase.getInstance().getReference().child("Category");
    private static final DatabaseReference productRef= FirebaseDatabase.getInstance().getReference().child("ThemeProducts");
    private static final DatabaseReference ratingRef= FirebaseDatabase.getInstance().getReference().child("ProductRatings");
    private static final DatabaseReference orderRef= FirebaseDatabase.getInstance().getReference().child("Orders");

    public static DatabaseReference getUserRef() {
        return userRef;
    }

    public static DatabaseReference getBrandRef() {
        return brandRef;
    }

    public static DatabaseReference getCategoryRef() {
        return categoryRef;
    }

    public static DatabaseReference getProductRef() {
        return productRef;
    }

    public static DatabaseReference getRatingRef() {
        return ratingRef;
    }

    public static DatabaseReference getOrderRef() {
        return orderRef;
    }
}
