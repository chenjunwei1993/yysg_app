package com.dyibing.myapp.bean;

import android.content.Context;
import android.text.TextUtils;

import com.dyibing.myapp.MyApplication;
import com.dyibing.myapp.common.Constant;

import java.util.HashMap;
import java.util.Map;


/**
 * 数据中心
 * Created by Alan on 20-7-10
 */

public class DataCenter {
    private static DataCenter instance;
    private static User mUser = new User();
    private static Map<String, String> mLotteryType = new HashMap<>();

    public static DataCenter getInstance() {
        if (instance == null) {
            instance = new DataCenter();
        }
        return instance;
    }


    public void setUser(User user) {
//        mUser.setDomain(user.getDomain());
//        mUser.setImgDomain(user.getImgDomain());
        mUser.setLogin(user.isLogin());
//        mUser.setUserId(user.getUserId());
        mUser.setUsername(user.getUsername());
        mUser.setPassword(user.getPassword());
//        mUser.setNickname(user.getNickname());
//        mUser.setBalance(user.getBalance());
        mUser.setAvatarUrl(user.getAvatarUrl());
        mUser.setToken(user.getToken());
    }

    public void clearUser() {
        mUser.setLogin(false);
        mUser.setUserId(null);
        mUser.setUsername("");
        mUser.setPassword("");
        mUser.setNickname("");
        mUser.setAvatarUrl("");
        mUser.setToken("");
    }

    public User getUser() {
        return mUser;
    }


    public String getToken() {
        if (TextUtils.isEmpty(mUser.getToken())){
            mUser.setToken(MyApplication.getContext().getSharedPreferences(Constant.PREFERENCES_DB, Context.MODE_PRIVATE).getString("token", ""));
        }
            return mUser.getToken();
    }

    public void setToken(String cookie) {
        mUser.setToken(cookie);
    }

    public void setUserId(String userId) {
        mUser.setUserId(userId);
    }

    public String getUserId() {
        if (TextUtils.isEmpty(mUser.getUserId())) {
            mUser.setUserId(MyApplication.getContext().getSharedPreferences(Constant.PREFERENCES_DB, Context.MODE_PRIVATE).getString("userid", ""));
        }
        return mUser.getUserId();
    }

    public boolean isLogin() {
        return mUser.isLogin();
    }

}
