package com.dyibing.myapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
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
import com.dyibing.myapp.utils.tts.AudioUtils;
import com.dyibing.myapp.view.CircleImageView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class HelpYYActivity extends AppCompatActivity implements HelpYYView, LikesView, UserInfoView {

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
    @BindView(R.id.tv_pro_name)
    TextView tvProName;
    @BindView(R.id.tv_donated)
    TextView tvDonated;
    @BindView(R.id.tv_donation)
    TextView tvDonation;
    @BindView(R.id.tv_progress)
    TextView tvProgress;
    @BindView(R.id.rlv_love_list)
    RecyclerView rlvLoveList;
    @BindView(R.id.tv_pr_detail)
    TextView tvPrDetail;
    @BindView(R.id.iv_weitu)
    ImageView ivWeitu;
    @BindView(R.id.tv_pro_content)
    TextView tvProContent;
    @BindView(R.id.tv_gyaxb)
    TextView tvGYAXB;
    @BindView(R.id.tv_zhint)
    TextView tvZhint;
    @BindView(R.id.srl_fresh)
    SwipeRefreshLayout srlFresh;
    @BindView(R.id.tv_related_articles)
    TextView tvRelatedArticles;

    private HelpYYPresenter helpYYPresenter;
    private int publicWelfareId;
    private LoveListAdapter loveListAdapter;
    private UserInfoPresenter userInfoPresenter;
    public String ids = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_yy);
        ButterKnife.bind(this);
        LikesPresenter likesPresenter = new LikesPresenter(this, this);
        loveListAdapter = new LoveListAdapter(this, 0, likesPresenter);
        userInfoPresenter = new UserInfoPresenter(this, this);
        rlvLoveList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        rlvLoveList.setAdapter(loveListAdapter);
        helpYYPresenter = new HelpYYPresenter(this, this);
        srlFresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //这里获取数据的逻辑
                srlFresh.setRefreshing(false);
                helpYYPresenter.getWelfareRankTenList(1, 5, null);
            }
        });
        AudioUtils.getInstance().init(this); //初始化语音对象
    }

    @Override
    protected void onResume() {
        super.onResume();
        initInfo();
        helpYYPresenter.getWelfareRankTenList(1, 5, null);
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
        else {
            tvAixin.setText(DataCenter.getInstance().getUser().getForestCoinCount() + "");
        }
        if (DataCenter.getInstance().getUser().getForestCoinCount() == 0) {
            tvDonation.setEnabled(false);
        }

    }

    Gson gson = new Gson();

    @OnClick({R.id.nice_iv0, R.id.iv_back, R.id.tv_donation, R.id.tv_zhint})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.nice_iv0:
                Intent intent4 = new Intent(this, UserCenterActivity.class);
                startActivity(intent4);
                break;
            case R.id.tv_donation:
                HashMap<String, Object> paramsMap = new HashMap<>();
                paramsMap.put("userId", DataCenter.getInstance().getUserId());
                paramsMap.put("publicWelfareId", publicWelfareId);
                paramsMap.put("donationCount", 1);
                String strEntity = gson.toJson(paramsMap);
                RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), strEntity);
                helpYYPresenter.donationForestCoin(body);
                break;
            case R.id.tv_zhint:
                String bobao = "快来给公益榜上的小勇士们点赞吧，点赞的同时，自己也能获得相应的赞哦。年度获得赞最多，和捐赠森林币最多的小朋友，有机会获得参加环保夏令营的机会。";
                AudioUtils.getInstance().speakText(bobao); //播放语音
                break;
        }
    }

    @Override
    public void onDonationForestCoin(HttpResult httpResult) {
        if (httpResult != null) {
            if ("0000".equals(httpResult.getCode())) {
                SingleToast.showMsg("捐献成功！");
                helpYYPresenter.getWelfareRankTenList(1, 5, null);
                DataCenter.getInstance().getUser().setForestCoinCount(DataCenter.getInstance().getUser().getForestCoinCount() - 1);
                if (DataCenter.getInstance().getUser().getForestCoinCount_ls() != 0)
                    tvAixin.setText(DataCenter.getInstance().getUser().getForestCoinCount() + "+" + DataCenter.getInstance().getUser().getForestCoinCount_ls());
                else {
                    tvAixin.setText(DataCenter.getInstance().getUser().getForestCoinCount() + "");
                }
                if (DataCenter.getInstance().getUser().getForestCoinCount() == 0) {
                    tvDonation.setEnabled(false);
                }
            } else SingleToast.showMsg(httpResult.getMsg());
        }
    }

    @Override
    public void onWelfareRankTenList(WelfareBean welfareBean) {
        if (welfareBean == null)
            return;
        if (welfareBean.getCurrentWelfare() != null) {
            publicWelfareId = welfareBean.getCurrentWelfare().getPublicWelfareId();
            tvProName.setText(welfareBean.getCurrentWelfare().getPublicWelfareName());
            tvDonated.setText(String.format("我已捐赠：%d森林币", welfareBean.getCurrentWelfare().getDonationForestCoin()));
            tvProgress.setText(String.format("进度：%d/%d", welfareBean.getCurrentWelfare().getHaveForestCoin(), welfareBean.getCurrentWelfare().getForestCoin()));
            if (TextUtils.isEmpty(welfareBean.getCurrentWelfare().getPublicWelfareContent())) {
                tvPrDetail.setVisibility(View.GONE);
                tvProContent.setVisibility(View.GONE);
            } else {
                tvPrDetail.setVisibility(View.VISIBLE);
                tvProContent.setVisibility(View.VISIBLE);
                tvProContent.setText(welfareBean.getCurrentWelfare().getPublicWelfareContent());
            }
            if (TextUtils.isEmpty(welfareBean.getCurrentWelfare().getPublicWelfareUrl())) {
                ivWeitu.setVisibility(View.GONE);
            } else {
                ivWeitu.setVisibility(View.VISIBLE);
                Glide.with(this).load(welfareBean.getCurrentWelfare().getPublicWelfareUrl()).into(ivWeitu);
            }
            if (TextUtils.isEmpty(welfareBean.getCurrentWelfare().getRelatedArticles())) {
                tvRelatedArticles.setVisibility(View.GONE);
            } else {
                tvRelatedArticles.setVisibility(View.VISIBLE);
                tvRelatedArticles.setText(welfareBean.getCurrentWelfare().getRelatedArticles());
            }
        }

        if (welfareBean.getWelfareRankList().getData() != null && welfareBean.getWelfareRankList().getData().size() != 0) {
            List<WelfareBean.WelfareRankListBean.DataBean> datas = welfareBean.getWelfareRankList().getData();
            String ids = "";
            for (int i = 0; i < datas.size(); i++) {
                if (i != (datas.size() - 1))
                    ids = ids + datas.get(i).getId() + ",";
                else ids = ids + datas.get(i).getId();
            }
            this.ids = ids;
            loveListAdapter.setNewData(welfareBean.getWelfareRankList().getData());
            tvGYAXB.setVisibility(View.VISIBLE);
            tvZhint.setVisibility(View.VISIBLE);
        } else {
            tvGYAXB.setVisibility(View.GONE);
            tvZhint.setVisibility(View.GONE);
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
    public void onUserInfo(UserInfoBean userInfoBean) {
        DataCenter.getInstance().getUser().setLikesCount(userInfoBean.getLikesCount());
        tvZan.setText(DataCenter.getInstance().getUser().getLikesCount() + "");
    }
}
