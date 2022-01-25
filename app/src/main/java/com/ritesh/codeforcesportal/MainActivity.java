package com.ritesh.codeforcesportal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.ritesh.codeforcesportal.adapter.ContestAdapter;
import com.ritesh.codeforcesportal.model.Contest;
import com.ritesh.codeforcesportal.viewmodel.MainViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CircularProgressIndicator progressIndicator = findViewById(R.id.contest_list_progress);
        RecyclerView recyclerView = findViewById(R.id.upcoming_contest_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ContestAdapter adapter = new ContestAdapter(MainActivity.this,true);
        recyclerView.setAdapter(adapter);
        MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.init();
        viewModel.getContestProgressObservable().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean progress) {
                Toast.makeText(MainActivity.this, progress.toString(), Toast.LENGTH_SHORT).show();
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
                if(contests == null) {
                    System.out.println("null m");
                }
                adapter.updateList(contests);
            }
        });


    }
}