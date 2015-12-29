package com.example.mrpan.annhome;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshHorizontalScrollView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshWebView;

import entity.Posts;
import entity.Share;
import tencent.QQShared;
import utils.MyLog;

/**
 * Created by mrpan on 15/12/13.
 */
public class AriticleFragment extends Fragment implements PullToRefreshBase.OnRefreshListener<WebView> ,View.OnClickListener{

    public static final String TAG = "AriticleFragment";

    private View currentView=null;

    private Context context=null;

    private TextView art_title,art_author;

    private WebView art_content;

    private ImageButton m_toggle=null;
    private ImageButton m_settings=null;

    private FragmentTransaction fragmentTransaction;

    ScrollView mScrollView;

    Posts posts;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        currentView = inflater.inflate(R.layout.articles_content,
                container, false);
        context = this.getActivity();
        ViewGroup parent = (ViewGroup) currentView.getParent();
        if (parent != null) {
            parent.removeView(currentView);
        }
        initView();
        Bundle bundle=getArguments();
        posts= (Posts)bundle.getSerializable("posts");

        initData(posts);
        return currentView;
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
        ((FrameLayout)((MainActivity)getActivity()).findViewById(R.id.slidingpane_menu)).setVisibility(View.GONE);

        art_content=(WebView)currentView.findViewById(R.id.art_content);
        art_title=(TextView)currentView.findViewById(R.id.art_title);
        art_author=(TextView)currentView.findViewById(R.id.art_author);

        art_content.setBackgroundColor(0);
        m_toggle=(ImageButton)currentView.findViewById(R.id.m_toggle);
        m_toggle.setBackgroundResource(R.drawable.btn_menu);
        m_toggle.setOnClickListener(this);
        m_settings=(ImageButton)currentView.findViewById(R.id.m_settings);
        m_settings.setOnClickListener(this);

      // mScrollView = (ScrollView) currentView.findViewById(R.id.pull_refresh_scrollview);

        WebSettings webSettings = art_content.getSettings();
        webSettings.setJavaScriptEnabled(true);
//
//        webSettings.setDefaultTextEncodingName("utf-8");
//
//        DisplayMetrics metrics = new DisplayMetrics();
//        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
//        int mDensity = metrics.densityDpi;
//        if (mDensity == 240) {
//            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
//        } else if (mDensity == 160) {
//            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
//        } else if(mDensity == 120) {
//            webSettings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
//        }else if(mDensity == DisplayMetrics.DENSITY_XHIGH) {
//            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
//        }else if (mDensity == DisplayMetrics.DENSITY_TV){
//            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
//        }else{
//            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
//        }
//        // User settings
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//        webSettings.setUseWideViewPort(true);//关键点
//
//        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        webSettings.setLoadWithOverviewMode(true);
//
//        webSettings.setDisplayZoomControls(false);
//        webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
//        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setBuiltInZoomControls(false); // 设置显示缩放按钮
        webSettings.setSupportZoom(false); // 支持缩放

    }


    @Override
    public void onClick(View v) {
        fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.push_left_in,R.anim.push_left_out);

        switch (v.getId()){

            case R.id.m_toggle:
                Fragment fragment = MainActivity.fragmentMap.get(AllFragment.TAG);

                fragmentTransaction.replace(R.id.slidingpane_content,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case R.id.m_settings:
                QQShared qq=new QQShared(getActivity(),context);
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

    private static class SampleWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    public void onRefresh(final PullToRefreshBase<WebView> refreshView) {
        refreshView.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshView.onRefreshComplete();
            }
        }, 2 * 1000);
    }
}
