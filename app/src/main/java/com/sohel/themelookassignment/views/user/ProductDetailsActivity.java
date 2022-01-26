package com.sohel.themelookassignment.views.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.sohel.themelookassignment.R;
import com.sohel.themelookassignment.adapter.ImageSliderAdapter2;
import com.sohel.themelookassignment.adapter.SpinnerArrayAdapter;
import com.sohel.themelookassignment.api.ApiRef;
import com.sohel.themelookassignment.handler.Quantity;
import com.sohel.themelookassignment.interfaces.QuantityChangeListner;
import com.sohel.themelookassignment.model.Product;

public class ProductDetailsActivity extends AppCompatActivity {

    private LinearLayout mainLayout;
    private ProgressBar progressBar;
    private RecyclerView sliderRecyclerview;
    private TextView productPriceTv,productTitleTv,productDescriptionTv,ratingTv;
    private String productId;
    ImageSliderAdapter2 sliderAdapter;
    private RatingBar productRatingBar;
    private Spinner productColorSpinner,productSizeSpinner;

    String[] productColors={"White","Black"};
    String[] productSizes={"Small","Large"};
    String selectedProductColors="White";
    String selectedProductSize="Small";
    private Product currentProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_product_details);
        init();
       productId=getIntent().getStringExtra("productId");

       productSizeSpinner.setAdapter(new SpinnerArrayAdapter(this,productSizes));
       productColorSpinner.setAdapter(new SpinnerArrayAdapter(this,productColors));

        productSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               selectedProductSize=productSizes[position];
               setProductPrice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });   productColorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               selectedProductColors=productColors[position];
               setProductPrice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });







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
                            currentProduct=product;
                            productTitleTv.setText(""+product.getProductName());
                            setProductPrice();
                            productDescriptionTv.setText(""+product.getProductDescription());
                            productRatingBar.setRating((float) product.getRating());
                            ratingTv.setText("("+String.format("%.1f",product.getRating())+")");
                            showMainLayout();

                             sliderAdapter=new ImageSliderAdapter2(ProductDetailsActivity.this,product.getProductImages());
                             sliderRecyclerview.setAdapter(sliderAdapter);

                        }else{
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(ProductDetailsActivity.this, "No Product Found.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        showMainLayout();
                    }
                });
    }
    private void init(){
        //finding cart layout
        productDescriptionTv=findViewById(R.id.p_d_productDescriptionTvId);
        mainLayout=findViewById(R.id.p_d_mainLinearLayoutId);
        progressBar=findViewById(R.id.p_d_progressBarId);
        sliderRecyclerview=findViewById(R.id.p_d_sliderRecyclerViewId);
        productPriceTv=findViewById(R.id.p_d_productPriceTvId);
        productTitleTv=findViewById(R.id.p_d_productTitleTvId);
        productRatingBar=findViewById(R.id.p_d_ratingBarId);
        productColorSpinner=findViewById(R.id.p_d_productColorSpinner);
        productSizeSpinner=findViewById(R.id.p_d_productSizeSpinner);
        ratingTv=findViewById(R.id.p_d_ratingTvId);

        sliderRecyclerview.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        sliderRecyclerview.setLayoutManager(layoutManager);
        mainLayout.setVisibility(View.GONE);
    }
    private void setProductPrice(){
        if(currentProduct!=null){
            //productPriceTv.setText(""+Product.getExactProductPrice(currentProduct.getProductPrice(),selectedProductColors,selectedProductSize));
        }
    }
    private void showMainLayout(){
        progressBar.setVisibility(View.GONE);
        mainLayout.setVisibility(View.VISIBLE);
    }
}