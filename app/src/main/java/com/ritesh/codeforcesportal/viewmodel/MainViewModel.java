package com.ritesh.codeforcesportal.viewmodel;

import android.app.Application;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ritesh.codeforcesportal.model.Contest;
import com.ritesh.codeforcesportal.model.User;
import com.ritesh.codeforcesportal.repository.ContestRepository;
import com.ritesh.codeforcesportal.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MainViewModel extends AndroidViewModel {

    private final ContestRepository contestRepository;
    private final UserRepository userRepository;
    private LiveData<List<Contest>> contestList;
    private MutableLiveData<List<User>> user = new MutableLiveData<>();
    private final MutableLiveData<List<Contest>> upComingContests = new MutableLiveData<>();
    private final MutableLiveData<Boolean> contestProgressObservable = new MutableLiveData<>();
    public MainViewModel(@NonNull Application application) {
        super(application);
        contestRepository = new ContestRepository();
        userRepository = new UserRepository();
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
        },2000);
        userRepository.fetchUserProfile("REXRITZ");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                user.postValue(userRepository.getUsersProfile().getValue());
            }
        },2000);
    }

    private void loadContestData() {
        List<Contest>contests = contestList.getValue();
        List<Contest>upComing = new ArrayList<>();
        if(contests == null) {
            contestProgressObservable.postValue(false);
            return;
        }
        for(int index = 0; index < contests.size(); ++index) {
            Contest contest = contests.get(index);
            if(contest.getPhase().equals("BEFORE")) {
                upComing.add(contest);
            } else {
                break;
            }
        }
        Collections.reverse(upComing);
        upComingContests.setValue(upComing);
        contestProgressObservable.postValue(false);
    }

    public LiveData<Boolean> getContestProgressObservable() {
        return contestProgressObservable;
    }

    public LiveData<List<Contest>> getUpComingContests() {
        return upComingContests;
    }

    public LiveData<List<Contest>> getContestList() {
        return contestList;
    }

    public LiveData<List<User>> getUsersProfile() {
        return user;
    }

}
