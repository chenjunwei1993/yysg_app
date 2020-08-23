package com.dyibing.myapp.mvp.view;

import com.dyibing.myapp.bean.FinishStatusBean;
import com.dyibing.myapp.bean.LoginBean;
import com.dyibing.myapp.bean.UserInfoBean;
import com.dyibing.myapp.net.HttpResult;

public interface LoginView extends IBaseView{

    void onLogin(LoginBean loginBean);

    void onUserInfo(UserInfoBean userInfoBean);

    void onUserFinishTaskStatus(FinishStatusBean finishStatusBean);

    void onReceiveForestCoin(HttpResult httpResult);

}
