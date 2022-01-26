package com.ritesh.codeforcesportal.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.ritesh.codeforcesportal.R;
import com.ritesh.codeforcesportal.adapter.ContestAdapter;
import com.ritesh.codeforcesportal.model.Contest;
import com.ritesh.codeforcesportal.model.User;
import com.ritesh.codeforcesportal.viewmodel.MainViewModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private CircularProgressIndicator progressIndicator;
    private RecyclerView recyclerView;
    private ContestAdapter adapter;
    private TextView userName, fullName, userRank, userRating;
    private CircleImageView profilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.init();
        viewModel.getContestProgressObservable().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean progress) {
                if(progress) {
                    progressIndicator.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    progressIndicator.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }
        });

        viewModel.getUpComingContests().observe(this, new Observer<List<Contest>>() {
            @Override
            public void onChanged(List<Contest> contests) {
                adapter.updateList(contests);
            }
        });

        viewModel.getUsersProfile().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> userList) {
                if(userList != null)
                    displayUserData(userList.get(0));
            }
        });
    }

    public void initViews() {
        profilePic = findViewById(R.id.profile_photo);
        fullName = findViewById(R.id.full_name);
        userName = findViewById(R.id.user_name);
        userRank = findViewById(R.id.user_rank);
        userRating = findViewById(R.id.user_rating);
        progressIndicator = findViewById(R.id.contest_list_progress);
        recyclerView = findViewById(R.id.upcoming_contest_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ContestAdapter(MainActivity.this,true);
        recyclerView.setAdapter(adapter);
    }
    public void displayUserData(User user) {
        Glide.with(this)
                .load(user.getAvatar())
                .into(profilePic);

        userName.setText(user.getHandle());
        userRank.setText(user.getRank());
        userRating.setText("Rating: " + user.getRating());
        StringBuilder name = new StringBuilder("");
        if(user.getFirstName() != null) {
            name.append(user.getFirstName());
            name.append(" ");
        }
        if(user.getLastName() != null) {
            name.append(user.getLastName());
        }
        if(name.length() == 0)
            fullName.setVisibility(View.GONE);
        else {
            fullName.setVisibility(View.VISIBLE);
            fullName.setText(name);
        }
    }
}