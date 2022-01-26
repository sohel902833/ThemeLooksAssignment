package com.sohel.themelookassignment.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.sohel.themelookassignment.R;
import com.sohel.themelookassignment.handler.Quantity;
import com.sohel.themelookassignment.interfaces.QuantityChangeListner;
import com.sohel.themelookassignment.model.ImageModel;
import com.sohel.themelookassignment.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class
CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    private Activity context;
    private List<Product> productList;
    private  OnItemClickListner listner;

    public CartAdapter(Activity context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Product product=productList.get(position);
        holder.productTitleTv.setText(""+product.getProductName());
        holder.productPriceTv.setText(""+product.getProductPrice().getWlPrice());
        holder.quantityTv.setText("Q: "+product.getQuantity());
        List<ImageModel> imageList=product.getProductImages();
        if(imageList!=null){
            Picasso.get().load(imageList.get(0).getImageUrl()).placeholder(R.drawable.sari).into(holder.productImageView);
        }

        Quantity quantity=new Quantity(context, holder.quantityLayout,product.getQuantity());
        quantity.init(new QuantityChangeListner() {
            @Override
            public void onIncrease(int quantity) {
                if(listner!=null){
                    listner.onQuantityIncrease(holder.getAdapterPosition(),quantity);
                }
            }

            @Override
            public void onDecrease(int quantity) {
                if(listner!=null){
                    listner.onQuantityDecrease(holder.getAdapterPosition(),quantity);
                }
            }
        });

        holder.clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listner!=null){
                    listner.onRemoveFromCart(holder.getAdapterPosition());
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listner!=null){
                    listner.onItemClick(holder.getAdapterPosition(),quantity.getQuantity());
                }
            }
        });




    }
    @Override
    public int getItemCount() {
        if(productList!=null){

            return productList.size();
        }else{
            return  0;
        }
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder {
        ImageView productImageView;
        TextView productTitleTv,productPriceTv,quantityTv;
        Button clearButton;
        LinearLayout quantityLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            productImageView=itemView.findViewById(R.id.c_i_productImageView);
            productTitleTv=itemView.findViewById(R.id.c_i_productNameTv);
            productPriceTv=itemView.findViewById(R.id.c_i_productPriceTv);
            clearButton=itemView.findViewById(R.id.c_i_clearButtonId);
            quantityLayout=itemView.findViewById(R.id.c_i_quantityItemLayout);
            quantityTv=itemView.findViewById(R.id.c_i_productQuantityTv);
        }
    }
    public interface  OnItemClickListner{
        void onItemClick(int position,int quantitiy);
        void onRemoveFromCart(int position);
        void onQuantityIncrease(int position,int quantity);
        void onQuantityDecrease(int position,int quantity);
    }

    public void setOnItemClickListner(OnItemClickListner listner){
        this.listner=listner;
    }

}
