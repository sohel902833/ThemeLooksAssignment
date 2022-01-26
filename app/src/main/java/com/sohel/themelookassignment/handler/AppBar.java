package com.sohel.themelookassignment.handler;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.sohel.themelookassignment.R;


public class AppBar {

    public static void setUpAppBar(Activity activity, Toolbar toolbar, String text){
        ImageView backButton = (ImageView)toolbar.findViewById(R.id.appbar_BackButton);
        TextView appBarTv=toolbar.findViewById(R.id.appBarText);
        appBarTv.setText(""+text);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               activity.finish();
            }
        });
    }



}
