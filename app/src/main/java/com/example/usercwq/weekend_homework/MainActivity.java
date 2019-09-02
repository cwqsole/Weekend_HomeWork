package com.example.usercwq.weekend_homework;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.usercwq.weekend_homework.adapters.VpAdapoter;
import com.example.usercwq.weekend_homework.fragment.HomeFragment;
import com.example.usercwq.weekend_homework.fragment.WangFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.tablayout)
    TabLayout tablayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();

    }

    private void initView() {
        setSupportActionBar(toolbar);
        vpTab();

    }

    private void vpTab() {
        HomeFragment homeFragment = new HomeFragment();
        WangFragment wangFragment = new WangFragment();
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(homeFragment);
        fragments.add(wangFragment);
        VpAdapoter vpAdapoter = new VpAdapoter(getSupportFragmentManager(),fragments,this);
        vp.setAdapter(vpAdapoter);
        tablayout.setupWithViewPager(vp);
        for(int i=0;i<vpAdapoter.getCount();i++){
            tablayout.getTabAt(i).setCustomView(vpAdapoter.setCastomView(i));
        }
    }
}
