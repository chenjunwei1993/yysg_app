package com.dyibing.myapp.mvp.view;

import com.dyibing.myapp.bean.ForestCoinBean;
import com.dyibing.myapp.bean.LoginBean;
import com.dyibing.myapp.bean.UserInfoBean;
import com.dyibing.myapp.net.HttpResult;

public interface LoginView extends IBaseView{

    void onLogin(LoginBean loginBean);

    void onUserInfo(UserInfoBean userInfoBean);

    void onReceiveForestCoinStatus(HttpResult<ForestCoinBean> httpResult);
}
