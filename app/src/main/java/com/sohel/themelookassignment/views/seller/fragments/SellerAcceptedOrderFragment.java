package com.sohel.themelookassignment.views.seller.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sohel.themelookassignment.R;
import com.sohel.themelookassignment.adapter.SellerOrderListAdapter;
import com.sohel.themelookassignment.api.ApiRef;
import com.sohel.themelookassignment.localdb.UserDb;
import com.sohel.themelookassignment.model.Order;
import com.sohel.themelookassignment.views.seller.SellerUserDetailsActivity;
import com.sohel.themelookassignment.views.user.ProductDetailsActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SellerAcceptedOrderFragment extends Fragment {


    public SellerAcceptedOrderFragment() {
        // Required empty public constructor
    }
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private SellerOrderListAdapter orderListAdapter;
    private List<Order> orderList=new ArrayList<>();
    private UserDb userDb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_seller_accepted_order, container, false);
        recyclerView=view.findViewById(R.id.orderRecyclerview);
        userDb=new UserDb(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        progressBar=view.findViewById(R.id.orderProgressBar);
        orderListAdapter=new SellerOrderListAdapter(getContext(),orderList);
        recyclerView.setAdapter(orderListAdapter);
        orderListAdapter.setOnItemClickListener(new SellerOrderListAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(int position) {
                Order product=orderList.get(position);
                Intent intent=new Intent(getContext(), ProductDetailsActivity.class);
                intent.putExtra("productId",product.getProductId());
                startActivity(intent);
            }
            @Override
            public void onAccept(int position) {


            }

            @Override
            public void onDelivered(int position) {
                Order order=orderList.get(position);
                progressBar.setVisibility(View.VISIBLE);
                HashMap<String,Object> orderMap=new HashMap<>();
                orderMap.put("state",Order.ORDER_STEP_3);
                ApiRef.getOrderRef().child(order.getOrderId())
                        .updateChildren(orderMap)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressBar.setVisibility(View.GONE);
                                if(task.isSuccessful()){
                                    Toast.makeText(getContext(), "Order Delivered.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

            @Override
            public void onViewUserDetails(int position) {
                Order product=orderList.get(position);
                Intent intent=new Intent(getContext(), SellerUserDetailsActivity.class);
                intent.putExtra("userId",product.getUserId());
                startActivity(intent);
            }
        });

        return  view;
    }
    @Override
    public void onStart() {
        super.onStart();

        Query query = ApiRef.getOrderRef().orderByChild("shopId")
                .equalTo(userDb.getUserData().getUserId());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressBar.setVisibility(View.GONE);
                if(snapshot.exists()){
                    orderList.clear();
                    for(DataSnapshot snapshot1:snapshot.getChildren()){
                        Order order=snapshot1.getValue(Order.class);
                        if(order.getState().equals(Order.ORDER_STEP_2)){
                            orderList.add(order);
                        }

                    }
                    orderListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
            }
        });



    }
}