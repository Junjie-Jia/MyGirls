package com.junjie.jia.io.mygirls.net;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

public class GankRetrofitFactory implements IRetrofitFactory {

    private static final String baseUrl = "http://gank.io/api/";

    public Retrofit create() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okhttpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        return new Retrofit.Builder()
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .addConverterFactory(gsonConverterFactory)
                .baseUrl(baseUrl)
                .client(okhttpClient)
                .build();
    }
}
