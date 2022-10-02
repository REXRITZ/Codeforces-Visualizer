package com.ritesh.codeforcesvisualizer.model

data class User(
    val name: String,
    val handle: String,
    val rank: String,
    val rating: Int,
    val maxRank: String,
    val maxRating: Int,
    val photoUrl: String,
    val organization: String?
)
