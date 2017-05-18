package com.edu.hrbeu.hospitalorder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Search extends Activity{
    private TextView tvCancel;
    private EditText etSearch;
    private Context mContext;
    private ImageView imgSearch;
    private LinearLayout searchMain;
    private ProgressBar progressBar;
    private String result;
    private ListView searchList;
    private Activity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(GlobalData.MY_THEME);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        mContext=this;
        mActivity=this;
        imgSearch=(ImageView)findViewById(R.id.img_search);
        tvCancel=(TextView)findViewById(R.id.tv_cancel);
        etSearch=(EditText)findViewById(R.id.et_search);
        etSearch.addTextChangedListener(watcher);
        searchMain=(LinearLayout)findViewById(R.id.search_main);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        searchList =(ListView)findViewById(R.id.lv_searchlist);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });



    }


    private TextWatcher watcher= new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (etSearch.getText().toString().equals("")){
                searchList.setVisibility(View.GONE);
                searchMain.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}
