package com.dyibing.myapp.mvp.model;

import com.dyibing.myapp.bean.CurrentDateTaskBean;
import com.dyibing.myapp.mvp.service.ITaskService;
import com.dyibing.myapp.mvp.service.IWaitingCashTaskService;
import com.dyibing.myapp.net.HttpResult;
import com.dyibing.myapp.net.RetrofitHelper;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

public class TaskModel extends BaseModel {

    public Subscription getCurrentDateTask(Subscriber subscriber) {
        Observable<CurrentDateTaskBean> observable = RetrofitHelper
                .getService(ITaskService.class)
                .getCurrentDateTask()
                .map(new HttpResultFunc<CurrentDateTaskBean>());
        return toSubscribe(observable, subscriber);
    }
    public Subscription updateCurrentDateTask(RequestBody body, Subscriber subscriber) {
        Observable<HttpResult> observable = RetrofitHelper
                .getService(ITaskService.class)
                .updateCurrentDateTask(body);
        return toSubscribe(observable, subscriber);
    }
}
