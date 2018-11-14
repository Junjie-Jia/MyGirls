package com.junjie.jia.io.mygirls.service;

import com.junjie.jia.io.mygirls.bean.OneSentenceBean;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface OneSentenceService {
    @GET("?c=f")
    Single<OneSentenceBean> getOneSentence();
}
