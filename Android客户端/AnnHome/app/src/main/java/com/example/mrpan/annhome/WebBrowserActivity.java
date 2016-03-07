package com.example.mrpan.annhome;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by mrpan on 16/3/7.
 */
public class WebBrowserActivity extends Activity {

    WebView webView=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        webView= (WebView) findViewById(R.id.webbrowser);
        webView.setBackgroundColor(0);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(false); // 设置显示缩放按钮
        webSettings.setSupportZoom(false); // 支持缩放
        Bundle bundle=getIntent().getExtras();
        String url=bundle.getString("url");
        webView.loadUrl(url);
    }
}
