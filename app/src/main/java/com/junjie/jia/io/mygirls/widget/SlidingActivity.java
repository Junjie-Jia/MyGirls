package com.junjie.jia.io.mygirls.widget;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public abstract class SlidingActivity extends AppCompatActivity {

    private SlidingLayout rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (enableSliding()) {
            rootView = new SlidingLayout(this);
            rootView.bindActivity(this);
        }
    }

    protected boolean enableSliding() {
        return true;
    }

    protected void scrollFinish(){
        rootView.scrollClose();
    }
}
