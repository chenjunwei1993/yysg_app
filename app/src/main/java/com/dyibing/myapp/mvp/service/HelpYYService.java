package com.dyibing.myapp.mvp.service;

import com.dyibing.myapp.bean.WelfareBean;
import com.dyibing.myapp.net.HttpResult;

import androidx.recyclerview.widget.AsyncDifferConfig;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface HelpYYService{

//    /**
//     * 捐赠森林币
//     *
//     * @return
//     */
//    @Headers("Content-Type:application/json")
//    @FormUrlEncoded
//    @POST("walfare/donationForestCoin")
//    Observable<HttpResult> donationForestCoin(@Field("userId") String userId,
//                                              @Field("publicWelfareId") int publicWelfareId,
//                                              @Field("donationCount") int donationCount);

    /**
     * 捐赠森林币
     *
     * @return
     */
    @Headers("Content-Type:application/json")
    @POST("walfare/donationForestCoin")
    Observable<HttpResult> donationForestCoin(@Body RequestBody body);

    /**
     * 公益信息查询
     *
     * @return
     */
    @Headers("Content-Type:application/json")
    @GET("walfare/getWelfareRankTenList")
    Observable<HttpResult<WelfareBean>> getWelfareRankTenList(@Query("pageNo") int pageNo,
                                                              @Query("pageSize") int pageSize,
                                                              @Query("ids") String ids);

    /**
     * 公益信息查询
     *
     * @return
     */
    @Headers("Content-Type:application/json")
    @GET("walfare/getWelfareRankMoreList")
    Observable<HttpResult<WelfareBean>> getWelfareRankMoreList(@Query("pageNo") int pageNo,
                                                              @Query("pageSize") int pageSize,
                                                              @Query("ids") String ids);

}
