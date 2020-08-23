package com.dyibing.myapp.mvp.presenter;

import android.content.Context;

import com.dyibing.myapp.bean.WelfareBean;
import com.dyibing.myapp.mvp.model.HelpYYModel;
import com.dyibing.myapp.mvp.view.HelpYYView;
import com.dyibing.myapp.mvp.view.IBaseView;
import com.dyibing.myapp.mvp.view.LikesView;
import com.dyibing.myapp.net.HttpResult;
import com.dyibing.myapp.net.rx.ProgressSubscriber;

import okhttp3.RequestBody;
import rx.Subscription;

public class HelpYYPresenter extends BasePresenter {

    private final HelpYYModel mModel;

    public HelpYYPresenter(Context mContext, IBaseView view) {
        super(mContext, view);
        mModel = new HelpYYModel();
    }

//    /**
//     * 捐赠森林币
//     */
//
//    public void donationForestCoin(String id,int publicWelfareId,int donationCount) {
//        Subscription subscription = mModel.donationForestCoin(id,publicWelfareId,donationCount,
//                new ProgressSubscriber(o -> ((HelpYYView) mView).onDonationForestCoin((HttpResult) o), mContext));
//        subList.add(subscription);
//    }

    /**
     * 捐赠森林币
     */

    public void donationForestCoin(RequestBody body) {
        Subscription subscription = mModel.donationForestCoin(body,
                new ProgressSubscriber(o -> ((HelpYYView) mView).onDonationForestCoin((HttpResult) o), mContext));
        subList.add(subscription);
    }

    /**
     * 公益
     */

    public void getWelfareRankTenList(int pageNo,int pageSize,String ids) {
        Subscription subscription = mModel.getWelfareRankTenList(pageNo,pageSize,ids,
                new ProgressSubscriber(o -> ((HelpYYView) mView).onWelfareRankTenList((WelfareBean) o), mContext));
        subList.add(subscription);
    }

    /**
     * 公益
     */

    public void getWelfareRankMoreList(int pageNo,int pageSize,String ids) {
        Subscription subscription = mModel.getWelfareRankMoreList(pageNo,pageSize,ids,
                new ProgressSubscriber(o -> ((HelpYYView) mView).onWelfareRankTenList((WelfareBean) o), mContext));
        subList.add(subscription);
    }


}
