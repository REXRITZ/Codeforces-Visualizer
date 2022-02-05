package com.ritesh.codeforcesportal.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.ritesh.codeforcesportal.R;
import com.ritesh.codeforcesportal.adapter.ContestAdapter;
import com.ritesh.codeforcesportal.model.Contest;
import com.ritesh.codeforcesportal.model.User;
import com.ritesh.codeforcesportal.utils.Utils;
import com.ritesh.codeforcesportal.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ContestAdapter adapter;
    private LinearProgressIndicator ratingProgress;
    private TextView userName, fullName, userRank, userRating;
    private CircleImageView profilePic;
    private User user;
    private ArrayList<Contest> contests;
    private AlertDialog loadingScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        loadLoadingDialog();
        contests = new ArrayList<>();
        MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.init();
        viewModel.getContestProgressObservable().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean progress) {
                if(progress) {
                    loadingScreen.show();
                } else {
                    loadingScreen.dismiss();
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
                if(userList != null) {
                    user = userList.get(0);
                    displayUserData(user);
                    contests = (ArrayList<Contest>) viewModel.getContestList().getValue();
                }
            }
        });

    }

    private void loadLoadingDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.loading,null);
        alertDialog.setView(view);

        loadingScreen = alertDialog.create();
        loadingScreen.setCanceledOnTouchOutside(false);

    }

    public void initViews() {
        ratingProgress = findViewById(R.id.user_rating_progress);
        profilePic = findViewById(R.id.profile_photo);
        fullName = findViewById(R.id.full_name);
        userName = findViewById(R.id.user_name);
        userRank = findViewById(R.id.user_rank);
        userRating = findViewById(R.id.user_rating);
        recyclerView = findViewById(R.id.upcoming_contest_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ContestAdapter(MainActivity.this,true);
        recyclerView.setAdapter(adapter);


        findViewById(R.id.btn_more_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,UserStatsActivity.class);
                intent.putExtra("userProfile",user);
                startActivity(intent);
//                startActivity(new Intent(MainActivity.this,UserStatsActivity.class));
            }
        });

        findViewById(R.id.btn_more_contest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ContestActivity.class);
                intent.putParcelableArrayListExtra("contests",contests);
                startActivity(intent);
            }
        });
    }
    public void displayUserData(User user) {
        Glide.with(this)
                .load(user.getAvatar())
                .into(profilePic);
        profilePic.setBorderColor(getResources().getColor(Utils.getRankColor(user.getRating())));
        userName.setText(user.getHandle());
        userRank.setText(user.getRank());
        userRank.setTextColor(getResources().getColor(Utils.getRankColor(user.getRating())));
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
        ratingProgress.setProgress(user.getRating());
    }
}