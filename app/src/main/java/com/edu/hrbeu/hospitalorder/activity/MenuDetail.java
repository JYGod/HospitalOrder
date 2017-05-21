package com.edu.hrbeu.hospitalorder.activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.edu.hrbeu.hospitalorder.GlobalData;
import com.edu.hrbeu.hospitalorder.R;
import com.edu.hrbeu.hospitalorder.bean.MenuBean;
import com.edu.hrbeu.hospitalorder.bean.UpdateStatus;
import com.edu.hrbeu.hospitalorder.utils.HttpUtils;
import com.edu.hrbeu.hospitalorder.utils.TopMenuHeader;
import com.google.gson.Gson;
import com.like.IconType;
import com.like.LikeButton;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;

public class MenuDetail extends Activity implements View.OnClickListener {
    private TextView tvMenuName;
    private ImageView iv_img;
    private TextView tvMenuInfo;
    private TextView tvPrice;
    private Button btnOrder;
    private Context mContext;
    private String menuInfo;
    private TopMenuHeader topHeader;
    private LikeButton btnLike;
    private String res;
    private MenuBean menu;
    private DialogPlus dialog;
    private String orderResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_detail);
        findIds();
        initView();
        listener();
    }

    private void listener() {
        topHeader.topMenuLeft.setOnClickListener(this);
        btnLike.setOnClickListener(this);
        btnOrder.setOnClickListener(this);
    }

    private void initView() {
        mContext=this;
        Intent intent=getIntent();
        menuInfo=intent.getStringExtra("menuInfo");
        btnLike.setIcon(IconType.Heart);
        Gson gson=new Gson();
        menu=gson.fromJson(menuInfo,MenuBean.class);
        tvMenuName.setText(menu.getMenu().getMenu_name());
        tvMenuInfo.setText(menu.getMenu().getMenu_intro());
        tvPrice.setText(menu.getMenu().getMenu_price()+"元");
        Glide.with(mContext).load(menu.getMenu().getMenu_url()).placeholder(R.drawable.img_two_bi_one)
                .error(R.drawable.img_two_bi_one)
                .crossFade(1000)
                .into(iv_img);
        topHeader=new TopMenuHeader(getWindow().getDecorView());
        new Thread(){
            public void run(){
                HashMap<String,String>map=new HashMap<String, String>();
                map.put("collectUsername", GlobalData.USER_NAME);
                map.put("collectMenuname",menu.getMenu().getMenu_name());
                res=HttpUtils.sendPost(GlobalData.URL+"collect/judgeCollect",map,"utf8");
                mHandler.sendEmptyMessage(2);
            }
        }.start();
    }

    private void findIds() {
        tvMenuName=(TextView)findViewById(R.id.tv_menu_name);
        iv_img=(ImageView)findViewById(R.id.iv_menu_pic);
        tvMenuInfo=(TextView)findViewById(R.id.tv_menu_info);
        tvPrice=(TextView)findViewById(R.id.tv_price);
        btnOrder=(Button)findViewById(R.id.btn_order);
        btnLike=(LikeButton)findViewById(R.id.btn_like);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.top_menu_left:
                finish();
                break;
            case R.id.btn_like:
                new Thread(){
                    public void run(){
                        HashMap<String,String>map=new HashMap<String, String>();
                        map.put("collectUsername", GlobalData.USER_NAME);
                        map.put("collectMenuname",tvMenuName.getText().toString());
                        map.put("collectClass",menu.getMenu().getMenu_class());
                        String updateResult= HttpUtils.sendPost(GlobalData.URL+"collect/addCollect",map,"utf8");
                        Gson gson=new Gson();
                        UpdateStatus status=gson.fromJson(updateResult,UpdateStatus.class);
                        if (status.getI()==1){
                            mHandler.sendEmptyMessage(1);
                        }else {
                            mHandler.sendEmptyMessage(-1);
                        }
                    }
                }.start();
               // btnLike.setLiked(true);
                break;
            case R.id.btn_order:
                dialog = DialogPlus.newDialog(this)
                        .setContentHolder(new ViewHolder(R.layout.order_dialog))
                        .setCancelable(false)
                        .setExpanded(true)
                        .create();
                final int[] num = {1};
                View view1 = dialog.getHolderView();
                Button btnOrderCheck=(Button)view1.findViewById(R.id.btn_order_check);
                ImageView ivSub=(ImageView)view1.findViewById(R.id.iv_sub);
                ImageView ivAdd=(ImageView)view1.findViewById(R.id.iv_add);
                TextView tvName=(TextView)view1.findViewById(R.id.tv_name);
                final TextView tvNum=(TextView)view1.findViewById(R.id.tv_num);
                final TextView tvPrice=(TextView)view1.findViewById(R.id.tv_price);
                tvName.setText(menu.getMenu().getMenu_name());
                tvNum.setText("份数：✖️"+"1");
                tvPrice.setText("-"+menu.getMenu().getMenu_price()+"元");
                ImageView ivCancel=(ImageView)view1.findViewById(R.id.iv_cancel);
                ivAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        num[0]++;
                        tvNum.setText("份数：✖️"+String.valueOf(num[0]));
                        tvPrice.setText("-"+String.valueOf(Integer.parseInt(menu.getMenu().getMenu_price())* num[0])+"元");

                    }
                });
                ivSub.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (num[0]>1){
                            num[0]--;
                        }else {
                            num[0]=1;
                        }
                        tvNum.setText("份数：✖️"+String.valueOf(num[0]));
                        tvPrice.setText("-"+String.valueOf(Integer.parseInt(menu.getMenu().getMenu_price())* num[0])+"元");
                    }
                });
                ivCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                btnOrderCheck.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new Thread(){
                            public void run(){
                                HashMap<String,String>map=new HashMap<String, String>();
                                map.put("orderformUsername",GlobalData.USER_NAME);
                                map.put("orderformMenuname",menu.getMenu().getMenu_name());
                                map.put("orderformMenuprice",menu.getMenu().getMenu_price());
                                map.put("orderformNum",String.valueOf(num[0]));
                                HashMap<String,String>map2=new HashMap<String, String>();
                                map2.put("userName",GlobalData.USER_NAME);
                                map2.put("menuName",menu.getMenu().getMenu_name());
                                orderResult=HttpUtils.sendPost(GlobalData.URL+"orderform/addOrderform",map,"utf8");
                                HttpUtils.sendPost(GlobalData.URL+"userorderform/updateUserOrderform",map2,"utf8");
                                mHandler.sendEmptyMessage(3);
                            }
                        }.start();
                    }
                });
                dialog.show();
                break;
        }
    }

    Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){//success
                btnLike.setLiked(true);
                Toast.makeText(getApplicationContext(),"收藏成功！",Toast.LENGTH_SHORT).show();
                btnLike.setEnabled(false);
            }else if (msg.what==-1){//fail
                Toast.makeText(getApplicationContext(),"收藏失败！",Toast.LENGTH_SHORT).show();
            }else if (msg.what==2){
                Gson gson=new Gson();
                UpdateStatus status=gson.fromJson(res,UpdateStatus.class);
                if (status.getI()==1){
                    btnLike.setLiked(true);
                    btnLike.setEnabled(false);
                }
            }else if (msg.what==3){
                Gson gson=new Gson();
                UpdateStatus status = gson.fromJson(orderResult, UpdateStatus.class);
                if (status.getI()==1){
                    Toast.makeText(getApplicationContext(),"下单成功~",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }else {
                    Toast.makeText(getApplicationContext(),"下单失败！",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        }
    };
}
