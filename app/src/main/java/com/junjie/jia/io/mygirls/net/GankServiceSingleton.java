package com.junjie.jia.io.mygirls.net;

import com.junjie.jia.io.mygirls.servic.GankService;

public class GankServiceSingleton {

    public static GankService getGankService() {
        return GankServiceHolder.gankService;
    }

    private static class GankServiceHolder {
        private static final GankService gankService = new GankRetrofitFactory()
            .createRetrofit()
            .create(GankService.class);
    }
}
