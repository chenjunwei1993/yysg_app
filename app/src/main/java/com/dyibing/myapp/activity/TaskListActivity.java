package com.dyibing.myapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dyibing.myapp.R;
import com.dyibing.myapp.adapter.TaskListAdapter;
import com.dyibing.myapp.bean.DataCenter;
import com.dyibing.myapp.bean.UserInfoBean;
import com.dyibing.myapp.bean.WaitingCashTaskBean;
import com.dyibing.myapp.mvp.presenter.UserInfoPresenter;
import com.dyibing.myapp.mvp.presenter.WaitingCashTaskPresenter;
import com.dyibing.myapp.mvp.view.UserInfoView;
import com.dyibing.myapp.mvp.view.WaitingCashTaskView;
import com.dyibing.myapp.net.HttpResult;
import com.dyibing.myapp.utils.SingleToast;
import com.dyibing.myapp.view.CircleImageView;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
 * 任务列表
 * */

public class TaskListActivity extends AppCompatActivity implements WaitingCashTaskView , UserInfoView {

    @BindView(R.id.nice_iv0)
    CircleImageView niceIv0;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_zan)
    TextView tvZan;
    @BindView(R.id.tv_aixin)
    TextView tvAixin;
    @BindView(R.id.iv_help_yy)
    TextView ivHelpYy;
    @BindView(R.id.iv_check_ta)
    TextView ivCheckTa;
    @BindView(R.id.rlv_task_list)
    RecyclerView rlvTaskList;
    @BindView(R.id.iv_back)
    TextView ivBack;
    @BindView(R.id.srl_fresh)
    SwipeRefreshLayout srlFresh;

    private TaskListAdapter taskListAdapter;
    private WaitingCashTaskPresenter waitingCashTaskPresenter;
    private UserInfoPresenter userInfoPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        ButterKnife.bind(this);
        userInfoPresenter = new UserInfoPresenter(this,this);
        waitingCashTaskPresenter = new WaitingCashTaskPresenter(this, this);
        taskListAdapter = new TaskListAdapter(this, waitingCashTaskPresenter);
        rlvTaskList.setLayoutManager(new LinearLayoutManager(this));
        rlvTaskList.setAdapter(taskListAdapter);
        srlFresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //这里获取数据的逻辑
                srlFresh.setRefreshing(false);
                waitingCashTaskPresenter.getCurrentDateTask();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initInfo();
        waitingCashTaskPresenter.getCurrentDateTask();
        userInfoPresenter.getUserInfo();
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


    @OnClick({R.id.nice_iv0, R.id.iv_back, R.id.iv_help_yy, R.id.iv_check_ta})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nice_iv0:
                Intent intent4 = new Intent(this, UserCenterActivity.class);
                startActivity(intent4);
                break;

            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_help_yy:
                Intent intent3 = new Intent(this, HelpYYActivity.class);
                startActivity(intent3);
                break;
            case R.id.iv_check_ta:
                Intent intent1 = new Intent(this, ClassTimetableActivity.class);
                startActivity(intent1);
                break;
        }
    }

    @Override
    public void onTaskList(List<WaitingCashTaskBean> taskBeans) {
        taskListAdapter.setData(taskBeans);
    }

    @Override
    public void onUpdateCashTask(HttpResult result) {
        if (result != null) {
            if ("0000".equals(result.getCode())) {
                waitingCashTaskPresenter.getCurrentDateTask();
                userInfoPresenter.getUserInfo();
            } else SingleToast.showMsg(result.getMsg());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        waitingCashTaskPresenter.onDestory();
    }

    @Override
    public void onUserInfo(UserInfoBean userInfoBean) {
        if (userInfoBean == null)
            return;
        DataCenter.getInstance().getUser().setForestCoinCount(userInfoBean.getForestCoinCount());
        if (DataCenter.getInstance().getUser().getForestCoinCount_ls() != 0)
            tvAixin.setText(DataCenter.getInstance().getUser().getForestCoinCount() + "+" + DataCenter.getInstance().getUser().getForestCoinCount_ls());
        else
            tvAixin.setText(DataCenter.getInstance().getUser().getForestCoinCount()+"");
    }
}
