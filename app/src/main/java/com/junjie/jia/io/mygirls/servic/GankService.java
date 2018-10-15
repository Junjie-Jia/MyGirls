package com.junjie.jia.io.mygirls.servic;

import com.junjie.jia.io.mygirls.bean.CategoryBean;
import com.junjie.jia.io.mygirls.bean.DataBean;
import com.junjie.jia.io.mygirls.bean.TodayData;

import java.util.List;

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
