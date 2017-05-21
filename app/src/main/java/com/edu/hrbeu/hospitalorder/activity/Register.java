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
import android.widget.Toast;

import com.edu.hrbeu.hospitalorder.GlobalData;
import com.edu.hrbeu.hospitalorder.R;
import com.edu.hrbeu.hospitalorder.bean.UpdateStatus;
import com.edu.hrbeu.hospitalorder.utils.HttpUtils;
import com.google.gson.Gson;

import java.util.HashMap;

public class Register extends Activity implements View.OnClickListener {
    private Context mContext;
    private EditText etName,etPhone,etPwd,etPwdAgain;
    private Button btnRegister;
    private String res;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        mContext=this;
        findIds();
        listener();

    }

    private void listener() {
        btnRegister.setOnClickListener(this);
    }

    private void findIds() {
        etName=(EditText)findViewById(R.id.et_username);
        etPhone=(EditText)findViewById(R.id.et_phone);
        etPwd=(EditText)findViewById(R.id.et_pwd);
        etPwdAgain=(EditText)findViewById(R.id.et_pwd_again);
        btnRegister=(Button)findViewById(R.id.btn_register);
    }




    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                Gson gson=new Gson();
                UpdateStatus status=gson.fromJson(res,UpdateStatus.class);
                if (status.getI()==1){
                    Toast.makeText(getApplicationContext(),"注册成功！",Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(),"注册失败！",Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_register:
                if (etName.getText().toString().equals("")||etPhone.getText().toString().equals("")||
                        etPwd.getText().toString().equals("")||etPwdAgain.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"补充信息！",Toast.LENGTH_SHORT).show();
                }else if (!etPwd.getText().toString().equals(etPwdAgain.getText().toString())){
                    Toast.makeText(getApplicationContext(),"两次密码输入不一样！",Toast.LENGTH_SHORT).show();
                }else {
                    new Thread(){
                        public void run(){
                            HashMap<String,String>map=new HashMap<String, String>();
                            map.put("userName",etName.getText().toString());
                            map.put("userPwd",etPwd.getText().toString());
                            map.put("userPhone",etPhone.getText().toString());
                            res= HttpUtils.sendPost(GlobalData.URL+"user/addUser",map,"utf8");
                            mHandler.sendEmptyMessage(1);
                        }
                    }.start();
                }
                break;
        }
    }
}
