package com.junjie.jia.io.mygirls;

import android.app.Application;

import com.junjie.jia.io.mygirls.db.MyGirlsDataBase;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public MyGirlsDataBase getDataBase(){
        return MyGirlsDataBase.getInstance(this);
    }
}
