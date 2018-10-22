package com.junjie.jia.io.mygirls.servic;

import com.junjie.jia.io.mygirls.bean.OneSentenceBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface OneSentenceService {
    @GET("v1?encode=realjson")
    Observable<OneSentenceBean> getOneSentence();
}
