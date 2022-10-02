package com.ritesh.codeforcesvisualizer.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ritesh.codeforcesvisualizer.data.local.entity.ContestEntity

@Dao
interface ContestDao {

    @Query("SELECT * FROM ContestEntity ORDER BY startTime DESC")
    suspend fun getLocalContestList(): List<ContestEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContestList(contestList: List<ContestEntity>)

    @Query("DELETE FROM ContestEntity")
    suspend fun deleteContests()
}