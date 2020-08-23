package com.dyibing.myapp.mvp.service;

import com.dyibing.myapp.bean.EasyHttpResult;
import com.dyibing.myapp.bean.WaitingCashTaskBean;
import com.dyibing.myapp.net.HttpResult;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

public interface IWaitingCashTaskService {


    /**
     * 获取待完成，待总换任务
     *
     * @return
     */
    @Headers("Content-Type:application/json")
    @GET("task/getTaskList")
    Observable<HttpResult<List<WaitingCashTaskBean>>> getTaskList();

    /**
     * 更改完成状态
     *
     * @return
     */
    @Headers("Content-Type:application/json")
    @POST("task/updateCashTask")
    Observable<HttpResult> updateCashTask(@Body RequestBody body);

}
