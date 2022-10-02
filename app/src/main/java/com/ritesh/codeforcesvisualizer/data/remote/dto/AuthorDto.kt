package com.ritesh.codeforcesvisualizer.data.remote.dto

data class AuthorDto(
    val contestId: Int,
    val ghost: Boolean,
    val members: List<MemberDto>,
    val participantType: String,
    val room: Int,
    val startTimeSeconds: Int
)