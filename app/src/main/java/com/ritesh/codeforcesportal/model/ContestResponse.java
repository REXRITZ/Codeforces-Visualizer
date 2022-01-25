package com.ritesh.codeforcesportal.model;

import com.ritesh.codeforcesportal.model.Contest;
import com.squareup.moshi.Json;

import java.util.ArrayList;
import java.util.List;

public class ContestResponse {
    private String status;
    @Json(name = "result")
    private List<Contest> contests;

    public String getStatus() {
        return status;
    }

    public List<Contest> getContests() {
        return contests;
    }
}
