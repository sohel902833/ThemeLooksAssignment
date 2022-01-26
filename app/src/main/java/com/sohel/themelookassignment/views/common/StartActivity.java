package com.sohel.themelookassignment.views.common;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.sohel.themelookassignment.R;
import com.sohel.themelookassignment.api.ApiRef;
import com.sohel.themelookassignment.localdb.UserDb;
import com.sohel.themelookassignment.model.User;
import com.sohel.themelookassignment.views.seller.SellerHomeActivity;
import com.sohel.themelookassignment.views.user.MainActivity;
import com.sohel.themelookassignment.views.user.UserRegisterActivity;

public class StartActivity extends AppCompatActivity {


    private EditText emailEt,passwordEt;
    private Button loginButton;
    private TextView registerTv;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private UserDb userDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        init();

        loginButton.setOnClickListener(v -> {
            String email=emailEt.getText().toString();
            String password=passwordEt.getText().toString().trim();

             if(email.isEmpty()){
                emailEt.setError("Enter Your Email");
                emailEt.requestFocus();
            }else if(password.isEmpty()){
                passwordEt.setError("Enter Your Password.");
                passwordEt.requestFocus();
            }else{

                loginUser(email,password);
            }




        });


        registerTv.setOnClickListener(v -> {
            startActivity(new Intent(StartActivity.this, UserRegisterActivity.class));
        });




    }

    private  void init(){
        userDb=new UserDb(this);
        mAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        loginButton=findViewById(R.id.u_l_loginButton);
        registerTv=findViewById(R.id.u_l_registerTv);
        emailEt=findViewById(R.id.u_l_emailEt);
        passwordEt=findViewById(R.id.u_l_passwordEt);



    }

    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser()!=null){
            User user=userDb.getUserData();
            if(user.getUserType().equals(User.SIMPLE_USER)){
                sendUserToUserMainActivity();
            }else if(user.getUserType().equals(User.SELLER_USER)){
                sendUserToSellerMainActivity();
            }
        }
    }

    private void loginUser(String email, String password) {
        progressDialog.setMessage("Please Wait.");
        progressDialog.setTitle("Logging...");
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //get current user
                            String userId=mAuth.getCurrentUser().getUid();
                            ApiRef.getUserRef().child(userId)
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                                            if(snapshot.exists()){
                                                progressDialog.dismiss();
                                                User currentUser=snapshot.getValue(User.class);
                                                userDb.setUserData(currentUser);
                                                if(currentUser!=null){
                                                    if(currentUser.getUserType().equals(User.SIMPLE_USER)){
                                                        sendUserToUserMainActivity();
                                                    }else if(currentUser.getUserType().equals(User.SELLER_USER)){
                                                        sendUserToSellerMainActivity();
                                                    }
                                                }

                                            }else{
                                                progressDialog.dismiss();
                                                Toast.makeText(StartActivity.this, "User Not Found.", Toast.LENGTH_SHORT).show();

                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            progressDialog.dismiss();
                                            Toast.makeText(StartActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });



                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(StartActivity.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });


    }
    private void sendUserToUserMainActivity() {
        Intent intent=new Intent(StartActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
   private void sendUserToSellerMainActivity() {
        Intent intent=new Intent(StartActivity.this, SellerHomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

}