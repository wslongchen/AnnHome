package com.example.mrpan.annhome;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.baidu.mobads.SplashAd;
import com.baidu.mobads.SplashAdListener;

import utils.MySharePreference;
import view.parallaxpager.ParallaxContainer;

/**
 * Created by mrpan on 16/3/7.
 */
public class GuidActivity extends Activity {
    private Context context;
    ImageView iv_man;
    ImageView rl_weibo;
    ParallaxContainer parallaxContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_main);
        context = this;

        /**
         * 动画支持11以上sdk,11以下默认不显示动画
         * 若需要支持11以下动画,也可导入https://github.com/JakeWharton/NineOldAndroids
         */
        if (android.os.Build.VERSION.SDK_INT > 10) {
            iv_man = (ImageView) findViewById(R.id.iv_man);
            parallaxContainer = (ParallaxContainer) findViewById(R.id.parallax_container);

            if (parallaxContainer != null) {
                parallaxContainer.setImage(iv_man);
                parallaxContainer.setLooping(false);

                iv_man.setVisibility(View.VISIBLE);
                parallaxContainer.setupChildren(getLayoutInflater(),
                        R.layout.view_intro_1, R.layout.view_intro_2,
                        R.layout.view_intro_3, R.layout.view_intro_4,
                        R.layout.view_intro_5, R.layout.view_login);
            }
        } else {
            setContentView(R.layout.view_login);
        }
        ImageView imageView = (ImageView) findViewById(R.id.open_app);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jump();
            }
        });
    }

    private void jump() {
        this.startActivity(new Intent(GuidActivity.this, MainActivity.class));
        this.finish();
    }

}
