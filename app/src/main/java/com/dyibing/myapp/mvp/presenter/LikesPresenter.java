package com.dyibing.myapp.mvp.presenter;

import android.content.Context;

import com.dyibing.myapp.bean.CourseBean;
import com.dyibing.myapp.mvp.model.likesModel;
import com.dyibing.myapp.mvp.view.CourseView;
import com.dyibing.myapp.mvp.view.IBaseView;
import com.dyibing.myapp.mvp.view.LikesView;
import com.dyibing.myapp.net.HttpResult;
import com.dyibing.myapp.net.rx.ProgressSubscriber;

import okhttp3.RequestBody;
import rx.Subscription;

public class LikesPresenter extends BasePresenter {

    private final likesModel mModel;

    public LikesPresenter(Context mContext, IBaseView view) {
        super(mContext, view);
        mModel = new likesModel();
    }

    /**
     * 点赞
     */

    public void saveLikes(RequestBody body) {
        Subscription subscription = mModel.saveLikes(body,new ProgressSubscriber(o -> ((LikesView) mView).onSaveLikes((HttpResult) o), mContext));
        subList.add(subscription);
    }


}
