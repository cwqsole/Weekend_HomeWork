package com.example.usercwq.weekend_homework.presenter;

import android.support.v4.app.FragmentActivity;

import com.example.usercwq.weekend_homework.beans.PersonBean;
import com.example.usercwq.weekend_homework.model.HomeModel;
import com.example.usercwq.weekend_homework.view.HomeView;

import java.util.ArrayList;

/**
 * Created by usercwq on 2019/9/1.
 */

public class HomePresenter implements HomeModel.ResultCallBack {
    private final HomeView homeView;
    private final HomeModel homeModel;

    public HomePresenter(HomeView homeView) {
        this.homeView=homeView;
        homeModel = new HomeModel();
    }

    public void loadDatas(int page) {
        homeModel.loadDatas(page,this);
    }

    @Override
    public void onSussecc(ArrayList<PersonBean.DataBean.DatasBean> list) {
        homeView.setDatas(list);
    }

    @Override
    public void onFelid(String str) {
        homeView.showToast(str);
    }
}
