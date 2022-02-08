package com.ritesh.codeforcesportal.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.textfield.TextInputLayout;
import com.ritesh.codeforcesportal.R;
import com.ritesh.codeforcesportal.adapter.ContestAdapter;
import com.ritesh.codeforcesportal.model.Contest;
import com.ritesh.codeforcesportal.model.Status;
import com.ritesh.codeforcesportal.model.User;
import com.ritesh.codeforcesportal.model.UserResponse;
import com.ritesh.codeforcesportal.utils.Utils;
import com.ritesh.codeforcesportal.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private ContestAdapter adapter;
    private LinearProgressIndicator ratingProgress;
    private TextView userName, fullName, userRank, userRating;
    private CircleImageView profilePic;
    private User user;
    private ArrayList<Contest> contests;
    private AlertDialog loadingScreen;
    private Status status;
    private String comment;
    private String handle;
    private SharedPreferences preferences;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        handle = preferences.getString("handle", "tourist");
        initViews();
        loadLoadingDialog();
        contests = new ArrayList<>();
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.init();
        viewModel.initUser(handle);
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
            public void onChanged(List<Contest> c) {
                adapter.updateList(c);
                contests = (ArrayList<Contest>) viewModel.getContestList().getValue();
            }
        });

        viewModel.getUsersProfile().observe(this, new Observer<UserResponse>() {
            @Override
            public void onChanged(UserResponse userResponse) {
                if(userResponse != null) {
                    status = userResponse.getStatus();
                    comment = userResponse.getComment();
                    user = userResponse.getUserList().get(0);
                    displayUserData(user);
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
        ExtendedFloatingActionButton searchUser = findViewById(R.id.search_user);
        ratingProgress = findViewById(R.id.user_rating_progress);
        profilePic = findViewById(R.id.profile_photo);
        fullName = findViewById(R.id.full_name);
        userName = findViewById(R.id.user_name);
        userRank = findViewById(R.id.user_rank);
        userRating = findViewById(R.id.user_rating);
        RecyclerView recyclerView = findViewById(R.id.upcoming_contest_list);
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

        searchUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inflateThat();

            }
        });
    }

    private void inflateThat() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.search_user,null);
        alertDialog.setView(view);

        TextInputLayout inputLayout = view.findViewById(R.id.search_edittext);
        MaterialButton searchBtn = view.findViewById(R.id.btn_search_user);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = String.valueOf(Objects.requireNonNull(inputLayout.getEditText()).getText()).trim();
                if(s.length() == 0) {
                    inputLayout.setError("input is empty");
                    return;
                }
                viewModel.initUser(s);
            }
        });
        AlertDialog dialog = alertDialog.create();
        dialog.show();
        viewModel.isValidUser().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isValid) {
                if (Objects.requireNonNull(inputLayout.getEditText()).getText().toString().trim().length() > 0) {
                    if (isValid) {
                        System.out.println("VALID");
                        handle = inputLayout.getEditText().getText().toString();
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("handle", handle);
                        editor.apply();
                        dialog.dismiss();
                    } else {
                        System.out.println("INVALID");
                        inputLayout.setError("error please check if connected to internet or user handle is valid");
                    }
                }
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
        StringBuilder name = new StringBuilder();
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