package com.junjie.jia.io.mygirls.db;

import com.junjie.jia.io.mygirls.bean.DataBean;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Author : Victor Jia user
 * Date  :  2018/11/14.
 */

@Dao
public interface GankDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<DataBean> dataBeans);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DataBean dataBean);

    @Query("SELECT * FROM gankDatas")
    Flowable<List<DataBean>> findAllGankData();

    @Query("SELECT * FROM gankDatas WHERE type = :type ORDER BY createMilliseconds DESC LIMIT :pageSize OFFSET :offset")
    Single<List<DataBean>> findOnePageData(int offset, int pageSize, String type);

    @Query("UPDATE gankDatas SET width = :width ,height = :height WHERE _id = :id")
    void updateImageWidthAndHeight(String id, int width, int height);

    @Update
    void updateImageWidthAndHeight(DataBean dataBean);

    @Delete
    void deleteDataBean(DataBean dataBean);
}
