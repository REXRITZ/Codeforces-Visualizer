package com.ritesh.codeforcesvisualizer.data.remote.dto

import com.ritesh.codeforcesvisualizer.data.local.entity.ContestEntity
import com.ritesh.codeforcesvisualizer.model.Contest
import java.sql.Timestamp
import java.util.*

data class ContestDto(
    val durationSeconds: Int,
    val frozen: Boolean,
    val id: Int,
    val name: String,
    val phase: String,
    val relativeTimeSeconds: Int,
    val startTimeSeconds: Int,
    val type: String
) {

    fun toContest(): Contest {
        return Contest(
            id = id,
            name = name,
            phase = phase,
            duration = durationSeconds * 1000L,
            startTime = startTimeSeconds * 1000L,
            type = type
        )
    }

    fun toContestEntity(): ContestEntity {
        return ContestEntity(
            id = id,
            name = name,
            phase = phase,
            duration = durationSeconds * 1000L,
            startTime = startTimeSeconds * 1000L,
            type = type
        )
    }
}