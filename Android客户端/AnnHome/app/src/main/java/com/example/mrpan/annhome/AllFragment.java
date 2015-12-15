package com.example.mrpan.annhome;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entity.Datas;
import entity.Posts;
import http.HttpHelper;
import http.HttpResponseCallBack;
import utils.GsonUtils;
import utils.MyLog;

/**
 * Created by mrpan on 15/12/12.
 */
public class AllFragment extends Fragment implements OnClickListener {

    public static final String TAG = "AllFragment";

    private Context context=null;

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        transaction=getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.push_left_in, R.anim.push_left_out);

        initData();
        m_toggle = (ImageView) currentView.findViewById(R.id.m_toggle);
        m_toggle.setOnClickListener(this);
        ptrlvHeadLineNews=(PullToRefreshListView)currentView.findViewById(R.id.ptrlvHeadLineNews);
        ptrlvHeadLineNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Fragment fragment=MainActivity.fragmentMap.get(AriticleFragment.TAG);
                Bundle bundle=new Bundle();
                bundle.putSerializable("posts",datas.getPosts().get(position-1));
                fragment.setArguments(bundle);
                transaction.replace(R.id.slidingpane_content,fragment
                        );
                transaction.commit();
            }
        });
    }

    private void initData(){
        HttpHelper mHttpClient = HttpHelper.getInstance();
        mHttpClient.asyHttpGetRequest("http://www.mrpann.com/?json=1", new HttpResponseCallBack() {
            @Override
            public void onSuccess(String url, String result) {
                datas = (Datas) GsonUtils.getEntity(result, Datas.class);
               myHandler.post(runnable);
            }

            @Override
            public void onFailure(int httpResponseCode, int errCode, String err) {
                MyLog.i("error",err);
            }
        });
    }
    public class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:

                    break;

                default:
                    break;
            }
        }

    };
    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            if(datas!=null) {
                newAdapter = new NewListAdapter(getActivity(), getSimulationNews(0));
                MyLog.i("!!!!!", getSimulationNews(9) + "");
                ptrlvHeadLineNews.setAdapter(newAdapter);
            }

        }
    };
    public ArrayList<HashMap<String, Object>> getSimulationNews(int n) {
        ArrayList<HashMap<String, Object>> ret = new ArrayList<HashMap<String, Object>>();
        if(datas!=null)
        {
            HashMap<String, Object> hm;
            List<Posts> posts=datas.getPosts();
            for(Posts p :posts){
                hm = new HashMap<String, Object>();
                if(p.getAttachments().size()>0) {
                    hm.put("uri", "http://images.china.cn/attachement/jpg/site1000/20131029/001fd04cfc4813d9af0118.jpg");
                }
                else{
                    hm.put("uri",
                            "http://images.china.cn/attachement/jpg/site1000/20131029/001fd04cfc4813d9af0118.jpg");
                }
                hm.put("title", p.getTitle());
                hm.put("content", p.getAuthor().getNickname());
                ret.add(hm);
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
}
