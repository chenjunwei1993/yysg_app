package com.dyibing.myapp.mvp.view;

import com.dyibing.myapp.bean.WaitingCashTaskBean;
import com.dyibing.myapp.net.HttpResult;

import java.util.List;

public interface WaitingCashTaskView extends IBaseView{
    void onTaskList(List<WaitingCashTaskBean> taskBeans);
    void onUpdateCashTask(HttpResult result);
}
