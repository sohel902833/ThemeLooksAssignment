package com.sohel.themelookassignment.handler;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.sohel.themelookassignment.R;
import com.sohel.themelookassignment.interfaces.QuantityChangeListner;

public class Quantity {
    int counter=1;
    Activity activity;
    LinearLayout linearLayout;
    ImageView increaseButton;
    TextView counterTv,decreseButton;

    public Quantity(Activity activity, LinearLayout linearLayout,int quantity) {
        this.counter=quantity;
        this.activity = activity;
        this.linearLayout = linearLayout;
        increaseButton=linearLayout.findViewById(R.id.q_increaseTv);
        counterTv=linearLayout.findViewById(R.id.q_counterTv);
        decreseButton=linearLayout.findViewById(R.id.q_decreseTv);
    }

    public void init(QuantityChangeListner quantityChangeListner){
        counterTv.setText(""+counter);
        increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                notifyCounterChange();
                quantityChangeListner.onIncrease(counter);
            }
        });
        decreseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counter>1){
                    counter--;
                    notifyCounterChange();
                    quantityChangeListner.onDecrease(counter);
                }
            }
        });


    }
    public int getQuantity(){
        return counter;
    }
    public void notifyCounterChange(){
        counterTv.setText(""+counter);
    }

}
