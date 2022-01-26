package com.ritesh.codeforcesportal.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ritesh.codeforcesportal.model.ContestResponse;
import com.ritesh.codeforcesportal.model.User;
import com.ritesh.codeforcesportal.model.UserResponse;
import com.ritesh.codeforcesportal.service.ApiInterface;
import com.ritesh.codeforcesportal.service.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private static ApiInterface apiInterface;
    private MutableLiveData<List<User>> usersList;

    public UserRepository() {
        apiInterface = RetrofitService.getApiInterface();
        usersList = new MutableLiveData<>();
    }

    public void fetchUserProfile(String handles) {
        apiInterface.getUsersProfile("user.info",handles).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (!response.isSuccessful() || response.body() == null) return;
                UserResponse userResponse = response.body();
                if(userResponse.getStatus().equals("OK")) {
                    usersList.postValue(userResponse.getUserList());
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                usersList.postValue(null);
            }
        });
    }

    public LiveData<List<User>> getUsersProfile() {
        return usersList;
    }
}
