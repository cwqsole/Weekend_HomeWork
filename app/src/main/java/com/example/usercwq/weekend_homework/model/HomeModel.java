package com.example.usercwq.weekend_homework.model;

import com.example.usercwq.weekend_homework.beans.ApiService;
import com.example.usercwq.weekend_homework.beans.PersonBean;
import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by usercwq on 2019/9/1.
 */

public class HomeModel {
    public void loadDatas(int page, final ResultCallBack resultCallBack) {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiService.url)
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Observable<PersonBean> datas = apiService.getDatas(page);
        datas.map(new Function<PersonBean,List<PersonBean.DataBean.DatasBean>>() {
            @Override
            public List<PersonBean.DataBean.DatasBean> apply(PersonBean personBean) throws Exception {
                return personBean.getData().getDatas();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<PersonBean.DataBean.DatasBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<PersonBean.DataBean.DatasBean> datasBeans) {
                            if(datasBeans!=null){
                                resultCallBack.onSussecc((ArrayList<PersonBean.DataBean.DatasBean>) datasBeans);
                            }else{
                                resultCallBack.onFelid("没有数据");
                            }
                    }

                    @Override
                    public void onError(Throwable e) {
                        resultCallBack.onFelid("网络请求失败"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public interface ResultCallBack{
        void onSussecc(ArrayList<PersonBean.DataBean.DatasBean> list);
        void onFelid(String str);
    }
}
