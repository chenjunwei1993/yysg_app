package com.dyibing.myapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dyibing.myapp.R;
import com.dyibing.myapp.adapter.LoveListAdapter;
import com.dyibing.myapp.bean.DataCenter;
import com.dyibing.myapp.bean.UserInfoBean;
import com.dyibing.myapp.bean.WelfareBean;
import com.dyibing.myapp.mvp.presenter.HelpYYPresenter;
import com.dyibing.myapp.mvp.presenter.LikesPresenter;
import com.dyibing.myapp.mvp.presenter.UserInfoPresenter;
import com.dyibing.myapp.mvp.view.HelpYYView;
import com.dyibing.myapp.mvp.view.LikesView;
import com.dyibing.myapp.mvp.view.UserInfoView;
import com.dyibing.myapp.net.HttpResult;
import com.dyibing.myapp.utils.SingleToast;
import com.dyibing.myapp.view.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoveListActivity extends AppCompatActivity implements HelpYYView, LikesView, UserInfoView {

    @BindView(R.id.nice_iv0)
    CircleImageView niceIv0;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_zan)
    TextView tvZan;
    @BindView(R.id.iv_back)
    TextView ivBack;
    @BindView(R.id.tv_aixin)
    TextView tvAixin;
    @BindView(R.id.tv_gyaxb)
    TextView tvGyaxb;
    @BindView(R.id.tv_zhint)
    TextView tvZhint;
    @BindView(R.id.rlv_love_list)
    RecyclerView rlvLoveList;
    @BindView(R.id.srl_fresh)
    SwipeRefreshLayout srlFresh;
    private HelpYYPresenter helpYYPresenter;
    private int pageNo = 1;
    private final int pageSize = 8;
    private LoveListAdapter loveListAdapter;
    private UserInfoPresenter userInfoPresenter;
    //    private boolean isMore= true;
    private String ids = ""; //上一页请求的id集合

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_love_list);
        ButterKnife.bind(this);
        ids = getIntent().getStringExtra("ids");
        LikesPresenter likesPresenter = new LikesPresenter(this, this);
        userInfoPresenter = new UserInfoPresenter(this, this);
        loveListAdapter = new LoveListAdapter(this, 1, likesPresenter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == loveListAdapter.getDataCount())
                    return 2;
                else return 1;
            }
        });
        rlvLoveList.setLayoutManager(gridLayoutManager);
        rlvLoveList.setAdapter(loveListAdapter);
        helpYYPresenter = new HelpYYPresenter(this, this);
        helpYYPresenter.getWelfareRankMoreList(pageNo, pageSize, ids);
        srlFresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //这里获取数据的逻辑
                srlFresh.setRefreshing(false);
                pageNo = 1;
                ids = "";
                helpYYPresenter.getWelfareRankMoreList(pageNo, pageSize, ids);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initInfo();
//        helpYYPresenter.getWelfareRankTenList(pageNo, pageSize, null);
    }

    private void initInfo() {
        if (!TextUtils.isEmpty(DataCenter.getInstance().getUser().getAvatarUrl())) {
            Glide.with(this).load(DataCenter.getInstance().getUser().getAvatarUrl()).into(niceIv0);
        }
        if (!TextUtils.isEmpty(DataCenter.getInstance().getUser().getNickname())) {
            tvName.setText(DataCenter.getInstance().getUser().getNickname());
        } else {
            tvName.setText(DataCenter.getInstance().getUserId());
        }
        tvZan.setText(DataCenter.getInstance().getUser().getLikesCount() + "");
        if (DataCenter.getInstance().getUser().getForestCoinCount_ls() != 0)
            tvAixin.setText(DataCenter.getInstance().getUser().getForestCoinCount() + "+" + DataCenter.getInstance().getUser().getForestCoinCount_ls());
        else
            tvAixin.setText(DataCenter.getInstance().getUser().getForestCoinCount() + "");
    }


    @OnClick({R.id.nice_iv0, R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nice_iv0:
                Intent intent4 = new Intent(this, UserCenterActivity.class);
                startActivity(intent4);
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    public void onSaveLikes(HttpResult httpResult) {
        if (httpResult != null) {
            if ("0000".equals(httpResult.getCode())) {
                SingleToast.showMsg("点赞成功！");
                loveListAdapter.likeCallback();
                userInfoPresenter.getUserInfo();
            } else {
                SingleToast.showMsg(httpResult.getMsg());
            }
        }
    }

    @Override
    public void onDonationForestCoin(HttpResult httpResult) {

    }

    @Override
    public void onWelfareRankTenList(WelfareBean welfareBean) {
        if (welfareBean != null && welfareBean.getWelfareRankList().getData() != null) {
            loveListAdapter.setIsMore(welfareBean.getWelfareRankList().isHasNext());
            List<WelfareBean.WelfareRankListBean.DataBean> datas = welfareBean.getWelfareRankList().getData();
            if (datas != null && datas.size() != 0) {
                String ids = "";
                for (int i = 0; i < datas.size(); i++) {
                    if (i != (datas.size() - 1))
                        ids = ids + datas.get(i).getId() + ",";
                    else ids = ids + datas.get(i).getId();
                }
                this.ids = ids;
            }
        }
        loveListAdapter.setData(welfareBean.getWelfareRankList().getData());
    }

    public void loadMore() {
        pageNo += 1;
        helpYYPresenter.getWelfareRankMoreList(pageNo, pageSize, null);
    }

    @Override
    public void onUserInfo(UserInfoBean userInfoBean) {
        DataCenter.getInstance().getUser().setLikesCount(userInfoBean.getLikesCount());
        tvZan.setText(DataCenter.getInstance().getUser().getLikesCount() + "");
    }
}
