package com.ritesh.codeforcesvisualizer.repository

import com.ritesh.codeforcesvisualizer.data.local.dao.UserDao
import com.ritesh.codeforcesvisualizer.data.remote.CodeforcesApi
import com.ritesh.codeforcesvisualizer.model.Submission
import com.ritesh.codeforcesvisualizer.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class StatsRepository(
    private val api: CodeforcesApi,
    private val userDao: UserDao
) {

    fun getUserSubmissions(): Flow<Resource<List<Submission>>> = flow {
        emit(Resource.Loading())

        try {
            val handle = userDao.getUserHandle()
            val remoteSubmissionList = api.getUserSubmissions(handle ?: "tourist").result.map { it.toSubmission() }
            emit(Resource.Success(remoteSubmissionList))
        } catch (e: HttpException) {
            emit(Resource.Error(
                message = "Oops, something went wrong!",
                data = null
            ))
        } catch (e: IOException) {
            emit(Resource.Error(
                message = "Couldn't reach server, check your internet connection.",
                data = null
            ))
        }
    }
}