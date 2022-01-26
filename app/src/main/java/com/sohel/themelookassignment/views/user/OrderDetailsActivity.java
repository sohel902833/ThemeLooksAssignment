package com.sohel.themelookassignment.views.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.sohel.themelookassignment.R;
import com.sohel.themelookassignment.adapter.ViewPagerAdapter;
import com.sohel.themelookassignment.views.user.fragments.OrderAcceptFragment;
import com.sohel.themelookassignment.views.user.fragments.OrderPendingFragment;
import com.sohel.themelookassignment.views.user.fragments.OrderSuccessFragment;

public class OrderDetailsActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        mTabLayout=findViewById(R.id.tablayout_id);
        viewPager=findViewById(R.id.view_pagerId);
        adapter=new ViewPagerAdapter(getSupportFragmentManager());


        //add Fragment here
        adapter.AddFragment(new OrderPendingFragment(),"Pending");
        adapter.AddFragment(new OrderAcceptFragment(),"Accepted");
        adapter.AddFragment(new OrderSuccessFragment(),"Success");
        viewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(viewPager);




    }
}