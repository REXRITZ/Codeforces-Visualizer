package com.ritesh.codeforcesvisualizer.data.remote.dto

data class Response<T>(
    val result: List<T>,
    val status: String
)