package com.example.usercwq.weekend_homework.beans;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.widget.Toast;

import com.example.usercwq.weekend_homework.VpActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by usercwq on 2019/9/1.
 */

public class MyReceiver3 extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int ppppp = intent.getIntExtra("ppppp", 0);
        int id = intent.getIntExtra("id", 0);
        ArrayList<PersonBean.DataBean.DatasBean> data = intent.getParcelableArrayListExtra("data");
        Toast.makeText(context,id+"", Toast.LENGTH_SHORT).show();
        EventBus.getDefault().postSticky(new EventBean(data,ppppp));
        Intent vpintent = new Intent(context, VpActivity.class);
        context.startActivity(vpintent);

    }
}
