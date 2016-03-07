package com.example.mrpan.annhome;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobads.AdView;
import com.baidu.mobads.AdViewListener;
import com.example.mrpan.annhome.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;

import DB.DBAdapter;
import entity.Dialog;
import entity.Posts;
import entity.Share;
import tencent.QQShared;
import utils.BitmapUtils;

/**
 * Created by mrpan on 16/2/4.
 */
 public class AticleActivity extends Activity implements View.OnClickListener{

    private Context context=null;

    private TextView art_title,art_author;

    private WebView art_content;

    private ImageButton m_toggle=null;
    private ImageButton m_settings=null;
    private ImageButton m_favorite=null;

    private Bitmap artile_img=null;

    RelativeLayout relativeLayout;

    AdView adView;

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
        initAd();
    }


    void initAd(){
        // 创建广告View
        String adPlaceId = "2412095"; //  重要：请填上您的广告位ID，代码位错误会导致无法请求到广告
        adView = new AdView(this, adPlaceId);
        // 设置监听器
        adView.setListener(new AdViewListener() {
            public void onAdSwitch() {
            }

            public void onAdShow(JSONObject info) {
                relativeLayout.setVisibility(View.VISIBLE);
            }

            public void onAdReady(AdView adView) {
            }

            public void onAdFailed(String reason) {
            }

            public void onAdClick(JSONObject info) {

            }
        });
        relativeLayout = (RelativeLayout)findViewById(R.id.article_ad_layout);
        //relativeLayout.setVisibility(View.VISIBLE);
        // 将adView添加到父控件中(注：该父控件不一定为您的根控件，只要该控件能通过addView能添加广告视图即可)
        RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        rllp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        relativeLayout.addView(adView, rllp);
    }


    private void initData(Posts posts){
        if(posts!=null){
            art_author.setText(posts.getAuthor().getName());
            art_title.setText(posts.getTitle());
            // art_content.loadUrl(posts.getUrl());
            if(posts.getAttachments().size() > 0) {
                //artile_img=BitmapUtils.returnBitMap(posts.getAttachments().get(0).getUrl());
                new DownImage(posts.getAttachments().get(0).getUrl()).execute();
            }
            if(DBAdapter.getDBAdapter(context).isHave("游客",posts.getTitle()))
                m_favorite.setImageResource(R.mipmap.favorite_press);
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
        m_favorite=(ImageButton)findViewById(R.id.imgBtn_download_art);
        m_favorite.setOnClickListener(this);
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
            case R.id.imgBtn_download_art:
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("是否作" +
                        "为羞答答的私密喜欢收藏？");

                builder.setTitle("提示");
                builder.setPositiveButton("欣喜接受",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                if(posts!=null){
                                    Dialog d=new Dialog();
                                    d.setTitle(posts.getTitle());
                                    d.setAuthor(posts.getAuthor().getName());
                                    d.setContent(posts.getContent());
                                    d.setDate(posts.getDate());
                                    if(artile_img!=null){
                                        byte[] bytes=BitmapUtils.Bitmap2Bytes(artile_img);
                                        if(bytes.length>0){
                                            d.setImage(bytes);
                                        }
                                    }
                                    d.setIsLock(false);
                                    d.setUser("游客");
                                    if(DBAdapter.getDBAdapter(context).SaveArticle(d,true)>0)
                                    {
                                        Toast.makeText(context, "喜欢成功！", Toast.LENGTH_LONG).show();
                                        m_favorite.setBackgroundResource(R.mipmap.favorite_press);
                                    }
                                }
                            }
                        });
                builder.setNegativeButton("残忍拒绝",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                if(posts!=null){
                                    Dialog d=new Dialog();
                                    d.setTitle(posts.getTitle());
                                    d.setAuthor(posts.getAuthor().getName());
                                    d.setContent(posts.getContent());
                                    d.setDate(posts.getDate());
                                    d.setIsLock(false);
                                    if(artile_img!=null){
                                        byte[] bytes=BitmapUtils.Bitmap2Bytes(artile_img);
                                        if(bytes.length>0){
                                            d.setImage(bytes);
                                        }
                                    }
                                    d.setUser("游客");
                                    if(DBAdapter.getDBAdapter(context).SaveArticle(d,false)>0)
                                    {
                                        Toast.makeText(context,"喜欢成功！",Toast.LENGTH_LONG).show();
                                        m_favorite.setBackgroundResource(R.mipmap.favorite_press);
                                    }
                                }

                            }
                        });

                builder.create().show();
                break;
            default:
                break;
        }
    }

    class DownImage extends AsyncTask {

        private String imageView;

        public DownImage(String imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Object doInBackground(Object[] params) {
            String url = imageView;
            Bitmap bitmap = null;
            try {
                //加载一个网络图片
                InputStream is = new URL(url).openStream();
                bitmap = BitmapFactory.decodeStream(is);
                System.out.println("successed");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }
    }
    @Override
    protected void onDestroy() {
        adView.destroy();
        super.onDestroy();
    }
}
