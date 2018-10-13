package com.junjie.jia.io.mygirls.net;

import retrofit2.Retrofit;

public class GankRetrofitFactory implements IRetrofitFactory {

    private static final String baseUrl = "http://gank.io/api/";

    public Retrofit create() {
        return new Retrofit.Builder()
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .addConverterFactory(gsonConverterFactory)
                .baseUrl(baseUrl)
                .build();
    }
}
