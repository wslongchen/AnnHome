package com.example.mrpan.annhome;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import utils.CacheUtils;
import utils.MySharePreference;

/**
 * Created by mrpan on 15/12/11.
 */
public class SettingActivity extends FragmentActivity implements View.OnClickListener{

    private TextView cache_size,item_out,title;

    Context context;

    private ImageButton m_toggle,m_setting;

    private CheckBox wifi_checkBox;

    private ProgressDialog mDialog;

    private MySharePreference appPreference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        context=this;
        changeTheme();
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
        mDialog=new ProgressDialog(context);
        mDialog.setMessage("正在清楚缓存...");
        wifi_checkBox = (CheckBox) findViewById(R.id.wifi_checkBox);
        wifi_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    appPreference.commitInt(Config.TYPE_CONN, Config.TYPE_WIFI);
                } else {
                    appPreference.commitInt(Config.TYPE_CONN, Config.TYPE_ALL);
                }
            }
        });
        int p = appPreference.getInt(Config.TYPE_CONN, 0);
        if (p == Config.TYPE_ALL) {
            wifi_checkBox.setChecked(false);
        } else if (p == Config.TYPE_WIFI) {
            wifi_checkBox.setChecked(true);
        } else {
            wifi_checkBox.setChecked(false);
        }
    }

    public void changeTheme(){
        MySharePreference mySharePreference=new MySharePreference(this);
        int theme=mySharePreference.getInt("theme",0);
        LinearLayout linearLayout= (LinearLayout)findViewById(R.id.main_bg);
        linearLayout.setBackgroundResource(theme);
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

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.arg1) {
                case Config.CLEAR_CACHE:
                    mDialog.cancel();
                    mDialog.dismiss();
                    Toast.makeText(SettingActivity.this, "清除缓存成功！",
                            Toast.LENGTH_SHORT).show();
                    break;

                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            CacheUtils.clearAppCache(context);
            cache_size.setText(CacheUtils.getHttpCacheSize(context));
            Message message=new Message();
            message.arg1= Config.CLEAR_CACHE;
            handler.sendMessage(message);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.m_toggle:
                    System.exit(0);
            case R.id.item_out:
                exitiDalog();
                break;
            case R.id.item_clear_cache:
                mDialog.show();
                new Thread(runnable).start();
                break;
            case R.id.wifi_checkBox:
                 if(wifi_checkBox.isChecked()) {
                wifi_checkBox.setChecked(false);
            } else {
                wifi_checkBox.setChecked(true);
            }
                break;
            default:
                break;
        }
    }
}
