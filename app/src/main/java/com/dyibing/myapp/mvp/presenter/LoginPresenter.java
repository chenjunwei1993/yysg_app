package com.dyibing.myapp.mvp.presenter;

import android.content.Context;

import com.dyibing.myapp.bean.FinishStatusBean;
import com.dyibing.myapp.bean.LoginBean;
import com.dyibing.myapp.bean.UserInfoBean;
import com.dyibing.myapp.mvp.model.LoginModel;
import com.dyibing.myapp.mvp.view.IBaseView;
import com.dyibing.myapp.mvp.view.LikesView;
import com.dyibing.myapp.mvp.view.LoginView;
import com.dyibing.myapp.net.HttpResult;
import com.dyibing.myapp.net.rx.ProgressSubscriber;

import okhttp3.RequestBody;
import rx.Subscription;

public class LoginPresenter extends BasePresenter {

    private final LoginModel mModel;

    public LoginPresenter(Context mContext, IBaseView view) {
        super(mContext, view);
        mModel = new LoginModel();
    }


    /**
     * 登录
     */

    public void login(RequestBody body) {
        Subscription subscription = mModel.login(body,new ProgressSubscriber(o -> ((LoginView) mView).onLogin((LoginBean) o), mContext));
        subList.add(subscription);
    }

    /**
     * 获取用户信息
     */

    public void getUserInfo() {
        Subscription subscription = mModel.getUserInfo(new ProgressSubscriber(o -> ((LoginView) mView).onUserInfo((UserInfoBean) o), mContext));
        subList.add(subscription);
    }
    /**
     * 获取用户前一天任务完成状态
     */

    public void getUserFinishTaskStatus() {
        Subscription subscription = mModel.getUserFinishTaskStatus(new ProgressSubscriber(o -> ((LoginView) mView).onUserFinishTaskStatus((FinishStatusBean) o), mContext));
        subList.add(subscription);
    }

    /**
     * 领取森林币
     */

    public void receiveForestCoin(RequestBody body) {
        Subscription subscription = mModel.receiveForestCoin(body,new ProgressSubscriber(o -> ((LoginView) mView).onReceiveForestCoin((HttpResult) o), mContext));
        subList.add(subscription);
    }

}
