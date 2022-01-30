package com.ritesh.codeforcesportal.model;

import com.squareup.moshi.Json;

import java.util.List;

public class UserStatusResponse {

    private String status;
    @Json(name = "result")
    private List<Submission> userSubmissions;

    public String getStatus() {
        return status;
    }

    public List<Submission> getUserSubmissions() {
        return userSubmissions;
    }
}
