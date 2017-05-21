package com.edu.hrbeu.hospitalorder.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import com.edu.hrbeu.hospitalorder.GlobalData;
import com.edu.hrbeu.hospitalorder.R;
import com.edu.hrbeu.hospitalorder.adapter.OrderAdapter;
import com.edu.hrbeu.hospitalorder.bean.Order;
import com.edu.hrbeu.hospitalorder.bean.OrderListBean;
import com.edu.hrbeu.hospitalorder.utils.HttpUtils;
import com.edu.hrbeu.hospitalorder.utils.TopMenuHeader;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderListActivity extends Activity implements View.OnClickListener {
    private Context mContex;
    private ListView lvOrder;
    private String orderResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_list);
        mContex=this;
        findIds();
        initView();
    }

    private void initView() {
        new Thread(){
            public void run(){
                HashMap<String,String>map=new HashMap<String, String>();
                map.put("orderformUsername", GlobalData.USER_NAME);
                orderResult= HttpUtils.sendPost(GlobalData.URL+"orderform/selectOrderform",map,"utf8");
                mHandler.sendEmptyMessage(1);
            }
        }.start();

    }

    private void findIds() {
        TopMenuHeader header=new TopMenuHeader(getWindow().getDecorView());
        header.topMenuTitle.setText("订单信息");
        header.topMenuLeft.setOnClickListener(this);
        lvOrder=(ListView)findViewById(R.id.lv_order);
    }

    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                Gson gson=new Gson();
                OrderListBean orderListBean=gson.fromJson(orderResult,OrderListBean.class);
                ArrayList<Order>orders=orderListBean.getList();
                OrderAdapter adapter=new OrderAdapter(mContex,orders);
                lvOrder.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.top_menu_left:
                finish();
        }
    }
}
