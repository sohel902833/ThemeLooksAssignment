package com.sohel.themelookassignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sohel.themelookassignment.R;
import com.sohel.themelookassignment.model.ImageModel;
import com.sohel.themelookassignment.model.ProductPrice;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ColorSizeRecyclerAdapter extends RecyclerView.Adapter<ColorSizeRecyclerAdapter.MyViewHolder> {

    private Context context;
    List<ProductPrice> priceList=new ArrayList<>();
    private ProductListAdapter.OnItemClickListner listner;


    public ColorSizeRecyclerAdapter(Context context, List<ProductPrice> priceList) {
        this.context = context;
        this.priceList = priceList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.color_size_item_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ProductPrice productPrice=priceList.get(position);
         holder.textView.setText(""+productPrice.getColor()+"&\n"+productPrice.getSize());
    }

    @Override
    public int getItemCount() {
        return priceList.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {
       TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.icz_TextViewId);
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

    public void setOnItemClickListner(ProductListAdapter.OnItemClickListner listner){
        this.listner=listner;
    }

}
