package com.y3.presentation.model

import com.y3.domain.model.TaskDomainModel


sealed class TasksUiState {
    object Loading : TasksUiState()
    class Success(val tasks: List<TaskDomainModel>) : TasksUiState()
    object Idle : TasksUiState()
    object Failure : TasksUiState()
}