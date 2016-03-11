package com.example.mrpan.annhome;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import org.json.JSONException;
import org.json.JSONObject;

import entity.Datas;
import http.HttpHelper;
import http.HttpResponseCallBack;
import utils.CacheUtils;
import utils.GsonUtils;
import utils.MyLog;
import utils.MySharePreference;

/**
 * Created by mrpan on 15/12/11.
 */
public class SettingActivity extends FragmentActivity implements View.OnClickListener{

    private TextView cache_size,item_out,title,item_about,item_clear_cache;

    Context context;

    private ImageButton m_toggle,m_setting;

    private CheckBox wifi_checkBox;

    private ProgressDialog mDialog;

    private MySharePreference appPreference;

    HttpHelper mHttpClient;

    String aboutObejct=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        context=this;
        changeTheme();
        cache_size= (TextView) findViewById(R.id.cache_size);
        item_out=(TextView)findViewById(R.id.item_out);
        item_out.setOnClickListener(this);
        item_about=(TextView)findViewById(R.id.item_about);
        item_about.setOnClickListener(this);
        item_clear_cache=(TextView)findViewById(R.id.item_clear_cache);
        item_clear_cache.setOnClickListener(this);
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
        appPreference=new MySharePreference(this);
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
        mHttpClient = HttpHelper.getInstance();
        initCacheData();
    }

    void initCacheData(){
        try {
            if(CacheUtils.readHttpCache(Config.DIR_PATH,"about_index")==null){
                mHttpClient.asyHttpGetRequest("http://www.mrpann.com/?json=get_page&id=105", new
                        FilpperHttpResponseCallBack(0));
            }
            else {
                JSONObject about = new JSONObject(CacheUtils.readHttpCache(Config.DIR_PATH, "about_index").toString());
                if (about != null)
                    aboutObejct = about.getString("page").toString();
                mHttpClient.asyHttpGetRequest("http://www.mrpann.com/?json=get_page&id=105", new
                        FilpperHttpResponseCallBack(0));
            }
        } catch (JSONException e) {
            e.printStackTrace();
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
                    cache_size.setText(CacheUtils.getHttpCacheSize(context));
                    Toast.makeText(SettingActivity.this, "清除缓存成功！",
                            Toast.LENGTH_SHORT).show();
                    break;
                case Config.DATA_SHOW:
                    if(msg.obj!=null)
                    {
                        switch (msg.arg2)
                        {
                            case 0:
                                try {
                                    JSONObject jsonObject=new JSONObject(msg.obj.toString().trim());
                                    //System.out.println(msg.obj.toString());
                                    JSONObject page= jsonObject.getJSONObject("page");
                                    if(page!=null) {
                                       String content= page.getString("content").toString();
                                        CacheUtils.saveHttpCache(Config.DIR_PATH,"about_index",page.toString());
                                        aboutObejct=content;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                break;
                        }
                    }
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
            Message message=new Message();
            message.arg1= Config.CLEAR_CACHE;
            handler.sendMessage(message);
        }
    };

    class FilpperHttpResponseCallBack implements HttpResponseCallBack {
        private int position;

        public FilpperHttpResponseCallBack(int position) {
            super();
            this.position = position;

        }

        @Override
        public void onSuccess(String url, String result) {
            Message msg = new Message();
            msg.arg1 = Config.DATA_SHOW;
            msg.obj=result;
            msg.arg2=position;
            handler.sendMessage(msg);
            MyLog.i("all", url);
        }

        @Override
        public void onFailure(int httpResponseCode, int errCode, String err) {
            MyLog.i("all", err);
            if(httpResponseCode==-1)
            {
                Message msg = new Message();
                msg.arg1 = Config.NET_ERROR;
                handler.sendMessage(msg);
            }
        }
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
            case  R.id.item_about:
                if(aboutObejct!=null)
                {
                    Intent intent=new Intent(context,WebBrowserActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("content",aboutObejct);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }
}
