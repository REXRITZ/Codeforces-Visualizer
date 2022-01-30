package com.ritesh.codeforcesportal.service;

import com.ritesh.codeforcesportal.model.ContestResponse;
import com.ritesh.codeforcesportal.model.UserResponse;
import com.ritesh.codeforcesportal.model.UserStatusResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("{methodName}")
    Call<ContestResponse> getContestDetails(@Path("methodName") String method);

    @GET("{methodName}")
    Call<UserResponse> getUsersProfile(@Path("methodName") String method, @Query("handles") String handles);

    @GET("{methodName}")
    Call<UserStatusResponse> getUsersStatus(@Path("methodName") String method, @Query("handle") String handle);
}
