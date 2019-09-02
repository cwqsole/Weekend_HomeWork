package com.example.usercwq.weekend_homework.beans;

import java.util.ArrayList;

/**
 * Created by usercwq on 2019/9/1.
 */

public class EventBean  {
    private ArrayList<PersonBean.DataBean.DatasBean> list;
    private int postion;

    public EventBean(ArrayList<PersonBean.DataBean.DatasBean> list, int postion) {
        this.list = list;
        this.postion = postion;
    }

    public ArrayList<PersonBean.DataBean.DatasBean> getList() {
        return list;
    }

    public void setList(ArrayList<PersonBean.DataBean.DatasBean> list) {
        this.list = list;
    }

    public int getPostion() {
        return postion;
    }

    public void setPostion(int postion) {
        this.postion = postion;
    }
}
