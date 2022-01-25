package com.ritesh.codeforcesportal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.ritesh.codeforcesportal.R;
import com.ritesh.codeforcesportal.model.Contest;
import com.ritesh.codeforcesportal.utils.Utils;

import java.util.List;

public class ContestAdapter extends RecyclerView.Adapter<ContestAdapter.MyViewHolder>{

    private List<Contest> contestsList;
    private boolean isLimit;
    private final int LIMIT = 3;
    private final Context context;

    public ContestAdapter(Context context, boolean isLimit) {
        this.isLimit = isLimit;
        this.context = context;
    }

    public void updateList(List<Contest> list) {
        this.contestsList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contest_list_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Contest contest = contestsList.get(position);
        holder.contestName.setText(contest.getName());
        holder.contestType.setText(contest.getType());
        holder.contestLength.setText(Utils.formatTimeInSeconds(contest.getDurationSeconds()));
        holder.contestRating.setText(contest.getName());
        holder.contestStartTime.setText(Utils.formatContestStartTime(contest.getStartTimeSeconds()));
    }

    @Override
    public int getItemCount() {
        if(contestsList == null) return 0;
        if(isLimit)
            return Math.min(LIMIT,contestsList.size());
        return contestsList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView contestName, contestRating, contestLength, contestStartTime;
        Chip contestType;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            contestName = itemView.findViewById(R.id.contest_name);
            contestRating = itemView.findViewById(R.id.contest_rating);
            contestLength = itemView.findViewById(R.id.contest_length);
            contestStartTime = itemView.findViewById(R.id.contest_start_time);
            contestType = itemView.findViewById(R.id.contest_type);
        }
    }
}
