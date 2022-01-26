 package com.sohel.themelookassignment.views.seller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.sohel.themelookassignment.R;
import com.sohel.themelookassignment.api.ApiRef;
import com.sohel.themelookassignment.handler.AppBar;
import com.sohel.themelookassignment.model.User;
import com.sohel.themelookassignment.model.UserLocation;


 public class SellerUserDetailsActivity extends AppCompatActivity {

     private Toolbar toolbar;
     private TextView nameTv,detailsTv;
     private String userId="";
     private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_user_details);
        userId=getIntent().getStringExtra("userId");
        init();



    }
    private void init(){
        toolbar=findViewById(R.id.appBarId);
        nameTv=findViewById(R.id.s_u_nameTv);
        detailsTv=findViewById(R.id.s_u_detailsTv);
        progressBar=findViewById(R.id.progressbarId);
    }

     @Override
     protected void onStart() {
         super.onStart();

         ApiRef.getUserRef()
                 .child(userId)
                 .addListenerForSingleValueEvent(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull DataSnapshot snapshot) {
                         if(snapshot.exists()){

                             progressBar.setVisibility(View.GONE);
                             User user=snapshot.getValue(User.class);
                             UserLocation location=user.getLocation();

                             AppBar.setUpAppBar(SellerUserDetailsActivity.this,toolbar,user.getName());

                             nameTv.setText(user.getName());
                             detailsTv.setText(
                                             "Address: \n\n"+
                                             "Full Name: "+location.getFullName()+"\n"+
                                             "Phone: "+location.getPhone()+"\n"+
                                             "Email: "+location.getEmail()+"\n"+
                                             "Division: "+location.getDivision()+"\n"+
                                             "District: "+location.getDistrict()+"\n"+
                                             "Village: "+location.getVillage()+"\n\n\n"
                             );


                         }else{
                             progressBar.setVisibility(View.GONE);
                             Toast.makeText(SellerUserDetailsActivity.this, "User Not Found.", Toast.LENGTH_SHORT).show();
                         }
                     }

                     @Override
                     public void onCancelled(@NonNull DatabaseError error) {
                          progressBar.setVisibility(View.GONE);
                     }
                 });


     }
 }