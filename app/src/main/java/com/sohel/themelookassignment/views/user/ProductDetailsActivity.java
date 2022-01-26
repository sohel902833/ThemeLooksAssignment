package com.sohel.themelookassignment.views.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.sohel.themelookassignment.R;
import com.sohel.themelookassignment.adapter.ImageSliderAdapter2;
import com.sohel.themelookassignment.adapter.ReviewAdapter;
import com.sohel.themelookassignment.adapter.SpinnerArrayAdapter;
import com.sohel.themelookassignment.api.ApiRef;
import com.sohel.themelookassignment.handler.CustomCart;
import com.sohel.themelookassignment.handler.Quantity;
import com.sohel.themelookassignment.interfaces.QuantityChangeListner;
import com.sohel.themelookassignment.localdb.CartDb;
import com.sohel.themelookassignment.localdb.UserDb;
import com.sohel.themelookassignment.model.Category;
import com.sohel.themelookassignment.model.Product;
import com.sohel.themelookassignment.model.RatingModel;
import com.sohel.themelookassignment.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity {

    private LinearLayout mainLayout;
    private ProgressBar progressBar,reviewProgressBar;
    private RecyclerView sliderRecyclerview,reviewRecyclerView;
    private TextView productPriceTv,productTitleTv,productDescriptionTv,submitRatingTv,ratingTv;
    private CardView submitRatingCard;
    private String productId;
    ImageSliderAdapter2 sliderAdapter;
    private RatingBar productRatingBar,submitRatingBar;
    private EditText ratingCommentEt;
    private Button submitRatingButton;
    private UserDb userDb;
    private ReviewAdapter reviewAdapter;
    private List<RatingModel> reviewList=new ArrayList<>();
    private CustomCart customCart;
    private CartDb cartDb;
    private RelativeLayout cartLayout;
    LinearLayout quantityLayout;
    private TextView addToCartTv;
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






       submitRatingTv.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               toggleSubmitRating();
           }
       });
       submitRatingButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String comment=ratingCommentEt.getText().toString();
               if(comment.isEmpty()){
                   ratingCommentEt.setError("Please Write Your Experience.");
                   ratingCommentEt.requestFocus();
               }else{
                   submitRating(comment);
               }
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
                            getProductReview();


                            Quantity quantity=new Quantity(ProductDetailsActivity.this,quantityLayout,product.getQuantity());
                            quantity.init(new QuantityChangeListner() {
                                @Override
                                public void onIncrease(int quantity) {
                                    product.setQuantity(quantity);
                                    cartDb.updateProduct(product);
                                }
                                @Override
                                public void onDecrease(int quantity) {
                                    product.setQuantity(quantity);
                                    cartDb.updateProduct(product);
                                }
                            });
                            addToCartTv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    product.setQuantity(quantity.getQuantity());
                                    cartDb.addProductToCart(product);
                                    customCart.notifyCartChanged();
                                }
                            });



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

        userDb=new UserDb(this);
        cartDb=new CartDb(this);

        //finding cart layout
        cartLayout=findViewById(R.id.main_CartItemLayout);
        customCart=new CustomCart(this,cartLayout);
        customCart.init();
        quantityLayout=findViewById(R.id.c_i_quantityItemLayout);
        addToCartTv=findViewById(R.id.addToCartTv);

        productDescriptionTv=findViewById(R.id.p_d_productDescriptionTvId);
        mainLayout=findViewById(R.id.p_d_mainLinearLayoutId);
        progressBar=findViewById(R.id.p_d_progressBarId);
        sliderRecyclerview=findViewById(R.id.p_d_sliderRecyclerViewId);
        productPriceTv=findViewById(R.id.p_d_productPriceTvId);
        productTitleTv=findViewById(R.id.p_d_productTitleTvId);
        submitRatingCard=findViewById(R.id.p_d_submitRatingCard);
        submitRatingTv=findViewById(R.id.p_d_submitRatingTvId);
        productRatingBar=findViewById(R.id.p_d_ratingBarId);
        submitRatingBar=findViewById(R.id.p_d_submitRatingBarId);
        ratingCommentEt=findViewById(R.id.p_d_ratingCommentEt);
        submitRatingButton=findViewById(R.id.p_d_submitRatingButton);
        productColorSpinner=findViewById(R.id.p_d_productColorSpinner);
        productSizeSpinner=findViewById(R.id.p_d_productSizeSpinner);
        ratingTv=findViewById(R.id.p_d_ratingTvId);
        reviewProgressBar=findViewById(R.id.p_d_getReviewProgressBar);
        reviewRecyclerView=findViewById(R.id.p_d_ratingRecyclerViewId);
        reviewRecyclerView.setHasFixedSize(true);
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        reviewRecyclerView.setVisibility(View.GONE);
        sliderRecyclerview.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        sliderRecyclerview.setLayoutManager(layoutManager);
        mainLayout.setVisibility(View.GONE);
    }
    private void setProductPrice(){
        if(currentProduct!=null){
            productPriceTv.setText(""+Product.getExactProductPrice(currentProduct.getProductPrice(),selectedProductColors,selectedProductSize));
        }
    }
    private void submitRating(String comment) {
        User currentUser=userDb.getUserData();
        double rating=submitRatingBar.getRating();
        String ratingId=ApiRef.getRatingRef().push().getKey();
        RatingModel ratingModel=new RatingModel(productId,ratingId,comment,currentUser.getUserId(),currentUser.getName(),rating);
        submitRatingButton.setText("Saving..");
        submitRatingButton.setEnabled(false);
        ApiRef.getRatingRef()
                .child(productId)
                .child(ratingId)
                .setValue(ratingModel)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            submitRatingButton.setText("Submit Rating");
                            submitRatingButton.setEnabled(true);
                            Toast.makeText(ProductDetailsActivity.this, "Thanks For Your Review.", Toast.LENGTH_SHORT).show();
                            toggleSubmitRating();

                            updateProductRating();

                        }else{
                            Toast.makeText(ProductDetailsActivity.this, "Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            submitRatingButton.setText("Submit Rating");
                            submitRatingButton.setEnabled(false);
                        }
                    }
                });

    }
    private void showMainLayout(){
        progressBar.setVisibility(View.GONE);
        mainLayout.setVisibility(View.VISIBLE);
    }
    private void toggleSubmitRating(){
        if(submitRatingCard.getVisibility()==View.VISIBLE){
            submitRatingCard.setVisibility(View.GONE);
        }else{
            submitRatingCard.setVisibility(View.VISIBLE);
        }
    }
    private void updateProductRating(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                ApiRef.getRatingRef()
                        .child(productId)
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    double totalRating=0;
                                    int ratingCount=0;
                                    for(DataSnapshot snapshot1:snapshot.getChildren()){
                                        RatingModel ratingModel=snapshot1.getValue(RatingModel.class);
                                        ratingCount=ratingCount+1;
                                        totalRating=totalRating+ratingModel.getRating();
                                    }


                                    double updatedRating=totalRating/ratingCount;
                                    HashMap<String,Object> updateProductMap=new HashMap<>();
                                    updateProductMap.put("rating",updatedRating);
                                    ApiRef.getProductRef()
                                            .child(productId)
                                            .updateChildren(updateProductMap);

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            productRatingBar.setRating((float)updatedRating);
                                            ratingTv.setText("("+String.format("%.1f",updatedRating)+")");
                                        }
                                    });


                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


            }
        }).start();
    }
    private void getProductReview(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                ApiRef.getRatingRef()
                        .child(productId)
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    reviewList.clear();
                                    for(DataSnapshot snapshot1:snapshot.getChildren()){
                                        RatingModel review=snapshot1.getValue(RatingModel.class);
                                        reviewList.add(review);
                                    }

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            reviewProgressBar.setVisibility(View.GONE);
                                            reviewRecyclerView.setVisibility(View.VISIBLE);
                                            reviewAdapter=new ReviewAdapter(ProductDetailsActivity.this,reviewList);
                                            reviewRecyclerView.setAdapter(reviewAdapter);
                                        }
                                    });


                                }else{
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            reviewProgressBar.setVisibility(View.GONE);
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        reviewProgressBar.setVisibility(View.GONE);
                                    }
                                });
                            }
                        });
            }
        }).start();
    }




}