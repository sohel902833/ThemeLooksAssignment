package com.sohel.themelookassignment.views.seller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.sohel.themelookassignment.R;
import com.sohel.themelookassignment.adapter.ViewPagerAdapter;
import com.sohel.themelookassignment.views.seller.fragments.SellerAcceptedOrderFragment;
import com.sohel.themelookassignment.views.seller.fragments.SellerCompletedOrderFragment;
import com.sohel.themelookassignment.views.seller.fragments.SellerDeliveredOrderFragment;
import com.sohel.themelookassignment.views.seller.fragments.SellerPendingOrderFragment;


public class SellerOrderActivity extends AppCompatActivity {


    private TabLayout mTabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_order);

        mTabLayout=findViewById(R.id.tablayout_id);
        viewPager=findViewById(R.id.view_pagerId);
        adapter=new ViewPagerAdapter(getSupportFragmentManager());
        //add Fragment here
        adapter.AddFragment(new SellerPendingOrderFragment(),"Pending");
        adapter.AddFragment(new SellerAcceptedOrderFragment(),"Accepted");
        adapter.AddFragment(new SellerDeliveredOrderFragment(),"Delivered");
        adapter.AddFragment(new SellerCompletedOrderFragment(),"Completed");
        viewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(viewPager);


    }
}