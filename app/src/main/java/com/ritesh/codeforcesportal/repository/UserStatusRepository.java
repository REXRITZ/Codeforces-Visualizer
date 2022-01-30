package com.ritesh.codeforcesportal.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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
    private MutableLiveData<List<Submission>> userSubmisions;

    public UserStatusRepository() {
        apiInterface = RetrofitClient.getApiInterface();
        userSubmisions = new MutableLiveData<>();
    }


    public void loadUserStats() {
        apiInterface.getUsersStatus("user.status","07rritz")
                .enqueue(new Callback<UserStatusResponse>() {
                    @Override
                    public void onResponse(Call<UserStatusResponse> call, Response<UserStatusResponse> response) {
                        if(response.isSuccessful() && response.body() != null
                                && response.body().getStatus().equals("OK")) {
                            userSubmisions.postValue(response.body().getUserSubmissions());
                        }
                    }

                    @Override
                    public void onFailure(Call<UserStatusResponse> call, Throwable t) {
                        userSubmisions.postValue(null);
                    }
                });

        System.out.println(userSubmisions.getValue() == null);
    }


    public LiveData<List<Submission>> getUserSubmissions() {
        return userSubmisions;
    }

}
