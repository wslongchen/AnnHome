package com.example.mrpan.annhome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import com.baidu.mobads.SplashAd;
import com.baidu.mobads.SplashAdListener;

import utils.MySharePreference;

/**
 * Created by mrpan on 16/2/17.
 */
public class WelcomeActivity extends Activity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        RelativeLayout adsParent = (RelativeLayout) this
                .findViewById(R.id.adsRl);
        SplashAdListener listener = new SplashAdListener() {
            @Override
            public void onAdPresent() {

            }

            @Override
            public void onAdDismissed() {
                jumpWhenCanClick();// 跳转至您的应用主界面
            }

            @Override
            public void onAdFailed(String s) {
                Log.i("Activity", "onAdFailed");
                jump();
            }

            @Override
            public void onAdClick() {

            }
        };

        String adPlaceId = "2412017";//代码位ID
        new SplashAd(this, adsParent, listener, adPlaceId, true);

        MySharePreference sharedPreferences = new MySharePreference(this);
        boolean isFirst = sharedPreferences.getBoolean("first", false);
        if (!isFirst) {
            sharedPreferences.commitBoolean("first", true);
            //int theme=sharedPreferences.getInt("theme",0);
            sharedPreferences.commitInt("theme", R.color.list_bg_color);
        }
        else
        {
            jump();
        }

    }

    public boolean canJumpImmediately = false;

    private void jumpWhenCanClick() {
        if (canJumpImmediately) {
            this.startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            this.finish();
        } else {
            canJumpImmediately = true;
        }

    }

    private void jump() {
        this.startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
        this.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (canJumpImmediately) {
            jumpWhenCanClick();
        }
        canJumpImmediately = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        canJumpImmediately = false;
    }
}
