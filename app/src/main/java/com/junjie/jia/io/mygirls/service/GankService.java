package com.junjie.jia.io.mygirls.service;

import com.junjie.jia.io.mygirls.bean.CategoryBean;
import com.junjie.jia.io.mygirls.bean.TodayData;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GankService {

    @GET("today")
    Observable<TodayData> getTodayData();

    @GET("data/{category}/{number}/{page}")
    Observable<CategoryBean> searchCategoryData(@Path("category") String category,
                                                @Path("number") int number,
                                                @Path("page") int page);
}
