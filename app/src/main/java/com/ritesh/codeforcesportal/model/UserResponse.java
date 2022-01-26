package com.ritesh.codeforcesportal.model;

import com.squareup.moshi.Json;

import java.util.List;

public class UserResponse {
    private String status;
    @Json(name = "result")
    private List<User> userList;

    public String getStatus() {
        return status;
    }

    public List<User> getUserList() {
        return userList;
    }
}
