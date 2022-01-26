package com.sohel.themelookassignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sohel.themelookassignment.R;
import com.sohel.themelookassignment.model.ImageModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SmallImageSliderAdapter2 extends RecyclerView.Adapter<SmallImageSliderAdapter2.MyViewHolder> {

    private Context context;
    List<ImageModel> imageList=new ArrayList<>();
    private ProductListAdapter.OnItemClickListner listner;


    public SmallImageSliderAdapter2(Context context, List<ImageModel> imageList) {
        this.context = context;
        this.imageList = imageList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.small_image_item_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
           Picasso.get().load(imageList.get(position).getImageUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.read_ImageViewid);
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
