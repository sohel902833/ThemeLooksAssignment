package com.sohel.themelookassignment.views.seller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.sohel.themelookassignment.R;
import com.sohel.themelookassignment.handler.AppBar;
import com.sohel.themelookassignment.handler.CustomDialog;
import com.sohel.themelookassignment.localdb.UserDb;
import com.sohel.themelookassignment.views.common.StartActivity;


public class SellerHomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private UserDb userDb;

    private CardView profileCard,productsCard,addProductsCard,logoutCard,addBrandCard,addCategoryCard,orderCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_home);
        init();

        logoutCard.setOnClickListener(v -> {
            logoutUser();
        });

        addBrandCard.setOnClickListener(v -> {
            CustomDialog.showBrandDialog(SellerHomeActivity.this);
        });
        addCategoryCard.setOnClickListener(v -> {
            CustomDialog.showCategoryDialog(SellerHomeActivity.this);
        });
        productsCard.setOnClickListener(v -> {
            startActivity(new Intent(SellerHomeActivity.this,SellerProductActivity.class));
        });
        orderCard.setOnClickListener(v -> {
            startActivity(new Intent(SellerHomeActivity.this,SellerOrderActivity.class));
        });

        addProductsCard.setOnClickListener(v -> startActivity(new Intent(SellerHomeActivity.this,SellerAddProductActivity.class)));
    }
    private void init(){
        userDb=new UserDb(this);
        mAuth=FirebaseAuth.getInstance();
        profileCard=findViewById(R.id.s_h_ProfileCard);
        addProductsCard=findViewById(R.id.s_h_addProductCard);
        productsCard=findViewById(R.id.s_h_productsCard);
        logoutCard=findViewById(R.id.s_h_logoutCard);
        addBrandCard=findViewById(R.id.s_h_addBrandCard);
        orderCard=findViewById(R.id.s_h_orderCard);
        addCategoryCard=findViewById(R.id.s_h_addCategoryCard);
        Toolbar toolbar = findViewById(R.id.appBarId);
        AppBar.setUpAppBar(this,toolbar,"Seller");
    }
    private void logoutUser() {
        mAuth.signOut();
        userDb.removeUserData();
        Intent intent=new Intent(SellerHomeActivity.this, StartActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}