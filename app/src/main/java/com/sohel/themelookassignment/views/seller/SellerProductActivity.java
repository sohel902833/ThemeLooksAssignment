package com.sohel.themelookassignment.views.seller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sohel.themelookassignment.R;
import com.sohel.themelookassignment.adapter.ProductListAdapter;
import com.sohel.themelookassignment.api.ApiRef;
import com.sohel.themelookassignment.handler.AppBar;
import com.sohel.themelookassignment.localdb.UserDb;
import com.sohel.themelookassignment.model.Product;
import com.sohel.themelookassignment.views.user.ProductDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class SellerProductActivity extends AppCompatActivity {

    private UserDb userDb;
    private RecyclerView recyclerView;
    private ProductListAdapter productListAdapter;
    private Toolbar toolbar;
    private List<Product> productList=new ArrayList<>();
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_product);
        init();

        productListAdapter=new ProductListAdapter(this,productList,true);
        recyclerView.setAdapter(productListAdapter);

        productListAdapter.setOnItemClickListner(new ProductListAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(int position) {
                Product product=productList.get(position);
                Intent intent=new Intent(SellerProductActivity.this, ProductDetailsActivity.class);
                intent.putExtra("productId",product.getProductId());
                startActivity(intent);
            }

            @Override
            public void onAddToCart(int position) {

            }

            @Override
            public void onDelete(int position) {
                progressBar.setVisibility(View.VISIBLE);
                Product product=productList.get(position);
                ApiRef.getProductRef().child(product.getProductId())
                        .removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressBar.setVisibility(View.GONE);
                                if(task.isSuccessful()){
                                    Toast.makeText(SellerProductActivity.this, "Product Deleted Successful.", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(SellerProductActivity.this, "Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });



    }
    private  void init(){
        userDb=new UserDb(this);
        recyclerView=findViewById(R.id.myProductRecyclerViewId);
        progressBar=findViewById(R.id.s_p_l_ProgressBarId);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        toolbar=findViewById(R.id.appBarId);
        AppBar.setUpAppBar(this,toolbar,"My Products.");
    }

    @Override
    protected void onStart() {
        super.onStart();

        Query query = ApiRef.getProductRef().orderByChild("shopId")
                .equalTo(userDb.getUserData().getUserId());
        query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            productList.clear();
                            for(DataSnapshot snapshot1:snapshot.getChildren()){
                                Product product=snapshot1.getValue(Product.class);
                                productList.add(product);
                            }
                            productListAdapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                        }else{
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        progressBar.setVisibility(View.GONE);

                    }
                });
    }
}