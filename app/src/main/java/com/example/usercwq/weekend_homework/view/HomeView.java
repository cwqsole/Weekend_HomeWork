package com.example.usercwq.weekend_homework.view;

import com.example.usercwq.weekend_homework.beans.PersonBean;

import java.util.ArrayList;

/**
 * Created by usercwq on 2019/9/1.
 */

public interface HomeView {
    void setDatas(ArrayList<PersonBean.DataBean.DatasBean> list);
    void showToast(String str);
}
