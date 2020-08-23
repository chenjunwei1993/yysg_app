package com.dyibing.myapp.mvp.model;

import com.dyibing.myapp.bean.CourseBean;
import com.dyibing.myapp.bean.CurrentDateTaskBean;
import com.dyibing.myapp.mvp.model.BaseModel;
import com.dyibing.myapp.mvp.service.ICourseService;
import com.dyibing.myapp.mvp.service.ITaskService;
import com.dyibing.myapp.net.RetrofitHelper;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

public class CourseModel extends BaseModel {

    public Subscription getCourseInfo(String userId,String currentDateWeek,Subscriber subscriber) {
        Observable<CourseBean> observable = RetrofitHelper
                .getService(ICourseService.class)
                .getCourseInfo(userId,currentDateWeek)
                .map(new HttpResultFunc<CourseBean>());
        return toSubscribe(observable, subscriber);
    }

}
