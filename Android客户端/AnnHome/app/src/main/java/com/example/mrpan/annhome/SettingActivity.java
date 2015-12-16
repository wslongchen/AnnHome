package com.example.mrpan.annhome;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import utils.CacheUtils;
import utils.MySharePreference;

/**
 * Created by mrpan on 15/12/11.
 */
public class SettingActivity extends FragmentActivity {

    private MySharePreference appPreference;

    private TextView cache_size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        appPreference = new MySharePreference(this);

        cache_size= (TextView) findViewById(R.id.cache_size);

        cache_size.setText(CacheUtils.getHttpCacheSize(this));

        initView();
    }

    private void initView() {

    }
}
