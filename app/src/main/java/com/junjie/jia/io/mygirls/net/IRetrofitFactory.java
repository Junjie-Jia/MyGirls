package com.junjie.jia.io.mygirls.net;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public interface IRetrofitFactory {
    RxJava2CallAdapterFactory rxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create();
    GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create();

    Retrofit create();
}
