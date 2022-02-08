package com.ritesh.codeforcesportal.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ritesh.codeforcesportal.model.Status;
import com.ritesh.codeforcesportal.model.Submission;
import com.ritesh.codeforcesportal.model.UserStatusResponse;
import com.ritesh.codeforcesportal.service.ApiInterface;
import com.ritesh.codeforcesportal.service.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserStatusRepository {

    private static ApiInterface apiInterface;
    private static MutableLiveData<List<Submission>> userSubmisions;
    public UserStatusRepository() {
        apiInterface = RetrofitClient.getApiInterface();
        userSubmisions = new MutableLiveData<>();
    }


    public void loadUserStats(String handle) {
        apiInterface.getUsersStatus("user.status",handle).enqueue(new Callback<UserStatusResponse>() {
            @Override
            public void onResponse(Call<UserStatusResponse> call, Response<UserStatusResponse> response) {
                if(response.isSuccessful() && response.body() != null
                        && response.body().getStatus() == Status.OK) {
                    userSubmisions.postValue(response.body().getUserSubmissions());
                }
            }

            @Override
            public void onFailure(Call<UserStatusResponse> call, Throwable t) {
                Log.e("Error: ",t.toString());
                userSubmisions.postValue(null);
            }
        });
    }

    public LiveData<List<Submission>> getUserSubmissions() {
        return userSubmisions;
    }


}
