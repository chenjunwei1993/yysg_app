package com.dyibing.myapp.mvp.presenter;

import android.content.Context;

import com.dyibing.myapp.bean.CourseBean;
import com.dyibing.myapp.bean.CurrentDateTaskBean;
import com.dyibing.myapp.mvp.model.CourseModel;
import com.dyibing.myapp.mvp.view.CourseView;
import com.dyibing.myapp.mvp.view.IBaseView;
import com.dyibing.myapp.mvp.view.TasksView;
import com.dyibing.myapp.net.rx.ProgressSubscriber;

import rx.Subscription;

public class CoursePresenter extends BasePresenter {

    private final CourseModel mModel;

    public CoursePresenter(Context mContext, IBaseView view) {
        super(mContext, view);
        mModel = new CourseModel();
    }

    /**
     * 获取课表信息
     * 当天的星期：monday：星期一，tuesday：星期二，wednesday：星期三，thursday：星期四，friday：星期五，saturday：星期六，sunday：星期日
     */

    public void getCourseInfo(String userId,String currentDateWeek) {
        Subscription subscription = mModel.getCourseInfo(userId,currentDateWeek,new ProgressSubscriber(o -> ((CourseView) mView).onCourseInfo((CourseBean) o), mContext));
        subList.add(subscription);
    }


}
