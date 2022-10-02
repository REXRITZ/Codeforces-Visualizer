package com.ritesh.codeforcesvisualizer.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ritesh.codeforcesvisualizer.model.Blog

@Entity
data class BlogEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val lastModified: Long
) {
    fun toBlog() = Blog(id,title,lastModified)
}
