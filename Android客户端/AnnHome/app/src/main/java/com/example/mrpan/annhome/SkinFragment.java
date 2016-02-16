package com.example.mrpan.annhome;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import utils.MySharePreference;

/**
 * Created by mrpan on 16/02/16.
 */
public class SkinFragment extends Fragment implements OnClickListener {

    public static final String TAG = "SkinFragment";

    private final int skinIconID[] = {R.color.blue,
            R.color.dark_brown,
            R.color.dark_gray,
            R.color.list_bg_color};

    private final String text[] = {"蓝水静溢", "绿雾晨光", "粉色花语", "银装素裹"};

    private final int uncheckIcon = R.mipmap.logo_app;

    private ListView mListView;

    private View currentView = null;

    private SkinListAdapter mSkinListAdapter;

    private MySharePreference mySharePreference=null;

    private TextView mTitleTextView;

    private ImageButton m_toggle,m_setting;

    FragmentTransaction transaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        currentView = inflater.inflate(R.layout.fragment_skin_layout,
                container, false);
        ViewGroup parent = (ViewGroup) currentView.getParent();
        if (parent != null) {
            parent.removeView(currentView);
        }
        ((MainActivity)getActivity()).setSlidingPaneLayout(currentView);
        return currentView;
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        transaction=getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.push_left_in, R.anim.push_left_out);
        mySharePreference=new MySharePreference(getActivity());
        changeTheme();
        init();


    }

    public void changeTheme(){
        MySharePreference mySharePreference=new MySharePreference(getActivity());
        int theme=mySharePreference.getInt("theme",0);
        LinearLayout linearLayout= (LinearLayout) currentView.findViewById(R.id.main_bg);
        linearLayout.setBackgroundResource(theme);
    }
    private void init()
    {
        m_toggle=(ImageButton)currentView.findViewById(R.id.m_toggle);
        m_toggle.setOnClickListener(this);
        m_toggle.setBackgroundResource(R.drawable.menu_btn);
        m_setting=(ImageButton)currentView.findViewById(R.id.m_setting);
        m_setting.setVisibility(View.GONE);
        mListView = (ListView) currentView.findViewById(R.id.themelist);

        mSkinListAdapter = new SkinListAdapter(getActivity(), getItemList());

        mListView.setAdapter(mSkinListAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                mSkinListAdapter.setSelect(position);
                mySharePreference.commitInt("theme",skinIconID[position]);
                //mTitleTextView.setBackgroundResource(skinIconID[position]);
                changeTheme();
            }
        });


        mTitleTextView = (TextView) currentView.findViewById(R.id.top_bar_title);
        mTitleTextView.setText("更换皮肤");


    }


    private List<SkinListItemData> getItemList()
    {
        List<SkinListItemData> list = new ArrayList<SkinListItemData>();

        for(int i = 0; i < 4; i++)
        {
            SkinListItemData data = new SkinListItemData();
            data.mImageViewLeftID = skinIconID[i];
            data.mTextView = text[i];
            data.mImageViewRightID = uncheckIcon;
            list.add(data);
        }


        return list;
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
