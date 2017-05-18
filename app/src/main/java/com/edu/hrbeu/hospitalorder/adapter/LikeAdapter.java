package com.edu.hrbeu.hospitalorder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.hrbeu.hospitalorder.R;
import com.edu.hrbeu.hospitalorder.bean.LikeBean;

import java.util.ArrayList;


public class LikeAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<LikeBean.List> data;
    private ViewHolder holder;

    public LikeAdapter(Context mContext, ArrayList<LikeBean.List> data) {
        super();
        this.mContext = mContext;
        this.data = data;
    }

    public void bindData(ArrayList<LikeBean.List> list){
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
            holder = new ViewHolder();
            v=LayoutInflater.from(mContext).inflate(R.layout.com_item,null);
            holder.likeName=(TextView)v.findViewById(R.id.com_content);
            holder.likeTime=(TextView)v.findViewById(R.id.com_time);
            v.setTag(holder);
        }else {
            holder=(ViewHolder)v.getTag();
        }
        if(data!=null){
            LikeBean.List info=new LikeBean.List();
            info=data.get(i);
            holder.likeName.setText(info.getCollect_menu_name());
            holder.likeTime.setText(info.getCollect_time());
        }
        return v;
    }

    class ViewHolder {
        TextView likeName,likeTime;
    }
}
