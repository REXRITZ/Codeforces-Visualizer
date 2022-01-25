package com.ritesh.codeforcesportal.service;

import com.ritesh.codeforcesportal.model.ContestResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("{methodName}")
    Call<ContestResponse> getContestDetails(@Path("methodName") String method);
}
