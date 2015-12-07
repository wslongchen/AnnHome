package com.example.mrpan.annhome;

import android.app.Application;

/**
 * Created by mrpan on 15/12/7.
 */
public class MyApplication extends Application {
    private static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        instance = this;
    }
}
