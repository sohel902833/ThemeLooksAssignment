package com.sohel.themelookassignment.localdb;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sohel.themelookassignment.model.Product;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CartDb {
    private Activity activity;
    public CartDb(Activity activity) {
        this.activity = activity;
    }

    public void addProductToCart(Product product) {
        SharedPreferences sharedPreferences=activity.getSharedPreferences("cartDb", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        product.setQuantity(1);
        List<Product> prevProductList=getCardProduct();
        if(prevProductList==null){
            prevProductList=new ArrayList<>();
            prevProductList.add(product);
        }else{
            if(!productExistsOnCart(product.getProductId())){
                prevProductList.add(product);
            }
        }
        Gson gson = new Gson();
        String json = gson.toJson(prevProductList);
        editor.putString("products", json);
        editor.commit();
    }

    public void addProductToCart(List<Product> productList){
        SharedPreferences sharedPreferences=activity.getSharedPreferences("cartDb", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(productList);
        editor.putString("products", json);
        editor.commit();
    }
    public List<Product>  getCardProduct(){
        List<Product> cartList=null;
        SharedPreferences sharedPreferences=activity.getSharedPreferences("cartDb", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String serializedObject = sharedPreferences.getString("products", null);
        Type type= new TypeToken<List<Product>>(){}.getType();
        cartList=gson.fromJson(serializedObject,type);

        return  cartList;
    }
    public void removeProduct(String productId){
        List<Product> productList=getCardProduct();
        if(productList!=null){
            for(int i=0; i<productList.size(); i++){
                Product product=productList.get(i);
                if(product.getProductId().equals(productId)){
                    productList.remove(product);
                    break;
                }
            }
            clearCart();
            addProductToCart(productList);
        }




    }
    public boolean productExistsOnCart(String productId){
        boolean exists=false;
        List<Product> productList=getCardProduct();

        if(productList!=null){
            for(int i=0; i<productList.size(); i++){
                Product product=productList.get(i);
                if(product.getProductId().equals(productId)){
                    exists=true;
                    break;
                }
            }
        }
        return exists;

    }
    public int getCartSize(){
        List<Product> productList=getCardProduct();
        if(productList==null){
            return  0;
        }else{
            return productList.size();
        }
    }
    public void clearCart(){
        SharedPreferences userShared = activity.getSharedPreferences("cartDb", Context.MODE_PRIVATE);
        userShared.edit().clear().apply();
    }
    public double getTotalPrice(){
        List<Product> productList=getCardProduct();
        double totalPrice=0;
        if(productList!=null){
            for(int i=0; i<productList.size(); i++){
                Product product=productList.get(i);
               totalPrice=totalPrice+(product.getProductPrice().getBlPrice()*product.getQuantity());
            }

            return  totalPrice;
        }else
            return 0;
    }

    public void updateProduct(Product product){
        List<Product> productList=getCardProduct();
        if(productList!=null){
            for(int i=0; i<productList.size(); i++){
                Product currentProduct=productList.get(i);
                if(currentProduct.getProductId().equals(product.getProductId())){
                    productList.set(i,product);
                    break;
                }
            }
            clearCart();
            addProductToCart(productList);
        }

    }



}
