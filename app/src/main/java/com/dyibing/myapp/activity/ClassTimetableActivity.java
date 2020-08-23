package com.dyibing.myapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dyibing.myapp.R;
import com.dyibing.myapp.adapter.CourseInfoAdapter;
import com.dyibing.myapp.bean.CourseBean;
import com.dyibing.myapp.bean.DataCenter;
import com.dyibing.myapp.mvp.presenter.CoursePresenter;
import com.dyibing.myapp.mvp.view.CourseView;
import com.dyibing.myapp.utils.SingleToast;
import com.dyibing.myapp.utils.Utils;
import com.dyibing.myapp.view.CircleImageView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClassTimetableActivity extends AppCompatActivity implements CourseView {

    @BindView(R.id.nice_iv0)
    CircleImageView niceIv0;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_zan)
    TextView tvZan;
    @BindView(R.id.tv_aixin)
    TextView tvAixin;
    @BindView(R.id.iv_back)
    TextView ivBack;
    @BindView(R.id.tv_today_task)
    TextView tvTodayTask;
    @BindView(R.id.iv_previous)
    ImageView ivPrevious;
    @BindView(R.id.iv_date)
    TextView ivDate;
    @BindView(R.id.iv_next)
    ImageView ivNext;
    @BindView(R.id.tlv_class_timetable)
    RecyclerView tlvClassTimetable;
    @BindView(R.id.srl_fresh)
    SwipeRefreshLayout srlFresh;
    private CoursePresenter coursePresenter;
    private CourseInfoAdapter adapter;
    String beforeDateWeek;
    String afterDateWeek;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_timetable);
        ButterKnife.bind(this);
        adapter = new CourseInfoAdapter(this);
        //添加Android自带的分割线
        tlvClassTimetable.setLayoutManager(new LinearLayoutManager(this));
        tlvClassTimetable.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        tlvClassTimetable.setAdapter(adapter);

        coursePresenter = new CoursePresenter(this, this);
        coursePresenter.getCourseInfo(DataCenter.getInstance().getUserId(), null);
        srlFresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //这里获取数据的逻辑
                srlFresh.setRefreshing(false);
                coursePresenter.getCourseInfo(DataCenter.getInstance().getUserId(), null);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initInfo();
        coursePresenter.getCourseInfo(DataCenter.getInstance().getUserId(), null);
    }

    private void initInfo() {
        if (!TextUtils.isEmpty(DataCenter.getInstance().getUser().getAvatarUrl())) {
            Glide.with(this).load(DataCenter.getInstance().getUser().getAvatarUrl()).into(niceIv0);
        }
        if (!TextUtils.isEmpty(DataCenter.getInstance().getUser().getNickname())) {
            tvName.setText(DataCenter.getInstance().getUser().getNickname());
        }else {
            tvName.setText(DataCenter.getInstance().getUserId());
        }
        tvZan.setText(DataCenter.getInstance().getUser().getLikesCount() + "");
        if (DataCenter.getInstance().getUser().getForestCoinCount_ls() != 0)
            tvAixin.setText(DataCenter.getInstance().getUser().getForestCoinCount() + "+" + DataCenter.getInstance().getUser().getForestCoinCount_ls());
        else
            tvAixin.setText(DataCenter.getInstance().getUser().getForestCoinCount()+ "");
    }

    @OnClick({R.id.nice_iv0, R.id.iv_back, R.id.iv_previous, R.id.iv_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.nice_iv0:
                Intent intent4 = new Intent(this, UserCenterActivity.class);
                startActivity(intent4);
                break;
            case R.id.iv_previous:
                coursePresenter.getCourseInfo(DataCenter.getInstance().getUserId(), beforeDateWeek);
                break;
            case R.id.iv_next:
                coursePresenter.getCourseInfo(DataCenter.getInstance().getUserId(), afterDateWeek);
                break;
        }
    }

    Gson gson = new Gson();

    //{"night":"语文411，英语4","am":"语文,数学","pm":"看漫画,学钢琴"}
    @Override
    public void onCourseInfo(CourseBean courseBean) {
        if (courseBean != null) {
            beforeDateWeek = courseBean.getYesterdayDate();
            afterDateWeek = courseBean.getTomorrowDate();
            ivDate.setText(String.format("%s  %s", courseBean.getCurrentDate(), Utils.getWeekString(courseBean.getCurrentDateWeek())));
            String course = courseBean.getCurrentCourseContent();
            List<List<String>> strs = new ArrayList<>();
            if (course != null) {
                course.replace("\\", "");
                CourseBean.Class_ info = gson.fromJson(course, CourseBean.Class_.class);
                String am = info.getAm();
                String[] ams = new String[0];
                if (am != null)
                    ams = am.split(",");
                String[] pms = new String[0];
                String pm = info.getPm();
                if (pm != null)
                    pms = pm.split(",");
                String[] nights = new String[0];
                String night = info.getNight();
                if (night != null)
                    nights = night.split(",");
                int length;
                if (ams.length >= pms.length && ams.length >= nights.length) {
                    length = ams.length;
                } else {
                    if (pms.length >= nights.length)
                        length = pms.length;
                    else length = nights.length;
                }
                for (int i = 0; i < length; i++) {
                    List<String> strings = new ArrayList<>();
                    if (ams.length - 1 < i)
                        strings.add("");
                    else strings.add(ams[i]);
                    if (pms.length - 1 < i)
                        strings.add("");
                    else strings.add(pms[i]);
                    if (nights.length - 1 < i)
                        strings.add("");
                    else strings.add(nights[i]);
                    strs.add(strings);
                }
            }
            adapter.setData(strs);
        } else SingleToast.showMsg("获取课表失败！");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        coursePresenter.onDestory();
    }
}
