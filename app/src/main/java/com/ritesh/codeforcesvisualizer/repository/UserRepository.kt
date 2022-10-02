package com.ritesh.codeforcesvisualizer.repository

import com.ritesh.codeforcesvisualizer.data.local.dao.UserDao
import com.ritesh.codeforcesvisualizer.data.remote.CodeforcesApi
import com.ritesh.codeforcesvisualizer.model.Blog
import com.ritesh.codeforcesvisualizer.model.User
import com.ritesh.codeforcesvisualizer.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class UserRepository(
    private val api: CodeforcesApi,
    private val dao: UserDao
) {

    fun getBlogs(handle: String?): Flow<Resource<List<Blog>>> = flow {

        emit(Resource.Loading())

        val blogList = dao.getLocalBlogs().map { it.toBlog() }
        emit(Resource.Loading(data = blogList))

        try {
            val remoteBlogList = api.getBlogs(handle ?: "tourist").result
            dao.deleteBlogs()
            dao.insertBlogs(remoteBlogList.map { it.toBlogEntity() })
            emit(Resource.Success(remoteBlogList.map { it.toBlog() }))
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                message = "Oops, something went wrong!",
                data = blogList
            ))
        } catch (e: IOException) {
            emit(
                Resource.Error(
                message = "Couldn't reach server, check your internet connection.",
                data = blogList
            ))
        }
    }

    fun getUserInfo(handle: String?): Flow<Resource<User>> = flow {
        emit(Resource.Loading())

        val userInfoQuery = dao.getLocalUserInfo()
        val userInfo =
            if(userInfoQuery.isEmpty())
                null
            else
                userInfoQuery[0].toUser()
        emit(Resource.Loading(data = userInfo))

        try {
            val remoteUserInfo = api.getUserInfo(handle ?: "tourist").result
            dao.deleteUserInfo()
            dao.insertUserInfo(remoteUserInfo[0].toUserEntity())
            emit(Resource.Success(remoteUserInfo[0].toUser()))
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "Oops, something went wrong!",
                    data = userInfo
                ))
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Couldn't reach server, check your internet connection.",
                    data = userInfo
                ))
        }
    }
}