package com.example.usercwq.weekend_homework.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.usercwq.weekend_homework.R;

import java.util.ArrayList;

/**
 * Created by usercwq on 2019/9/1.
 */

public class VpAdapoter extends FragmentPagerAdapter {
    private ArrayList<Fragment> list;
    private Context context;
    private String [] message=new String[]{"首页","网页"};
    private int [] image=new int[]{R.drawable.home_tab1,R.drawable.home_tab2};

    public VpAdapoter(FragmentManager fm, ArrayList<Fragment> list, Context context) {
        super(fm);
        this.list = list;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }


    public View setCastomView(int poasion) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.activity_vp_adapter, null,
                false);
        ImageView iv_tab_img = inflate.findViewById(R.id.iv_tab_img);
        TextView tv_tab_home = inflate.findViewById(R.id.tv_tab_home);
        iv_tab_img.setImageResource(image[poasion]);
        tv_tab_home.setText(message[poasion]);
        return inflate;
    }
}
