package com.dyibing.myapp.mvp.service;

import com.dyibing.myapp.bean.CurrentDateTaskBean;
import com.dyibing.myapp.net.HttpResult;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

public interface ITaskService {


    /**
     * 获取今日任务
     *
     * @return
     */
    @Headers("Content-Type:application/json")
    @GET("task/getCurrentDateTask")
    Observable<HttpResult<CurrentDateTaskBean>> getCurrentDateTask();

    /**
     * 更改完成状态
     *
     * @return
     */
    @Headers("Content-Type:application/json")
    @POST("task/updateCurrentDateTask")
    Observable<HttpResult> updateCurrentDateTask(@Body RequestBody body);

}
