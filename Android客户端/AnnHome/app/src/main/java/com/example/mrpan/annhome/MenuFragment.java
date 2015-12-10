package com.example.mrpan.annhome;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class MenuFragment extends Fragment implements OnClickListener {
	private View currentView = null;
	private Button bt_xiaoqingxin, bt_collect, showRecommendGameWall,
			showRecommendAppWall, bt_shouye, bt_systemSetting;

	private Button bt_exit, bt_setting;

	private Context context;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		currentView = inflater.inflate(R.layout.fragment_menu_layout,
				container, false);
		context = getActivity();
		bt_xiaoqingxin = (Button) currentView.findViewById(R.id.bt_xiaoqingxin);
		bt_collect = (Button) currentView.findViewById(R.id.bt_collect);
		showRecommendAppWall = (Button) currentView
				.findViewById(R.id.showRecommendAppWall);
		bt_shouye = (Button) currentView.findViewById(R.id.bt_shouye);
		bt_systemSetting = (Button) currentView
				.findViewById(R.id.bt_systemSetting);
		bt_systemSetting.setOnClickListener(this);
		bt_xiaoqingxin.setOnClickListener(this);
		bt_collect.setOnClickListener(this);
		showRecommendAppWall.setOnClickListener(this);
		bt_shouye.setOnClickListener(this);

		bt_exit = (Button) currentView.findViewById(R.id.bt_exit);
		bt_exit.setOnClickListener(this);

		bt_setting = (Button) currentView.findViewById(R.id.bt_setting);
		bt_setting.setOnClickListener(this);

		return currentView;
	}

	public View getCurrentView() {
		return currentView;
	}


	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onClick(View v) {
		FragmentTransaction transaction = getActivity()
				.getSupportFragmentManager().beginTransaction();
		switch (v.getId()) {
		case R.id.bt_shouye:
//			((MainActivity) getActivity()).getSlidingPaneLayout().closePane();
//			transaction.replace(R.id.slidingpane_content,
//					MainActivity.fragmentMap.get(MainFragment.TAG));
//			transaction.commit();
			break;
		case R.id.bt_xiaoqingxin:
//			((MainActivity) getActivity()).getSlidingPaneLayout().closePane();
//			transaction.replace(R.id.slidingpane_content,
//					MainActivity.fragmentMap.get(AllmeinviFragment.TAG));
//			transaction.commit();
			break;
		case R.id.bt_collect:
//			((MainActivity) getActivity()).getSlidingPaneLayout().closePane();
//			transaction.replace(R.id.slidingpane_content,
//					MainActivity.fragmentMap.get(CollectFragment.TAG));
//			transaction.commit();
			break;
		case R.id.showRecommendAppWall:
			//DiyManager.showRecommendAppWall(getActivity());
			break;
		case R.id.bt_systemSetting:
//			Intent inten = new Intent();
//			inten.setClass(context, SettingActivity.class);
//			startActivity(inten);
			break;

		case R.id.bt_exit:
			 exitiDalog();

			break;
		case R.id.bt_setting:
//			Intent intent = new Intent();
//			intent.setClass(context, SettingActivity.class);
//			startActivity(intent);
			break;
		default:
			break;
		}
	}

	protected void exitiDalog() {
		Builder builder = new Builder(context);
		builder.setMessage("确认退出吗？");

		builder.setTitle("提示");
		builder.setPositiveButton("确认",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						System.exit(0);
					}
				});
		builder.setNegativeButton("取消",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		builder.create().show();
	}
}
