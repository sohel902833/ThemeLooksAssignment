package com.sohel.themelookassignment.handler;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sohel.themelookassignment.R;
import com.sohel.themelookassignment.localdb.CartDb;
import com.sohel.themelookassignment.views.user.CartActivity;

public class CustomCart {
    Activity activity;
    CartDb cartDb;
    TextView cartTv;

    public CustomCart(Activity activity, RelativeLayout relativeLayout) {
        this.activity = activity;
        this.relativeLayout = relativeLayout;

        cartDb=new CartDb(activity);
    }

    RelativeLayout relativeLayout;


    public void init(){
         cartTv=relativeLayout.findViewById(R.id.cartCountTvId);
        cartTv.setText(""+cartDb.getCartSize());

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(activity, CartActivity.class));
            }
        });


    }

    public void notifyCartChanged(){
        cartTv.setText(""+cartDb.getCartSize());
    }


}
