package com.ritesh.codeforcesvisualizer.repository

import com.ritesh.codeforcesvisualizer.data.local.dao.ContestDao
import com.ritesh.codeforcesvisualizer.data.remote.CodeforcesApi
import com.ritesh.codeforcesvisualizer.model.Contest
import com.ritesh.codeforcesvisualizer.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class ContestRepository(
    private val api: CodeforcesApi,
    private val dao: ContestDao
) {

    fun getContestList(): Flow<Resource<List<Contest>>> = flow {
        emit(Resource.Loading())

        val contestList = dao.getLocalContestList().map { it.toContest() }
        emit(Resource.Loading(data = contestList))

        try {
            val remoteContestList = api.getContestsList().result
            dao.deleteContests()
            dao.insertContestList(remoteContestList.map { it.toContestEntity() })
            emit(Resource.Success(remoteContestList.map { it.toContest() }))
        } catch (e: HttpException) {
            emit(Resource.Error(
                message = "Oops, something went wrong!",
                data = contestList
            ))
        } catch (e: IOException) {
            emit(Resource.Error(
                message = "Couldn't reach server, check your internet connection.",
                data = contestList
            ))
        }
    }
}