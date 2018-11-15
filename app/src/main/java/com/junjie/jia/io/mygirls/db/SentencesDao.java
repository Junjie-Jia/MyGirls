package com.junjie.jia.io.mygirls.db;

import com.junjie.jia.io.mygirls.bean.OneSentenceBean;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Flowable;

@Dao
public interface SentencesDao {

    @Query("SELECT * FROM sentences LIMIT 1")
    Flowable<OneSentenceBean> findLatestSentence();

    @Query("SELECT * FROM sentences")
    Flowable<List<OneSentenceBean>> findAllSentences();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOneSentence(OneSentenceBean oneSentenceBean);

}
