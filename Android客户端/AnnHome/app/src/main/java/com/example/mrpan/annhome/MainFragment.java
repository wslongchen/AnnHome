package com.example.mrpan.annhome;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.toolbox.ImageLoader;
import com.google.gson.Gson;

import entity.Datas;
import entity.Posts;
import http.HttpHelper;
import http.HttpResponseCallBack;
import utils.DateUtils;
import utils.GsonUtils;
import utils.MyLog;
import utils.Network;
import volley.VolleyLoadPicture;


public class MainFragment extends Fragment implements OnClickListener {

	public static final int Fliper_ONE = 1001;
	public static final int Fliper_TWO = 1002;
	public static final int Fliper_THREE = 1003;
	public static final int Fliper_FOUR = 1004;
	public static final int DATA_SHOW=0012;
	public static final int NET_ERROR=0013;

	private MyHandler mHandler;

	private View currentView = null;
	private ImageButton m_toggle, m_setting;
	private TextView top_bar_title, date_TextView;;
	private ViewFlipper viewFlipper;
	private int currentPage = 0;
	private Context context;
	private static final int SHOW_NEXT = 0011;
	private boolean showNext = true;
	// 滚动 横幅
	private ImageView fliper_img_one, fliper_img_two, fliper_img_three,
			fliper_img_four;
	private TextView fliper_tx_one, fliper_tx_two, fliper_tx_three,
			fliper_tx_four;
	private String fliper_str_one, fliper_str_two, fliper_str_three,
			fliper_str_four;
	// http
	private HttpHelper mHttpClient;

	FragmentTransaction transaction;

	private MyAdapter myAdapter;

	private RecyclerView mRecyclerView;

	SwipeRefreshLayout mSwipeRefreshWidget;

	//data
	private Datas datas=null;

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
		MyLog.i("111","22222222222222222"+Network.checkNetwork(getActivity()));
		if(Network.checkNetwork(getActivity()))
			initData();
		else{
			MyLog.i("111","22222222222222222");
		}
		return currentView;
	}

	private void initData() {
		//下拉刷新
		mSwipeRefreshWidget = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipe_refresh_widget);
		// 拿到RecyclerView
		mRecyclerView = (RecyclerView)currentView.findViewById(R.id.news_list);
		// 设置LinearLayoutManager
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		// 设置ItemAnimator
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		// 设置固定大小
		mRecyclerView.setHasFixedSize(true);
		// 初始化自定义的适配器
		myAdapter = new MyAdapter(getActivity(), MainActivity.getItems());
		// 为mRcyclerView设置适配器
		mRecyclerView.setAdapter(myAdapter);

