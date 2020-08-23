package com.dyibing.myapp.mvp.service;

import com.dyibing.myapp.bean.CourseBean;
import com.dyibing.myapp.bean.CurrentDateTaskBean;
import com.dyibing.myapp.net.HttpResult;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

public interface ICourseService {

    /**
     * 获取课表信息
     *
     * 当天的星期：monday：星期一，tuesday：星期二，wednesday：星期三，thursday：星期四，friday：星期五，saturday：星期六，sunday：星期日
     * @return
     */
    @Headers("Content-Type:application/json")
    @GET("course/getCourseInfo")
    Observable<HttpResult<CourseBean>> getCourseInfo(@Query("userId") String userId,
                                                     @Query("todayDate") String currentDateWeek);

}
