package com.dyibing.myapp.mvp.model;

import com.dyibing.myapp.mvp.service.IWaitingCashTaskService;
import com.dyibing.myapp.mvp.service.LikeService;
import com.dyibing.myapp.net.HttpResult;
import com.dyibing.myapp.net.RetrofitHelper;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

public class likesModel  extends BaseModel{

    public Subscription saveLikes(RequestBody body, Subscriber subscriber) {
        Observable<HttpResult> observable = RetrofitHelper
                .getService(LikeService.class)
                .saveLikes(body);
        return toSubscribe(observable, subscriber);
    }

}
