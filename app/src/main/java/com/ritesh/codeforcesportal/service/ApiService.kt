package com.ritesh.codeforcesportal.service

import retrofit2.http.GET
import com.ritesh.codeforcesportal.model.ContestResponse
import com.ritesh.codeforcesportal.model.UserResponse
import com.ritesh.codeforcesportal.model.UserStatusResponse
import retrofit2.Call
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("{methodName}")
    fun getContestDetails(@Path("methodName") method: String?): Call<ContestResponse?>?

    @GET("{methodName}")
    fun getUsersProfile(
        @Path("methodName") method: String?,
        @Query("handles") handles: String?
    ): Call<UserResponse?>?

    @GET("{methodName}")
    fun getUsersStatus(
        @Path("methodName") method: String?,
        @Query("handle") handle: String?
    ): Call<UserStatusResponse?>?
}