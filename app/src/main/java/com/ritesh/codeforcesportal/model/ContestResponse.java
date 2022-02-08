package com.ritesh.codeforcesportal.model;

import com.ritesh.codeforcesportal.model.Contest;
import com.squareup.moshi.Json;

import java.util.List;

public class ContestResponse {

    private Status status;
    @Json(name = "result")
    private List<Contest> contests;

    public Status getStatus() {
        return status;
    }

    public List<Contest> getContests() {
        return contests;
    }
}
