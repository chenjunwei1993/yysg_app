package com.dyibing.myapp.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dyibing.myapp.R;
import com.dyibing.myapp.bean.DataCenter;
import com.dyibing.myapp.bean.FinishStatusBean;
import com.dyibing.myapp.bean.LoginBean;
import com.dyibing.myapp.bean.UserInfoBean;
import com.dyibing.myapp.common.Constant;
import com.dyibing.myapp.mvp.presenter.LoginPresenter;
import com.dyibing.myapp.mvp.view.LoginView;
import com.dyibing.myapp.net.HttpResult;
import com.dyibing.myapp.utils.DateUtils;
import com.dyibing.myapp.utils.SingleToast;
import com.dyibing.myapp.utils.tts.AudioUtils;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class SplashActivity extends AppCompatActivity implements LoginView {

    @BindView(R.id.iv_finish_status)
    ImageView ivFinishStatus;
    @BindView(R.id.btn_getslb)
    Button btnGetslb;
    private String finishStatus;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SplashActivity.class);
        intent.putExtra(Constant.LOG_IN_AGAIN, Constant.LOG_IN_AGAIN);
        context.startActivity(intent);
    }


    @BindView(R.id.mViewPager)
    ViewPager mViewPager;
    @BindView(R.id.point1)
    ImageView point1;
    @BindView(R.id.point2)
    ImageView point2;
    @BindView(R.id.point3)
    ImageView point3;
    @BindView(R.id.point4)
    ImageView point4;
    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.ll_point)
    LinearLayout llPoint;

    //容器
    private List<View> mList = new ArrayList<>();


    /**
     * 权限部分
     */
    String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.RECORD_AUDIO
    };
    List<String> mPermissionList = new ArrayList<>();
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        AudioUtils.getInstance().init(this); //初始化语音对象
        loginPresenter = new LoginPresenter(this, this);
//        if (!TextUtils.isEmpty(DataCenter.getInstance().getUserId())) {
//            checkPermision();
//        } else {
//            initView();
//        }
        initView();
    }

    private void initView() {
        point1.setVisibility(View.VISIBLE);
        point2.setVisibility(View.VISIBLE);
        point3.setVisibility(View.VISIBLE);
        //设置默认图片
        setPointImg(true, false, false, false);

        View view1 = View.inflate(this, R.layout.pager_item_one, null);
        View view2 = View.inflate(this, R.layout.pager_item_two, null);
        View view3 = View.inflate(this, R.layout.pager_item_three, null);
        View view4 = View.inflate(this, R.layout.pager_item_four, null);
        view4.findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermision();
            }
        });

        mList.add(view1);
        mList.add(view2);
//        mList.add(view3);
        mList.add(view4);

        //设置适配器
        mViewPager.setAdapter(new GuideAdapter());

        //监听ViewPager滑动
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //pager切换
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        setPointImg(true, false, false, false);
                        String bobao = "小鹿公主丫丫，和爸爸妈妈生活在美丽的森林中，人类的活动，破坏了森林，也破坏了小鹿们的家园，森林的破坏，让小鹿丫丫与爸爸妈妈分离";
                        AudioUtils.getInstance().speakText(bobao); //播放语音
                        break;
                    case 1:
                        setPointImg(false, true, false, false);
                        String bobao2 = "丫丫在途中遇到了小伙伴，勇敢的小鸡木木，在木木的帮助下，丫丫积攒了好多的森林币，希望能够寻找到新的家园，小伙伴们，让我们和木木一起，帮助丫丫回家吧";
                        AudioUtils.getInstance().speakText(bobao2); //播放语音
                        break;
                    case 2:
                        setPointImg(false, false, true, false);
                        String bobao3 = "和爸爸妈妈一起，每天完成任务，领取森林币，将森林币投入公益池，每积攒到一定数量的森林币，我们就会和小朋友们一起，向指定公益项目贡献安心，帮助丫丫重建家园，每日投币最多的小伙伴，还会荣登公益榜，接受其他小伙伴的点赞哦，每年我们将邀请被点赞数最多的10位小伙伴，和丫丫木木一起参加公益夏令营！";
                        AudioUtils.getInstance().speakText(bobao3); //播放语音
                        break;
                    case 3:
                        setPointImg(false, false, false, true);
//                        btnBack.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

