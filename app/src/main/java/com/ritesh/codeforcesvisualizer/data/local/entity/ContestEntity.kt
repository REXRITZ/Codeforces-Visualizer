package com.ritesh.codeforcesvisualizer.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ritesh.codeforcesvisualizer.model.Contest

@Entity
data class ContestEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val phase: String,
    val duration: Long,
    val startTime: Long,
    val type: String
) {
    fun toContest() = Contest(id,name,phase,duration,startTime,type)
}
