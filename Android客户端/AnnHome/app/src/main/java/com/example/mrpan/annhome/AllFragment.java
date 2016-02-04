package com.example.mrpan.annhome;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entity.Datas;
import entity.Posts;
import http.HttpHelper;
import http.HttpResponseCallBack;
import utils.BitmapUtils;
import utils.CacheUtils;
import utils.GsonUtils;
import utils.ImageCacheUtils;
import utils.MyLog;
import utils.Network;

/**
 * Created by mrpan on 15/12/12.
 */
public class AllFragment extends Fragment implements OnClickListener {

    public static final String TAG = "AllFragment";

    private Context context=null;

    private LinearLayout tips_layout;

    private TextView top_tips;

    private View currentView = null;

    private ImageView m_toggle;
    private PullToRefreshListView ptrlvHeadLineNews;
    private NewListAdapter newAdapter = null;

    FragmentTransaction transaction;

    private Datas datas=null;
    MyHandler myHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        currentView = inflater.inflate(R.layout.fragment_all_layout,
                container, false);
        context = this.getActivity();
        ViewGroup parent = (ViewGroup) currentView.getParent();
        if (parent != null) {
            parent.removeView(currentView);
        }
        myHandler=new MyHandler();
        ((MainActivity)getActivity()).setSlidingPaneLayout(currentView);
        return currentView;
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        transaction=getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.push_left_in, R.anim.push_left_out);
        initView();
        if(Network.isNetworkAvailable())
        {
            initData();
        }
        else{
            showNoConnect();

        }

        ptrlvHeadLineNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                Fragment fragment = MainActivity.fragmentMap.get(AriticleFragment.TAG);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("posts", datas.getPosts().get(position - 1));
//                fragment.setArguments(bundle);
//                transaction.add(R.id.slidingpane_content, fragment);
//                transaction.commit();
                Intent intent=new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("posts", datas.getPosts().get(position-1));
                intent.putExtras(bundle);
                intent.setClass(context,ArticleActivity.class);
                startActivity(intent);

            }
        });
    }

    private void initView(){
        tips_layout =(LinearLayout)currentView.findViewById(R.id.tips_layout);
        top_tips = (TextView) currentView.findViewById(R.id.tips_title);

        m_toggle = (ImageView) currentView.findViewById(R.id.m_toggle);
        m_toggle.setOnClickListener(this);
        ptrlvHeadLineNews=(PullToRefreshListView)currentView.findViewById(R.id.ptrlvHeadLineNews);
    }

    private void initData(){
        HttpHelper mHttpClient = HttpHelper.getInstance();
        mHttpClient.asyHttpGetRequest("http://www.mrpann.com/?json=1", new HttpResponseCallBack() {
            @Override
            public void onSuccess(String url, String result) {
                datas = (Datas) GsonUtils.getEntity(result, Datas.class);
              // myHandler.post(runnable);
                myHandler.sendEmptyMessage(1);
            }

            @Override
            public void onFailure(int httpResponseCode, int errCode, String err) {
                myHandler.sendEmptyMessage(Config.NET_ERROR);
                MyLog.i("error",err);
            }
        });
    }

    public class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if(datas!=null) {
                        newAdapter = new NewListAdapter(getActivity(), getSimulationNews(0));
//                        MyLog.i("!!!!!", getSimulationNews(9) + "");
                       ptrlvHeadLineNews.setOnRefreshListener(new MyOnRefreshListener(ptrlvHeadLineNews));
                        ptrlvHeadLineNews.setAdapter(newAdapter);
            }
                    break;
                case Config.NET_ERROR:
                    showNoConnect();
                    break;
                default:
                    break;
            }
        }

    };
//    Runnable runnable = new Runnable() {
//
//        @Override
//        public void run() {
//            if(datas!=null) {
//                newAdapter = new NewListAdapter(getActivity(), getSimulationNews(0));
//                MyLog.i("!!!!!", getSimulationNews(9) + "");
//                ptrlvHeadLineNews.setAdapter(newAdapter);
//            }
//
//        }
//    };

    //没网时候的显示
    private void showNoConnect(){
        tips_layout.setVisibility(View.VISIBLE);
        top_tips.setText("世界上最遥远的距离就是没网。检查设置");
         datas=(Datas) CacheUtils.readHttpCache(Config.DIR_PATH, "datas_index");
        myHandler.sendEmptyMessage(1);
    }

    public ArrayList<HashMap<String, Object>> getSimulationNews(int n) {
        ArrayList<HashMap<String, Object>> ret = new ArrayList<HashMap<String, Object>>();
        if(datas!=null)
        {
            HashMap<String, Object> hm;
            List<Posts> posts=datas.getPosts();
            //int i=1;
            for(Posts p :posts){
                hm = new HashMap<String, Object>();
                if(p.getAttachments().size()>0) {
                    hm.put("uri",p.getAttachments().get(0).getUrl());
                }
                else{
                    hm.put("uri",
                            "http://images.china.cn/attachement/jpg/site1000/20131029/001fd04cfc4813d9af0118.jpg");
                }
                hm.put("title", p.getTitle());
                hm.put("content", p.getAuthor().getNickname());
                ret.add(hm);
               // i++;
            }

        }
        return ret;
    }



    @Override
    public void onClick(View v) {
        transaction = getActivity().getSupportFragmentManager()
                .beginTransaction();
        transaction.setCustomAnimations(R.anim.push_left_in,
                R.anim.push_left_out);
        switch (v.getId()){
            case R.id.m_toggle:
                ((MainActivity) getActivity()).getSlidingPaneLayout().openPane();

                break;
            default:
                break;
        }
    }

    class MyOnRefreshListener implements PullToRefreshBase.OnRefreshListener2<ListView> {

        private PullToRefreshListView mPtflv;

        public MyOnRefreshListener(PullToRefreshListView ptflv) {
            this.mPtflv = ptflv;
        }

        @Override
        public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
            // 下拉刷新
            String label = "上次刷新: "+DateUtils.formatDateTime(getActivity(),
                    System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
                            | DateUtils.FORMAT_SHOW_DATE
                            | DateUtils.FORMAT_ABBREV_ALL);


            refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

           // refreshView.getLoadingLayoutProxy().setLoadingDrawable(getActivity().getDrawable(R.mipmap.drawing012));
            //refreshView.getLoadingLayoutProxy().setLoadingDrawable(getActivity().getDrawable(R.mipmap.drawing012));
            new GetNewsTask(ptrlvHeadLineNews).execute();
        }

        @Override
        public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
            // 上拉加载

        }

    }
    /**
     * 请求网络获信息
     */
    class GetNewsTask extends AsyncTask<String, Void, Integer> {
        private PullToRefreshListView mPtrlv;

        public GetNewsTask(PullToRefreshListView ptrlv) {
            this.mPtrlv = ptrlv;
        }

        @Override
        protected Integer doInBackground(String... params) {

             if (Network.isNetworkAvailable()) {
             try {
             Thread.sleep(1000);
                return Config.HTTP_REQUEST_SUCCESS;
             } catch (InterruptedException e) {
             e.printStackTrace();
             }
             }
             return Config.HTTP_REQUEST_ERROR;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            switch (result) {
                case Config.HTTP_REQUEST_SUCCESS:
//                    newAdapter.addNews(getSimulationNews(10));
//                    newAdapter.notifyDataSetChanged();
//                    try {
//                        Thread.sleep(3000);
                          initData();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    break;
                case Config.HTTP_REQUEST_ERROR:
                    showNoConnect();
                    break;
            }
            mPtrlv.onRefreshComplete();
        }

    }

}
