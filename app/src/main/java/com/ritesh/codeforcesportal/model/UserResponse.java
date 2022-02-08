package com.ritesh.codeforcesportal.model;

import com.squareup.moshi.Json;

import java.util.List;

public class UserResponse {
    private Status status;
    @Json(name = "result")
    private List<User> userList;
    private String comment;

    public Status getStatus() {
        return status;
    }

    public List<User> getUserList() {
        return userList;
    }

    public String getComment() {
        return comment;
    }
}
