package com.example.mrpan.annhome;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SlidingPaneLayout;

import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.LocationUtils;
import utils.MyLog;

import static android.location.LocationManager.NETWORK_PROVIDER;

public class MainActivity extends FragmentActivity {

    //界面
    public MenuFragment menuFragment;
    private MainFragment mainFragment;
    private AllFragment allFramgment;
    private AriticleFragment ariticleFragment;

    //框架中的slidingMenu
    private SlidingMenu slidingMenu = null;
    private SlidingPaneLayout slidingPaneLayout;
    //缩放尺寸
    private int maxMargin = 0;
    private DisplayMetrics displayMetrics = new DisplayMetrics();

    Context context = null;

    LocationManager lm = null;

    Location myLocation = null;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss.SSSZ");

    private FragmentTransaction transaction;

    private MapView mMapView;
    BaiduMap mBaiduMap;
    //界面容器
    public static Map<String, Fragment> fragmentMap = new HashMap<String, Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baidumap_fragment);
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        context = this;
       // initView();
        mMapView = (MapView) findViewById(R.id.bmapView);
         BDLocation location = new BDLocation();
        mBaiduMap=mMapView.getMap();


        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory.fromResource(R.mipmap.setting_btn_normal); // 自定义图标
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, mCurrentMarker));




     //   mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        MyLocationListenner myListener=new MyLocationListenner();
        LocationClient mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(5000);
        mLocClient.setLocOption(option);
        mLocClient.start();
    // 当不需要定位图层时关闭定位图层
        //mBaiduMap.setMyLocationEnabled(false);
    }
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {

            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null)
                return;
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                            // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.animateMapStatus(u);
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    //初始化界面
    private void initView() {
        lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        slidingPaneLayout = (SlidingPaneLayout) findViewById(R.id.slidingpanellayout);
        allFramgment = new AllFragment();
        ariticleFragment = new AriticleFragment();
        mainFragment = new MainFragment();
        menuFragment = new MenuFragment();

        fragmentMap.put(AriticleFragment.TAG, ariticleFragment);
        fragmentMap.put(AllFragment.TAG, allFramgment);
        fragmentMap.put(MainFragment.TAG, mainFragment);

        transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.slidingpane_menu, menuFragment);
        transaction.replace(R.id.slidingpane_content, mainFragment);
        transaction.commit();
    }

    //公开侧边栏
    public SlidingPaneLayout getSlidingPaneLayout() {
        return slidingPaneLayout;
    }

    //设置侧边栏缩放规格
    public void setSlidingPaneLayout(final View view) {
        maxMargin = displayMetrics.heightPixels / 10;
        slidingPaneLayout.setPanelSlideListener(new SlidingPaneLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                int contentMargin = (int) (slideOffset * maxMargin);

                FrameLayout.LayoutParams contentParams = (FrameLayout.LayoutParams) view
                        .getLayoutParams();
                contentParams.setMargins(0, contentMargin, 0, contentMargin);

                view.setLayoutParams(contentParams);


                float scale = 1 - ((1 - slideOffset) * maxMargin * 3)
                        / (float) displayMetrics.heightPixels;
                menuFragment.getCurrentView().setScaleX(scale);//设置缩放的基准点
                menuFragment.getCurrentView().setScaleY(scale);// 设置缩放的基准点
                menuFragment.getCurrentView().setPivotX(0);// 设置缩放和选择的点
                menuFragment.getCurrentView().setPivotY(
                        displayMetrics.heightPixels / 2);
                menuFragment.getCurrentView().setAlpha(slideOffset);
            }

            @Override
            public void onPanelOpened(View arg0) {
            }

            @Override
            public void onPanelClosed(View arg0) {
            }
        });
    }


    //第一种SlideView
    private void init2() {
//        //绑定控件
//        Button btnMenu=(Button)findViewById(R.id.btnMenu);
//        Button btnPerson=(Button)findViewById(R.id.btnPerson);
//        btnMenu.setOnClickListener(new MyOnClickListener());
//        btnPerson.setOnClickListener(new MyOnClickListener());


//        // 设置抽屉菜单
//        slidingMenu = new SlidingMenu(this);
//        slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
//        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN); // 触摸边界拖出菜单
//        slidingMenu.setMenu(R.layout.slidingmenu_left);
//        slidingMenu.setSecondaryMenu(R.layout.slidingmenu_right);
////        if (log_State) {
////            slidingMenu.setSecondaryMenu(R.layout.slidingmenu_right_havelogin);
////        } else {
////            slidingMenu.setSecondaryMenu(R.layout.slidingmenu_right);
////        }
//        slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
//        // 将抽屉菜单与主页面关联起来
//        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (slidingPaneLayout.isOpen()) {
                    slidingPaneLayout.closePane();
                } else {

//                    // slidingPaneLayout.openPane();
//                    transaction = getSupportFragmentManager().beginTransaction();
//                    transaction.setCustomAnimations(R.anim.push_right_in,
//                            R.anim.push_right_out);
//                    transaction.replace(R.id.slidingpane_content, mainFragment);
//                    transaction.commit();
                }
                break;
        }
        return true;
    }

    LocationListener listener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            // 实际上报时间
            // String time = sdf.format(new Date(location.getTime()));
            // timeText.setText("实际上报时间：" + time);

            if (LocationUtils.isBetterLocation(location, myLocation)) {
                // 获取纬度
                double lat = location.getLatitude();
                // 获取经度
                double lon = location.getLongitude();
                // 位置提供者
                String provider = location.getProvider();
                // 位置的准确性
                float accuracy = location.getAccuracy();
                // 高度信息
                double altitude = location.getAltitude();
                // 方向角
                float bearing = location.getBearing();
                // 速度 米/秒
                float speed = location.getSpeed();

                String locationTime = sdf.format(new Date(location.getTime()));
                String currentTime = null;

                if (myLocation != null) {
                    currentTime = sdf.format(new Date(myLocation.getTime()));
                    myLocation = location;

                } else {
                    myLocation = location;
                }

                // 获取当前详细地址
                StringBuffer sb = new StringBuffer();
                if (myLocation != null) {
                    Geocoder gc = new Geocoder(context);
                    List<Address> addresses = null;
                    try {
                        addresses = gc.getFromLocation(myLocation.getLatitude(), myLocation.getLongitude(), 1);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    if (addresses != null && addresses.size() > 0) {
                        Address address = addresses.get(0);
                        sb.append(address.getCountryName() + address.getLocality());
                        sb.append(address.getSubThoroughfare());

                    }
                }

                MyLog.i("dizhi", "经度：" + lon + "\n纬度：" + lat + "\n服务商：" + provider + "\n准确性：" + accuracy + "\n高度：" + altitude + "\n方向角：" + bearing
                        + "\n速度：" + speed + "\n上次上报时间：" + currentTime + "\n最新上报时间：" + locationTime + "\n您所在的城市：" + sb.toString());

            }

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

    };

    @Override
    protected void onResume() {
        super.onResume();
//        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener);
//        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
    }

    @Override
    protected void onPause() {
        super.onPause();
 //       lm.removeUpdates(listener);
    }
}
