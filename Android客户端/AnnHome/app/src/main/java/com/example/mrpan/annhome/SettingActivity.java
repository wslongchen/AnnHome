package com.example.mrpan.annhome;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import utils.CacheUtils;
import utils.MySharePreference;

/**
 * Created by mrpan on 15/12/11.
 */
public class SettingActivity extends FragmentActivity implements View.OnClickListener{

    private MySharePreference appPreference;

    private TextView cache_size,item_out;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        appPreference = new MySharePreference(this);
        int theme=appPreference.getInt("theme",0);
        LinearLayout linearLayout= (LinearLayout) this.findViewById(R.id.main_bg);
        linearLayout.setBackgroundResource(theme);
        cache_size= (TextView) findViewById(R.id.cache_size);

        cache_size.setText(CacheUtils.getHttpCacheSize(this));


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
            case R.id.item_out:
                exitiDalog();
                break;
            default:
                break;
        }
    }
}
