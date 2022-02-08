package com.ritesh.codeforcesportal.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.ritesh.codeforcesportal.model.Submission;
import com.ritesh.codeforcesportal.repository.UserStatusRepository;

import java.util.List;

public class UserStatsViewModel extends AndroidViewModel {

    private final UserStatusRepository repository;
    private final MutableLiveData<List<Submission>> userSubmissions;
    public UserStatsViewModel(@NonNull Application application) {
        super(application);
        repository = new UserStatusRepository();
        userSubmissions = new MutableLiveData<>();
    }

    public void init(String handle) {
        repository.loadUserStats(handle);
        repository.getUserSubmissions().observeForever(new Observer<List<Submission>>() {
            @Override
            public void onChanged(List<Submission> submissions) {
                userSubmissions.postValue(submissions);
            }
        });
    }

    public LiveData<List<Submission>> getUserSubmissions() {
        return userSubmissions;
    }
}
