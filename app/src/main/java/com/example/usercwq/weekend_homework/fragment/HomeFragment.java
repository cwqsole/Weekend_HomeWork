package com.example.usercwq.weekend_homework.fragment;


import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.usercwq.weekend_homework.R;
import com.example.usercwq.weekend_homework.adapters.HomeAdapter;
import com.example.usercwq.weekend_homework.beans.MyReceiver3;
import com.example.usercwq.weekend_homework.beans.PersonBean;
import com.example.usercwq.weekend_homework.presenter.HomePresenter;
import com.example.usercwq.weekend_homework.view.HomeView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements HomeView{


    int page=1;
    @BindView(R.id.rclv)
    RecyclerView rclv;
    Unbinder unbinder;
    private ArrayList<PersonBean.DataBean.DatasBean> datasBeans;
    private HomeAdapter homeAdapter;
    public int position1;
    private MyReceiver3 myReceiver3;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(getView());
    }

    private void initView(View view) {
        intRclv();
        HomePresenter homePresenter = new HomePresenter(this);
        homePresenter.loadDatas(page);

    }

    private void intRclv() {
        rclv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rclv.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        datasBeans = new ArrayList<>();
        homeAdapter = new HomeAdapter(datasBeans,getActivity());
        rclv.setAdapter(homeAdapter);
        registerForContextMenu(rclv);
        homeAdapter.setOnLongClick(new HomeAdapter.OnLongClick() {
            @Override
            public void onLongItem(int position) {
                position1 = position;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setDatas(ArrayList<PersonBean.DataBean.DatasBean> list) {
        datasBeans.addAll(list);
        homeAdapter.notifyDataSetChanged();
    }

    @Override
    public void showToast(String str) {
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo
            menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(1,1,1,"发送广播");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 1:
                Intent intent = new Intent("ccc");
                intent.putExtra("ppppp",position1);
                intent.putExtra("id",datasBeans.get(position1).getId());
                intent.putParcelableArrayListExtra("data",datasBeans);
                getActivity().sendBroadcast(intent);
                break;


        }
        return super.onContextItemSelected(item);

    }

    @Override
    public void onPause() {
        super.onPause();
      getActivity().unregisterReceiver(myReceiver3);

    }

    @Override
    public void onResume() {
        super.onResume();
        //动态注册MyReceiver3
        myReceiver3 = new MyReceiver3();
        IntentFilter intentFilter = new IntentFilter("ccc");
        getActivity().registerReceiver(myReceiver3,intentFilter);
    }
}
