package com.dyibing.myapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dyibing.myapp.R;
import com.dyibing.myapp.bean.DataCenter;
import com.dyibing.myapp.bean.WaitingCashTaskBean;
import com.dyibing.myapp.mvp.presenter.WaitingCashTaskPresenter;
import com.dyibing.myapp.utils.tts.AudioUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class TaskListAdapter extends RecyclerView.Adapter {

    private final Context mContext;
    private final WaitingCashTaskPresenter waitingCashTaskPresenter;
    private List<WaitingCashTaskBean> waitingCashTaskBeans = new ArrayList<>();

    public TaskListAdapter(Context context, WaitingCashTaskPresenter taskPresenter) {
        this.mContext = context;
        waitingCashTaskPresenter = taskPresenter;
        AudioUtils.getInstance().init(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_task_list, viewGroup, false);
        return new RecyclerHolder(view);
    }
    Gson gson = new Gson();
    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        WaitingCashTaskBean waitingCashTaskBean = waitingCashTaskBeans.get(i);
        RecyclerHolder recyclerHolder = (RecyclerHolder)viewHolder;
        recyclerHolder.tvTaskContent.setText(waitingCashTaskBean.getTaskName());
        recyclerHolder.tvCount.setText(String.format("共%d次", waitingCashTaskBean.getTaskRepeartCount()));
        if (!TextUtils.isEmpty(waitingCashTaskBean.getTaskReward())){
            recyclerHolder.tvJiangli.setVisibility(View.VISIBLE);
            recyclerHolder.tvJiangli.setText(waitingCashTaskBean.getTaskReward());
        }else recyclerHolder.tvJiangli.setVisibility(View.GONE);
        recyclerHolder.tvTime.setText(String.format("%s-%s", waitingCashTaskBean.getTaskStartTime().replace(" 00:00:00","").replace("-", "."), waitingCashTaskBean.getTaskEndTime().replace(" 00:00:00","").replace("-",".")));
        if ("CASH".equals(waitingCashTaskBean.getTaskStatus())){
            recyclerHolder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.text_color3));
            recyclerHolder.tvStatus.setText("已兑现");
            recyclerHolder.tvComplete.setVisibility(View.GONE);
        }else if ("AWAITCONFIRM".equals(waitingCashTaskBean.getTaskStatus())){
            recyclerHolder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.text_color3));
            recyclerHolder.tvStatus.setText("待确认");
            recyclerHolder.tvComplete.setVisibility(View.GONE);
        }else if ("AWAITCASH".equals(waitingCashTaskBean.getTaskStatus())){
            recyclerHolder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.text_color4));
            recyclerHolder.tvStatus.setText("等待兑现");
            recyclerHolder.tvComplete.setVisibility(View.VISIBLE);
        }else if ("DOING".equals(waitingCashTaskBean.getTaskStatus())){
            recyclerHolder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.text_color4));
            recyclerHolder.tvStatus.setText("进行中");
            recyclerHolder.tvComplete.setVisibility(View.GONE);
        }else if ("FINISH".equals(waitingCashTaskBean.getTaskStatus())){
            recyclerHolder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.text_color3));
            recyclerHolder.tvStatus.setText("已完成");
            recyclerHolder.tvComplete.setVisibility(View.GONE);
        }else if ("DELETE".equals(waitingCashTaskBean.getTaskStatus())){
            recyclerHolder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.text_color5));
            recyclerHolder.tvStatus.setText("已删除");
            recyclerHolder.tvComplete.setVisibility(View.GONE);
        }else if ("NORMAL".equals(waitingCashTaskBean.getTaskStatus())){
            recyclerHolder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.text_color5));
            recyclerHolder.tvStatus.setText("未完成");
            recyclerHolder.tvComplete.setVisibility(View.GONE);
        }else if ("EXPIRED".equals(waitingCashTaskBean.getTaskStatus())){
            recyclerHolder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.text_color5));
            recyclerHolder.tvStatus.setText("已失效");
            recyclerHolder.tvComplete.setVisibility(View.GONE);
        }
        recyclerHolder.tvComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> paramsMap = new HashMap<>();
                paramsMap.put("taskId", waitingCashTaskBean.getTaskId());
                String strEntity = gson.toJson(paramsMap);
                RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), strEntity);
                waitingCashTaskPresenter.updateCashTask(body);
            }
        });
        recyclerHolder.tvTaskContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bobao = waitingCashTaskBean.getTaskName();
                if (!TextUtils.isEmpty(waitingCashTaskBean.getTaskReward())){
                    bobao = bobao + "[p1000]奖励" + waitingCashTaskBean.getTaskReward();
                }
                AudioUtils.getInstance().speakText(bobao);
            }
        });
    }

    @Override
    public int getItemCount() {
        return waitingCashTaskBeans.size();
    }

    public void setData(List<WaitingCashTaskBean> waitingCashTaskBeans){
        if (waitingCashTaskBeans != null) {
            this.waitingCashTaskBeans.clear();
            this.waitingCashTaskBeans.addAll(waitingCashTaskBeans);
            notifyDataSetChanged();
        }

    }

    static class RecyclerHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_task_content)
        TextView tvTaskContent;
        @BindView(R.id.tv_count)
        TextView tvCount;
        @BindView(R.id.tv_jiangli)
        TextView tvJiangli;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.tv_complete)
        TextView tvComplete;

        private RecyclerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
