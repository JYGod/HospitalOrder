package com.edu.hrbeu.hospitalorder.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.edu.hrbeu.hospitalorder.R;
import com.youth.banner.loader.ImageLoader;

/**
 * 首页轮播图
 */

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object url, ImageView imageView) {
        Glide.with(context).load(url)
                .placeholder(R.drawable.img_two_bi_one)
                .error(R.drawable.img_two_bi_one)
                .crossFade(1000)
                .into(imageView);
    }
}
