package com.example.mrpan.annhome;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v4.widget.SwipeRefreshLayout;

import android.support.v7.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends FragmentActivity {

    public MenuFragment menuFragment;
    private MainFragment mainFragment;
    private AllFragment allFramgment;
    private AriticleFragment ariticleFragment;

    private SlidingMenu slidingMenu = null;

    private int maxMargin = 0;
    private DisplayMetrics displayMetrics = new DisplayMetrics();
    private SlidingPaneLayout slidingPaneLayout;
    private FragmentTransaction transaction;

    public static Map<String, Fragment> fragmentMap = new HashMap<String, Fragment>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        initData();
    }
    //第二种SlideView
    private void initData(){
        slidingPaneLayout = (SlidingPaneLayout) findViewById(R.id.slidingpanellayout);
        menuFragment = new MenuFragment();
        mainFragment = new MainFragment();

        allFramgment=new AllFragment();
        ariticleFragment=new AriticleFragment();

        maxMargin = displayMetrics.heightPixels / 10;
        slidingPaneLayout.setPanelSlideListener(new SlidingPaneLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                int contentMargin = (int) (slideOffset * maxMargin);

                FrameLayout.LayoutParams contentParams = mainFragment
                        .getCurrentViewParams();
                contentParams.setMargins(0, contentMargin, 0, contentMargin);

                mainFragment.setCurrentViewPararms(contentParams);


                float scale = 1 - ((1 - slideOffset) * maxMargin * 3)
                        / (float) displayMetrics.heightPixels;
                menuFragment.getCurrentView().setScaleX(scale);//设置缩放的基准点
                menuFragment.getCurrentView().setScaleY(scale);// 设置缩放的基准点
                menuFragment.getCurrentView().setPivotX(0);// 设置缩放和选择的点
                menuFragment.getCurrentView().setPivotY(
                        displayMetrics.heightPixels / 2);
                menuFragment.getCurrentView().setAlpha(slideOffset);
            }

            @Override
            public void onPanelOpened(View arg0) {
            }

            @Override
            public void onPanelClosed(View arg0) {
            }
        });



        fragmentMap.put(AriticleFragment.TAG,ariticleFragment);
        fragmentMap.put(AllFragment.TAG,allFramgment);
        fragmentMap.put(MainFragment.TAG, mainFragment);
        transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.slidingpane_menu, menuFragment);
        transaction.replace(R.id.slidingpane_content, mainFragment);
        transaction.commit();
    }

    public SlidingPaneLayout getSlidingPaneLayout() {
        return slidingPaneLayout;
    }

    //第一种SlideView
    private void init(){
//        //绑定控件
//        Button btnMenu=(Button)findViewById(R.id.btnMenu);
//        Button btnPerson=(Button)findViewById(R.id.btnPerson);
//        btnMenu.setOnClickListener(new MyOnClickListener());
//        btnPerson.setOnClickListener(new MyOnClickListener());


//        // 设置抽屉菜单
//        slidingMenu = new SlidingMenu(this);
//        slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
//        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN); // 触摸边界拖出菜单
//        slidingMenu.setMenu(R.layout.slidingmenu_left);
//        slidingMenu.setSecondaryMenu(R.layout.slidingmenu_right);
////        if (log_State) {
////            slidingMenu.setSecondaryMenu(R.layout.slidingmenu_right_havelogin);
////        } else {
////            slidingMenu.setSecondaryMenu(R.layout.slidingmenu_right);
////        }
//        slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
//        // 将抽屉菜单与主页面关联起来
//        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
    }


    public class MyOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnMenu:
                    slidingMenu.showMenu();
                    break;
                case R.id.btnPerson:
                    slidingMenu.showSecondaryMenu();
                    break;
                default:
                    break;
            }
        }
    }
}
