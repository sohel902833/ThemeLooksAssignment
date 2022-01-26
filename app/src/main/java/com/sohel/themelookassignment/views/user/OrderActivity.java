package com.sohel.themelookassignment.views.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.sohel.themelookassignment.R;
import com.sohel.themelookassignment.api.ApiRef;
import com.sohel.themelookassignment.handler.AppBar;
import com.sohel.themelookassignment.localdb.CartDb;
import com.sohel.themelookassignment.localdb.UserDb;
import com.sohel.themelookassignment.model.Order;
import com.sohel.themelookassignment.model.Product;
import com.sohel.themelookassignment.model.User;
import com.sohel.themelookassignment.model.UserLocation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button addLocationButton,submitOrderButton;
    private TextView detailsTv;
    UserDb userDb;
    CartDb cartDb;
    String orderNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        init();
        User currentUser=userDb.getUserData();


        addLocationButton.setOnClickListener(v -> {
                startActivity(new Intent(OrderActivity.this,AddLocationActivity.class));
        });
        submitOrderButton.setOnClickListener(v -> {
                if(currentUser.getLocation()!=null){
                    submitOrder();
                }else{
                    startActivity(new Intent(OrderActivity.this,AddLocationActivity.class));
                }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();

        User currentUser=userDb.getUserData();

        if(currentUser.getLocation()==null){
            submitOrderButton.setText("Add Location First");
        }else{
            submitOrderButton.setText("Submit Order");
            addLocationButton.setText("Update Location");
        }

        UserLocation location= currentUser.getLocation();
        orderNo=String.valueOf(System.currentTimeMillis())+currentUser.getEmail().substring(2,6).toLowerCase(Locale.ROOT).trim();
        if(location!=null){
            detailsTv.setText(
                    " Total Product: "+cartDb.getCartSize()+"\n" +
                            " Total Price: "+cartDb.getTotalPrice()+"\n\n"+
                            "Address: \n"+
                            "Full Name: "+location.getFullName()+"\n"+
                            "Phone: "+location.getPhone()+"\n"+
                            "Email: "+location.getEmail()+"\n"+
                            "Division: "+location.getDivision()+"\n"+
                            "District: "+location.getDistrict()+"\n"+
                            "Village: "+location.getVillage()+"\n\n\n"+
                            "Order No:  "+orderNo);
        }else{
            detailsTv.setText(" Total Product: "+cartDb.getCartSize()+"\n" +
                    " Total Price: "+cartDb.getTotalPrice()+"\n\n"+
                    "Address: \n");
        }



    }
    private void init(){
        userDb=new UserDb(this);
        cartDb=new CartDb(this);
        toolbar=findViewById(R.id.appBarId);
        addLocationButton=findViewById(R.id.o_addCurrentLocationButton);
        submitOrderButton=findViewById(R.id.o_submitOrderButton);
        detailsTv=findViewById(R.id.o_detailsTvId);
        AppBar.setUpAppBar(OrderActivity.this,toolbar,"Checkout.");
    }
    private void submitOrder(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss", Locale.getDefault());
        String currentDateAndTime = sdf.format(new Date());

        submitOrderButton.setEnabled(false);
        submitOrderButton.setText("Submitting Order....");
        List<Product> orderList=cartDb.getCardProduct();
        User currentUser=userDb.getUserData();
        for(int i=0; i<orderList.size(); i++){
            Product product=orderList.get(i);
            String orderId= ApiRef.getOrderRef().push().getKey()+"_"+System.currentTimeMillis();
            Order order=new Order(orderId,currentUser.getUserId(),product.getShopId(),product.getProductId(),orderNo,cartDb.getTotalPrice(),cartDb.getCartSize(),currentUser.getName(),currentUser.getPhone(),currentDateAndTime,Order.ORDER_STEP_1);
            ApiRef.getOrderRef()
                    .child(orderId)
                    .setValue(order).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){

                    }else{
                        Toast.makeText(OrderActivity.this, "Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            if(cartDb.getCartSize()==(i+1)){
                submitOrderButton.setEnabled(true);
                Toast.makeText(OrderActivity.this, "Order Submit Complete.", Toast.LENGTH_SHORT).show();
                cartDb.clearCart();
                startActivity(new Intent(OrderActivity.this,MainActivity.class));
                finish();
            }

        }


    }
}