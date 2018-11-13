package com.junjie.jia.io.mygirls;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.junjie.jia.io.mygirls.bean.OneSentenceBean;
import com.junjie.jia.io.mygirls.servic.OneSentenceService;

import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class SplashActivity extends Activity {

    private final String TAG = SplashActivity.class.getSimpleName();
    private final int COLD_BOOT_DELAY = 3200;
    private final int HOT_BOOT_DELAY = 1200;

    private TextView text;
    private TextView from;
    private Disposable delayDispose;
    private Disposable oneSentenceDispose;

    private boolean isHotBoot = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        text = findViewById(R.id.text);
        from = findViewById(R.id.from);
        ImageView imageView = findViewById(R.id.imageView);
        Glide.with(this).load(R.drawable.photo1440_90).into(imageView);

        new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://v1.hitokoto.cn/")
                .build()
                .create(OneSentenceService.class)
                .getOneSentence()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<OneSentenceBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        oneSentenceDispose = d;
                    }

                    @Override
                    public void onSuccess(OneSentenceBean oneSentenceBean) {
                        Log.e(TAG, oneSentenceBean.toString());
                        text.setText(oneSentenceBean.getHitokoto());
                        if (!TextUtils.isEmpty(oneSentenceBean.getCreator())) {
                            from.setText(String.format(getResources().getString(R.string.one_sentence_creator), oneSentenceBean.getCreator()));
                        } else {
                            from.setVisibility(View.GONE);
                        }
                        delay(COLD_BOOT_DELAY);
                    }

                    @Override
                    public void onError(Throwable e) {
                        text.setText(getResources().getString(R.string.one_sentence_default));
                        from.setText(String.format(getResources().getString(R.string.one_sentence_creator), getResources().getString(R.string.one_sentence_default_author)));
                        delay(COLD_BOOT_DELAY);
                    }
                });
    }

    private void delay(int millSeconds) {
        delayDispose = Observable.timer(millSeconds, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) {
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isHotBoot) {
            delay(HOT_BOOT_DELAY);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!oneSentenceDispose.isDisposed()) {
            oneSentenceDispose.dispose();
        }
        if (!delayDispose.isDisposed()) {
            delayDispose.dispose();
        }
        isHotBoot = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isHotBoot = false;
    }
}
