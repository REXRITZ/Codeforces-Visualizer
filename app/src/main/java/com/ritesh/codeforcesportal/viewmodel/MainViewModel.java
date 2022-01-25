package com.ritesh.codeforcesportal.viewmodel;

import android.app.Application;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ritesh.codeforcesportal.model.Contest;
import com.ritesh.codeforcesportal.repository.ContestRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainViewModel extends AndroidViewModel {

    private final ContestRepository contestRepository;
    private LiveData<List<Contest>> contestList;
    private final MutableLiveData<List<Contest>> upComingContests = new MutableLiveData<>();
    private final MutableLiveData<Boolean> contestProgressObservable = new MutableLiveData<>();
    public MainViewModel(@NonNull Application application) {
        super(application);
        contestRepository = new ContestRepository();
    }

    public void init() {
        contestProgressObservable.postValue(true);
        contestRepository.loadContestData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                contestList = contestRepository.getContestDetails();
                loadContestData();
            }
        },3000);
    }

    private void loadContestData() {
        List<Contest>contests = contestList.getValue();
        List<Contest>upComing = new ArrayList<>();
        if(contests == null) return;
        for(int index = 0; index < contests.size(); ++index) {
            Contest contest = contests.get(index);
            if(contest.getPhase().equals("BEFORE")) {
                upComing.add(contest);
            } else {
                break;
            }
        }
        upComingContests.setValue(upComing);
        contestProgressObservable.postValue(false);
    }

    public LiveData<Boolean> getContestProgressObservable() {
        return contestProgressObservable;
    }

    public LiveData<List<Contest>> getUpComingContests() {
        return upComingContests;
    }

}
