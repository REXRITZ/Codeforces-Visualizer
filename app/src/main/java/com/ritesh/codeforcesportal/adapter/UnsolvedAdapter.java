package com.ritesh.codeforcesportal.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ritesh.codeforcesportal.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UnsolvedAdapter extends RecyclerView.Adapter<UnsolvedAdapter.MyViewHolder> {

    private List<String> problemNames;
    private Context context;

    public UnsolvedAdapter(Context context) {
        this.context = context;
    }

    public void updateList(List<String> list) {
        this.problemNames = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.unsolved_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String problemName = problemNames.get(position);
        holder.problemName.setText(problemName);
        holder.link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://codeforces.com/problemset/problem/" + problemName)));
            }
        });
    }

    @Override
    public int getItemCount() {
        if(problemNames == null)
            return 0;
        return problemNames.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView problemName;
        CircleImageView link;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            problemName = itemView.findViewById(R.id.problem_name);
            link = itemView.findViewById(R.id.goto_problem);
        }
    }
}
