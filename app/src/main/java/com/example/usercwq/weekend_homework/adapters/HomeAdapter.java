package com.example.usercwq.weekend_homework.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.usercwq.weekend_homework.R;
import com.example.usercwq.weekend_homework.beans.PersonBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by usercwq on 2019/9/1.
 */

public class HomeAdapter extends RecyclerView.Adapter {
    private ArrayList<PersonBean.DataBean.DatasBean> list;
    private Context context;

    public HomeAdapter(ArrayList<PersonBean.DataBean.DatasBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyItem extends RecyclerView.ViewHolder {
        @BindView(R.id.uv_home_item1_img)
        ImageView uvHomeItem1Img;
        @BindView(R.id.tv_home_item1_title)
        TextView tvHomeItem1Title;
        @BindView(R.id.tv_home_item1_id)
        TextView tvHomeItem1Id;

        public MyItem(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class MyItem2 extends RecyclerView.ViewHolder {
        @BindView(R.id.uv_home_item2_img)
        ImageView uvHomeItem2Img;
        @BindView(R.id.tv_home_item2_title)
        TextView tvHomeItem2Title;
        @BindView(R.id.tv_home_item2_id)
        TextView tvHomeItem2Id;
        public MyItem2(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = null;
        if (viewType == 1) {
            inflate = LayoutInflater.from(context).inflate(R.layout
                    .activity_home_adapter_item1, parent, false);
            MyItem myItem = new MyItem(inflate);
            return myItem;
        } else {
            inflate = LayoutInflater.from(context).inflate(R.layout
                    .activity_home_adapter_item2, parent, false);
            MyItem2 myItem2 = new MyItem2(inflate);
            return myItem2;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        int type = getItemViewType(position);
        if(type==1){
            MyItem myItem = (MyItem) holder;
            Glide.with(context).load(list.get(position).getEnvelopePic()).into(myItem.uvHomeItem1Img);
            myItem.tvHomeItem1Id.setText(list.get(position).getId()+"");
            myItem.tvHomeItem1Title.setText(list.get(position).getTitle());

        }else{
           MyItem2 myItem2= (MyItem2) holder;
            Glide.with(context).load(list.get(position).getEnvelopePic()).into(myItem2.uvHomeItem2Img);
            myItem2.tvHomeItem2Id.setText(list.get(position).getId()+"");
            myItem2.tvHomeItem2Title.setText(list.get(position).getTitle());
        }
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onLongClick.onLongItem(position);
                return false;
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getId() % 3 == 0) {
            return 1;
        } else {
            return 2;
        }

    }
    public OnLongClick onLongClick;

    public void setOnLongClick(OnLongClick onLongClick) {
        this.onLongClick = onLongClick;
    }

    public interface OnLongClick{
        void onLongItem(int position);
    }
}