//        String status = getIntent().getStringExtra(Constant.LOG_IN_AGAIN);
//        if (Constant.LOG_IN_AGAIN.equals(status)) {
//            mViewPager.setCurrentItem(2);
//        }
        //已存储用户信息，直接跳转登录
        if (!TextUtils.isEmpty(DataCenter.getInstance().getUserId())) {
            mViewPager.setCurrentItem(2);
        } else {
            String bobao = "小鹿公主丫丫，和爸爸妈妈生活在美丽的森林中，人类的活动，破坏了森林，也破坏了小鹿们的家园，森林的破坏，让小鹿丫丫与爸爸妈妈分离";
            AudioUtils.getInstance().speakText(bobao); //播放语音
        }
    }

    @OnClick({R.id.btn_back, R.id.btn_getslb})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:

                break;

            case R.id.btn_getslb:
                HashMap<String, Object> paramsMap = new HashMap<>();
                paramsMap.put("userId", DataCenter.getInstance().getUserId());
                if ("finished".equals(finishStatus)) {
                    paramsMap.put("forestCoinCount", 3);
                } else paramsMap.put("forestCoinCount", 1);
                String strEntity = gson.toJson(paramsMap);
                RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), strEntity);
                loginPresenter.receiveForestCoin(body);
                break;
        }
    }

    @Override
    public void onLogin(LoginBean loginBean) {
        if (loginBean != null) {
            DataCenter.getInstance().setUserId(loginBean.getUserOpenId());
            DataCenter.getInstance().setToken(loginBean.getToken());
            getSharedPreferences(Constant.PREFERENCES_DB, Context.MODE_PRIVATE).edit().putString("userid", loginBean.getUserOpenId()).apply();
            getSharedPreferences(Constant.PREFERENCES_DB, Context.MODE_PRIVATE).edit().putString("token", loginBean.getToken()).apply();
            if ("noStock".equals(loginBean.getUserStockType())) {
                //新用户，第一次登录，不用判断是否完成任务
                getSharedPreferences(Constant.PREFERENCES_DB, Context.MODE_PRIVATE).edit().putString(Constant.RECEIVE_FOREST_CURRENCY, DateUtils.convertToString(DateUtils.DATE_FORMAT, new Date())).apply();
                getSharedPreferences(Constant.PREFERENCES_DB, Context.MODE_PRIVATE).edit().putBoolean(Constant.FIRST_LOGIN, true).apply();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
                loginPresenter.getUserInfo();
            }
//            loginPresenter.getUserFinishTaskStatus();
        } else {
            initView();
        }
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
            if (status.equals(DateUtils.convertToString(DateUtils.DATE_FORMAT, new Date()))) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                loginPresenter.getUserFinishTaskStatus();
            }
        } else {
            getSharedPreferences(Constant.PREFERENCES_DB, Context.MODE_PRIVATE).edit().putBoolean(Constant.FIRST_LOGIN, true).apply();
            SingleToast.showMsg("还没有创建个人信息哟！");
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public void onUserFinishTaskStatus(FinishStatusBean finishStatusBean) {
        if (finishStatusBean != null) {
            finishStatus = finishStatusBean.getFinishTaskStatus();
            mViewPager.setVisibility(View.GONE);
            llPoint.setVisibility(View.GONE);
            ivFinishStatus.setVisibility(View.VISIBLE);
            btnGetslb.setVisibility(View.VISIBLE);
            if ("finished".equals(finishStatusBean.getFinishTaskStatus())) {
                ivFinishStatus.setImageDrawable(getResources().getDrawable(R.mipmap.five));
                btnGetslb.setText("领3个森林币");
                String bobao = "亲爱的朋友，谢谢你的帮助，你昨天完成了任务并获得森林币，真的太棒了，每天登录养成，完成任务，养成良好的习惯哦。";
                AudioUtils.getInstance().speakText(bobao); //播放语音
            } else {
                ivFinishStatus.setImageDrawable(getResources().getDrawable(R.mipmap.four));
                btnGetslb.setText("领1个森林币");
                String bobao = "嘿！伙计，昨天你没有完成任务获得森林币，怎么了，别忘了丫丫还在等我们的森林币帮助他重建家园呢，快去做任务，获得更多的森林币吧，一起帮丫丫回家。";
                AudioUtils.getInstance().speakText(bobao); //播放语音
            }
        }
    }

    @Override
    public void onReceiveForestCoin(HttpResult httpResult) {
        if (httpResult != null) {
            if ("0000".equals(httpResult.getCode())) {
                SingleToast.showMsg("领取成功！");
                loginPresenter.getUserInfo();
            } else {
                SingleToast.showMsg(httpResult.getMsg());
            }
            getSharedPreferences(Constant.PREFERENCES_DB, Context.MODE_PRIVATE).edit().putString(Constant.RECEIVE_FOREST_CURRENCY, DateUtils.convertToString(DateUtils.DATE_FORMAT, new Date())).apply();
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        //判断是否勾选禁止后不再询问
                        boolean showRequestPermission = ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i]);
                        if (showRequestPermission) {//
                            checkPermision();//重新申请权限
                            return;
                        } else {
                            Dialog dialog = new AlertDialog.Builder(SplashActivity.this).setTitle("权限设置")//设置对话框标题
                                    .setMessage(
                                            "获取权限失败，" +
                                                    "将导致部分功能无法正常使用，需要到设置页面手动授权,取消则无法进入游戏！！")//设置显示的内容
                                    .setPositiveButton("去授权", new DialogInterface.OnClickListener() {//添加确定按钮
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                            //TODO Auto-generated method stub
                                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                            Uri uri = Uri.fromParts("package", getApplicationContext().getPackageName(), null);
                                            intent.setData(uri);
                                            startActivityForResult(intent, 1);
                                            dialog.dismiss();
                                        }
                                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加返回按钮
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {//响应事件
                                            // TODO Auto-generated method stub
                                            dialog.dismiss();
                                            finish();
                                        }
                                    }).show();//在按键响应事件中显示此对话框
                            dialog.setCanceledOnTouchOutside(false);
                            return;
                        }
                    }
                }
