package com.ritesh.codeforcesvisualizer.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ritesh.codeforcesvisualizer.model.User

@Entity
data class UserEntity(
    val name: String,
    @PrimaryKey val handle: String,
    val rank: String,
    val rating: Int,
    val maxRank: String,
    val maxRating: Int,
    val photoUrl: String,
    val organization: String?
) {
    fun toUser() = User(name,handle,rank,rating,maxRank, maxRating,photoUrl,organization)
}
