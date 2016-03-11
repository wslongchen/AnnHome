package com.example.mrpan.annhome;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import view.ProgressWebView;

/**
 * Created by mrpan on 16/3/7.
 */
public class WebBrowserActivity extends Activity implements View.OnClickListener{

    ProgressWebView webView=null;
    ProgressBar progressBar=null;
    TextView browser_top_bar_title=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        init();

    }

    void init(){
        webView= (ProgressWebView) findViewById(R.id.webbrowser);
        browser_top_bar_title= (TextView) findViewById(R.id.browser_top_bar_title);
        webView.setBackgroundColor(0);
        progressBar= (ProgressBar) findViewById(R.id.browser_progressBar);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(false); // 设置显示缩放按钮
        webSettings.setSupportZoom(false); // 支持缩放
        Bundle bundle=getIntent().getExtras();
        String url=bundle.getString("url");
        String content=bundle.getString("content");
        String title=bundle.getString("name");
        browser_top_bar_title.setText(title);
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                if (url != null && url.startsWith("http://"))
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });
        if(null==url && url.equals(""))
        {
            webView.loadDataWithBaseURL(null, content, "text/html", "UTF-8", null);
        }
        else
        {
            webView.loadUrl(url);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.browser_return:
                break;
            case R.id.browser_more:
                break;
            case R.id.back:
                webView.goBack();
                break;
            case R.id.next:
                webView.goForward();
                break;
            case R.id.refresh:
                webView.reload();
                break;
            case R.id.more:
                Toast.makeText(WebBrowserActivity.this, "此功能敬请期待", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
