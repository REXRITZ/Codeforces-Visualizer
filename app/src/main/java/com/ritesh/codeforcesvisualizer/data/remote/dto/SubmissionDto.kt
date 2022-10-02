package com.ritesh.codeforcesvisualizer.data.remote.dto

import com.ritesh.codeforcesvisualizer.model.Submission

data class SubmissionDto(
    val author: AuthorDto,
    val contestId: Int,
    val creationTimeSeconds: Int,
    val id: Int,
    val memoryConsumedBytes: Int,
    val passedTestCount: Int,
    val problem: ProblemDto,
    val programmingLanguage: String,
    val relativeTimeSeconds: Int,
    val testset: String,
    val timeConsumedMillis: Int,
    val verdict: String
) {

    fun toSubmission(): Submission {
        return Submission(
            programmingLanguage = programmingLanguage,
            verdict = verdict,
            rating = problem.rating,
            tags = problem.tags
        )
    }
}