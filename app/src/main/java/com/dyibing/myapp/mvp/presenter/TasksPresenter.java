package com.dyibing.myapp.mvp.presenter;

import android.content.Context;

import com.dyibing.myapp.bean.CurrentDateTaskBean;
import com.dyibing.myapp.mvp.model.TaskModel;
import com.dyibing.myapp.mvp.view.IBaseView;
import com.dyibing.myapp.mvp.view.TasksView;
import com.dyibing.myapp.mvp.view.WaitingCashTaskView;
import com.dyibing.myapp.net.HttpResult;
import com.dyibing.myapp.net.rx.ProgressSubscriber;

import okhttp3.RequestBody;
import rx.Subscription;

public class TasksPresenter<T extends IBaseView> extends BasePresenter {

    private final TaskModel mModel;

    public TasksPresenter(Context mContext, IBaseView view) {
        super(mContext, view);
        this.mContext = mContext;
        this.mView = view;
        mModel = new TaskModel();

    }

    /**
     * 今日任务
     */

    public void getCurrentDateTask() {
        Subscription subscription = mModel.getCurrentDateTask(new ProgressSubscriber(o -> ((TasksView) mView).onCurrentDateTask((CurrentDateTaskBean)o), mContext));
        subList.add(subscription);
    }

    /**
     * 更改任务状态
     */

    public void updateCurrentDateTask(RequestBody taskId) {
        Subscription subscription = mModel.updateCurrentDateTask(taskId,new ProgressSubscriber(o -> ((TasksView) mView).onUpdateCurrentDateTask((HttpResult) o), mContext));
        subList.add(subscription);
    }


}
