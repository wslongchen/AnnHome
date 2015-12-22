package com.example.mrpan.annhome;

import java.util.List;
import java.util.Objects;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;


import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entity.Datas;
import entity.Posts;
import http.HttpHelper;
import http.HttpResponseCallBack;
import utils.CacheUtils;
import utils.DateUtils;
import utils.GsonUtils;
import utils.ImageCacheUtils;
import utils.MyLog;
import utils.MySharePreference;
import utils.Network;
import volley.VolleyLoadPicture;


public class MainFragment extends Fragment implements OnClickListener {

	public static final String TAG = "MainFragment";

	private MyHandler mHandler;

	private View currentView = null;
	private Context context=null;
	private boolean showNext = true;

	private LinearLayout tips_layout;
	// http
	private HttpHelper mHttpClient;

	SwipeRefreshLayout mSwipeRefreshWidget;
	FragmentTransaction transaction;

	//data
	private Datas datas=null;
	private JSONArray jokeArray=null;

	//顶部菜单
	private ImageButton m_setting;
	private ImageView m_toggle;
	//相关控件
	private TextView top_bar_title, date_TextView,top_tips,joke_content,joke_next;
	private ViewFlipper viewFlipper;
	private int currentPage = 0,currentJoke=0;

	// 滚动 横幅
	private NetworkImageView fliper_img_one, fliper_img_two, fliper_img_three,
			fliper_img_four;
	private TextView fliper_tx_one, fliper_tx_two, fliper_tx_three,
			fliper_tx_four;
	private TextView item_article_one,item_article_two,item_article_three,item_article_four,
			item_article_five,item_article_six;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		currentView = inflater.inflate(R.layout.fragment_main_layout,
				container, false);

		context = this.getActivity();
		mHandler=new MyHandler();
		ViewGroup parent = (ViewGroup) currentView.getParent();
		if (parent != null) {
			parent.removeView(currentView);
		}

		((MainActivity)getActivity()).setSlidingPaneLayout(currentView);

		return currentView;
	}

