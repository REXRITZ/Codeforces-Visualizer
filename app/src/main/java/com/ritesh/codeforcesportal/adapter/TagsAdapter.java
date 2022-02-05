package com.ritesh.codeforcesportal.adapter;

import android.content.Context;
import android.nfc.Tag;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.ritesh.codeforcesportal.R;
import com.ritesh.codeforcesportal.model.Contest;
import com.ritesh.codeforcesportal.model.Tags;

import java.util.List;
import java.util.Random;

public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.MyViewHolder>{

    private List<Tags> tagsList;
    private Context context;
    private final int[] PASTEL_COLORS;
    private final int colorsLen;
    private Random random;
    public TagsAdapter(Context context, int[] PASTEL_COLORS) {
        this.context = context;
        this.PASTEL_COLORS = PASTEL_COLORS;
        colorsLen = PASTEL_COLORS.length;
        random = new Random();
    }

    public void updateList(List<Tags> list) {
        this.tagsList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tags_detail_list,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Tags tags = tagsList.get(position);
        holder.tagName.setText(tags.getName());
        holder.tagPercent.setText(tags.getPercent());
        holder.triedCount.setText(String.valueOf(tags.getTriedCount()));
        holder.solvedCount.setText(String.valueOf(tags.getSolvedCount()));
        holder.tag_bg.setBackgroundColor(PASTEL_COLORS[random.nextInt(colorsLen)]);

    }

    @Override
    public int getItemCount() {
        if(tagsList == null) return 0;
        return tagsList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tagName, tagPercent, solvedCount, triedCount;
        RelativeLayout tag_bg;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tagName = itemView.findViewById(R.id.tv_tag);
            tagPercent = itemView.findViewById(R.id.tv_percent);
            solvedCount = itemView.findViewById(R.id.tv_solvedCount);
            triedCount = itemView.findViewById(R.id.tv_triedCount);
            tag_bg = itemView.findViewById(R.id.tag_bg);
        }
    }
}
