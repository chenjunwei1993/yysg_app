package com.dyibing.myapp.mvp.model;

import com.dyibing.myapp.bean.EasyHttpResult;
import com.dyibing.myapp.bean.WaitingCashTaskBean;
import com.dyibing.myapp.mvp.service.IWaitingCashTaskService;
import com.dyibing.myapp.net.HttpResult;
import com.dyibing.myapp.net.RetrofitHelper;

import java.util.List;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

public class WaitingCashTaskModel extends BaseModel {

    public Subscription getTaskList(Subscriber subscriber) {
        Observable<List<WaitingCashTaskBean>> observable = RetrofitHelper
                .getService(IWaitingCashTaskService.class)
                .getTaskList()
                .map(new HttpResultFunc<List<WaitingCashTaskBean>>());
        return toSubscribe(observable, subscriber);
    }

    public Subscription updateCashTask(RequestBody logTaskId, Subscriber subscriber) {
        Observable<HttpResult> observable = RetrofitHelper
                .getService(IWaitingCashTaskService.class)
                .updateCashTask(logTaskId);
        return toSubscribe(observable, subscriber);
    }

}
