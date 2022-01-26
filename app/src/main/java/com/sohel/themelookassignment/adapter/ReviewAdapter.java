package com.sohel.themelookassignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.sohel.themelookassignment.R;
import com.sohel.themelookassignment.model.RatingModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder> {

    private Context context;
    private List<RatingModel> ratingList;
    private  OnItemClickListner listner;


    public ReviewAdapter(Context context, List<RatingModel> ratingList) {
        this.context = context;
        this.ratingList = ratingList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        RatingModel review=ratingList.get(position);

        holder.ratingBar.setRating((float) review.getRating());
       holder.userNameTv.setText(""+review.getUserName());
        holder.commentTv.setText(""+review.getComment());

    }

    @Override
    public int getItemCount() {
        return ratingList.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {

        TextView userNameTv,commentTv;
        RatingBar ratingBar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
             ratingBar=itemView.findViewById(R.id.r_i_ratingBar);
             userNameTv=itemView.findViewById(R.id.r_i_userNameTv);
             commentTv=itemView.findViewById(R.id.r_i_commentTv);

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
