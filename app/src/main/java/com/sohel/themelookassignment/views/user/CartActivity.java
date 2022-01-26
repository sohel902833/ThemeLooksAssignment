package com.sohel.themelookassignment.views.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sohel.themelookassignment.R;
import com.sohel.themelookassignment.adapter.CartAdapter;
import com.sohel.themelookassignment.handler.AppBar;
import com.sohel.themelookassignment.localdb.CartDb;
import com.sohel.themelookassignment.localdb.UserDb;
import com.sohel.themelookassignment.model.Product;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private CartDb cartDb;
    private UserDb userDb;
    private RecyclerView cartRecyclerView;
    private Toolbar toolbar;
    private CartAdapter cartAdapter;
    private List<Product> cartList=new ArrayList<>();
    private TextView totalPriceTv;
    private Button checkOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        init();
      setUpCartRecyclerView();
      cartAdapter=new CartAdapter(this,cartList);
      cartRecyclerView.setAdapter(cartAdapter);
      cartAdapter.setOnItemClickListner(new CartAdapter.OnItemClickListner() {
          @Override
          public void onItemClick(int position,int quantity) {
              Product product=cartList.get(position);
              Intent intent=new Intent(CartActivity.this,ProductDetailsActivity.class);
              intent.putExtra("productId",product.getProductId());
              startActivity(intent);
           }

          @Override
          public void onRemoveFromCart(int position) {
             Product product=cartList.get(position);
             cartDb.removeProduct(product.getProductId());
             cartList.remove(position);
             cartAdapter.notifyDataSetChanged();
              setAppBarText();
          }

          @Override
          public void onQuantityIncrease(int position, int quantity) {
              Product product=cartList.get(position);
              product.setQuantity(quantity);
              cartDb.updateProduct(product);
              cartAdapter.notifyDataSetChanged();
              setAppBarText();
         }

          @Override
          public void onQuantityDecrease(int position, int quantity) {
             Product product=cartList.get(position);
              product.setQuantity(quantity);
              cartDb.updateProduct(product);
              cartAdapter.notifyDataSetChanged();
              setAppBarText();
         }
      });

      checkOutButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if(cartList.size()>0){
                startActivity(new Intent(CartActivity.this,OrderActivity.class));
              }else{
                  Toast.makeText(CartActivity.this, "No Product Found.", Toast.LENGTH_SHORT).show();
              }
          }
      });


    }
    private void init(){
        cartDb=new CartDb(this);
        userDb=new UserDb(this);
        cartRecyclerView=findViewById(R.id.cartListRecyclerViewId);
        totalPriceTv=findViewById(R.id.c_i_totalPriceTv);
        checkOutButton=findViewById(R.id.c_i_checkoutButtonId);
        toolbar=findViewById(R.id.appBarId);
        AppBar.setUpAppBar(this,toolbar,"Cart List");
        setAppBarText();
        cartRecyclerView.setHasFixedSize(true);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    private void setAppBarText(){
       totalPriceTv.setText("Total Price: "+cartDb.getTotalPrice()+"");
    }
    private  void setUpCartRecyclerView(){
        cartList.clear();
        cartList=cartDb.getCardProduct();
    }
}