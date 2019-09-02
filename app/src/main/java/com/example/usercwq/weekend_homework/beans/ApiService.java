package com.example.usercwq.weekend_homework.beans;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by usercwq on 2019/9/1.
 */

public interface ApiService {
    String url="https://www.wanandroid.com/";
    @GET("project/list/{page}/json?cid=294")
    Observable<PersonBean> getDatas(@Path("page") int page);
}
