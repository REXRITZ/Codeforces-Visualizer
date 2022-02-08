package com.ritesh.codeforcesportal.model;

import com.squareup.moshi.Json;

import java.util.List;

public class UserStatusResponse {

    private Status status;
    @Json(name = "result")
    private List<Submission> userSubmissions;

    public Status getStatus() {
        return status;
    }

    public List<Submission> getUserSubmissions() {
        return userSubmissions;
    }
}
