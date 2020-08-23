package com.dyibing.myapp.mvp.model;

import com.dyibing.myapp.bean.CurrentDateTaskBean;
import com.dyibing.myapp.bean.FinishStatusBean;
import com.dyibing.myapp.bean.LoginBean;
import com.dyibing.myapp.bean.UserInfoBean;
import com.dyibing.myapp.mvp.service.LikeService;
import com.dyibing.myapp.mvp.service.LoginService;
import com.dyibing.myapp.net.HttpResult;
import com.dyibing.myapp.net.RetrofitHelper;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

public class LoginModel extends BaseModel {

    public Subscription login(RequestBody body, Subscriber subscriber) {
        Observable<LoginBean> observable = RetrofitHelper
                .getService(LoginService.class)
                .login(body)
                .map(new HttpResultFunc<LoginBean>());
        return toSubscribe(observable, subscriber);
    }

    public Subscription getUserInfo(Subscriber subscriber) {
        Observable<UserInfoBean> observable = RetrofitHelper
                .getService(LoginService.class)
                .getUserInfo()
                .map(new HttpResultFunc<UserInfoBean>());
        return toSubscribe(observable, subscriber);
    }

    public Subscription getUserFinishTaskStatus(Subscriber subscriber) {
        Observable<FinishStatusBean> observable = RetrofitHelper
                .getService(LoginService.class)
                .getUserFinishTaskStatus()
                .map(new HttpResultFunc<FinishStatusBean>());
        return toSubscribe(observable, subscriber);
    }
    public Subscription receiveForestCoin(RequestBody body, Subscriber subscriber) {
        Observable<HttpResult> observable = RetrofitHelper
                .getService(LoginService.class)
                .receiveForestCoin(body);
        return toSubscribe(observable, subscriber);
    }



}
