package com.junjie.jia.io.mygirls.db;

import android.content.Context;

import com.junjie.jia.io.mygirls.bean.DataBean;
import com.junjie.jia.io.mygirls.bean.OneSentenceBean;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {OneSentenceBean.class, DataBean.class}, version = 1)
public abstract class MyGirlsDataBase extends RoomDatabase {

    private static final String DB_NAME = "my_girls.db";

    public static volatile MyGirlsDataBase INSTANCE;

    public abstract SentencesDao sentencesDao();

    public abstract GankDataDao gankDataDao();

    public static MyGirlsDataBase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (MyGirlsDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        MyGirlsDataBase.class, DB_NAME)
                        .build();
                }
            }
        }
        return INSTANCE;
    }
}
