package com.dyibing.myapp.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.dyibing.myapp.R;
import com.dyibing.myapp.bean.DataCenter;
import com.dyibing.myapp.common.Constant;
import com.dyibing.myapp.utils.Utils;
import com.dyibing.myapp.utils.tts.AudioUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends AppCompatActivity{
    public final String TAG = getClass().getSimpleName();

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
    private String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.RECORD_AUDIO
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
        checkPermision();
        initView();
    }

    private void init() {
        ButterKnife.bind(this);
        AudioUtils.getInstance().init(this); //初始化语音对象
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
                startActivity(new Intent(SplashActivity.this, AuthActivity.class));
                finish();
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

        //已存储用户信息，直接跳转登录
        if (!TextUtils.isEmpty(DataCenter.getInstance().getUserId())) {
            mViewPager.setCurrentItem(2);
        } else {
            String bobao = "小鹿公主丫丫，和爸爸妈妈生活在美丽的森林中，人类的活动，破坏了森林，也破坏了小鹿们的家园，森林的破坏，让小鹿丫丫与爸爸妈妈分离";
            AudioUtils.getInstance().speakText(bobao); //播放语音
        }
    }

    @OnClick({R.id.btn_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
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
    @SuppressLint("CheckResult")
    private void checkPermision() {
        new RxPermissions(this).request(permissions)
                .subscribe(granted -> {
                    if (granted) {
                        LogUtils.dTag(TAG, "所有权限都已打开！可以开始操作界面");
                        if (!TextUtils.isEmpty(DataCenter.getInstance().getToken())) {
                            String status = SPUtils.getInstance(Constant.PREFERENCES_DB).getString(Constant.RECEIVE_FOREST_CURRENCY, "");
                            if (status.equals(Utils.getTodayDate())) {
                                startActivity(new Intent(this, MainActivity.class));
                            } else {
                                startActivity(new Intent(SplashActivity.this, ForestCoinActivity.class));
                            }
                            finish();
                        }
                    } else {
                        LogUtils.dTag(TAG, "至少有一个权限被禁止");
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getApplicationContext().getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, 1);
                    }
                });
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
