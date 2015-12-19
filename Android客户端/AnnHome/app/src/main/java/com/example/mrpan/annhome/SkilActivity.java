package com.example.mrpan.annhome;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import utils.MySharePreference;

/**
 * Created by mrpan on 15/12/19.
 */
public class SkilActivity extends FragmentActivity{

    private final int skinIconID[] = {R.color.blue,
            R.color.dark_brown,
            R.color.dark_gray,
            R.color.list_bg_color};

    private final String text[] = {"蓝水静溢", "绿雾晨光", "粉色花语", "银装素裹"};

    private final int uncheckIcon = R.mipmap.logo_app;

    private ListView mListView;

    private SkinListAdapter mSkinListAdapter;

    private MySharePreference mySharePreference=null;

    private TextView mTitleTextView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        setContentView(R.layout.activity_skin);
        mySharePreference=new MySharePreference(this);
        changeTheme();
        init();
    }

    public void changeTheme(){
        MySharePreference mySharePreference=new MySharePreference(this);
        int theme=mySharePreference.getInt("theme",0);
        LinearLayout linearLayout= (LinearLayout)findViewById(R.id.skin_color);
        linearLayout.setBackgroundResource(theme);
    }
    private void init()
    {
        mListView = (ListView) findViewById(R.id.themelist);

        mSkinListAdapter = new SkinListAdapter(this, getItemList());

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


        mTitleTextView = (TextView) findViewById(R.id.top_bar_title);
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
}
