package com.ritesh.codeforcesvisualizer.model

data class Submission(
    val programmingLanguage: String,
    val verdict: String,
    val rating: Int,
    val tags: List<String>
)
