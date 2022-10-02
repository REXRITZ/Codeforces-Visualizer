package com.ritesh.codeforcesvisualizer.data.remote.dto

import com.ritesh.codeforcesvisualizer.data.local.entity.BlogEntity
import com.ritesh.codeforcesvisualizer.model.Blog

data class BlogDto(
    val allowViewHistory: Boolean,
    val authorHandle: String,
    val creationTimeSeconds: Int,
    val id: Int,
    val locale: String,
    val modificationTimeSeconds: Int,
    val originalLocale: String,
    val rating: Int,
    val tags: List<String>,
    val title: String
) {

    fun toBlog(): Blog {
        return Blog(
            id = id,
            title = toFormattedTitle(title),
            lastModified = modificationTimeSeconds * 1000L
        )
    }

    fun toBlogEntity(): BlogEntity {
        return BlogEntity(
            id = id,
            title = toFormattedTitle(title),
            lastModified = modificationTimeSeconds * 1000L
        )
    }

    private fun toFormattedTitle(title: String): String {
        var res: String = title.removePrefix("<p>")
        res = res.removeSuffix("</p>")
        return res
    }
}