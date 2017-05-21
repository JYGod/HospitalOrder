package com.edu.hrbeu.hospitalorder.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.edu.hrbeu.hospitalorder.R;
import com.edu.hrbeu.hospitalorder.bean.Order;

import java.util.ArrayList;

public class OrderAdapter extends BaseAdapter{

    private Context mContext;
    private ArrayList<Order> data;
    private ViewHolder holder;

    public OrderAdapter(Context mContext, ArrayList<Order> data) {
        this.mContext = mContext;
        this.data = data;
    }

    public void bindData(ArrayList<Order> list){
        this.data=list;
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        if (v==null){
            v = LayoutInflater.from(mContext).inflate(R.layout.item_order, null);
            holder = new ViewHolder();
            holder.tvName=(TextView)v.findViewById(R.id.tv_name);
            holder.tvNum=(TextView)v.findViewById(R.id.tv_num);
            holder.tvPrice=(TextView)v.findViewById(R.id.tv_price);
            holder.tvTime=(TextView)v.findViewById(R.id.tv_time);
            v.setTag(holder);
        }else {
            holder=(ViewHolder)v.getTag();
        }

        if(data!=null){
            Order order=new Order();
            order=data.get(i);
            holder.tvName.setText(order.getOrder_menu_name());
            holder.tvNum.setText("✖️"+order.getOrder_num()+"份");
            holder.tvPrice.setText("共计："+order.getOrder_menu_price()+"元");
            holder.tvTime.setText(order.getOrder_time());
        }
        return v;
    }


    class ViewHolder {
        TextView tvName,tvNum,tvPrice,tvTime;
    }
}
