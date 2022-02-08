package com.ritesh.codeforcesportal.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.ritesh.codeforcesportal.model.Contest;
import com.ritesh.codeforcesportal.model.UserResponse;
import com.ritesh.codeforcesportal.repository.ContestRepository;
import com.ritesh.codeforcesportal.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private final ContestRepository contestRepository;
    private final UserRepository userRepository;
    private LiveData<List<Contest>> contestList;
    private final MutableLiveData<UserResponse> user = new MutableLiveData<>();
    private final MutableLiveData<List<Contest>> upComingContests = new MutableLiveData<>();
    private final MutableLiveData<Boolean> contestProgressObservable = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isValid = new MutableLiveData<>();
    public MainViewModel(@NonNull Application application) {
        super(application);
        contestRepository = new ContestRepository();
        userRepository = new UserRepository();
        isValid.setValue(true);
    }

    public void init() {
        contestProgressObservable.postValue(true);
        contestRepository.loadContestData();
        contestRepository.getContestDetails().observeForever(new Observer<List<Contest>>() {
            @Override
            public void onChanged(List<Contest> list) {
               if(list != null) {
                   MutableLiveData<List<Contest>> m = new MutableLiveData<>();
                   m.setValue(list);
                   contestList = m;
                   loadContestData();
               } else {
                   contestProgressObservable.postValue(false);
               }
            }
        });
    }

    public void initUser(String handle) {
        userRepository.fetchUserProfile(handle);
        userRepository.getUsersProfile().observeForever(new Observer<UserResponse>() {
            @Override
            public void onChanged(UserResponse userResponse) {
                user.postValue((userResponse));
                isValid.postValue(userResponse != null);
            }
        });
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

    public LiveData<UserResponse> getUsersProfile() {
        return user;
    }

    public MutableLiveData<Boolean> isValidUser() {
        return isValid;
    }

}
