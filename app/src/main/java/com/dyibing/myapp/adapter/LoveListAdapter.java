package com.dyibing.myapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dyibing.myapp.R;
import com.dyibing.myapp.activity.HelpYYActivity;
import com.dyibing.myapp.activity.LoveListActivity;
import com.dyibing.myapp.bean.DataCenter;
import com.dyibing.myapp.bean.WelfareBean;
import com.dyibing.myapp.mvp.presenter.HelpYYPresenter;
import com.dyibing.myapp.mvp.presenter.LikesPresenter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;

public class LoveListAdapter extends RecyclerView.Adapter {

    private final Context mContext;
    private final int moreType; //控制查看更多的样式  0：帮助丫丫界面  1：爱心列表界面
    private boolean isMore = true;
    private final LikesPresenter likesPresenter;
    private int itemPosition;

    List<WelfareBean.WelfareRankListBean.DataBean> objectList = new ArrayList<>();

    public LoveListAdapter(Context context, int moreType, LikesPresenter likesPresenter) {
        this.mContext = context;
        this.moreType = moreType;
        this.likesPresenter = likesPresenter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (0 == viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_love_list, parent, false);
            return new RecyclerHolder(view);
        } else {
            if (0 == moreType) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.item_love_list_more, parent, false);
                return new RecyclerHolder2(view);
            } else {
                View view = LayoutInflater.from(mContext).inflate(R.layout.item_love_list_more2, parent, false);
                return new RecyclerHolder3(view);
            }
        }
    }

    Gson gson = new Gson();
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RecyclerHolder) {
            RecyclerHolder recyclerHolder = (RecyclerHolder) holder;
            WelfareBean.WelfareRankListBean.DataBean dataBean = objectList.get(position);
            Glide.with(mContext).load(dataBean.getPublicWelfareUrl()).into(recyclerHolder.ivHead);
            recyclerHolder.tvUserName.setText(dataBean.getNickName());
            recyclerHolder.tvDonationYesterday.setText(String.format("昨天捐赠：%d", dataBean.getDonationForestCoinCount()));
            recyclerHolder.tvDonationTotal.setText(String.format("合计捐赠：%d", dataBean.getHaveForestCoin()));
            recyclerHolder.tvZan.setText(String.format("%d", dataBean.getLikesCount()));
            recyclerHolder.tvZan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HashMap<String,Object> paramsMap=new HashMap<>();
                    paramsMap.put("id",dataBean.getId());
                    paramsMap.put("userId", dataBean.getUserId());
                    String strEntity = gson.toJson(paramsMap);
                    RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),strEntity);
                    likesPresenter.saveLikes(body);
                    itemPosition = position;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if (objectList.size() == 0)
            return 0;
        if (isMore)
            return objectList.size() + 1;
        else return objectList.size();
    }

    public int getDataCount() {
        return objectList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == objectList.size())
            return 1;
        else return 0;
    }

    public void setNewData(List<WelfareBean.WelfareRankListBean.DataBean> dataBeans) {
        objectList.clear();
        objectList.addAll(dataBeans);
        notifyDataSetChanged();
    }

    public void setData(List<WelfareBean.WelfareRankListBean.DataBean> dataBeans) {
        objectList.addAll(dataBeans);
        notifyDataSetChanged();
    }

    public void setIsMore(boolean more) {
        isMore = more;
    }

    public void likeCallback() {
        objectList.get(itemPosition).setLikesCount(objectList.get(itemPosition).getLikesCount() + 1);
        notifyItemChanged(itemPosition);
    }

    class RecyclerHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.iv_head)
        ImageView ivHead;
        @BindView(R.id.tv_user_name)
        TextView tvUserName;
        @BindView(R.id.tv_donation_yesterday)
        TextView tvDonationYesterday;
        @BindView(R.id.tv_donation_total)
        TextView tvDonationTotal;
        @BindView(R.id.tv_zan)
        TextView tvZan;

        private RecyclerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


    class RecyclerHolder2 extends RecyclerView.ViewHolder {

        View itemView;

        private RecyclerHolder2(View itemView) {
            super(itemView);
            this.itemView = itemView;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, LoveListActivity.class);
                    intent.putExtra("ids",((HelpYYActivity)mContext).ids);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    class RecyclerHolder3 extends RecyclerView.ViewHolder {

        View itemView;

        private RecyclerHolder3(View itemView) {
            super(itemView);
            this.itemView = itemView;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((LoveListActivity)mContext).loadMore();
                }
            });
        }
    }
}
