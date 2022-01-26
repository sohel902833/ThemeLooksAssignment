package com.sohel.themelookassignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.sohel.themelookassignment.R;
import com.sohel.themelookassignment.model.Brand;
import com.sohel.themelookassignment.model.Category;

import java.util.ArrayList;
import java.util.List;

public class SpinnerCustomAdapter extends BaseAdapter {
    private Context context;
    private List<Brand> brandList=new ArrayList<>();
    private List<Category> categoryList=new ArrayList<>();
    private int type;

    public SpinnerCustomAdapter(Context context, List<Brand> brandList, List<Category> categoryList,int type) {
        this.context = context;
        this.brandList = brandList;
        this.categoryList=categoryList;
        this.type=type;
    }

    @Override
    public int getCount() {

        if(type==1 && brandList!=null){
            return brandList.size();
        }else if(type==2 && categoryList!=null){
            return categoryList.size();
        }else
            return 0;

    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if(convertView==null) {


            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.spinner_item_layout, parent, false);

            TextView textView=convertView.findViewById(R.id.spinnerHeaderTExt);

            if(type==1 && brandList!=null){
                textView.setText(brandList.get(position).getBrandName());
            }else if(type==2 && categoryList!=null){
                textView.setText(categoryList.get(position).getCategoryName());
            }


        }
        return convertView;
    }
}
