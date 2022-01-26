package com.sohel.themelookassignment.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.sohel.themelookassignment.R;
import com.sohel.themelookassignment.model.ImageModel;
import com.sohel.themelookassignment.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder> {

    private Context context;
    private List<Product> productList;
    private  OnItemClickListner listner;
    private boolean isSeller=false;

    public ProductListAdapter(Context context, List<Product> productList,@Nullable boolean isSeller) {
        this.context = context;
        this.productList = productList;
        this.isSeller=isSeller;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_card,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Product product=productList.get(position);

        if(product.getProductName().length()>15){

            holder.productTitleTv.setText(""+product.getProductName().substring(0,14)+"..");
        }else{
            holder.productTitleTv.setText(""+product.getProductName());
        }

        if(product.getProductPrice().size()>1){
            holder.productPriceTv.setText("\u09F3"+Product.getMinimumPrice(product.getProductPrice())+"-"+Product.getMaximumPrice(product.getProductPrice()));
        }else{
            holder.productPriceTv.setText("\u09F3"+product.getProductPrice().get(0).getPrice());
        }




        List<ImageModel> imageList=product.getProductImages();
        if(imageList!=null){
            Picasso.get().load(imageList.get(0).getImageUrl()).placeholder(R.drawable.sari).into(holder.productImageView);
        }

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView productImageView;
        TextView productTitleTv,productPriceTv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            productImageView=itemView.findViewById(R.id.p_i_productImageViewId);
            productTitleTv=itemView.findViewById(R.id.p_i_productTitleTv);
            productPriceTv=itemView.findViewById(R.id.p_i_productPriceTv);

            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            if(listner!=null){
                int position=getAdapterPosition();
                if(position!= RecyclerView.NO_POSITION){
                    listner.onItemClick(position);
                }
            }
        }
    }
    public interface  OnItemClickListner{
        void onItemClick(int position);
    }

    public void setOnItemClickListner(OnItemClickListner listner){
        this.listner=listner;
    }

}
