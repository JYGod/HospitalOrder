package com.edu.hrbeu.hospitalorder;


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
import com.edu.hrbeu.hospitalorder.bean.MenuBean;
import com.edu.hrbeu.hospitalorder.bean.UpdateStatus;
import com.edu.hrbeu.hospitalorder.utils.HttpUtils;
import com.edu.hrbeu.hospitalorder.utils.TopMenuHeader;
import com.google.gson.Gson;
import com.like.IconType;
import com.like.LikeButton;

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
    }

    private void initView() {
        mContext=this;
        Intent intent=getIntent();
        menuInfo=intent.getStringExtra("menuInfo");
        btnLike.setIcon(IconType.Heart);
        Gson gson=new Gson();
        MenuBean menu=gson.fromJson(menuInfo,MenuBean.class);
        tvMenuName.setText(menu.getMenu().getMenu_name());
        tvMenuInfo.setText(menu.getMenu().getMenu_intro());
        tvPrice.setText(menu.getMenu().getMenu_price()+"元");
        Glide.with(mContext).load(menu.getMenu().getMenu_url()).placeholder(R.drawable.img_two_bi_one)
                .error(R.drawable.img_two_bi_one)
                .crossFade(1000)
                .into(iv_img);
        topHeader=new TopMenuHeader(getWindow().getDecorView());
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
            }
        }
    };
}
