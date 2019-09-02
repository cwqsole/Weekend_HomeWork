package com.example.usercwq.weekend_homework;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.bikenavi.BikeNavigateHelper;
import com.baidu.mapapi.bikenavi.adapter.IBEngineInitListener;
import com.baidu.mapapi.bikenavi.adapter.IBRoutePlanListener;
import com.baidu.mapapi.bikenavi.model.BikeRoutePlanError;
import com.baidu.mapapi.bikenavi.params.BikeNaviLaunchParam;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.walknavi.WalkNavigateHelper;
import com.baidu.mapapi.walknavi.adapter.IWEngineInitListener;
import com.example.usercwq.weekend_homework.beans.PoiOverlay;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BaiDuActivity extends AppCompatActivity {

    @BindView(R.id.map_map)
    MapView mapMap;
    @BindView(R.id.bt_me)
    Button btMe;
    @BindView(R.id.edit_sousuo)
    EditText editSousuo;
    @BindView(R.id.bt_sousuo)
    Button btSousuo;
    private BaiduMap baiduMap;
    private LocationClient mLocationClient;
    private LatLng latLng;
    private PoiSearch mPoiSearch;
    private BikeNaviLaunchParam param;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai_du);
        ButterKnife.bind(this);
        baiduMap = mapMap.getMap();

        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(this, mPermissionList, 123);
        }
        //  可以通过如下代码设置地图的缩放级别：
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.zoom(12.0f);
        baiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        //开启地图的定位图层
        baiduMap.setMyLocationEnabled(true);

        baiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                addMarKer(latLng);
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                startNav(marker.getPosition());
                return false;
            }
        });
        //定位
        lcate();


    }

    private void startNav(final LatLng end) {
// 获取导航控制类
// 引擎初始化
        BikeNavigateHelper.getInstance().initNaviEngine(this, new IBEngineInitListener() {
            @Override
            public void engineInitSuccess() {
                //骑行导航初始化成功之后的回调
                routePlanWithParam(end);
            }

            @Override
            public void engineInitFail() {
                //骑行导航初始化失败之后的回调
            }
        });
    }

    private void routePlanWithParam(LatLng end) {

//构造BikeNaviLaunchParam
//.vehicle(0)默认的普通骑行导航
        if(latLng!=null && end!=null){
            param = new BikeNaviLaunchParam().stPt(latLng).endPt(end).vehicle(0);

            //发起算路
            BikeNavigateHelper.getInstance().routePlanWithParams(param, new IBRoutePlanListener() {
                @Override
                public void onRoutePlanStart() {
                    //执行算路开始的逻辑
                }

                @Override
                public void onRoutePlanSuccess() {
                    //算路成功
                    //跳转至诱导页面
                    Intent intent = new Intent(BaiDuActivity.this, BNaviGuideActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onRoutePlanFail(BikeRoutePlanError bikeRoutePlanError) {
                    //执行算路失败的逻辑
                }
            });
        }

    }

    private void addMarKer(LatLng latLng) {
        //定义Maker坐标点
//构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_marka);
//构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(latLng)
                .animateType(MarkerOptions.MarkerAnimateType.drop)
                .icon(bitmap);
//在地图上添加Marker，并显示
        baiduMap.addOverlay(option);
    }

    private void lcate() {
        //定位初始化
        mLocationClient = new LocationClient(this);

//通过LocationClientOption设置LocationClient相关参数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);

//设置locationClientOption
        mLocationClient.setLocOption(option);

//注册LocationListener监听器
        MyLocationListener myLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myLocationListener);
//开启地图定位图层
        mLocationClient.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mapMap.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mapMap.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mapMap.onDestroy();
    }

    @OnClick(R.id.bt_me)
    public void onClick() {

        loateUser();
    }

    private void loateUser() {

        if (latLng != null) {
            //北京为地图中心，logo在左上角
            MapStatusUpdate status1 = MapStatusUpdateFactory.newLatLng(latLng);
            baiduMap.setMapStatus(status1);
        }

    }

    @OnClick(R.id.bt_sousuo)
    public void onSousuo() {
        poiSerch();

    }

    private void poiSerch() {
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(listener);

        /**
         *  PoiCiySearchOption 设置检索属性
         *  city 检索城市
         *  keyword 检索内容关键字
         *  pageNum 分页页码
         */
        if(latLng!=null && !TextUtils.isEmpty(editSousuo.getText().toString().trim())){
            mPoiSearch.searchNearby(new PoiNearbySearchOption()
                    .location(latLng)
                    .radius(5000)
                    .keyword(editSousuo.getText().toString().trim())
                    .pageNum(10));
        }

    }
    OnGetPoiSearchResultListener listener = new OnGetPoiSearchResultListener() {
        @Override
        public void onGetPoiResult(PoiResult poiResult) {
            if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {
                baiduMap.clear();

                //创建PoiOverlay对象
                PoiOverlay poiOverlay = new PoiOverlay(baiduMap);

                //设置Poi检索数据
                poiOverlay.setData(poiResult);

                //将poiOverlay添加至地图并缩放至合适级别
                poiOverlay.addToMap();
                poiOverlay.zoomToSpan();
            }
        }
        @Override
        public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {

        }
        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

        }
        //废弃
        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

        }
    };


    //我们通过继承抽象类BDAbstractListener并重写其onReceieveLocation方法来获取定位数据，
    // 并将其传给MapView。
    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapView 销毁后不在处理新接收的位置
            if (location == null || baiduMap == null) {
                return;
            }
            latLng = new LatLng(location.getLatitude(), location.getLongitude());

            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            baiduMap.setMyLocationData(locData);

        }
    }

}