//                afterCheckPermision();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            checkPermision();//重新申请权限
        }
    }


    /**
     * 权限检测
     */
    private void checkPermision() {

        mPermissionList.clear();
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);
            }
        }
        /**
         * 判断是否为空
         */
        if (mPermissionList.isEmpty()) {//未授予的权限为空，表示都授予了
//            afterCheckPermision();
            onWxScanLogin();
        } else {//请求权限方法
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(SplashActivity.this, "需要给予权限才能正常使用哟！", Toast.LENGTH_LONG);
                }
            });
            String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
            ActivityCompat.requestPermissions(this, permissions, 1);
        }
    }

    Gson gson = new Gson();

    /**
     * 微信扫码登录
     */
    private void onWxScanLogin() {
        startActivity(new Intent(SplashActivity.this, AuthActivity.class));
        finish();
    }

    /**
     * 所有权限检测完毕之后
     */
    private void afterCheckPermision() {
        String macAddress = "";
        macAddress = getMacAddress(this);
        Log.e("regus_mac ", macAddress + "");

        if ("02:00:00:00:00:00".equals(macAddress)) {//如果获取不到mac地址
            macAddress = getDeviceId();
            Log.e("regus_mac_dev ", macAddress);
        }

        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("code", macAddress);
        String strEntity = gson.toJson(paramsMap);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), strEntity);

        //拿到设备id登录
        loginPresenter.login(body);

    }

    /**
     * 获取MAC地址
     *
     * @param context
     * @return
     */
    public static String getMacAddress(Context context) {
        String mac = "02:00:00:00:00:00";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mac = getMacDefault(context);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            mac = getMacAddress();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mac = getMacFromHardware();
        }
        return mac;
    }

    /**
     * Android  6.0 之前（不包括6.0）
     * 必须的权限  <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
     *
     * @param context
     * @return
     */
    private static String getMacDefault(Context context) {
        String mac = "02:00:00:00:00:00";
        if (context == null) {
            return mac;
        }

        WifiManager wifi = (WifiManager) context.getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        if (wifi == null) {
            return mac;
        }
        WifiInfo info = null;
        try {
            info = wifi.getConnectionInfo();
        } catch (Exception e) {
        }
        if (info == null) {
            return null;
        }
        mac = info.getMacAddress();
        if (!TextUtils.isEmpty(mac)) {
            mac = mac.toUpperCase(Locale.ENGLISH);
        }
        return mac;
    }


    /**
     * Android 6.0（包括） - Android 7.0（不包括）
     *
     * @return
     */
    private static String getMacAddress() {
        String WifiAddress = "02:00:00:00:00:00";
        try {
            WifiAddress = new BufferedReader(new FileReader(new File("/sys/class/net/wlan0/address"))).readLine();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("regus", " getMacAddress-> " + e.getLocalizedMessage());
        }
        return WifiAddress;
    }


    /**
     * 7.0 以后
     * 遍历循环所有的网络接口，找到接口是 wlan0
     * 必须的权限 <uses-permission android:name="android.permission.INTERNET" />
     *
     * @return
     */
    private static String getMacFromHardware() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception e) {
            Log.e("regus", " getMacFromHardware-> " + e.getLocalizedMessage());
            e.printStackTrace();
        }
        return "02:00:00:00:00:00";
    }


    /**
     * 获取设备id
     *
     * @return
     */

    @SuppressLint("MissingPermission")
    public String getDeviceId() {
        String deviceId = "";

        try {
            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            deviceId = tm.getDeviceId();
        } catch (Exception e) {
            Log.e("regus", " getDeviceId-> " + e.getLocalizedMessage());
        }

        return deviceId;

    }


    class GuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager) container).addView(mList.get(position));
            return mList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(mList.get(position));
            //super.destroyItem(container, position, object);
        }
    }

    //设置小圆点的选中效果
    private void setPointImg(boolean isCheck1, boolean isCheck2, boolean isCheck3, boolean isCheck4) {
        if (isCheck1) {
            point1.setBackgroundResource(R.mipmap.point_on);
        } else {
            point1.setBackgroundResource(R.mipmap.point_off);
        }

        if (isCheck2) {
            point2.setBackgroundResource(R.mipmap.point_on);
        } else {
            point2.setBackgroundResource(R.mipmap.point_off);
        }

        if (isCheck3) {
            point3.setBackgroundResource(R.mipmap.point_on);
        } else {
            point3.setBackgroundResource(R.mipmap.point_off);
        }

        if (isCheck4) {
            point4.setBackgroundResource(R.mipmap.point_on);
        } else {
            point4.setBackgroundResource(R.mipmap.point_off);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AudioUtils.getInstance().stopSpeaking();
    }
}
