package com.ritesh.codeforcesportal.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import com.google.android.material.chip.ChipGroup;
import com.ritesh.codeforcesportal.R;
import com.ritesh.codeforcesportal.adapter.ContestAdapter;
import com.ritesh.codeforcesportal.model.Contest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContestActivity extends AppCompatActivity {

    private List<Contest> upComingContest,onGoingContest,finishedContest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contest);

        upComingContest = new ArrayList<>();
        onGoingContest = new ArrayList<>();
        finishedContest = new ArrayList<>();
        loadData(getIntent().getParcelableArrayListExtra("contests"));

        RecyclerView recyclerView = findViewById(R.id.contest_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ContestAdapter adapter = new ContestAdapter(this,false);
        recyclerView.setAdapter(adapter);
        adapter.updateList(upComingContest);
        ChipGroup group = findViewById(R.id.contest_filter);
        group.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                if(checkedId == R.id.chip_upcoming) {
                    adapter.updateList(upComingContest);
                } else if(checkedId == R.id.chip_ongoing) {
                    adapter.updateList(onGoingContest);
                } else if(checkedId == R.id.chip_finished) {
                    adapter.updateList(finishedContest);
                }
            }
        });
    }

    private void loadData(ArrayList<Contest> contests) {
        if(contests == null) return;
        for(Contest contest : contests) {
            String phase = contest.getPhase();
            switch (phase) {
                case "BEFORE":
                    upComingContest.add(contest);
                    break;
                case "CODING":
                    onGoingContest.add(contest);
                    break;
                case "FINISHED":
                    finishedContest.add(contest);
                    break;
            }
        }
        Collections.reverse(upComingContest);
    }
}