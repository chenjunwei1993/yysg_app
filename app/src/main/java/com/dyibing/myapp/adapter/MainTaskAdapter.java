package com.dyibing.myapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dyibing.myapp.R;
import com.dyibing.myapp.activity.MainActivity;
import com.dyibing.myapp.bean.CurrentDateTaskBean;
import com.dyibing.myapp.bean.DataCenter;
import com.dyibing.myapp.mvp.presenter.TasksPresenter;
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

public class MainTaskAdapter extends RecyclerView.Adapter {

    private final Context mContext;
    private int awaitConfirmCount = 0;
    private List<CurrentDateTaskBean.TaskBean.TaskListBean> mTaskList = new ArrayList<>();
    public MainTaskAdapter(Context context) {
        this.mContext = context;
        AudioUtils.getInstance().init(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_main_task, viewGroup, false);
        return new RecyclerHolder(view);
    }
    Gson gson = new Gson();
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        CurrentDateTaskBean.TaskBean.TaskListBean taskListBean = mTaskList.get(i);
        RecyclerHolder recyclerHolder = (RecyclerHolder)viewHolder;
        recyclerHolder.tvTaskContent.setText(taskListBean.getTaskName());
        recyclerHolder.tvCont.setText(taskListBean.getTaskFinishCount()+"/"+taskListBean.getTaskRepeartCount());
        if (!TextUtils.isEmpty(taskListBean.getTaskReward())){
            recyclerHolder.tvJiangli.setVisibility(View.VISIBLE);
            recyclerHolder.l1.setVisibility(View.VISIBLE);
            recyclerHolder.l2.setVisibility(View.GONE);
            recyclerHolder.tvJiangli.setText(taskListBean.getTaskReward());
        }else {
            recyclerHolder.tvJiangli.setVisibility(View.GONE);
            recyclerHolder.l1.setVisibility(View.GONE);
            recyclerHolder.l2.setVisibility(View.VISIBLE);
        }
        if ("DOING".equals(taskListBean.getTaskFinishStatus())){
            recyclerHolder.tvStatus.setBackground(mContext.getResources().getDrawable(R.drawable.selector_task_button_bg));
            recyclerHolder.tvStatus.setSelected(false);
            recyclerHolder.tvStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity)mContext).updateCurrentDateTask(taskListBean.getLogTaskId(),"AWAITCONFIRM");
                }
            });
        }else if ("FINISH".equals(taskListBean.getTaskFinishStatus()) || "CONFIRM".equals(taskListBean.getTaskFinishStatus())){
            recyclerHolder.tvStatus.setBackground(mContext.getResources().getDrawable(R.mipmap.aixin2));
            recyclerHolder.tvStatus.setOnClickListener(null);
        } else if ("AWAITCONFIRM".equals(taskListBean.getTaskFinishStatus())){
            awaitConfirmCount +=1;
            recyclerHolder.tvStatus.setBackground(mContext.getResources().getDrawable(R.drawable.selector_task_button_bg));
            recyclerHolder.tvStatus.setSelected(true);
            recyclerHolder.tvStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity)mContext).updateCurrentDateTask(taskListBean.getLogTaskId(),"DOING");
                }
            });
        }else {
            recyclerHolder.tvStatus.setBackground(mContext.getResources().getDrawable(R.drawable.selector_task_button_bg));
            recyclerHolder.tvStatus.setSelected(true);
            recyclerHolder.tvStatus.setOnClickListener(null);
        }


        recyclerHolder.tvTaskContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("FINISH".equals(taskListBean.getTaskFinishStatus())||"CONFIRM".equals(taskListBean.getTaskFinishStatus())){
                    AudioUtils.getInstance().speakText("哇！真棒，你已经完成了任务并获得一个森林币，快去投入公益池帮助小鹿丫丫回家吧");
                }else {
                    String bobao = taskListBean.getTaskName();
                    if (!TextUtils.isEmpty(taskListBean.getTaskReward())){
                        bobao = bobao+"[p1000]奖励" + taskListBean.getTaskReward();
                    }
                    AudioUtils.getInstance().speakText(bobao);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }


    public void setData(List<CurrentDateTaskBean.TaskBean.TaskListBean> taskList){
        if (taskList != null){
            mTaskList.clear();
            mTaskList.addAll(taskList);
        }else mTaskList.clear();
        awaitConfirmCount = 0;
        notifyDataSetChanged();
    }

    public int getAwaitConfirmCount(){
        return awaitConfirmCount;
    }

    static class RecyclerHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.tv_task_content)
        TextView tvTaskContent;
        @BindView(R.id.tv_cont)
        TextView tvCont;
        @BindView(R.id.tv_jiangli)
        TextView tvJiangli;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.l1)
        View l1;
        @BindView(R.id.l2)
        View l2;
        private RecyclerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}


