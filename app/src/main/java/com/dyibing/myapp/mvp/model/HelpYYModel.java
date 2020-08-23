package com.dyibing.myapp.mvp.model;

import com.dyibing.myapp.bean.CurrentDateTaskBean;
import com.dyibing.myapp.bean.WelfareBean;
import com.dyibing.myapp.mvp.model.BaseModel;
import com.dyibing.myapp.mvp.service.HelpYYService;
import com.dyibing.myapp.mvp.service.LikeService;
import com.dyibing.myapp.net.HttpResult;
import com.dyibing.myapp.net.RetrofitHelper;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

public class HelpYYModel extends BaseModel {

//    public Subscription donationForestCoin(String id,int publicWelfareId,int donationCount, Subscriber subscriber) {
//        Observable<HttpResult> observable = RetrofitHelper
//                .getService(HelpYYService.class)
//                .donationForestCoin(id,publicWelfareId,donationCount);
//        return toSubscribe(observable, subscriber);
//    }

    public Subscription donationForestCoin(RequestBody body, Subscriber subscriber) {
        Observable<HttpResult> observable = RetrofitHelper
                .getService(HelpYYService.class)
                .donationForestCoin(body);
        return toSubscribe(observable, subscriber);
    }

    public Subscription getWelfareRankTenList(int pageNo,int pageSize,String ids, Subscriber subscriber) {
        Observable<WelfareBean> observable = RetrofitHelper
                .getService(HelpYYService.class)
                .getWelfareRankTenList(pageNo,pageSize,ids)
                .map(new HttpResultFunc<WelfareBean>());
        return toSubscribe(observable, subscriber);
    }

    public Subscription getWelfareRankMoreList(int pageNo,int pageSize,String ids, Subscriber subscriber) {
        Observable<WelfareBean> observable = RetrofitHelper
                .getService(HelpYYService.class)
                .getWelfareRankMoreList(pageNo,pageSize,ids)
                .map(new HttpResultFunc<WelfareBean>());
        return toSubscribe(observable, subscriber);
    }

}
