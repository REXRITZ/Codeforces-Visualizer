package com.ritesh.codeforcesportal.adapter;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ritesh.codeforcesportal.R;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder>{

    private ArrayList<Pair<String,String>> userList;
    private final Context context;

    public UserAdapter(Context context) {
        this.context = context;
    }

    public void updateList(ArrayList<Pair<String,String>> list) {
        this.userList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_info_list_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Pair<String,String> p = userList.get(position);
        holder.name.setText(p.first);
        holder.value.setText(p.second);
    }

    @Override
    public int getItemCount() {
        if (userList == null)
            return 0;
        return userList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        final TextView name;
        final TextView value;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.user_key);
            value = itemView.findViewById(R.id.user_value);
        }
    }
}
