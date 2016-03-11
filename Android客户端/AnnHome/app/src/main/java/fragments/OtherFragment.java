package fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mrpan.annhome.MainActivity;
import com.example.mrpan.annhome.R;

import utils.MySharePreference;
import utils.Network;

/**
 * Created by mrpan on 16/3/7.
 */
public class OtherFragment extends Fragment implements View.OnClickListener{

    private View currentView;

    public static final String TAG = "OtherFragment";

    FragmentTransaction transaction;

    private TextView mTitleTextView;

    private ImageButton m_toggle,m_setting;

    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        currentView = inflater.inflate(R.layout.fragment_other_layout,
                container, false);
        context = this.getActivity();
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
        changeTheme();
        initView();
    }
    public void changeTheme(){
        MySharePreference mySharePreference=new MySharePreference(getActivity());
        int theme=mySharePreference.getInt("theme",0);
        LinearLayout linearLayout= (LinearLayout) currentView.findViewById(R.id.main_bg);
        linearLayout.setBackgroundResource(theme);
    }

    private void initView(){
        m_toggle=(ImageButton)currentView.findViewById(R.id.m_toggle);
        m_toggle.setOnClickListener(this);
        m_toggle.setBackgroundResource(R.drawable.menu_btn);
        m_setting=(ImageButton)currentView.findViewById(R.id.m_setting);
        m_setting.setVisibility(View.GONE);
        mTitleTextView = (TextView) currentView.findViewById(R.id.top_bar_title);
        mTitleTextView.setText("其他工具");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.m_toggle:
                ((MainActivity) getActivity()).getSlidingPaneLayout().openPane();
                break;
            default:
                break;
        }
    }
}
