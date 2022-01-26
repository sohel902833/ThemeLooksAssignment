package com.sohel.themelookassignment.views.seller;

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
import com.sohel.themelookassignment.R;
import com.sohel.themelookassignment.api.ApiRef;
import com.sohel.themelookassignment.localdb.UserDb;
import com.sohel.themelookassignment.model.User;
import com.sohel.themelookassignment.views.user.UserRegisterActivity;

public class SellerRegisterActivity extends AppCompatActivity {


    private Button registerButton;
    private EditText nameEt,emailEt,passwordEt,phoneEt,shopNameEt;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private TextView createUserAccount;
    private UserDb userDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_register);
        init();

        registerButton.setOnClickListener(v -> {
            String name=nameEt.getText().toString();
            String email=emailEt.getText().toString();
            String password=passwordEt.getText().toString().trim();
            String phone=phoneEt.getText().toString();
            String shopName=shopNameEt.getText().toString();

            if(name.isEmpty()){
                nameEt.setError("Enter Your Password.");
                nameEt.requestFocus();
            }else if(email.isEmpty()){
                emailEt.setError("Enter Your Email");
                emailEt.requestFocus();
            }else if(password.isEmpty()){
                passwordEt.setError("Enter Your Password.");
                passwordEt.requestFocus();
            }else if(phone.isEmpty()){
                phoneEt.setError("Enter Your Phone");
                phoneEt.requestFocus();
            }else if(password.length()<6){
                passwordEt.setError("Password Length Minimum 6 Character Long");
                passwordEt.requestFocus();
            }else if(shopName.isEmpty()){
                shopNameEt.setError("Enter Your Shop Name");
                shopNameEt.requestFocus();
            }else{
                registerUser(name,email,phone,password,shopName);
            }




        });


        createUserAccount.setOnClickListener(v -> {
            startActivity(new Intent(SellerRegisterActivity.this, UserRegisterActivity.class));
        });

    }

    private void init(){
        userDb=new UserDb(SellerRegisterActivity.this);
        registerButton=findViewById(R.id.s_r_RegisterButtonId);
        nameEt=findViewById(R.id.s_r_emailEt);
        phoneEt=findViewById(R.id.s_r_phoneEt);
        emailEt=findViewById(R.id.s_r_emailEt);
        passwordEt=findViewById(R.id.s_r_passwordEt);
        shopNameEt=findViewById(R.id.s_r_shopNameEt);
        createUserAccount=findViewById(R.id.s_r_createUserAccount);
        progressDialog=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();

    }


    private void registerUser(String name, String email, String phone, String password,String shopName) {
        progressDialog.setMessage("Please Wait.");
        progressDialog.setTitle("We Are Creating Your Account.");
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String userId=mAuth.getCurrentUser().getUid();
                    User user=new User(userId,name,email,password,phone,0,User.SELLER_USER,shopName,null);
                    ApiRef.getUserRef()
                            .child(userId)
                            .setValue(user)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressDialog.dismiss();
                                    if(task.isSuccessful()){
                                        userDb.setUserData(user);
                                        Toast.makeText(SellerRegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                        sendUserToSellerMainActivity();
                                    }else{
                                        Toast.makeText(SellerRegisterActivity.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(SellerRegisterActivity.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void sendUserToSellerMainActivity() {
        Intent intent=new Intent(SellerRegisterActivity.this, SellerHomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}