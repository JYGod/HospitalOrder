package com.edu.hrbeu.hospitalorder;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.hrbeu.hospitalorder.adapter.LikeAdapter;
import com.edu.hrbeu.hospitalorder.bean.LikeBean;
import com.edu.hrbeu.hospitalorder.bean.UpdateStatus;
import com.edu.hrbeu.hospitalorder.utils.CustomDialog;
import com.edu.hrbeu.hospitalorder.utils.HttpUtils;
import com.edu.hrbeu.hospitalorder.utils.TopMenuHeader;
import com.google.gson.Gson;
import com.jayfang.dropdownmenu.DropDownMenu;
import com.jayfang.dropdownmenu.OnMenuSelectedListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LikeActivity extends Activity implements View.OnClickListener {
    private ListView lvLike;
    private TopMenuHeader topHeader;
    private String res;
    private Context mContext;
    final String[] strings=new String[]{"所有分类"};
    final String[] arr1=new String[]{"所有分类","气血双补","健脾开胃","补虚养身","防癌抗癌","清热解毒"};
    private DropDownMenu mMenu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.like);
        findIds();
        initView();
        listener();
    }

    private void listener() {
        topHeader.topMenuLeft.setOnClickListener(this);
    }

    private void initView() {
        mContext=this;
        topHeader=new TopMenuHeader(getWindow().getDecorView());
        topHeader.topMenuTitle.setText("我的收藏");
        new Thread(){
            public void run(){
                HashMap<String,String>map=new HashMap<String, String>();
                map.put("collectUsername",GlobalData.USER_NAME);
                res= HttpUtils.sendPost(GlobalData.URL+"collect/selectCollect",map,"utf8");
                mHandler.sendEmptyMessage(1);

            }
        }.start();

        mMenu=(DropDownMenu)findViewById(R.id.menu);
        mMenu.setmMenuCount(1);
        mMenu.setmShowCount(6);
        mMenu.setShowCheck(true);
        mMenu.setmMenuTitleTextSize(16);
        mMenu.setmMenuListTextSize(14);
        mMenu.setmMenuBackColor(getResources().getColor(R.color.grey));
        mMenu.setmMenuTitleTextColor(Color.GRAY);
        mMenu.setmMenuPressedBackColor(Color.GRAY);
        mMenu.setMenuSelectedListener(new OnMenuSelectedListener() {
            @Override
            public void onSelected(View view, int RowIndex, int ColumnIndex) {
                if (RowIndex==0){
                    new Thread(){
                        public void run(){
                            HashMap<String,String>map=new HashMap<String, String>();
                            map.put("collectUsername",GlobalData.USER_NAME);
                            res= HttpUtils.sendPost(GlobalData.URL+"collect/selectCollect",map,"utf8");
                            mHandler.sendEmptyMessage(1);

                        }
                    }.start();
                }else {

                }
            }
        });
        List<String[]> items = new ArrayList<>();
        items.add(arr1);
        mMenu.setmMenuItems(items);
        mMenu.setIsDebug(false);
    }

    private void findIds() {
        lvLike=(ListView)findViewById(R.id.lv_like);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.top_menu_left:
                finish();
                break;

        }
    }

    private LikeBean likeBean;
    private ArrayList<LikeBean.List> likelist;
    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                Gson gson=new Gson();
                likeBean=gson.fromJson(res, LikeBean.class);
                likelist=likeBean.getList();
                LikeAdapter adapter=new LikeAdapter(mContext,likelist);
                lvLike.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                lvLike.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        final TextView tvName= (TextView) lvLike.getChildAt(i).findViewById(R.id.com_content);
                        CustomDialog.Builder builder = new CustomDialog.Builder(mContext);
                        builder.setMessage("取消收藏?");
                        builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                new Thread(){
                                    public void run(){
                                        HashMap<String,String>map=new HashMap<String, String>();
                                        map.put("collectUsername",GlobalData.USER_NAME);
                                        map.put("collectMenuname",tvName.getText().toString());
                                        String res=HttpUtils.sendPost(GlobalData.URL+"collect/delCollect",map,"utf8");
                                        Gson gson=new Gson();
                                        UpdateStatus status = gson.fromJson(res, UpdateStatus.class);
                                        if (status.getI()==1){
                                            mHandler.sendEmptyMessage(2);
                                        }else {
                                            mHandler.sendEmptyMessage(-2);
                                        }
                                    }
                                }.start();
                            }
                        });
                        builder.setNegativeButton("取消",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        builder.create().show();
                    }
                });
            }else if (msg.what==2){
                Toast.makeText(getApplicationContext(),"取消成功~",Toast.LENGTH_SHORT).show();
                new Thread(){
                    public void run(){
                        HashMap<String,String>map=new HashMap<String, String>();
                        map.put("collectUsername",GlobalData.USER_NAME);
                        res= HttpUtils.sendPost(GlobalData.URL+"collect/selectCollect",map,"utf8");
                        mHandler.sendEmptyMessage(1);

                    }
                }.start();
            }else if (msg.what==-2){
                Toast.makeText(getApplicationContext(),"取消失败!",Toast.LENGTH_SHORT).show();
            }
        }
    };
}
