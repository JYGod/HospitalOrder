package com.edu.hrbeu.hospitalorder;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.edu.hrbeu.hospitalorder.activity.MenuDetail;
import com.edu.hrbeu.hospitalorder.adapter.MenuAdapter;
import com.edu.hrbeu.hospitalorder.bean.Menu;
import com.edu.hrbeu.hospitalorder.bean.ResultBean;
import com.edu.hrbeu.hospitalorder.utils.HttpUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * 选项卡的界面实现，根据tab标签的不同，加载不同的界面信息
 */
public class TabLayoutFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    public static String TABLAYOUT_FRAGMENT = "tab_fragment";
    private int type;
    private String result;
    private ListView newsList;
    private ProgressBar progressBar;
    private SwipeRefreshLayout freshLayout;
    public static final String[] tabTitle = new String[]{"气血双补", "健脾开胃", "补虚养身", "防癌抗癌", "清热解毒", "润肺止咳", "延缓衰老"};
    private String typeString;
    private ListView menuList;
    private ArrayList<Menu> list;
    private String selectRes;


    public static TabLayoutFragment newInstance(int type) {
        TabLayoutFragment fragment = new TabLayoutFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TABLAYOUT_FRAGMENT, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this.getContext();
        Log.e("onCreate","onCreate");
        if (getArguments() != null) {
            type = (int) getArguments().getSerializable(TABLAYOUT_FRAGMENT);
        }

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_list, container, false);
        initView(view);
        Log.e("onCreateView","onCreateView");
        return view;
    }


    protected void initView(View view) {
        menuList =(ListView)view.findViewById(R.id.lv_menu);
        progressBar= (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        menuList.setVisibility(View.GONE);
        typeString= tabTitle[type-1];
        new MyTask().execute();
    }

    private Context mContext;
    Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                progressBar.setVisibility(View.GONE);
                Intent intent=new Intent(mContext,MenuDetail.class);
                intent.putExtra("menuInfo",selectRes);
                startActivity(intent);
            }

        }
    };

    @Override
    public void onRefresh() {
        freshLayout.setRefreshing(true);
    }


    class MyTask extends AsyncTask<String,Void,ArrayList<Menu>> implements AdapterView.OnItemClickListener {
        @Override
        protected ArrayList<Menu> doInBackground(String... strings) {
            HashMap<String,String>map=new HashMap<>();
            map.put("menuClass",typeString);
            result= HttpUtils.sendPost(GlobalData.URL+"menu/selectMenuClass",map,"utf8");
            Gson gson=new Gson();
            ResultBean resultBean = gson.fromJson(result, ResultBean.class);
            list=new ArrayList<Menu>();
            list=resultBean.getList();
            return list;
        }

        @Override
        protected void onPostExecute(ArrayList<Menu> infos) {
            super.onPostExecute(infos);
            MenuAdapter adapter = new MenuAdapter(mContext, list);
            menuList.setAdapter(adapter);
            adapter.notifyDataSetChanged();
           // menuList.setVisibility(View.VISIBLE );
            progressBar.setVisibility(View.GONE);
            menuList.setVisibility(View.VISIBLE);
            menuList.setOnItemClickListener(this);
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
            progressBar.setVisibility(View.VISIBLE);
            final TextView tvMenuName=(TextView)menuList.getChildAt(i).findViewById(R.id.menu_name);
            new Thread(){
                public void run(){
                    HashMap<String,String>map=new HashMap<>();
                    map.put("menuName",tvMenuName.getText().toString());
                    selectRes=HttpUtils.sendPost(GlobalData.URL+"menu/selectMenuName",map,"utf8");
                    mHandler.sendEmptyMessage(1);
                }

            }.start();
        }
    }

 }
