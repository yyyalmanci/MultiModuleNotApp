package com.y3.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.y3.common.Resource
import com.y3.domain.GetTasksUseCase
import com.y3.presentation.model.TasksUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val tasksUseCase: GetTasksUseCase
) : ViewModel() {

    private var _tasks = MutableStateFlow<TasksUiState>(TasksUiState.Idle)
    val tasks = _tasks.asStateFlow()

    init {
        loadTask()
    }

    private fun loadTask() {
        viewModelScope.launch {
            _tasks.value = TasksUiState.Loading
            when (val resourceTasks = tasksUseCase().first()) {
                is Resource.Success -> {
                    _tasks.value = TasksUiState.Success(resourceTasks.data)
                }
                is Resource.Failure -> {
                    Log.d("failure", resourceTasks.throwable.message.toString())
                }

            }
        }
    }

}