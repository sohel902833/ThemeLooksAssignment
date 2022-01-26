package com.sohel.themelookassignment.views.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.sohel.themelookassignment.R;
import com.sohel.themelookassignment.adapter.ProductListAdapter;
import com.sohel.themelookassignment.api.ApiRef;
import com.sohel.themelookassignment.localdb.UserDb;
import com.sohel.themelookassignment.model.Product;
import com.sohel.themelookassignment.model.User;
import com.sohel.themelookassignment.views.common.StartActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private UserDb userDb;
    private RecyclerView productRecyclerView;
    private ProgressBar progressBar;
    private Toolbar toolbar;

    private ProductListAdapter productListAdapter;
    private List<Product> productList=new ArrayList<>();



    //navigation drawer

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();


        productListAdapter=new ProductListAdapter(this,productList,false);
        productRecyclerView.setAdapter(productListAdapter);

        productListAdapter.setOnItemClickListner(new ProductListAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(int position) {
                Product product=productList.get(position);
                Intent intent=new Intent(MainActivity.this,ProductDetails2Activity.class);
                intent.putExtra("productId",product.getProductId());
                startActivity(intent);
             }

        });





    }

    @Override
    protected void onStart() {
        super.onStart();

        ApiRef.getProductRef()
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            productList.clear();
                            for(DataSnapshot snapshot1:snapshot.getChildren()){
                                Product product=snapshot1.getValue(Product.class);
                                productList.add(product);
                            }
                            productListAdapter.notifyDataSetChanged();
                            showRecyclerView();
                        }else{
                            showRecyclerView();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                     showRecyclerView();

                    }
                });
        getUserData();
    }
    private void init(){
        userDb=new UserDb(this);
        mAuth=FirebaseAuth.getInstance();


        productRecyclerView=findViewById(R.id.main_ProductRecyclerViewId);
        progressBar=findViewById(R.id.m_progressBarId);

        //setup toolbar
         toolbar = findViewById(R.id.appBarId);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);







        productRecyclerView.setHasFixedSize(true);
        productRecyclerView.setLayoutManager(new GridLayoutManager(this,2));

        progressBar.setVisibility(View.VISIBLE);
        productRecyclerView.setVisibility(View.GONE);





    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.logoutMenuItem){
            logoutUser();
        }

        return super.onOptionsItemSelected(item);
    }
    private void logoutUser() {
        mAuth.signOut();
        userDb.removeUserData();
        Intent intent=new Intent(MainActivity.this, StartActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
    private void showRecyclerView(){
        productRecyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }
    private void getUserData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                ApiRef.getUserRef()
                        .child(userDb.getUserData().getUserId())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    User currentUser=snapshot.getValue(User.class);
                                    userDb.setUserData(currentUser);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
            }
        }).start();
    }


}