package com.example.mrpan.annhome;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import utils.CacheUtils;
import utils.MySharePreference;
import view.CircleImageView;

/**
 * Created by mrpan on 15/12/11.
 */
public class SettingActivity extends FragmentActivity implements View.OnClickListener{

    private MySharePreference appPreference;

    private TextView cache_size,item_out,title;

    Context context;

    private ImageButton m_toggle,m_setting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        context=this;
        appPreference = new MySharePreference(this);
        int theme=appPreference.getInt("theme",0);
        LinearLayout linearLayout= (LinearLayout) this.findViewById(R.id.setting_bg);
        linearLayout.setBackgroundResource(theme);
        cache_size= (TextView) findViewById(R.id.cache_size);
        item_out=(TextView)findViewById(R.id.item_out);
        item_out.setOnClickListener(this);
        cache_size.setText(CacheUtils.getHttpCacheSize(this));
        title=(TextView)findViewById(R.id.top_bar_title);
        title.setText("系统设置");
        m_toggle=(ImageButton)findViewById(R.id.m_toggle);
        m_toggle.setOnClickListener(this);
        m_toggle.setBackgroundResource(R.drawable.menu_btn);
        m_setting=(ImageButton)findViewById(R.id.m_setting);
        m_setting.setVisibility(View.GONE);
    }

    protected void exitiDalog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确认退出吗？");

        builder.setTitle("提示");
        builder.setPositiveButton("确认",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        System.exit(0);
                    }
                });
        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builder.create().show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.m_toggle:
                    System.exit(0);
            case R.id.item_out:
                exitiDalog();
                break;
            case R.id.item_clear_cache:

                CacheUtils.clearAppCache(this);

                break;
            default:
                break;
        }
    }
}
