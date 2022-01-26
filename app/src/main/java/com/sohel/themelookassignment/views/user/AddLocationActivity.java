package com.sohel.themelookassignment.views.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.sohel.themelookassignment.R;
import com.sohel.themelookassignment.api.ApiRef;
import com.sohel.themelookassignment.handler.AppBar;
import com.sohel.themelookassignment.localdb.UserDb;
import com.sohel.themelookassignment.model.User;
import com.sohel.themelookassignment.model.UserLocation;

import java.util.HashMap;

public class AddLocationActivity extends AppCompatActivity {


    private Button saveInfoButton;
    private EditText nameEt,emailEt,phoneEt,divisionEt,districtEt,villageEt;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private UserDb userDb;
    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
        init();

        User currentUser=userDb.getUserData();
        if(currentUser.getLocation()!=null){
            UserLocation location= currentUser.getLocation();

            if(location.getEmail()!=null){
                emailEt.setText(location.getEmail());
            }
            if(location.getPhone()!=null){
                phoneEt.setText(location.getPhone());
            }
            if(location.getFullName()!=null){
                nameEt.setText(location.getFullName());
            }

            if(location.getDistrict()!=null){
                districtEt.setText(location.getDistrict());
            }
            if(location.getDivision()!=null){
                divisionEt.setText(location.getDivision());
            }
            if(location.getVillage()!=null){
                villageEt.setText(location.getVillage());
            }
        }else{
            nameEt.setText(""+currentUser.getName());
            phoneEt.setText(""+currentUser.getPhone());
            emailEt.setText(""+currentUser.getEmail());
        }
        saveInfoButton.setOnClickListener(v -> {
            String name=nameEt.getText().toString();
            String email=emailEt.getText().toString();
            String phone=phoneEt.getText().toString();
            String district=districtEt.getText().toString();
            String division=divisionEt.getText().toString();
            String village=villageEt.getText().toString();

            if(name.isEmpty()){
                nameEt.setError("Enter Your Password.");
                nameEt.requestFocus();
            }else if(email.isEmpty()){
                emailEt.setError("Enter Your Email");
                emailEt.requestFocus();
            }else if(phone.isEmpty()){
                phoneEt.setError("Enter Your Phone");
                phoneEt.requestFocus();
            }else if(division.isEmpty()){
                divisionEt.setError("Enter Division");
                divisionEt.requestFocus();
            }else if(district.isEmpty()){
                districtEt.setError("Enter District");
                districtEt.requestFocus();
            }else if(village.isEmpty()){
                villageEt.setError("Enter Village Name.");
                villageEt.requestFocus();
            }else{
                saveInfo(name,email,phone,division,district,village);
            }




        });
    }

    private void init(){
        userDb=new UserDb(AddLocationActivity.this);
        progressDialog=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();

        nameEt=findViewById(R.id.a_l_nameEt);
        phoneEt=findViewById(R.id.a_l_phoneEt);
        emailEt=findViewById(R.id.a_l_emailEt);
        districtEt=findViewById(R.id.a_l_districtEt);
        divisionEt=findViewById(R.id.a_l_divisionEt);
        villageEt=findViewById(R.id.a_l_villageEt);
        saveInfoButton=findViewById(R.id.a_l_saveInfoButton);

        toolbar=findViewById(R.id.appBarId);
        AppBar.setUpAppBar(AddLocationActivity.this,toolbar,"Set Current Location");

    }


    private void saveInfo(String name, String email, String phone, String division, String district, String village) {
        saveInfoButton.setEnabled(false);
        saveInfoButton.setText("Saving User Info......");
        User currentUser=userDb.getUserData();
        UserLocation location=new UserLocation(name,email,phone,division,district,village);
        currentUser.setLocation(location);
        HashMap<String,Object> userUpdateMap=new HashMap<>();
        userUpdateMap.put("location",location);
        ApiRef.getUserRef()
                .child(userDb.getUserData().getUserId())
                .updateChildren(userUpdateMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        saveInfoButton.setEnabled(true);
                        saveInfoButton.setText("Save Info");
                        if(task.isSuccessful()){
                            userDb.setUserData(currentUser);
                            finish();
                            Toast.makeText(AddLocationActivity.this, "Successfully Updated.", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(AddLocationActivity.this, "Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}