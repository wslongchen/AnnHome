package com.example.mrpan.annhome;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshWebView;

/**
 * Created by mrpan on 15/12/13.
 */
public class AriticleFragment extends Fragment implements PullToRefreshBase.OnRefreshListener<WebView> {

    public static final String TAG = "AriticleFragment";

    private View currentView=null;

    private Context context=null;

    private TextView art_title,art_author;

    private PullToRefreshWebView art_content;


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
        art_content=(PullToRefreshWebView)currentView.findViewById(R.id.art_content);
        art_content.setOnRefreshListener(this);

        WebView webView = art_content.getRefreshableView();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new SampleWebViewClient());

        // We just load a prepared HTML page from the assets folder for this
        // sample, see that file for the Javascript implementation
        webView.loadData("<p>闲来无事，又给手抄本加了一首很喜欢的歌的谱子。</p><p><img alt=\"\" src=\"http://www.mrpann.com/wp-content/uploads/2015/11/7B00BBA286FE29E74BE6EA10A983DB47.jpg\" style=\"width: 860px; height: 780px\";/></p><p><a href=\"http://www.mrpann.com/?p=66#more-66\" class=\"more-link\">Read more</a></p>","text/html",null);

        return currentView;
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