//		this.myAdapter.setOnItemClickListener(new MyAdapter.MyItemClickListener() {
//			@Override
//			public void onItemClick(View view, int postion) {
//				Toast.makeText(getActivity(), "11111111111111", Toast.LENGTH_SHORT).show();
//			}
//		});
//		this.myAdapter.setOnItemLongClickListener(new MyAdapter.MyItemLongClickListener() {
//			@Override
//			public void onItemLongClick(View view, int postion) {
//				Toast.makeText(getActivity(), "22222222222222", Toast.LENGTH_SHORT).show();
//			}
//		});
		//http://www.mrpann.com/json=get_recent_posts
		mHttpClient = HttpHelper.getInstance();
		new FilpperHttpResponseCallBack(0);
		mHttpClient.asyHttpGetRequest("http://www.mrpann.com/?json=get_recent_posts",
				new FilpperHttpResponseCallBack(0));


	}
	private void showData(String result){
		datas=(Datas)GsonUtils.getEntity(result,Datas.class);
		//fliper_img_two.setImageResource(R.mipmap.p);fliper_img_one.setImageResource(R.mipmap.drawing012);
		if(datas!=null)
		{
			List<Posts> posts=datas.getPosts();
			fliper_tx_one.setText(posts.get(0).getTitle());
			fliper_tx_two.setText(posts.get(1).getTitle());
			fliper_tx_three.setText(posts.get(2).getTitle());
			fliper_tx_four.setText(posts.get(3).getTitle());
			if(posts.get(0).getThumbnail_images()!=null) {
				//Bitmap b=HttpHelper.getHttpBitmap(posts.get(0).getAttachments().get(0).getUrl());
				//fliper_img_one.setImageBitmap(b);

				VolleyLoadPicture.getInstance().showNetImage(posts.get(0).getAttachments().get(0).getUrl(),fliper_img_one);
			}
			if(posts.get(1).getThumbnail_images()!=null) {
				//Bitmap b=HttpHelper.getHttpBitmap(posts.get(1).getAttachments().get(0).getUrl());
				//fliper_img_two.setImageBitmap(b);
				VolleyLoadPicture.getInstance().showNetImage(posts.get(1).getAttachments().get(0).getUrl(), fliper_img_two);
			}
			if(posts.get(2).getThumbnail_images()!=null) {
				Bitmap b=HttpHelper.getHttpBitmap(posts.get(2).getAttachments().get(0).getUrl());
				fliper_img_three.setImageBitmap(b);
			}
			if(posts.get(3).getThumbnail_images()!=null) {
				Bitmap b = HttpHelper.getHttpBitmap(posts.get(3).getAttachments().get(0).getUrl());
				fliper_img_four.setImageBitmap(b);
			}
		}
		mHandler.post(runnable);
	}
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		initView();
		super.onViewCreated(view, savedInstanceState);
	}

	private void initView() {
		m_toggle = (ImageButton) currentView.findViewById(R.id.m_toggle);
		m_toggle.setOnClickListener(this);
		m_setting = (ImageButton) currentView.findViewById(R.id.m_setting);
		m_setting.setOnClickListener(this);
		top_bar_title = (TextView) currentView.findViewById(R.id.top_bar_title);

		date_TextView = (TextView) currentView.findViewById(R.id.home_date_tv);

		viewFlipper = (ViewFlipper) currentView
				.findViewById(R.id.mViewFliper_vf);

		fliper_img_one = (ImageView) currentView
				.findViewById(R.id.fliper_img_one);
		fliper_img_two = (ImageView) currentView
				.findViewById(R.id.fliper_img_two);
		fliper_img_three = (ImageView) currentView
				.findViewById(R.id.fliper_img_three);
		fliper_img_four = (ImageView) currentView
				.findViewById(R.id.fliper_img_four);

		fliper_tx_one = (TextView) currentView.findViewById(R.id.fliper_tx_one);
		fliper_tx_two = (TextView) currentView.findViewById(R.id.fliper_tx_two);
		fliper_tx_three = (TextView) currentView
				.findViewById(R.id.fliper_tx_three);
		fliper_tx_four = (TextView) currentView
				.findViewById(R.id.fliper_tx_four);

		displayRatio_selelct(currentPage);


		top_bar_title.setText("最新文章");
		date_TextView.setText(DateUtils.getCurrentDateStr());

	}


	public class MyHandler extends  Handler {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.arg1) {
				case NET_ERROR:
					mHandler.post(runnable);
				case DATA_SHOW:
					if(msg.obj!=null)
						showData(msg.obj.toString());
					break;
			case SHOW_NEXT:
				if (showNext) {
					showNextView();
				} else {
					showPreviousView();
				}
				break;

			case Fliper_ONE:
				if (fliper_img_one != null) {
				}

				break;
			case Fliper_TWO:
				if (fliper_img_two != null) {
				}

				break;
			case Fliper_THREE:
				if (fliper_img_three != null) {


				}
				break;
			case Fliper_FOUR:
				if (fliper_img_four != null) {
				}
				break;

			default:
				break;
			}
		}

	};

	public LayoutParams getCurrentViewParams() {
		return (LayoutParams) currentView.getLayoutParams();
	}

	public void setCurrentViewPararms(LayoutParams layoutParams) {
		currentView.setLayoutParams(layoutParams);
	}

	@Override
	public void onClick(View view) {
		transaction = getActivity().getSupportFragmentManager()
				.beginTransaction();
		transaction.setCustomAnimations(R.anim.push_left_in,
				R.anim.push_left_out);

		switch (view.getId()) {
		case R.id.m_toggle:
			((MainActivity) getActivity()).getSlidingPaneLayout().openPane();

			break;
		case R.id.m_setting:
			viewFlipper.setInAnimation(AnimationUtils.loadAnimation(context,
					R.anim.push_left_in));
			viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(context,
					R.anim.push_left_out));
			viewFlipper.showNext();
			break;
		default:
			break;
		}

	}

	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			Message msg = new Message();
			msg.arg1 = SHOW_NEXT;
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
			msg.arg1 = DATA_SHOW;
			msg.obj=result;
			mHandler.sendMessage(msg);
			 MyLog.i("all", url);
		}

		@Override
		public void onFailure(int httpResponseCode, int errCode, String err) {
			if(httpResponseCode==-1)
			{
				Message msg = new Message();
				msg.arg1 = NET_ERROR;
				mHandler.sendMessage(msg);
			}

			MyLog.i("all", httpResponseCode + "---" + err);
		}
	}



	@Override
	public void onPause() {
		mHandler.removeCallbacks(runnable);
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
	}
}
