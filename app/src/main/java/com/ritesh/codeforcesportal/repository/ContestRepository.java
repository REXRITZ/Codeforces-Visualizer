package com.ritesh.codeforcesportal.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ritesh.codeforcesportal.model.Contest;
import com.ritesh.codeforcesportal.model.ContestResponse;
import com.ritesh.codeforcesportal.service.ApiInterface;
import com.ritesh.codeforcesportal.service.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContestRepository {

    private static ApiInterface apiInterface;
    private final MutableLiveData<List<Contest>> contestList;
    public ContestRepository() {
        apiInterface = RetrofitClient.getApiInterface();
        contestList = new MutableLiveData<>();
    }

    public void loadContestData() {
        apiInterface.getContestDetails("contest.list").enqueue(new Callback<ContestResponse>() {
            @Override
            public void onResponse(Call<ContestResponse> call, Response<ContestResponse> response) {
                if (!response.isSuccessful() || response.body() == null) return;
                ContestResponse contestResponse = response.body();
                if(contestResponse.getStatus().equals("OK")) {
                    contestList.postValue(contestResponse.getContests());
                }
            }

            @Override
            public void onFailure(Call<ContestResponse> call, Throwable t) {
                contestList.postValue(null);
            }
        });
    }

    public LiveData<List<Contest>> getContestDetails() {
        return contestList;
    }


}
