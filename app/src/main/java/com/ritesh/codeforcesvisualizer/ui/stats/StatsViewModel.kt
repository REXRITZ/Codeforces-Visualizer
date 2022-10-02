package com.ritesh.codeforcesvisualizer.ui.stats

import android.graphics.Color
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ritesh.codeforcesvisualizer.chart.PieData
import com.ritesh.codeforcesvisualizer.model.Submission
import com.ritesh.codeforcesvisualizer.repository.StatsRepository
import com.ritesh.codeforcesvisualizer.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val statsRepository: StatsRepository
): ViewModel() {

    private var job: Job? = null

    private val _verdictData = MutableLiveData<List<PieData>>()
    val verdictData: LiveData<List<PieData>> = _verdictData

    private val _tagsData = MutableLiveData<List<PieData>>()
    val tagsData: LiveData<List<PieData>> = _tagsData

    private val _progressIndicator = MutableLiveData<Int>()
    val progressIndicator: LiveData<Int> = _progressIndicator

    init {
        getUserSubmissions()
    }

    fun getUserSubmissions() {
        job?.cancel()
        job = viewModelScope.launch {
            statsRepository.getUserSubmissions().collect { result->
                when(result) {
                    is Resource.Error -> {
                        _progressIndicator.postValue(View.GONE)
                    }
                    is Resource.Loading -> {
                        _progressIndicator.postValue(View.VISIBLE)
                    }
                    is Resource.Success -> {
                        val data = result.data
                        if (data != null) {
                            loadVerdictData(data)
                            loadTagsData(data)
                        }
                        _progressIndicator.postValue(View.GONE)
                    }
                }
            }
        }
    }

    private fun loadTagsData(data: List<Submission>) {
        val tagsMap = mutableMapOf<String,Float>()
        var tagsCount = 0
        for (info in data) {
            for (tag in info.tags) {
                if(tagsMap.containsKey(tag)) {
                    tagsMap[tag] = tagsMap[tag]!! + 1f
                } else {
                    tagsMap[tag] = 1f
                }
            }
            tagsCount += info.tags.size
        }
        val finalData = mutableListOf<PieData>()
        var startAngle = 0f
        for(item in tagsMap) {
            val sweepAngle = item.value / tagsCount * 360
            finalData.add(PieData(item.key,item.value,startAngle,sweepAngle))
            startAngle += sweepAngle
        }
        _tagsData.value = finalData
    }

    private fun loadVerdictData(data: List<Submission>) {
        val verdictMap = mutableMapOf<String,Float>()
        for (info in data) {
            val key = info.verdict
            if(verdictMap.containsKey(key)) {
                verdictMap[key] = verdictMap[key]!! + 1f
            } else {
                verdictMap[key] = 1f
            }
        }
        val finalData = mutableListOf<PieData>()
        var startAngle = 0f
        for(item in verdictMap) {
            val sweepAngle = item.value / data.size * 360
            finalData.add(PieData(item.key,item.value,startAngle,sweepAngle))
            startAngle += sweepAngle
            println("$sweepAngle $startAngle")
        }
        _verdictData.value = finalData
    }
}