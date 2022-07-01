package com.ritesh.codeforcesportal.service

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import com.ritesh.codeforcesportal.service.CodeforcesApi
import retrofit2.converter.gson.GsonConverterFactory
import com.ritesh.codeforcesportal.service.ApiInterface
import java.util.concurrent.TimeUnit

object CodeforcesApi {
    private const val baseUrl = "https://codeforces.com/api/"
    private val client = OkHttpClient.Builder()
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .build()
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val apiInterface: ApiInterface
        get() = retrofit.create(ApiInterface::class.java)
}