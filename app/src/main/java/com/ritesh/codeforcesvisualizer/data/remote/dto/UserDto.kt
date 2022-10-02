package com.ritesh.codeforcesvisualizer.data.remote.dto

import com.ritesh.codeforcesvisualizer.data.local.entity.UserEntity
import com.ritesh.codeforcesvisualizer.model.User

data class UserDto(
    val avatar: String,
    val city: String,
    val contribution: Int,
    val country: String,
    val firstName: String?,
    val friendOfCount: Int,
    val handle: String,
    val lastName: String?,
    val lastOnlineTimeSeconds: Int,
    val maxRank: String,
    val maxRating: Int,
    val organization: String?,
    val rank: String,
    val rating: Int,
    val registrationTimeSeconds: Int,
    val titlePhoto: String

) {
    fun toUser(): User {
        return User(
            name = "$firstName $lastName",
            handle = handle,
            rank = rank,
            rating = rating,
            maxRank = maxRank,
            maxRating = maxRating,
            photoUrl = titlePhoto,
            organization = organization
        )
    }

    fun toUserEntity(): UserEntity {
        return UserEntity(
            name = "$firstName $lastName",
            handle = handle,
            rank = rank,
            rating = rating,
            maxRank = maxRank,
            maxRating = maxRating,
            photoUrl = titlePhoto,
            organization = organization
        )
    }
}