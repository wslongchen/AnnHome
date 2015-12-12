package com.example.mrpan.annhome;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import utils.MySharePreference;

/**
 * Created by mrpan on 15/12/11.
 */
public class SettingActivity extends FragmentActivity {

    private MySharePreference appPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        appPreference = new MySharePreference(this);
        initView();
    }

    private void initView() {

    }
}
