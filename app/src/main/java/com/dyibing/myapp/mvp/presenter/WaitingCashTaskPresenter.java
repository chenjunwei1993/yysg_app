package com.dyibing.myapp.mvp.presenter;

import android.content.Context;

import com.dyibing.myapp.bean.WaitingCashTaskBean;
import com.dyibing.myapp.mvp.model.WaitingCashTaskModel;
import com.dyibing.myapp.mvp.view.IBaseView;
import com.dyibing.myapp.mvp.view.WaitingCashTaskView;
import com.dyibing.myapp.net.HttpResult;
import com.dyibing.myapp.net.rx.ProgressSubscriber;

import java.util.List;

import okhttp3.RequestBody;
import rx.Subscription;

public class WaitingCashTaskPresenter extends BasePresenter {

    private final WaitingCashTaskModel mModel;

    public WaitingCashTaskPresenter(Context mContext, IBaseView view) {
        super(mContext, view);
        mModel = new WaitingCashTaskModel();
    }

    /**
     * 待总现任务
     */

    public void getCurrentDateTask() {
        Subscription subscription = mModel.getTaskList(new ProgressSubscriber(o -> ((WaitingCashTaskView) mView).onTaskList((List<WaitingCashTaskBean>)o), mContext));
        subList.add(subscription);
    }

    /**
     * 更改任务状态
     */

    public void updateCashTask(RequestBody taskId) {
        Subscription subscription = mModel.updateCashTask(taskId,new ProgressSubscriber(o -> ((WaitingCashTaskView) mView).onUpdateCashTask((HttpResult) o), mContext));
        subList.add(subscription);
    }

}
