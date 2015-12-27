package tencent;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

/**
 * Created by mrpan on 15/12/27.
 */
public class BaseUiListener implements IUiListener {
    Context mContext;
    String mScope="";
    private static final int ON_COMPLETE = 0;

    public BaseUiListener(Context mContext) {
        super();
        this.mContext = mContext;
    }

    public BaseUiListener(Context mContext, String mScope) {
        super();
        this.mContext = mContext;
        this.mScope = mScope;
    }
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ON_COMPLETE:
                    JSONObject response = (JSONObject)msg.obj;
                   // mrpan.android.loveproject.bean.Util.login_obj="";
                   // mrpan.android.loveproject.bean.Util.login_obj=response.toString();

                    break;

            }
        }
    };

    protected void doComplete(JSONObject jsonObj,String mScope) {
        Message msg = mHandler.obtainMessage();
        msg.what = ON_COMPLETE;
        msg.obj = jsonObj;
        mHandler.sendMessage(msg);
        if(mScope.equals("get_vip_rich_info"))
        {
            //mrpan.android.loveproject.bean.Util.obj="";
            //mrpan.android.loveproject.bean.Util.obj=jsonObj.toString();
        }
       // Log.v("main", jsonObj.toString());
        // try {
        // // JSONObject jsonObj = new JSONObject(values.toString());
        // OPENID = jsonObj.getString("openid");
        // PFKEY = jsonObj.getString("pfkey");
        // ACCESS_TOKEN = jsonObj.getString("access_token");
        // // System.out.println("222222openid"+OPENID);
        // mTencent.setAccessToken(ACCESS_TOKEN, null);
        // mTencent.setOpenId(OPENID);
        //
        // } catch (JSONException e) {
        // System.out.println("Json parse error");
        // e.printStackTrace();
        // }
    }

    @Override
    public void onError(UiError e) {
        // showResult("onError:", "code:" + e.errorCode + ", msg:"
        // + e.errorMessage + ", detail:" + e.errorDetail);
    }

    @Override
    public void onCancel() {
        // showResult("onCancel", "");
    }

    @Override
    public void onComplete(Object arg0) {
        // TODO Auto-generated method stub
        doComplete((JSONObject) arg0,this.mScope);

    }
}
