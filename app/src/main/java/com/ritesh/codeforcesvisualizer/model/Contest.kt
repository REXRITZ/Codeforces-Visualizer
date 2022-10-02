package com.ritesh.codeforcesvisualizer.model

data class Contest(
    val id: Int,
    val name: String,
    val phase: String,
    val duration: Long,
    val startTime: Long,
    val type: String
)
