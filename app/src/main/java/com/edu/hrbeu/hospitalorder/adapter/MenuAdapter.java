package com.edu.hrbeu.hospitalorder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.edu.hrbeu.hospitalorder.Menu;
import com.edu.hrbeu.hospitalorder.R;

import java.util.ArrayList;



public class MenuAdapter extends BaseAdapter{

    private Context mContext;
    private ArrayList<Menu> data;
    private ViewHolder holder;

    public MenuAdapter(Context mContext, ArrayList<Menu> data) {
        super();
        this.mContext = mContext;
        this.data = data;
    }
    public void bindData(ArrayList<Menu> list){
        this.data=list;
    }


    @Override
    public int getCount() {
        return  data.size();
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
        if (v == null) {
            v = LayoutInflater.from(mContext).inflate(R.layout.item_menu, null);
            holder = new ViewHolder();
            holder.menuName=(TextView)v.findViewById(R.id.menu_name);
            holder.menuPrice=(TextView)v.findViewById(R.id.menu_price);
            holder.menuUrl=(ImageView)v.findViewById(R.id.menu_url);
            v.setTag(holder);
        }else {
            holder=(ViewHolder)v.getTag();
        }
        if(data!=null){
            Menu menu=new Menu();
            menu=data.get(i);
            holder.menuName.setText(menu.getMenu_name());
            holder.menuPrice.setText("价格："+menu.getMenu_price()+"元");
            Glide.with(mContext).load(menu.getMenu_url()).placeholder(R.drawable.img_two_bi_one)
                    .error(R.drawable.img_two_bi_one)
                    .crossFade(1000)
                    .into(holder.menuUrl);
        }
            return v;
    }


    class ViewHolder {
        TextView menuName,menuPrice;
        ImageView menuUrl;
    }
}
