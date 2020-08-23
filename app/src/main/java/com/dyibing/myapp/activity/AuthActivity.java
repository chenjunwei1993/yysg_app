package com.dyibing.myapp.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.LogUtils;
import com.dyibing.myapp.R;
import com.dyibing.myapp.bean.DataCenter;
import com.dyibing.myapp.bean.FinishStatusBean;
import com.dyibing.myapp.bean.LoginBean;
import com.dyibing.myapp.bean.UserInfoBean;
import com.dyibing.myapp.bean.WXTicketBean;
import com.dyibing.myapp.bean.WXTokenBean;
import com.dyibing.myapp.common.Constant;
import com.dyibing.myapp.mvp.presenter.LoginPresenter;
import com.dyibing.myapp.mvp.presenter.WXAuthPresenter;
import com.dyibing.myapp.mvp.view.LoginView;
import com.dyibing.myapp.mvp.view.WXAuthView;
import com.dyibing.myapp.net.HttpResult;
import com.dyibing.myapp.utils.SingleToast;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.diffdev.DiffDevOAuthFactory;
import com.tencent.mm.opensdk.diffdev.IDiffDevOAuth;
import com.tencent.mm.opensdk.diffdev.OAuthErrCode;
import com.tencent.mm.opensdk.diffdev.OAuthListener;

import java.util.HashMap;
import java.util.Random;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class AuthActivity extends AppCompatActivity implements WXAuthView, LoginView {
    @BindView(R.id.img_wx)
    ImageView img_wx;

    private IDiffDevOAuth oauth;
    private WXAuthPresenter wxAuthPresenter;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);
        oauth = DiffDevOAuthFactory.getDiffDevOAuth();
        loginPresenter = new LoginPresenter(this, this);
        wxAuthPresenter = new WXAuthPresenter(this, this);
        wxAuthPresenter.getWXTokenBean();
    }

    @Override
    public void onWXToken(WXTokenBean wxTokenBean) {
        String access_token = wxTokenBean.getAccess_token();
        if (!TextUtils.isEmpty(access_token)) {
            wxAuthPresenter.getWXTicketBean(access_token);
        }
    }

    @Override
    public void onWXTicket(WXTicketBean wxTicketBean) {
        String ticket = wxTicketBean.getTicket();
        if (!TextUtils.isEmpty(ticket)) {
            auth(ticket);
        }
    }

    private String getNonceStr() {
        Random r = new Random(System.currentTimeMillis());
        return EncryptUtils.encryptMD5ToString((Constant.APP_ID +
                r.nextInt(10000) + System.currentTimeMillis()));
    }

    private String getTimestamp() {
        return String.valueOf(System.currentTimeMillis());
    }

    private String getSignature(String noncestr, String timestamp, String sdk_ticket) {
        StringBuilder str = new StringBuilder();
        str.append("appid=" + Constant.APP_ID);
        str.append("&noncestr=" + noncestr);
        str.append("&sdk_ticket=" + sdk_ticket);
        str.append("&timestamp=" + timestamp);
        return EncryptUtils.encryptSHA1ToString(str.toString());
    }

    private void auth(String ticket) {
        String noncestr = getNonceStr();
        String timestamp = getTimestamp();
        String signature = getSignature(noncestr, timestamp, ticket);

        try {
            oauth.auth(Constant.APP_ID, "snsapi_userinfo", noncestr, timestamp, signature, new OAuthListener() {
                @Override
                public void onAuthGotQrcode(String s, byte[] imgBuf) {
                    if (imgBuf != null) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(imgBuf, 0, imgBuf.length);
                        runOnUiThread(() -> img_wx.setImageBitmap(bitmap));
                    }
                }

                @Override
                public void onQrcodeScanned() {
                    LogUtils.dTag("OAuthListener", "onQrcodeScanned");
                }

                @Override
                public void onAuthFinish(OAuthErrCode oAuthErrCode, String authCode) {
                    LogUtils.dTag("OAuthListener", "onAuthFinish");
                    if (oAuthErrCode.getCode() == 0) {
                        HashMap<String, Object> paramsMap = new HashMap<>();
                        paramsMap.put("code", authCode);
                        String strEntity = new Gson().toJson(paramsMap);
                        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), strEntity);
                        loginPresenter.login(body);
                    } else {
                        //刷新二维码
                        wxAuthPresenter.getWXTokenBean();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        oauth.stopAuth();
        oauth = null;
        wxAuthPresenter.onDestory();
    }

    @Override
    public void onLogin(LoginBean loginBean) {
        DataCenter.getInstance().setUserId(loginBean.getUserOpenId());
        DataCenter.getInstance().setToken(loginBean.getToken());
        getSharedPreferences(Constant.PREFERENCES_DB, Context.MODE_PRIVATE).edit().putString("userid", loginBean.getUserOpenId()).apply();
        getSharedPreferences(Constant.PREFERENCES_DB, Context.MODE_PRIVATE).edit().putString("token", loginBean.getToken()).apply();
        loginPresenter.getUserInfo();
    }

    @Override
    public void onUserInfo(UserInfoBean userInfoBean) {
        if (userInfoBean != null) {
            DataCenter.getInstance().getUser().setNickname(userInfoBean.getNickName());
            DataCenter.getInstance().getUser().setBirthday(userInfoBean.getBirthday());
            DataCenter.getInstance().getUser().setUserSex(userInfoBean.getUserSex());
            DataCenter.getInstance().getUser().setUserHobby(userInfoBean.getUserHobby());
            DataCenter.getInstance().getUser().setLikeGift(userInfoBean.getLikeGift());
            DataCenter.getInstance().getUser().setLikeCartoon(userInfoBean.getLikeCartoon());
            DataCenter.getInstance().getUser().setLikeIdol(userInfoBean.getLikeIdol());
            DataCenter.getInstance().getUser().setLikeGame(userInfoBean.getLikeGame());
            DataCenter.getInstance().getUser().setAvatarUrl(userInfoBean.getAvatarUrl());
            DataCenter.getInstance().getUser().setForestCoinCount(userInfoBean.getForestCoinCount());
            DataCenter.getInstance().getUser().setLikesCount(userInfoBean.getLikesCount());
            DataCenter.getInstance().getUser().setLogin(true);
            getSharedPreferences(Constant.PREFERENCES_DB, Context.MODE_PRIVATE).edit().putBoolean(Constant.FIRST_LOGIN, false).apply();
            String status = getSharedPreferences(Constant.PREFERENCES_DB, Context.MODE_PRIVATE).getString(Constant.RECEIVE_FOREST_CURRENCY, "");
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            getSharedPreferences(Constant.PREFERENCES_DB, Context.MODE_PRIVATE).edit().putBoolean(Constant.FIRST_LOGIN, true).apply();
            SingleToast.showMsg("还没有创建个人信息哟！");
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public void onUserFinishTaskStatus(FinishStatusBean finishStatusBean) {

    }

    @Override
    public void onReceiveForestCoin(HttpResult httpResult) {

    }

}
