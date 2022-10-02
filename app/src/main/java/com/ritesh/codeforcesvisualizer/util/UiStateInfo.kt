package com.ritesh.codeforcesvisualizer.util

import android.view.View
import com.ritesh.codeforcesvisualizer.model.Contest

data class UiStateInfo<T>(
    val data: List<T> = emptyList(),
    val isLoading: Int = View.GONE,
    val showError: Boolean = false,
    val errorMessage: String? = null
)

