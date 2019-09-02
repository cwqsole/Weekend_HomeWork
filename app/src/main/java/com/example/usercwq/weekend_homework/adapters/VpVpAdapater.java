package com.example.usercwq.weekend_homework.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.usercwq.weekend_homework.beans.PersonBean;

import java.util.ArrayList;

/**
 * Created by usercwq on 2019/9/1.
 */

public class VpVpAdapater extends PagerAdapter {
    private ArrayList<String> list;
    private Context context;

    public VpVpAdapater(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        ImageView imageView = new ImageView(context);
        Glide.with(context).load(list.get(position)).into(imageView);
        container.addView(imageView);
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onLongItem.onLongClic(position);
                return false;
            }
        });
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
       // super.destroyItem(container, position, object);
        container.removeView((View) object);
    }
    private OnLongItem onLongItem;

    public void setOnLongItem(OnLongItem onLongItem) {
        this.onLongItem = onLongItem;
    }

    public interface OnLongItem{
        void onLongClic(int position);
    }
}