public void changeTheme(){
	MySharePreference mySharePreference=new MySharePreference(getActivity());
	int theme=mySharePreference.getInt("theme",0);
	LinearLayout linearLayout= (LinearLayout) currentView.findViewById(R.id.main_bg);
	linearLayout.setBackgroundResource(theme);
}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {

		super.onViewCreated(view, savedInstanceState);
		changeTheme();
		initView();
		if(Network.isNetworkAvailable())
		{
			initData();
		}
		else{
			showNoConnect();
			Message msg = new Message();
			msg.arg1 = Config.CACHE_DATA_SHOW;
			mHandler.sendMessage(msg);
		}

		mSwipeRefreshWidget.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {

				if (Network.isNetworkAvailable()) {
					initData();
//					currentJoke=0;
//					showJoke(jokeArray);
					tips_layout.setVisibility(View.GONE);
					mSwipeRefreshWidget.setRefreshing(false);
				} else {
					showNoConnect();
					mSwipeRefreshWidget.setRefreshing(false);
				}
			}
		});
	}

	//没网时候的显示
	private void showNoConnect(){
		tips_layout.setVisibility(View.VISIBLE);
		top_tips.setText("世界上最遥远的距离就是没网。检查设置");
	}

	//加载要显示的数据
	private void initData() {

		mHttpClient = HttpHelper.getInstance();

		mHttpClient.asyHttpGetRequest("http://www.mrpann.com/?json=get_noce&method=create_post&controller=posts", new
				FilpperHttpResponseCallBack(0));

		mHttpClient.asyHttpGetRequest("http://apis.baidu.com/showapi_open_bus/showapi_joke/joke_text?page=1", new
				FilpperHttpResponseCallBack(1));

	}

	//加载显示缓存数据
	private void showCacheData(){
		Object object=CacheUtils.readHttpCache(Config.DIR_PATH,"datas_index");
		if(object!=null)
		{
			showData((Datas)object);

			try {
				jokeArray = new JSONArray(CacheUtils.readHttpCache(Config.DIR_PATH, "joke_index").toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			currentJoke=0;
			showJoke(jokeArray);
		}
	}

	//拿到数据后进行的显示
	private void showData(Datas datas){
		if(datas!=null)
		{

			List<Posts> posts=datas.getPosts();
			fliper_tx_one.setText(posts.get(0).getTitle());

			fliper_tx_two.setText(posts.get(1).getTitle());
			fliper_tx_three.setText(posts.get(2).getTitle());
			fliper_tx_four.setText(posts.get(3).getTitle());
			if(posts.get(0).getAttachments().size()>0) {
				ImageCacheUtils.getInstance().showNetImage(posts.get(0).getAttachments().get(0).getUrl(),fliper_img_one);
			}
			else
			{
				fliper_img_one.setImageResource(R.mipmap.bg_image);
			}
			if(posts.get(1).getAttachments().size() > 0) {
				ImageCacheUtils.getInstance().showNetImage(posts.get(1).getAttachments().get(0).getUrl(), fliper_img_two);
			}
			else
			{
				fliper_img_two.setImageResource(R.mipmap.bg_image);
			}
			if(posts.get(2).getAttachments().size() > 0) {
				ImageCacheUtils.getInstance().showNetImage(posts.get(2).getAttachments().get(0).getUrl(), fliper_img_three);
			}
			else
			{
				fliper_img_three.setImageResource(R.mipmap.bg_image);
			}
			if(posts.get(3).getAttachments().size()>0) {
				ImageCacheUtils.getInstance().showNetImage(posts.get(3).getAttachments().get(0).getUrl(), fliper_img_four);
			}
			else
			{
				fliper_img_four.setImageResource(R.mipmap.bg_image);
			}
			if(posts.size()>6){
				item_article_one.setText(posts.get(0).getTitle());
				item_article_two.setText(posts.get(1).getTitle());
				item_article_three.setText(posts.get(2).getTitle());
				item_article_four.setText(posts.get(3).getTitle());
				item_article_five.setText(posts.get(4).getTitle());
				item_article_six.setText(posts.get(5).getTitle());
			}
		}

	}

	private void initView() {
		//下拉刷新
		mSwipeRefreshWidget = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipe_refresh_widget);

		mSwipeRefreshWidget.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));

		tips_layout =(LinearLayout)currentView.findViewById(R.id.tips_layout);
		m_toggle = (ImageView) currentView.findViewById(R.id.m_toggle);
		m_toggle.setOnClickListener(this);
		m_setting = (ImageButton) currentView.findViewById(R.id.m_setting);
		m_setting.setBackgroundResource(R.drawable.location_btn);
		m_setting.setOnClickListener(this);
		top_bar_title = (TextView) currentView.findViewById(R.id.top_bar_title);
		top_tips = (TextView) currentView.findViewById(R.id.tips_title);

		date_TextView = (TextView) currentView.findViewById(R.id.home_date_tv);

		viewFlipper = (ViewFlipper) currentView
				.findViewById(R.id.mViewFliper_vf);

		fliper_img_one = (NetworkImageView) currentView
				.findViewById(R.id.fliper_img_one);
		fliper_img_two = (NetworkImageView) currentView
				.findViewById(R.id.fliper_img_two);
		fliper_img_three = (NetworkImageView) currentView
				.findViewById(R.id.fliper_img_three);
		fliper_img_four = (NetworkImageView) currentView
				.findViewById(R.id.fliper_img_four);

		fliper_img_one.setDefaultImageResId(R.mipmap.loading);
		fliper_img_one.setErrorImageResId(R.mipmap.loading);
		fliper_img_two.setDefaultImageResId(R.mipmap.loading);
		fliper_img_two.setErrorImageResId(R.mipmap.loading);
		fliper_img_three.setDefaultImageResId(R.mipmap.loading);
		fliper_img_three.setErrorImageResId(R.mipmap.loading);
		fliper_img_four.setDefaultImageResId(R.mipmap.loading);
		fliper_img_four.setErrorImageResId(R.mipmap.loading);

		fliper_tx_one = (TextView) currentView.findViewById(R.id.fliper_tx_one);
		fliper_tx_two = (TextView) currentView.findViewById(R.id.fliper_tx_two);
		fliper_tx_three = (TextView) currentView
				.findViewById(R.id.fliper_tx_three);
		fliper_tx_four = (TextView) currentView
				.findViewById(R.id.fliper_tx_four);

		item_article_one= (TextView) currentView
				.findViewById(R.id.item_article_one);
		item_article_two= (TextView) currentView
				.findViewById(R.id.item_article_two);
		item_article_three= (TextView) currentView
				.findViewById(R.id.item_article_three);
		item_article_four= (TextView) currentView
				.findViewById(R.id.item_article_four);
		item_article_five= (TextView) currentView
						.findViewById(R.id.item_article_five);
		item_article_six= (TextView) currentView
						.findViewById(R.id.item_article_six);


		displayRatio_selelct(currentPage);


		top_bar_title.setText("首页");
		date_TextView.setText(DateUtils.getCurrentDateStr());

		joke_next=(TextView)currentView.findViewById(R.id.joke_next);
		joke_content=(TextView)currentView.findViewById(R.id.joke_content);
		joke_next.setOnClickListener(this);

	}


	public class MyHandler extends  Handler {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.arg1) {
				case Config.NET_ERROR:
					showNoConnect();
				case Config.CACHE_DATA_SHOW:
					showCacheData();
				case Config.DATA_SHOW:
					if(msg.obj!=null)
					{

						switch (msg.arg2)
						{
							case 0:

								datas=(Datas)GsonUtils.getEntity(msg.obj.toString(),Datas.class);
								//作缓存
								CacheUtils.saveHttpCache(Config.DIR_PATH,"datas_index",datas);

								showData(datas);
								tips_layout.setVisibility(View.GONE);
								break;
							case 1:
								try {
									JSONObject jsonObject=new JSONObject(msg.obj.toString());
									JSONObject showapi_res_body=jsonObject.getJSONObject("showapi_res_body");
									if(showapi_res_body!=null) {
										jokeArray= showapi_res_body.getJSONArray("contentlist");
										CacheUtils.saveHttpCache(Config.DIR_PATH,"joke_index",jokeArray.toString());
										currentJoke=0;
										showJoke(jokeArray);
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}
								break;
						}
					}
					break;
			case Config.SHOW_NEXT:
				if (showNext) {
					showNextView();
				} else {
					showPreviousView();
				}
				break;

			default:
				break;
			}
		}

	};

	//显示笑话
	private void showJoke(JSONArray jokeArray){
		if(jokeArray!=null)
		{
		if(jokeArray.length()>0)
		{
			try {
				if(currentJoke>=jokeArray.length())
					currentJoke=0;
				String content=jokeArray.getJSONObject(currentJoke).getString("text");
				joke_content.setText(content);
				currentJoke++;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		}
	}

	@Override
	public void onClick(View view) {
		transaction = getActivity().getSupportFragmentManager()
				.beginTransaction();
		transaction.setCustomAnimations(R.anim.push_left_in,
				R.anim.push_left_out);

		switch (view.getId()) {
			case R.id.joke_next:
				showJoke(jokeArray);
				MyLog.i("button", "333333");
				break;

			case R.id.m_toggle:
				((MainActivity) getActivity()).getSlidingPaneLayout().openPane();

				break;
			case R.id.m_setting:
				((MainActivity) getActivity()).getSlidingPaneLayout().closePane();
				Intent intent = new Intent();
				intent.setClass(context, LocationActivity.class);
				startActivity(intent);
				break;
			default:
				break;
		}

	}

	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			Message msg = new Message();
			msg.arg1 = Config.SHOW_NEXT;
			mHandler.sendMessage(msg);
			mHandler.postDelayed(runnable, 3000);
		}
	};



	private void showPreviousView() {
		displayRatio_selelct(currentPage);
		viewFlipper.setInAnimation(AnimationUtils.loadAnimation(context,
				R.anim.push_left_in));
		viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(context,
				R.anim.push_left_out));
		viewFlipper.showPrevious();
		currentPage--;
		if (currentPage == -1) {
			displayRatio_normal(currentPage + 1);
			currentPage = viewFlipper.getChildCount() - 1;
			displayRatio_selelct(currentPage);
		} else {
			displayRatio_selelct(currentPage);
			displayRatio_normal(currentPage + 1);
		}
	}



	private void showNextView() {

		viewFlipper.setInAnimation(AnimationUtils.loadAnimation(context,
				R.anim.push_left_in));
		viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(context,
				R.anim.push_left_out));
		viewFlipper.showNext();
		currentPage++;
		if (currentPage == viewFlipper.getChildCount()) {
			displayRatio_normal(currentPage - 1);
			currentPage = 0;
			displayRatio_selelct(currentPage);
		} else {
			displayRatio_selelct(currentPage);
			displayRatio_normal(currentPage - 1);
		}
	}

	private void displayRatio_normal(int id) {
		int[] ratioId = { R.id.home_ratio_img_04, R.id.home_ratio_img_03,
				R.id.home_ratio_img_02, R.id.home_ratio_img_01 };
		ImageView img = (ImageView) currentView.findViewById(ratioId[id]);
		img.setSelected(false);
	}

	private void displayRatio_selelct(int id) {
		int[] ratioId = { R.id.home_ratio_img_04, R.id.home_ratio_img_03,
				R.id.home_ratio_img_02, R.id.home_ratio_img_01 };
		ImageView img = (ImageView) currentView.findViewById(ratioId[id]);
		img.setSelected(true);
	}


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
			mHandler.sendMessage(msg);
			 MyLog.i("all", url);
		}

		@Override
		public void onFailure(int httpResponseCode, int errCode, String err) {
			MyLog.i("al2l", err);
			if(httpResponseCode==-1)
			{
				Message msg = new Message();
				msg.arg1 = Config.NET_ERROR;
				mHandler.sendMessage(msg);
			}
		}
	}



	@Override
	public void onPause() {
		mHandler.removeCallbacks(runnable);
		MyLog.i("MainFrame", "暂停应用.");
		changeTheme();
		super.onPause();
	}

	@Override
	public void onResume() {
		mHandler.post(runnable);
		MyLog.i("MainFrame", "重新进入.");
		changeTheme();
		super.onResume();
	}

}
