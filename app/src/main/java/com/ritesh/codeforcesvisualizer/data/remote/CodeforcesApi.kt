package com.ritesh.codeforcesvisualizer.data.remote

import com.ritesh.codeforcesvisualizer.data.remote.dto.*
import retrofit2.http.GET
import retrofit2.http.Query

interface CodeforcesApi {

    @GET("/api/contest.list")
    suspend fun getContestsList(): Response<ContestDto>

    @GET("/api/user.status")
    suspend fun getUserSubmissions(@Query("handle") handle: String): Response<SubmissionDto>

    @GET("api/user.blogEntries")
    suspend fun getBlogs(@Query("handle") handle: String): Response<BlogDto>

    @GET("api/user.info")
    suspend fun getUserInfo(@Query("handles") handle: String): Response<UserDto>

    companion object {
        val BASE_URL = "https://codeforces.com"
    }
}