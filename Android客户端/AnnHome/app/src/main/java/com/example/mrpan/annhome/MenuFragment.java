package com.example.mrpan.annhome;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;


public class MenuFragment extends Fragment implements OnClickListener {

	public static final String TAG = "MenuFragment";

	private View currentView = null;
	private Button bt_all, bt_favorite, bt_widget,
			bt_skin, bt_systemSetting,bt_index;

	private Button bt_exit, bt_setting;

	private Context context;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		currentView = inflater.inflate(R.layout.fragment_menu_layout,
				container, false);
		context = getActivity();
		bt_all = (Button) currentView.findViewById(R.id.bt_all);
		bt_favorite = (Button) currentView.findViewById(R.id.bt_favorite);
		bt_widget = (Button) currentView
				.findViewById(R.id.bt_widget);
		bt_skin = (Button) currentView.findViewById(R.id.bt_skin);
		bt_systemSetting = (Button) currentView
				.findViewById(R.id.bt_systemSetting);
		bt_systemSetting.setOnClickListener(this);
		bt_all.setOnClickListener(this);
		bt_favorite.setOnClickListener(this);
		bt_widget.setOnClickListener(this);
		bt_skin.setOnClickListener(this);

		bt_exit = (Button) currentView.findViewById(R.id.bt_exit);
		bt_exit.setOnClickListener(this);

		bt_index = (Button) currentView.findViewById(R.id.bt_index);
		bt_index.setOnClickListener(this);

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

			case R.id.bt_index:
				((MainActivity) getActivity()).getSlidingPaneLayout().closePane();
				transaction.replace(R.id.slidingpane_content,
						MainActivity.fragmentMap.get(MainFragment.TAG));
				transaction.commit();
				break;
			case R.id.bt_all:
			((MainActivity) getActivity()).getSlidingPaneLayout().closePane();
			transaction.replace(R.id.slidingpane_content,
					MainActivity.fragmentMap.get(AllFragment.TAG));
			transaction.commit();
			break;
		case R.id.bt_favorite:

			break;
		case R.id.bt_skin:

				break;
		case R.id.bt_widget:

				break;
		case R.id.bt_systemSetting:
			break;

		case R.id.bt_exit:
			 exitiDalog();

			break;
		case R.id.bt_setting:

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
