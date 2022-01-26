package com.sohel.themelookassignment.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.sohel.themelookassignment.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Uri> imageList = new ArrayList();
    public ImageSliderAdapter(Context context, ArrayList<Uri> imageList) {
        this.context = context;
        this.imageList = imageList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
           Picasso.get().load(imageList.get(position)).placeholder(R.drawable.sari).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{
        ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.read_ImageViewid);
        }
    }
}
