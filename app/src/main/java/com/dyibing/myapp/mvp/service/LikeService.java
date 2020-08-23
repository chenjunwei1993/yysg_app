package com.dyibing.myapp.mvp.service;

import com.dyibing.myapp.net.HttpResult;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

public interface LikeService {

    /**
     * 保存点赞
     *
     * @return
     */
    @Headers("Content-Type:application/json")
    @POST("walfare/saveLikes")
    Observable<HttpResult> saveLikes(@Body RequestBody body);

}
