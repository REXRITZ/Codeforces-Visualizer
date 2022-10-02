package com.ritesh.codeforcesvisualizer.data.local.dao

import androidx.room.*
import com.ritesh.codeforcesvisualizer.data.local.entity.BlogEntity
import com.ritesh.codeforcesvisualizer.data.local.entity.UserEntity
import com.ritesh.codeforcesvisualizer.model.Blog
import com.ritesh.codeforcesvisualizer.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM UserEntity")
    suspend fun getLocalUserInfo(): List<UserEntity>

    @Query("SELECT * FROM BlogEntity ORDER BY lastModified DESC")
    suspend fun getLocalBlogs(): List<BlogEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBlogs(blogs: List<BlogEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserInfo(user: UserEntity)

    @Query("DELETE FROM BlogEntity")
    suspend fun deleteBlogs()

    @Query("DELETE FROM UserEntity")
    suspend fun deleteUserInfo()

    @Query("SELECT handle FROM UserEntity LIMIT 1")
    suspend fun getUserHandle(): String?
}