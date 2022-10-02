package com.ritesh.codeforcesvisualizer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ritesh.codeforcesvisualizer.data.local.dao.ContestDao
import com.ritesh.codeforcesvisualizer.data.local.dao.UserDao
import com.ritesh.codeforcesvisualizer.data.local.entity.BlogEntity
import com.ritesh.codeforcesvisualizer.data.local.entity.ContestEntity
import com.ritesh.codeforcesvisualizer.data.local.entity.UserEntity
import com.ritesh.codeforcesvisualizer.util.Converters

@Database(entities = [BlogEntity::class, ContestEntity::class, UserEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getContestDao(): ContestDao
    abstract fun getUserDao(): UserDao


    companion object {
        val DATABASE_NAME = "codeforces_db"
    }
}