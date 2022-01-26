package com.sohel.themelookassignment.views.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.jsibbold.zoomage.ZoomageView;
import com.sohel.themelookassignment.R;
import com.sohel.themelookassignment.adapter.ColorSizeRecyclerAdapter;
import com.sohel.themelookassignment.adapter.ImageSliderAdapter2;
import com.sohel.themelookassignment.adapter.ProductListAdapter;
import com.sohel.themelookassignment.adapter.SmallImageSliderAdapter2;
import com.sohel.themelookassignment.api.ApiRef;
import com.sohel.themelookassignment.model.Product;
import com.sohel.themelookassignment.model.ProductPrice;
import com.squareup.picasso.Picasso;

public class ProductDetails2Activity extends AppCompatActivity {

    private ZoomageView mainImageView;
    private RecyclerView imageListRecyclerView,colorSizeRecyclerView;
    private ProgressBar progressBar;

    private TextView productNameTv,productPriceTv,selectedColorAndSizeTv,productDesTv;

    //<================>
    private String productId="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details2);
        init();
        productId=getIntent().getStringExtra("productId");

    }

    private void init(){
        progressBar=findViewById(R.id.progressBarId);
        mainImageView=findViewById(R.id.pd_productImageViewId);
        imageListRecyclerView=findViewById(R.id.pd_productShortImagesRecyclerViewId);
        colorSizeRecyclerView=findViewById(R.id.pd_sizeAndColorRecyclerViewId);
        productNameTv=findViewById(R.id.pd_productNameTv);
        productPriceTv=findViewById(R.id.pd_productPriceTv);
        selectedColorAndSizeTv=findViewById(R.id.pd_selectedColorAndSizeTv);
        productDesTv=findViewById(R.id.pd_productDescriptionTv);


        imageListRecyclerView.setHasFixedSize(true);
        colorSizeRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        imageListRecyclerView.setLayoutManager(layoutManager);

        LinearLayoutManager layoutManager2=new LinearLayoutManager(this);
        layoutManager2.setOrientation(RecyclerView.HORIZONTAL);
        colorSizeRecyclerView.setLayoutManager(layoutManager2);


    }


    @Override
    protected void onStart() {
        super.onStart();
        ApiRef.getProductRef().child(productId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                           Product product=snapshot.getValue(Product.class);
                            progressBar.setVisibility(View.GONE);
                            updateUi(product);


                        }else{
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(ProductDetails2Activity.this, "No Product Found.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }


    private void updateUi(Product product){
        Picasso.get().load(product.getProductImages().get(0).getImageUrl()).placeholder(R.drawable.sari).into(mainImageView);
        //setup slider Adapter
        SmallImageSliderAdapter2 smallImageSliderAdapter2=new SmallImageSliderAdapter2(this,product.getProductImages());
        imageListRecyclerView.setAdapter(smallImageSliderAdapter2);

        //set product name
        productNameTv.setText(""+product.getProductName());
        //set product description
        productDesTv.setText(""+product.getProductDescription());

        ProductPrice productPrice=product.getProductPrice().get(0);
        productPriceTv.setText("\u09F3"+productPrice.getPrice());
        selectedColorAndSizeTv.setText("Color: "+productPrice.getColor()+", Size: "+productPrice.getSize());

        ColorSizeRecyclerAdapter colorSizeAdapter=new ColorSizeRecyclerAdapter(this,product.getProductPrice());
        colorSizeRecyclerView.setAdapter(colorSizeAdapter);



        smallImageSliderAdapter2.setOnItemClickListner(new ProductListAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(int position) {
                String clickedImageUrl=product.getProductImages().get(position).getImageUrl();
                Picasso.get().load(clickedImageUrl).placeholder(R.drawable.sari).into(mainImageView);

            }
        });

        colorSizeAdapter.setOnItemClickListner(new ProductListAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(int position) {
                ProductPrice sProductPrice=product.getProductPrice().get(position);
                productPriceTv.setText("\u09F3"+sProductPrice.getPrice());
                selectedColorAndSizeTv.setText("Color: "+sProductPrice.getColor()+", Size: "+sProductPrice.getSize());
            }
        });





    }



}