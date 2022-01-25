package com.ritesh.codeforcesportal.service;

import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class RetrofitService {

    private static final String baseUrl = "https://codeforces.com/api/";

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .build();

    public static ApiInterface getApiInterface() {
        return  retrofit.create(ApiInterface.class);
    }
}
