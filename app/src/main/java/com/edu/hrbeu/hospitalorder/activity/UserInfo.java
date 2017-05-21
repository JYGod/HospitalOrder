package com.edu.hrbeu.hospitalorder.activity;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.hrbeu.hospitalorder.GlobalData;
import com.edu.hrbeu.hospitalorder.R;
import com.edu.hrbeu.hospitalorder.utils.HttpUtils;
import com.edu.hrbeu.hospitalorder.utils.TopMenuHeader;
import com.google.gson.Gson;

import java.util.HashMap;

public class UserInfo extends Activity implements View.OnClickListener {
    private EditText etUserName,etSex,etPhone,etAddress,etMoney;
    private Context mContext;
    private String res;
    private Button btnInfo;
    private TopMenuHeader topHeader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info);
        findIds();
        initView();
        listener();

    }

    private void listener() {
        btnInfo.setOnClickListener(this);
        topHeader.topMenuLeft.setOnClickListener(this);
    }

    private void initView() {
        mContext=this;
        topHeader=new TopMenuHeader(getWindow().getDecorView());
        new Thread(){
            public void run(){
                HashMap<String,String >map=new HashMap<String, String>();
                map.put("userName", GlobalData.USER_NAME);
                res=HttpUtils.sendPost(GlobalData.URL+"user/selectUser",map,"utf8");
                mHandler.sendEmptyMessage(1);
            }
        }.start();
    }

    private void findIds() {
        etUserName=(EditText)findViewById(R.id.et_username);
        etSex=(EditText)findViewById(R.id.et_sex);
        etPhone=(EditText)findViewById(R.id.et_phone);
        etAddress=(EditText)findViewById(R.id.et_address);
        etMoney=(EditText)findViewById(R.id.et_money);
        btnInfo=(Button)findViewById(R.id.btn_info);
    }

    Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                Gson gson=new Gson();
                com.edu.hrbeu.hospitalorder.bean.UserInfo info=gson.fromJson(res,
                        com.edu.hrbeu.hospitalorder.bean.UserInfo.class);
                etUserName.setEnabled(false);
                etSex.setEnabled(false);
                etPhone.setEnabled(false);
                etAddress.setEnabled(false);
                etMoney.setEnabled(false);
                etUserName.setText(info.getList().get(0).getUser_name());
                etSex.setText(info.getList().get(0).getUser_sex());
                etPhone.setText(info.getList().get(0).getUser_phone());
                etAddress.setText(info.getList().get(0).getUser_addr());
                etMoney.setText(info.getList().get(0).getUser_money());

            }else if (msg.what==2){
                Toast.makeText(getApplicationContext(),"修改成功！",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_info:
                if (btnInfo.getText().toString().equals("保存信息")){
                    final HashMap<String,String>map=new HashMap<>();
                    map.put("userName",GlobalData.USER_NAME);
                    map.put("userSex",etSex.getText().toString());
                    map.put("userAddr",etAddress.getText().toString());
                    map.put("userPhone",etPhone.getText().toString());
                    new Thread(){
                        public void run(){
                            HttpUtils.sendPost(GlobalData.URL+"user/updateUser",map,"utf8");
                            mHandler.sendEmptyMessage(2);
                        }
                    }.start();
                }else {
                    etSex.setEnabled(true);
                    etPhone.setEnabled(true);
                    etAddress.setEnabled(true);
                    btnInfo.setText("保存信息");
                }
                break;
            case R.id.top_menu_left:
                finish();
                break;
        }
    }
}
