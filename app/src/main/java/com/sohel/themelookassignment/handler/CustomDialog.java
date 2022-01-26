package com.sohel.themelookassignment.handler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.sohel.themelookassignment.R;
import com.sohel.themelookassignment.api.ApiRef;
import com.sohel.themelookassignment.model.Brand;
import com.sohel.themelookassignment.model.Category;

public class CustomDialog {
    public static void showBrandDialog(Activity context){

        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        View view=context.getLayoutInflater().inflate(R.layout.add_brand_dialog,null);
        builder.setView(view);
        TextView titleTv=view.findViewById(R.id.a_bd_DialogTitle);
        EditText brandNameEt=view.findViewById(R.id.a_bd_brandNameEt);
        Button addBrand=view.findViewById(R.id.a_bd_AddBrandButton);

        final AlertDialog dialog=builder.create();
        dialog.show();
        addBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String brandName=brandNameEt.getText().toString();
               if(brandName.isEmpty()){
                   brandNameEt.setError("Enter Brand Name.");
                   brandNameEt.requestFocus();
               }else{
                   addBrand.setText("Adding New Brand...");
                   addBrand.setEnabled(false);
                   String brandId= ApiRef.getBrandRef().push().getKey();
                   Brand brand=new Brand(brandId,brandName);
                   ApiRef.getBrandRef()
                           .child(brandId)
                           .setValue(brand)
                           .addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull Task<Void> task) {
                                   if(task.isSuccessful()){
                                       dialog.dismiss();
                                       Toast.makeText(context, "Brand Added Successful.", Toast.LENGTH_SHORT).show();
                                   }else{
                                       addBrand.setText("Add Brand");
                                       addBrand.setEnabled(true);
                                       Toast.makeText(context, "Brand Add Failed.", Toast.LENGTH_SHORT).show();
                                   }
                               }
                           });
               }
            }
        });
    }
    public static void showCategoryDialog(Activity context){

        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        View view=context.getLayoutInflater().inflate(R.layout.add_brand_dialog,null);
        builder.setView(view);
        TextView titleTv=view.findViewById(R.id.a_bd_DialogTitle);
        EditText brandNameEt=view.findViewById(R.id.a_bd_brandNameEt);
        Button addBrand=view.findViewById(R.id.a_bd_AddBrandButton);

        titleTv.setText("Add New Category.");
        brandNameEt.setHint("Enter Category Name.");
        addBrand.setText("Add Category");


        final AlertDialog dialog=builder.create();
        dialog.show();
        addBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String brandName=brandNameEt.getText().toString();
               if(brandName.isEmpty()){
                   brandNameEt.setError("Enter Category Name.");
                   brandNameEt.requestFocus();
               }else{
                   addBrand.setText("Adding New Category...");
                   addBrand.setEnabled(false);
                   String brandId=ApiRef.getBrandRef().push().getKey();
                   Category brand=new Category(brandId,brandName);
                   ApiRef.getCategoryRef()
                           .child(brandId)
                           .setValue(brand)
                           .addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull Task<Void> task) {
                                   if(task.isSuccessful()){
                                       dialog.dismiss();
                                       Toast.makeText(context, "Category Added Successful.", Toast.LENGTH_SHORT).show();
                                   }else{
                                       addBrand.setText("Add Brand");
                                       addBrand.setEnabled(true);
                                       Toast.makeText(context, "Category Add Failed.", Toast.LENGTH_SHORT).show();
                                   }
                               }
                           });
               }
            }
        });
    }
}
