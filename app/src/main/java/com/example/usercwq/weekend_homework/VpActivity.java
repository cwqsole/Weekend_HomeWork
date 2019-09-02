package com.example.usercwq.weekend_homework;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usercwq.weekend_homework.adapters.VpVpAdapater;
import com.example.usercwq.weekend_homework.beans.EventBean;
import com.example.usercwq.weekend_homework.beans.PersonBean;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VpActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.vp_vp)
    ViewPager vpVp;
    @BindView(R.id.tv_count)
    TextView tvCount;
    private PopupWindow popupWindow;
    private View inflate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vp);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        if(Build.VERSION.SDK_INT>=23){
            String[] mPermissionList = new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this,mPermissionList,123);
        }
        initPopuWindow();
    }

    private void initPopuWindow() {
        inflate = LayoutInflater.from(this).inflate(R.layout.activity_popup, null, false);
        Button bt_baidu = inflate.findViewById(R.id.bt_baidu);
        Button bt_ymeng = inflate.findViewById(R.id.bt_ymeng);
        Button bt_jgts = inflate.findViewById(R.id.bt_jgts);
        bt_ymeng.setOnClickListener(this);
        bt_baidu.setOnClickListener(this);
        bt_jgts.setOnClickListener(this);
        popupWindow = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT,
                500);
        popupWindow.setBackgroundDrawable(null);
        popupWindow.setOutsideTouchable(true);
        inflate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

    }

    @Subscribe(sticky = true)
    public void getDatas(EventBean eventBean) {
        final ArrayList<PersonBean.DataBean.DatasBean> list = eventBean.getList();
        int postion = eventBean.getPostion();
        ArrayList<String> image = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            image.add(list.get(i).getEnvelopePic());
        }
        VpVpAdapater vpVpAdapater = new VpVpAdapater(image,this);
        vpVp.setAdapter(vpVpAdapater);
        vpVp.setCurrentItem(postion);
        vpVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pageVp(position,list.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vpVpAdapater.setOnLongItem(new VpVpAdapater.OnLongItem() {
            @Override
            public void onLongClic(int position) {
                popupWindow.showAsDropDown(inflate,Gravity.CENTER,Gravity.CENTER);
            }
        });
    }

    private void pageVp(int position, int size) {
        tvCount.setText("第"+(position+1)+"张共"+size+"页");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_ymeng:
                //5d6b68310cafb295ce000e9a
                Toast.makeText(this, "我是友盟", Toast.LENGTH_SHORT).show();
                shareBorad();
                break;
            case R.id.bt_jgts:
                Toast.makeText(this, "我是极光推送", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bt_baidu:
                Intent intent = new Intent(VpActivity.this, BaiDuActivity.class);
                startActivity(intent);
                //U837aBRSP3e7ivQvDikM7Q1N4yUnzVCx
                Toast.makeText(this, "我是百度地图", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void shareBorad() {
        UMImage thumb =  new UMImage(this,"https://ww1.sinaimg.cn/large/0065oQSqly1ft5q7ys128j30sg10gnk5.jpg");

        new ShareAction(VpActivity.this)
                .withText("hello")
                .setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN)
                .withMedia(thumb)
                .setCallback(shareListener).open();
    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(VpActivity.this,"成功了",Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(VpActivity.this,"失败"+t.getMessage(),Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(VpActivity.this,"取消了",Toast.LENGTH_LONG).show();

        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
