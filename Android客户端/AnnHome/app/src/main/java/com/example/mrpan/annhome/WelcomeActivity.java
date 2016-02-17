package com.example.mrpan.annhome;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import com.baidu.mobads.SplashAd;
import com.baidu.mobads.SplashAdListener;

/**
 * Created by mrpan on 16/2/17.
 */
public class WelcomeActivity extends Activity{

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        context=this;
        RelativeLayout adsParent = (RelativeLayout) this
                .findViewById(R.id.adsRl);
        SplashAdListener listener=new SplashAdListener(){
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

        String adPlaceId = "2410714";//代码位ID
        new SplashAd(this, adsParent, listener, adPlaceId, true);

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
