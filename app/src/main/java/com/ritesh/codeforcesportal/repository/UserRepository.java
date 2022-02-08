package com.ritesh.codeforcesportal.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ritesh.codeforcesportal.model.Status;
import com.ritesh.codeforcesportal.model.UserResponse;
import com.ritesh.codeforcesportal.service.ApiInterface;
import com.ritesh.codeforcesportal.service.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private static ApiInterface apiInterface;
    private final MutableLiveData<UserResponse> usersList;

    public UserRepository() {
        apiInterface = RetrofitClient.getApiInterface();
        usersList = new MutableLiveData<>();
    }

    public void fetchUserProfile(String handles) {
        apiInterface.getUsersProfile("user.info",handles).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (!response.isSuccessful() || response.body() == null) return;
                UserResponse userResponse = response.body();
                if(userResponse.getStatus() == Status.OK) {
                    usersList.postValue(userResponse);
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                usersList.postValue(null);
            }
        });
    }

    public LiveData<UserResponse> getUsersProfile() {
        return usersList;
    }
}
