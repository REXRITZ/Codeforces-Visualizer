package com.ritesh.codeforcesvisualizer.ui.contest

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ritesh.codeforcesvisualizer.model.Contest
import com.ritesh.codeforcesvisualizer.repository.ContestRepository
import com.ritesh.codeforcesvisualizer.util.Resource
import com.ritesh.codeforcesvisualizer.util.UiStateInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContestViewModel @Inject constructor(
    private val contestRepository: ContestRepository
) : ViewModel() {

    private var job: Job? = null

    private val _contestState = MutableLiveData(UiStateInfo<Contest>())
    val contestState: LiveData<UiStateInfo<Contest>> = _contestState

    init {
        getContestList()
    }

    fun getContestList() {
        job?.cancel()
        job = viewModelScope.launch {
            delay(200)
            contestRepository.getContestList().collect { result ->
                when(result) {
                    is Resource.Loading -> {
                        _contestState.postValue(
                            _contestState.value?.copy(
                                isLoading = View.VISIBLE,
                                showError = false,
                                data = result.data ?: emptyList()
                            )
                        )
                    }
                    is Resource.Success -> {
                        _contestState.postValue(
                            _contestState.value?.copy(
                                data = result.data ?: emptyList(),
                                isLoading = View.GONE,
                                showError = false
                            )
                        )
                    }
                    is Resource.Error -> {
                        _contestState.postValue(
                            _contestState.value?.copy(
                                isLoading = View.GONE,
                                showError = true,
                                errorMessage = result.message,
                                data = result.data ?: emptyList()
                            )
                        )
                    }
                }
            }
        }
    }
}