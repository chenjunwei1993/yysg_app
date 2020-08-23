package com.dyibing.myapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dyibing.myapp.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CourseInfoAdapter extends RecyclerView.Adapter {

    private final Context mContext;
    private List<List<String>> strings = new ArrayList<>();

    public CourseInfoAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_course_info, parent, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        List<String> strs = strings.get(position);
        RecyclerHolder recyclerHolder = (RecyclerHolder) holder;
        if (!TextUtils.isEmpty(strs.get(0)))
            recyclerHolder.tvCourse1.setText(strs.get(0));
        else recyclerHolder.tvCourse1.setText("");
        if (!TextUtils.isEmpty(strs.get(1)))
            recyclerHolder.tvCourse2.setText(strs.get(1));
        else recyclerHolder.tvCourse2.setText("");
        if (!TextUtils.isEmpty(strs.get(2)))
            recyclerHolder.tvCourse3.setText(strs.get(2));
        else recyclerHolder.tvCourse3.setText("");
    }

    @Override
    public int getItemCount() {
        return strings.size();
    }

    public void setData(List<List<String>> strings) {
        if (this.strings != null) {
            this.strings.clear();
            this.strings.addAll(strings);

        }else this.strings.clear();
        notifyDataSetChanged();
    }

    class RecyclerHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_course1)
        TextView tvCourse1;
        @BindView(R.id.tv_course2)
        TextView tvCourse2;
        @BindView(R.id.tv_course3)
        TextView tvCourse3;

        private RecyclerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
