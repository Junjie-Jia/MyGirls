package com.junjie.jia.io.mygirls.imageLoader;

import androidx.annotation.NonNull;
import android.widget.ImageView;

public interface ImageLoader {
    void load(@NonNull ImageView imageView,@NonNull String url);
}
