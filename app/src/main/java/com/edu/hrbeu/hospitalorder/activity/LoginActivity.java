package com.edu.hrbeu.hospitalorder.activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.edu.hrbeu.hospitalorder.GlobalData;
import com.edu.hrbeu.hospitalorder.R;
import com.edu.hrbeu.hospitalorder.bean.UpdateStatus;
import com.edu.hrbeu.hospitalorder.utils.HttpUtils;
import com.google.gson.Gson;

import java.util.HashMap;

public class LoginActivity extends Activity implements View.OnClickListener {
    private Context mContext;
    private Button btnLogin;
    private EditText etName,etPwd;
    private Button btnRegister;
    private String loginRes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        findIds();
        initView();
        listener();
    }

    private void findIds() {
        etName=(EditText)findViewById(R.id.et_name);
        etPwd=(EditText)findViewById(R.id.et_pwd);
        btnLogin=(Button)findViewById(R.id.btn_login);
        btnRegister=(Button)findViewById(R.id.btn_register);
    }

    private void listener() {
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    private void initView() {
        mContext=this;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                if (etName.getText().toString().equals("")||etPwd.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"输入登录信息",Toast.LENGTH_SHORT).show();
                }else {
                    new Thread(){
                        public void run(){
                            HashMap<String,String>map=new HashMap<String, String>();
                            map.put("userName",etName.getText().toString());
                            map.put("userPwd",etPwd.getText().toString());
                            loginRes= HttpUtils.sendPost(GlobalData.URL+"user/judgeUser",map,"utf8");
                            mHandler.sendEmptyMessage(1);
                        }
                    }.start();
                }
                break;
            case R.id.btn_register:
                Intent intent=new Intent(mContext,Register.class);
                startActivity(intent);
                break;
        }
    }


    Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                Gson gson=new Gson();
                UpdateStatus status=gson.fromJson(loginRes,UpdateStatus.class);
                if (status.getI()==1){//登录成功
                    Toast.makeText(getApplicationContext(),"登录成功~",Toast.LENGTH_SHORT).show();
                    GlobalData.USER_NAME=etName.getText().toString();
                    Intent intent=new Intent(mContext,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else if (status.getI()==0){//密码错误
                    Toast.makeText(getApplicationContext(),"密码错误！",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(),"用户不存在！",Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
}
