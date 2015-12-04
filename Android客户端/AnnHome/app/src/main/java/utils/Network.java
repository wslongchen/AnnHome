package utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by mrpan on 15/12/3.
 */
public class Network {

    private Context mContext;
    public NetworkInfo.State wifiState = null;
    public NetworkInfo.State mobileState = null;

    public Network(Context context) {
        mContext = context;
    }

    public enum NetWorkState {
        WIFI, MOBILE, NONE;

    }

    /**
     * 获取当前的网络状态
     *
     * @return
     */
    public NetWorkState getConnectState() {
        ConnectivityManager manager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        manager.getActiveNetworkInfo();
        wifiState = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState();
        mobileState = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .getState();
        if (wifiState != null && mobileState != null
                && NetworkInfo.State.CONNECTED != wifiState
                && NetworkInfo.State.CONNECTED == mobileState) {
            return NetWorkState.MOBILE;
        } else if (wifiState != null && mobileState != null
                && NetworkInfo.State.CONNECTED != wifiState
                && NetworkInfo.State.CONNECTED != mobileState) {
            return NetWorkState.NONE;
        } else if (wifiState != null && NetworkInfo.State.CONNECTED == wifiState) {
            return NetWorkState.WIFI;
        }
        return NetWorkState.NONE;
    }


    public static boolean checkNetwork(Context context){

        ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //3G
        NetworkInfo.State mobile=manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        NetworkInfo.State wifi=manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();

        if(mobile== NetworkInfo.State.CONNECTED||mobile== NetworkInfo.State.CONNECTING)
            return true;
        if(wifi== NetworkInfo.State.CONNECTED||wifi== NetworkInfo.State.CONNECTING)
            return true;

        return false;
    }
}
