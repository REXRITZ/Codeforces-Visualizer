package com.ritesh.codeforcesvisualizer.ui.user

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ritesh.codeforcesvisualizer.model.Blog
import com.ritesh.codeforcesvisualizer.model.User
import com.ritesh.codeforcesvisualizer.repository.UserRepository
import com.ritesh.codeforcesvisualizer.util.Resource
import com.ritesh.codeforcesvisualizer.util.UiStateInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {


    private val _blogs = MutableLiveData(UiStateInfo<Blog>())
    val blogs: LiveData<UiStateInfo<Blog>> = _blogs

    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> = _user

    private var job: Job ?= null
    private val scope = CoroutineScope(Dispatchers.IO)
    init {
        getData(null)
    }

    fun getData(handle: String?) {
        job?.cancel()
        job = scope.launch {
            launch {
                getBlogs(handle)
            }
            launch {
                getUserInfo(handle)
            }
        }
    }

    private suspend fun getBlogs(handle: String?) {
        userRepository.getBlogs(handle).collect { result->
            when(result) {
                is Resource.Error -> {
                    _blogs.postValue(
                        _blogs.value?.copy(
                            data = result.data ?: emptyList()
                        )
                    )
                }
                is Resource.Loading -> {
                    _blogs.postValue(
                        _blogs.value?.copy(
                            isLoading = View.VISIBLE,
                            showError = false,
                            data = result.data ?: emptyList()
                        )
                    )
                }
                is Resource.Success -> {
                    _blogs.postValue(
                        _blogs.value?.copy(
                            data = result.data ?: emptyList(),
                            isLoading = View.GONE,
                            showError = false
                        )
                    )
                }
            }
        }
    }

    private suspend fun getUserInfo(handle: String?) {
        userRepository.getUserInfo(handle).collect { result->
            when(result) {
                is Resource.Error -> {
                    _blogs.postValue(
                        _blogs.value?.copy(
                            isLoading = View.GONE,
                            showError = true,
                            errorMessage = result.message
                        )
                    )
                }
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    _user.postValue(result.data)
                }
            }
        }
    }
}