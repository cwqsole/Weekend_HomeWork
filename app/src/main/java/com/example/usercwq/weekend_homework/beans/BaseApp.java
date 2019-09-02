package com.example.usercwq.weekend_homework.beans;

import android.app.Application;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by usercwq on 2019/9/1.
 */

public class BaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        UMConfigure.setLogEnabled(true);
        //58edcfeb310c93091c000be2 5965ee00734be40b580001a0
        UMConfigure.init(this,"5d6b68310cafb295ce000e9a"
                ,"umeng",UMConfigure.DEVICE_TYPE_PHONE,"");
        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        //豆瓣RENREN平台目前只能在服务器端配置
        PlatformConfig.setSinaWeibo("1285335677", "b18a16c6179cd20ceb0da45445edcc16",
                "http://sns.whalecloud.com");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");


        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

    }

}
