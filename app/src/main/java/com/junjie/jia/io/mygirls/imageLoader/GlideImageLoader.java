package com.junjie.jia.io.mygirls.imageLoader;

import androidx.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class GlideImageLoader{
    public static void load(@NonNull ImageView imageView, @NonNull String url) {
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }
}
