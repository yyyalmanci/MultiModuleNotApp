package com.y3.presentation.model

import com.y3.domain.model.TaskDomainModel

sealed class TaskUiState {
    object Loading : TaskUiState()
    class Success(val task: TaskDomainModel) : TaskUiState()
    object Idle : TaskUiState()
    object Failure : TaskUiState()
}