package com.edu.hrbeu.hospitalorder.activity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.edu.hrbeu.hospitalorder.GlobalData;
import com.edu.hrbeu.hospitalorder.R;
import com.edu.hrbeu.hospitalorder.TabLayoutFragment;
import com.edu.hrbeu.hospitalorder.adapter.TabAdapter;
import com.edu.hrbeu.hospitalorder.utils.GlideImageLoader;
import com.edu.hrbeu.hospitalorder.utils.PerfectClickListener;
import com.edu.hrbeu.hospitalorder.utils.TopMenuHeader;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 主界面的Activity
 */
public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    public static final String[] tabTitle = new String[]{"气血双补", "健脾开胃", "补虚养身", "防癌抗癌", "清热解毒", "润肺止咳", "延缓衰老"};

    private FrameLayout contnet;
    private List<Fragment> fragments;
    private Context mContext;
    private ProgressBar mProgress;
    private ListView mListView;
    private SwipeRefreshLayout mRefresh;
    private String result;
    private int[] itemId = {R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications};
    private ArrayList<String> tags = new ArrayList<>(Arrays.asList("1", "2", "3"));
    private Activity mActivity;
    private ArrayList<String> mImages;
    private Banner mBanner;

    /**
     * 加载菜谱页面
     */
    private void loadHomePage() {
        Log.e("load", "load");
        View topInflate = View.inflate(this, R.layout.search_bar, null);
        View mInflate = View.inflate(this, R.layout.main_tablayout, null);
        LinearLayout searchBar = (LinearLayout) topInflate.findViewById(R.id.top_search_bar);
        TabLayout tab = (TabLayout) mInflate.findViewById(R.id.tab);
        tab.setVisibility(View.VISIBLE);
        fragments = new ArrayList<>();
        for (int i = 0; i < tabTitle.length; i++) {
            fragments.add(TabLayoutFragment.newInstance(i + 1));
        }
        ViewPager viewPager = (ViewPager) mInflate.findViewById(R.id.viewpager);
        TabAdapter adapter = new TabAdapter(getSupportFragmentManager(), fragments);
        //给ViewPager设置适配器
        viewPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        //将TabLayout和ViewPager关联起来。
        tab.setupWithViewPager(viewPager);
        //设置可以滑动
        tab.setTabMode(TabLayout.MODE_SCROLLABLE);
        contnet.addView(topInflate);
        contnet.addView(mInflate);
        searchBar.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                Intent intent=new Intent(mContext,Search.class);
                startActivity(intent);
                finish();
            }
        });

    }


    /**
     * 加载首页
     */
    private void loadLikePage() {
        mImages=new ArrayList<>(Arrays.asList(GlobalData.PIC_URL_1,GlobalData.PIC_URL_2,GlobalData.PIC_URL_3,GlobalData.PIC_URL_4,GlobalData.PIC_URL_5));
        View mInflate = View.inflate(this, R.layout.like_page, null);
        View mInflateMain=View.inflate(this,R.layout.main_contain,null);
        LinearLayout contain =(LinearLayout) mInflateMain.findViewById(R.id.container_main);
        TopMenuHeader top=new TopMenuHeader(mInflate);
        top.topMenuRight.setVisibility(View.GONE);
        top.topMenuLeft.setVisibility(View.GONE);
        top.topMenuTitle.setText("首页");
        mBanner=(Banner)mInflateMain.findViewById(R.id.my_banner);
        mBanner.startAutoPlay();
        mBanner.setDelayTime(4000);
        loadBannerPicture();
        View inflateMain1=View.inflate(this,R.layout.item_main_zixun,null);
        View inflateMain2=View.inflate(this,R.layout.item_main_zixun2,null);
        ImageView pic1 = (ImageView) inflateMain1.findViewById(R.id.iv_main_1);
        Glide.with(mContext).load(GlobalData.PIC_YAN).placeholder(R.drawable.img_two_bi_one)
                .error(R.drawable.img_two_bi_one)
                .crossFade(1000)
                .into(pic1);
        ImageView pic2 = (ImageView) inflateMain2.findViewById(R.id.iv_main_2);
        ImageView pic3 = (ImageView) inflateMain2.findViewById(R.id.iv_main_3);
        Glide.with(mContext).load(GlobalData.PIC_ZIZHU_1).placeholder(R.drawable.img_two_bi_one)
                .error(R.drawable.img_two_bi_one)
                .crossFade(1000)
                .into(pic2);
        Glide.with(mContext).load(GlobalData.PIC_ZIZHU_2).placeholder(R.drawable.img_two_bi_one)
                .error(R.drawable.img_two_bi_one)
                .crossFade(1000)
                .into(pic3);
        contnet.addView(mInflate);
        contnet.addView(mInflateMain);
        contnet.addView(inflateMain1);
        contnet.addView(inflateMain2);
    }

    private void loadBannerPicture() {
        mBanner.setImages(mImages).setImageLoader(new GlideImageLoader()).start();
    }


    /**
     * 加载个人中心
     */
    private void loadLikePersonPage() {
        View mInfalte=View.inflate(this,R.layout.peison_center,null);
        TopMenuHeader top=new TopMenuHeader(mInfalte);
        top.topMenuRight.setVisibility(View.GONE);
        top.topMenuLeft.setVisibility(View.GONE);
        top.topMenuTitle.setText("个人中心");
        contnet.addView(mInfalte);
        TextView tvLogout = (TextView) mInfalte.findViewById(R.id.tv_logout);
        LinearLayout btnLogout=(LinearLayout)mInfalte.findViewById(R.id.select_logout);
        LinearLayout layoutLike=(LinearLayout)mInfalte.findViewById(R.id.select_like);
        LinearLayout layoutOrder=(LinearLayout)mInfalte.findViewById(R.id.select_order);
        LinearLayout layoutOut=(LinearLayout)mInfalte.findViewById(R.id.select_logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        LinearLayout mInfo = (LinearLayout) mInfalte.findViewById(R.id.person_info);
        mInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext,UserInfo.class);
                startActivity(intent);
            }
        });
        layoutLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext,LikeActivity.class);
                startActivity(intent);
            }
        });
        layoutOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext,OrderListActivity.class);
                startActivity(intent);
            }
        });
        layoutOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


    /**
     * 进入该界面第一个执行的方法onCreate（），里面初始化一些基本资源
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // ThemeUtil.onActivityCreateSetTheme(this);
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_main);
        mContext = this;
        mActivity = this;
        contnet = (FrameLayout) findViewById(R.id.content);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        if (GlobalData.MAIN_TAG.equals("1")) {
            loadHomePage();
        } else if (GlobalData.MAIN_TAG.equals("2")) {
            loadLikePage();
        } else {
            loadHomePage();
        }
        navigation.setSelectedItemId(itemId[tags.indexOf(GlobalData.MAIN_TAG)]);
    }

    private int LOAD_NEWSES = 1;
    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };

        /**
         * 底部导航的监听
         *
         * @param item
         * @return
         */
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    contnet.removeAllViews();
                    GlobalData.MAIN_TAG = "1";
                    Log.e("tag", "home");
                    loadLikePage();
                    return true;
                case R.id.navigation_dashboard:
                    contnet.removeAllViews();
                    GlobalData.MAIN_TAG = "2";
                    Log.e("tag", "like");
                    loadHomePage();
                    return true;
                case R.id.navigation_notifications:
                    contnet.removeAllViews();
                    GlobalData.MAIN_TAG = "3";
                    loadLikePersonPage();
                    return true;
            }
            return false;
        }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}