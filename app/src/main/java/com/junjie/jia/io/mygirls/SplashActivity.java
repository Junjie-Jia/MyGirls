package com.junjie.jia.io.mygirls;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.junjie.jia.io.mygirls.bean.OneSentenceBean;
import com.junjie.jia.io.mygirls.servic.OneSentenceService;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class SplashActivity extends Activity {

    private TextView text;
    private TextView from;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        text = findViewById(R.id.text);
        from = findViewById(R.id.from);
        ImageView imageView = findViewById(R.id.imageView);
        Glide.with(this).load(R.drawable.splash).into(imageView);

        new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.lwl12.com/hitokoto/")
                .build()
                .create(OneSentenceService.class)
                .getOneSentence().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<OneSentenceBean>() {
                    @Override
                    public void accept(OneSentenceBean oneSentenceBean) throws Exception {
                        Log.e("XXXX", oneSentenceBean.toString());
                        text.setText(oneSentenceBean.getText());
                        if(!TextUtils.isEmpty(oneSentenceBean.getSource())){
                            from.setText("————  " + oneSentenceBean.getSource());
                        }else {
                            from.setVisibility(View.GONE);
                        }

                        delay();
                    }
                });
    }

    private void delay(){
        Observable.timer(3000, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        finish();
                    }
                });
    }
}
