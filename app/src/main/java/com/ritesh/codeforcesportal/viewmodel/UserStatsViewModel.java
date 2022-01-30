package com.ritesh.codeforcesportal.viewmodel;

import android.app.Application;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ritesh.codeforcesportal.model.Submission;
import com.ritesh.codeforcesportal.repository.UserStatusRepository;

import java.util.List;

public class UserStatsViewModel extends AndroidViewModel {

    private UserStatusRepository repository;
    private MutableLiveData<List<Submission>> userSubmissions;
    public UserStatsViewModel(@NonNull Application application) {
        super(application);
        repository = new UserStatusRepository();
        userSubmissions = new MutableLiveData<>();
    }

    public void init() {
        repository.loadUserStats();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                userSubmissions.postValue(repository.getUserSubmissions().getValue());
            }
        },2000);
    }

    public LiveData<List<Submission>> getUserSubmissions() {
        return userSubmissions;
    }
}
