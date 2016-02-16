package com.example.mrpan.annhome;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;

import entity.Posts;
import entity.Share;
import tencent.QQShared;

/**
 * Created by mrpan on 16/2/4.
 */
public class ArticleActivity extends Activity implements PullToRefreshBase.OnRefreshListener<WebView> ,View.OnClickListener{

    private Context context=null;

    private TextView art_title,art_author;

    private WebView art_content;

    private ImageButton m_toggle=null;
    private ImageButton m_settings=null;



    Posts posts=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.articles_content);
        context = this;
        initView();
        Bundle bundle=getIntent().getExtras();
        posts= (Posts)bundle.getSerializable("posts");

        initData(posts);
    }

    private void initData(Posts posts){
        if(posts!=null){
            art_author.setText(posts.getAuthor().getName());
            art_title.setText(posts.getTitle());
            // art_content.loadUrl(posts.getUrl());
            art_content.loadDataWithBaseURL(null, posts.getContent(), "text/html", "UTF-8", null);
        }
    }

    private void initView(){

        art_content=(WebView)findViewById(R.id.art_content);
        art_title=(TextView)findViewById(R.id.art_title);
        art_author=(TextView)findViewById(R.id.art_author);

        art_content.setBackgroundColor(0);
        m_toggle=(ImageButton)findViewById(R.id.m_toggle);
        m_toggle.setBackgroundResource(R.drawable.btn_menu);
        m_toggle.setOnClickListener(this);
        m_settings=(ImageButton)findViewById(R.id.art_shares);
        m_settings.setOnClickListener(this);

        WebSettings webSettings = art_content.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(false); // 设置显示缩放按钮
        webSettings.setSupportZoom(false); // 支持缩放

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.m_toggle:
                finish();
                break;
            case R.id.art_shares:
                QQShared qq=new QQShared(this,context);
                Share share=new Share();
                share.setAPP_NAME("Ann");
                //share.setIMAGE_URL(posts.getAttachments().get(0).getUrl());
                share.setSUMMARY(posts.getDate());
                share.setTITLE(posts.getTitle());
                share.setTARGET_URL(posts.getUrl());
                qq.ShareSimple(share);
                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh(PullToRefreshBase<WebView> refreshView) {

    }
}
