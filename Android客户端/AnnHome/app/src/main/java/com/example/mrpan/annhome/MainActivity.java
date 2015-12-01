package com.example.mrpan.annhome;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class MainActivity extends Activity {

    private RecyclerView mRecyclerView;

    private MyAdapter myAdapter;

    private SlidingMenu slidingMenu = null;

    public boolean log_State;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 设置抽屉菜单
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN); // 触摸边界拖出菜单
        slidingMenu.setMenu(R.layout.slidingmenu_left);
        if (log_State) {
            slidingMenu.setSecondaryMenu(R.layout.slidingmenu_right_havelogin);
        } else {
            slidingMenu.setSecondaryMenu(R.layout.slidingmenu_right);
        }
        slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);

        // 将抽屉菜单与主页面关联起来
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        //Gson gson=new Gson();
        // 拿到RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        // 设置LinearLayoutManager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // 设置ItemAnimator
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // 设置固定大小
        mRecyclerView.setHasFixedSize(true);
        // 初始化自定义的适配器
        myAdapter = new MyAdapter(this, getItems());
        // 为mRcyclerView设置适配器
        mRecyclerView.setAdapter(myAdapter);



    }

    private ArrayList<HashMap<String,Object>> getItems(){
        ArrayList<HashMap<String,Object>> items=new ArrayList<HashMap<String, Object>>();
        for(int i=0;i<5;i++) {
            HashMap<String,Object> item=new HashMap<String,Object>();
            item.put("title", "上海");
            items.add(item);

        }

        return items;
    }
}
